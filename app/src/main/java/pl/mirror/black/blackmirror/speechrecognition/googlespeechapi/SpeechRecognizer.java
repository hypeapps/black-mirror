package pl.mirror.black.blackmirror.speechrecognition.googlespeechapi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
/**
    Klasa zajmująca się rozpoznawaniem mowy. Implementuje serwis
    GoogleSpeechApi.
 */
public class SpeechRecognizer implements SpeechService.Listener, ServiceConnection {

    public interface Listener {
        void onSpeechRecognized(String result);

        void onFinishSpeechRecognizing();
    }

    private static final String TAG = "CommandSpeechRecognizer";

    private Context context;

    private SpeechService speechService;

    private VoiceRecorder voiceRecorder;

    private SpeechRecognizer.Listener listener;

    public SpeechRecognizer(Context context, SpeechRecognizer.Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * @param text    Rezultat rozpoznawania mowy.
     * @param isFinal {@code true} zwracana kiedy google cloud speech api zakończy przetwarzać mowę.
     * Jeśli rezulatat jest finalny, publikujemy event z odpowiednią komendą.
     */
    @Override
    public void onSpeechRecognized(String text, boolean isFinal) {
        Log.e("REZULATAT WYKRYWANIA", text);
        if (isFinal) {
            listener.onSpeechRecognized(text);
//            stopListeningCommand();
        }
    }

    /**
     * Wołana kiedy serwis rozpoznawania mowy zostanie stworzony i podłączony.
     * Dodajemy listener który słucha {@code onSpeechRecognized()}
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        speechService = SpeechService.from(service);
        speechService.addListener(this);
    }

    /**
     * Wołana kiedy serwis rozpoznawania mowy zostanie odłączony.
     */
    @Override
    public void onServiceDisconnected(ComponentName name) {
        speechService = null;
    }

    /**
     * Odbiera zdarzenia nasłuchiwania mowy.
     */
    private final VoiceRecorder.Callback voiceCallback = new VoiceRecorder.Callback() {
        @Override
        public void onVoiceStart() {
            if (speechService != null) {
                Log.i(TAG, " On voice start, start recognizing");
                speechService.startRecognizing(voiceRecorder.getSampleRate());
            }
        }

        /**
         * Zdarzenie wołane pomiędzy {@code onVoiceStart()} oraz {@code onVoiceEnd()}/
         * Rozpoznaje mowę.
         */
        @Override
        public void onVoice(byte[] data, int size) {
            if (speechService != null) {
                speechService.recognize(data, size);
            }
        }

        /**
         * Zdarzenie kończące nasluchiwanie mowy. Zatrzymuje rozpoznawanie mowy.
         */
        @Override
        public void onVoiceEnd() {
            if (speechService != null) {
                Log.i(TAG, " On voice end, finishing recognizing");
                speechService.finishRecognizing();
                stopVoiceRecorder();
                listener.onFinishSpeechRecognizing();
            }
        }
    };

    /**
     * Podłącza serwis do podanego kontekstu, czyli w naszym przypadku HomeActivity.
     */
    public void onStart() {
        context.bindService(new Intent(context, SpeechService.class), this, Context.BIND_AUTO_CREATE);
    }

    /**
     * Zatrzymuje nasłuchiwanie mowy oraz odpina serwis rozponawania mowy.
     */
    public void onStop() {
        stopVoiceRecorder();
        speechService.removeListener(this);
        context.unbindService(this);
    }

    /**
     * Wywołanie spowoduje nasłuchiwanie komend głosowych.
     */
    public void startListeningCommand() {
        startVoiceRecorder();
    }

    public void stopListeningCommand() {
        if (speechService != null) {
            speechService.finishRecognizing();
            stopVoiceRecorder();
        }
    }

    /**
     * Zaczyna nasłuchiwać głos odbierany z mikrofonu
     */
    private void startVoiceRecorder() {
        if (voiceRecorder != null) {
            voiceRecorder.stop();
        }
        voiceRecorder = new VoiceRecorder(voiceCallback);
        voiceRecorder.start();
    }

    /**
     * Kończy nasłuchiwać głos odbierany z mikrofonu
     */
    private void stopVoiceRecorder() {
        if (voiceRecorder != null) {
            voiceRecorder.stop();
            voiceRecorder = null;
        }
    }

}
