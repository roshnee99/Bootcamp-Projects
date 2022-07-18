package c_restaurant.bean;

import java.util.HashMap;
import java.util.Map;

public enum ETableType {

    SMALL(2),
    MEDIUM(4),
    LARGE(6);

    private int maxMembers;

    private static Map<Integer, ETableType> numMembersToTableSizeCache = new HashMap<>();

    ETableType(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    /**
     * ex. table that can seat 4 people should be used only for 3-4 people (with this implementation)
     * So, if a party of 3 come in, they should be seated at a MEDIUM table, not a LARGE one
     */
    public static ETableType getTableTypeForParty(int numMembers) {
        if (numMembersToTableSizeCache.containsKey(numMembers)) {
            return numMembersToTableSizeCache.get(numMembers);
        }
        // for each table, check if it can seat the party. If it can, then add it to the cache
        for (ETableType tableType : ETableType.values()) {
            if (tableType.canSeatParty(numMembers)) {
                numMembersToTableSizeCache.put(numMembers, tableType);
            }
        }
        return numMembersToTableSizeCache.get(numMembers);
    }

    private boolean canSeatParty(int numMembers) {
        return numMembers == this.maxMembers || numMembers == this.maxMembers - 1;
    }
}
