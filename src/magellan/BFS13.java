package magellan;
import battlecode.common.*;
public class BFS13 extends BFS{
BFS13(RobotController rc, Robot robot){
super(rc, robot);
}
static MapLocation l54;
static double v54;
static Direction d54;
static double p54;


static MapLocation l44;
static double v44;
static Direction d44;
static double p44;


static MapLocation l64;
static double v64;
static Direction d64;
static double p64;


static MapLocation l53;
static double v53;
static Direction d53;
static double p53;


static MapLocation l55;
static double v55;
static Direction d55;
static double p55;


static MapLocation l43;
static double v43;
static Direction d43;
static double p43;


static MapLocation l45;
static double v45;
static Direction d45;
static double p45;


static MapLocation l63;
static double v63;
static Direction d63;
static double p63;


static MapLocation l65;
static double v65;
static Direction d65;
static double p65;


static MapLocation l34;
static double v34;
static Direction d34;
static double p34;


static MapLocation l74;
static double v74;
static Direction d74;
static double p74;


static MapLocation l52;
static double v52;
static Direction d52;
static double p52;


static MapLocation l56;
static double v56;
static Direction d56;
static double p56;


static MapLocation l33;
static double v33;
static Direction d33;
static double p33;


static MapLocation l35;
static double v35;
static Direction d35;
static double p35;


static MapLocation l73;
static double v73;
static Direction d73;
static double p73;


static MapLocation l75;
static double v75;
static Direction d75;
static double p75;


static MapLocation l42;
static double v42;
static Direction d42;
static double p42;


static MapLocation l46;
static double v46;
static Direction d46;
static double p46;


static MapLocation l62;
static double v62;
static Direction d62;
static double p62;


static MapLocation l66;
static double v66;
static Direction d66;
static double p66;


static MapLocation l32;
static double v32;
static Direction d32;
static double p32;


static MapLocation l36;
static double v36;
static Direction d36;
static double p36;


static MapLocation l72;
static double v72;
static Direction d72;
static double p72;


static MapLocation l76;
static double v76;
static Direction d76;
static double p76;


static MapLocation l24;
static double v24;
static Direction d24;
static double p24;


static MapLocation l84;
static double v84;
static Direction d84;
static double p84;


static MapLocation l51;
static double v51;
static Direction d51;
static double p51;


static MapLocation l57;
static double v57;
static Direction d57;
static double p57;


static MapLocation l23;
static double v23;
static Direction d23;
static double p23;


static MapLocation l25;
static double v25;
static Direction d25;
static double p25;


static MapLocation l83;
static double v83;
static Direction d83;
static double p83;


static MapLocation l85;
static double v85;
static Direction d85;
static double p85;


static MapLocation l41;
static double v41;
static Direction d41;
static double p41;


static MapLocation l47;
static double v47;
static Direction d47;
static double p47;


static MapLocation l61;
static double v61;
static Direction d61;
static double p61;


static MapLocation l67;
static double v67;
static Direction d67;
static double p67;


static MapLocation l22;
static double v22;
static Direction d22;
static double p22;


static MapLocation l26;
static double v26;
static Direction d26;
static double p26;


static MapLocation l82;
static double v82;
static Direction d82;
static double p82;


static MapLocation l86;
static double v86;
static Direction d86;
static double p86;


static MapLocation l31;
static double v31;
static Direction d31;
static double p31;


static MapLocation l37;
static double v37;
static Direction d37;
static double p37;


static MapLocation l71;
static double v71;
static Direction d71;
static double p71;


static MapLocation l77;
static double v77;
static Direction d77;
static double p77;


public void resetVars() throws GameActionException{
l54 = robot.myLoc;
v54 = 0;

l44 = l54.add(Direction.WEST);
v44 = 100000000;
d44 = null;

l64 = l54.add(Direction.EAST);
v64 = 100000000;
d64 = null;

l53 = l54.add(Direction.SOUTH);
v53 = 100000000;
d53 = null;

l55 = l54.add(Direction.NORTH);
v55 = 100000000;
d55 = null;

l43 = l44.add(Direction.SOUTH);
v43 = 100000000;
d43 = null;

l45 = l55.add(Direction.WEST);
v45 = 100000000;
d45 = null;

l63 = l53.add(Direction.EAST);
v63 = 100000000;
d63 = null;

l65 = l55.add(Direction.EAST);
v65 = 100000000;
d65 = null;

l34 = l44.add(Direction.WEST);
v34 = 100000000;
d34 = null;

l74 = l64.add(Direction.EAST);
v74 = 100000000;
d74 = null;

l52 = l53.add(Direction.SOUTH);
v52 = 100000000;
d52 = null;

l56 = l55.add(Direction.NORTH);
v56 = 100000000;
d56 = null;

l33 = l34.add(Direction.SOUTH);
v33 = 100000000;
d33 = null;

l35 = l45.add(Direction.WEST);
v35 = 100000000;
d35 = null;

l73 = l63.add(Direction.EAST);
v73 = 100000000;
d73 = null;

l75 = l65.add(Direction.EAST);
v75 = 100000000;
d75 = null;

l42 = l43.add(Direction.SOUTH);
v42 = 100000000;
d42 = null;

l46 = l56.add(Direction.WEST);
v46 = 100000000;
d46 = null;

l62 = l52.add(Direction.EAST);
v62 = 100000000;
d62 = null;

l66 = l56.add(Direction.EAST);
v66 = 100000000;
d66 = null;

l32 = l33.add(Direction.SOUTH);
v32 = 100000000;
d32 = null;

l36 = l46.add(Direction.WEST);
v36 = 100000000;
d36 = null;

l72 = l62.add(Direction.EAST);
v72 = 100000000;
d72 = null;

l76 = l66.add(Direction.EAST);
v76 = 100000000;
d76 = null;

l24 = l34.add(Direction.WEST);
v24 = 100000000;
d24 = null;

l84 = l74.add(Direction.EAST);
v84 = 100000000;
d84 = null;

l51 = l52.add(Direction.SOUTH);
v51 = 100000000;
d51 = null;

l57 = l56.add(Direction.NORTH);
v57 = 100000000;
d57 = null;

l23 = l24.add(Direction.SOUTH);
v23 = 100000000;
d23 = null;

l25 = l35.add(Direction.WEST);
v25 = 100000000;
d25 = null;

l83 = l73.add(Direction.EAST);
v83 = 100000000;
d83 = null;

l85 = l75.add(Direction.EAST);
v85 = 100000000;
d85 = null;

l41 = l42.add(Direction.SOUTH);
v41 = 100000000;
d41 = null;

l47 = l57.add(Direction.WEST);
v47 = 100000000;
d47 = null;

l61 = l51.add(Direction.EAST);
v61 = 100000000;
d61 = null;

l67 = l57.add(Direction.EAST);
v67 = 100000000;
d67 = null;

l22 = l23.add(Direction.SOUTH);
v22 = 100000000;
d22 = null;

l26 = l36.add(Direction.WEST);
v26 = 100000000;
d26 = null;

l82 = l72.add(Direction.EAST);
v82 = 100000000;
d82 = null;

l86 = l76.add(Direction.EAST);
v86 = 100000000;
d86 = null;

l31 = l32.add(Direction.SOUTH);
v31 = 100000000;
d31 = null;

l37 = l47.add(Direction.WEST);
v37 = 100000000;
d37 = null;

l71 = l61.add(Direction.EAST);
v71 = 100000000;
d71 = null;

l77 = l67.add(Direction.EAST);
v77 = 100000000;
d77 = null;

this.vars_are_reset = true;
System.out.println("Finished Initializing Variables: " + Clock.getBytecodesLeft());
}

Direction runBFSNorth(MapLocation target) throws GameActionException{
System.out.println("Starting BFS Method: " + Clock.getBytecodesLeft());
try{ 
	double sum;
if(rc.onTheMap(l44) && rc.sensePassability(l44)){
if(!rc.isLocationOccupied(l44)){
p44 = 1.0;
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(rc.onTheMap(l64) && rc.sensePassability(l64)){
if(!rc.isLocationOccupied(l64)){
p64 = 1.0;
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(rc.onTheMap(l53) && rc.sensePassability(l53)){
if(!rc.isLocationOccupied(l53)){
p53 = 1.0;
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
if(rc.onTheMap(l55) && rc.sensePassability(l55)){
if(!rc.isLocationOccupied(l55)){
p55 = 1.0;
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
if(rc.onTheMap(l43) && rc.sensePassability(l43)){
if(!rc.isLocationOccupied(l43)){
p43 = 1.0;
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
if(rc.onTheMap(l45) && rc.sensePassability(l45)){
if(!rc.isLocationOccupied(l45)){
p45 = 1.0;
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
if(rc.onTheMap(l63) && rc.sensePassability(l63)){
if(!rc.isLocationOccupied(l63)){
p63 = 1.0;
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
if(rc.onTheMap(l65) && rc.sensePassability(l65)){
if(!rc.isLocationOccupied(l65)){
p65 = 1.0;
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
if(rc.onTheMap(l34) && rc.sensePassability(l34)){
p34 = 1.0;
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
if(rc.onTheMap(l74) && rc.sensePassability(l74)){
p74 = 1.0;
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
if(rc.onTheMap(l52) && rc.sensePassability(l52)){
p52 = 1.0;
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
if(rc.onTheMap(l56) && rc.sensePassability(l56)){
p56 = 1.0;
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
if(rc.onTheMap(l33) && rc.sensePassability(l33)){
p33 = 1.0;
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
if(rc.onTheMap(l35) && rc.sensePassability(l35)){
p35 = 1.0;
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
if(rc.onTheMap(l73) && rc.sensePassability(l73)){
p73 = 1.0;
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
if(rc.onTheMap(l75) && rc.sensePassability(l75)){
p75 = 1.0;
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
if(rc.onTheMap(l42) && rc.sensePassability(l42)){
p42 = 1.0;
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
if(rc.onTheMap(l46) && rc.sensePassability(l46)){
p46 = 1.0;
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
if(rc.onTheMap(l62) && rc.sensePassability(l62)){
p62 = 1.0;
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
if(rc.onTheMap(l66) && rc.sensePassability(l66)){
p66 = 1.0;
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
if(rc.onTheMap(l32) && rc.sensePassability(l32)){
p32 = 1.0;
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
if(rc.onTheMap(l36) && rc.sensePassability(l36)){
p36 = 1.0;
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
if(rc.onTheMap(l72) && rc.sensePassability(l72)){
p72 = 1.0;
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
if(rc.onTheMap(l76) && rc.sensePassability(l76)){
p76 = 1.0;
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
if(rc.onTheMap(l24) && rc.sensePassability(l24)){
p24 = 1.0;
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
if(rc.onTheMap(l84) && rc.sensePassability(l84)){
p84 = 1.0;
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
if(rc.onTheMap(l57) && rc.sensePassability(l57)){
p57 = 1.0;
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
if(rc.onTheMap(l23) && rc.sensePassability(l23)){
p23 = 1.0;
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
if(rc.onTheMap(l25) && rc.sensePassability(l25)){
p25 = 1.0;
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
if(rc.onTheMap(l83) && rc.sensePassability(l83)){
p83 = 1.0;
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
if(rc.onTheMap(l85) && rc.sensePassability(l85)){
p85 = 1.0;
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
if(rc.onTheMap(l47) && rc.sensePassability(l47)){
p47 = 1.0;
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
if(rc.onTheMap(l67) && rc.sensePassability(l67)){
p67 = 1.0;
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
if(rc.onTheMap(l22) && rc.sensePassability(l22)){
p22 = 1.0;
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
if(rc.onTheMap(l26) && rc.sensePassability(l26)){
p26 = 1.0;
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
if(rc.onTheMap(l82) && rc.sensePassability(l82)){
p82 = 1.0;
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
if(rc.onTheMap(l86) && rc.sensePassability(l86)){
p86 = 1.0;
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
if(rc.onTheMap(l37) && rc.sensePassability(l37)){
p37 = 1.0;
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
if(rc.onTheMap(l77) && rc.sensePassability(l77)){
p77 = 1.0;
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
System.out.println("Ran BFS: " + Clock.getBytecodesLeft());
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
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
}
break;
}
System.out.println("Didn't find within radius, gonna use distance heuristic: " + Clock.getBytecodesLeft());
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l52.distanceSquaredTo(target)) / v52;
if(currScore > bestScore){
bestScore = currScore;
ans = d52;
System.out.println("Best end location: " + l52.toString());}
currScore = (initialDist - l42.distanceSquaredTo(target)) / v42;
if(currScore > bestScore){
bestScore = currScore;
ans = d42;
System.out.println("Best end location: " + l42.toString());}
currScore = (initialDist - l62.distanceSquaredTo(target)) / v62;
if(currScore > bestScore){
bestScore = currScore;
ans = d62;
System.out.println("Best end location: " + l62.toString());}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
System.out.println("Best end location: " + l32.toString());}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
System.out.println("Best end location: " + l36.toString());}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
System.out.println("Best end location: " + l72.toString());}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
System.out.println("Best end location: " + l76.toString());}
currScore = (initialDist - l24.distanceSquaredTo(target)) / v24;
if(currScore > bestScore){
bestScore = currScore;
ans = d24;
System.out.println("Best end location: " + l24.toString());}
currScore = (initialDist - l84.distanceSquaredTo(target)) / v84;
if(currScore > bestScore){
bestScore = currScore;
ans = d84;
System.out.println("Best end location: " + l84.toString());}
currScore = (initialDist - l57.distanceSquaredTo(target)) / v57;
if(currScore > bestScore){
bestScore = currScore;
ans = d57;
System.out.println("Best end location: " + l57.toString());}
currScore = (initialDist - l23.distanceSquaredTo(target)) / v23;
if(currScore > bestScore){
bestScore = currScore;
ans = d23;
System.out.println("Best end location: " + l23.toString());}
currScore = (initialDist - l25.distanceSquaredTo(target)) / v25;
if(currScore > bestScore){
bestScore = currScore;
ans = d25;
System.out.println("Best end location: " + l25.toString());}
currScore = (initialDist - l83.distanceSquaredTo(target)) / v83;
if(currScore > bestScore){
bestScore = currScore;
ans = d83;
System.out.println("Best end location: " + l83.toString());}
currScore = (initialDist - l85.distanceSquaredTo(target)) / v85;
if(currScore > bestScore){
bestScore = currScore;
ans = d85;
System.out.println("Best end location: " + l85.toString());}
currScore = (initialDist - l47.distanceSquaredTo(target)) / v47;
if(currScore > bestScore){
bestScore = currScore;
ans = d47;
System.out.println("Best end location: " + l47.toString());}
currScore = (initialDist - l67.distanceSquaredTo(target)) / v67;
if(currScore > bestScore){
bestScore = currScore;
ans = d67;
System.out.println("Best end location: " + l67.toString());}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
System.out.println("Best end location: " + l22.toString());}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
System.out.println("Best end location: " + l26.toString());}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
System.out.println("Best end location: " + l82.toString());}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
System.out.println("Best end location: " + l86.toString());}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
System.out.println("Best end location: " + l37.toString());}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
System.out.println("Best end location: " + l77.toString());}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSSouth(MapLocation target) throws GameActionException{
System.out.println("Starting BFS Method: " + Clock.getBytecodesLeft());
try{ 
	double sum;
if(rc.onTheMap(l44) && rc.sensePassability(l44)){
if(!rc.isLocationOccupied(l44)){
p44 = 1.0;
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(rc.onTheMap(l64) && rc.sensePassability(l64)){
if(!rc.isLocationOccupied(l64)){
p64 = 1.0;
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(rc.onTheMap(l53) && rc.sensePassability(l53)){
if(!rc.isLocationOccupied(l53)){
p53 = 1.0;
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
if(rc.onTheMap(l55) && rc.sensePassability(l55)){
if(!rc.isLocationOccupied(l55)){
p55 = 1.0;
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
if(rc.onTheMap(l43) && rc.sensePassability(l43)){
if(!rc.isLocationOccupied(l43)){
p43 = 1.0;
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
if(rc.onTheMap(l45) && rc.sensePassability(l45)){
if(!rc.isLocationOccupied(l45)){
p45 = 1.0;
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
if(rc.onTheMap(l63) && rc.sensePassability(l63)){
if(!rc.isLocationOccupied(l63)){
p63 = 1.0;
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
if(rc.onTheMap(l65) && rc.sensePassability(l65)){
if(!rc.isLocationOccupied(l65)){
p65 = 1.0;
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
if(rc.onTheMap(l34) && rc.sensePassability(l34)){
p34 = 1.0;
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
if(rc.onTheMap(l74) && rc.sensePassability(l74)){
p74 = 1.0;
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
if(rc.onTheMap(l52) && rc.sensePassability(l52)){
p52 = 1.0;
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
if(rc.onTheMap(l56) && rc.sensePassability(l56)){
p56 = 1.0;
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
if(rc.onTheMap(l33) && rc.sensePassability(l33)){
p33 = 1.0;
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
if(rc.onTheMap(l35) && rc.sensePassability(l35)){
p35 = 1.0;
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
if(rc.onTheMap(l73) && rc.sensePassability(l73)){
p73 = 1.0;
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
if(rc.onTheMap(l75) && rc.sensePassability(l75)){
p75 = 1.0;
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
if(rc.onTheMap(l42) && rc.sensePassability(l42)){
p42 = 1.0;
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
if(rc.onTheMap(l46) && rc.sensePassability(l46)){
p46 = 1.0;
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
if(rc.onTheMap(l62) && rc.sensePassability(l62)){
p62 = 1.0;
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
if(rc.onTheMap(l66) && rc.sensePassability(l66)){
p66 = 1.0;
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
if(rc.onTheMap(l32) && rc.sensePassability(l32)){
p32 = 1.0;
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
if(rc.onTheMap(l36) && rc.sensePassability(l36)){
p36 = 1.0;
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
if(rc.onTheMap(l72) && rc.sensePassability(l72)){
p72 = 1.0;
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
if(rc.onTheMap(l76) && rc.sensePassability(l76)){
p76 = 1.0;
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
if(rc.onTheMap(l24) && rc.sensePassability(l24)){
p24 = 1.0;
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
if(rc.onTheMap(l84) && rc.sensePassability(l84)){
p84 = 1.0;
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
if(rc.onTheMap(l51) && rc.sensePassability(l51)){
p51 = 1.0;
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
if(rc.onTheMap(l23) && rc.sensePassability(l23)){
p23 = 1.0;
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
if(rc.onTheMap(l25) && rc.sensePassability(l25)){
p25 = 1.0;
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
if(rc.onTheMap(l83) && rc.sensePassability(l83)){
p83 = 1.0;
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
if(rc.onTheMap(l85) && rc.sensePassability(l85)){
p85 = 1.0;
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
if(rc.onTheMap(l41) && rc.sensePassability(l41)){
p41 = 1.0;
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
if(rc.onTheMap(l61) && rc.sensePassability(l61)){
p61 = 1.0;
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
if(rc.onTheMap(l22) && rc.sensePassability(l22)){
p22 = 1.0;
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
if(rc.onTheMap(l26) && rc.sensePassability(l26)){
p26 = 1.0;
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
if(rc.onTheMap(l82) && rc.sensePassability(l82)){
p82 = 1.0;
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
if(rc.onTheMap(l86) && rc.sensePassability(l86)){
p86 = 1.0;
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
if(rc.onTheMap(l31) && rc.sensePassability(l31)){
p31 = 1.0;
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
if(rc.onTheMap(l71) && rc.sensePassability(l71)){
p71 = 1.0;
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
System.out.println("Ran BFS: " + Clock.getBytecodesLeft());
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
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
}
break;
case -2:
switch (dy){
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
}
System.out.println("Didn't find within radius, gonna use distance heuristic: " + Clock.getBytecodesLeft());
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l56.distanceSquaredTo(target)) / v56;
if(currScore > bestScore){
bestScore = currScore;
ans = d56;
System.out.println("Best end location: " + l56.toString());}
currScore = (initialDist - l46.distanceSquaredTo(target)) / v46;
if(currScore > bestScore){
bestScore = currScore;
ans = d46;
System.out.println("Best end location: " + l46.toString());}
currScore = (initialDist - l66.distanceSquaredTo(target)) / v66;
if(currScore > bestScore){
bestScore = currScore;
ans = d66;
System.out.println("Best end location: " + l66.toString());}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
System.out.println("Best end location: " + l32.toString());}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
System.out.println("Best end location: " + l36.toString());}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
System.out.println("Best end location: " + l72.toString());}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
System.out.println("Best end location: " + l76.toString());}
currScore = (initialDist - l24.distanceSquaredTo(target)) / v24;
if(currScore > bestScore){
bestScore = currScore;
ans = d24;
System.out.println("Best end location: " + l24.toString());}
currScore = (initialDist - l84.distanceSquaredTo(target)) / v84;
if(currScore > bestScore){
bestScore = currScore;
ans = d84;
System.out.println("Best end location: " + l84.toString());}
currScore = (initialDist - l51.distanceSquaredTo(target)) / v51;
if(currScore > bestScore){
bestScore = currScore;
ans = d51;
System.out.println("Best end location: " + l51.toString());}
currScore = (initialDist - l23.distanceSquaredTo(target)) / v23;
if(currScore > bestScore){
bestScore = currScore;
ans = d23;
System.out.println("Best end location: " + l23.toString());}
currScore = (initialDist - l25.distanceSquaredTo(target)) / v25;
if(currScore > bestScore){
bestScore = currScore;
ans = d25;
System.out.println("Best end location: " + l25.toString());}
currScore = (initialDist - l83.distanceSquaredTo(target)) / v83;
if(currScore > bestScore){
bestScore = currScore;
ans = d83;
System.out.println("Best end location: " + l83.toString());}
currScore = (initialDist - l85.distanceSquaredTo(target)) / v85;
if(currScore > bestScore){
bestScore = currScore;
ans = d85;
System.out.println("Best end location: " + l85.toString());}
currScore = (initialDist - l41.distanceSquaredTo(target)) / v41;
if(currScore > bestScore){
bestScore = currScore;
ans = d41;
System.out.println("Best end location: " + l41.toString());}
currScore = (initialDist - l61.distanceSquaredTo(target)) / v61;
if(currScore > bestScore){
bestScore = currScore;
ans = d61;
System.out.println("Best end location: " + l61.toString());}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
System.out.println("Best end location: " + l22.toString());}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
System.out.println("Best end location: " + l26.toString());}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
System.out.println("Best end location: " + l82.toString());}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
System.out.println("Best end location: " + l86.toString());}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
System.out.println("Best end location: " + l31.toString());}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
System.out.println("Best end location: " + l71.toString());}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSEast(MapLocation target) throws GameActionException{
System.out.println("Starting BFS Method: " + Clock.getBytecodesLeft());
try{ 
	double sum;
if(rc.onTheMap(l44) && rc.sensePassability(l44)){
if(!rc.isLocationOccupied(l44)){
p44 = 1.0;
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(rc.onTheMap(l64) && rc.sensePassability(l64)){
if(!rc.isLocationOccupied(l64)){
p64 = 1.0;
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(rc.onTheMap(l53) && rc.sensePassability(l53)){
if(!rc.isLocationOccupied(l53)){
p53 = 1.0;
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
if(rc.onTheMap(l55) && rc.sensePassability(l55)){
if(!rc.isLocationOccupied(l55)){
p55 = 1.0;
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
if(rc.onTheMap(l43) && rc.sensePassability(l43)){
if(!rc.isLocationOccupied(l43)){
p43 = 1.0;
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
if(rc.onTheMap(l45) && rc.sensePassability(l45)){
if(!rc.isLocationOccupied(l45)){
p45 = 1.0;
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
if(rc.onTheMap(l63) && rc.sensePassability(l63)){
if(!rc.isLocationOccupied(l63)){
p63 = 1.0;
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
if(rc.onTheMap(l65) && rc.sensePassability(l65)){
if(!rc.isLocationOccupied(l65)){
p65 = 1.0;
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
if(rc.onTheMap(l34) && rc.sensePassability(l34)){
p34 = 1.0;
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
if(rc.onTheMap(l74) && rc.sensePassability(l74)){
p74 = 1.0;
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
if(rc.onTheMap(l52) && rc.sensePassability(l52)){
p52 = 1.0;
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
if(rc.onTheMap(l56) && rc.sensePassability(l56)){
p56 = 1.0;
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
if(rc.onTheMap(l33) && rc.sensePassability(l33)){
p33 = 1.0;
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
if(rc.onTheMap(l35) && rc.sensePassability(l35)){
p35 = 1.0;
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
if(rc.onTheMap(l73) && rc.sensePassability(l73)){
p73 = 1.0;
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
if(rc.onTheMap(l75) && rc.sensePassability(l75)){
p75 = 1.0;
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
if(rc.onTheMap(l42) && rc.sensePassability(l42)){
p42 = 1.0;
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
if(rc.onTheMap(l46) && rc.sensePassability(l46)){
p46 = 1.0;
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
if(rc.onTheMap(l62) && rc.sensePassability(l62)){
p62 = 1.0;
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
if(rc.onTheMap(l66) && rc.sensePassability(l66)){
p66 = 1.0;
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
if(rc.onTheMap(l32) && rc.sensePassability(l32)){
p32 = 1.0;
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
if(rc.onTheMap(l36) && rc.sensePassability(l36)){
p36 = 1.0;
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
if(rc.onTheMap(l72) && rc.sensePassability(l72)){
p72 = 1.0;
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
if(rc.onTheMap(l76) && rc.sensePassability(l76)){
p76 = 1.0;
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
if(rc.onTheMap(l84) && rc.sensePassability(l84)){
p84 = 1.0;
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
if(rc.onTheMap(l51) && rc.sensePassability(l51)){
p51 = 1.0;
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
if(rc.onTheMap(l57) && rc.sensePassability(l57)){
p57 = 1.0;
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
if(rc.onTheMap(l83) && rc.sensePassability(l83)){
p83 = 1.0;
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
if(rc.onTheMap(l85) && rc.sensePassability(l85)){
p85 = 1.0;
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
if(rc.onTheMap(l41) && rc.sensePassability(l41)){
p41 = 1.0;
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
if(rc.onTheMap(l47) && rc.sensePassability(l47)){
p47 = 1.0;
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
if(rc.onTheMap(l61) && rc.sensePassability(l61)){
p61 = 1.0;
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
if(rc.onTheMap(l67) && rc.sensePassability(l67)){
p67 = 1.0;
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
if(rc.onTheMap(l82) && rc.sensePassability(l82)){
p82 = 1.0;
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
if(rc.onTheMap(l86) && rc.sensePassability(l86)){
p86 = 1.0;
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
if(rc.onTheMap(l31) && rc.sensePassability(l31)){
p31 = 1.0;
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
if(rc.onTheMap(l37) && rc.sensePassability(l37)){
p37 = 1.0;
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
if(rc.onTheMap(l71) && rc.sensePassability(l71)){
p71 = 1.0;
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
if(rc.onTheMap(l77) && rc.sensePassability(l77)){
p77 = 1.0;
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
System.out.println("Ran BFS: " + Clock.getBytecodesLeft());
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -2:
switch (dy){
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
}
break;
case -1:
switch (dy){
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
}
break;
case 0:
switch (dy){
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
}
break;
case 1:
switch (dy){
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
}
break;
case 2:
switch (dy){
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
}
break;
}
System.out.println("Didn't find within radius, gonna use distance heuristic: " + Clock.getBytecodesLeft());
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l34.distanceSquaredTo(target)) / v34;
if(currScore > bestScore){
bestScore = currScore;
ans = d34;
System.out.println("Best end location: " + l34.toString());}
currScore = (initialDist - l33.distanceSquaredTo(target)) / v33;
if(currScore > bestScore){
bestScore = currScore;
ans = d33;
System.out.println("Best end location: " + l33.toString());}
currScore = (initialDist - l35.distanceSquaredTo(target)) / v35;
if(currScore > bestScore){
bestScore = currScore;
ans = d35;
System.out.println("Best end location: " + l35.toString());}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
System.out.println("Best end location: " + l32.toString());}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
System.out.println("Best end location: " + l36.toString());}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
System.out.println("Best end location: " + l72.toString());}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
System.out.println("Best end location: " + l76.toString());}
currScore = (initialDist - l84.distanceSquaredTo(target)) / v84;
if(currScore > bestScore){
bestScore = currScore;
ans = d84;
System.out.println("Best end location: " + l84.toString());}
currScore = (initialDist - l51.distanceSquaredTo(target)) / v51;
if(currScore > bestScore){
bestScore = currScore;
ans = d51;
System.out.println("Best end location: " + l51.toString());}
currScore = (initialDist - l57.distanceSquaredTo(target)) / v57;
if(currScore > bestScore){
bestScore = currScore;
ans = d57;
System.out.println("Best end location: " + l57.toString());}
currScore = (initialDist - l83.distanceSquaredTo(target)) / v83;
if(currScore > bestScore){
bestScore = currScore;
ans = d83;
System.out.println("Best end location: " + l83.toString());}
currScore = (initialDist - l85.distanceSquaredTo(target)) / v85;
if(currScore > bestScore){
bestScore = currScore;
ans = d85;
System.out.println("Best end location: " + l85.toString());}
currScore = (initialDist - l41.distanceSquaredTo(target)) / v41;
if(currScore > bestScore){
bestScore = currScore;
ans = d41;
System.out.println("Best end location: " + l41.toString());}
currScore = (initialDist - l47.distanceSquaredTo(target)) / v47;
if(currScore > bestScore){
bestScore = currScore;
ans = d47;
System.out.println("Best end location: " + l47.toString());}
currScore = (initialDist - l61.distanceSquaredTo(target)) / v61;
if(currScore > bestScore){
bestScore = currScore;
ans = d61;
System.out.println("Best end location: " + l61.toString());}
currScore = (initialDist - l67.distanceSquaredTo(target)) / v67;
if(currScore > bestScore){
bestScore = currScore;
ans = d67;
System.out.println("Best end location: " + l67.toString());}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
System.out.println("Best end location: " + l82.toString());}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
System.out.println("Best end location: " + l86.toString());}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
System.out.println("Best end location: " + l31.toString());}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
System.out.println("Best end location: " + l37.toString());}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
System.out.println("Best end location: " + l71.toString());}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
System.out.println("Best end location: " + l77.toString());}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSWest(MapLocation target) throws GameActionException{
System.out.println("Starting BFS Method: " + Clock.getBytecodesLeft());
try{ 
	double sum;
if(rc.onTheMap(l44) && rc.sensePassability(l44)){
if(!rc.isLocationOccupied(l44)){
p44 = 1.0;
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(rc.onTheMap(l64) && rc.sensePassability(l64)){
if(!rc.isLocationOccupied(l64)){
p64 = 1.0;
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(rc.onTheMap(l53) && rc.sensePassability(l53)){
if(!rc.isLocationOccupied(l53)){
p53 = 1.0;
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
if(rc.onTheMap(l55) && rc.sensePassability(l55)){
if(!rc.isLocationOccupied(l55)){
p55 = 1.0;
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
if(rc.onTheMap(l43) && rc.sensePassability(l43)){
if(!rc.isLocationOccupied(l43)){
p43 = 1.0;
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
if(rc.onTheMap(l45) && rc.sensePassability(l45)){
if(!rc.isLocationOccupied(l45)){
p45 = 1.0;
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
if(rc.onTheMap(l63) && rc.sensePassability(l63)){
if(!rc.isLocationOccupied(l63)){
p63 = 1.0;
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
if(rc.onTheMap(l65) && rc.sensePassability(l65)){
if(!rc.isLocationOccupied(l65)){
p65 = 1.0;
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
if(rc.onTheMap(l34) && rc.sensePassability(l34)){
p34 = 1.0;
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
if(rc.onTheMap(l74) && rc.sensePassability(l74)){
p74 = 1.0;
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
if(rc.onTheMap(l52) && rc.sensePassability(l52)){
p52 = 1.0;
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
if(rc.onTheMap(l56) && rc.sensePassability(l56)){
p56 = 1.0;
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
if(rc.onTheMap(l33) && rc.sensePassability(l33)){
p33 = 1.0;
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
if(rc.onTheMap(l35) && rc.sensePassability(l35)){
p35 = 1.0;
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
if(rc.onTheMap(l73) && rc.sensePassability(l73)){
p73 = 1.0;
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
if(rc.onTheMap(l75) && rc.sensePassability(l75)){
p75 = 1.0;
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
if(rc.onTheMap(l42) && rc.sensePassability(l42)){
p42 = 1.0;
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
if(rc.onTheMap(l46) && rc.sensePassability(l46)){
p46 = 1.0;
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
if(rc.onTheMap(l62) && rc.sensePassability(l62)){
p62 = 1.0;
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
if(rc.onTheMap(l66) && rc.sensePassability(l66)){
p66 = 1.0;
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
if(rc.onTheMap(l32) && rc.sensePassability(l32)){
p32 = 1.0;
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
if(rc.onTheMap(l36) && rc.sensePassability(l36)){
p36 = 1.0;
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
if(rc.onTheMap(l72) && rc.sensePassability(l72)){
p72 = 1.0;
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
if(rc.onTheMap(l76) && rc.sensePassability(l76)){
p76 = 1.0;
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
if(rc.onTheMap(l24) && rc.sensePassability(l24)){
p24 = 1.0;
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
if(rc.onTheMap(l51) && rc.sensePassability(l51)){
p51 = 1.0;
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
if(rc.onTheMap(l57) && rc.sensePassability(l57)){
p57 = 1.0;
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
if(rc.onTheMap(l23) && rc.sensePassability(l23)){
p23 = 1.0;
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
if(rc.onTheMap(l25) && rc.sensePassability(l25)){
p25 = 1.0;
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
if(rc.onTheMap(l41) && rc.sensePassability(l41)){
p41 = 1.0;
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
if(rc.onTheMap(l47) && rc.sensePassability(l47)){
p47 = 1.0;
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
if(rc.onTheMap(l61) && rc.sensePassability(l61)){
p61 = 1.0;
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
if(rc.onTheMap(l67) && rc.sensePassability(l67)){
p67 = 1.0;
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
if(rc.onTheMap(l22) && rc.sensePassability(l22)){
p22 = 1.0;
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
if(rc.onTheMap(l26) && rc.sensePassability(l26)){
p26 = 1.0;
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
if(rc.onTheMap(l31) && rc.sensePassability(l31)){
p31 = 1.0;
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
if(rc.onTheMap(l37) && rc.sensePassability(l37)){
p37 = 1.0;
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
if(rc.onTheMap(l71) && rc.sensePassability(l71)){
p71 = 1.0;
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
if(rc.onTheMap(l77) && rc.sensePassability(l77)){
p77 = 1.0;
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
System.out.println("Ran BFS: " + Clock.getBytecodesLeft());
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
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
}
break;
case -2:
switch (dy){
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
}
break;
case -1:
switch (dy){
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
}
break;
case 0:
switch (dy){
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
}
break;
case 1:
switch (dy){
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
}
break;
case 2:
switch (dy){
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
}
break;
}
System.out.println("Didn't find within radius, gonna use distance heuristic: " + Clock.getBytecodesLeft());
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l74.distanceSquaredTo(target)) / v74;
if(currScore > bestScore){
bestScore = currScore;
ans = d74;
System.out.println("Best end location: " + l74.toString());}
currScore = (initialDist - l73.distanceSquaredTo(target)) / v73;
if(currScore > bestScore){
bestScore = currScore;
ans = d73;
System.out.println("Best end location: " + l73.toString());}
currScore = (initialDist - l75.distanceSquaredTo(target)) / v75;
if(currScore > bestScore){
bestScore = currScore;
ans = d75;
System.out.println("Best end location: " + l75.toString());}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
System.out.println("Best end location: " + l32.toString());}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
System.out.println("Best end location: " + l36.toString());}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
System.out.println("Best end location: " + l72.toString());}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
System.out.println("Best end location: " + l76.toString());}
currScore = (initialDist - l24.distanceSquaredTo(target)) / v24;
if(currScore > bestScore){
bestScore = currScore;
ans = d24;
System.out.println("Best end location: " + l24.toString());}
currScore = (initialDist - l51.distanceSquaredTo(target)) / v51;
if(currScore > bestScore){
bestScore = currScore;
ans = d51;
System.out.println("Best end location: " + l51.toString());}
currScore = (initialDist - l57.distanceSquaredTo(target)) / v57;
if(currScore > bestScore){
bestScore = currScore;
ans = d57;
System.out.println("Best end location: " + l57.toString());}
currScore = (initialDist - l23.distanceSquaredTo(target)) / v23;
if(currScore > bestScore){
bestScore = currScore;
ans = d23;
System.out.println("Best end location: " + l23.toString());}
currScore = (initialDist - l25.distanceSquaredTo(target)) / v25;
if(currScore > bestScore){
bestScore = currScore;
ans = d25;
System.out.println("Best end location: " + l25.toString());}
currScore = (initialDist - l41.distanceSquaredTo(target)) / v41;
if(currScore > bestScore){
bestScore = currScore;
ans = d41;
System.out.println("Best end location: " + l41.toString());}
currScore = (initialDist - l47.distanceSquaredTo(target)) / v47;
if(currScore > bestScore){
bestScore = currScore;
ans = d47;
System.out.println("Best end location: " + l47.toString());}
currScore = (initialDist - l61.distanceSquaredTo(target)) / v61;
if(currScore > bestScore){
bestScore = currScore;
ans = d61;
System.out.println("Best end location: " + l61.toString());}
currScore = (initialDist - l67.distanceSquaredTo(target)) / v67;
if(currScore > bestScore){
bestScore = currScore;
ans = d67;
System.out.println("Best end location: " + l67.toString());}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
System.out.println("Best end location: " + l22.toString());}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
System.out.println("Best end location: " + l26.toString());}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
System.out.println("Best end location: " + l31.toString());}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
System.out.println("Best end location: " + l37.toString());}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
System.out.println("Best end location: " + l71.toString());}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
System.out.println("Best end location: " + l77.toString());}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSNortheast(MapLocation target) throws GameActionException{
System.out.println("Starting BFS Method: " + Clock.getBytecodesLeft());
try{ 
	double sum;
if(rc.onTheMap(l44) && rc.sensePassability(l44)){
if(!rc.isLocationOccupied(l44)){
p44 = 1.0;
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(rc.onTheMap(l64) && rc.sensePassability(l64)){
if(!rc.isLocationOccupied(l64)){
p64 = 1.0;
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(rc.onTheMap(l53) && rc.sensePassability(l53)){
if(!rc.isLocationOccupied(l53)){
p53 = 1.0;
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
if(rc.onTheMap(l55) && rc.sensePassability(l55)){
if(!rc.isLocationOccupied(l55)){
p55 = 1.0;
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
if(rc.onTheMap(l43) && rc.sensePassability(l43)){
if(!rc.isLocationOccupied(l43)){
p43 = 1.0;
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
if(rc.onTheMap(l45) && rc.sensePassability(l45)){
if(!rc.isLocationOccupied(l45)){
p45 = 1.0;
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
if(rc.onTheMap(l63) && rc.sensePassability(l63)){
if(!rc.isLocationOccupied(l63)){
p63 = 1.0;
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
if(rc.onTheMap(l65) && rc.sensePassability(l65)){
if(!rc.isLocationOccupied(l65)){
p65 = 1.0;
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
if(rc.onTheMap(l34) && rc.sensePassability(l34)){
p34 = 1.0;
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
if(rc.onTheMap(l74) && rc.sensePassability(l74)){
p74 = 1.0;
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
if(rc.onTheMap(l52) && rc.sensePassability(l52)){
p52 = 1.0;
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
if(rc.onTheMap(l56) && rc.sensePassability(l56)){
p56 = 1.0;
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
if(rc.onTheMap(l33) && rc.sensePassability(l33)){
p33 = 1.0;
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
if(rc.onTheMap(l35) && rc.sensePassability(l35)){
p35 = 1.0;
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
if(rc.onTheMap(l73) && rc.sensePassability(l73)){
p73 = 1.0;
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
if(rc.onTheMap(l75) && rc.sensePassability(l75)){
p75 = 1.0;
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
if(rc.onTheMap(l42) && rc.sensePassability(l42)){
p42 = 1.0;
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
if(rc.onTheMap(l46) && rc.sensePassability(l46)){
p46 = 1.0;
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
if(rc.onTheMap(l62) && rc.sensePassability(l62)){
p62 = 1.0;
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
if(rc.onTheMap(l66) && rc.sensePassability(l66)){
p66 = 1.0;
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
if(rc.onTheMap(l32) && rc.sensePassability(l32)){
p32 = 1.0;
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
if(rc.onTheMap(l36) && rc.sensePassability(l36)){
p36 = 1.0;
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
if(rc.onTheMap(l72) && rc.sensePassability(l72)){
p72 = 1.0;
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
if(rc.onTheMap(l76) && rc.sensePassability(l76)){
p76 = 1.0;
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
if(rc.onTheMap(l24) && rc.sensePassability(l24)){
p24 = 1.0;
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
if(rc.onTheMap(l84) && rc.sensePassability(l84)){
p84 = 1.0;
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
if(rc.onTheMap(l51) && rc.sensePassability(l51)){
p51 = 1.0;
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
if(rc.onTheMap(l57) && rc.sensePassability(l57)){
p57 = 1.0;
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
if(rc.onTheMap(l25) && rc.sensePassability(l25)){
p25 = 1.0;
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
if(rc.onTheMap(l83) && rc.sensePassability(l83)){
p83 = 1.0;
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
if(rc.onTheMap(l85) && rc.sensePassability(l85)){
p85 = 1.0;
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
if(rc.onTheMap(l47) && rc.sensePassability(l47)){
p47 = 1.0;
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
if(rc.onTheMap(l61) && rc.sensePassability(l61)){
p61 = 1.0;
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
if(rc.onTheMap(l67) && rc.sensePassability(l67)){
p67 = 1.0;
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
if(rc.onTheMap(l26) && rc.sensePassability(l26)){
p26 = 1.0;
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
if(rc.onTheMap(l82) && rc.sensePassability(l82)){
p82 = 1.0;
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
if(rc.onTheMap(l86) && rc.sensePassability(l86)){
p86 = 1.0;
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
if(rc.onTheMap(l37) && rc.sensePassability(l37)){
p37 = 1.0;
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
if(rc.onTheMap(l71) && rc.sensePassability(l71)){
p71 = 1.0;
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
if(rc.onTheMap(l77) && rc.sensePassability(l77)){
p77 = 1.0;
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
System.out.println("Ran BFS: " + Clock.getBytecodesLeft());
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -3:
switch (dy){
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
}
break;
case 0:
switch (dy){
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
}
break;
case 1:
switch (dy){
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
}
break;
case 2:
switch (dy){
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
}
break;
}
System.out.println("Didn't find within radius, gonna use distance heuristic: " + Clock.getBytecodesLeft());
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l34.distanceSquaredTo(target)) / v34;
if(currScore > bestScore){
bestScore = currScore;
ans = d34;
System.out.println("Best end location: " + l34.toString());}
currScore = (initialDist - l52.distanceSquaredTo(target)) / v52;
if(currScore > bestScore){
bestScore = currScore;
ans = d52;
System.out.println("Best end location: " + l52.toString());}
currScore = (initialDist - l33.distanceSquaredTo(target)) / v33;
if(currScore > bestScore){
bestScore = currScore;
ans = d33;
System.out.println("Best end location: " + l33.toString());}
currScore = (initialDist - l42.distanceSquaredTo(target)) / v42;
if(currScore > bestScore){
bestScore = currScore;
ans = d42;
System.out.println("Best end location: " + l42.toString());}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
System.out.println("Best end location: " + l32.toString());}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
System.out.println("Best end location: " + l36.toString());}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
System.out.println("Best end location: " + l72.toString());}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
System.out.println("Best end location: " + l76.toString());}
currScore = (initialDist - l24.distanceSquaredTo(target)) / v24;
if(currScore > bestScore){
bestScore = currScore;
ans = d24;
System.out.println("Best end location: " + l24.toString());}
currScore = (initialDist - l84.distanceSquaredTo(target)) / v84;
if(currScore > bestScore){
bestScore = currScore;
ans = d84;
System.out.println("Best end location: " + l84.toString());}
currScore = (initialDist - l51.distanceSquaredTo(target)) / v51;
if(currScore > bestScore){
bestScore = currScore;
ans = d51;
System.out.println("Best end location: " + l51.toString());}
currScore = (initialDist - l57.distanceSquaredTo(target)) / v57;
if(currScore > bestScore){
bestScore = currScore;
ans = d57;
System.out.println("Best end location: " + l57.toString());}
currScore = (initialDist - l25.distanceSquaredTo(target)) / v25;
if(currScore > bestScore){
bestScore = currScore;
ans = d25;
System.out.println("Best end location: " + l25.toString());}
currScore = (initialDist - l83.distanceSquaredTo(target)) / v83;
if(currScore > bestScore){
bestScore = currScore;
ans = d83;
System.out.println("Best end location: " + l83.toString());}
currScore = (initialDist - l85.distanceSquaredTo(target)) / v85;
if(currScore > bestScore){
bestScore = currScore;
ans = d85;
System.out.println("Best end location: " + l85.toString());}
currScore = (initialDist - l47.distanceSquaredTo(target)) / v47;
if(currScore > bestScore){
bestScore = currScore;
ans = d47;
System.out.println("Best end location: " + l47.toString());}
currScore = (initialDist - l61.distanceSquaredTo(target)) / v61;
if(currScore > bestScore){
bestScore = currScore;
ans = d61;
System.out.println("Best end location: " + l61.toString());}
currScore = (initialDist - l67.distanceSquaredTo(target)) / v67;
if(currScore > bestScore){
bestScore = currScore;
ans = d67;
System.out.println("Best end location: " + l67.toString());}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
System.out.println("Best end location: " + l26.toString());}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
System.out.println("Best end location: " + l82.toString());}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
System.out.println("Best end location: " + l86.toString());}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
System.out.println("Best end location: " + l37.toString());}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
System.out.println("Best end location: " + l71.toString());}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
System.out.println("Best end location: " + l77.toString());}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSNorthwest(MapLocation target) throws GameActionException{
System.out.println("Starting BFS Method: " + Clock.getBytecodesLeft());
try{ 
	double sum;
if(rc.onTheMap(l44) && rc.sensePassability(l44)){
if(!rc.isLocationOccupied(l44)){
p44 = 1.0;
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(rc.onTheMap(l64) && rc.sensePassability(l64)){
if(!rc.isLocationOccupied(l64)){
p64 = 1.0;
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(rc.onTheMap(l53) && rc.sensePassability(l53)){
if(!rc.isLocationOccupied(l53)){
p53 = 1.0;
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
if(rc.onTheMap(l55) && rc.sensePassability(l55)){
if(!rc.isLocationOccupied(l55)){
p55 = 1.0;
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
if(rc.onTheMap(l43) && rc.sensePassability(l43)){
if(!rc.isLocationOccupied(l43)){
p43 = 1.0;
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
if(rc.onTheMap(l45) && rc.sensePassability(l45)){
if(!rc.isLocationOccupied(l45)){
p45 = 1.0;
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
if(rc.onTheMap(l63) && rc.sensePassability(l63)){
if(!rc.isLocationOccupied(l63)){
p63 = 1.0;
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
if(rc.onTheMap(l65) && rc.sensePassability(l65)){
if(!rc.isLocationOccupied(l65)){
p65 = 1.0;
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
if(rc.onTheMap(l34) && rc.sensePassability(l34)){
p34 = 1.0;
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
if(rc.onTheMap(l74) && rc.sensePassability(l74)){
p74 = 1.0;
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
if(rc.onTheMap(l52) && rc.sensePassability(l52)){
p52 = 1.0;
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
if(rc.onTheMap(l56) && rc.sensePassability(l56)){
p56 = 1.0;
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
if(rc.onTheMap(l33) && rc.sensePassability(l33)){
p33 = 1.0;
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
if(rc.onTheMap(l35) && rc.sensePassability(l35)){
p35 = 1.0;
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
if(rc.onTheMap(l73) && rc.sensePassability(l73)){
p73 = 1.0;
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
if(rc.onTheMap(l75) && rc.sensePassability(l75)){
p75 = 1.0;
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
if(rc.onTheMap(l42) && rc.sensePassability(l42)){
p42 = 1.0;
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
if(rc.onTheMap(l46) && rc.sensePassability(l46)){
p46 = 1.0;
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
if(rc.onTheMap(l62) && rc.sensePassability(l62)){
p62 = 1.0;
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
if(rc.onTheMap(l66) && rc.sensePassability(l66)){
p66 = 1.0;
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
if(rc.onTheMap(l32) && rc.sensePassability(l32)){
p32 = 1.0;
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
if(rc.onTheMap(l36) && rc.sensePassability(l36)){
p36 = 1.0;
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
if(rc.onTheMap(l72) && rc.sensePassability(l72)){
p72 = 1.0;
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
if(rc.onTheMap(l76) && rc.sensePassability(l76)){
p76 = 1.0;
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
if(rc.onTheMap(l24) && rc.sensePassability(l24)){
p24 = 1.0;
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
if(rc.onTheMap(l84) && rc.sensePassability(l84)){
p84 = 1.0;
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
if(rc.onTheMap(l51) && rc.sensePassability(l51)){
p51 = 1.0;
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
if(rc.onTheMap(l57) && rc.sensePassability(l57)){
p57 = 1.0;
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
if(rc.onTheMap(l23) && rc.sensePassability(l23)){
p23 = 1.0;
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
if(rc.onTheMap(l25) && rc.sensePassability(l25)){
p25 = 1.0;
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
if(rc.onTheMap(l85) && rc.sensePassability(l85)){
p85 = 1.0;
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
if(rc.onTheMap(l41) && rc.sensePassability(l41)){
p41 = 1.0;
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
if(rc.onTheMap(l47) && rc.sensePassability(l47)){
p47 = 1.0;
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
if(rc.onTheMap(l67) && rc.sensePassability(l67)){
p67 = 1.0;
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
if(rc.onTheMap(l22) && rc.sensePassability(l22)){
p22 = 1.0;
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
if(rc.onTheMap(l26) && rc.sensePassability(l26)){
p26 = 1.0;
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
if(rc.onTheMap(l86) && rc.sensePassability(l86)){
p86 = 1.0;
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
if(rc.onTheMap(l31) && rc.sensePassability(l31)){
p31 = 1.0;
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
if(rc.onTheMap(l37) && rc.sensePassability(l37)){
p37 = 1.0;
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
if(rc.onTheMap(l77) && rc.sensePassability(l77)){
p77 = 1.0;
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
System.out.println("Ran BFS: " + Clock.getBytecodesLeft());
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
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
}
break;
case -2:
switch (dy){
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
}
break;
case -1:
switch (dy){
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
}
break;
case 0:
switch (dy){
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
}
break;
}
System.out.println("Didn't find within radius, gonna use distance heuristic: " + Clock.getBytecodesLeft());
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l74.distanceSquaredTo(target)) / v74;
if(currScore > bestScore){
bestScore = currScore;
ans = d74;
System.out.println("Best end location: " + l74.toString());}
currScore = (initialDist - l52.distanceSquaredTo(target)) / v52;
if(currScore > bestScore){
bestScore = currScore;
ans = d52;
System.out.println("Best end location: " + l52.toString());}
currScore = (initialDist - l73.distanceSquaredTo(target)) / v73;
if(currScore > bestScore){
bestScore = currScore;
ans = d73;
System.out.println("Best end location: " + l73.toString());}
currScore = (initialDist - l62.distanceSquaredTo(target)) / v62;
if(currScore > bestScore){
bestScore = currScore;
ans = d62;
System.out.println("Best end location: " + l62.toString());}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
System.out.println("Best end location: " + l32.toString());}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
System.out.println("Best end location: " + l36.toString());}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
System.out.println("Best end location: " + l72.toString());}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
System.out.println("Best end location: " + l76.toString());}
currScore = (initialDist - l24.distanceSquaredTo(target)) / v24;
if(currScore > bestScore){
bestScore = currScore;
ans = d24;
System.out.println("Best end location: " + l24.toString());}
currScore = (initialDist - l84.distanceSquaredTo(target)) / v84;
if(currScore > bestScore){
bestScore = currScore;
ans = d84;
System.out.println("Best end location: " + l84.toString());}
currScore = (initialDist - l51.distanceSquaredTo(target)) / v51;
if(currScore > bestScore){
bestScore = currScore;
ans = d51;
System.out.println("Best end location: " + l51.toString());}
currScore = (initialDist - l57.distanceSquaredTo(target)) / v57;
if(currScore > bestScore){
bestScore = currScore;
ans = d57;
System.out.println("Best end location: " + l57.toString());}
currScore = (initialDist - l23.distanceSquaredTo(target)) / v23;
if(currScore > bestScore){
bestScore = currScore;
ans = d23;
System.out.println("Best end location: " + l23.toString());}
currScore = (initialDist - l25.distanceSquaredTo(target)) / v25;
if(currScore > bestScore){
bestScore = currScore;
ans = d25;
System.out.println("Best end location: " + l25.toString());}
currScore = (initialDist - l85.distanceSquaredTo(target)) / v85;
if(currScore > bestScore){
bestScore = currScore;
ans = d85;
System.out.println("Best end location: " + l85.toString());}
currScore = (initialDist - l41.distanceSquaredTo(target)) / v41;
if(currScore > bestScore){
bestScore = currScore;
ans = d41;
System.out.println("Best end location: " + l41.toString());}
currScore = (initialDist - l47.distanceSquaredTo(target)) / v47;
if(currScore > bestScore){
bestScore = currScore;
ans = d47;
System.out.println("Best end location: " + l47.toString());}
currScore = (initialDist - l67.distanceSquaredTo(target)) / v67;
if(currScore > bestScore){
bestScore = currScore;
ans = d67;
System.out.println("Best end location: " + l67.toString());}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
System.out.println("Best end location: " + l22.toString());}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
System.out.println("Best end location: " + l26.toString());}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
System.out.println("Best end location: " + l86.toString());}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
System.out.println("Best end location: " + l31.toString());}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
System.out.println("Best end location: " + l37.toString());}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
System.out.println("Best end location: " + l77.toString());}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSSoutheast(MapLocation target) throws GameActionException{
System.out.println("Starting BFS Method: " + Clock.getBytecodesLeft());
try{ 
	double sum;
if(rc.onTheMap(l44) && rc.sensePassability(l44)){
if(!rc.isLocationOccupied(l44)){
p44 = 1.0;
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(rc.onTheMap(l64) && rc.sensePassability(l64)){
if(!rc.isLocationOccupied(l64)){
p64 = 1.0;
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(rc.onTheMap(l53) && rc.sensePassability(l53)){
if(!rc.isLocationOccupied(l53)){
p53 = 1.0;
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
if(rc.onTheMap(l55) && rc.sensePassability(l55)){
if(!rc.isLocationOccupied(l55)){
p55 = 1.0;
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
if(rc.onTheMap(l43) && rc.sensePassability(l43)){
if(!rc.isLocationOccupied(l43)){
p43 = 1.0;
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
if(rc.onTheMap(l45) && rc.sensePassability(l45)){
if(!rc.isLocationOccupied(l45)){
p45 = 1.0;
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
if(rc.onTheMap(l63) && rc.sensePassability(l63)){
if(!rc.isLocationOccupied(l63)){
p63 = 1.0;
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
if(rc.onTheMap(l65) && rc.sensePassability(l65)){
if(!rc.isLocationOccupied(l65)){
p65 = 1.0;
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
if(rc.onTheMap(l34) && rc.sensePassability(l34)){
p34 = 1.0;
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
if(rc.onTheMap(l74) && rc.sensePassability(l74)){
p74 = 1.0;
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
if(rc.onTheMap(l52) && rc.sensePassability(l52)){
p52 = 1.0;
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
if(rc.onTheMap(l56) && rc.sensePassability(l56)){
p56 = 1.0;
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
if(rc.onTheMap(l33) && rc.sensePassability(l33)){
p33 = 1.0;
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
if(rc.onTheMap(l35) && rc.sensePassability(l35)){
p35 = 1.0;
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
if(rc.onTheMap(l73) && rc.sensePassability(l73)){
p73 = 1.0;
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
if(rc.onTheMap(l75) && rc.sensePassability(l75)){
p75 = 1.0;
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
if(rc.onTheMap(l42) && rc.sensePassability(l42)){
p42 = 1.0;
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
if(rc.onTheMap(l46) && rc.sensePassability(l46)){
p46 = 1.0;
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
if(rc.onTheMap(l62) && rc.sensePassability(l62)){
p62 = 1.0;
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
if(rc.onTheMap(l66) && rc.sensePassability(l66)){
p66 = 1.0;
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
if(rc.onTheMap(l32) && rc.sensePassability(l32)){
p32 = 1.0;
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
if(rc.onTheMap(l36) && rc.sensePassability(l36)){
p36 = 1.0;
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
if(rc.onTheMap(l72) && rc.sensePassability(l72)){
p72 = 1.0;
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
if(rc.onTheMap(l76) && rc.sensePassability(l76)){
p76 = 1.0;
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
if(rc.onTheMap(l24) && rc.sensePassability(l24)){
p24 = 1.0;
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
if(rc.onTheMap(l84) && rc.sensePassability(l84)){
p84 = 1.0;
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
if(rc.onTheMap(l51) && rc.sensePassability(l51)){
p51 = 1.0;
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
if(rc.onTheMap(l57) && rc.sensePassability(l57)){
p57 = 1.0;
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
if(rc.onTheMap(l23) && rc.sensePassability(l23)){
p23 = 1.0;
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
if(rc.onTheMap(l83) && rc.sensePassability(l83)){
p83 = 1.0;
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
if(rc.onTheMap(l85) && rc.sensePassability(l85)){
p85 = 1.0;
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
if(rc.onTheMap(l41) && rc.sensePassability(l41)){
p41 = 1.0;
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
if(rc.onTheMap(l61) && rc.sensePassability(l61)){
p61 = 1.0;
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
if(rc.onTheMap(l67) && rc.sensePassability(l67)){
p67 = 1.0;
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
if(rc.onTheMap(l22) && rc.sensePassability(l22)){
p22 = 1.0;
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
if(rc.onTheMap(l82) && rc.sensePassability(l82)){
p82 = 1.0;
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
if(rc.onTheMap(l86) && rc.sensePassability(l86)){
p86 = 1.0;
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
if(rc.onTheMap(l31) && rc.sensePassability(l31)){
p31 = 1.0;
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
if(rc.onTheMap(l71) && rc.sensePassability(l71)){
p71 = 1.0;
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
if(rc.onTheMap(l77) && rc.sensePassability(l77)){
p77 = 1.0;
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
System.out.println("Ran BFS: " + Clock.getBytecodesLeft());
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
case -3:
switch (dy){
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
}
break;
case 1:
switch (dy){
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
}
break;
case 2:
switch (dy){
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
}
break;
}
System.out.println("Didn't find within radius, gonna use distance heuristic: " + Clock.getBytecodesLeft());
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l34.distanceSquaredTo(target)) / v34;
if(currScore > bestScore){
bestScore = currScore;
ans = d34;
System.out.println("Best end location: " + l34.toString());}
currScore = (initialDist - l56.distanceSquaredTo(target)) / v56;
if(currScore > bestScore){
bestScore = currScore;
ans = d56;
System.out.println("Best end location: " + l56.toString());}
currScore = (initialDist - l35.distanceSquaredTo(target)) / v35;
if(currScore > bestScore){
bestScore = currScore;
ans = d35;
System.out.println("Best end location: " + l35.toString());}
currScore = (initialDist - l46.distanceSquaredTo(target)) / v46;
if(currScore > bestScore){
bestScore = currScore;
ans = d46;
System.out.println("Best end location: " + l46.toString());}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
System.out.println("Best end location: " + l32.toString());}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
System.out.println("Best end location: " + l36.toString());}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
System.out.println("Best end location: " + l72.toString());}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
System.out.println("Best end location: " + l76.toString());}
currScore = (initialDist - l24.distanceSquaredTo(target)) / v24;
if(currScore > bestScore){
bestScore = currScore;
ans = d24;
System.out.println("Best end location: " + l24.toString());}
currScore = (initialDist - l84.distanceSquaredTo(target)) / v84;
if(currScore > bestScore){
bestScore = currScore;
ans = d84;
System.out.println("Best end location: " + l84.toString());}
currScore = (initialDist - l51.distanceSquaredTo(target)) / v51;
if(currScore > bestScore){
bestScore = currScore;
ans = d51;
System.out.println("Best end location: " + l51.toString());}
currScore = (initialDist - l57.distanceSquaredTo(target)) / v57;
if(currScore > bestScore){
bestScore = currScore;
ans = d57;
System.out.println("Best end location: " + l57.toString());}
currScore = (initialDist - l23.distanceSquaredTo(target)) / v23;
if(currScore > bestScore){
bestScore = currScore;
ans = d23;
System.out.println("Best end location: " + l23.toString());}
currScore = (initialDist - l83.distanceSquaredTo(target)) / v83;
if(currScore > bestScore){
bestScore = currScore;
ans = d83;
System.out.println("Best end location: " + l83.toString());}
currScore = (initialDist - l85.distanceSquaredTo(target)) / v85;
if(currScore > bestScore){
bestScore = currScore;
ans = d85;
System.out.println("Best end location: " + l85.toString());}
currScore = (initialDist - l41.distanceSquaredTo(target)) / v41;
if(currScore > bestScore){
bestScore = currScore;
ans = d41;
System.out.println("Best end location: " + l41.toString());}
currScore = (initialDist - l61.distanceSquaredTo(target)) / v61;
if(currScore > bestScore){
bestScore = currScore;
ans = d61;
System.out.println("Best end location: " + l61.toString());}
currScore = (initialDist - l67.distanceSquaredTo(target)) / v67;
if(currScore > bestScore){
bestScore = currScore;
ans = d67;
System.out.println("Best end location: " + l67.toString());}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
System.out.println("Best end location: " + l22.toString());}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
System.out.println("Best end location: " + l82.toString());}
currScore = (initialDist - l86.distanceSquaredTo(target)) / v86;
if(currScore > bestScore){
bestScore = currScore;
ans = d86;
System.out.println("Best end location: " + l86.toString());}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
System.out.println("Best end location: " + l31.toString());}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
System.out.println("Best end location: " + l71.toString());}
currScore = (initialDist - l77.distanceSquaredTo(target)) / v77;
if(currScore > bestScore){
bestScore = currScore;
ans = d77;
System.out.println("Best end location: " + l77.toString());}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}
Direction runBFSSouthwest(MapLocation target) throws GameActionException{
System.out.println("Starting BFS Method: " + Clock.getBytecodesLeft());
try{ 
	double sum;
if(rc.onTheMap(l44) && rc.sensePassability(l44)){
if(!rc.isLocationOccupied(l44)){
p44 = 1.0;
v44 -= p44;
if(v44 > v54){
v44 = v54;
d44 = Direction.WEST;
}
v44 += p44;
}
}
if(rc.onTheMap(l64) && rc.sensePassability(l64)){
if(!rc.isLocationOccupied(l64)){
p64 = 1.0;
v64 -= p64;
if(v64 > v54){
v64 = v54;
d64 = Direction.EAST;
}
v64 += p64;
}
}
if(rc.onTheMap(l53) && rc.sensePassability(l53)){
if(!rc.isLocationOccupied(l53)){
p53 = 1.0;
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
if(rc.onTheMap(l55) && rc.sensePassability(l55)){
if(!rc.isLocationOccupied(l55)){
p55 = 1.0;
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
if(rc.onTheMap(l43) && rc.sensePassability(l43)){
if(!rc.isLocationOccupied(l43)){
p43 = 1.0;
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
if(rc.onTheMap(l45) && rc.sensePassability(l45)){
if(!rc.isLocationOccupied(l45)){
p45 = 1.0;
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
if(rc.onTheMap(l63) && rc.sensePassability(l63)){
if(!rc.isLocationOccupied(l63)){
p63 = 1.0;
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
if(rc.onTheMap(l65) && rc.sensePassability(l65)){
if(!rc.isLocationOccupied(l65)){
p65 = 1.0;
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
if(rc.onTheMap(l34) && rc.sensePassability(l34)){
p34 = 1.0;
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
if(rc.onTheMap(l74) && rc.sensePassability(l74)){
p74 = 1.0;
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
if(rc.onTheMap(l52) && rc.sensePassability(l52)){
p52 = 1.0;
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
if(rc.onTheMap(l56) && rc.sensePassability(l56)){
p56 = 1.0;
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
if(rc.onTheMap(l33) && rc.sensePassability(l33)){
p33 = 1.0;
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
if(rc.onTheMap(l35) && rc.sensePassability(l35)){
p35 = 1.0;
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
if(rc.onTheMap(l73) && rc.sensePassability(l73)){
p73 = 1.0;
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
if(rc.onTheMap(l75) && rc.sensePassability(l75)){
p75 = 1.0;
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
if(rc.onTheMap(l42) && rc.sensePassability(l42)){
p42 = 1.0;
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
if(rc.onTheMap(l46) && rc.sensePassability(l46)){
p46 = 1.0;
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
if(rc.onTheMap(l62) && rc.sensePassability(l62)){
p62 = 1.0;
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
if(rc.onTheMap(l66) && rc.sensePassability(l66)){
p66 = 1.0;
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
if(rc.onTheMap(l32) && rc.sensePassability(l32)){
p32 = 1.0;
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
if(rc.onTheMap(l36) && rc.sensePassability(l36)){
p36 = 1.0;
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
if(rc.onTheMap(l72) && rc.sensePassability(l72)){
p72 = 1.0;
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
if(rc.onTheMap(l76) && rc.sensePassability(l76)){
p76 = 1.0;
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
if(rc.onTheMap(l24) && rc.sensePassability(l24)){
p24 = 1.0;
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
if(rc.onTheMap(l84) && rc.sensePassability(l84)){
p84 = 1.0;
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
if(rc.onTheMap(l51) && rc.sensePassability(l51)){
p51 = 1.0;
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
if(rc.onTheMap(l57) && rc.sensePassability(l57)){
p57 = 1.0;
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
if(rc.onTheMap(l23) && rc.sensePassability(l23)){
p23 = 1.0;
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
if(rc.onTheMap(l25) && rc.sensePassability(l25)){
p25 = 1.0;
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
if(rc.onTheMap(l83) && rc.sensePassability(l83)){
p83 = 1.0;
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
if(rc.onTheMap(l41) && rc.sensePassability(l41)){
p41 = 1.0;
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
if(rc.onTheMap(l47) && rc.sensePassability(l47)){
p47 = 1.0;
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
if(rc.onTheMap(l61) && rc.sensePassability(l61)){
p61 = 1.0;
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
if(rc.onTheMap(l22) && rc.sensePassability(l22)){
p22 = 1.0;
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
if(rc.onTheMap(l26) && rc.sensePassability(l26)){
p26 = 1.0;
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
if(rc.onTheMap(l82) && rc.sensePassability(l82)){
p82 = 1.0;
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
if(rc.onTheMap(l31) && rc.sensePassability(l31)){
p31 = 1.0;
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
if(rc.onTheMap(l37) && rc.sensePassability(l37)){
p37 = 1.0;
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
if(rc.onTheMap(l71) && rc.sensePassability(l71)){
p71 = 1.0;
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
System.out.println("Ran BFS: " + Clock.getBytecodesLeft());
int dx = target.x - l54.x;
int dy = target.y - l54.y;
switch (dx) {
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
}
break;
case -2:
switch (dy){
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
}
break;
case -1:
switch (dy){
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
}
break;
case 0:
switch (dy){
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
}
break;
case 1:
switch (dy){
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
case -2:
return d82;
case -1:
return d83;
case 0:
return d84;
}
break;
}
System.out.println("Didn't find within radius, gonna use distance heuristic: " + Clock.getBytecodesLeft());
Direction ans = null;
double bestScore = 0;
double initialDist = robot.myLoc.distanceSquaredTo(target);
double currScore;
currScore = (initialDist - l74.distanceSquaredTo(target)) / v74;
if(currScore > bestScore){
bestScore = currScore;
ans = d74;
System.out.println("Best end location: " + l74.toString());}
currScore = (initialDist - l56.distanceSquaredTo(target)) / v56;
if(currScore > bestScore){
bestScore = currScore;
ans = d56;
System.out.println("Best end location: " + l56.toString());}
currScore = (initialDist - l75.distanceSquaredTo(target)) / v75;
if(currScore > bestScore){
bestScore = currScore;
ans = d75;
System.out.println("Best end location: " + l75.toString());}
currScore = (initialDist - l66.distanceSquaredTo(target)) / v66;
if(currScore > bestScore){
bestScore = currScore;
ans = d66;
System.out.println("Best end location: " + l66.toString());}
currScore = (initialDist - l32.distanceSquaredTo(target)) / v32;
if(currScore > bestScore){
bestScore = currScore;
ans = d32;
System.out.println("Best end location: " + l32.toString());}
currScore = (initialDist - l36.distanceSquaredTo(target)) / v36;
if(currScore > bestScore){
bestScore = currScore;
ans = d36;
System.out.println("Best end location: " + l36.toString());}
currScore = (initialDist - l72.distanceSquaredTo(target)) / v72;
if(currScore > bestScore){
bestScore = currScore;
ans = d72;
System.out.println("Best end location: " + l72.toString());}
currScore = (initialDist - l76.distanceSquaredTo(target)) / v76;
if(currScore > bestScore){
bestScore = currScore;
ans = d76;
System.out.println("Best end location: " + l76.toString());}
currScore = (initialDist - l24.distanceSquaredTo(target)) / v24;
if(currScore > bestScore){
bestScore = currScore;
ans = d24;
System.out.println("Best end location: " + l24.toString());}
currScore = (initialDist - l84.distanceSquaredTo(target)) / v84;
if(currScore > bestScore){
bestScore = currScore;
ans = d84;
System.out.println("Best end location: " + l84.toString());}
currScore = (initialDist - l51.distanceSquaredTo(target)) / v51;
if(currScore > bestScore){
bestScore = currScore;
ans = d51;
System.out.println("Best end location: " + l51.toString());}
currScore = (initialDist - l57.distanceSquaredTo(target)) / v57;
if(currScore > bestScore){
bestScore = currScore;
ans = d57;
System.out.println("Best end location: " + l57.toString());}
currScore = (initialDist - l23.distanceSquaredTo(target)) / v23;
if(currScore > bestScore){
bestScore = currScore;
ans = d23;
System.out.println("Best end location: " + l23.toString());}
currScore = (initialDist - l25.distanceSquaredTo(target)) / v25;
if(currScore > bestScore){
bestScore = currScore;
ans = d25;
System.out.println("Best end location: " + l25.toString());}
currScore = (initialDist - l83.distanceSquaredTo(target)) / v83;
if(currScore > bestScore){
bestScore = currScore;
ans = d83;
System.out.println("Best end location: " + l83.toString());}
currScore = (initialDist - l41.distanceSquaredTo(target)) / v41;
if(currScore > bestScore){
bestScore = currScore;
ans = d41;
System.out.println("Best end location: " + l41.toString());}
currScore = (initialDist - l47.distanceSquaredTo(target)) / v47;
if(currScore > bestScore){
bestScore = currScore;
ans = d47;
System.out.println("Best end location: " + l47.toString());}
currScore = (initialDist - l61.distanceSquaredTo(target)) / v61;
if(currScore > bestScore){
bestScore = currScore;
ans = d61;
System.out.println("Best end location: " + l61.toString());}
currScore = (initialDist - l22.distanceSquaredTo(target)) / v22;
if(currScore > bestScore){
bestScore = currScore;
ans = d22;
System.out.println("Best end location: " + l22.toString());}
currScore = (initialDist - l26.distanceSquaredTo(target)) / v26;
if(currScore > bestScore){
bestScore = currScore;
ans = d26;
System.out.println("Best end location: " + l26.toString());}
currScore = (initialDist - l82.distanceSquaredTo(target)) / v82;
if(currScore > bestScore){
bestScore = currScore;
ans = d82;
System.out.println("Best end location: " + l82.toString());}
currScore = (initialDist - l31.distanceSquaredTo(target)) / v31;
if(currScore > bestScore){
bestScore = currScore;
ans = d31;
System.out.println("Best end location: " + l31.toString());}
currScore = (initialDist - l37.distanceSquaredTo(target)) / v37;
if(currScore > bestScore){
bestScore = currScore;
ans = d37;
System.out.println("Best end location: " + l37.toString());}
currScore = (initialDist - l71.distanceSquaredTo(target)) / v71;
if(currScore > bestScore){
bestScore = currScore;
ans = d71;
System.out.println("Best end location: " + l71.toString());}
return ans;
} catch (Exception e){
e.printStackTrace();
}return null;
}

    public Direction getBestDir(MapLocation target) throws GameActionException {
        Direction targetDir = robot.myLoc.directionTo(target);
        if(!this.vars_are_reset){
            resetVars();
        }
        System.out.println("Running getBestDir: " + Clock.getBytecodesLeft());
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
