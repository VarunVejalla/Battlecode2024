package suntzu_lattice_traps_water;

import battlecode.common.MapLocation;

import java.util.Random;

// Inspired by GeeksForGeeks
class PriorityQueue {

    int[] H;
    MapLocation[] mapLocs;
    int size, capacity;
    int temp, left, right, parent, maxIndex;
    MapLocation tempLoc;

    public PriorityQueue(int capacity){
        H = new int[capacity];
        mapLocs = new MapLocation[capacity];
        this.capacity = capacity;
        size = -1;
    }

    // Function to shift up the
    // node in order to maintain
    // the heap property
    void shiftUp(int i)
    {
        parent = (i - 1) / 2; // parent
        while (i > 0 &&
                H[parent] > H[i])
        {
            // Swap parent and current node
            temp = H[i];
            H[i] = H[parent];
            H[parent] = temp;

            tempLoc = mapLocs[i];
            mapLocs[i] = mapLocs[parent];
            mapLocs[parent] = tempLoc;

            // Update i to parent of i
            i = parent;
        }
    }

    // Function to shift down the node in
    // order to maintain the heap property
    void shiftDown(int i)
    {
        maxIndex = i;

        // Left Child
        left = ((2 * i) + 1);

        if (left <= size &&
                H[left] < H[maxIndex])
        {
            maxIndex = left;
        }

        // Right Child
        right = ((2 * i) + 2);

        if (right <= size &&
                H[right] < H[maxIndex])
        {
            maxIndex = right;
        }

        // If i not same as maxIndex
        if (i != maxIndex)
        {
            // Swap i with max index
            temp = H[i];
            H[i] = H[maxIndex];
            H[maxIndex] = temp;

            tempLoc = mapLocs[i];
            mapLocs[i] = mapLocs[maxIndex];
            mapLocs[maxIndex] = tempLoc;

            shiftDown(maxIndex);
        }
    }

    // Function to insert a
    // new element in
    // the Binary Heap
    void insert(int p, MapLocation loc)
    {
        size = size + 1;
        H[size] = p;
        mapLocs[size] = loc;

        // Shift Up to maintain
        // heap property
        int i = size;
        parent = (i - 1) / 2; // parent
        while (H[parent] > H[i]) {
            // Swap parent and current node
            temp = H[i];
            H[i] = H[parent];
            H[parent] = temp;

            tempLoc = mapLocs[i];
            mapLocs[i] = mapLocs[parent];
            mapLocs[parent] = tempLoc;

            // Update i to parent of i
            i = parent;
            parent = (i - 1) / 2; // parent
        }

    }

    // Function to extract
    // the element with
    // maximum priority
    MapLocation extractMin()
    {
        if(size <= -1){
            return null;
        }
        MapLocation result = mapLocs[0];

        // Replace the value
        // at the root with
        // the last leaf
        H[0] = H[size];
        mapLocs[0] = mapLocs[size];
        size--;

        // Shift down the replaced
        // element to maintain the
        // heap property
        shiftDown(0);
        return result;
    }

    int peekPriority()
    {
        if(size <= 0){
            return -1;
        }
        return H[0];
    }

    // Function to remove the element
    // located at given index
    void remove(int i)
    {
        H[i] = Integer.MIN_VALUE;

        // Shift the node to the root
        // of the heap
        shiftUp(i);

        // Extract the node
        extractMin();
    }

    // 9696 - 8493 = 1203
    // 10391 - 9244 = 1147
    // 12355 - 11349 = 1006
    // 2914 - 1286 = 1700
    // 9560 - 7997
    // Driver Code
    public static void test(Random rand)
    {

  /*           45
            /        \
           31      14
          /  \    /  \
         13  20  7   11
        /  \
       12   7
    Create a priority queue shown in
    example in a binary max heap form.
    Queue will be represented in the
    form of array as:
    45 31 14 13 20 7 11 12 7 */

        // Insert the element to the
        // priority queue
        Util.logBytecode("Beginning of PQ test method");
        PriorityQueue pq = new PriorityQueue(200);

        byte x = 50;
        byte y = 50;
        for(int i = 0; i < 180; i++){
            pq.insert(rand.nextInt(300), new MapLocation(x, y));
        }

        x = 60;
        y = 60;
        MapLocation toAdd = new MapLocation(x, y);

        Util.logBytecode("Get ready, get set, go!");

        pq.insert(45, toAdd);
        pq.insert(200, toAdd);
        pq.insert(134, toAdd);
        pq.insert(12, toAdd);
        pq.insert(31, toAdd);
        pq.insert(7, toAdd);
        pq.insert(181, toAdd);
        pq.insert(263, toAdd);
        pq.insert(79, toAdd);

        Util.logBytecode("After insertions");

        System.out.println("PQ size: " + pq.size);
//        System.out.print("Priority queue after insertions: ");
//        int l = 0;
//        while (l <= pq.size)
//        {
//            System.out.println(pq.H[l] + " ");
//            l++;
//        }

        // Node with maximum priority
        System.out.println("Node with minimum priority: ");

        for(int i = 0; i < 7; i++){
            System.out.println(pq.extractMin());
        }

        Util.logBytecode("After extraction");

        // Remove element at index 3
        pq.remove(3);

        Util.logBytecode("After removal");
    }
}

// This code is contributed by 29AjayKumar
