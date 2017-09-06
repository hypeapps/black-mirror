package pl.mirror.black.blackmirror.speechrecognition.sphinx;

public interface ActivationPhraseListener {

    void onActivationPhraseRecognizerReady();

    void onActivationPhraseDetected();

    void onTimeout();

}
