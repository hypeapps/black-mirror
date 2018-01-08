package pl.mirror.black.blackmirror.speechrecognition.sphinx;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class PocketSphinx implements RecognitionListener {

    private static final String TAG = PocketSphinx.class.getSimpleName();

    /* Słowo kluczowe */
    private static final String ACTIVATION_KEYPHRASE = "wakeup mirror";

    /* Nazwa akcji rozpoznawnia słowa kluczowego */
    private static final String WAKEUP_ACTION = "wakeup mirror";

    private final ActivationKeywordListener activationKeywordListener;

    private SpeechRecognizer recognizer;

    public PocketSphinx(Context context, ActivationKeywordListener activationKeywordListener) {
        this.activationKeywordListener = activationKeywordListener;
        runRecognizerSetup(context);
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech");
        activationKeywordListener.onActivationKeywordBeginningOfSpeech();
    }

    /**
     * Wołana podczas zakończenia mowy.
     */
    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech");
        activationKeywordListener.onActivationKeywordEndOfSpeech();
        if (!recognizer.getSearchName().equals(WAKEUP_ACTION)) {
            Log.i(TAG, "End of speech. Stop recognizer");
            recognizer.stop();
        }
    }

    /**
     * Podczas częściowego rezultatu dostajemy szybkie aktualizacje rezulatu rozpoznawania
     * mowy. Kiedy hipoteza pasuje do podanego słowa kluczowego {@code recognizer.stop()}
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }
        String text = hypothesis.getHypstr();
        Log.e(TAG, " PARTIAL RESULT " + text);
        if (text.equals(ACTIVATION_KEYPHRASE)) {
            Log.i(TAG, "Activation keyphrase detected during a partial result");
            recognizer.stop();
        } else {
            Log.e(TAG, "On partial result: " + text);
        }
    }

    /**
     * Wołana kiedy recognizer.stop()
     */
    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }

        String text = hypothesis.getHypstr();
        Log.i(TAG, "On result: " + text);

        if (ACTIVATION_KEYPHRASE.equals(text)) {
            recognizer.stop();
            activationKeywordListener.onActivationKeywordDetected();
        }
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, "On error", e);
    }

    @Override
    public void onTimeout() {
        Log.i(TAG, "Timeout!");
        recognizer.stop();
    }

    public void startListeningToActivationKeyword() {
        Log.i(TAG, "Start listening for the " + ACTIVATION_KEYPHRASE + "keyphrase");
        recognizer.startListening(WAKEUP_ACTION);
    }

    public void onDestroy() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

    /**
     * @param context potrzebny dla Assets
     * Konfiguruje obiekt rozpoznawania mowy. Odczytuje model z plików.
     */
    private void runRecognizerSetup(final Context context) {
        Log.d(TAG, "Recognizer setup");
        // Recognizer initialization is a time-consuming and it involves IO, so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(context);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    Log.e(TAG, "Failed to initialize recognizer: " + result);
                } else {
                    activationKeywordListener.onActivationKeywordRecognizerReady();
                }
            }
        }.execute();
    }

    /**
     * @param assetsDir
     * @throws IOException
     * Tworzy obiekt rozpoznawania mowy.
     */
    private void setupRecognizer(File assetsDir) throws IOException {
        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                .getRecognizer();
        recognizer.addListener(this);
//        File phoneticModel = new File(assetsDir, "en-phone.dmp");
//        recognizer.addAllphoneSearch("PHONE", phoneticModel);
//        recognizer.addKeywordSearch(WAKEUP_ACTION, new File(assetsDir, "cmudict-en-us.dict"));
        recognizer.addKeyphraseSearch(WAKEUP_ACTION, ACTIVATION_KEYPHRASE);
    }

}
