package b_battleship.constants;

import java.util.HashMap;
import java.util.Map;

public enum EStringConstants {

    SHIP(" S ", EAnsiColor.GREY),
    WATER(" ~ ", EAnsiColor.BLUE),
    MISS(" O ", EAnsiColor.YELLOW),
    HIT(" X ", EAnsiColor.RED),
    ;

    private static Map<String, EStringConstants> printedValToType;

    private String printedVal;
    private EAnsiColor color;

    EStringConstants(String printedVal, EAnsiColor color) {
        this.printedVal = printedVal;
        this.color = color;
    }

    public String getPrintedVal() {
        return generateColoredText(this);
    }

    private static String generateColoredText(EStringConstants constant) {
        return constant.color.getCode() + constant.printedVal + EAnsiColor.RESET.getCode();
    }

    public static EStringConstants getTypeFromPrintedVal(String printedVal) {
        if (printedValToType == null) {
            printedValToType = new HashMap<>();
            for (EStringConstants type : EStringConstants.values()) {
                printedValToType.put(generateColoredText(type), type);
            }
        }
        return printedValToType.get(printedVal);
    }
}
