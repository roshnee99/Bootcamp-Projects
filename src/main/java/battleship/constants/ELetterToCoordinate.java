package battleship.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum ELetterToCoordinate {

    A("a", 0),
    B("b", 1),
    C("c", 2),
    D("d", 3),
    E("e", 4),
    F("f", 5),
    G("g", 6),
    H("h", 7),
    I("i", 8),
    J("j", 9),
    UNKNOWN("", -1)
    ;

    private static Map<String, Integer> letterToCoordinate;
    private static Map<Integer, String> coordinateToLetter;

    private String letter;
    private int column;

    ELetterToCoordinate(String letter, int column) {
        this.letter = letter;
        this.column = column;
    }

    public String getLetter() {
        return letter;
    }

    public static int getColumnFromLetter(String letter) {
        if (letterToCoordinate == null) {
            letterToCoordinate = new HashMap<>();
            Arrays.stream(ELetterToCoordinate.values()).forEach(x -> letterToCoordinate.put(x.letter, x.column));
        }
        return letterToCoordinate.get(letter);
    }

    public static String getLetterFromColumn(int column) {
        if (coordinateToLetter == null) {
            coordinateToLetter = new HashMap<>();
            Arrays.stream(ELetterToCoordinate.values()).forEach(x -> coordinateToLetter.put(x.column, x.letter));
        }
        return coordinateToLetter.get(column);
    }

    public static List<ELetterToCoordinate> getValues() {
        return Arrays.stream(ELetterToCoordinate.values()).filter(x -> x != ELetterToCoordinate.UNKNOWN).collect(Collectors.toList());
    }

}
