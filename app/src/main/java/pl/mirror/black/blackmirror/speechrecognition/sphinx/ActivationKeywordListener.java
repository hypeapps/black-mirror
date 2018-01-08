package pl.mirror.black.blackmirror.speechrecognition.sphinx;

public interface ActivationKeywordListener {

    void onActivationKeywordRecognizerReady();

    void onActivationKeywordDetected();

    void onActivationKeywordBeginningOfSpeech();

    void onActivationKeywordEndOfSpeech();

}
