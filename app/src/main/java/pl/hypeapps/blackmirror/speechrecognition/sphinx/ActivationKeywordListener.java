package pl.hypeapps.blackmirror.speechrecognition.sphinx;

public interface ActivationKeywordListener {

    void onActivationKeywordRecognizerReady();

    void onActivationKeywordDetected();

    void onActivationKeywordBeginningOfSpeech();

    void onActivationKeywordEndOfSpeech();

}
