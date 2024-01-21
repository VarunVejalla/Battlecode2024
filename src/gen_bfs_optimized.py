RADIUS_SQUARED = 20
CLASS_NAME = "BFS20"
OUTPUT_FILENAME = "bfs_code.txt"
OUTPUT_FILENAME = "magellan/BFS20.java"
PACKAGE_NAME = "magellan"
start_loc = (5, 4)  # you may need to increase these vals if the RADIUS_SQUARED is increased from 20
THRESHOLD_KEEP_ALL_DIRECTIONS = 8

DIR_DICT = {(-1, 1): "NORTHWEST",
            (0, 1): "NORTH",
            (1, 1): "NORTHEAST",
            (1, 0): "EAST",
            (1, -1): "SOUTHEAST",
            (0, -1): "SOUTH",
            (-1, -1): "SOUTHWEST",
            (-1, 0): "WEST"}


def get_neighbor_locs(loc, order=None):
    neighbors = []
    dirs = [(-1, -1), (-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1)]
    for dir in dirs:
        neighbors.append((loc[0]+dir[0], loc[1]+dir[1]))

    if order != None:
        valid_neighbors = []
        for neighbor in neighbors:
            if neighbor in order:
                valid_neighbors.append(neighbor)
        return valid_neighbors

    return neighbors


def get_edge_locs(order):
    # find spots that are on the edge of our circle (for last segment in code generation)
    edge_locs = []
    for loc in order:
        neighbors = get_neighbor_locs(loc, order)
        if len(neighbors) != 8:
            edge_locs.append(loc)
    return edge_locs


def distSquared(loca, locb):
    dx = loca[0] - locb[0]
    dy = loca[1] - locb[1]
    return dx ** 2 + dy ** 2


def valid_point(start_loc, point, target_dir):
    if distSquared(start_loc, point) <= THRESHOLD_KEEP_ALL_DIRECTIONS:
        return True
    dx = point[0] - start_loc[0]
    dy = point[1] - start_loc[1]
    # Say target_dir is [0, 1] (north)
    # Then we want to remove points that are (relatively) facing south
    if target_dir == [0, 1]:  # North
        return dy >= 0 or abs(dx) > abs(dy)
    if target_dir == [0, -1]:  # South
        return dy <= 0 or abs(dx) > abs(dy)
    if target_dir == [1, 0]:  # East
        return dx >= 0 or abs(dy) > abs(dx)
    if target_dir == [-1, 0]:  # West
        return dx <= 0 or abs(dy) > abs(dx)

    if target_dir == [1, 1]:  # Northeast
        return dx >= 0 or dy >= 0
    if target_dir == [-1, 1]:  # Northwest
        return dx <= 0 or dy >= 0
    if target_dir == [1, -1]:  # Southeast
        return dx >= 0 or dy <= 0
    if target_dir == [-1, -1]:  # Southwest
        return dx <= 0 or dy <= 0

    return True


def get_dirs(target_dir):
    dirs = [(-1, 0), (-1, -1), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1)]
    return sorted(dirs, key=lambda d: distSquared(d, target_dir))


def get_order(target_dir):
    move_arr = []
    for i in range(0, RADIUS_SQUARED):
        for j in range(0, i+1):
            if i**2 + j**2 <= RADIUS_SQUARED:
                move_arr.append((i, j))
                if i != j:
                    move_arr.append((j, i))
            else:
                break

    # order in which we will conduct updates to heuristic
    order = []
    diff = []
    for dx, dy in move_arr:
        for x_dir in [-1, 1]:
            for y_dir in [-1, 1]:
                x_move, y_move = x_dir*dx, y_dir*dy
                new_loc = (start_loc[0]+x_move, start_loc[1]+y_move)

                if not valid_point(start_loc, new_loc, target_dir):  # Remove points that prolly won't matter
                    continue

                if new_loc not in order:
                    order.append(new_loc)
                    diff.append((x_move, y_move))
    return order, diff


def get_direction_arr(target_dir, order):
    opp_dir_dict = {(x, y): DIR_DICT[(-1*x, -1*y)] for (x, y) in DIR_DICT.keys()}

    # generating directions to create locations
    direction_arr = [(start_loc, None)]
    for i in range(1, len(order)):
        curr_x, curr_y = order[i]
        dirs = get_dirs(target_dir)
        for dir in dirs:
            prev_loc = (curr_x+dir[0], curr_y+dir[1])
            if prev_loc in order[0:i]:
                direction_arr.append((order[i], prev_loc, opp_dir_dict[dir]))

    return direction_arr


def gen_code(f, methodname, target_dir):
    order, diff = get_order(target_dir)

    # generating diff_dict for try_catch code
    diff_dict = {x: [] for x in set([i for (i, j) in diff])}
    for d in diff:
        diff_dict[d[0]].append(d[1])

    for key in diff_dict.keys():
        diff_dict[key].sort()

    ######################################################################################################
    # CODE GENERATION

    # section 3
    f.write("Direction " + methodname + "(MapLocation target) throws GameActionException{\n")
    f.write("System.out.println(\"Starting BFS Method: \" + Clock.getBytecodesLeft());\n")
    f.write("try{ \n")
    f.write("\tdouble sum;\n")
    for i in range(1, len(order)):
        x, y = order[i]
        # f.write("if(rc.onTheMap(l{}{})){{\n".format(x, y))
        if start_loc in get_neighbor_locs((x, y)):
            # if True:
            f.write("if(!rc.isLocationOccupied(l{}{})){{\n".format(x, y))

        # f.write("p{}{} = 20*((double)rc.senseRubble(l{}{})/10.0 + 1);\n".format(x, y, x, y))

        # f.write("info = rc.senseMapInfo(l{}{});\n".format(x, y))
        # f.write("if(info.isPassable()){\n")
        # f.write("if(robot.nav.isPassableBFS(info)){\n")
        f.write("if(p{}{} != 0){{\n".format(x, y))
        # f.write("p{}{} = info.isWater() ? 3.0 : 1.0;\n".format(x, y, x, y))
        f.write("v{}{} -= p{}{};\n".format(x, y, x, y))

        neighbors = get_neighbor_locs((x, y))
        neighbors = sorted(neighbors, key=lambda n: distSquared(n, start_loc))
        for neighbor in neighbors:
            if neighbor in order[0:i]:        # check if we have updated this neighbor's value already
                n_x, n_y = neighbor
                # f.write("sum = v{}{} + p{}{};\n".format(n_x, n_y, x, y))
                # f.write("if(v{}{} > sum){{\n".format(x, y))
                f.write("if(v{}{} > v{}{}){{\n".format(x, y, n_x, n_y))
                # f.write("v{}{} = sum;\n".format(x, y))
                f.write("v{}{} = v{}{};\n".format(x, y, n_x, n_y))

                if (n_x, n_y) == start_loc:
                    x_incr, y_incr = x-n_x, y-n_y
                    direction = DIR_DICT[(x_incr, y_incr)]
                    f.write("d{}{} = Direction.{};\n".format(x, y, direction))
                else:
                    f.write("d{}{} = d{}{};\n".format(x, y, n_x, n_y))
                f.write("}\n")

        f.write("v{}{} += p{}{};\n".format(x, y, x, y))
        # f.write("}\n")
        if start_loc in get_neighbor_locs((x, y)):
            # if True:
            f.write("}\n")
        f.write("}\n")

    # section 4 (try catch section to check if we have just explored the target)
    f.write("System.out.println(\"Ran BFS: \" + Clock.getBytecodesLeft());\n")

    f.write("int dx = target.x - l{}{}.x;\n".format(start_loc[0], start_loc[1]))
    f.write("int dy = target.y - l{}{}.y;\n".format(start_loc[0], start_loc[1]))

    f.write("switch (dx) {\n")
    for dx in sorted(list(diff_dict.keys())):
        f.write("case {}:\n".format(dx))
        f.write("switch (dy){\n")
        for dy in diff_dict[dx]:
            target_x, target_y = dx+start_loc[0], dy+start_loc[1]
            f.write("case {}:\n".format(dy))
            f.write("return d{}{};\n".format(target_x, target_y))
        f.write("}\n")
        f.write("break;\n")
    f.write("}\n")

    f.write("System.out.println(\"Didn't find within radius, gonna use distance heuristic: \" + Clock.getBytecodesLeft());\n")

    f.write("Direction ans = null;\n")
    # f.write("double bestScore = Double.MAX_VALUE;\n")
    f.write("double bestScore = 0;\n")
    f.write("double initialDist = robot.myLoc.distanceSquaredTo(target);\n")
    f.write("double currScore;\n")
    # f.write("double tempDist;\n")

    edge_locs = get_edge_locs(order)
    for edge_loc in edge_locs:
        e_x, e_y = edge_loc
        # f.write("tempDist = v{}{}/10;\n".format(e_x, e_y, e_x, e_y))
        # f.write("currScore = l{}{}.distanceSquaredTo(target) + tempDist*tempDist;\n".format(e_x, e_y))
        # f.write("currScore = (initialDist - l{}{}.distanceSquaredTo(target)) / tempDist;\n".format(e_x, e_y))
        f.write("currScore = (initialDist - l{}{}.distanceSquaredTo(target)) / v{}{};\n".format(e_x, e_y, e_x, e_y))
        # f.write("if(currScore < bestScore){\n")
        f.write("if(currScore > bestScore){\n")
        f.write("bestScore = currScore;\n")
        f.write("ans = d{}{};\n".format(e_x, e_y))
        f.write("System.out.println(\"Best end location: \" + l{}{}.toString());".format(e_x, e_y))
        f.write("}\n")

    f.write("return ans;\n")
    f.write("} catch (Exception e){\n")
    f.write("e.printStackTrace();\n")
    f.write("}")
    f.write("return null;\n")
    f.write("}\n")
    # f.write("initialDist = Math.sqrt(l{}{})")

    # print(order)
    # print((5, 4) in order)


extra_code_commented = """
    public Direction getBestDir(MapLocation target) throws GameActionException {
        Direction targetDir = robot.myLoc.directionTo(target);
//        switch(targetDir){
//            case NORTH:
//                return runBFSNorth(target);
//            case SOUTH:
//                return runBFSSouth(target);
//            case EAST:
//                return runBFSEast(target);
//            case WEST:
//                return runBFSWest(target);
//            case NORTHEAST:
//                return runBFSNortheast(target);
//            case NORTHWEST:
//                return runBFSNorthwest(target);
//            case SOUTHEAST:
//                return runBFSSoutheast(target);
//            case SOUTHWEST:
//                return runBFSSouthwest(target);
//        }
        return runBFS(target);
//        System.out.println("ERROR DIRECTION UNKNOWN");
//        return null;
    }
"""
extra_code = """
    public Direction getBestDir(MapLocation target, int[][] heuristicMap) throws GameActionException {
        Direction targetDir = robot.myLoc.directionTo(target);
        if(!this.vars_are_reset){
            resetVars(heuristicMap);
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
"""

f = open(OUTPUT_FILENAME, "w")

f.write("package {};\n".format(PACKAGE_NAME))
f.write("import battlecode.common.*;\n")
f.write("public class {} extends BFS{{\n".format(CLASS_NAME))
# f.write("RobotController rc;\nRobot robot;\n")
# f.write("boolean vars_are_reset = false;\n")

f.write("{}(RobotController rc, Robot robot){{\n".format(CLASS_NAME))
f.write("super(rc, robot);\n")
f.write("}\n")

# f.write("this.rc = rc;\nthis.robot=robot;\nthis.vars_are_reset=false;\n}\n")

# section 1
full_order, _ = get_order([0, 0])
f.write("static MapInfo info;\n\n")
for x, y in full_order:
    f.write("static MapLocation l{}{};\n".format(x, y))
    f.write("static double v{}{};\n".format(x, y))
    f.write("static Direction d{}{};\n".format(x, y))
    f.write("static int p{}{};\n".format(x, y))
    f.write("\n"*2)

# section 2
direction_arr = get_direction_arr([0, 0], full_order)
f.write("public void resetVars(int[][] heuristicMap) throws GameActionException{\n")

f.write("l{}{} = robot.myLoc;\n".format(start_loc[0], start_loc[1]))
f.write("v{}{} = 0;\n\n".format(start_loc[0], start_loc[1]))

var_set = set()
for d in direction_arr[1:]:
    curr_x, curr_y = d[0]
    prev_x, prev_y = d[1]
    dir = d[2]
    if (curr_x, curr_y) in var_set:
        continue
    var_set.add((curr_x, curr_y))
    f.write("l{}{} = l{}{}.add(Direction.{});\n".format(curr_x, curr_y, prev_x, prev_y, dir))
    f.write("v{}{} = 100000000;\n".format(curr_x, curr_y, prev_x, prev_y, dir))
    f.write("d{}{} = null;\n\n".format(curr_x, curr_y))
    f.write("p{}{} = heuristicMap[{}][{}];\n\n".format(curr_x, curr_y, curr_x, curr_y))

f.write("this.vars_are_reset = true;\n")
f.write("System.out.println(\"Finished Initializing Variables: \" + Clock.getBytecodesLeft());\n")
f.write("}\n\n")


gen_code(f, "runBFSNorth", [0, 1])
gen_code(f, "runBFSSouth", [0, -1])
gen_code(f, "runBFSEast", [1, 0])
gen_code(f, "runBFSWest", [-1, 0])
gen_code(f, "runBFSNortheast", [1, 1])
gen_code(f, "runBFSNorthwest", [-1, 1])
gen_code(f, "runBFSSoutheast", [1, -1])
gen_code(f, "runBFSSouthwest", [-1, -1])
f.write(extra_code)

# Test NORTHWEST BFS

# gen_code(f, "runBFS", [-1, 1])
# gen_code(f, "runBFS", [0, 0])
# f.write(extra_code_commented)

f.write("}\n")
f.close()

# TODO: Check vars reset method