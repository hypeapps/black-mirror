package pl.mirror.black.blackmirror;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import pl.mirror.black.blackmirror.speechrecognition.sphinx.ActivationPhraseListener;
import pl.mirror.black.blackmirror.speechrecognition.sphinx.PocketSphinx;

public class HomeActivity extends AppCompatActivity implements ActivationPhraseListener {

    private PocketSphinx pocketSphinx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pocketSphinx = new PocketSphinx(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        pocketSphinx.onDestroy();
    }

    @Override
    public void onActivationPhraseRecognizerReady() {
        Log.i("ACTIVATION PHRASE ", " ACTIVATION READY ");
        pocketSphinx.startListeningToActivationPhrase();
    }

    @Override
    public void onActivationPhraseDetected() {
        Log.i("ACTIVATION PHRASE ", " DETECTED ");
        pocketSphinx.startListeningToActivationPhrase();
    }

    @Override
    public void onTimeout() {
        Log.i("ACTIVATION PHRASE ", " TIMEOUT ");
        pocketSphinx.startListeningToActivationPhrase();
    }

}
