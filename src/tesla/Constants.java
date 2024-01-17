package tesla;

public class Constants {
    public Constants(){

    }

    public static final int BOT_THRESHOLD_TO_MARK_TARGET_AS_COMPLETE = 7;


    public static final int FULL_MASK = 65535; // 1111 1111 1111 1111
    public static final int LOCATION_NULL_VAL = 61; // value used to signify that a location field is null



    // ------------ approximate flag indices from broadcast -------------------
    public static final int APPROX_OPP_FLAG_1_IDX = 0;
    public static final int APPROX_OPP_FLAG_2_IDX = 1;
    public static final int APPROX_OPP_FLAG_3_IDX = 2;
    public static final int[] APPROX_OPP_FLAG_INDICES = {APPROX_OPP_FLAG_1_IDX, APPROX_OPP_FLAG_2_IDX, APPROX_OPP_FLAG_3_IDX};

    // flag should be 111111000000
    public static final int APPROX_OPP_FLAG_X_MASK = 4032; // 111111000000
    public static final int APPROX_OPP_FLAG_X_SHIFT = 6;
    public static final int APPROX_OPP_FLAG_Y_MASK = 31; // 000000111111
    public static final int APPROX_OPP_FLAG_Y_SHIFT = 0;

    public static final int APPROX_OPP_FLAG_INFO_LAST_UPDATED_IDX = 3;
    public static final int APPROX_OPP_FLAG_INFO_LAST_UPDATED_MASK = 255; // 11111111
    public static final int APPROX_OPP_FLAG_INFO_LAST_UPDATED_SHIFT = 0;

    // ------------ known flag indices from broadcast -------------------


    public static final int KNOWN_OPP_FLAG_1_IDX = 4;
    public static final int KNOWN_OPP_FLAG_2_IDX = 5;
    public static final int KNOWN_OPP_FLAG_3_IDX = 6;
    public static final int[] KNOWN_OPP_FLAG_INDICES = {KNOWN_OPP_FLAG_1_IDX, KNOWN_OPP_FLAG_2_IDX, KNOWN_OPP_FLAG_3_IDX};

    public static final int KNOWN_OPP_FLAG_X_MASK = 4032; // 111111000000
    public static final int KNOWN_OPP_FLAG_X_SHIFT = 6;
    public static final int KNOWN_OPP_FLAG_Y_MASK = 31; // 00000011111
    public static final int KNOWN_OPP_FLAG_Y_SHIFT = 0;

    public static final int KNOWN_OPP_FLAG_CARRIED_MASK = 4096; // 1 000000 000000
    public static final int KNOWN_OPP_FLAG_CARRIED_SHIFT = 12;
    // ------------------------------------------------------------------
    // variables for shared target
    public static final int SHARED_OFFENSIVE_TARGET_IDX = 7;
    public static final int SHARED_OFFENSIVE_TARGET_X_MASK = 4032; // 111111000000
    public static final int SHARED_OFFENSIVE_TARGET_Y_MASK = 31; // 00000011111
    public static final int SHARED_OFFENSIVE_TARGET_X_SHIFT = 6;
    public static final int SHARED_OFFENSIVE_TARGET_Y_SHIFT = 0;

    // ----------------------------------------------------------
    // constants for home flags
    public static final int TRAPPERS_SPAWNED_IDX = 14;

    public static final int DEFAULT_FLAG_LOC_0_IDX = 8;
    public static final int DEFAULT_FLAG_LOC_1_IDX = 9;
    public static final int DEFAULT_FLAG_LOC_2_IDX = 10;
    public static final int[] DEFAULT_FLAG_LOCS_INDICES = {DEFAULT_FLAG_LOC_0_IDX, DEFAULT_FLAG_LOC_1_IDX, DEFAULT_FLAG_LOC_2_IDX};

    public static final int DEFAULT_FLAG_LOC_X_SHIFT = 0;
    public static final int DEFAULT_FLAG_LOC_Y_SHIFT = 6;
    public static final int DEFAULT_FLAG_LOC_X_MASK = 31; // 000000 111111
    public static final int DEFAULT_FLAG_LOC_Y_MASK = 4032; // 111111 000000
    public static final int DEFAULT_FLAG_TAKEN_MASK = 4096; // 1 000000 000000
    public static final int DEFAULT_FLAG_TAKEN_SHIFT = 12;


    // ----------------------------------------------------------
    // constants for scouting.
    public static final int DIST_TO_SPAWN_CENTERS_IDX = 15;
    public static final int DIST_TO_SPAWN_CENTER_MASKS[] = {0b000000000011111, 0b000001111100000, 0b111110000000000};
    public static final int DIST_TO_SPAWN_CENTER_SHIFTS[] = {0, 5, 10};
    public static final int SCOUT_EVEN_MASK = 0b1000000000000000;
    public static final int SCOUT_EVEN_SHIFT = 15;

    // -------------------------------
    ////////////////////////////////
    // ratio-related constants
    public static final int BOT_RATIO_INDEX = 11;

    // offensive ratio
    public static final int OFFENSIVE_RATIO_MASK = 15; // 1111
    public static final int OFFENSIVE_RATIO_SHIFT = 0;

    // mobile defender ratio
    public static final int MOBILE_DEFENDER_RATIO_MASK =  240;// 1111 0000
    public static final int MOBILE_DEFENDER_RATIO_SHIFT = 4;

    // stationary defender ratio
    public static final int STATIONARY_DEFENDER_RATIO_MASK = 3840; //1111 0000 0000
    public static final int STATIONARY_DEFENDER_RATIO_SHIFT = 8;

    // trapper ratio
    public static final int TRAPPER_RATIO_MASK = 61440; // 1111 0000 0000 0000
    public static final int TRAPPER_RATIO_SHIFT = 12;

    // BOT count-related constants
    // offensive soldier counts
    public static final int OFFENSIVE_COUNT_INDEX = 12;
    public static final int OFFENSIVE_COUNT_MASK = 0b111111; // 111111
    public static final int OFFENSIVE_COUNT_SHIFT = 0;

    // mobile defender counts
    public static final int MOBILE_DEFENDER_COUNT_INDEX = 12;
    public static final int MOBILE_DEFENDER_COUNT_MASK = 0b111111000000; // 111111 000000
    public static final int MOBILE_DEFENDER_COUNT_SHIFT = 6;

    // stationary defender counts
    public static final int STATIONARY_DEFENDER_COUNT_INDEX = 13;
    public static final int STATIONARY_DEFENDER_COUNT_MASK = 0b111111;
    public static final int STATIONARY_DEFENDER_COUNT_SHIFT = 0;

    public static final int TRAPPER_COUNT_INDEX = 13;
    public static final int TRAPPER_COUNT_MASK = 0b111111000000;
    public static final int TRAPPER_COUNT_SHIFT = 6;

    //--------------------------------------------------------------------------------------------
    public static final int SHARED_DEFENSIVE_TARGET_IDX = 17;
    public static final int SHARED_DEFENSIVE_TARGET_X_MASK = 0b111111000000;
    public static final int SHARED_DEFENSIVE_TARGET_X_SHIFT = 6;
    public static final int SHARED_DEFENSIVE_TARGET_Y_MASK = 0b000000111111;
    public static final int SHARED_DEFENSIVE_TARGET_Y_SHIFT = 0;
}
