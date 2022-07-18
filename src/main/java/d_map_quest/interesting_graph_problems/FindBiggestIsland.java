package d_map_quest.interesting_graph_problems;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class FindBiggestIsland {
    /*
     * Imagine a globe represented by a 2d array. This array is full of 1s to represent land, and 0s to
     * represent water. Find the biggest connected chunk of land.
     *
     * ex.
     * 0 0 0 0 0
     * 1 1 0 1 1
     * 0 0 1 1 0
     * 0 0 0 1 0
     * 0 0 0 0 0
     *
     * The size of the biggest island here is 5
     */

    /**
     * This method looks at the 2d array as an adjacency matrix. If we imagine this as an adjacency matrix,
     * we can start to solve this problem by imagining a graph and finding which node has the most edges,
     * or most nodes reachable from it.
     */
    public static int biggestIsland(int[][] globe) {
        boolean[][] visited = new boolean[globe.length][globe[0].length];
        int biggestIslandSize = 0;
        for (int i = 0; i < globe.length; i++) {
            for (int j = 0; j < globe[i].length; j++) {
                if (globe[i][j] == 1 && !visited[i][j]) {
                    int currentIslandSize = 1;
                    visited[i][j] = true; // mark node as visited
                    Stack<Pair<Integer, Integer>> indicesToVisit = new Stack<>();
                    addAdjacentSpots(i, j, visited, indicesToVisit);
                    while (!indicesToVisit.isEmpty()) {
                        Pair<Integer, Integer> currentIndex = indicesToVisit.pop();
                        // make sure one more time that not already visited
                        if (visited[currentIndex.getLeft()][currentIndex.getRight()]) {
                            continue;
                        }
                        visited[currentIndex.getLeft()][currentIndex.getRight()] = true;
                        if (globe[currentIndex.getLeft()][currentIndex.getRight()] == 1) {
                            currentIslandSize++;
                            addAdjacentSpots(currentIndex.getLeft(), currentIndex.getRight(), visited, indicesToVisit);
                        }
                    }
                    if (currentIslandSize > biggestIslandSize) {
                        biggestIslandSize = currentIslandSize;
                    }
                }
            }
        }
        return biggestIslandSize;
    }

    /**
     * Given current index that has been found as "1", adds all adjacent indices to stack
     */
    private static void addAdjacentSpots(int i, int j, boolean[][] visited, Stack<Pair<Integer, Integer>> stack) {
        List<Pair<Integer, Integer>> spotsToCheck = Arrays.asList(
                Pair.of(i - 1, j), Pair.of(i + 1, j), Pair.of(i, j - 1), Pair.of(i, j + 1)
        );
        for (Pair<Integer, Integer> spot : spotsToCheck) {
            if (isValidSpotToCheck(spot.getLeft(), spot.getRight(), visited)) {
                stack.push(spot);
            }
        }

    }

    /**
     * Checks if [i][j] is a valid index in globe and hasn't already been visited
     */
    private static boolean isValidSpotToCheck(int i, int j, boolean[][] visited) {
        if (i > visited.length - 1 || i < 0) {
            return false;
        }
        if (j < 0 || j > visited[i].length - 1) {
            return false;
        }
        return !visited[i][j];
    }

    public static void main(String[] args) {
        int[][] globe = new int[][]{
                {0, 0, 0, 0, 0},
                {1, 1, 0, 1, 1},
                {0, 0, 1, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0}
        };
        System.out.println(biggestIsland(globe));
    }

}
