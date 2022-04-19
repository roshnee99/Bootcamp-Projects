package battleship.constants;

import java.util.HashMap;
import java.util.Map;

public enum EOrientation {

    HORIZONTAL("H"),
    VERTICAL("V"),
    UNKNOWN(null)
    ;

    private static Map<String, EOrientation> keyToOrientationMap;
    private String orientationKey;

    EOrientation(String orientationKey) {
        this.orientationKey = orientationKey;
    }

    public String getOrientationKey() {
        return orientationKey;
    }

    public static EOrientation getOrientationFromKey(String key) {
        if (keyToOrientationMap == null) {
            keyToOrientationMap = new HashMap<>();
            for (EOrientation orientation : EOrientation.values()) {
                keyToOrientationMap.put(orientation.getOrientationKey(), orientation);
            }
        }
        return keyToOrientationMap.getOrDefault(key, UNKNOWN);
    }
}

