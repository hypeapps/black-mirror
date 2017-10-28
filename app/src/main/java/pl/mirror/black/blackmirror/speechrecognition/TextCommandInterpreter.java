package pl.mirror.black.blackmirror.speechrecognition;


import java.util.ArrayList;
import java.util.Arrays;

public class TextCommandInterpreter {

    public interface Listener {
        void onWeatherCommandRecognized(String location);

        void onTimeCommandRecognized(String location);

        void onFailureCommandRecognizing();

        void onHideWeatherWidget();
    }

    private TextCommandInterpreter.Listener listener;

    private final ArrayList<String> weatherVocabulary = new ArrayList<>(
            Arrays.asList("pogoda", "pogodę", "pogode", "pogodą", "pogody", "pogód"));

    private final ArrayList<String> cityVocabulary = new ArrayList<>(
            Arrays.asList("miasto", "miast", "miasta", "miasteczko", "miastom", "miastu"));

    private final ArrayList<String> countryVocabulary = new ArrayList<>(
            Arrays.asList("kraj", "kraju", "krajowi", "krajom"));

    private final ArrayList<String> timeVocabulary = new ArrayList<>(
            Arrays.asList("czas", "strefy", "czasu", "czasom", "czasowej", "strefę", "strefe"));

    public TextCommandInterpreter(TextCommandInterpreter.Listener listener) {
        this.listener = listener;
    }

    public void interpret(String result) {
        String interpretingCommand = result.toLowerCase().trim();
        if (containsOnlyOneWord(interpretingCommand)) {
            listener.onFailureCommandRecognizing();
            return;
        } else if (tryRecognizeWeatherCommand(interpretingCommand)) {
            listener.onWeatherCommandRecognized(getLocationName(result));
        } else if (tryRecognizeTimeCommand(interpretingCommand)) {
            listener.onTimeCommandRecognized(getLocationName(result));
        } else {
            listener.onFailureCommandRecognizing();
        }
    }

    private Boolean tryRecognizeWeatherCommand(String candidate) {
        return containsWordFromVocabulary(candidate, weatherVocabulary)
                && (containsWordFromVocabulary(candidate, cityVocabulary) || containsWordFromVocabulary(candidate, countryVocabulary));
    }

    private Boolean tryRecognizeTimeCommand(String candidate) {
        return containsWordFromVocabulary(candidate, timeVocabulary)
                && (containsWordFromVocabulary(candidate, cityVocabulary) || containsWordFromVocabulary(candidate, countryVocabulary));
    }

    private Boolean containsWordFromVocabulary(String candidate, ArrayList<String> vocabulary) {
        for (String item : vocabulary) {
            if (candidate.contains(item)) {
                return true;
            }
        }
        return false;
    }

    private Boolean containsOnlyOneWord(String candidate) {
        return candidate.split(" ").length == 1;
    }

    private String getLocationName(String command) {
        return command.substring(command.lastIndexOf(" ") + 1);
    }

}
