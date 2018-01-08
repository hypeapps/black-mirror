package pl.mirror.black.blackmirror.speechrecognition;

import java.util.ArrayList;
import java.util.Arrays;

public class TextCommandInterpreter {

    public interface Listener {
        void onShowWeatherCommandRecognized(String location);

        void onHideWeatherCommandRecognized();

        void onShowTimeCommandRecognized(String location);

        void onHideTimeCommandRecognized();

        void onFailureCommandRecognizing();

        void onShowCalendarCommandRecognized();

        void onHideCalendarCommandRecognized();

        void onNextMonthCommandRecognized();

        void onPreviousMonthRecognized();

        void onShowNewsCommandRecognized();

        void onHideNewsCommandRecognized();

        void onShowTvnNewsCommandRecognized();

        void onShowPolsatNewsCommandRecognized();
    }

    private TextCommandInterpreter.Listener listener;

    private final ArrayList<String> weatherVocabulary = new ArrayList<>(
            Arrays.asList("pogoda", "pogodę", "pogode", "pogodą", "pogody", "pogód", "pogodowy"));

    private final ArrayList<String> cityVocabulary = new ArrayList<>(
            Arrays.asList("miasto", "miast", "miasta", "miasteczko", "miastom", "miastu"));

    private final ArrayList<String> countryVocabulary = new ArrayList<>(
            Arrays.asList("kraj", "kraju", "krajowi", "krajom"));

    private final ArrayList<String> timeVocabulary = new ArrayList<>(
            Arrays.asList("czas", "strefy", "czasu", "czasom", "czasowej", "strefę",
                    "strefe", "godzinę", "godziny", "godziną", "godzina"));

    private final ArrayList<String> newsVocabulary = new ArrayList<>(
            Arrays.asList("news", "newsy", "newsa", "wiadomość", "wiadomości",
                    "wiadomością", "wiadomosciami"));

    private final ArrayList<String> showVocabulary = new ArrayList<>(
            Arrays.asList("pokaż", "pokazuj", "wyświetl", "wyświetlić"));

    private final ArrayList<String> hideVocabulary = new ArrayList<>(
            Arrays.asList("ukryj", "ukryć", "ukrywanie", "ukrywaniu", "ukrywać", "schowaj", "chowaj",
                    "chowanie", "chować",
                    "zamknij", "zamknąć"));

    private final ArrayList<String> calendarVocabulary = new ArrayList<>(
            Arrays.asList("kalendarz", "kalendarzy", "kalendarzom", "kalendarza", "kalendarzy", "kalendarzem",
                    "kalendarzowi"));

    private final ArrayList<String> tvnNewsVocabulary = new ArrayList<>(
            Arrays.asList("tvnu", "tvn", "tev", "tv", "tnv"));

    private final ArrayList<String> polsatNewsVocabulary = new ArrayList<>(
            Arrays.asList("polsat", "polsatu", "polsatowi"));

    private final ArrayList<String> nextVocabulary = new ArrayList<>(
            Arrays.asList("następny", "następna", "następnym", "następnego", "kolejny", "kolejnego", "kolejnym"));

    private final ArrayList<String> previousVocabulary = new ArrayList<>(
            Arrays.asList("poprzedni", "poprzednim", "poprzedniemu", "poprzedniego"));

    private final ArrayList<String> monthVocabulary = new ArrayList<>(
            Arrays.asList("miesiąc", "miesięcy", "miesiącowi", "miesiącu", "miesiąca", "miesiące"));

    public TextCommandInterpreter(TextCommandInterpreter.Listener listener) {
        this.listener = listener;
    }

    public void interpret(String result) {
        String interpretingCommand = result.toLowerCase().trim();
        if (containsOnlyOneWord(interpretingCommand)) {
            listener.onFailureCommandRecognizing();
            return;
        } else if (tryRecognizeShowWeatherCommand(interpretingCommand)) {
            listener.onShowWeatherCommandRecognized(getLocationName(interpretingCommand));
        } else if (tryRecognizeHideWeatherCommand(interpretingCommand)) {
            listener.onHideWeatherCommandRecognized();
        } else if (tryRecognizeShowTimeCommand(interpretingCommand)) {
            listener.onShowTimeCommandRecognized(getLocationName(interpretingCommand));
        } else if (tryRecognizeHideTimeCommand(interpretingCommand)) {
            listener.onHideTimeCommandRecognized();
        } else if (tryRecognizeHideNewsCommand(interpretingCommand)) {
            listener.onHideNewsCommandRecognized();
        } else if (tryRecognizeShowPolsatNewsCommand(interpretingCommand)) {
            listener.onShowPolsatNewsCommandRecognized();
        } else if (tryRecognizeShowTvnNewsCommand(interpretingCommand)) {
            listener.onShowTvnNewsCommandRecognized();
        } else if (tryRecognizeShowNewsCommand(interpretingCommand)) {
            listener.onShowNewsCommandRecognized();
        } else if (tryRecognizeShowCalendarCommand(interpretingCommand)) {
            listener.onShowCalendarCommandRecognized();
        } else if (tryRecognizeHideCalendarCommand(interpretingCommand)) {
            listener.onHideCalendarCommandRecognized();
        } else if (tryRecognizeNextMonthCommand(interpretingCommand)) {
            listener.onNextMonthCommandRecognized();
        } else if (tryRecognizePreviousMonthCommand(interpretingCommand)) {
            listener.onPreviousMonthRecognized();
        } else {
            listener.onFailureCommandRecognizing();
        }
    }

    private boolean tryRecognizePreviousMonthCommand(String candidate) {
        return containsWordFromVocabulary(candidate, previousVocabulary)
                && containsWordFromVocabulary(candidate, monthVocabulary);
    }

    private boolean tryRecognizeNextMonthCommand(String candidate) {
        return containsWordFromVocabulary(candidate, nextVocabulary)
                && containsWordFromVocabulary(candidate, monthVocabulary);
    }

    private boolean tryRecognizeShowCalendarCommand(String candidate) {
        return containsWordFromVocabulary(candidate, showVocabulary)
                && (containsWordFromVocabulary(candidate, calendarVocabulary));
    }

    private boolean tryRecognizeHideCalendarCommand(String candidate) {
        return containsWordFromVocabulary(candidate, hideVocabulary)
                && (containsWordFromVocabulary(candidate, calendarVocabulary));
    }

    private Boolean tryRecognizeShowWeatherCommand(String candidate) {
        return containsWordFromVocabulary(candidate, weatherVocabulary)
                && (containsWordFromVocabulary(candidate, cityVocabulary)
                || containsWordFromVocabulary(candidate, countryVocabulary));
    }

    private Boolean tryRecognizeHideWeatherCommand(String candidate) {
        return containsWordFromVocabulary(candidate, weatherVocabulary)
                && containsWordFromVocabulary(candidate, hideVocabulary);
    }

    private Boolean tryRecognizeShowTimeCommand(String candidate) {
        return containsWordFromVocabulary(candidate, timeVocabulary)
                && (containsWordFromVocabulary(candidate, cityVocabulary) ||
                containsWordFromVocabulary(candidate, countryVocabulary));
    }

    private Boolean tryRecognizeHideTimeCommand(String candidate) {
        return containsWordFromVocabulary(candidate, timeVocabulary)
                && containsWordFromVocabulary(candidate, hideVocabulary);
    }

    private Boolean tryRecognizeShowNewsCommand(String candidate) {
        return containsWordFromVocabulary(candidate, newsVocabulary)
                && (containsWordFromVocabulary(candidate, showVocabulary));
    }

    private Boolean tryRecognizeHideNewsCommand(String candidate) {
        return containsWordFromVocabulary(candidate, newsVocabulary)
                && (containsWordFromVocabulary(candidate, hideVocabulary));
    }

    private Boolean tryRecognizeShowTvnNewsCommand(String candidate) {
        return containsWordFromVocabulary(candidate, tvnNewsVocabulary)
                && (containsWordFromVocabulary(candidate, showVocabulary));
    }

    private Boolean tryRecognizeShowPolsatNewsCommand(String candidate) {
        return containsWordFromVocabulary(candidate, polsatNewsVocabulary)
                && (containsWordFromVocabulary(candidate, showVocabulary));
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
