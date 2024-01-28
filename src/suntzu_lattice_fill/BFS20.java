package suntzu_lattice_fill;
import battlecode.common.*;

public class BFS20 extends BFS {
BFS20(RobotController rc, Robot robot){
super(rc, robot);
}
static MapInfo info;

static MapLocation l54;
static double v54;
static Direction d54;
static int p54;


static MapLocation l44;
static double v44;
static Direction d44;
static int p44;


static MapLocation l64;
static double v64;
static Direction d64;
static int p64;


static MapLocation l53;
static double v53;
static Direction d53;
static int p53;


static MapLocation l55;
static double v55;
static Direction d55;
static int p55;


static MapLocation l43;
static double v43;
static Direction d43;
static int p43;


static MapLocation l45;
static double v45;
static Direction d45;
static int p45;


static MapLocation l63;
static double v63;
static Direction d63;
static int p63;


static MapLocation l65;
static double v65;
static Direction d65;
static int p65;


static MapLocation l34;
static double v34;
static Direction d34;
static int p34;


static MapLocation l74;
static double v74;
static Direction d74;
static int p74;


static MapLocation l52;
static double v52;
static Direction d52;
static int p52;


static MapLocation l56;
static double v56;
static Direction d56;
static int p56;


static MapLocation l33;
static double v33;
static Direction d33;
static int p33;


static MapLocation l35;
static double v35;
static Direction d35;
static int p35;


static MapLocation l73;
static double v73;
static Direction d73;
static int p73;


static MapLocation l75;
static double v75;
static Direction d75;
static int p75;


static MapLocation l42;
static double v42;
static Direction d42;
static int p42;


static MapLocation l46;
static double v46;
static Direction d46;
static int p46;


static MapLocation l62;
static double v62;
static Direction d62;
static int p62;


static MapLocation l66;
static double v66;
static Direction d66;
static int p66;


static MapLocation l32;
static double v32;
static Direction d32;
static int p32;


static MapLocation l36;
static double v36;
static Direction d36;
static int p36;


static MapLocation l72;
static double v72;
static Direction d72;
static int p72;


static MapLocation l76;
static double v76;
static Direction d76;
static int p76;


static MapLocation l24;
static double v24;
static Direction d24;
static int p24;


static MapLocation l84;
static double v84;
static Direction d84;
static int p84;


static MapLocation l51;
static double v51;
static Direction d51;
static int p51;


static MapLocation l57;
static double v57;
static Direction d57;
static int p57;


static MapLocation l23;
static double v23;
static Direction d23;
static int p23;


static MapLocation l25;
static double v25;
static Direction d25;
static int p25;


static MapLocation l83;
static double v83;
static Direction d83;
static int p83;


static MapLocation l85;
static double v85;
static Direction d85;
static int p85;


static MapLocation l41;
static double v41;
static Direction d41;
static int p41;


static MapLocation l47;
static double v47;
static Direction d47;
static int p47;


static MapLocation l61;
static double v61;
static Direction d61;
static int p61;


static MapLocation l67;
static double v67;
static Direction d67;
static int p67;


static MapLocation l22;
static double v22;
static Direction d22;
static int p22;


static MapLocation l26;
static double v26;
static Direction d26;
static int p26;


static MapLocation l82;
static double v82;
static Direction d82;
static int p82;


static MapLocation l86;
static double v86;
static Direction d86;
static int p86;


static MapLocation l31;
static double v31;
static Direction d31;
static int p31;


static MapLocation l37;
static double v37;
static Direction d37;
static int p37;


static MapLocation l71;
static double v71;
static Direction d71;
static int p71;


static MapLocation l77;
static double v77;
static Direction d77;
static int p77;


static MapLocation l21;
static double v21;
static Direction d21;
static int p21;


static MapLocation l27;
static double v27;
static Direction d27;
static int p27;


static MapLocation l81;
static double v81;
static Direction d81;
static int p81;


static MapLocation l87;
static double v87;
static Direction d87;
static int p87;


static MapLocation l14;
static double v14;
static Direction d14;
static int p14;


static MapLocation l94;
static double v94;
static Direction d94;
static int p94;


static MapLocation l50;
static double v50;
static Direction d50;
static int p50;


static MapLocation l58;
static double v58;
static Direction d58;
static int p58;


static MapLocation l13;
static double v13;
static Direction d13;
static int p13;


static MapLocation l15;
static double v15;
static Direction d15;
static int p15;


static MapLocation l93;
static double v93;
static Direction d93;
static int p93;


static MapLocation l95;
static double v95;
static Direction d95;
static int p95;


static MapLocation l40;
static double v40;
static Direction d40;
static int p40;


static MapLocation l48;
static double v48;
static Direction d48;
static int p48;


static MapLocation l60;
static double v60;
static Direction d60;
static int p60;


static MapLocation l68;
static double v68;
static Direction d68;
static int p68;


static MapLocation l12;
static double v12;
static Direction d12;
static int p12;


static MapLocation l16;
static double v16;
static Direction d16;
static int p16;


static MapLocation l92;
static double v92;
static Direction d92;
static int p92;


static MapLocation l96;
static double v96;
static Direction d96;
static int p96;


static MapLocation l30;
static double v30;
static Direction d30;
static int p30;


static MapLocation l38;
static double v38;
static Direction d38;
static int p38;


static MapLocation l70;
static double v70;
static Direction d70;
static int p70;


static MapLocation l78;
static double v78;
static Direction d78;
static int p78;


public void resetVars(int[][] heuristicMap) throws GameActionException{
l54 = robot.myLoc;
v54 = 0;

l44 = l54.add(Direction.WEST);
v44 = 100000000;
d44 = null;

p44 = heuristicMap[4][4];

l64 = l54.add(Direction.EAST);
v64 = 100000000;
d64 = null;

p64 = heuristicMap[6][4];

l53 = l54.add(Direction.SOUTH);
v53 = 100000000;
d53 = null;

p53 = heuristicMap[5][3];

l55 = l54.add(Direction.NORTH);
v55 = 100000000;
d55 = null;

p55 = heuristicMap[5][5];

l43 = l44.add(Direction.SOUTH);
v43 = 100000000;
d43 = null;

p43 = heuristicMap[4][3];

l45 = l55.add(Direction.WEST);
v45 = 100000000;
d45 = null;

p45 = heuristicMap[4][5];

l63 = l53.add(Direction.EAST);
v63 = 100000000;
d63 = null;

p63 = heuristicMap[6][3];

l65 = l55.add(Direction.EAST);
v65 = 100000000;
d65 = null;

p65 = heuristicMap[6][5];

l34 = l44.add(Direction.WEST);
v34 = 100000000;
d34 = null;

p34 = heuristicMap[3][4];

l74 = l64.add(Direction.EAST);
v74 = 100000000;
d74 = null;

p74 = heuristicMap[7][4];

l52 = l53.add(Direction.SOUTH);
v52 = 100000000;
d52 = null;

p52 = heuristicMap[5][2];

l56 = l55.add(Direction.NORTH);
v56 = 100000000;
d56 = null;

p56 = heuristicMap[5][6];

l33 = l34.add(Direction.SOUTH);
v33 = 100000000;
d33 = null;

p33 = heuristicMap[3][3];

l35 = l45.add(Direction.WEST);
v35 = 100000000;
d35 = null;

p35 = heuristicMap[3][5];

l73 = l63.add(Direction.EAST);
v73 = 100000000;
d73 = null;

p73 = heuristicMap[7][3];

l75 = l65.add(Direction.EAST);
v75 = 100000000;
d75 = null;

p75 = heuristicMap[7][5];

l42 = l43.add(Direction.SOUTH);
v42 = 100000000;
d42 = null;

p42 = heuristicMap[4][2];

l46 = l56.add(Direction.WEST);
v46 = 100000000;
d46 = null;

p46 = heuristicMap[4][6];

l62 = l52.add(Direction.EAST);
v62 = 100000000;
d62 = null;

p62 = heuristicMap[6][2];

l66 = l56.add(Direction.EAST);
v66 = 100000000;
d66 = null;

p66 = heuristicMap[6][6];

l32 = l33.add(Direction.SOUTH);
v32 = 100000000;
d32 = null;

p32 = heuristicMap[3][2];

l36 = l46.add(Direction.WEST);
v36 = 100000000;
d36 = null;

p36 = heuristicMap[3][6];

l72 = l62.add(Direction.EAST);
v72 = 100000000;
d72 = null;

p72 = heuristicMap[7][2];

l76 = l66.add(Direction.EAST);
v76 = 100000000;
d76 = null;

p76 = heuristicMap[7][6];

l24 = l34.add(Direction.WEST);
v24 = 100000000;
d24 = null;

p24 = heuristicMap[2][4];

l84 = l74.add(Direction.EAST);
v84 = 100000000;
d84 = null;

p84 = heuristicMap[8][4];

l51 = l52.add(Direction.SOUTH);
v51 = 100000000;
d51 = null;

p51 = heuristicMap[5][1];

l57 = l56.add(Direction.NORTH);
v57 = 100000000;
d57 = null;

p57 = heuristicMap[5][7];

l23 = l24.add(Direction.SOUTH);
v23 = 100000000;
d23 = null;

p23 = heuristicMap[2][3];

l25 = l35.add(Direction.WEST);
v25 = 100000000;
d25 = null;

p25 = heuristicMap[2][5];

l83 = l73.add(Direction.EAST);
v83 = 100000000;
d83 = null;

p83 = heuristicMap[8][3];

l85 = l75.add(Direction.EAST);
v85 = 100000000;
d85 = null;

p85 = heuristicMap[8][5];

l41 = l42.add(Direction.SOUTH);
v41 = 100000000;
d41 = null;

p41 = heuristicMap[4][1];

l47 = l57.add(Direction.WEST);
v47 = 100000000;
d47 = null;

p47 = heuristicMap[4][7];

l61 = l51.add(Direction.EAST);
v61 = 100000000;
d61 = null;

p61 = heuristicMap[6][1];

l67 = l57.add(Direction.EAST);
v67 = 100000000;
d67 = null;

p67 = heuristicMap[6][7];

l22 = l23.add(Direction.SOUTH);
v22 = 100000000;
d22 = null;

p22 = heuristicMap[2][2];

l26 = l36.add(Direction.WEST);
v26 = 100000000;
d26 = null;

p26 = heuristicMap[2][6];

l82 = l72.add(Direction.EAST);
v82 = 100000000;
d82 = null;

p82 = heuristicMap[8][2];

l86 = l76.add(Direction.EAST);
v86 = 100000000;
d86 = null;

p86 = heuristicMap[8][6];

l31 = l32.add(Direction.SOUTH);
v31 = 100000000;
d31 = null;

p31 = heuristicMap[3][1];

l37 = l47.add(Direction.WEST);
v37 = 100000000;
d37 = null;

p37 = heuristicMap[3][7];

l71 = l61.add(Direction.EAST);
v71 = 100000000;
d71 = null;

p71 = heuristicMap[7][1];

l77 = l67.add(Direction.EAST);
v77 = 100000000;
d77 = null;

p77 = heuristicMap[7][7];

l21 = l22.add(Direction.SOUTH);
v21 = 100000000;
d21 = null;

p21 = heuristicMap[2][1];

l27 = l37.add(Direction.WEST);
v27 = 100000000;
d27 = null;

p27 = heuristicMap[2][7];

l81 = l71.add(Direction.EAST);
v81 = 100000000;
d81 = null;

p81 = heuristicMap[8][1];

l87 = l77.add(Direction.EAST);
v87 = 100000000;
d87 = null;

p87 = heuristicMap[8][7];

l14 = l24.add(Direction.WEST);
v14 = 100000000;
d14 = null;

p14 = heuristicMap[1][4];

l94 = l84.add(Direction.EAST);
v94 = 100000000;
d94 = null;

p94 = heuristicMap[9][4];

l50 = l51.add(Direction.SOUTH);
v50 = 100000000;
d50 = null;

p50 = heuristicMap[5][0];

l58 = l57.add(Direction.NORTH);
v58 = 100000000;
d58 = null;

p58 = heuristicMap[5][8];

l13 = l14.add(Direction.SOUTH);
v13 = 100000000;
d13 = null;

p13 = heuristicMap[1][3];

l15 = l25.add(Direction.WEST);
v15 = 100000000;
d15 = null;

p15 = heuristicMap[1][5];

l93 = l83.add(Direction.EAST);
v93 = 100000000;
d93 = null;

p93 = heuristicMap[9][3];

l95 = l85.add(Direction.EAST);
v95 = 100000000;
d95 = null;

p95 = heuristicMap[9][5];

l40 = l41.add(Direction.SOUTH);
v40 = 100000000;
d40 = null;

p40 = heuristicMap[4][0];

l48 = l58.add(Direction.WEST);
v48 = 100000000;
d48 = null;

p48 = heuristicMap[4][8];

l60 = l50.add(Direction.EAST);
v60 = 100000000;
d60 = null;

p60 = heuristicMap[6][0];

l68 = l58.add(Direction.EAST);
v68 = 100000000;
d68 = null;

p68 = heuristicMap[6][8];

l12 = l13.add(Direction.SOUTH);
v12 = 100000000;
d12 = null;

p12 = heuristicMap[1][2];

l16 = l26.add(Direction.WEST);
v16 = 100000000;
d16 = null;

p16 = heuristicMap[1][6];

l92 = l82.add(Direction.EAST);
v92 = 100000000;
d92 = null;

p92 = heuristicMap[9][2];

l96 = l86.add(Direction.EAST);
v96 = 100000000;
d96 = null;

p96 = heuristicMap[9][6];

l30 = l31.add(Direction.SOUTH);
v30 = 100000000;
d30 = null;

p30 = heuristicMap[3][0];

l38 = l48.add(Direction.WEST);
v38 = 100000000;
d38 = null;

p38 = heuristicMap[3][8];

l70 = l60.add(Direction.EAST);
v70 = 100000000;
d70 = null;

p70 = heuristicMap[7][0];

l78 = l68.add(Direction.EAST);
v78 = 100000000;
d78 = null;

p78 = heuristicMap[7][8];

this.vars_are_reset = true;
}

Direction runBFSNorth(MapLocation target) throws GameActionException{
try{ 
	double sum;
if(p44 != 0){
if(!rc.isLocationOccupied(l44)){
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(p64 != 0){
if(!rc.isLocationOccupied(l64)){
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(p53 != 0){
if(!rc.isLocationOccupied(l53)){
v53 -= p53;
if(v53 > v54){
v53 = v54;
d53 = Direction.SOUTH;
}
if(v53 > v44){
v53 = v44;
d53 = d44;
}
if(v53 > v64){
v53 = v64;
d53 = d64;
}
v53 += p53;
}
}
if(p55 != 0){
if(!rc.isLocationOccupied(l55)){
v55 -= p55;
if(v55 > v54){
v55 = v54;
d55 = Direction.NORTH;
}
if(v55 > v44){
v55 = v44;
d55 = d44;
}
if(v55 > v64){
v55 = v64;
d55 = d64;
}
v55 += p55;
}
}
if(p43 != 0){
if(!rc.isLocationOccupied(l43)){
v43 -= p43;
if(v43 > v54){
v43 = v54;
d43 = Direction.SOUTHWEST;
}
if(v43 > v44){
v43 = v44;
d43 = d44;
}
if(v43 > v53){
v43 = v53;
d43 = d53;
}
v43 += p43;
}
}
if(p45 != 0){
if(!rc.isLocationOccupied(l45)){
v45 -= p45;
if(v45 > v54){
v45 = v54;
d45 = Direction.NORTHWEST;
}
if(v45 > v55){
v45 = v55;
d45 = d55;
}
if(v45 > v44){
v45 = v44;
d45 = d44;
}
v45 += p45;
}
}
if(p63 != 0){
if(!rc.isLocationOccupied(l63)){
v63 -= p63;
if(v63 > v54){
v63 = v54;
d63 = Direction.SOUTHEAST;
}
if(v63 > v53){
v63 = v53;
d63 = d53;
}
if(v63 > v64){
v63 = v64;
d63 = d64;
}
v63 += p63;
}
}
if(p65 != 0){
if(!rc.isLocationOccupied(l65)){
v65 -= p65;
if(v65 > v54){
v65 = v54;
d65 = Direction.NORTHEAST;
}
if(v65 > v55){
v65 = v55;
d65 = d55;
}
if(v65 > v64){
v65 = v64;
d65 = d64;
}
v65 += p65;
}
}
if(p34 != 0){
v34 -= p34;
if(v34 > v44){
v34 = v44;
d34 = d44;
}
if(v34 > v45){
v34 = v45;
d34 = d45;
}
if(v34 > v43){
v34 = v43;
d34 = d43;
}
v34 += p34;
}
if(p74 != 0){
v74 -= p74;
if(v74 > v64){
v74 = v64;
d74 = d64;
}
if(v74 > v63){
v74 = v63;
d74 = d63;
}
if(v74 > v65){
v74 = v65;
d74 = d65;
}
v74 += p74;
}
if(p52 != 0){
v52 -= p52;
if(v52 > v53){
v52 = v53;
d52 = d53;
}
if(v52 > v43){
v52 = v43;
d52 = d43;
}
if(v52 > v63){
v52 = v63;
d52 = d63;
}
v52 += p52;
}
if(p56 != 0){
v56 -= p56;
if(v56 > v55){
v56 = v55;
d56 = d55;
}
if(v56 > v45){
v56 = v45;
d56 = d45;
}
if(v56 > v65){
v56 = v65;
d56 = d65;
}
v56 += p56;
}
if(p33 != 0){
v33 -= p33;
if(v33 > v44){
v33 = v44;
d33 = d44;
}
if(v33 > v43){
v33 = v43;
d33 = d43;
}
if(v33 > v34){
v33 = v34;
d33 = d34;
}
v33 += p33;
}
if(p35 != 0){
v35 -= p35;
if(v35 > v44){
v35 = v44;
d35 = d44;
}
if(v35 > v45){
v35 = v45;
d35 = d45;
}
if(v35 > v34){
v35 = v34;
d35 = d34;
}
v35 += p35;
}
if(p73 != 0){
v73 -= p73;
if(v73 > v64){
v73 = v64;
d73 = d64;
}
if(v73 > v63){
v73 = v63;
d73 = d63;
}
if(v73 > v74){
v73 = v74;
d73 = d74;
}
v73 += p73;
}
if(p75 != 0){
v75 -= p75;
if(v75 > v64){
v75 = v64;
d75 = d64;
}
if(v75 > v65){
v75 = v65;
d75 = d65;
}
if(v75 > v74){
v75 = v74;
d75 = d74;
}
v75 += p75;
}
if(p42 != 0){
v42 -= p42;
if(v42 > v53){
v42 = v53;
d42 = d53;
}
if(v42 > v43){
v42 = v43;
d42 = d43;
}
if(v42 > v52){
v42 = v52;
d42 = d52;
}
if(v42 > v33){
v42 = v33;
d42 = d33;
}
v42 += p42;
}
if(p46 != 0){
v46 -= p46;
if(v46 > v55){
v46 = v55;
d46 = d55;
}
if(v46 > v45){
v46 = v45;
d46 = d45;
}
if(v46 > v56){
v46 = v56;
d46 = d56;
}
if(v46 > v35){
v46 = v35;
d46 = d35;
}
v46 += p46;
}
if(p62 != 0){
v62 -= p62;
if(v62 > v53){
v62 = v53;
d62 = d53;
}
if(v62 > v63){
v62 = v63;
d62 = d63;
}
if(v62 > v52){
v62 = v52;
d62 = d52;
}
if(v62 > v73){
v62 = v73;
d62 = d73;
}
v62 += p62;
}
if(p66 != 0){
v66 -= p66;
if(v66 > v55){
v66 = v55;
d66 = d55;
}
if(v66 > v65){
v66 = v65;
d66 = d65;
}
if(v66 > v56){
v66 = v56;
d66 = d56;
}
if(v66 > v75){
v66 = v75;
d66 = d75;
}
v66 += p66;
}
if(p32 != 0){
v32 -= p32;
if(v32 > v43){
v32 = v43;
d32 = d43;
}
if(v32 > v33){
v32 = v33;
d32 = d33;
}
if(v32 > v42){
v32 = v42;
d32 = d42;
}
v32 += p32;
}
if(p36 != 0){
v36 -= p36;
if(v36 > v45){
v36 = v45;
d36 = d45;
}
if(v36 > v46){
v36 = v46;
d36 = d46;
}
if(v36 > v35){
v36 = v35;
d36 = d35;
}
v36 += p36;
}
if(p72 != 0){
v72 -= p72;
if(v72 > v63){
v72 = v63;
d72 = d63;
}
if(v72 > v62){
v72 = v62;
d72 = d62;
}
if(v72 > v73){
v72 = v73;
d72 = d73;
}
v72 += p72;
}
if(p76 != 0){
v76 -= p76;
if(v76 > v65){
v76 = v65;
d76 = d65;
}
if(v76 > v66){
v76 = v66;
d76 = d66;
}
if(v76 > v75){
v76 = v75;
d76 = d75;
}
v76 += p76;
}
if(p24 != 0){
v24 -= p24;
if(v24 > v34){
v24 = v34;
d24 = d34;
}
if(v24 > v35){
v24 = v35;
d24 = d35;
}
if(v24 > v33){
v24 = v33;
d24 = d33;
}
v24 += p24;
}
if(p84 != 0){
v84 -= p84;
if(v84 > v74){
v84 = v74;
d84 = d74;
}
if(v84 > v73){
v84 = v73;
d84 = d73;
}
if(v84 > v75){
v84 = v75;
d84 = d75;
}
v84 += p84;
}
if(p57 != 0){
v57 -= p57;
if(v57 > v56){
v57 = v56;
d57 = d56;
}
if(v57 > v46){
v57 = v46;
d57 = d46;
}
if(v57 > v66){
v57 = v66;
d57 = d66;
}
v57 += p57;
}
if(p23 != 0){
v23 -= p23;
if(v23 > v34){
v23 = v34;
d23 = d34;
}
if(v23 > v33){
v23 = v33;
d23 = d33;
}
if(v23 > v32){
v23 = v32;
d23 = d32;
}
if(v23 > v24){
v23 = v24;
d23 = d24;
}
v23 += p23;
}
if(p25 != 0){
v25 -= p25;
if(v25 > v34){
v25 = v34;
d25 = d34;
}
if(v25 > v35){
v25 = v35;
d25 = d35;
}
if(v25 > v36){
v25 = v36;
d25 = d36;
}
if(v25 > v24){
v25 = v24;
d25 = d24;
}
v25 += p25;
}
if(p83 != 0){
v83 -= p83;
if(v83 > v74){
v83 = v74;
d83 = d74;
}
if(v83 > v73){
v83 = v73;
d83 = d73;
}
if(v83 > v72){
v83 = v72;
d83 = d72;
}
if(v83 > v84){
v83 = v84;
d83 = d84;
}
v83 += p83;
}
if(p85 != 0){
v85 -= p85;
if(v85 > v74){
v85 = v74;
d85 = d74;
}
if(v85 > v75){
v85 = v75;
d85 = d75;
}
if(v85 > v76){
v85 = v76;
d85 = d76;
}
if(v85 > v84){
v85 = v84;
d85 = d84;
}
v85 += p85;
}
if(p47 != 0){
v47 -= p47;
if(v47 > v56){
v47 = v56;
d47 = d56;
}
if(v47 > v46){
v47 = v46;
d47 = d46;
}
if(v47 > v36){
v47 = v36;
d47 = d36;
}
if(v47 > v57){
v47 = v57;
d47 = d57;
}
v47 += p47;
}
if(p67 != 0){
v67 -= p67;
if(v67 > v56){
v67 = v56;
d67 = d56;
}
if(v67 > v66){
v67 = v66;
d67 = d66;
}
if(v67 > v76){
v67 = v76;
d67 = d76;
}
if(v67 > v57){
v67 = v57;
d67 = d57;
}
v67 += p67;
}
if(p22 != 0){
v22 -= p22;
if(v22 > v33){
v22 = v33;
d22 = d33;
}
if(v22 > v32){
v22 = v32;
d22 = d32;
}
if(v22 > v23){
v22 = v23;
d22 = d23;
}
v22 += p22;
}
if(p26 != 0){
v26 -= p26;
if(v26 > v35){
v26 = v35;
d26 = d35;
}
if(v26 > v36){
v26 = v36;
d26 = d36;
}
if(v26 > v25){
v26 = v25;
d26 = d25;
}
v26 += p26;
}
if(p82 != 0){
v82 -= p82;
if(v82 > v73){
v82 = v73;
d82 = d73;
}
if(v82 > v72){
v82 = v72;
d82 = d72;
}
if(v82 > v83){
v82 = v83;
d82 = d83;
}
v82 += p82;
}
if(p86 != 0){
v86 -= p86;
if(v86 > v75){
v86 = v75;
d86 = d75;
}
if(v86 > v76){
v86 = v76;
d86 = d76;
}
if(v86 > v85){
v86 = v85;
d86 = d85;
}
v86 += p86;
}
if(p37 != 0){
v37 -= p37;
if(v37 > v46){
v37 = v46;
d37 = d46;
}
if(v37 > v36){
v37 = v36;
d37 = d36;
}
if(v37 > v47){
v37 = v47;
d37 = d47;
}
if(v37 > v26){
v37 = v26;
d37 = d26;
}
v37 += p37;
}
if(p77 != 0){
v77 -= p77;
if(v77 > v66){
v77 = v66;
d77 = d66;
}
if(v77 > v76){
v77 = v76;
d77 = d76;
}
if(v77 > v67){
v77 = v67;
d77 = d67;
}
if(v77 > v86){
v77 = v86;
d77 = d86;
}
v77 += p77;
}
if(p27 != 0){
v27 -= p27;
if(v27 > v36){
v27 = v36;
d27 = d36;
}
if(v27 > v37){
v27 = v37;
d27 = d37;
}
if(v27 > v26){
v27 = v26;
d27 = d26;
}
v27 += p27;
}
if(p87 != 0){
v87 -= p87;
if(v87 > v76){
v87 = v76;
d87 = d76;
}
if(v87 > v77){
v87 = v77;
d87 = d77;
}
if(v87 > v86){
v87 = v86;
d87 = d86;
}
v87 += p87;
}
if(p14 != 0){
v14 -= p14;
if(v14 > v24){
v14 = v24;
d14 = d24;
}
if(v14 > v25){
v14 = v25;
d14 = d25;
}
if(v14 > v23){
v14 = v23;
d14 = d23;
}
v14 += p14;
}
if(p94 != 0){
v94 -= p94;
if(v94 > v84){
v94 = v84;
d94 = d84;
}
if(v94 > v83){
v94 = v83;
d94 = d83;
}
if(v94 > v85){
v94 = v85;
d94 = d85;
}
v94 += p94;
}
if(p58 != 0){
v58 -= p58;
if(v58 > v57){
v58 = v57;
d58 = d57;
}
if(v58 > v47){
v58 = v47;
d58 = d47;
}
if(v58 > v67){
v58 = v67;
d58 = d67;
}
v58 += p58;
}
if(p13 != 0){
v13 -= p13;
if(v13 > v24){
v13 = v24;
d13 = d24;
}
if(v13 > v23){
v13 = v23;
d13 = d23;
}
if(v13 > v22){
v13 = v22;
d13 = d22;
}
if(v13 > v14){
v13 = v14;
d13 = d14;
}
v13 += p13;
}
if(p15 != 0){
v15 -= p15;
if(v15 > v24){
v15 = v24;
d15 = d24;
}
if(v15 > v25){
v15 = v25;
d15 = d25;
}
if(v15 > v26){
v15 = v26;
d15 = d26;
}
if(v15 > v14){
v15 = v14;
d15 = d14;
}
v15 += p15;
}
if(p93 != 0){
v93 -= p93;
if(v93 > v84){
v93 = v84;
d93 = d84;
}
if(v93 > v83){
v93 = v83;
d93 = d83;
}
if(v93 > v82){
v93 = v82;
d93 = d82;
}
if(v93 > v94){
v93 = v94;
d93 = d94;
}
v93 += p93;
}
if(p95 != 0){
v95 -= p95;
if(v95 > v84){
v95 = v84;
d95 = d84;
}
if(v95 > v85){
v95 = v85;
d95 = d85;
}
if(v95 > v86){
v95 = v86;
d95 = d86;
}
if(v95 > v94){
v95 = v94;
d95 = d94;
}
v95 += p95;
}
if(p48 != 0){
v48 -= p48;
if(v48 > v57){
v48 = v57;
d48 = d57;
}
if(v48 > v47){
v48 = v47;
d48 = d47;
}
if(v48 > v37){
v48 = v37;
d48 = d37;
}
if(v48 > v58){
v48 = v58;
d48 = d58;
}
v48 += p48;
}
if(p68 != 0){
v68 -= p68;
if(v68 > v57){
v68 = v57;
d68 = d57;
}
if(v68 > v67){
v68 = v67;
d68 = d67;
}
if(v68 > v77){
v68 = v77;
d68 = d77;
}
if(v68 > v58){
v68 = v58;
d68 = d58;
}
v68 += p68;
}
if(p12 != 0){
v12 -= p12;
if(v12 > v23){
v12 = v23;
d12 = d23;
}
if(v12 > v22){
v12 = v22;
d12 = d22;
}
if(v12 > v13){
v12 = v13;
d12 = d13;
}
v12 += p12;
}
if(p16 != 0){
v16 -= p16;
if(v16 > v25){
v16 = v25;
d16 = d25;
}
if(v16 > v26){
v16 = v26;
d16 = d26;
}
if(v16 > v15){
v16 = v15;
d16 = d15;
}
if(v16 > v27){
v16 = v27;
d16 = d27;
}
v16 += p16;
}
if(p92 != 0){
v92 -= p92;
if(v92 > v83){
v92 = v83;
d92 = d83;
}
if(v92 > v82){
v92 = v82;
d92 = d82;
}
if(v92 > v93){
v92 = v93;
d92 = d93;
}
v92 += p92;
}
if(p96 != 0){
v96 -= p96;
if(v96 > v85){
v96 = v85;
d96 = d85;
}
if(v96 > v86){
v96 = v86;
d96 = d86;
}
if(v96 > v95){
v96 = v95;
d96 = d95;
}
if(v96 > v87){
v96 = v87;
d96 = d87;
}
v96 += p96;
}
if(p38 != 0){
v38 -= p38;
if(v38 > v47){
v38 = v47;
d38 = d47;
}
if(v38 > v37){
v38 = v37;
d38 = d37;
}
if(v38 > v48){
v38 = v48;
d38 = d48;
}
if(v38 > v27){
v38 = v27;
d38 = d27;
}
v38 += p38;
}
if(p78 != 0){
v78 -= p78;
if(v78 > v67){
v78 = v67;
d78 = d67;
}
if(v78 > v77){
v78 = v77;
d78 = d77;
}
if(v78 > v68){
v78 = v68;
d78 = d68;
}
if(v78 > v87){
v78 = v87;
d78 = d87;
}
v78 += p78;
}
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -4:
switch (dy){
case -2:
return d12;
case -1:
return d13;
case 0:
return d14;
case 1:
return d15;
case 2:
return d16;
}
break;
case -3:
switch (dy){
case -2:
return d22;
case -1:
return d23;
case 0:
return d24;
case 1:
return d25;
case 2:
return d26;
case 3:
return d27;
}
break;
case -2:
switch (dy){
case -2:
return d32;
case -1:
return d33;
case 0:
return d34;
case 1:
return d35;
case 2:
return d36;
case 3:
return d37;
case 4:
return d38;
}
break;
case -1:
switch (dy){
case -2:
return d42;
case -1:
return d43;
case 0:
return d44;
case 1:
return d45;
case 2:
return d46;
case 3:
return d47;
case 4:
return d48;
}
break;
case 0:
switch (dy){
case -2:
return d52;
case -1:
return d53;
case 0:
return d54;
case 1:
return d55;
case 2:
return d56;
case 3:
return d57;
case 4:
return d58;
}
break;
case 1:
switch (dy){
case -2:
return d62;
case -1:
return d63;
case 0:
return d64;
case 1:
return d65;
case 2:
return d66;
case 3:
return d67;
case 4:
return d68;
}
break;
case 2:
switch (dy){
case -2:
return d72;
case -1:
return d73;
case 0:
return d74;
case 1:
return d75;
case 2:
return d76;
case 3:
return d77;
case 4:
return d78;
}
break;
case 3:
switch (dy){
case -2:
return d82;
case -1:
return d83;
case 0:
return d84;
case 1:
return d85;
case 2:
return d86;
case 3:
return d87;
}
break;
case 4:
switch (dy){
case -2:
return d92;
case -1:
return d93;
case 0:
return d94;
case 1:
return d95;
case 2:
return d96;
}
break;
}
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l52.distanceSquaredTo(target)) / v52;
if(currScore > bestScore){
bestScore = currScore;
ans = d52;
}
currScore = (initialDist - l42.distanceSquaredTo(target)) / v42;
if(currScore > bestScore){
bestScore = currScore;
ans = d42;
}
currScore = (initialDist - l62.distanceSquaredTo(target)) / v62;
if(currScore > bestScore){
bestScore = currScore;
ans = d62;
}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
}
currScore = (initialDist - l27.distanceSquaredTo(target)) / v27;
if(currScore > bestScore){
bestScore = currScore;
ans = d27;
}
currScore = (initialDist - l87.distanceSquaredTo(target)) / v87;
if(currScore > bestScore){
bestScore = currScore;
ans = d87;
}
currScore = (initialDist - l14.distanceSquaredTo(target)) / v14;
if(currScore > bestScore){
bestScore = currScore;
ans = d14;
}
currScore = (initialDist - l94.distanceSquaredTo(target)) / v94;
if(currScore > bestScore){
bestScore = currScore;
ans = d94;
}
currScore = (initialDist - l58.distanceSquaredTo(target)) / v58;
if(currScore > bestScore){
bestScore = currScore;
ans = d58;
}
currScore = (initialDist - l13.distanceSquaredTo(target)) / v13;
if(currScore > bestScore){
bestScore = currScore;
ans = d13;
}
currScore = (initialDist - l15.distanceSquaredTo(target)) / v15;
if(currScore > bestScore){
bestScore = currScore;
ans = d15;
}
currScore = (initialDist - l93.distanceSquaredTo(target)) / v93;
if(currScore > bestScore){
bestScore = currScore;
ans = d93;
}
currScore = (initialDist - l95.distanceSquaredTo(target)) / v95;
if(currScore > bestScore){
bestScore = currScore;
ans = d95;
}
currScore = (initialDist - l48.distanceSquaredTo(target)) / v48;
if(currScore > bestScore){
bestScore = currScore;
ans = d48;
}
currScore = (initialDist - l68.distanceSquaredTo(target)) / v68;
if(currScore > bestScore){
bestScore = currScore;
ans = d68;
}
currScore = (initialDist - l12.distanceSquaredTo(target)) / v12;
if(currScore > bestScore){
bestScore = currScore;
ans = d12;
}
currScore = (initialDist - l16.distanceSquaredTo(target)) / v16;
if(currScore > bestScore){
bestScore = currScore;
ans = d16;
}
currScore = (initialDist - l92.distanceSquaredTo(target)) / v92;
if(currScore > bestScore){
bestScore = currScore;
ans = d92;
}
currScore = (initialDist - l96.distanceSquaredTo(target)) / v96;
if(currScore > bestScore){
bestScore = currScore;
ans = d96;
}
currScore = (initialDist - l38.distanceSquaredTo(target)) / v38;
if(currScore > bestScore){
bestScore = currScore;
ans = d38;
}
currScore = (initialDist - l78.distanceSquaredTo(target)) / v78;
if(currScore > bestScore){
bestScore = currScore;
ans = d78;
}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSSouth(MapLocation target) throws GameActionException{
try{ 
	double sum;
if(p44 != 0){
if(!rc.isLocationOccupied(l44)){
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(p64 != 0){
if(!rc.isLocationOccupied(l64)){
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(p53 != 0){
if(!rc.isLocationOccupied(l53)){
v53 -= p53;
if(v53 > v54){
v53 = v54;
d53 = Direction.SOUTH;
}
if(v53 > v44){
v53 = v44;
d53 = d44;
}
if(v53 > v64){
v53 = v64;
d53 = d64;
}
v53 += p53;
}
}
if(p55 != 0){
if(!rc.isLocationOccupied(l55)){
v55 -= p55;
if(v55 > v54){
v55 = v54;
d55 = Direction.NORTH;
}
if(v55 > v44){
v55 = v44;
d55 = d44;
}
if(v55 > v64){
v55 = v64;
d55 = d64;
}
v55 += p55;
}
}
if(p43 != 0){
if(!rc.isLocationOccupied(l43)){
v43 -= p43;
if(v43 > v54){
v43 = v54;
d43 = Direction.SOUTHWEST;
}
if(v43 > v44){
v43 = v44;
d43 = d44;
}
if(v43 > v53){
v43 = v53;
d43 = d53;
}
v43 += p43;
}
}
if(p45 != 0){
if(!rc.isLocationOccupied(l45)){
v45 -= p45;
if(v45 > v54){
v45 = v54;
d45 = Direction.NORTHWEST;
}
if(v45 > v55){
v45 = v55;
d45 = d55;
}
if(v45 > v44){
v45 = v44;
d45 = d44;
}
v45 += p45;
}
}
if(p63 != 0){
if(!rc.isLocationOccupied(l63)){
v63 -= p63;
if(v63 > v54){
v63 = v54;
d63 = Direction.SOUTHEAST;
}
if(v63 > v53){
v63 = v53;
d63 = d53;
}
if(v63 > v64){
v63 = v64;
d63 = d64;
}
v63 += p63;
}
}
if(p65 != 0){
if(!rc.isLocationOccupied(l65)){
v65 -= p65;
if(v65 > v54){
v65 = v54;
d65 = Direction.NORTHEAST;
}
if(v65 > v55){
v65 = v55;
d65 = d55;
}
if(v65 > v64){
v65 = v64;
d65 = d64;
}
v65 += p65;
}
}
if(p34 != 0){
v34 -= p34;
if(v34 > v44){
v34 = v44;
d34 = d44;
}
if(v34 > v45){
v34 = v45;
d34 = d45;
}
if(v34 > v43){
v34 = v43;
d34 = d43;
}
v34 += p34;
}
if(p74 != 0){
v74 -= p74;
if(v74 > v64){
v74 = v64;
d74 = d64;
}
if(v74 > v63){
v74 = v63;
d74 = d63;
}
if(v74 > v65){
v74 = v65;
d74 = d65;
}
v74 += p74;
}
if(p52 != 0){
v52 -= p52;
if(v52 > v53){
v52 = v53;
d52 = d53;
}
if(v52 > v43){
v52 = v43;
d52 = d43;
}
if(v52 > v63){
v52 = v63;
d52 = d63;
}
v52 += p52;
}
if(p56 != 0){
v56 -= p56;
if(v56 > v55){
v56 = v55;
d56 = d55;
}
if(v56 > v45){
v56 = v45;
d56 = d45;
}
if(v56 > v65){
v56 = v65;
d56 = d65;
}
v56 += p56;
}
if(p33 != 0){
v33 -= p33;
if(v33 > v44){
v33 = v44;
d33 = d44;
}
if(v33 > v43){
v33 = v43;
d33 = d43;
}
if(v33 > v34){
v33 = v34;
d33 = d34;
}
v33 += p33;
}
if(p35 != 0){
v35 -= p35;
if(v35 > v44){
v35 = v44;
d35 = d44;
}
if(v35 > v45){
v35 = v45;
d35 = d45;
}
if(v35 > v34){
v35 = v34;
d35 = d34;
}
v35 += p35;
}
if(p73 != 0){
v73 -= p73;
if(v73 > v64){
v73 = v64;
d73 = d64;
}
if(v73 > v63){
v73 = v63;
d73 = d63;
}
if(v73 > v74){
v73 = v74;
d73 = d74;
}
v73 += p73;
}
if(p75 != 0){
v75 -= p75;
if(v75 > v64){
v75 = v64;
d75 = d64;
}
if(v75 > v65){
v75 = v65;
d75 = d65;
}
if(v75 > v74){
v75 = v74;
d75 = d74;
}
v75 += p75;
}
if(p42 != 0){
v42 -= p42;
if(v42 > v53){
v42 = v53;
d42 = d53;
}
if(v42 > v43){
v42 = v43;
d42 = d43;
}
if(v42 > v52){
v42 = v52;
d42 = d52;
}
if(v42 > v33){
v42 = v33;
d42 = d33;
}
v42 += p42;
}
if(p46 != 0){
v46 -= p46;
if(v46 > v55){
v46 = v55;
d46 = d55;
}
if(v46 > v45){
v46 = v45;
d46 = d45;
}
if(v46 > v56){
v46 = v56;
d46 = d56;
}
if(v46 > v35){
v46 = v35;
d46 = d35;
}
v46 += p46;
}
if(p62 != 0){
v62 -= p62;
if(v62 > v53){
v62 = v53;
d62 = d53;
}
if(v62 > v63){
v62 = v63;
d62 = d63;
}
if(v62 > v52){
v62 = v52;
d62 = d52;
}
if(v62 > v73){
v62 = v73;
d62 = d73;
}
v62 += p62;
}
if(p66 != 0){
v66 -= p66;
if(v66 > v55){
v66 = v55;
d66 = d55;
}
if(v66 > v65){
v66 = v65;
d66 = d65;
}
if(v66 > v56){
v66 = v56;
d66 = d56;
}
if(v66 > v75){
v66 = v75;
d66 = d75;
}
v66 += p66;
}
if(p32 != 0){
v32 -= p32;
if(v32 > v43){
v32 = v43;
d32 = d43;
}
if(v32 > v33){
v32 = v33;
d32 = d33;
}
if(v32 > v42){
v32 = v42;
d32 = d42;
}
v32 += p32;
}
if(p36 != 0){
v36 -= p36;
if(v36 > v45){
v36 = v45;
d36 = d45;
}
if(v36 > v46){
v36 = v46;
d36 = d46;
}
if(v36 > v35){
v36 = v35;
d36 = d35;
}
v36 += p36;
}
if(p72 != 0){
v72 -= p72;
if(v72 > v63){
v72 = v63;
d72 = d63;
}
if(v72 > v62){
v72 = v62;
d72 = d62;
}
if(v72 > v73){
v72 = v73;
d72 = d73;
}
v72 += p72;
}
if(p76 != 0){
v76 -= p76;
if(v76 > v65){
v76 = v65;
d76 = d65;
}
if(v76 > v66){
v76 = v66;
d76 = d66;
}
if(v76 > v75){
v76 = v75;
d76 = d75;
}
v76 += p76;
}
if(p24 != 0){
v24 -= p24;
if(v24 > v34){
v24 = v34;
d24 = d34;
}
if(v24 > v35){
v24 = v35;
d24 = d35;
}
if(v24 > v33){
v24 = v33;
d24 = d33;
}
v24 += p24;
}
if(p84 != 0){
v84 -= p84;
if(v84 > v74){
v84 = v74;
d84 = d74;
}
if(v84 > v73){
v84 = v73;
d84 = d73;
}
if(v84 > v75){
v84 = v75;
d84 = d75;
}
v84 += p84;
}
if(p51 != 0){
v51 -= p51;
if(v51 > v52){
v51 = v52;
d51 = d52;
}
if(v51 > v42){
v51 = v42;
d51 = d42;
}
if(v51 > v62){
v51 = v62;
d51 = d62;
}
v51 += p51;
}
if(p23 != 0){
v23 -= p23;
if(v23 > v34){
v23 = v34;
d23 = d34;
}
if(v23 > v33){
v23 = v33;
d23 = d33;
}
if(v23 > v32){
v23 = v32;
d23 = d32;
}
if(v23 > v24){
v23 = v24;
d23 = d24;
}
v23 += p23;
}
if(p25 != 0){
v25 -= p25;
if(v25 > v34){
v25 = v34;
d25 = d34;
}
if(v25 > v35){
v25 = v35;
d25 = d35;
}
if(v25 > v36){
v25 = v36;
d25 = d36;
}
if(v25 > v24){
v25 = v24;
d25 = d24;
}
v25 += p25;
}
if(p83 != 0){
v83 -= p83;
if(v83 > v74){
v83 = v74;
d83 = d74;
}
if(v83 > v73){
v83 = v73;
d83 = d73;
}
if(v83 > v72){
v83 = v72;
d83 = d72;
}
if(v83 > v84){
v83 = v84;
d83 = d84;
}
v83 += p83;
}
if(p85 != 0){
v85 -= p85;
if(v85 > v74){
v85 = v74;
d85 = d74;
}
if(v85 > v75){
v85 = v75;
d85 = d75;
}
if(v85 > v76){
v85 = v76;
d85 = d76;
}
if(v85 > v84){
v85 = v84;
d85 = d84;
}
v85 += p85;
}
if(p41 != 0){
v41 -= p41;
if(v41 > v52){
v41 = v52;
d41 = d52;
}
if(v41 > v42){
v41 = v42;
d41 = d42;
}
if(v41 > v32){
v41 = v32;
d41 = d32;
}
if(v41 > v51){
v41 = v51;
d41 = d51;
}
v41 += p41;
}
if(p61 != 0){
v61 -= p61;
if(v61 > v52){
v61 = v52;
d61 = d52;
}
if(v61 > v62){
v61 = v62;
d61 = d62;
}
if(v61 > v72){
v61 = v72;
d61 = d72;
}
if(v61 > v51){
v61 = v51;
d61 = d51;
}
v61 += p61;
}
if(p22 != 0){
v22 -= p22;
if(v22 > v33){
v22 = v33;
d22 = d33;
}
if(v22 > v32){
v22 = v32;
d22 = d32;
}
if(v22 > v23){
v22 = v23;
d22 = d23;
}
v22 += p22;
}
if(p26 != 0){
v26 -= p26;
if(v26 > v35){
v26 = v35;
d26 = d35;
}
if(v26 > v36){
v26 = v36;
d26 = d36;
}
if(v26 > v25){
v26 = v25;
d26 = d25;
}
v26 += p26;
}
if(p82 != 0){
v82 -= p82;
if(v82 > v73){
v82 = v73;
d82 = d73;
}
if(v82 > v72){
v82 = v72;
d82 = d72;
}
if(v82 > v83){
v82 = v83;
d82 = d83;
}
v82 += p82;
}
if(p86 != 0){
v86 -= p86;
if(v86 > v75){
v86 = v75;
d86 = d75;
}
if(v86 > v76){
v86 = v76;
d86 = d76;
}
if(v86 > v85){
v86 = v85;
d86 = d85;
}
v86 += p86;
}
if(p31 != 0){
v31 -= p31;
if(v31 > v42){
v31 = v42;
d31 = d42;
}
if(v31 > v32){
v31 = v32;
d31 = d32;
}
if(v31 > v41){
v31 = v41;
d31 = d41;
}
if(v31 > v22){
v31 = v22;
d31 = d22;
}
v31 += p31;
}
if(p71 != 0){
v71 -= p71;
if(v71 > v62){
v71 = v62;
d71 = d62;
}
if(v71 > v72){
v71 = v72;
d71 = d72;
}
if(v71 > v61){
v71 = v61;
d71 = d61;
}
if(v71 > v82){
v71 = v82;
d71 = d82;
}
v71 += p71;
}
if(p21 != 0){
v21 -= p21;
if(v21 > v32){
v21 = v32;
d21 = d32;
}
if(v21 > v22){
v21 = v22;
d21 = d22;
}
if(v21 > v31){
v21 = v31;
d21 = d31;
}
v21 += p21;
}
if(p81 != 0){
v81 -= p81;
if(v81 > v72){
v81 = v72;
d81 = d72;
}
if(v81 > v71){
v81 = v71;
d81 = d71;
}
if(v81 > v82){
v81 = v82;
d81 = d82;
}
v81 += p81;
}
if(p14 != 0){
v14 -= p14;
if(v14 > v24){
v14 = v24;
d14 = d24;
}
if(v14 > v25){
v14 = v25;
d14 = d25;
}
if(v14 > v23){
v14 = v23;
d14 = d23;
}
v14 += p14;
}
if(p94 != 0){
v94 -= p94;
if(v94 > v84){
v94 = v84;
d94 = d84;
}
if(v94 > v83){
v94 = v83;
d94 = d83;
}
if(v94 > v85){
v94 = v85;
d94 = d85;
}
v94 += p94;
}
if(p50 != 0){
v50 -= p50;
if(v50 > v51){
v50 = v51;
d50 = d51;
}
if(v50 > v41){
v50 = v41;
d50 = d41;
}
if(v50 > v61){
v50 = v61;
d50 = d61;
}
v50 += p50;
}
if(p13 != 0){
v13 -= p13;
if(v13 > v24){
v13 = v24;
d13 = d24;
}
if(v13 > v23){
v13 = v23;
d13 = d23;
}
if(v13 > v22){
v13 = v22;
d13 = d22;
}
if(v13 > v14){
v13 = v14;
d13 = d14;
}
v13 += p13;
}
if(p15 != 0){
v15 -= p15;
if(v15 > v24){
v15 = v24;
d15 = d24;
}
if(v15 > v25){
v15 = v25;
d15 = d25;
}
if(v15 > v26){
v15 = v26;
d15 = d26;
}
if(v15 > v14){
v15 = v14;
d15 = d14;
}
v15 += p15;
}
if(p93 != 0){
v93 -= p93;
if(v93 > v84){
v93 = v84;
d93 = d84;
}
if(v93 > v83){
v93 = v83;
d93 = d83;
}
if(v93 > v82){
v93 = v82;
d93 = d82;
}
if(v93 > v94){
v93 = v94;
d93 = d94;
}
v93 += p93;
}
if(p95 != 0){
v95 -= p95;
if(v95 > v84){
v95 = v84;
d95 = d84;
}
if(v95 > v85){
v95 = v85;
d95 = d85;
}
if(v95 > v86){
v95 = v86;
d95 = d86;
}
if(v95 > v94){
v95 = v94;
d95 = d94;
}
v95 += p95;
}
if(p40 != 0){
v40 -= p40;
if(v40 > v51){
v40 = v51;
d40 = d51;
}
if(v40 > v41){
v40 = v41;
d40 = d41;
}
if(v40 > v31){
v40 = v31;
d40 = d31;
}
if(v40 > v50){
v40 = v50;
d40 = d50;
}
v40 += p40;
}
if(p60 != 0){
v60 -= p60;
if(v60 > v51){
v60 = v51;
d60 = d51;
}
if(v60 > v61){
v60 = v61;
d60 = d61;
}
if(v60 > v71){
v60 = v71;
d60 = d71;
}
if(v60 > v50){
v60 = v50;
d60 = d50;
}
v60 += p60;
}
if(p12 != 0){
v12 -= p12;
if(v12 > v23){
v12 = v23;
d12 = d23;
}
if(v12 > v22){
v12 = v22;
d12 = d22;
}
if(v12 > v13){
v12 = v13;
d12 = d13;
}
if(v12 > v21){
v12 = v21;
d12 = d21;
}
v12 += p12;
}
if(p16 != 0){
v16 -= p16;
if(v16 > v25){
v16 = v25;
d16 = d25;
}
if(v16 > v26){
v16 = v26;
d16 = d26;
}
if(v16 > v15){
v16 = v15;
d16 = d15;
}
v16 += p16;
}
if(p92 != 0){
v92 -= p92;
if(v92 > v83){
v92 = v83;
d92 = d83;
}
if(v92 > v82){
v92 = v82;
d92 = d82;
}
if(v92 > v93){
v92 = v93;
d92 = d93;
}
if(v92 > v81){
v92 = v81;
d92 = d81;
}
v92 += p92;
}
if(p96 != 0){
v96 -= p96;
if(v96 > v85){
v96 = v85;
d96 = d85;
}
if(v96 > v86){
v96 = v86;
d96 = d86;
}
if(v96 > v95){
v96 = v95;
d96 = d95;
}
v96 += p96;
}
if(p30 != 0){
v30 -= p30;
if(v30 > v41){
v30 = v41;
d30 = d41;
}
if(v30 > v31){
v30 = v31;
d30 = d31;
}
if(v30 > v40){
v30 = v40;
d30 = d40;
}
if(v30 > v21){
v30 = v21;
d30 = d21;
}
v30 += p30;
}
if(p70 != 0){
v70 -= p70;
if(v70 > v61){
v70 = v61;
d70 = d61;
}
if(v70 > v71){
v70 = v71;
d70 = d71;
}
if(v70 > v60){
v70 = v60;
d70 = d60;
}
if(v70 > v81){
v70 = v81;
d70 = d81;
}
v70 += p70;
}
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -4:
switch (dy){
case -2:
return d12;
case -1:
return d13;
case 0:
return d14;
case 1:
return d15;
case 2:
return d16;
}
break;
case -3:
switch (dy){
case -3:
return d21;
case -2:
return d22;
case -1:
return d23;
case 0:
return d24;
case 1:
return d25;
case 2:
return d26;
}
break;
case -2:
switch (dy){
case -4:
return d30;
case -3:
return d31;
case -2:
return d32;
case -1:
return d33;
case 0:
return d34;
case 1:
return d35;
case 2:
return d36;
}
break;
case -1:
switch (dy){
case -4:
return d40;
case -3:
return d41;
case -2:
return d42;
case -1:
return d43;
case 0:
return d44;
case 1:
return d45;
case 2:
return d46;
}
break;
case 0:
switch (dy){
case -4:
return d50;
case -3:
return d51;
case -2:
return d52;
case -1:
return d53;
case 0:
return d54;
case 1:
return d55;
case 2:
return d56;
}
break;
case 1:
switch (dy){
case -4:
return d60;
case -3:
return d61;
case -2:
return d62;
case -1:
return d63;
case 0:
return d64;
case 1:
return d65;
case 2:
return d66;
}
break;
case 2:
switch (dy){
case -4:
return d70;
case -3:
return d71;
case -2:
return d72;
case -1:
return d73;
case 0:
return d74;
case 1:
return d75;
case 2:
return d76;
}
break;
case 3:
switch (dy){
case -3:
return d81;
case -2:
return d82;
case -1:
return d83;
case 0:
return d84;
case 1:
return d85;
case 2:
return d86;
}
break;
case 4:
switch (dy){
case -2:
return d92;
case -1:
return d93;
case 0:
return d94;
case 1:
return d95;
case 2:
return d96;
}
break;
}
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l56.distanceSquaredTo(target)) / v56;
if(currScore > bestScore){
bestScore = currScore;
ans = d56;
}
currScore = (initialDist - l46.distanceSquaredTo(target)) / v46;
if(currScore > bestScore){
bestScore = currScore;
ans = d46;
}
currScore = (initialDist - l66.distanceSquaredTo(target)) / v66;
if(currScore > bestScore){
bestScore = currScore;
ans = d66;
}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
}
currScore = (initialDist - l21.distanceSquaredTo(target)) / v21;
if(currScore > bestScore){
bestScore = currScore;
ans = d21;
}
currScore = (initialDist - l81.distanceSquaredTo(target)) / v81;
if(currScore > bestScore){
bestScore = currScore;
ans = d81;
}
currScore = (initialDist - l14.distanceSquaredTo(target)) / v14;
if(currScore > bestScore){
bestScore = currScore;
ans = d14;
}
currScore = (initialDist - l94.distanceSquaredTo(target)) / v94;
if(currScore > bestScore){
bestScore = currScore;
ans = d94;
}
currScore = (initialDist - l50.distanceSquaredTo(target)) / v50;
if(currScore > bestScore){
bestScore = currScore;
ans = d50;
}
currScore = (initialDist - l13.distanceSquaredTo(target)) / v13;
if(currScore > bestScore){
bestScore = currScore;
ans = d13;
}
currScore = (initialDist - l15.distanceSquaredTo(target)) / v15;
if(currScore > bestScore){
bestScore = currScore;
ans = d15;
}
currScore = (initialDist - l93.distanceSquaredTo(target)) / v93;
if(currScore > bestScore){
bestScore = currScore;
ans = d93;
}
currScore = (initialDist - l95.distanceSquaredTo(target)) / v95;
if(currScore > bestScore){
bestScore = currScore;
ans = d95;
}
currScore = (initialDist - l40.distanceSquaredTo(target)) / v40;
if(currScore > bestScore){
bestScore = currScore;
ans = d40;
}
currScore = (initialDist - l60.distanceSquaredTo(target)) / v60;
if(currScore > bestScore){
bestScore = currScore;
ans = d60;
}
currScore = (initialDist - l12.distanceSquaredTo(target)) / v12;
if(currScore > bestScore){
bestScore = currScore;
ans = d12;
}
currScore = (initialDist - l16.distanceSquaredTo(target)) / v16;
if(currScore > bestScore){
bestScore = currScore;
ans = d16;
}
currScore = (initialDist - l92.distanceSquaredTo(target)) / v92;
if(currScore > bestScore){
bestScore = currScore;
ans = d92;
}
currScore = (initialDist - l96.distanceSquaredTo(target)) / v96;
if(currScore > bestScore){
bestScore = currScore;
ans = d96;
}
currScore = (initialDist - l30.distanceSquaredTo(target)) / v30;
if(currScore > bestScore){
bestScore = currScore;
ans = d30;
}
currScore = (initialDist - l70.distanceSquaredTo(target)) / v70;
if(currScore > bestScore){
bestScore = currScore;
ans = d70;
}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSEast(MapLocation target) throws GameActionException{
try{ 
	double sum;
if(p44 != 0){
if(!rc.isLocationOccupied(l44)){
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(p64 != 0){
if(!rc.isLocationOccupied(l64)){
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(p53 != 0){
if(!rc.isLocationOccupied(l53)){
v53 -= p53;
if(v53 > v54){
v53 = v54;
d53 = Direction.SOUTH;
}
if(v53 > v44){
v53 = v44;
d53 = d44;
}
if(v53 > v64){
v53 = v64;
d53 = d64;
}
v53 += p53;
}
}
if(p55 != 0){
if(!rc.isLocationOccupied(l55)){
v55 -= p55;
if(v55 > v54){
v55 = v54;
d55 = Direction.NORTH;
}
if(v55 > v44){
v55 = v44;
d55 = d44;
}
if(v55 > v64){
v55 = v64;
d55 = d64;
}
v55 += p55;
}
}
if(p43 != 0){
if(!rc.isLocationOccupied(l43)){
v43 -= p43;
if(v43 > v54){
v43 = v54;
d43 = Direction.SOUTHWEST;
}
if(v43 > v44){
v43 = v44;
d43 = d44;
}
if(v43 > v53){
v43 = v53;
d43 = d53;
}
v43 += p43;
}
}
if(p45 != 0){
if(!rc.isLocationOccupied(l45)){
v45 -= p45;
if(v45 > v54){
v45 = v54;
d45 = Direction.NORTHWEST;
}
if(v45 > v55){
v45 = v55;
d45 = d55;
}
if(v45 > v44){
v45 = v44;
d45 = d44;
}
v45 += p45;
}
}
if(p63 != 0){
if(!rc.isLocationOccupied(l63)){
v63 -= p63;
if(v63 > v54){
v63 = v54;
d63 = Direction.SOUTHEAST;
}
if(v63 > v53){
v63 = v53;
d63 = d53;
}
if(v63 > v64){
v63 = v64;
d63 = d64;
}
v63 += p63;
}
}
if(p65 != 0){
if(!rc.isLocationOccupied(l65)){
v65 -= p65;
if(v65 > v54){
v65 = v54;
d65 = Direction.NORTHEAST;
}
if(v65 > v55){
v65 = v55;
d65 = d55;
}
if(v65 > v64){
v65 = v64;
d65 = d64;
}
v65 += p65;
}
}
if(p34 != 0){
v34 -= p34;
if(v34 > v44){
v34 = v44;
d34 = d44;
}
if(v34 > v45){
v34 = v45;
d34 = d45;
}
if(v34 > v43){
v34 = v43;
d34 = d43;
}
v34 += p34;
}
if(p74 != 0){
v74 -= p74;
if(v74 > v64){
v74 = v64;
d74 = d64;
}
if(v74 > v63){
v74 = v63;
d74 = d63;
}
if(v74 > v65){
v74 = v65;
d74 = d65;
}
v74 += p74;
}
if(p52 != 0){
v52 -= p52;
if(v52 > v53){
v52 = v53;
d52 = d53;
}
if(v52 > v43){
v52 = v43;
d52 = d43;
}
if(v52 > v63){
v52 = v63;
d52 = d63;
}
v52 += p52;
}
if(p56 != 0){
v56 -= p56;
if(v56 > v55){
v56 = v55;
d56 = d55;
}
if(v56 > v45){
v56 = v45;
d56 = d45;
}
if(v56 > v65){
v56 = v65;
d56 = d65;
}
v56 += p56;
}
if(p33 != 0){
v33 -= p33;
if(v33 > v44){
v33 = v44;
d33 = d44;
}
if(v33 > v43){
v33 = v43;
d33 = d43;
}
if(v33 > v34){
v33 = v34;
d33 = d34;
}
v33 += p33;
}
if(p35 != 0){
v35 -= p35;
if(v35 > v44){
v35 = v44;
d35 = d44;
}
if(v35 > v45){
v35 = v45;
d35 = d45;
}
if(v35 > v34){
v35 = v34;
d35 = d34;
}
v35 += p35;
}
if(p73 != 0){
v73 -= p73;
if(v73 > v64){
v73 = v64;
d73 = d64;
}
if(v73 > v63){
v73 = v63;
d73 = d63;
}
if(v73 > v74){
v73 = v74;
d73 = d74;
}
v73 += p73;
}
if(p75 != 0){
v75 -= p75;
if(v75 > v64){
v75 = v64;
d75 = d64;
}
if(v75 > v65){
v75 = v65;
d75 = d65;
}
if(v75 > v74){
v75 = v74;
d75 = d74;
}
v75 += p75;
}
if(p42 != 0){
v42 -= p42;
if(v42 > v53){
v42 = v53;
d42 = d53;
}
if(v42 > v43){
v42 = v43;
d42 = d43;
}
if(v42 > v52){
v42 = v52;
d42 = d52;
}
if(v42 > v33){
v42 = v33;
d42 = d33;
}
v42 += p42;
}
if(p46 != 0){
v46 -= p46;
if(v46 > v55){
v46 = v55;
d46 = d55;
}
if(v46 > v45){
v46 = v45;
d46 = d45;
}
if(v46 > v56){
v46 = v56;
d46 = d56;
}
if(v46 > v35){
v46 = v35;
d46 = d35;
}
v46 += p46;
}
if(p62 != 0){
v62 -= p62;
if(v62 > v53){
v62 = v53;
d62 = d53;
}
if(v62 > v63){
v62 = v63;
d62 = d63;
}
if(v62 > v52){
v62 = v52;
d62 = d52;
}
if(v62 > v73){
v62 = v73;
d62 = d73;
}
v62 += p62;
}
if(p66 != 0){
v66 -= p66;
if(v66 > v55){
v66 = v55;
d66 = d55;
}
if(v66 > v65){
v66 = v65;
d66 = d65;
}
if(v66 > v56){
v66 = v56;
d66 = d56;
}
if(v66 > v75){
v66 = v75;
d66 = d75;
}
v66 += p66;
}
if(p32 != 0){
v32 -= p32;
if(v32 > v43){
v32 = v43;
d32 = d43;
}
if(v32 > v33){
v32 = v33;
d32 = d33;
}
if(v32 > v42){
v32 = v42;
d32 = d42;
}
v32 += p32;
}
if(p36 != 0){
v36 -= p36;
if(v36 > v45){
v36 = v45;
d36 = d45;
}
if(v36 > v46){
v36 = v46;
d36 = d46;
}
if(v36 > v35){
v36 = v35;
d36 = d35;
}
v36 += p36;
}
if(p72 != 0){
v72 -= p72;
if(v72 > v63){
v72 = v63;
d72 = d63;
}
if(v72 > v62){
v72 = v62;
d72 = d62;
}
if(v72 > v73){
v72 = v73;
d72 = d73;
}
v72 += p72;
}
if(p76 != 0){
v76 -= p76;
if(v76 > v65){
v76 = v65;
d76 = d65;
}
if(v76 > v66){
v76 = v66;
d76 = d66;
}
if(v76 > v75){
v76 = v75;
d76 = d75;
}
v76 += p76;
}
if(p84 != 0){
v84 -= p84;
if(v84 > v74){
v84 = v74;
d84 = d74;
}
if(v84 > v73){
v84 = v73;
d84 = d73;
}
if(v84 > v75){
v84 = v75;
d84 = d75;
}
v84 += p84;
}
if(p51 != 0){
v51 -= p51;
if(v51 > v52){
v51 = v52;
d51 = d52;
}
if(v51 > v42){
v51 = v42;
d51 = d42;
}
if(v51 > v62){
v51 = v62;
d51 = d62;
}
v51 += p51;
}
if(p57 != 0){
v57 -= p57;
if(v57 > v56){
v57 = v56;
d57 = d56;
}
if(v57 > v46){
v57 = v46;
d57 = d46;
}
if(v57 > v66){
v57 = v66;
d57 = d66;
}
v57 += p57;
}
if(p83 != 0){
v83 -= p83;
if(v83 > v74){
v83 = v74;
d83 = d74;
}
if(v83 > v73){
v83 = v73;
d83 = d73;
}
if(v83 > v72){
v83 = v72;
d83 = d72;
}
if(v83 > v84){
v83 = v84;
d83 = d84;
}
v83 += p83;
}
if(p85 != 0){
v85 -= p85;
if(v85 > v74){
v85 = v74;
d85 = d74;
}
if(v85 > v75){
v85 = v75;
d85 = d75;
}
if(v85 > v76){
v85 = v76;
d85 = d76;
}
if(v85 > v84){
v85 = v84;
d85 = d84;
}
v85 += p85;
}
if(p41 != 0){
v41 -= p41;
if(v41 > v52){
v41 = v52;
d41 = d52;
}
if(v41 > v42){
v41 = v42;
d41 = d42;
}
if(v41 > v32){
v41 = v32;
d41 = d32;
}
if(v41 > v51){
v41 = v51;
d41 = d51;
}
v41 += p41;
}
if(p47 != 0){
v47 -= p47;
if(v47 > v56){
v47 = v56;
d47 = d56;
}
if(v47 > v46){
v47 = v46;
d47 = d46;
}
if(v47 > v36){
v47 = v36;
d47 = d36;
}
if(v47 > v57){
v47 = v57;
d47 = d57;
}
v47 += p47;
}
if(p61 != 0){
v61 -= p61;
if(v61 > v52){
v61 = v52;
d61 = d52;
}
if(v61 > v62){
v61 = v62;
d61 = d62;
}
if(v61 > v72){
v61 = v72;
d61 = d72;
}
if(v61 > v51){
v61 = v51;
d61 = d51;
}
v61 += p61;
}
if(p67 != 0){
v67 -= p67;
if(v67 > v56){
v67 = v56;
d67 = d56;
}
if(v67 > v66){
v67 = v66;
d67 = d66;
}
if(v67 > v76){
v67 = v76;
d67 = d76;
}
if(v67 > v57){
v67 = v57;
d67 = d57;
}
v67 += p67;
}
if(p82 != 0){
v82 -= p82;
if(v82 > v73){
v82 = v73;
d82 = d73;
}
if(v82 > v72){
v82 = v72;
d82 = d72;
}
if(v82 > v83){
v82 = v83;
d82 = d83;
}
v82 += p82;
}
if(p86 != 0){
v86 -= p86;
if(v86 > v75){
v86 = v75;
d86 = d75;
}
if(v86 > v76){
v86 = v76;
d86 = d76;
}
if(v86 > v85){
v86 = v85;
d86 = d85;
}
v86 += p86;
}
if(p31 != 0){
v31 -= p31;
if(v31 > v42){
v31 = v42;
d31 = d42;
}
if(v31 > v32){
v31 = v32;
d31 = d32;
}
if(v31 > v41){
v31 = v41;
d31 = d41;
}
v31 += p31;
}
if(p37 != 0){
v37 -= p37;
if(v37 > v46){
v37 = v46;
d37 = d46;
}
if(v37 > v36){
v37 = v36;
d37 = d36;
}
if(v37 > v47){
v37 = v47;
d37 = d47;
}
v37 += p37;
}
if(p71 != 0){
v71 -= p71;
if(v71 > v62){
v71 = v62;
d71 = d62;
}
if(v71 > v72){
v71 = v72;
d71 = d72;
}
if(v71 > v61){
v71 = v61;
d71 = d61;
}
if(v71 > v82){
v71 = v82;
d71 = d82;
}
v71 += p71;
}
if(p77 != 0){
v77 -= p77;
if(v77 > v66){
v77 = v66;
d77 = d66;
}
if(v77 > v76){
v77 = v76;
d77 = d76;
}
if(v77 > v67){
v77 = v67;
d77 = d67;
}
if(v77 > v86){
v77 = v86;
d77 = d86;
}
v77 += p77;
}
if(p81 != 0){
v81 -= p81;
if(v81 > v72){
v81 = v72;
d81 = d72;
}
if(v81 > v71){
v81 = v71;
d81 = d71;
}
if(v81 > v82){
v81 = v82;
d81 = d82;
}
v81 += p81;
}
if(p87 != 0){
v87 -= p87;
if(v87 > v76){
v87 = v76;
d87 = d76;
}
if(v87 > v77){
v87 = v77;
d87 = d77;
}
if(v87 > v86){
v87 = v86;
d87 = d86;
}
v87 += p87;
}
if(p94 != 0){
v94 -= p94;
if(v94 > v84){
v94 = v84;
d94 = d84;
}
if(v94 > v83){
v94 = v83;
d94 = d83;
}
if(v94 > v85){
v94 = v85;
d94 = d85;
}
v94 += p94;
}
if(p50 != 0){
v50 -= p50;
if(v50 > v51){
v50 = v51;
d50 = d51;
}
if(v50 > v41){
v50 = v41;
d50 = d41;
}
if(v50 > v61){
v50 = v61;
d50 = d61;
}
v50 += p50;
}
if(p58 != 0){
v58 -= p58;
if(v58 > v57){
v58 = v57;
d58 = d57;
}
if(v58 > v47){
v58 = v47;
d58 = d47;
}
if(v58 > v67){
v58 = v67;
d58 = d67;
}
v58 += p58;
}
if(p93 != 0){
v93 -= p93;
if(v93 > v84){
v93 = v84;
d93 = d84;
}
if(v93 > v83){
v93 = v83;
d93 = d83;
}
if(v93 > v82){
v93 = v82;
d93 = d82;
}
if(v93 > v94){
v93 = v94;
d93 = d94;
}
v93 += p93;
}
if(p95 != 0){
v95 -= p95;
if(v95 > v84){
v95 = v84;
d95 = d84;
}
if(v95 > v85){
v95 = v85;
d95 = d85;
}
if(v95 > v86){
v95 = v86;
d95 = d86;
}
if(v95 > v94){
v95 = v94;
d95 = d94;
}
v95 += p95;
}
if(p40 != 0){
v40 -= p40;
if(v40 > v51){
v40 = v51;
d40 = d51;
}
if(v40 > v41){
v40 = v41;
d40 = d41;
}
if(v40 > v31){
v40 = v31;
d40 = d31;
}
if(v40 > v50){
v40 = v50;
d40 = d50;
}
v40 += p40;
}
if(p48 != 0){
v48 -= p48;
if(v48 > v57){
v48 = v57;
d48 = d57;
}
if(v48 > v47){
v48 = v47;
d48 = d47;
}
if(v48 > v37){
v48 = v37;
d48 = d37;
}
if(v48 > v58){
v48 = v58;
d48 = d58;
}
v48 += p48;
}
if(p60 != 0){
v60 -= p60;
if(v60 > v51){
v60 = v51;
d60 = d51;
}
if(v60 > v61){
v60 = v61;
d60 = d61;
}
if(v60 > v71){
v60 = v71;
d60 = d71;
}
if(v60 > v50){
v60 = v50;
d60 = d50;
}
v60 += p60;
}
if(p68 != 0){
v68 -= p68;
if(v68 > v57){
v68 = v57;
d68 = d57;
}
if(v68 > v67){
v68 = v67;
d68 = d67;
}
if(v68 > v77){
v68 = v77;
d68 = d77;
}
if(v68 > v58){
v68 = v58;
d68 = d58;
}
v68 += p68;
}
if(p92 != 0){
v92 -= p92;
if(v92 > v83){
v92 = v83;
d92 = d83;
}
if(v92 > v82){
v92 = v82;
d92 = d82;
}
if(v92 > v93){
v92 = v93;
d92 = d93;
}
if(v92 > v81){
v92 = v81;
d92 = d81;
}
v92 += p92;
}
if(p96 != 0){
v96 -= p96;
if(v96 > v85){
v96 = v85;
d96 = d85;
}
if(v96 > v86){
v96 = v86;
d96 = d86;
}
if(v96 > v95){
v96 = v95;
d96 = d95;
}
if(v96 > v87){
v96 = v87;
d96 = d87;
}
v96 += p96;
}
if(p30 != 0){
v30 -= p30;
if(v30 > v41){
v30 = v41;
d30 = d41;
}
if(v30 > v31){
v30 = v31;
d30 = d31;
}
if(v30 > v40){
v30 = v40;
d30 = d40;
}
v30 += p30;
}
if(p38 != 0){
v38 -= p38;
if(v38 > v47){
v38 = v47;
d38 = d47;
}
if(v38 > v37){
v38 = v37;
d38 = d37;
}
if(v38 > v48){
v38 = v48;
d38 = d48;
}
v38 += p38;
}
if(p70 != 0){
v70 -= p70;
if(v70 > v61){
v70 = v61;
d70 = d61;
}
if(v70 > v71){
v70 = v71;
d70 = d71;
}
if(v70 > v60){
v70 = v60;
d70 = d60;
}
if(v70 > v81){
v70 = v81;
d70 = d81;
}
v70 += p70;
}
if(p78 != 0){
v78 -= p78;
if(v78 > v67){
v78 = v67;
d78 = d67;
}
if(v78 > v77){
v78 = v77;
d78 = d77;
}
if(v78 > v68){
v78 = v68;
d78 = d68;
}
if(v78 > v87){
v78 = v87;
d78 = d87;
}
v78 += p78;
}
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -2:
switch (dy){
case -4:
return d30;
case -3:
return d31;
case -2:
return d32;
case -1:
return d33;
case 0:
return d34;
case 1:
return d35;
case 2:
return d36;
case 3:
return d37;
case 4:
return d38;
}
break;
case -1:
switch (dy){
case -4:
return d40;
case -3:
return d41;
case -2:
return d42;
case -1:
return d43;
case 0:
return d44;
case 1:
return d45;
case 2:
return d46;
case 3:
return d47;
case 4:
return d48;
}
break;
case 0:
switch (dy){
case -4:
return d50;
case -3:
return d51;
case -2:
return d52;
case -1:
return d53;
case 0:
return d54;
case 1:
return d55;
case 2:
return d56;
case 3:
return d57;
case 4:
return d58;
}
break;
case 1:
switch (dy){
case -4:
return d60;
case -3:
return d61;
case -2:
return d62;
case -1:
return d63;
case 0:
return d64;
case 1:
return d65;
case 2:
return d66;
case 3:
return d67;
case 4:
return d68;
}
break;
case 2:
switch (dy){
case -4:
return d70;
case -3:
return d71;
case -2:
return d72;
case -1:
return d73;
case 0:
return d74;
case 1:
return d75;
case 2:
return d76;
case 3:
return d77;
case 4:
return d78;
}
break;
case 3:
switch (dy){
case -3:
return d81;
case -2:
return d82;
case -1:
return d83;
case 0:
return d84;
case 1:
return d85;
case 2:
return d86;
case 3:
return d87;
}
break;
case 4:
switch (dy){
case -2:
return d92;
case -1:
return d93;
case 0:
return d94;
case 1:
return d95;
case 2:
return d96;
}
break;
}
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l34.distanceSquaredTo(target)) / v34;
if(currScore > bestScore){
bestScore = currScore;
ans = d34;
}
currScore = (initialDist - l33.distanceSquaredTo(target)) / v33;
if(currScore > bestScore){
bestScore = currScore;
ans = d33;
}
currScore = (initialDist - l35.distanceSquaredTo(target)) / v35;
if(currScore > bestScore){
bestScore = currScore;
ans = d35;
}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
}
currScore = (initialDist - l81.distanceSquaredTo(target)) / v81;
if(currScore > bestScore){
bestScore = currScore;
ans = d81;
}
currScore = (initialDist - l87.distanceSquaredTo(target)) / v87;
if(currScore > bestScore){
bestScore = currScore;
ans = d87;
}
currScore = (initialDist - l94.distanceSquaredTo(target)) / v94;
if(currScore > bestScore){
bestScore = currScore;
ans = d94;
}
currScore = (initialDist - l50.distanceSquaredTo(target)) / v50;
if(currScore > bestScore){
bestScore = currScore;
ans = d50;
}
currScore = (initialDist - l58.distanceSquaredTo(target)) / v58;
if(currScore > bestScore){
bestScore = currScore;
ans = d58;
}
currScore = (initialDist - l93.distanceSquaredTo(target)) / v93;
if(currScore > bestScore){
bestScore = currScore;
ans = d93;
}
currScore = (initialDist - l95.distanceSquaredTo(target)) / v95;
if(currScore > bestScore){
bestScore = currScore;
ans = d95;
}
currScore = (initialDist - l40.distanceSquaredTo(target)) / v40;
if(currScore > bestScore){
bestScore = currScore;
ans = d40;
}
currScore = (initialDist - l48.distanceSquaredTo(target)) / v48;
if(currScore > bestScore){
bestScore = currScore;
ans = d48;
}
currScore = (initialDist - l60.distanceSquaredTo(target)) / v60;
if(currScore > bestScore){
bestScore = currScore;
ans = d60;
}
currScore = (initialDist - l68.distanceSquaredTo(target)) / v68;
if(currScore > bestScore){
bestScore = currScore;
ans = d68;
}
currScore = (initialDist - l92.distanceSquaredTo(target)) / v92;
if(currScore > bestScore){
bestScore = currScore;
ans = d92;
}
currScore = (initialDist - l96.distanceSquaredTo(target)) / v96;
if(currScore > bestScore){
bestScore = currScore;
ans = d96;
}
currScore = (initialDist - l30.distanceSquaredTo(target)) / v30;
if(currScore > bestScore){
bestScore = currScore;
ans = d30;
}
currScore = (initialDist - l38.distanceSquaredTo(target)) / v38;
if(currScore > bestScore){
bestScore = currScore;
ans = d38;
}
currScore = (initialDist - l70.distanceSquaredTo(target)) / v70;
if(currScore > bestScore){
bestScore = currScore;
ans = d70;
}
currScore = (initialDist - l78.distanceSquaredTo(target)) / v78;
if(currScore > bestScore){
bestScore = currScore;
ans = d78;
}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSWest(MapLocation target) throws GameActionException{
try{ 
	double sum;
if(p44 != 0){
if(!rc.isLocationOccupied(l44)){
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(p64 != 0){
if(!rc.isLocationOccupied(l64)){
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(p53 != 0){
if(!rc.isLocationOccupied(l53)){
v53 -= p53;
if(v53 > v54){
v53 = v54;
d53 = Direction.SOUTH;
}
if(v53 > v44){
v53 = v44;
d53 = d44;
}
if(v53 > v64){
v53 = v64;
d53 = d64;
}
v53 += p53;
}
}
if(p55 != 0){
if(!rc.isLocationOccupied(l55)){
v55 -= p55;
if(v55 > v54){
v55 = v54;
d55 = Direction.NORTH;
}
if(v55 > v44){
v55 = v44;
d55 = d44;
}
if(v55 > v64){
v55 = v64;
d55 = d64;
}
v55 += p55;
}
}
if(p43 != 0){
if(!rc.isLocationOccupied(l43)){
v43 -= p43;
if(v43 > v54){
v43 = v54;
d43 = Direction.SOUTHWEST;
}
if(v43 > v44){
v43 = v44;
d43 = d44;
}
if(v43 > v53){
v43 = v53;
d43 = d53;
}
v43 += p43;
}
}
if(p45 != 0){
if(!rc.isLocationOccupied(l45)){
v45 -= p45;
if(v45 > v54){
v45 = v54;
d45 = Direction.NORTHWEST;
}
if(v45 > v55){
v45 = v55;
d45 = d55;
}
if(v45 > v44){
v45 = v44;
d45 = d44;
}
v45 += p45;
}
}
if(p63 != 0){
if(!rc.isLocationOccupied(l63)){
v63 -= p63;
if(v63 > v54){
v63 = v54;
d63 = Direction.SOUTHEAST;
}
if(v63 > v53){
v63 = v53;
d63 = d53;
}
if(v63 > v64){
v63 = v64;
d63 = d64;
}
v63 += p63;
}
}
if(p65 != 0){
if(!rc.isLocationOccupied(l65)){
v65 -= p65;
if(v65 > v54){
v65 = v54;
d65 = Direction.NORTHEAST;
}
if(v65 > v55){
v65 = v55;
d65 = d55;
}
if(v65 > v64){
v65 = v64;
d65 = d64;
}
v65 += p65;
}
}
if(p34 != 0){
v34 -= p34;
if(v34 > v44){
v34 = v44;
d34 = d44;
}
if(v34 > v45){
v34 = v45;
d34 = d45;
}
if(v34 > v43){
v34 = v43;
d34 = d43;
}
v34 += p34;
}
if(p74 != 0){
v74 -= p74;
if(v74 > v64){
v74 = v64;
d74 = d64;
}
if(v74 > v63){
v74 = v63;
d74 = d63;
}
if(v74 > v65){
v74 = v65;
d74 = d65;
}
v74 += p74;
}
if(p52 != 0){
v52 -= p52;
if(v52 > v53){
v52 = v53;
d52 = d53;
}
if(v52 > v43){
v52 = v43;
d52 = d43;
}
if(v52 > v63){
v52 = v63;
d52 = d63;
}
v52 += p52;
}
if(p56 != 0){
v56 -= p56;
if(v56 > v55){
v56 = v55;
d56 = d55;
}
if(v56 > v45){
v56 = v45;
d56 = d45;
}
if(v56 > v65){
v56 = v65;
d56 = d65;
}
v56 += p56;
}
if(p33 != 0){
v33 -= p33;
if(v33 > v44){
v33 = v44;
d33 = d44;
}
if(v33 > v43){
v33 = v43;
d33 = d43;
}
if(v33 > v34){
v33 = v34;
d33 = d34;
}
v33 += p33;
}
if(p35 != 0){
v35 -= p35;
if(v35 > v44){
v35 = v44;
d35 = d44;
}
if(v35 > v45){
v35 = v45;
d35 = d45;
}
if(v35 > v34){
v35 = v34;
d35 = d34;
}
v35 += p35;
}
if(p73 != 0){
v73 -= p73;
if(v73 > v64){
v73 = v64;
d73 = d64;
}
if(v73 > v63){
v73 = v63;
d73 = d63;
}
if(v73 > v74){
v73 = v74;
d73 = d74;
}
v73 += p73;
}
if(p75 != 0){
v75 -= p75;
if(v75 > v64){
v75 = v64;
d75 = d64;
}
if(v75 > v65){
v75 = v65;
d75 = d65;
}
if(v75 > v74){
v75 = v74;
d75 = d74;
}
v75 += p75;
}
if(p42 != 0){
v42 -= p42;
if(v42 > v53){
v42 = v53;
d42 = d53;
}
if(v42 > v43){
v42 = v43;
d42 = d43;
}
if(v42 > v52){
v42 = v52;
d42 = d52;
}
if(v42 > v33){
v42 = v33;
d42 = d33;
}
v42 += p42;
}
if(p46 != 0){
v46 -= p46;
if(v46 > v55){
v46 = v55;
d46 = d55;
}
if(v46 > v45){
v46 = v45;
d46 = d45;
}
if(v46 > v56){
v46 = v56;
d46 = d56;
}
if(v46 > v35){
v46 = v35;
d46 = d35;
}
v46 += p46;
}
if(p62 != 0){
v62 -= p62;
if(v62 > v53){
v62 = v53;
d62 = d53;
}
if(v62 > v63){
v62 = v63;
d62 = d63;
}
if(v62 > v52){
v62 = v52;
d62 = d52;
}
if(v62 > v73){
v62 = v73;
d62 = d73;
}
v62 += p62;
}
if(p66 != 0){
v66 -= p66;
if(v66 > v55){
v66 = v55;
d66 = d55;
}
if(v66 > v65){
v66 = v65;
d66 = d65;
}
if(v66 > v56){
v66 = v56;
d66 = d56;
}
if(v66 > v75){
v66 = v75;
d66 = d75;
}
v66 += p66;
}
if(p32 != 0){
v32 -= p32;
if(v32 > v43){
v32 = v43;
d32 = d43;
}
if(v32 > v33){
v32 = v33;
d32 = d33;
}
if(v32 > v42){
v32 = v42;
d32 = d42;
}
v32 += p32;
}
if(p36 != 0){
v36 -= p36;
if(v36 > v45){
v36 = v45;
d36 = d45;
}
if(v36 > v46){
v36 = v46;
d36 = d46;
}
if(v36 > v35){
v36 = v35;
d36 = d35;
}
v36 += p36;
}
if(p72 != 0){
v72 -= p72;
if(v72 > v63){
v72 = v63;
d72 = d63;
}
if(v72 > v62){
v72 = v62;
d72 = d62;
}
if(v72 > v73){
v72 = v73;
d72 = d73;
}
v72 += p72;
}
if(p76 != 0){
v76 -= p76;
if(v76 > v65){
v76 = v65;
d76 = d65;
}
if(v76 > v66){
v76 = v66;
d76 = d66;
}
if(v76 > v75){
v76 = v75;
d76 = d75;
}
v76 += p76;
}
if(p24 != 0){
v24 -= p24;
if(v24 > v34){
v24 = v34;
d24 = d34;
}
if(v24 > v35){
v24 = v35;
d24 = d35;
}
if(v24 > v33){
v24 = v33;
d24 = d33;
}
v24 += p24;
}
if(p51 != 0){
v51 -= p51;
if(v51 > v52){
v51 = v52;
d51 = d52;
}
if(v51 > v42){
v51 = v42;
d51 = d42;
}
if(v51 > v62){
v51 = v62;
d51 = d62;
}
v51 += p51;
}
if(p57 != 0){
v57 -= p57;
if(v57 > v56){
v57 = v56;
d57 = d56;
}
if(v57 > v46){
v57 = v46;
d57 = d46;
}
if(v57 > v66){
v57 = v66;
d57 = d66;
}
v57 += p57;
}
if(p23 != 0){
v23 -= p23;
if(v23 > v34){
v23 = v34;
d23 = d34;
}
if(v23 > v33){
v23 = v33;
d23 = d33;
}
if(v23 > v32){
v23 = v32;
d23 = d32;
}
if(v23 > v24){
v23 = v24;
d23 = d24;
}
v23 += p23;
}
if(p25 != 0){
v25 -= p25;
if(v25 > v34){
v25 = v34;
d25 = d34;
}
if(v25 > v35){
v25 = v35;
d25 = d35;
}
if(v25 > v36){
v25 = v36;
d25 = d36;
}
if(v25 > v24){
v25 = v24;
d25 = d24;
}
v25 += p25;
}
if(p41 != 0){
v41 -= p41;
if(v41 > v52){
v41 = v52;
d41 = d52;
}
if(v41 > v42){
v41 = v42;
d41 = d42;
}
if(v41 > v32){
v41 = v32;
d41 = d32;
}
if(v41 > v51){
v41 = v51;
d41 = d51;
}
v41 += p41;
}
if(p47 != 0){
v47 -= p47;
if(v47 > v56){
v47 = v56;
d47 = d56;
}
if(v47 > v46){
v47 = v46;
d47 = d46;
}
if(v47 > v36){
v47 = v36;
d47 = d36;
}
if(v47 > v57){
v47 = v57;
d47 = d57;
}
v47 += p47;
}
if(p61 != 0){
v61 -= p61;
if(v61 > v52){
v61 = v52;
d61 = d52;
}
if(v61 > v62){
v61 = v62;
d61 = d62;
}
if(v61 > v72){
v61 = v72;
d61 = d72;
}
if(v61 > v51){
v61 = v51;
d61 = d51;
}
v61 += p61;
}
if(p67 != 0){
v67 -= p67;
if(v67 > v56){
v67 = v56;
d67 = d56;
}
if(v67 > v66){
v67 = v66;
d67 = d66;
}
if(v67 > v76){
v67 = v76;
d67 = d76;
}
if(v67 > v57){
v67 = v57;
d67 = d57;
}
v67 += p67;
}
if(p22 != 0){
v22 -= p22;
if(v22 > v33){
v22 = v33;
d22 = d33;
}
if(v22 > v32){
v22 = v32;
d22 = d32;
}
if(v22 > v23){
v22 = v23;
d22 = d23;
}
v22 += p22;
}
if(p26 != 0){
v26 -= p26;
if(v26 > v35){
v26 = v35;
d26 = d35;
}
if(v26 > v36){
v26 = v36;
d26 = d36;
}
if(v26 > v25){
v26 = v25;
d26 = d25;
}
v26 += p26;
}
if(p31 != 0){
v31 -= p31;
if(v31 > v42){
v31 = v42;
d31 = d42;
}
if(v31 > v32){
v31 = v32;
d31 = d32;
}
if(v31 > v41){
v31 = v41;
d31 = d41;
}
if(v31 > v22){
v31 = v22;
d31 = d22;
}
v31 += p31;
}
if(p37 != 0){
v37 -= p37;
if(v37 > v46){
v37 = v46;
d37 = d46;
}
if(v37 > v36){
v37 = v36;
d37 = d36;
}
if(v37 > v47){
v37 = v47;
d37 = d47;
}
if(v37 > v26){
v37 = v26;
d37 = d26;
}
v37 += p37;
}
if(p71 != 0){
v71 -= p71;
if(v71 > v62){
v71 = v62;
d71 = d62;
}
if(v71 > v72){
v71 = v72;
d71 = d72;
}
if(v71 > v61){
v71 = v61;
d71 = d61;
}
v71 += p71;
}
if(p77 != 0){
v77 -= p77;
if(v77 > v66){
v77 = v66;
d77 = d66;
}
if(v77 > v76){
v77 = v76;
d77 = d76;
}
if(v77 > v67){
v77 = v67;
d77 = d67;
}
v77 += p77;
}
if(p21 != 0){
v21 -= p21;
if(v21 > v32){
v21 = v32;
d21 = d32;
}
if(v21 > v22){
v21 = v22;
d21 = d22;
}
if(v21 > v31){
v21 = v31;
d21 = d31;
}
v21 += p21;
}
if(p27 != 0){
v27 -= p27;
if(v27 > v36){
v27 = v36;
d27 = d36;
}
if(v27 > v37){
v27 = v37;
d27 = d37;
}
if(v27 > v26){
v27 = v26;
d27 = d26;
}
v27 += p27;
}
if(p14 != 0){
v14 -= p14;
if(v14 > v24){
v14 = v24;
d14 = d24;
}
if(v14 > v25){
v14 = v25;
d14 = d25;
}
if(v14 > v23){
v14 = v23;
d14 = d23;
}
v14 += p14;
}
if(p50 != 0){
v50 -= p50;
if(v50 > v51){
v50 = v51;
d50 = d51;
}
if(v50 > v41){
v50 = v41;
d50 = d41;
}
if(v50 > v61){
v50 = v61;
d50 = d61;
}
v50 += p50;
}
if(p58 != 0){
v58 -= p58;
if(v58 > v57){
v58 = v57;
d58 = d57;
}
if(v58 > v47){
v58 = v47;
d58 = d47;
}
if(v58 > v67){
v58 = v67;
d58 = d67;
}
v58 += p58;
}
if(p13 != 0){
v13 -= p13;
if(v13 > v24){
v13 = v24;
d13 = d24;
}
if(v13 > v23){
v13 = v23;
d13 = d23;
}
if(v13 > v22){
v13 = v22;
d13 = d22;
}
if(v13 > v14){
v13 = v14;
d13 = d14;
}
v13 += p13;
}
if(p15 != 0){
v15 -= p15;
if(v15 > v24){
v15 = v24;
d15 = d24;
}
if(v15 > v25){
v15 = v25;
d15 = d25;
}
if(v15 > v26){
v15 = v26;
d15 = d26;
}
if(v15 > v14){
v15 = v14;
d15 = d14;
}
v15 += p15;
}
if(p40 != 0){
v40 -= p40;
if(v40 > v51){
v40 = v51;
d40 = d51;
}
if(v40 > v41){
v40 = v41;
d40 = d41;
}
if(v40 > v31){
v40 = v31;
d40 = d31;
}
if(v40 > v50){
v40 = v50;
d40 = d50;
}
v40 += p40;
}
if(p48 != 0){
v48 -= p48;
if(v48 > v57){
v48 = v57;
d48 = d57;
}
if(v48 > v47){
v48 = v47;
d48 = d47;
}
if(v48 > v37){
v48 = v37;
d48 = d37;
}
if(v48 > v58){
v48 = v58;
d48 = d58;
}
v48 += p48;
}
if(p60 != 0){
v60 -= p60;
if(v60 > v51){
v60 = v51;
d60 = d51;
}
if(v60 > v61){
v60 = v61;
d60 = d61;
}
if(v60 > v71){
v60 = v71;
d60 = d71;
}
if(v60 > v50){
v60 = v50;
d60 = d50;
}
v60 += p60;
}
if(p68 != 0){
v68 -= p68;
if(v68 > v57){
v68 = v57;
d68 = d57;
}
if(v68 > v67){
v68 = v67;
d68 = d67;
}
if(v68 > v77){
v68 = v77;
d68 = d77;
}
if(v68 > v58){
v68 = v58;
d68 = d58;
}
v68 += p68;
}
if(p12 != 0){
v12 -= p12;
if(v12 > v23){
v12 = v23;
d12 = d23;
}
if(v12 > v22){
v12 = v22;
d12 = d22;
}
if(v12 > v13){
v12 = v13;
d12 = d13;
}
if(v12 > v21){
v12 = v21;
d12 = d21;
}
v12 += p12;
}
if(p16 != 0){
v16 -= p16;
if(v16 > v25){
v16 = v25;
d16 = d25;
}
if(v16 > v26){
v16 = v26;
d16 = d26;
}
if(v16 > v15){
v16 = v15;
d16 = d15;
}
if(v16 > v27){
v16 = v27;
d16 = d27;
}
v16 += p16;
}
if(p30 != 0){
v30 -= p30;
if(v30 > v41){
v30 = v41;
d30 = d41;
}
if(v30 > v31){
v30 = v31;
d30 = d31;
}
if(v30 > v40){
v30 = v40;
d30 = d40;
}
if(v30 > v21){
v30 = v21;
d30 = d21;
}
v30 += p30;
}
if(p38 != 0){
v38 -= p38;
if(v38 > v47){
v38 = v47;
d38 = d47;
}
if(v38 > v37){
v38 = v37;
d38 = d37;
}
if(v38 > v48){
v38 = v48;
d38 = d48;
}
if(v38 > v27){
v38 = v27;
d38 = d27;
}
v38 += p38;
}
if(p70 != 0){
v70 -= p70;
if(v70 > v61){
v70 = v61;
d70 = d61;
}
if(v70 > v71){
v70 = v71;
d70 = d71;
}
if(v70 > v60){
v70 = v60;
d70 = d60;
}
v70 += p70;
}
if(p78 != 0){
v78 -= p78;
if(v78 > v67){
v78 = v67;
d78 = d67;
}
if(v78 > v77){
v78 = v77;
d78 = d77;
}
if(v78 > v68){
v78 = v68;
d78 = d68;
}
v78 += p78;
}
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -4:
switch (dy){
case -2:
return d12;
case -1:
return d13;
case 0:
return d14;
case 1:
return d15;
case 2:
return d16;
}
break;
case -3:
switch (dy){
case -3:
return d21;
case -2:
return d22;
case -1:
return d23;
case 0:
return d24;
case 1:
return d25;
case 2:
return d26;
case 3:
return d27;
}
break;
case -2:
switch (dy){
case -4:
return d30;
case -3:
return d31;
case -2:
return d32;
case -1:
return d33;
case 0:
return d34;
case 1:
return d35;
case 2:
return d36;
case 3:
return d37;
case 4:
return d38;
}
break;
case -1:
switch (dy){
case -4:
return d40;
case -3:
return d41;
case -2:
return d42;
case -1:
return d43;
case 0:
return d44;
case 1:
return d45;
case 2:
return d46;
case 3:
return d47;
case 4:
return d48;
}
break;
case 0:
switch (dy){
case -4:
return d50;
case -3:
return d51;
case -2:
return d52;
case -1:
return d53;
case 0:
return d54;
case 1:
return d55;
case 2:
return d56;
case 3:
return d57;
case 4:
return d58;
}
break;
case 1:
switch (dy){
case -4:
return d60;
case -3:
return d61;
case -2:
return d62;
case -1:
return d63;
case 0:
return d64;
case 1:
return d65;
case 2:
return d66;
case 3:
return d67;
case 4:
return d68;
}
break;
case 2:
switch (dy){
case -4:
return d70;
case -3:
return d71;
case -2:
return d72;
case -1:
return d73;
case 0:
return d74;
case 1:
return d75;
case 2:
return d76;
case 3:
return d77;
case 4:
return d78;
}
break;
}
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l74.distanceSquaredTo(target)) / v74;
if(currScore > bestScore){
bestScore = currScore;
ans = d74;
}
currScore = (initialDist - l73.distanceSquaredTo(target)) / v73;
if(currScore > bestScore){
bestScore = currScore;
ans = d73;
}
currScore = (initialDist - l75.distanceSquaredTo(target)) / v75;
if(currScore > bestScore){
bestScore = currScore;
ans = d75;
}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
}
currScore = (initialDist - l21.distanceSquaredTo(target)) / v21;
if(currScore > bestScore){
bestScore = currScore;
ans = d21;
}
currScore = (initialDist - l27.distanceSquaredTo(target)) / v27;
if(currScore > bestScore){
bestScore = currScore;
ans = d27;
}
currScore = (initialDist - l14.distanceSquaredTo(target)) / v14;
if(currScore > bestScore){
bestScore = currScore;
ans = d14;
}
currScore = (initialDist - l50.distanceSquaredTo(target)) / v50;
if(currScore > bestScore){
bestScore = currScore;
ans = d50;
}
currScore = (initialDist - l58.distanceSquaredTo(target)) / v58;
if(currScore > bestScore){
bestScore = currScore;
ans = d58;
}
currScore = (initialDist - l13.distanceSquaredTo(target)) / v13;
if(currScore > bestScore){
bestScore = currScore;
ans = d13;
}
currScore = (initialDist - l15.distanceSquaredTo(target)) / v15;
if(currScore > bestScore){
bestScore = currScore;
ans = d15;
}
currScore = (initialDist - l40.distanceSquaredTo(target)) / v40;
if(currScore > bestScore){
bestScore = currScore;
ans = d40;
}
currScore = (initialDist - l48.distanceSquaredTo(target)) / v48;
if(currScore > bestScore){
bestScore = currScore;
ans = d48;
}
currScore = (initialDist - l60.distanceSquaredTo(target)) / v60;
if(currScore > bestScore){
bestScore = currScore;
ans = d60;
}
currScore = (initialDist - l68.distanceSquaredTo(target)) / v68;
if(currScore > bestScore){
bestScore = currScore;
ans = d68;
}
currScore = (initialDist - l12.distanceSquaredTo(target)) / v12;
if(currScore > bestScore){
bestScore = currScore;
ans = d12;
}
currScore = (initialDist - l16.distanceSquaredTo(target)) / v16;
if(currScore > bestScore){
bestScore = currScore;
ans = d16;
}
currScore = (initialDist - l30.distanceSquaredTo(target)) / v30;
if(currScore > bestScore){
bestScore = currScore;
ans = d30;
}
currScore = (initialDist - l38.distanceSquaredTo(target)) / v38;
if(currScore > bestScore){
bestScore = currScore;
ans = d38;
}
currScore = (initialDist - l70.distanceSquaredTo(target)) / v70;
if(currScore > bestScore){
bestScore = currScore;
ans = d70;
}
currScore = (initialDist - l78.distanceSquaredTo(target)) / v78;
if(currScore > bestScore){
bestScore = currScore;
ans = d78;
}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSNortheast(MapLocation target) throws GameActionException{
try{ 
	double sum;
if(p44 != 0){
if(!rc.isLocationOccupied(l44)){
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(p64 != 0){
if(!rc.isLocationOccupied(l64)){
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(p53 != 0){
if(!rc.isLocationOccupied(l53)){
v53 -= p53;
if(v53 > v54){
v53 = v54;
d53 = Direction.SOUTH;
}
if(v53 > v44){
v53 = v44;
d53 = d44;
}
if(v53 > v64){
v53 = v64;
d53 = d64;
}
v53 += p53;
}
}
if(p55 != 0){
if(!rc.isLocationOccupied(l55)){
v55 -= p55;
if(v55 > v54){
v55 = v54;
d55 = Direction.NORTH;
}
if(v55 > v44){
v55 = v44;
d55 = d44;
}
if(v55 > v64){
v55 = v64;
d55 = d64;
}
v55 += p55;
}
}
if(p43 != 0){
if(!rc.isLocationOccupied(l43)){
v43 -= p43;
if(v43 > v54){
v43 = v54;
d43 = Direction.SOUTHWEST;
}
if(v43 > v44){
v43 = v44;
d43 = d44;
}
if(v43 > v53){
v43 = v53;
d43 = d53;
}
v43 += p43;
}
}
if(p45 != 0){
if(!rc.isLocationOccupied(l45)){
v45 -= p45;
if(v45 > v54){
v45 = v54;
d45 = Direction.NORTHWEST;
}
if(v45 > v55){
v45 = v55;
d45 = d55;
}
if(v45 > v44){
v45 = v44;
d45 = d44;
}
v45 += p45;
}
}
if(p63 != 0){
if(!rc.isLocationOccupied(l63)){
v63 -= p63;
if(v63 > v54){
v63 = v54;
d63 = Direction.SOUTHEAST;
}
if(v63 > v53){
v63 = v53;
d63 = d53;
}
if(v63 > v64){
v63 = v64;
d63 = d64;
}
v63 += p63;
}
}
if(p65 != 0){
if(!rc.isLocationOccupied(l65)){
v65 -= p65;
if(v65 > v54){
v65 = v54;
d65 = Direction.NORTHEAST;
}
if(v65 > v55){
v65 = v55;
d65 = d55;
}
if(v65 > v64){
v65 = v64;
d65 = d64;
}
v65 += p65;
}
}
if(p34 != 0){
v34 -= p34;
if(v34 > v44){
v34 = v44;
d34 = d44;
}
if(v34 > v45){
v34 = v45;
d34 = d45;
}
if(v34 > v43){
v34 = v43;
d34 = d43;
}
v34 += p34;
}
if(p74 != 0){
v74 -= p74;
if(v74 > v64){
v74 = v64;
d74 = d64;
}
if(v74 > v63){
v74 = v63;
d74 = d63;
}
if(v74 > v65){
v74 = v65;
d74 = d65;
}
v74 += p74;
}
if(p52 != 0){
v52 -= p52;
if(v52 > v53){
v52 = v53;
d52 = d53;
}
if(v52 > v43){
v52 = v43;
d52 = d43;
}
if(v52 > v63){
v52 = v63;
d52 = d63;
}
v52 += p52;
}
if(p56 != 0){
v56 -= p56;
if(v56 > v55){
v56 = v55;
d56 = d55;
}
if(v56 > v45){
v56 = v45;
d56 = d45;
}
if(v56 > v65){
v56 = v65;
d56 = d65;
}
v56 += p56;
}
if(p33 != 0){
v33 -= p33;
if(v33 > v44){
v33 = v44;
d33 = d44;
}
if(v33 > v43){
v33 = v43;
d33 = d43;
}
if(v33 > v34){
v33 = v34;
d33 = d34;
}
v33 += p33;
}
if(p35 != 0){
v35 -= p35;
if(v35 > v44){
v35 = v44;
d35 = d44;
}
if(v35 > v45){
v35 = v45;
d35 = d45;
}
if(v35 > v34){
v35 = v34;
d35 = d34;
}
v35 += p35;
}
if(p73 != 0){
v73 -= p73;
if(v73 > v64){
v73 = v64;
d73 = d64;
}
if(v73 > v63){
v73 = v63;
d73 = d63;
}
if(v73 > v74){
v73 = v74;
d73 = d74;
}
v73 += p73;
}
if(p75 != 0){
v75 -= p75;
if(v75 > v64){
v75 = v64;
d75 = d64;
}
if(v75 > v65){
v75 = v65;
d75 = d65;
}
if(v75 > v74){
v75 = v74;
d75 = d74;
}
v75 += p75;
}
if(p42 != 0){
v42 -= p42;
if(v42 > v53){
v42 = v53;
d42 = d53;
}
if(v42 > v43){
v42 = v43;
d42 = d43;
}
if(v42 > v52){
v42 = v52;
d42 = d52;
}
if(v42 > v33){
v42 = v33;
d42 = d33;
}
v42 += p42;
}
if(p46 != 0){
v46 -= p46;
if(v46 > v55){
v46 = v55;
d46 = d55;
}
if(v46 > v45){
v46 = v45;
d46 = d45;
}
if(v46 > v56){
v46 = v56;
d46 = d56;
}
if(v46 > v35){
v46 = v35;
d46 = d35;
}
v46 += p46;
}
if(p62 != 0){
v62 -= p62;
if(v62 > v53){
v62 = v53;
d62 = d53;
}
if(v62 > v63){
v62 = v63;
d62 = d63;
}
if(v62 > v52){
v62 = v52;
d62 = d52;
}
if(v62 > v73){
v62 = v73;
d62 = d73;
}
v62 += p62;
}
if(p66 != 0){
v66 -= p66;
if(v66 > v55){
v66 = v55;
d66 = d55;
}
if(v66 > v65){
v66 = v65;
d66 = d65;
}
if(v66 > v56){
v66 = v56;
d66 = d56;
}
if(v66 > v75){
v66 = v75;
d66 = d75;
}
v66 += p66;
}
if(p32 != 0){
v32 -= p32;
if(v32 > v43){
v32 = v43;
d32 = d43;
}
if(v32 > v33){
v32 = v33;
d32 = d33;
}
if(v32 > v42){
v32 = v42;
d32 = d42;
}
v32 += p32;
}
if(p36 != 0){
v36 -= p36;
if(v36 > v45){
v36 = v45;
d36 = d45;
}
if(v36 > v46){
v36 = v46;
d36 = d46;
}
if(v36 > v35){
v36 = v35;
d36 = d35;
}
v36 += p36;
}
if(p72 != 0){
v72 -= p72;
if(v72 > v63){
v72 = v63;
d72 = d63;
}
if(v72 > v62){
v72 = v62;
d72 = d62;
}
if(v72 > v73){
v72 = v73;
d72 = d73;
}
v72 += p72;
}
if(p76 != 0){
v76 -= p76;
if(v76 > v65){
v76 = v65;
d76 = d65;
}
if(v76 > v66){
v76 = v66;
d76 = d66;
}
if(v76 > v75){
v76 = v75;
d76 = d75;
}
v76 += p76;
}
if(p24 != 0){
v24 -= p24;
if(v24 > v34){
v24 = v34;
d24 = d34;
}
if(v24 > v35){
v24 = v35;
d24 = d35;
}
if(v24 > v33){
v24 = v33;
d24 = d33;
}
v24 += p24;
}
if(p84 != 0){
v84 -= p84;
if(v84 > v74){
v84 = v74;
d84 = d74;
}
if(v84 > v73){
v84 = v73;
d84 = d73;
}
if(v84 > v75){
v84 = v75;
d84 = d75;
}
v84 += p84;
}
if(p51 != 0){
v51 -= p51;
if(v51 > v52){
v51 = v52;
d51 = d52;
}
if(v51 > v42){
v51 = v42;
d51 = d42;
}
if(v51 > v62){
v51 = v62;
d51 = d62;
}
v51 += p51;
}
if(p57 != 0){
v57 -= p57;
if(v57 > v56){
v57 = v56;
d57 = d56;
}
if(v57 > v46){
v57 = v46;
d57 = d46;
}
if(v57 > v66){
v57 = v66;
d57 = d66;
}
v57 += p57;
}
if(p25 != 0){
v25 -= p25;
if(v25 > v34){
v25 = v34;
d25 = d34;
}
if(v25 > v35){
v25 = v35;
d25 = d35;
}
if(v25 > v36){
v25 = v36;
d25 = d36;
}
if(v25 > v24){
v25 = v24;
d25 = d24;
}
v25 += p25;
}
if(p83 != 0){
v83 -= p83;
if(v83 > v74){
v83 = v74;
d83 = d74;
}
if(v83 > v73){
v83 = v73;
d83 = d73;
}
if(v83 > v72){
v83 = v72;
d83 = d72;
}
if(v83 > v84){
v83 = v84;
d83 = d84;
}
v83 += p83;
}
if(p85 != 0){
v85 -= p85;
if(v85 > v74){
v85 = v74;
d85 = d74;
}
if(v85 > v75){
v85 = v75;
d85 = d75;
}
if(v85 > v76){
v85 = v76;
d85 = d76;
}
if(v85 > v84){
v85 = v84;
d85 = d84;
}
v85 += p85;
}
if(p47 != 0){
v47 -= p47;
if(v47 > v56){
v47 = v56;
d47 = d56;
}
if(v47 > v46){
v47 = v46;
d47 = d46;
}
if(v47 > v36){
v47 = v36;
d47 = d36;
}
if(v47 > v57){
v47 = v57;
d47 = d57;
}
v47 += p47;
}
if(p61 != 0){
v61 -= p61;
if(v61 > v52){
v61 = v52;
d61 = d52;
}
if(v61 > v62){
v61 = v62;
d61 = d62;
}
if(v61 > v72){
v61 = v72;
d61 = d72;
}
if(v61 > v51){
v61 = v51;
d61 = d51;
}
v61 += p61;
}
if(p67 != 0){
v67 -= p67;
if(v67 > v56){
v67 = v56;
d67 = d56;
}
if(v67 > v66){
v67 = v66;
d67 = d66;
}
if(v67 > v76){
v67 = v76;
d67 = d76;
}
if(v67 > v57){
v67 = v57;
d67 = d57;
}
v67 += p67;
}
if(p26 != 0){
v26 -= p26;
if(v26 > v35){
v26 = v35;
d26 = d35;
}
if(v26 > v36){
v26 = v36;
d26 = d36;
}
if(v26 > v25){
v26 = v25;
d26 = d25;
}
v26 += p26;
}
if(p82 != 0){
v82 -= p82;
if(v82 > v73){
v82 = v73;
d82 = d73;
}
if(v82 > v72){
v82 = v72;
d82 = d72;
}
if(v82 > v83){
v82 = v83;
d82 = d83;
}
v82 += p82;
}
if(p86 != 0){
v86 -= p86;
if(v86 > v75){
v86 = v75;
d86 = d75;
}
if(v86 > v76){
v86 = v76;
d86 = d76;
}
if(v86 > v85){
v86 = v85;
d86 = d85;
}
v86 += p86;
}
if(p37 != 0){
v37 -= p37;
if(v37 > v46){
v37 = v46;
d37 = d46;
}
if(v37 > v36){
v37 = v36;
d37 = d36;
}
if(v37 > v47){
v37 = v47;
d37 = d47;
}
if(v37 > v26){
v37 = v26;
d37 = d26;
}
v37 += p37;
}
if(p71 != 0){
v71 -= p71;
if(v71 > v62){
v71 = v62;
d71 = d62;
}
if(v71 > v72){
v71 = v72;
d71 = d72;
}
if(v71 > v61){
v71 = v61;
d71 = d61;
}
if(v71 > v82){
v71 = v82;
d71 = d82;
}
v71 += p71;
}
if(p77 != 0){
v77 -= p77;
if(v77 > v66){
v77 = v66;
d77 = d66;
}
if(v77 > v76){
v77 = v76;
d77 = d76;
}
if(v77 > v67){
v77 = v67;
d77 = d67;
}
if(v77 > v86){
v77 = v86;
d77 = d86;
}
v77 += p77;
}
if(p27 != 0){
v27 -= p27;
if(v27 > v36){
v27 = v36;
d27 = d36;
}
if(v27 > v37){
v27 = v37;
d27 = d37;
}
if(v27 > v26){
v27 = v26;
d27 = d26;
}
v27 += p27;
}
if(p81 != 0){
v81 -= p81;
if(v81 > v72){
v81 = v72;
d81 = d72;
}
if(v81 > v71){
v81 = v71;
d81 = d71;
}
if(v81 > v82){
v81 = v82;
d81 = d82;
}
v81 += p81;
}
if(p87 != 0){
v87 -= p87;
if(v87 > v76){
v87 = v76;
d87 = d76;
}
if(v87 > v77){
v87 = v77;
d87 = d77;
}
if(v87 > v86){
v87 = v86;
d87 = d86;
}
v87 += p87;
}
if(p14 != 0){
v14 -= p14;
if(v14 > v24){
v14 = v24;
d14 = d24;
}
if(v14 > v25){
v14 = v25;
d14 = d25;
}
v14 += p14;
}
if(p94 != 0){
v94 -= p94;
if(v94 > v84){
v94 = v84;
d94 = d84;
}
if(v94 > v83){
v94 = v83;
d94 = d83;
}
if(v94 > v85){
v94 = v85;
d94 = d85;
}
v94 += p94;
}
if(p50 != 0){
v50 -= p50;
if(v50 > v51){
v50 = v51;
d50 = d51;
}
if(v50 > v61){
v50 = v61;
d50 = d61;
}
v50 += p50;
}
if(p58 != 0){
v58 -= p58;
if(v58 > v57){
v58 = v57;
d58 = d57;
}
if(v58 > v47){
v58 = v47;
d58 = d47;
}
if(v58 > v67){
v58 = v67;
d58 = d67;
}
v58 += p58;
}
if(p15 != 0){
v15 -= p15;
if(v15 > v24){
v15 = v24;
d15 = d24;
}
if(v15 > v25){
v15 = v25;
d15 = d25;
}
if(v15 > v26){
v15 = v26;
d15 = d26;
}
if(v15 > v14){
v15 = v14;
d15 = d14;
}
v15 += p15;
}
if(p93 != 0){
v93 -= p93;
if(v93 > v84){
v93 = v84;
d93 = d84;
}
if(v93 > v83){
v93 = v83;
d93 = d83;
}
if(v93 > v82){
v93 = v82;
d93 = d82;
}
if(v93 > v94){
v93 = v94;
d93 = d94;
}
v93 += p93;
}
if(p95 != 0){
v95 -= p95;
if(v95 > v84){
v95 = v84;
d95 = d84;
}
if(v95 > v85){
v95 = v85;
d95 = d85;
}
if(v95 > v86){
v95 = v86;
d95 = d86;
}
if(v95 > v94){
v95 = v94;
d95 = d94;
}
v95 += p95;
}
if(p48 != 0){
v48 -= p48;
if(v48 > v57){
v48 = v57;
d48 = d57;
}
if(v48 > v47){
v48 = v47;
d48 = d47;
}
if(v48 > v37){
v48 = v37;
d48 = d37;
}
if(v48 > v58){
v48 = v58;
d48 = d58;
}
v48 += p48;
}
if(p60 != 0){
v60 -= p60;
if(v60 > v51){
v60 = v51;
d60 = d51;
}
if(v60 > v61){
v60 = v61;
d60 = d61;
}
if(v60 > v71){
v60 = v71;
d60 = d71;
}
if(v60 > v50){
v60 = v50;
d60 = d50;
}
v60 += p60;
}
if(p68 != 0){
v68 -= p68;
if(v68 > v57){
v68 = v57;
d68 = d57;
}
if(v68 > v67){
v68 = v67;
d68 = d67;
}
if(v68 > v77){
v68 = v77;
d68 = d77;
}
if(v68 > v58){
v68 = v58;
d68 = d58;
}
v68 += p68;
}
if(p16 != 0){
v16 -= p16;
if(v16 > v25){
v16 = v25;
d16 = d25;
}
if(v16 > v26){
v16 = v26;
d16 = d26;
}
if(v16 > v15){
v16 = v15;
d16 = d15;
}
if(v16 > v27){
v16 = v27;
d16 = d27;
}
v16 += p16;
}
if(p92 != 0){
v92 -= p92;
if(v92 > v83){
v92 = v83;
d92 = d83;
}
if(v92 > v82){
v92 = v82;
d92 = d82;
}
if(v92 > v93){
v92 = v93;
d92 = d93;
}
if(v92 > v81){
v92 = v81;
d92 = d81;
}
v92 += p92;
}
if(p96 != 0){
v96 -= p96;
if(v96 > v85){
v96 = v85;
d96 = d85;
}
if(v96 > v86){
v96 = v86;
d96 = d86;
}
if(v96 > v95){
v96 = v95;
d96 = d95;
}
if(v96 > v87){
v96 = v87;
d96 = d87;
}
v96 += p96;
}
if(p38 != 0){
v38 -= p38;
if(v38 > v47){
v38 = v47;
d38 = d47;
}
if(v38 > v37){
v38 = v37;
d38 = d37;
}
if(v38 > v48){
v38 = v48;
d38 = d48;
}
if(v38 > v27){
v38 = v27;
d38 = d27;
}
v38 += p38;
}
if(p70 != 0){
v70 -= p70;
if(v70 > v61){
v70 = v61;
d70 = d61;
}
if(v70 > v71){
v70 = v71;
d70 = d71;
}
if(v70 > v60){
v70 = v60;
d70 = d60;
}
if(v70 > v81){
v70 = v81;
d70 = d81;
}
v70 += p70;
}
if(p78 != 0){
v78 -= p78;
if(v78 > v67){
v78 = v67;
d78 = d67;
}
if(v78 > v77){
v78 = v77;
d78 = d77;
}
if(v78 > v68){
v78 = v68;
d78 = d68;
}
if(v78 > v87){
v78 = v87;
d78 = d87;
}
v78 += p78;
}
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -4:
switch (dy){
case 0:
return d14;
case 1:
return d15;
case 2:
return d16;
}
break;
case -3:
switch (dy){
case 0:
return d24;
case 1:
return d25;
case 2:
return d26;
case 3:
return d27;
}
break;
case -2:
switch (dy){
case -2:
return d32;
case -1:
return d33;
case 0:
return d34;
case 1:
return d35;
case 2:
return d36;
case 3:
return d37;
case 4:
return d38;
}
break;
case -1:
switch (dy){
case -2:
return d42;
case -1:
return d43;
case 0:
return d44;
case 1:
return d45;
case 2:
return d46;
case 3:
return d47;
case 4:
return d48;
}
break;
case 0:
switch (dy){
case -4:
return d50;
case -3:
return d51;
case -2:
return d52;
case -1:
return d53;
case 0:
return d54;
case 1:
return d55;
case 2:
return d56;
case 3:
return d57;
case 4:
return d58;
}
break;
case 1:
switch (dy){
case -4:
return d60;
case -3:
return d61;
case -2:
return d62;
case -1:
return d63;
case 0:
return d64;
case 1:
return d65;
case 2:
return d66;
case 3:
return d67;
case 4:
return d68;
}
break;
case 2:
switch (dy){
case -4:
return d70;
case -3:
return d71;
case -2:
return d72;
case -1:
return d73;
case 0:
return d74;
case 1:
return d75;
case 2:
return d76;
case 3:
return d77;
case 4:
return d78;
}
break;
case 3:
switch (dy){
case -3:
return d81;
case -2:
return d82;
case -1:
return d83;
case 0:
return d84;
case 1:
return d85;
case 2:
return d86;
case 3:
return d87;
}
break;
case 4:
switch (dy){
case -2:
return d92;
case -1:
return d93;
case 0:
return d94;
case 1:
return d95;
case 2:
return d96;
}
break;
}
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l34.distanceSquaredTo(target)) / v34;
if(currScore > bestScore){
bestScore = currScore;
ans = d34;
}
currScore = (initialDist - l52.distanceSquaredTo(target)) / v52;
if(currScore > bestScore){
bestScore = currScore;
ans = d52;
}
currScore = (initialDist - l33.distanceSquaredTo(target)) / v33;
if(currScore > bestScore){
bestScore = currScore;
ans = d33;
}
currScore = (initialDist - l42.distanceSquaredTo(target)) / v42;
if(currScore > bestScore){
bestScore = currScore;
ans = d42;
}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
}
currScore = (initialDist - l24.distanceSquaredTo(target)) / v24;
if(currScore > bestScore){
bestScore = currScore;
ans = d24;
}
currScore = (initialDist - l51.distanceSquaredTo(target)) / v51;
if(currScore > bestScore){
bestScore = currScore;
ans = d51;
}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
}
currScore = (initialDist - l27.distanceSquaredTo(target)) / v27;
if(currScore > bestScore){
bestScore = currScore;
ans = d27;
}
currScore = (initialDist - l81.distanceSquaredTo(target)) / v81;
if(currScore > bestScore){
bestScore = currScore;
ans = d81;
}
currScore = (initialDist - l87.distanceSquaredTo(target)) / v87;
if(currScore > bestScore){
bestScore = currScore;
ans = d87;
}
currScore = (initialDist - l14.distanceSquaredTo(target)) / v14;
if(currScore > bestScore){
bestScore = currScore;
ans = d14;
}
currScore = (initialDist - l94.distanceSquaredTo(target)) / v94;
if(currScore > bestScore){
bestScore = currScore;
ans = d94;
}
currScore = (initialDist - l50.distanceSquaredTo(target)) / v50;
if(currScore > bestScore){
bestScore = currScore;
ans = d50;
}
currScore = (initialDist - l58.distanceSquaredTo(target)) / v58;
if(currScore > bestScore){
bestScore = currScore;
ans = d58;
}
currScore = (initialDist - l15.distanceSquaredTo(target)) / v15;
if(currScore > bestScore){
bestScore = currScore;
ans = d15;
}
currScore = (initialDist - l93.distanceSquaredTo(target)) / v93;
if(currScore > bestScore){
bestScore = currScore;
ans = d93;
}
currScore = (initialDist - l95.distanceSquaredTo(target)) / v95;
if(currScore > bestScore){
bestScore = currScore;
ans = d95;
}
currScore = (initialDist - l48.distanceSquaredTo(target)) / v48;
if(currScore > bestScore){
bestScore = currScore;
ans = d48;
}
currScore = (initialDist - l60.distanceSquaredTo(target)) / v60;
if(currScore > bestScore){
bestScore = currScore;
ans = d60;
}
currScore = (initialDist - l68.distanceSquaredTo(target)) / v68;
if(currScore > bestScore){
bestScore = currScore;
ans = d68;
}
currScore = (initialDist - l16.distanceSquaredTo(target)) / v16;
if(currScore > bestScore){
bestScore = currScore;
ans = d16;
}
currScore = (initialDist - l92.distanceSquaredTo(target)) / v92;
if(currScore > bestScore){
bestScore = currScore;
ans = d92;
}
currScore = (initialDist - l96.distanceSquaredTo(target)) / v96;
if(currScore > bestScore){
bestScore = currScore;
ans = d96;
}
currScore = (initialDist - l38.distanceSquaredTo(target)) / v38;
if(currScore > bestScore){
bestScore = currScore;
ans = d38;
}
currScore = (initialDist - l70.distanceSquaredTo(target)) / v70;
if(currScore > bestScore){
bestScore = currScore;
ans = d70;
}
currScore = (initialDist - l78.distanceSquaredTo(target)) / v78;
if(currScore > bestScore){
bestScore = currScore;
ans = d78;
}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSNorthwest(MapLocation target) throws GameActionException{
try{ 
	double sum;
if(p44 != 0){
if(!rc.isLocationOccupied(l44)){
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(p64 != 0){
if(!rc.isLocationOccupied(l64)){
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(p53 != 0){
if(!rc.isLocationOccupied(l53)){
v53 -= p53;
if(v53 > v54){
v53 = v54;
d53 = Direction.SOUTH;
}
if(v53 > v44){
v53 = v44;
d53 = d44;
}
if(v53 > v64){
v53 = v64;
d53 = d64;
}
v53 += p53;
}
}
if(p55 != 0){
if(!rc.isLocationOccupied(l55)){
v55 -= p55;
if(v55 > v54){
v55 = v54;
d55 = Direction.NORTH;
}
if(v55 > v44){
v55 = v44;
d55 = d44;
}
if(v55 > v64){
v55 = v64;
d55 = d64;
}
v55 += p55;
}
}
if(p43 != 0){
if(!rc.isLocationOccupied(l43)){
v43 -= p43;
if(v43 > v54){
v43 = v54;
d43 = Direction.SOUTHWEST;
}
if(v43 > v44){
v43 = v44;
d43 = d44;
}
if(v43 > v53){
v43 = v53;
d43 = d53;
}
v43 += p43;
}
}
if(p45 != 0){
if(!rc.isLocationOccupied(l45)){
v45 -= p45;
if(v45 > v54){
v45 = v54;
d45 = Direction.NORTHWEST;
}
if(v45 > v55){
v45 = v55;
d45 = d55;
}
if(v45 > v44){
v45 = v44;
d45 = d44;
}
v45 += p45;
}
}
if(p63 != 0){
if(!rc.isLocationOccupied(l63)){
v63 -= p63;
if(v63 > v54){
v63 = v54;
d63 = Direction.SOUTHEAST;
}
if(v63 > v53){
v63 = v53;
d63 = d53;
}
if(v63 > v64){
v63 = v64;
d63 = d64;
}
v63 += p63;
}
}
if(p65 != 0){
if(!rc.isLocationOccupied(l65)){
v65 -= p65;
if(v65 > v54){
v65 = v54;
d65 = Direction.NORTHEAST;
}
if(v65 > v55){
v65 = v55;
d65 = d55;
}
if(v65 > v64){
v65 = v64;
d65 = d64;
}
v65 += p65;
}
}
if(p34 != 0){
v34 -= p34;
if(v34 > v44){
v34 = v44;
d34 = d44;
}
if(v34 > v45){
v34 = v45;
d34 = d45;
}
if(v34 > v43){
v34 = v43;
d34 = d43;
}
v34 += p34;
}
if(p74 != 0){
v74 -= p74;
if(v74 > v64){
v74 = v64;
d74 = d64;
}
if(v74 > v63){
v74 = v63;
d74 = d63;
}
if(v74 > v65){
v74 = v65;
d74 = d65;
}
v74 += p74;
}
if(p52 != 0){
v52 -= p52;
if(v52 > v53){
v52 = v53;
d52 = d53;
}
if(v52 > v43){
v52 = v43;
d52 = d43;
}
if(v52 > v63){
v52 = v63;
d52 = d63;
}
v52 += p52;
}
if(p56 != 0){
v56 -= p56;
if(v56 > v55){
v56 = v55;
d56 = d55;
}
if(v56 > v45){
v56 = v45;
d56 = d45;
}
if(v56 > v65){
v56 = v65;
d56 = d65;
}
v56 += p56;
}
if(p33 != 0){
v33 -= p33;
if(v33 > v44){
v33 = v44;
d33 = d44;
}
if(v33 > v43){
v33 = v43;
d33 = d43;
}
if(v33 > v34){
v33 = v34;
d33 = d34;
}
v33 += p33;
}
if(p35 != 0){
v35 -= p35;
if(v35 > v44){
v35 = v44;
d35 = d44;
}
if(v35 > v45){
v35 = v45;
d35 = d45;
}
if(v35 > v34){
v35 = v34;
d35 = d34;
}
v35 += p35;
}
if(p73 != 0){
v73 -= p73;
if(v73 > v64){
v73 = v64;
d73 = d64;
}
if(v73 > v63){
v73 = v63;
d73 = d63;
}
if(v73 > v74){
v73 = v74;
d73 = d74;
}
v73 += p73;
}
if(p75 != 0){
v75 -= p75;
if(v75 > v64){
v75 = v64;
d75 = d64;
}
if(v75 > v65){
v75 = v65;
d75 = d65;
}
if(v75 > v74){
v75 = v74;
d75 = d74;
}
v75 += p75;
}
if(p42 != 0){
v42 -= p42;
if(v42 > v53){
v42 = v53;
d42 = d53;
}
if(v42 > v43){
v42 = v43;
d42 = d43;
}
if(v42 > v52){
v42 = v52;
d42 = d52;
}
if(v42 > v33){
v42 = v33;
d42 = d33;
}
v42 += p42;
}
if(p46 != 0){
v46 -= p46;
if(v46 > v55){
v46 = v55;
d46 = d55;
}
if(v46 > v45){
v46 = v45;
d46 = d45;
}
if(v46 > v56){
v46 = v56;
d46 = d56;
}
if(v46 > v35){
v46 = v35;
d46 = d35;
}
v46 += p46;
}
if(p62 != 0){
v62 -= p62;
if(v62 > v53){
v62 = v53;
d62 = d53;
}
if(v62 > v63){
v62 = v63;
d62 = d63;
}
if(v62 > v52){
v62 = v52;
d62 = d52;
}
if(v62 > v73){
v62 = v73;
d62 = d73;
}
v62 += p62;
}
if(p66 != 0){
v66 -= p66;
if(v66 > v55){
v66 = v55;
d66 = d55;
}
if(v66 > v65){
v66 = v65;
d66 = d65;
}
if(v66 > v56){
v66 = v56;
d66 = d56;
}
if(v66 > v75){
v66 = v75;
d66 = d75;
}
v66 += p66;
}
if(p32 != 0){
v32 -= p32;
if(v32 > v43){
v32 = v43;
d32 = d43;
}
if(v32 > v33){
v32 = v33;
d32 = d33;
}
if(v32 > v42){
v32 = v42;
d32 = d42;
}
v32 += p32;
}
if(p36 != 0){
v36 -= p36;
if(v36 > v45){
v36 = v45;
d36 = d45;
}
if(v36 > v46){
v36 = v46;
d36 = d46;
}
if(v36 > v35){
v36 = v35;
d36 = d35;
}
v36 += p36;
}
if(p72 != 0){
v72 -= p72;
if(v72 > v63){
v72 = v63;
d72 = d63;
}
if(v72 > v62){
v72 = v62;
d72 = d62;
}
if(v72 > v73){
v72 = v73;
d72 = d73;
}
v72 += p72;
}
if(p76 != 0){
v76 -= p76;
if(v76 > v65){
v76 = v65;
d76 = d65;
}
if(v76 > v66){
v76 = v66;
d76 = d66;
}
if(v76 > v75){
v76 = v75;
d76 = d75;
}
v76 += p76;
}
if(p24 != 0){
v24 -= p24;
if(v24 > v34){
v24 = v34;
d24 = d34;
}
if(v24 > v35){
v24 = v35;
d24 = d35;
}
if(v24 > v33){
v24 = v33;
d24 = d33;
}
v24 += p24;
}
if(p84 != 0){
v84 -= p84;
if(v84 > v74){
v84 = v74;
d84 = d74;
}
if(v84 > v73){
v84 = v73;
d84 = d73;
}
if(v84 > v75){
v84 = v75;
d84 = d75;
}
v84 += p84;
}
if(p51 != 0){
v51 -= p51;
if(v51 > v52){
v51 = v52;
d51 = d52;
}
if(v51 > v42){
v51 = v42;
d51 = d42;
}
if(v51 > v62){
v51 = v62;
d51 = d62;
}
v51 += p51;
}
if(p57 != 0){
v57 -= p57;
if(v57 > v56){
v57 = v56;
d57 = d56;
}
if(v57 > v46){
v57 = v46;
d57 = d46;
}
if(v57 > v66){
v57 = v66;
d57 = d66;
}
v57 += p57;
}
if(p23 != 0){
v23 -= p23;
if(v23 > v34){
v23 = v34;
d23 = d34;
}
if(v23 > v33){
v23 = v33;
d23 = d33;
}
if(v23 > v32){
v23 = v32;
d23 = d32;
}
if(v23 > v24){
v23 = v24;
d23 = d24;
}
v23 += p23;
}
if(p25 != 0){
v25 -= p25;
if(v25 > v34){
v25 = v34;
d25 = d34;
}
if(v25 > v35){
v25 = v35;
d25 = d35;
}
if(v25 > v36){
v25 = v36;
d25 = d36;
}
if(v25 > v24){
v25 = v24;
d25 = d24;
}
v25 += p25;
}
if(p85 != 0){
v85 -= p85;
if(v85 > v74){
v85 = v74;
d85 = d74;
}
if(v85 > v75){
v85 = v75;
d85 = d75;
}
if(v85 > v76){
v85 = v76;
d85 = d76;
}
if(v85 > v84){
v85 = v84;
d85 = d84;
}
v85 += p85;
}
if(p41 != 0){
v41 -= p41;
if(v41 > v52){
v41 = v52;
d41 = d52;
}
if(v41 > v42){
v41 = v42;
d41 = d42;
}
if(v41 > v32){
v41 = v32;
d41 = d32;
}
if(v41 > v51){
v41 = v51;
d41 = d51;
}
v41 += p41;
}
if(p47 != 0){
v47 -= p47;
if(v47 > v56){
v47 = v56;
d47 = d56;
}
if(v47 > v46){
v47 = v46;
d47 = d46;
}
if(v47 > v36){
v47 = v36;
d47 = d36;
}
if(v47 > v57){
v47 = v57;
d47 = d57;
}
v47 += p47;
}
if(p67 != 0){
v67 -= p67;
if(v67 > v56){
v67 = v56;
d67 = d56;
}
if(v67 > v66){
v67 = v66;
d67 = d66;
}
if(v67 > v76){
v67 = v76;
d67 = d76;
}
if(v67 > v57){
v67 = v57;
d67 = d57;
}
v67 += p67;
}
if(p22 != 0){
v22 -= p22;
if(v22 > v33){
v22 = v33;
d22 = d33;
}
if(v22 > v32){
v22 = v32;
d22 = d32;
}
if(v22 > v23){
v22 = v23;
d22 = d23;
}
v22 += p22;
}
if(p26 != 0){
v26 -= p26;
if(v26 > v35){
v26 = v35;
d26 = d35;
}
if(v26 > v36){
v26 = v36;
d26 = d36;
}
if(v26 > v25){
v26 = v25;
d26 = d25;
}
v26 += p26;
}
if(p86 != 0){
v86 -= p86;
if(v86 > v75){
v86 = v75;
d86 = d75;
}
if(v86 > v76){
v86 = v76;
d86 = d76;
}
if(v86 > v85){
v86 = v85;
d86 = d85;
}
v86 += p86;
}
if(p31 != 0){
v31 -= p31;
if(v31 > v42){
v31 = v42;
d31 = d42;
}
if(v31 > v32){
v31 = v32;
d31 = d32;
}
if(v31 > v41){
v31 = v41;
d31 = d41;
}
if(v31 > v22){
v31 = v22;
d31 = d22;
}
v31 += p31;
}
if(p37 != 0){
v37 -= p37;
if(v37 > v46){
v37 = v46;
d37 = d46;
}
if(v37 > v36){
v37 = v36;
d37 = d36;
}
if(v37 > v47){
v37 = v47;
d37 = d47;
}
if(v37 > v26){
v37 = v26;
d37 = d26;
}
v37 += p37;
}
if(p77 != 0){
v77 -= p77;
if(v77 > v66){
v77 = v66;
d77 = d66;
}
if(v77 > v76){
v77 = v76;
d77 = d76;
}
if(v77 > v67){
v77 = v67;
d77 = d67;
}
if(v77 > v86){
v77 = v86;
d77 = d86;
}
v77 += p77;
}
if(p21 != 0){
v21 -= p21;
if(v21 > v32){
v21 = v32;
d21 = d32;
}
if(v21 > v22){
v21 = v22;
d21 = d22;
}
if(v21 > v31){
v21 = v31;
d21 = d31;
}
v21 += p21;
}
if(p27 != 0){
v27 -= p27;
if(v27 > v36){
v27 = v36;
d27 = d36;
}
if(v27 > v37){
v27 = v37;
d27 = d37;
}
if(v27 > v26){
v27 = v26;
d27 = d26;
}
v27 += p27;
}
if(p87 != 0){
v87 -= p87;
if(v87 > v76){
v87 = v76;
d87 = d76;
}
if(v87 > v77){
v87 = v77;
d87 = d77;
}
if(v87 > v86){
v87 = v86;
d87 = d86;
}
v87 += p87;
}
if(p14 != 0){
v14 -= p14;
if(v14 > v24){
v14 = v24;
d14 = d24;
}
if(v14 > v25){
v14 = v25;
d14 = d25;
}
if(v14 > v23){
v14 = v23;
d14 = d23;
}
v14 += p14;
}
if(p94 != 0){
v94 -= p94;
if(v94 > v84){
v94 = v84;
d94 = d84;
}
if(v94 > v85){
v94 = v85;
d94 = d85;
}
v94 += p94;
}
if(p50 != 0){
v50 -= p50;
if(v50 > v51){
v50 = v51;
d50 = d51;
}
if(v50 > v41){
v50 = v41;
d50 = d41;
}
v50 += p50;
}
if(p58 != 0){
v58 -= p58;
if(v58 > v57){
v58 = v57;
d58 = d57;
}
if(v58 > v47){
v58 = v47;
d58 = d47;
}
if(v58 > v67){
v58 = v67;
d58 = d67;
}
v58 += p58;
}
if(p13 != 0){
v13 -= p13;
if(v13 > v24){
v13 = v24;
d13 = d24;
}
if(v13 > v23){
v13 = v23;
d13 = d23;
}
if(v13 > v22){
v13 = v22;
d13 = d22;
}
if(v13 > v14){
v13 = v14;
d13 = d14;
}
v13 += p13;
}
if(p15 != 0){
v15 -= p15;
if(v15 > v24){
v15 = v24;
d15 = d24;
}
if(v15 > v25){
v15 = v25;
d15 = d25;
}
if(v15 > v26){
v15 = v26;
d15 = d26;
}
if(v15 > v14){
v15 = v14;
d15 = d14;
}
v15 += p15;
}
if(p95 != 0){
v95 -= p95;
if(v95 > v84){
v95 = v84;
d95 = d84;
}
if(v95 > v85){
v95 = v85;
d95 = d85;
}
if(v95 > v86){
v95 = v86;
d95 = d86;
}
if(v95 > v94){
v95 = v94;
d95 = d94;
}
v95 += p95;
}
if(p40 != 0){
v40 -= p40;
if(v40 > v51){
v40 = v51;
d40 = d51;
}
if(v40 > v41){
v40 = v41;
d40 = d41;
}
if(v40 > v31){
v40 = v31;
d40 = d31;
}
if(v40 > v50){
v40 = v50;
d40 = d50;
}
v40 += p40;
}
if(p48 != 0){
v48 -= p48;
if(v48 > v57){
v48 = v57;
d48 = d57;
}
if(v48 > v47){
v48 = v47;
d48 = d47;
}
if(v48 > v37){
v48 = v37;
d48 = d37;
}
if(v48 > v58){
v48 = v58;
d48 = d58;
}
v48 += p48;
}
if(p68 != 0){
v68 -= p68;
if(v68 > v57){
v68 = v57;
d68 = d57;
}
if(v68 > v67){
v68 = v67;
d68 = d67;
}
if(v68 > v77){
v68 = v77;
d68 = d77;
}
if(v68 > v58){
v68 = v58;
d68 = d58;
}
v68 += p68;
}
if(p12 != 0){
v12 -= p12;
if(v12 > v23){
v12 = v23;
d12 = d23;
}
if(v12 > v22){
v12 = v22;
d12 = d22;
}
if(v12 > v13){
v12 = v13;
d12 = d13;
}
if(v12 > v21){
v12 = v21;
d12 = d21;
}
v12 += p12;
}
if(p16 != 0){
v16 -= p16;
if(v16 > v25){
v16 = v25;
d16 = d25;
}
if(v16 > v26){
v16 = v26;
d16 = d26;
}
if(v16 > v15){
v16 = v15;
d16 = d15;
}
if(v16 > v27){
v16 = v27;
d16 = d27;
}
v16 += p16;
}
if(p96 != 0){
v96 -= p96;
if(v96 > v85){
v96 = v85;
d96 = d85;
}
if(v96 > v86){
v96 = v86;
d96 = d86;
}
if(v96 > v95){
v96 = v95;
d96 = d95;
}
if(v96 > v87){
v96 = v87;
d96 = d87;
}
v96 += p96;
}
if(p30 != 0){
v30 -= p30;
if(v30 > v41){
v30 = v41;
d30 = d41;
}
if(v30 > v31){
v30 = v31;
d30 = d31;
}
if(v30 > v40){
v30 = v40;
d30 = d40;
}
if(v30 > v21){
v30 = v21;
d30 = d21;
}
v30 += p30;
}
if(p38 != 0){
v38 -= p38;
if(v38 > v47){
v38 = v47;
d38 = d47;
}
if(v38 > v37){
v38 = v37;
d38 = d37;
}
if(v38 > v48){
v38 = v48;
d38 = d48;
}
if(v38 > v27){
v38 = v27;
d38 = d27;
}
v38 += p38;
}
if(p78 != 0){
v78 -= p78;
if(v78 > v67){
v78 = v67;
d78 = d67;
}
if(v78 > v77){
v78 = v77;
d78 = d77;
}
if(v78 > v68){
v78 = v68;
d78 = d68;
}
if(v78 > v87){
v78 = v87;
d78 = d87;
}
v78 += p78;
}
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -4:
switch (dy){
case -2:
return d12;
case -1:
return d13;
case 0:
return d14;
case 1:
return d15;
case 2:
return d16;
}
break;
case -3:
switch (dy){
case -3:
return d21;
case -2:
return d22;
case -1:
return d23;
case 0:
return d24;
case 1:
return d25;
case 2:
return d26;
case 3:
return d27;
}
break;
case -2:
switch (dy){
case -4:
return d30;
case -3:
return d31;
case -2:
return d32;
case -1:
return d33;
case 0:
return d34;
case 1:
return d35;
case 2:
return d36;
case 3:
return d37;
case 4:
return d38;
}
break;
case -1:
switch (dy){
case -4:
return d40;
case -3:
return d41;
case -2:
return d42;
case -1:
return d43;
case 0:
return d44;
case 1:
return d45;
case 2:
return d46;
case 3:
return d47;
case 4:
return d48;
}
break;
case 0:
switch (dy){
case -4:
return d50;
case -3:
return d51;
case -2:
return d52;
case -1:
return d53;
case 0:
return d54;
case 1:
return d55;
case 2:
return d56;
case 3:
return d57;
case 4:
return d58;
}
break;
case 1:
switch (dy){
case -2:
return d62;
case -1:
return d63;
case 0:
return d64;
case 1:
return d65;
case 2:
return d66;
case 3:
return d67;
case 4:
return d68;
}
break;
case 2:
switch (dy){
case -2:
return d72;
case -1:
return d73;
case 0:
return d74;
case 1:
return d75;
case 2:
return d76;
case 3:
return d77;
case 4:
return d78;
}
break;
case 3:
switch (dy){
case 0:
return d84;
case 1:
return d85;
case 2:
return d86;
case 3:
return d87;
}
break;
case 4:
switch (dy){
case 0:
return d94;
case 1:
return d95;
case 2:
return d96;
}
break;
}
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l74.distanceSquaredTo(target)) / v74;
if(currScore > bestScore){
bestScore = currScore;
ans = d74;
}
currScore = (initialDist - l52.distanceSquaredTo(target)) / v52;
if(currScore > bestScore){
bestScore = currScore;
ans = d52;
}
currScore = (initialDist - l73.distanceSquaredTo(target)) / v73;
if(currScore > bestScore){
bestScore = currScore;
ans = d73;
}
currScore = (initialDist - l62.distanceSquaredTo(target)) / v62;
if(currScore > bestScore){
bestScore = currScore;
ans = d62;
}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
}
currScore = (initialDist - l84.distanceSquaredTo(target)) / v84;
if(currScore > bestScore){
bestScore = currScore;
ans = d84;
}
currScore = (initialDist - l51.distanceSquaredTo(target)) / v51;
if(currScore > bestScore){
bestScore = currScore;
ans = d51;
}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
}
currScore = (initialDist - l21.distanceSquaredTo(target)) / v21;
if(currScore > bestScore){
bestScore = currScore;
ans = d21;
}
currScore = (initialDist - l27.distanceSquaredTo(target)) / v27;
if(currScore > bestScore){
bestScore = currScore;
ans = d27;
}
currScore = (initialDist - l87.distanceSquaredTo(target)) / v87;
if(currScore > bestScore){
bestScore = currScore;
ans = d87;
}
currScore = (initialDist - l14.distanceSquaredTo(target)) / v14;
if(currScore > bestScore){
bestScore = currScore;
ans = d14;
}
currScore = (initialDist - l94.distanceSquaredTo(target)) / v94;
if(currScore > bestScore){
bestScore = currScore;
ans = d94;
}
currScore = (initialDist - l50.distanceSquaredTo(target)) / v50;
if(currScore > bestScore){
bestScore = currScore;
ans = d50;
}
currScore = (initialDist - l58.distanceSquaredTo(target)) / v58;
if(currScore > bestScore){
bestScore = currScore;
ans = d58;
}
currScore = (initialDist - l13.distanceSquaredTo(target)) / v13;
if(currScore > bestScore){
bestScore = currScore;
ans = d13;
}
currScore = (initialDist - l15.distanceSquaredTo(target)) / v15;
if(currScore > bestScore){
bestScore = currScore;
ans = d15;
}
currScore = (initialDist - l95.distanceSquaredTo(target)) / v95;
if(currScore > bestScore){
bestScore = currScore;
ans = d95;
}
currScore = (initialDist - l40.distanceSquaredTo(target)) / v40;
if(currScore > bestScore){
bestScore = currScore;
ans = d40;
}
currScore = (initialDist - l48.distanceSquaredTo(target)) / v48;
if(currScore > bestScore){
bestScore = currScore;
ans = d48;
}
currScore = (initialDist - l68.distanceSquaredTo(target)) / v68;
if(currScore > bestScore){
bestScore = currScore;
ans = d68;
}
currScore = (initialDist - l12.distanceSquaredTo(target)) / v12;
if(currScore > bestScore){
bestScore = currScore;
ans = d12;
}
currScore = (initialDist - l16.distanceSquaredTo(target)) / v16;
if(currScore > bestScore){
bestScore = currScore;
ans = d16;
}
currScore = (initialDist - l96.distanceSquaredTo(target)) / v96;
if(currScore > bestScore){
bestScore = currScore;
ans = d96;
}
currScore = (initialDist - l30.distanceSquaredTo(target)) / v30;
if(currScore > bestScore){
bestScore = currScore;
ans = d30;
}
currScore = (initialDist - l38.distanceSquaredTo(target)) / v38;
if(currScore > bestScore){
bestScore = currScore;
ans = d38;
}
currScore = (initialDist - l78.distanceSquaredTo(target)) / v78;
if(currScore > bestScore){
bestScore = currScore;
ans = d78;
}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSSoutheast(MapLocation target) throws GameActionException{
try{ 
	double sum;
if(p44 != 0){
if(!rc.isLocationOccupied(l44)){
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(p64 != 0){
if(!rc.isLocationOccupied(l64)){
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(p53 != 0){
if(!rc.isLocationOccupied(l53)){
v53 -= p53;
if(v53 > v54){
v53 = v54;
d53 = Direction.SOUTH;
}
if(v53 > v44){
v53 = v44;
d53 = d44;
}
if(v53 > v64){
v53 = v64;
d53 = d64;
}
v53 += p53;
}
}
if(p55 != 0){
if(!rc.isLocationOccupied(l55)){
v55 -= p55;
if(v55 > v54){
v55 = v54;
d55 = Direction.NORTH;
}
if(v55 > v44){
v55 = v44;
d55 = d44;
}
if(v55 > v64){
v55 = v64;
d55 = d64;
}
v55 += p55;
}
}
if(p43 != 0){
if(!rc.isLocationOccupied(l43)){
v43 -= p43;
if(v43 > v54){
v43 = v54;
d43 = Direction.SOUTHWEST;
}
if(v43 > v44){
v43 = v44;
d43 = d44;
}
if(v43 > v53){
v43 = v53;
d43 = d53;
}
v43 += p43;
}
}
if(p45 != 0){
if(!rc.isLocationOccupied(l45)){
v45 -= p45;
if(v45 > v54){
v45 = v54;
d45 = Direction.NORTHWEST;
}
if(v45 > v55){
v45 = v55;
d45 = d55;
}
if(v45 > v44){
v45 = v44;
d45 = d44;
}
v45 += p45;
}
}
if(p63 != 0){
if(!rc.isLocationOccupied(l63)){
v63 -= p63;
if(v63 > v54){
v63 = v54;
d63 = Direction.SOUTHEAST;
}
if(v63 > v53){
v63 = v53;
d63 = d53;
}
if(v63 > v64){
v63 = v64;
d63 = d64;
}
v63 += p63;
}
}
if(p65 != 0){
if(!rc.isLocationOccupied(l65)){
v65 -= p65;
if(v65 > v54){
v65 = v54;
d65 = Direction.NORTHEAST;
}
if(v65 > v55){
v65 = v55;
d65 = d55;
}
if(v65 > v64){
v65 = v64;
d65 = d64;
}
v65 += p65;
}
}
if(p34 != 0){
v34 -= p34;
if(v34 > v44){
v34 = v44;
d34 = d44;
}
if(v34 > v45){
v34 = v45;
d34 = d45;
}
if(v34 > v43){
v34 = v43;
d34 = d43;
}
v34 += p34;
}
if(p74 != 0){
v74 -= p74;
if(v74 > v64){
v74 = v64;
d74 = d64;
}
if(v74 > v63){
v74 = v63;
d74 = d63;
}
if(v74 > v65){
v74 = v65;
d74 = d65;
}
v74 += p74;
}
if(p52 != 0){
v52 -= p52;
if(v52 > v53){
v52 = v53;
d52 = d53;
}
if(v52 > v43){
v52 = v43;
d52 = d43;
}
if(v52 > v63){
v52 = v63;
d52 = d63;
}
v52 += p52;
}
if(p56 != 0){
v56 -= p56;
if(v56 > v55){
v56 = v55;
d56 = d55;
}
if(v56 > v45){
v56 = v45;
d56 = d45;
}
if(v56 > v65){
v56 = v65;
d56 = d65;
}
v56 += p56;
}
if(p33 != 0){
v33 -= p33;
if(v33 > v44){
v33 = v44;
d33 = d44;
}
if(v33 > v43){
v33 = v43;
d33 = d43;
}
if(v33 > v34){
v33 = v34;
d33 = d34;
}
v33 += p33;
}
if(p35 != 0){
v35 -= p35;
if(v35 > v44){
v35 = v44;
d35 = d44;
}
if(v35 > v45){
v35 = v45;
d35 = d45;
}
if(v35 > v34){
v35 = v34;
d35 = d34;
}
v35 += p35;
}
if(p73 != 0){
v73 -= p73;
if(v73 > v64){
v73 = v64;
d73 = d64;
}
if(v73 > v63){
v73 = v63;
d73 = d63;
}
if(v73 > v74){
v73 = v74;
d73 = d74;
}
v73 += p73;
}
if(p75 != 0){
v75 -= p75;
if(v75 > v64){
v75 = v64;
d75 = d64;
}
if(v75 > v65){
v75 = v65;
d75 = d65;
}
if(v75 > v74){
v75 = v74;
d75 = d74;
}
v75 += p75;
}
if(p42 != 0){
v42 -= p42;
if(v42 > v53){
v42 = v53;
d42 = d53;
}
if(v42 > v43){
v42 = v43;
d42 = d43;
}
if(v42 > v52){
v42 = v52;
d42 = d52;
}
if(v42 > v33){
v42 = v33;
d42 = d33;
}
v42 += p42;
}
if(p46 != 0){
v46 -= p46;
if(v46 > v55){
v46 = v55;
d46 = d55;
}
if(v46 > v45){
v46 = v45;
d46 = d45;
}
if(v46 > v56){
v46 = v56;
d46 = d56;
}
if(v46 > v35){
v46 = v35;
d46 = d35;
}
v46 += p46;
}
if(p62 != 0){
v62 -= p62;
if(v62 > v53){
v62 = v53;
d62 = d53;
}
if(v62 > v63){
v62 = v63;
d62 = d63;
}
if(v62 > v52){
v62 = v52;
d62 = d52;
}
if(v62 > v73){
v62 = v73;
d62 = d73;
}
v62 += p62;
}
if(p66 != 0){
v66 -= p66;
if(v66 > v55){
v66 = v55;
d66 = d55;
}
if(v66 > v65){
v66 = v65;
d66 = d65;
}
if(v66 > v56){
v66 = v56;
d66 = d56;
}
if(v66 > v75){
v66 = v75;
d66 = d75;
}
v66 += p66;
}
if(p32 != 0){
v32 -= p32;
if(v32 > v43){
v32 = v43;
d32 = d43;
}
if(v32 > v33){
v32 = v33;
d32 = d33;
}
if(v32 > v42){
v32 = v42;
d32 = d42;
}
v32 += p32;
}
if(p36 != 0){
v36 -= p36;
if(v36 > v45){
v36 = v45;
d36 = d45;
}
if(v36 > v46){
v36 = v46;
d36 = d46;
}
if(v36 > v35){
v36 = v35;
d36 = d35;
}
v36 += p36;
}
if(p72 != 0){
v72 -= p72;
if(v72 > v63){
v72 = v63;
d72 = d63;
}
if(v72 > v62){
v72 = v62;
d72 = d62;
}
if(v72 > v73){
v72 = v73;
d72 = d73;
}
v72 += p72;
}
if(p76 != 0){
v76 -= p76;
if(v76 > v65){
v76 = v65;
d76 = d65;
}
if(v76 > v66){
v76 = v66;
d76 = d66;
}
if(v76 > v75){
v76 = v75;
d76 = d75;
}
v76 += p76;
}
if(p24 != 0){
v24 -= p24;
if(v24 > v34){
v24 = v34;
d24 = d34;
}
if(v24 > v35){
v24 = v35;
d24 = d35;
}
if(v24 > v33){
v24 = v33;
d24 = d33;
}
v24 += p24;
}
if(p84 != 0){
v84 -= p84;
if(v84 > v74){
v84 = v74;
d84 = d74;
}
if(v84 > v73){
v84 = v73;
d84 = d73;
}
if(v84 > v75){
v84 = v75;
d84 = d75;
}
v84 += p84;
}
if(p51 != 0){
v51 -= p51;
if(v51 > v52){
v51 = v52;
d51 = d52;
}
if(v51 > v42){
v51 = v42;
d51 = d42;
}
if(v51 > v62){
v51 = v62;
d51 = d62;
}
v51 += p51;
}
if(p57 != 0){
v57 -= p57;
if(v57 > v56){
v57 = v56;
d57 = d56;
}
if(v57 > v46){
v57 = v46;
d57 = d46;
}
if(v57 > v66){
v57 = v66;
d57 = d66;
}
v57 += p57;
}
if(p23 != 0){
v23 -= p23;
if(v23 > v34){
v23 = v34;
d23 = d34;
}
if(v23 > v33){
v23 = v33;
d23 = d33;
}
if(v23 > v32){
v23 = v32;
d23 = d32;
}
if(v23 > v24){
v23 = v24;
d23 = d24;
}
v23 += p23;
}
if(p83 != 0){
v83 -= p83;
if(v83 > v74){
v83 = v74;
d83 = d74;
}
if(v83 > v73){
v83 = v73;
d83 = d73;
}
if(v83 > v72){
v83 = v72;
d83 = d72;
}
if(v83 > v84){
v83 = v84;
d83 = d84;
}
v83 += p83;
}
if(p85 != 0){
v85 -= p85;
if(v85 > v74){
v85 = v74;
d85 = d74;
}
if(v85 > v75){
v85 = v75;
d85 = d75;
}
if(v85 > v76){
v85 = v76;
d85 = d76;
}
if(v85 > v84){
v85 = v84;
d85 = d84;
}
v85 += p85;
}
if(p41 != 0){
v41 -= p41;
if(v41 > v52){
v41 = v52;
d41 = d52;
}
if(v41 > v42){
v41 = v42;
d41 = d42;
}
if(v41 > v32){
v41 = v32;
d41 = d32;
}
if(v41 > v51){
v41 = v51;
d41 = d51;
}
v41 += p41;
}
if(p61 != 0){
v61 -= p61;
if(v61 > v52){
v61 = v52;
d61 = d52;
}
if(v61 > v62){
v61 = v62;
d61 = d62;
}
if(v61 > v72){
v61 = v72;
d61 = d72;
}
if(v61 > v51){
v61 = v51;
d61 = d51;
}
v61 += p61;
}
if(p67 != 0){
v67 -= p67;
if(v67 > v56){
v67 = v56;
d67 = d56;
}
if(v67 > v66){
v67 = v66;
d67 = d66;
}
if(v67 > v76){
v67 = v76;
d67 = d76;
}
if(v67 > v57){
v67 = v57;
d67 = d57;
}
v67 += p67;
}
if(p22 != 0){
v22 -= p22;
if(v22 > v33){
v22 = v33;
d22 = d33;
}
if(v22 > v32){
v22 = v32;
d22 = d32;
}
if(v22 > v23){
v22 = v23;
d22 = d23;
}
v22 += p22;
}
if(p82 != 0){
v82 -= p82;
if(v82 > v73){
v82 = v73;
d82 = d73;
}
if(v82 > v72){
v82 = v72;
d82 = d72;
}
if(v82 > v83){
v82 = v83;
d82 = d83;
}
v82 += p82;
}
if(p86 != 0){
v86 -= p86;
if(v86 > v75){
v86 = v75;
d86 = d75;
}
if(v86 > v76){
v86 = v76;
d86 = d76;
}
if(v86 > v85){
v86 = v85;
d86 = d85;
}
v86 += p86;
}
if(p31 != 0){
v31 -= p31;
if(v31 > v42){
v31 = v42;
d31 = d42;
}
if(v31 > v32){
v31 = v32;
d31 = d32;
}
if(v31 > v41){
v31 = v41;
d31 = d41;
}
if(v31 > v22){
v31 = v22;
d31 = d22;
}
v31 += p31;
}
if(p71 != 0){
v71 -= p71;
if(v71 > v62){
v71 = v62;
d71 = d62;
}
if(v71 > v72){
v71 = v72;
d71 = d72;
}
if(v71 > v61){
v71 = v61;
d71 = d61;
}
if(v71 > v82){
v71 = v82;
d71 = d82;
}
v71 += p71;
}
if(p77 != 0){
v77 -= p77;
if(v77 > v66){
v77 = v66;
d77 = d66;
}
if(v77 > v76){
v77 = v76;
d77 = d76;
}
if(v77 > v67){
v77 = v67;
d77 = d67;
}
if(v77 > v86){
v77 = v86;
d77 = d86;
}
v77 += p77;
}
if(p21 != 0){
v21 -= p21;
if(v21 > v32){
v21 = v32;
d21 = d32;
}
if(v21 > v22){
v21 = v22;
d21 = d22;
}
if(v21 > v31){
v21 = v31;
d21 = d31;
}
v21 += p21;
}
if(p81 != 0){
v81 -= p81;
if(v81 > v72){
v81 = v72;
d81 = d72;
}
if(v81 > v71){
v81 = v71;
d81 = d71;
}
if(v81 > v82){
v81 = v82;
d81 = d82;
}
v81 += p81;
}
if(p87 != 0){
v87 -= p87;
if(v87 > v76){
v87 = v76;
d87 = d76;
}
if(v87 > v77){
v87 = v77;
d87 = d77;
}
if(v87 > v86){
v87 = v86;
d87 = d86;
}
v87 += p87;
}
if(p14 != 0){
v14 -= p14;
if(v14 > v24){
v14 = v24;
d14 = d24;
}
if(v14 > v23){
v14 = v23;
d14 = d23;
}
v14 += p14;
}
if(p94 != 0){
v94 -= p94;
if(v94 > v84){
v94 = v84;
d94 = d84;
}
if(v94 > v83){
v94 = v83;
d94 = d83;
}
if(v94 > v85){
v94 = v85;
d94 = d85;
}
v94 += p94;
}
if(p50 != 0){
v50 -= p50;
if(v50 > v51){
v50 = v51;
d50 = d51;
}
if(v50 > v41){
v50 = v41;
d50 = d41;
}
if(v50 > v61){
v50 = v61;
d50 = d61;
}
v50 += p50;
}
if(p58 != 0){
v58 -= p58;
if(v58 > v57){
v58 = v57;
d58 = d57;
}
if(v58 > v67){
v58 = v67;
d58 = d67;
}
v58 += p58;
}
if(p13 != 0){
v13 -= p13;
if(v13 > v24){
v13 = v24;
d13 = d24;
}
if(v13 > v23){
v13 = v23;
d13 = d23;
}
if(v13 > v22){
v13 = v22;
d13 = d22;
}
if(v13 > v14){
v13 = v14;
d13 = d14;
}
v13 += p13;
}
if(p93 != 0){
v93 -= p93;
if(v93 > v84){
v93 = v84;
d93 = d84;
}
if(v93 > v83){
v93 = v83;
d93 = d83;
}
if(v93 > v82){
v93 = v82;
d93 = d82;
}
if(v93 > v94){
v93 = v94;
d93 = d94;
}
v93 += p93;
}
if(p95 != 0){
v95 -= p95;
if(v95 > v84){
v95 = v84;
d95 = d84;
}
if(v95 > v85){
v95 = v85;
d95 = d85;
}
if(v95 > v86){
v95 = v86;
d95 = d86;
}
if(v95 > v94){
v95 = v94;
d95 = d94;
}
v95 += p95;
}
if(p40 != 0){
v40 -= p40;
if(v40 > v51){
v40 = v51;
d40 = d51;
}
if(v40 > v41){
v40 = v41;
d40 = d41;
}
if(v40 > v31){
v40 = v31;
d40 = d31;
}
if(v40 > v50){
v40 = v50;
d40 = d50;
}
v40 += p40;
}
if(p60 != 0){
v60 -= p60;
if(v60 > v51){
v60 = v51;
d60 = d51;
}
if(v60 > v61){
v60 = v61;
d60 = d61;
}
if(v60 > v71){
v60 = v71;
d60 = d71;
}
if(v60 > v50){
v60 = v50;
d60 = d50;
}
v60 += p60;
}
if(p68 != 0){
v68 -= p68;
if(v68 > v57){
v68 = v57;
d68 = d57;
}
if(v68 > v67){
v68 = v67;
d68 = d67;
}
if(v68 > v77){
v68 = v77;
d68 = d77;
}
if(v68 > v58){
v68 = v58;
d68 = d58;
}
v68 += p68;
}
if(p12 != 0){
v12 -= p12;
if(v12 > v23){
v12 = v23;
d12 = d23;
}
if(v12 > v22){
v12 = v22;
d12 = d22;
}
if(v12 > v13){
v12 = v13;
d12 = d13;
}
if(v12 > v21){
v12 = v21;
d12 = d21;
}
v12 += p12;
}
if(p92 != 0){
v92 -= p92;
if(v92 > v83){
v92 = v83;
d92 = d83;
}
if(v92 > v82){
v92 = v82;
d92 = d82;
}
if(v92 > v93){
v92 = v93;
d92 = d93;
}
if(v92 > v81){
v92 = v81;
d92 = d81;
}
v92 += p92;
}
if(p96 != 0){
v96 -= p96;
if(v96 > v85){
v96 = v85;
d96 = d85;
}
if(v96 > v86){
v96 = v86;
d96 = d86;
}
if(v96 > v95){
v96 = v95;
d96 = d95;
}
if(v96 > v87){
v96 = v87;
d96 = d87;
}
v96 += p96;
}
if(p30 != 0){
v30 -= p30;
if(v30 > v41){
v30 = v41;
d30 = d41;
}
if(v30 > v31){
v30 = v31;
d30 = d31;
}
if(v30 > v40){
v30 = v40;
d30 = d40;
}
if(v30 > v21){
v30 = v21;
d30 = d21;
}
v30 += p30;
}
if(p70 != 0){
v70 -= p70;
if(v70 > v61){
v70 = v61;
d70 = d61;
}
if(v70 > v71){
v70 = v71;
d70 = d71;
}
if(v70 > v60){
v70 = v60;
d70 = d60;
}
if(v70 > v81){
v70 = v81;
d70 = d81;
}
v70 += p70;
}
if(p78 != 0){
v78 -= p78;
if(v78 > v67){
v78 = v67;
d78 = d67;
}
if(v78 > v77){
v78 = v77;
d78 = d77;
}
if(v78 > v68){
v78 = v68;
d78 = d68;
}
if(v78 > v87){
v78 = v87;
d78 = d87;
}
v78 += p78;
}
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -4:
switch (dy){
case -2:
return d12;
case -1:
return d13;
case 0:
return d14;
}
break;
case -3:
switch (dy){
case -3:
return d21;
case -2:
return d22;
case -1:
return d23;
case 0:
return d24;
}
break;
case -2:
switch (dy){
case -4:
return d30;
case -3:
return d31;
case -2:
return d32;
case -1:
return d33;
case 0:
return d34;
case 1:
return d35;
case 2:
return d36;
}
break;
case -1:
switch (dy){
case -4:
return d40;
case -3:
return d41;
case -2:
return d42;
case -1:
return d43;
case 0:
return d44;
case 1:
return d45;
case 2:
return d46;
}
break;
case 0:
switch (dy){
case -4:
return d50;
case -3:
return d51;
case -2:
return d52;
case -1:
return d53;
case 0:
return d54;
case 1:
return d55;
case 2:
return d56;
case 3:
return d57;
case 4:
return d58;
}
break;
case 1:
switch (dy){
case -4:
return d60;
case -3:
return d61;
case -2:
return d62;
case -1:
return d63;
case 0:
return d64;
case 1:
return d65;
case 2:
return d66;
case 3:
return d67;
case 4:
return d68;
}
break;
case 2:
switch (dy){
case -4:
return d70;
case -3:
return d71;
case -2:
return d72;
case -1:
return d73;
case 0:
return d74;
case 1:
return d75;
case 2:
return d76;
case 3:
return d77;
case 4:
return d78;
}
break;
case 3:
switch (dy){
case -3:
return d81;
case -2:
return d82;
case -1:
return d83;
case 0:
return d84;
case 1:
return d85;
case 2:
return d86;
case 3:
return d87;
}
break;
case 4:
switch (dy){
case -2:
return d92;
case -1:
return d93;
case 0:
return d94;
case 1:
return d95;
case 2:
return d96;
}
break;
}
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l34.distanceSquaredTo(target)) / v34;
if(currScore > bestScore){
bestScore = currScore;
ans = d34;
}
currScore = (initialDist - l56.distanceSquaredTo(target)) / v56;
if(currScore > bestScore){
bestScore = currScore;
ans = d56;
}
currScore = (initialDist - l35.distanceSquaredTo(target)) / v35;
if(currScore > bestScore){
bestScore = currScore;
ans = d35;
}
currScore = (initialDist - l46.distanceSquaredTo(target)) / v46;
if(currScore > bestScore){
bestScore = currScore;
ans = d46;
}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
}
currScore = (initialDist - l24.distanceSquaredTo(target)) / v24;
if(currScore > bestScore){
bestScore = currScore;
ans = d24;
}
currScore = (initialDist - l57.distanceSquaredTo(target)) / v57;
if(currScore > bestScore){
bestScore = currScore;
ans = d57;
}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
}
currScore = (initialDist - l21.distanceSquaredTo(target)) / v21;
if(currScore > bestScore){
bestScore = currScore;
ans = d21;
}
currScore = (initialDist - l81.distanceSquaredTo(target)) / v81;
if(currScore > bestScore){
bestScore = currScore;
ans = d81;
}
currScore = (initialDist - l87.distanceSquaredTo(target)) / v87;
if(currScore > bestScore){
bestScore = currScore;
ans = d87;
}
currScore = (initialDist - l14.distanceSquaredTo(target)) / v14;
if(currScore > bestScore){
bestScore = currScore;
ans = d14;
}
currScore = (initialDist - l94.distanceSquaredTo(target)) / v94;
if(currScore > bestScore){
bestScore = currScore;
ans = d94;
}
currScore = (initialDist - l50.distanceSquaredTo(target)) / v50;
if(currScore > bestScore){
bestScore = currScore;
ans = d50;
}
currScore = (initialDist - l58.distanceSquaredTo(target)) / v58;
if(currScore > bestScore){
bestScore = currScore;
ans = d58;
}
currScore = (initialDist - l13.distanceSquaredTo(target)) / v13;
if(currScore > bestScore){
bestScore = currScore;
ans = d13;
}
currScore = (initialDist - l93.distanceSquaredTo(target)) / v93;
if(currScore > bestScore){
bestScore = currScore;
ans = d93;
}
currScore = (initialDist - l95.distanceSquaredTo(target)) / v95;
if(currScore > bestScore){
bestScore = currScore;
ans = d95;
}
currScore = (initialDist - l40.distanceSquaredTo(target)) / v40;
if(currScore > bestScore){
bestScore = currScore;
ans = d40;
}
currScore = (initialDist - l60.distanceSquaredTo(target)) / v60;
if(currScore > bestScore){
bestScore = currScore;
ans = d60;
}
currScore = (initialDist - l68.distanceSquaredTo(target)) / v68;
if(currScore > bestScore){
bestScore = currScore;
ans = d68;
}
currScore = (initialDist - l12.distanceSquaredTo(target)) / v12;
if(currScore > bestScore){
bestScore = currScore;
ans = d12;
}
currScore = (initialDist - l92.distanceSquaredTo(target)) / v92;
if(currScore > bestScore){
bestScore = currScore;
ans = d92;
}
currScore = (initialDist - l96.distanceSquaredTo(target)) / v96;
if(currScore > bestScore){
bestScore = currScore;
ans = d96;
}
currScore = (initialDist - l30.distanceSquaredTo(target)) / v30;
if(currScore > bestScore){
bestScore = currScore;
ans = d30;
}
currScore = (initialDist - l70.distanceSquaredTo(target)) / v70;
if(currScore > bestScore){
bestScore = currScore;
ans = d70;
}
currScore = (initialDist - l78.distanceSquaredTo(target)) / v78;
if(currScore > bestScore){
bestScore = currScore;
ans = d78;
}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSSouthwest(MapLocation target) throws GameActionException{
try{ 
	double sum;
if(p44 != 0){
if(!rc.isLocationOccupied(l44)){
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(p64 != 0){
if(!rc.isLocationOccupied(l64)){
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(p53 != 0){
if(!rc.isLocationOccupied(l53)){
v53 -= p53;
if(v53 > v54){
v53 = v54;
d53 = Direction.SOUTH;
}
if(v53 > v44){
v53 = v44;
d53 = d44;
}
if(v53 > v64){
v53 = v64;
d53 = d64;
}
v53 += p53;
}
}
if(p55 != 0){
if(!rc.isLocationOccupied(l55)){
v55 -= p55;
if(v55 > v54){
v55 = v54;
d55 = Direction.NORTH;
}
if(v55 > v44){
v55 = v44;
d55 = d44;
}
if(v55 > v64){
v55 = v64;
d55 = d64;
}
v55 += p55;
}
}
if(p43 != 0){
if(!rc.isLocationOccupied(l43)){
v43 -= p43;
if(v43 > v54){
v43 = v54;
d43 = Direction.SOUTHWEST;
}
if(v43 > v44){
v43 = v44;
d43 = d44;
}
if(v43 > v53){
v43 = v53;
d43 = d53;
}
v43 += p43;
}
}
if(p45 != 0){
if(!rc.isLocationOccupied(l45)){
v45 -= p45;
if(v45 > v54){
v45 = v54;
d45 = Direction.NORTHWEST;
}
if(v45 > v55){
v45 = v55;
d45 = d55;
}
if(v45 > v44){
v45 = v44;
d45 = d44;
}
v45 += p45;
}
}
if(p63 != 0){
if(!rc.isLocationOccupied(l63)){
v63 -= p63;
if(v63 > v54){
v63 = v54;
d63 = Direction.SOUTHEAST;
}
if(v63 > v53){
v63 = v53;
d63 = d53;
}
if(v63 > v64){
v63 = v64;
d63 = d64;
}
v63 += p63;
}
}
if(p65 != 0){
if(!rc.isLocationOccupied(l65)){
v65 -= p65;
if(v65 > v54){
v65 = v54;
d65 = Direction.NORTHEAST;
}
if(v65 > v55){
v65 = v55;
d65 = d55;
}
if(v65 > v64){
v65 = v64;
d65 = d64;
}
v65 += p65;
}
}
if(p34 != 0){
v34 -= p34;
if(v34 > v44){
v34 = v44;
d34 = d44;
}
if(v34 > v45){
v34 = v45;
d34 = d45;
}
if(v34 > v43){
v34 = v43;
d34 = d43;
}
v34 += p34;
}
if(p74 != 0){
v74 -= p74;
if(v74 > v64){
v74 = v64;
d74 = d64;
}
if(v74 > v63){
v74 = v63;
d74 = d63;
}
if(v74 > v65){
v74 = v65;
d74 = d65;
}
v74 += p74;
}
if(p52 != 0){
v52 -= p52;
if(v52 > v53){
v52 = v53;
d52 = d53;
}
if(v52 > v43){
v52 = v43;
d52 = d43;
}
if(v52 > v63){
v52 = v63;
d52 = d63;
}
v52 += p52;
}
if(p56 != 0){
v56 -= p56;
if(v56 > v55){
v56 = v55;
d56 = d55;
}
if(v56 > v45){
v56 = v45;
d56 = d45;
}
if(v56 > v65){
v56 = v65;
d56 = d65;
}
v56 += p56;
}
if(p33 != 0){
v33 -= p33;
if(v33 > v44){
v33 = v44;
d33 = d44;
}
if(v33 > v43){
v33 = v43;
d33 = d43;
}
if(v33 > v34){
v33 = v34;
d33 = d34;
}
v33 += p33;
}
if(p35 != 0){
v35 -= p35;
if(v35 > v44){
v35 = v44;
d35 = d44;
}
if(v35 > v45){
v35 = v45;
d35 = d45;
}
if(v35 > v34){
v35 = v34;
d35 = d34;
}
v35 += p35;
}
if(p73 != 0){
v73 -= p73;
if(v73 > v64){
v73 = v64;
d73 = d64;
}
if(v73 > v63){
v73 = v63;
d73 = d63;
}
if(v73 > v74){
v73 = v74;
d73 = d74;
}
v73 += p73;
}
if(p75 != 0){
v75 -= p75;
if(v75 > v64){
v75 = v64;
d75 = d64;
}
if(v75 > v65){
v75 = v65;
d75 = d65;
}
if(v75 > v74){
v75 = v74;
d75 = d74;
}
v75 += p75;
}
if(p42 != 0){
v42 -= p42;
if(v42 > v53){
v42 = v53;
d42 = d53;
}
if(v42 > v43){
v42 = v43;
d42 = d43;
}
if(v42 > v52){
v42 = v52;
d42 = d52;
}
if(v42 > v33){
v42 = v33;
d42 = d33;
}
v42 += p42;
}
if(p46 != 0){
v46 -= p46;
if(v46 > v55){
v46 = v55;
d46 = d55;
}
if(v46 > v45){
v46 = v45;
d46 = d45;
}
if(v46 > v56){
v46 = v56;
d46 = d56;
}
if(v46 > v35){
v46 = v35;
d46 = d35;
}
v46 += p46;
}
if(p62 != 0){
v62 -= p62;
if(v62 > v53){
v62 = v53;
d62 = d53;
}
if(v62 > v63){
v62 = v63;
d62 = d63;
}
if(v62 > v52){
v62 = v52;
d62 = d52;
}
if(v62 > v73){
v62 = v73;
d62 = d73;
}
v62 += p62;
}
if(p66 != 0){
v66 -= p66;
if(v66 > v55){
v66 = v55;
d66 = d55;
}
if(v66 > v65){
v66 = v65;
d66 = d65;
}
if(v66 > v56){
v66 = v56;
d66 = d56;
}
if(v66 > v75){
v66 = v75;
d66 = d75;
}
v66 += p66;
}
if(p32 != 0){
v32 -= p32;
if(v32 > v43){
v32 = v43;
d32 = d43;
}
if(v32 > v33){
v32 = v33;
d32 = d33;
}
if(v32 > v42){
v32 = v42;
d32 = d42;
}
v32 += p32;
}
if(p36 != 0){
v36 -= p36;
if(v36 > v45){
v36 = v45;
d36 = d45;
}
if(v36 > v46){
v36 = v46;
d36 = d46;
}
if(v36 > v35){
v36 = v35;
d36 = d35;
}
v36 += p36;
}
if(p72 != 0){
v72 -= p72;
if(v72 > v63){
v72 = v63;
d72 = d63;
}
if(v72 > v62){
v72 = v62;
d72 = d62;
}
if(v72 > v73){
v72 = v73;
d72 = d73;
}
v72 += p72;
}
if(p76 != 0){
v76 -= p76;
if(v76 > v65){
v76 = v65;
d76 = d65;
}
if(v76 > v66){
v76 = v66;
d76 = d66;
}
if(v76 > v75){
v76 = v75;
d76 = d75;
}
v76 += p76;
}
if(p24 != 0){
v24 -= p24;
if(v24 > v34){
v24 = v34;
d24 = d34;
}
if(v24 > v35){
v24 = v35;
d24 = d35;
}
if(v24 > v33){
v24 = v33;
d24 = d33;
}
v24 += p24;
}
if(p84 != 0){
v84 -= p84;
if(v84 > v74){
v84 = v74;
d84 = d74;
}
if(v84 > v73){
v84 = v73;
d84 = d73;
}
if(v84 > v75){
v84 = v75;
d84 = d75;
}
v84 += p84;
}
if(p51 != 0){
v51 -= p51;
if(v51 > v52){
v51 = v52;
d51 = d52;
}
if(v51 > v42){
v51 = v42;
d51 = d42;
}
if(v51 > v62){
v51 = v62;
d51 = d62;
}
v51 += p51;
}
if(p57 != 0){
v57 -= p57;
if(v57 > v56){
v57 = v56;
d57 = d56;
}
if(v57 > v46){
v57 = v46;
d57 = d46;
}
if(v57 > v66){
v57 = v66;
d57 = d66;
}
v57 += p57;
}
if(p23 != 0){
v23 -= p23;
if(v23 > v34){
v23 = v34;
d23 = d34;
}
if(v23 > v33){
v23 = v33;
d23 = d33;
}
if(v23 > v32){
v23 = v32;
d23 = d32;
}
if(v23 > v24){
v23 = v24;
d23 = d24;
}
v23 += p23;
}
if(p25 != 0){
v25 -= p25;
if(v25 > v34){
v25 = v34;
d25 = d34;
}
if(v25 > v35){
v25 = v35;
d25 = d35;
}
if(v25 > v36){
v25 = v36;
d25 = d36;
}
if(v25 > v24){
v25 = v24;
d25 = d24;
}
v25 += p25;
}
if(p83 != 0){
v83 -= p83;
if(v83 > v74){
v83 = v74;
d83 = d74;
}
if(v83 > v73){
v83 = v73;
d83 = d73;
}
if(v83 > v72){
v83 = v72;
d83 = d72;
}
if(v83 > v84){
v83 = v84;
d83 = d84;
}
v83 += p83;
}
if(p41 != 0){
v41 -= p41;
if(v41 > v52){
v41 = v52;
d41 = d52;
}
if(v41 > v42){
v41 = v42;
d41 = d42;
}
if(v41 > v32){
v41 = v32;
d41 = d32;
}
if(v41 > v51){
v41 = v51;
d41 = d51;
}
v41 += p41;
}
if(p47 != 0){
v47 -= p47;
if(v47 > v56){
v47 = v56;
d47 = d56;
}
if(v47 > v46){
v47 = v46;
d47 = d46;
}
if(v47 > v36){
v47 = v36;
d47 = d36;
}
if(v47 > v57){
v47 = v57;
d47 = d57;
}
v47 += p47;
}
if(p61 != 0){
v61 -= p61;
if(v61 > v52){
v61 = v52;
d61 = d52;
}
if(v61 > v62){
v61 = v62;
d61 = d62;
}
if(v61 > v72){
v61 = v72;
d61 = d72;
}
if(v61 > v51){
v61 = v51;
d61 = d51;
}
v61 += p61;
}
if(p22 != 0){
v22 -= p22;
if(v22 > v33){
v22 = v33;
d22 = d33;
}
if(v22 > v32){
v22 = v32;
d22 = d32;
}
if(v22 > v23){
v22 = v23;
d22 = d23;
}
v22 += p22;
}
if(p26 != 0){
v26 -= p26;
if(v26 > v35){
v26 = v35;
d26 = d35;
}
if(v26 > v36){
v26 = v36;
d26 = d36;
}
if(v26 > v25){
v26 = v25;
d26 = d25;
}
v26 += p26;
}
if(p82 != 0){
v82 -= p82;
if(v82 > v73){
v82 = v73;
d82 = d73;
}
if(v82 > v72){
v82 = v72;
d82 = d72;
}
if(v82 > v83){
v82 = v83;
d82 = d83;
}
v82 += p82;
}
if(p31 != 0){
v31 -= p31;
if(v31 > v42){
v31 = v42;
d31 = d42;
}
if(v31 > v32){
v31 = v32;
d31 = d32;
}
if(v31 > v41){
v31 = v41;
d31 = d41;
}
if(v31 > v22){
v31 = v22;
d31 = d22;
}
v31 += p31;
}
if(p37 != 0){
v37 -= p37;
if(v37 > v46){
v37 = v46;
d37 = d46;
}
if(v37 > v36){
v37 = v36;
d37 = d36;
}
if(v37 > v47){
v37 = v47;
d37 = d47;
}
if(v37 > v26){
v37 = v26;
d37 = d26;
}
v37 += p37;
}
if(p71 != 0){
v71 -= p71;
if(v71 > v62){
v71 = v62;
d71 = d62;
}
if(v71 > v72){
v71 = v72;
d71 = d72;
}
if(v71 > v61){
v71 = v61;
d71 = d61;
}
if(v71 > v82){
v71 = v82;
d71 = d82;
}
v71 += p71;
}
if(p21 != 0){
v21 -= p21;
if(v21 > v32){
v21 = v32;
d21 = d32;
}
if(v21 > v22){
v21 = v22;
d21 = d22;
}
if(v21 > v31){
v21 = v31;
d21 = d31;
}
v21 += p21;
}
if(p27 != 0){
v27 -= p27;
if(v27 > v36){
v27 = v36;
d27 = d36;
}
if(v27 > v37){
v27 = v37;
d27 = d37;
}
if(v27 > v26){
v27 = v26;
d27 = d26;
}
v27 += p27;
}
if(p81 != 0){
v81 -= p81;
if(v81 > v72){
v81 = v72;
d81 = d72;
}
if(v81 > v71){
v81 = v71;
d81 = d71;
}
if(v81 > v82){
v81 = v82;
d81 = d82;
}
v81 += p81;
}
if(p14 != 0){
v14 -= p14;
if(v14 > v24){
v14 = v24;
d14 = d24;
}
if(v14 > v25){
v14 = v25;
d14 = d25;
}
if(v14 > v23){
v14 = v23;
d14 = d23;
}
v14 += p14;
}
if(p94 != 0){
v94 -= p94;
if(v94 > v84){
v94 = v84;
d94 = d84;
}
if(v94 > v83){
v94 = v83;
d94 = d83;
}
v94 += p94;
}
if(p50 != 0){
v50 -= p50;
if(v50 > v51){
v50 = v51;
d50 = d51;
}
if(v50 > v41){
v50 = v41;
d50 = d41;
}
if(v50 > v61){
v50 = v61;
d50 = d61;
}
v50 += p50;
}
if(p58 != 0){
v58 -= p58;
if(v58 > v57){
v58 = v57;
d58 = d57;
}
if(v58 > v47){
v58 = v47;
d58 = d47;
}
v58 += p58;
}
if(p13 != 0){
v13 -= p13;
if(v13 > v24){
v13 = v24;
d13 = d24;
}
if(v13 > v23){
v13 = v23;
d13 = d23;
}
if(v13 > v22){
v13 = v22;
d13 = d22;
}
if(v13 > v14){
v13 = v14;
d13 = d14;
}
v13 += p13;
}
if(p15 != 0){
v15 -= p15;
if(v15 > v24){
v15 = v24;
d15 = d24;
}
if(v15 > v25){
v15 = v25;
d15 = d25;
}
if(v15 > v26){
v15 = v26;
d15 = d26;
}
if(v15 > v14){
v15 = v14;
d15 = d14;
}
v15 += p15;
}
if(p93 != 0){
v93 -= p93;
if(v93 > v84){
v93 = v84;
d93 = d84;
}
if(v93 > v83){
v93 = v83;
d93 = d83;
}
if(v93 > v82){
v93 = v82;
d93 = d82;
}
if(v93 > v94){
v93 = v94;
d93 = d94;
}
v93 += p93;
}
if(p40 != 0){
v40 -= p40;
if(v40 > v51){
v40 = v51;
d40 = d51;
}
if(v40 > v41){
v40 = v41;
d40 = d41;
}
if(v40 > v31){
v40 = v31;
d40 = d31;
}
if(v40 > v50){
v40 = v50;
d40 = d50;
}
v40 += p40;
}
if(p48 != 0){
v48 -= p48;
if(v48 > v57){
v48 = v57;
d48 = d57;
}
if(v48 > v47){
v48 = v47;
d48 = d47;
}
if(v48 > v37){
v48 = v37;
d48 = d37;
}
if(v48 > v58){
v48 = v58;
d48 = d58;
}
v48 += p48;
}
if(p60 != 0){
v60 -= p60;
if(v60 > v51){
v60 = v51;
d60 = d51;
}
if(v60 > v61){
v60 = v61;
d60 = d61;
}
if(v60 > v71){
v60 = v71;
d60 = d71;
}
if(v60 > v50){
v60 = v50;
d60 = d50;
}
v60 += p60;
}
if(p12 != 0){
v12 -= p12;
if(v12 > v23){
v12 = v23;
d12 = d23;
}
if(v12 > v22){
v12 = v22;
d12 = d22;
}
if(v12 > v13){
v12 = v13;
d12 = d13;
}
if(v12 > v21){
v12 = v21;
d12 = d21;
}
v12 += p12;
}
if(p16 != 0){
v16 -= p16;
if(v16 > v25){
v16 = v25;
d16 = d25;
}
if(v16 > v26){
v16 = v26;
d16 = d26;
}
if(v16 > v15){
v16 = v15;
d16 = d15;
}
if(v16 > v27){
v16 = v27;
d16 = d27;
}
v16 += p16;
}
if(p92 != 0){
v92 -= p92;
if(v92 > v83){
v92 = v83;
d92 = d83;
}
if(v92 > v82){
v92 = v82;
d92 = d82;
}
if(v92 > v93){
v92 = v93;
d92 = d93;
}
if(v92 > v81){
v92 = v81;
d92 = d81;
}
v92 += p92;
}
if(p30 != 0){
v30 -= p30;
if(v30 > v41){
v30 = v41;
d30 = d41;
}
if(v30 > v31){
v30 = v31;
d30 = d31;
}
if(v30 > v40){
v30 = v40;
d30 = d40;
}
if(v30 > v21){
v30 = v21;
d30 = d21;
}
v30 += p30;
}
if(p38 != 0){
v38 -= p38;
if(v38 > v47){
v38 = v47;
d38 = d47;
}
if(v38 > v37){
v38 = v37;
d38 = d37;
}
if(v38 > v48){
v38 = v48;
d38 = d48;
}
if(v38 > v27){
v38 = v27;
d38 = d27;
}
v38 += p38;
}
if(p70 != 0){
v70 -= p70;
if(v70 > v61){
v70 = v61;
d70 = d61;
}
if(v70 > v71){
v70 = v71;
d70 = d71;
}
if(v70 > v60){
v70 = v60;
d70 = d60;
}
if(v70 > v81){
v70 = v81;
d70 = d81;
}
v70 += p70;
}
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -4:
switch (dy){
case -2:
return d12;
case -1:
return d13;
case 0:
return d14;
case 1:
return d15;
case 2:
return d16;
}
break;
case -3:
switch (dy){
case -3:
return d21;
case -2:
return d22;
case -1:
return d23;
case 0:
return d24;
case 1:
return d25;
case 2:
return d26;
case 3:
return d27;
}
break;
case -2:
switch (dy){
case -4:
return d30;
case -3:
return d31;
case -2:
return d32;
case -1:
return d33;
case 0:
return d34;
case 1:
return d35;
case 2:
return d36;
case 3:
return d37;
case 4:
return d38;
}
break;
case -1:
switch (dy){
case -4:
return d40;
case -3:
return d41;
case -2:
return d42;
case -1:
return d43;
case 0:
return d44;
case 1:
return d45;
case 2:
return d46;
case 3:
return d47;
case 4:
return d48;
}
break;
case 0:
switch (dy){
case -4:
return d50;
case -3:
return d51;
case -2:
return d52;
case -1:
return d53;
case 0:
return d54;
case 1:
return d55;
case 2:
return d56;
case 3:
return d57;
case 4:
return d58;
}
break;
case 1:
switch (dy){
case -4:
return d60;
case -3:
return d61;
case -2:
return d62;
case -1:
return d63;
case 0:
return d64;
case 1:
return d65;
case 2:
return d66;
}
break;
case 2:
switch (dy){
case -4:
return d70;
case -3:
return d71;
case -2:
return d72;
case -1:
return d73;
case 0:
return d74;
case 1:
return d75;
case 2:
return d76;
}
break;
case 3:
switch (dy){
case -3:
return d81;
case -2:
return d82;
case -1:
return d83;
case 0:
return d84;
}
break;
case 4:
switch (dy){
case -2:
return d92;
case -1:
return d93;
case 0:
return d94;
}
break;
}
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l74.distanceSquaredTo(target)) / v74;
if(currScore > bestScore){
bestScore = currScore;
ans = d74;
}
currScore = (initialDist - l56.distanceSquaredTo(target)) / v56;
if(currScore > bestScore){
bestScore = currScore;
ans = d56;
}
currScore = (initialDist - l75.distanceSquaredTo(target)) / v75;
if(currScore > bestScore){
bestScore = currScore;
ans = d75;
}
currScore = (initialDist - l66.distanceSquaredTo(target)) / v66;
if(currScore > bestScore){
bestScore = currScore;
ans = d66;
}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
}
currScore = (initialDist - l84.distanceSquaredTo(target)) / v84;
if(currScore > bestScore){
bestScore = currScore;
ans = d84;
}
currScore = (initialDist - l57.distanceSquaredTo(target)) / v57;
if(currScore > bestScore){
bestScore = currScore;
ans = d57;
}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
}
currScore = (initialDist - l21.distanceSquaredTo(target)) / v21;
if(currScore > bestScore){
bestScore = currScore;
ans = d21;
}
currScore = (initialDist - l27.distanceSquaredTo(target)) / v27;
if(currScore > bestScore){
bestScore = currScore;
ans = d27;
}
currScore = (initialDist - l81.distanceSquaredTo(target)) / v81;
if(currScore > bestScore){
bestScore = currScore;
ans = d81;
}
currScore = (initialDist - l14.distanceSquaredTo(target)) / v14;
if(currScore > bestScore){
bestScore = currScore;
ans = d14;
}
currScore = (initialDist - l94.distanceSquaredTo(target)) / v94;
if(currScore > bestScore){
bestScore = currScore;
ans = d94;
}
currScore = (initialDist - l50.distanceSquaredTo(target)) / v50;
if(currScore > bestScore){
bestScore = currScore;
ans = d50;
}
currScore = (initialDist - l58.distanceSquaredTo(target)) / v58;
if(currScore > bestScore){
bestScore = currScore;
ans = d58;
}
currScore = (initialDist - l13.distanceSquaredTo(target)) / v13;
if(currScore > bestScore){
bestScore = currScore;
ans = d13;
}
currScore = (initialDist - l15.distanceSquaredTo(target)) / v15;
if(currScore > bestScore){
bestScore = currScore;
ans = d15;
}
currScore = (initialDist - l93.distanceSquaredTo(target)) / v93;
if(currScore > bestScore){
bestScore = currScore;
ans = d93;
}
currScore = (initialDist - l40.distanceSquaredTo(target)) / v40;
if(currScore > bestScore){
bestScore = currScore;
ans = d40;
}
currScore = (initialDist - l48.distanceSquaredTo(target)) / v48;
if(currScore > bestScore){
bestScore = currScore;
ans = d48;
}
currScore = (initialDist - l60.distanceSquaredTo(target)) / v60;
if(currScore > bestScore){
bestScore = currScore;
ans = d60;
}
currScore = (initialDist - l12.distanceSquaredTo(target)) / v12;
if(currScore > bestScore){
bestScore = currScore;
ans = d12;
}
currScore = (initialDist - l16.distanceSquaredTo(target)) / v16;
if(currScore > bestScore){
bestScore = currScore;
ans = d16;
}
currScore = (initialDist - l92.distanceSquaredTo(target)) / v92;
if(currScore > bestScore){
bestScore = currScore;
ans = d92;
}
currScore = (initialDist - l30.distanceSquaredTo(target)) / v30;
if(currScore > bestScore){
bestScore = currScore;
ans = d30;
}
currScore = (initialDist - l38.distanceSquaredTo(target)) / v38;
if(currScore > bestScore){
bestScore = currScore;
ans = d38;
}
currScore = (initialDist - l70.distanceSquaredTo(target)) / v70;
if(currScore > bestScore){
bestScore = currScore;
ans = d70;
}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}

    public Direction getBestDir(MapLocation target, int[][] heuristicMap) throws GameActionException {
        Direction targetDir = robot.myLoc.directionTo(target);
        if(!this.vars_are_reset){
            resetVars(heuristicMap);
        }
//        System.out.println("Running getBestDir: " + Clock.getBytecodesLeft());
        Direction output = null;
        switch(targetDir){
            case NORTH:
                output = runBFSNorth(target);
                break;
            case SOUTH:
                output = runBFSSouth(target);
                break;
            case EAST:
                output = runBFSEast(target);
                break;
            case WEST:
                output = runBFSWest(target);
                break;
            case NORTHEAST:
                output = runBFSNortheast(target);
                break;
            case NORTHWEST:
                output = runBFSNorthwest(target);
                break;
            case SOUTHEAST:
                output = runBFSSoutheast(target);
                break;
            case SOUTHWEST:
                output = runBFSSouthwest(target);
                break;
        }
        this.vars_are_reset = false;
        return output;
//        return runBFS(target);
//        System.out.println("ERROR DIRECTION UNKNOWN");
//        return null;
    }
}
