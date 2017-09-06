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
import io.reactivex.disposables.CompositeDisposable;

public class PocketSphinx implements RecognitionListener {

    private static final String TAG = PocketSphinx.class.getSimpleName();

    private static final String ACTIVATION_KEYPHRASE = "ok mirror";

    /* Named searches allow to quickly reconfigure the decoder */
    private static final String WAKEUP_SEARCH = "wakeup";

    private static final String ACTION_SEARCH = "action";

    private final ActivationPhraseListener activationPhraseListener;

    private SpeechRecognizer recognizer;

    public PocketSphinx(Context context, ActivationPhraseListener activationPhraseListener) {
        this.activationPhraseListener = activationPhraseListener;
        runRecognizerSetup(context);
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech");
    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech");

        if (!recognizer.getSearchName().equals(WAKEUP_SEARCH)) {
            Log.i(TAG, "End of speech. Stop recognizer");
            recognizer.stop();
        }
    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }

        String text = hypothesis.getHypstr();
        Log.e("TAG", text);
        if (text.equals(ACTIVATION_KEYPHRASE)) {
            Log.i(TAG, "Activation keyphrase detected during a partial result");
            recognizer.stop();
        } else {
            Log.e(TAG, "On partial result: " + text);
        }
    }

    /**
     * This callback is called when we stop the recognizer.
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
            activationPhraseListener.onActivationPhraseDetected();
        } else {
            recognizer.stop();
            startListeningToActivationPhrase();
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
        activationPhraseListener.onTimeout();
    }

    public void startListeningToActivationPhrase() {
        Log.i(TAG, "Start listening for the \"ok mirror\" keyphrase");
        recognizer.startListening(WAKEUP_SEARCH);
    }

    public void onDestroy() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

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
                    activationPhraseListener.onActivationPhraseRecognizerReady();
                }
            }
        }.execute();
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                .getRecognizer();
        recognizer.addListener(this);

        // Custom recognizer
        recognizer.addKeyphraseSearch(WAKEUP_SEARCH, ACTIVATION_KEYPHRASE);
        recognizer.addNgramSearch(ACTION_SEARCH, new File(assetsDir, "predefined.lm.bin"));
    }

}
