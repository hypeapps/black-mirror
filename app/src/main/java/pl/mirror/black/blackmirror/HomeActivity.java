package pl.mirror.black.blackmirror;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pl.mirror.black.blackmirror.speechrecognition.googlespeechapi.CommandSpeechRecognizer;
import pl.mirror.black.blackmirror.speechrecognition.sphinx.ActivationPhraseListener;
import pl.mirror.black.blackmirror.speechrecognition.sphinx.PocketSphinx;

public class HomeActivity extends AppCompatActivity implements ActivationPhraseListener, CommandSpeechRecognizer.Listener {

    private PocketSphinx pocketSphinx;

    private static final String TAG = "HomeActivity";

    private CommandSpeechRecognizer commandSpeechRecognizer;

    TextView activationPhraase;

    TextView finalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pocketSphinx = new PocketSphinx(this, this);
        commandSpeechRecognizer = new CommandSpeechRecognizer(this, this);
        finalText = (TextView) findViewById(R.id.textView);
        activationPhraase = (TextView) findViewById(R.id.activation_pharse);

    }

    @Override
    protected void onStart() {
        super.onStart();
        commandSpeechRecognizer.onStart();
    }

    @Override
    protected void onStop() {
        commandSpeechRecognizer.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pocketSphinx.onDestroy();
    }

    @Override
    public void onActivationPhraseRecognizerReady() {
        Log.i("ACTIVATION PHRASE ", " ACTIVATION READY ");
        activationPhraase.setVisibility(View.VISIBLE);
        pocketSphinx.startListeningToActivationPhrase();
    }

    @Override
    public void onActivationPhraseDetected() {
        Log.i("ACTIVATION PHRASE ", " DETECTED ");
        activationPhraase.setVisibility(View.INVISIBLE);
        commandSpeechRecognizer.startListeningCommand();
    }

    @Override
    public void onCommandRecognized(final String command) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finalText.setText(command);
            }
        });
    }

    @Override
    public void onFinishCommandRecognizing() {
        pocketSphinx.startListeningToActivationPhrase();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activationPhraase.setVisibility(View.VISIBLE);
            }
        });
    }

}
