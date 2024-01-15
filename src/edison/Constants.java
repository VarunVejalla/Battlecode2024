package edison;

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
    public static final int APPROX_OPP_FLAG_Y_MASK = 31; // 00000011111
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

    public static final int TRAPPERS_SPAWNED_IDX = 14;
    public static final int FLAGS_NEED_HELP_IDX = 15;

    public static final int SPAWN_0_IDX = 16;
    public static final int SPAWN_1_IDX = 17;
    public static final int SPAWN_2_IDX = 18;



}
