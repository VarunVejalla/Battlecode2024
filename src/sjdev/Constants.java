package sjdev;

import battlecode.common.GameConstants;

public class Constants {
    public Constants(){

    }

    // Round number constants
    public static final int NEW_FLAG_LOC_DECIDED_ROUND = 70;
    public static final int SETUP_ROUNDS = GameConstants.SETUP_ROUNDS;

    // Threshold constants
    public static final int BOT_THRESHOLD_TO_MARK_TARGET_AS_COMPLETE = 7;

    // Attack heuristic constants
    public static final double OFFENSE_ATTACK_SAFETY_FACTOR = 1.0;
    public static final double STATIONARY_DEFENSE_ATTACK_SAFETY_FACTOR = 2.0;
    public static final double MOBILE_DEFENSE_ATTACK_SAFETY_FACTOR = 0.5;

    // Comms constants
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
    public static final int APPROX_OPP_FLAG_Y_MASK = 63; // 000000111111
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
    public static final int KNOWN_OPP_FLAG_Y_MASK = 63; // 00000011111
    public static final int KNOWN_OPP_FLAG_Y_SHIFT = 0;

    public static final int KNOWN_OPP_FLAG_CARRIED_MASK = 4096; // 1 000000 000000
    public static final int KNOWN_OPP_FLAG_CARRIED_SHIFT = 12;
    // ------------------------------------------------------------------
    // variables for shared target
    public static final int SHARED_OFFENSIVE_TARGET_IDX = 7;
    public static final int SHARED_OFFENSIVE_TARGET_X_MASK = 4032; // 111111000000
    public static final int SHARED_OFFENSIVE_TARGET_Y_MASK = 63; // 00000011111
    public static final int SHARED_OFFENSIVE_TARGET_X_SHIFT = 6;
    public static final int SHARED_OFFENSIVE_TARGET_Y_SHIFT = 0;

    // ----------------------------------------------------------
    // constants for home flags
    public static final int DEFAULT_FLAG_LOC_0_IDX = 8;
    public static final int DEFAULT_FLAG_LOC_1_IDX = 9;
    public static final int DEFAULT_FLAG_LOC_2_IDX = 10;
    public static final int[] DEFAULT_FLAG_LOCS_INDICES = {DEFAULT_FLAG_LOC_0_IDX, DEFAULT_FLAG_LOC_1_IDX, DEFAULT_FLAG_LOC_2_IDX};

    public static final int DEFAULT_FLAG_LOC_X_SHIFT = 0;
    public static final int DEFAULT_FLAG_LOC_Y_SHIFT = 6;
    public static final int DEFAULT_FLAG_LOC_X_MASK = 63; // 000000 111111
    public static final int DEFAULT_FLAG_LOC_Y_MASK = 4032; // 111111 000000
    public static final int DEFAULT_FLAG_TAKEN_MASK = 4096; // 1 000000 000000
    public static final int DEFAULT_FLAG_TAKEN_SHIFT = 12;
    public static final int FLAG_PLACED_NEW_HOME_MASK = 8192; // 10 000000 000000
    public static final int FLAG_PLACED_NEW_HOME_SHIFT = 13;

    public static final int NEW_HOME_FLAG_CENTER_IDX = 16;
    public static final int NEW_HOME_FLAG_CENTER_X_SHIFT = 0;
    public static final int NEW_HOME_FLAG_CENTER_Y_SHIFT = 6;
    public static final int NEW_HOME_FLAG_CENTER_X_MASK = 63; // 000000 111111
    public static final int NEW_HOME_FLAG_CENTER_Y_MASK = 4032; // 111111 000000
    public static final int NEW_HOME_FLAG_CENTER_SET_BIT_SHIFT = 12;
    public static final int NEW_HOME_FLAG_CENTER_SET_BIT_MASK = 0b0001000000000000; // 000000 111111

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
    // -------------------------------------------------------------------------------------
    // the maximum number of rounds you should spend trying to get a crumb, before giving up
    public static final int MAX_ROUNDS_TO_CHASE_CRUMB = 15;

    // ----------------------------------------------------------
    // constants for flag defender count.
    public static final int NUM_DEFENDERS_FOR_FLAG_IDX = 23;
    public static final int NUM_DEFENDERS_FOR_FLAG_MASKS[] = {0b000000000011111, 0b000001111100000, 0b111110000000000};
    public static final int NUM_DEFENDERS_FOR_FLAG_SHIFTS[] = {0, 5, 10};

    // ------------ spotted ally flag indices -------------------
    public static final int TAKEN_ALLY_FLAG_1_IDX = 30;
    public static final int TAKEN_ALLY_FLAG_2_IDX = 31;
    public static final int TAKEN_ALLY_FLAG_3_IDX = 32;
    public static final int[] TAKEN_ALLY_FLAG_INDICES = {TAKEN_ALLY_FLAG_1_IDX, TAKEN_ALLY_FLAG_2_IDX, TAKEN_ALLY_FLAG_3_IDX};

    public static final int TAKEN_ALLY_FLAG_X_MASK = 4032; // 111111000000
    public static final int TAKEN_ALLY_FLAG_X_SHIFT = 6;
    public static final int TAKEN_ALLY_FLAG_Y_MASK = 63; // 00000011111
    public static final int TAKEN_ALLY_FLAG_Y_SHIFT = 0;

    // ------------------- trapper shit ------------------------
    public static final int TRAP_COUNT_IDX = 33;
    public static final int TRAP_COUNT_MASKS[] = {0b000000000011111, 0b000001111100000, 0b111110000000000};
    public static final int TRAP_COUNT_SHIFTS[] = {0, 5, 10};

    /////////////////// constants for default opp flag locs ////////////////
    // constants for default opp flag loc
    public static final int OPP_FLAG_1_ID_IDX = 24;
    public static final int OPP_FLAG_2_ID_IDX = 26;
    public static final int OPP_FLAG_3_ID_IDX = 28;

    public static final int[] OPP_FLAG_ID_INDICES = {
            OPP_FLAG_1_ID_IDX,
            OPP_FLAG_2_ID_IDX,
            OPP_FLAG_3_ID_IDX};


    public static final int MASK_FOR_OPP_FLAG_ID = 0b1111111111111111;
    public static final int SHIFT_FOR_OPP_FLAG_ID = 0;
    public static final int NULL_FLAG_ID_VAL = 3700; // max the id can be is 60x60=3600

    public static final int DEFAULT_OPP_FLAG_1_INFO_IDX = 25;
    public static final int DEFAULT_OPP_FLAG_2_INFO_IDX = 27;
    public static final int DEFAULT_OPP_FLAG_3_INFO_IDX = 29;
    public static final int[] DEFAULT_OPP_FLAG_INFO_INDICES = {
            DEFAULT_OPP_FLAG_1_INFO_IDX,
            DEFAULT_OPP_FLAG_2_INFO_IDX,
            DEFAULT_OPP_FLAG_3_INFO_IDX
    };


    public static final int DEFAULT_OPP_FLAG_X_SHIFT = 6;
    public static final int DEFAULT_OPP_FLAG_X_MASK = 0b111111000000;
    public static final int DEFAULT_OPP_FLAG_Y_SHIFT = 0;
    public static final int DEFAULT_OPP_FLAG_Y_MASK = 0b111111;
    public static final int OPP_FLAG_CAPTURED_MASK = 0b1000000000000;
    public static final int OPP_FLAG_CAPTURED_SHIFT = 12;
}
