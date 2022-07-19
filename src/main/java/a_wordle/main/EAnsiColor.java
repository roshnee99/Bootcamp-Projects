package a_wordle.main;

public enum EAnsiColor {

    BLUE("\u001B[34m"),
    GREEN("\u001B[32m"),
    RED("\u001B[31m"),
    YELLOW("\u001B[33m"),
    CYAN("\u001b[36;1m"),
    GREY("\u001b[37m"),
    MAGENTA("\u001B[35m"),
    WHITE("\u001B[37m"),
    RESET("\u001B[0m");

    private final String code;

    private EAnsiColor(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static String stringInColor(String toPrint, EAnsiColor color) {
        return color.getCode() + toPrint + RESET.getCode();
    }

    public static String stringInColor(char toPrint, EAnsiColor color) {
        return color.getCode() + toPrint + RESET.getCode();
    }

}
