# MTTI: Find Shortest Path (Dijkstra’s)

Another way that we can represent graphs can be through something called an “adjancency matrix”. So here’s a graph, and let’s convert it to a matrix (or 2d array)

```
1. Generally this representation assumes that the nodes are represented as numbers 1..n
2. If node i -> j are connected with an arrow (directed), then in the adjacency matrix we could put a 1, otherwise it's a 0
3. If the graph is weighted, rather than putting a 1, we can put the value of the edge (1...k)
```

So let’s practice now given an adjacency matrix, drawing the graph and see if we can make one from it.

```
0    4    3
0    0    2
1    2    0
```

So now looking at this graph, we can see that it takes different amounts of time to get from one node to another, right?

```
1 -> 2 : distance 4 or 5
1 -> 3 : distance 3 or 6
2 -> 1 : distance 3
2 -> 3 : distance 2
3 -> 2 : distance 2
```

So this is the basis for what is called the “shortest path” problem. So what is the problem?

```
Given a weighted, directed graph and a starting node, determine the shortest paths to reach the rest of the nodes in the graph.
If the node is unreachable, mark it as infinity.
```

So the first thing we would do is have a graph and run through an example of finding the shortest distances

```
Draw a graph and go through it
```

So now how do we go about it? Mark down this as the main algorithm.

```
- Start at the starting node
- Pick the destination node that is the shortest distance from the starting node (so that we know that when updating, it was the shortest path to u up to that point)
- Evaluate all the adjacent nodes and determine if any distances need to be updated
- Mark that node as visited
- Repeat by checking the next node
```

Next, let’s discuss the data structures we might need to store this information

```
- int[] representing distances, where distance[i] is the distance to get from the startingNode to node i. Length n, where n is num nodes
- boolean[] representing nodes that are visited, where visited[i] is if node i has been visited or not. Length n, where n is num nodes
```

So what would the code look like for this?

```java
public int[] getDistances(int[][] graph, int startingNode) {
	// initialize data structures
	int[] distance = new int[graph.length];
	boolean[] visited = new boolean[graph.length];
	for (int i = 0; i < count; i++) {
    visited [i] = false;
    distance[i] = Integer.MAX_VALUE;
  }
	distance[startingNode] = 0;
	// do dijkstra
	for (int i = 0; i < graph.length; i++) {
		int u = getNodeThatsClosestAndNotVisited(distance, visited);
		for (int v = 0; v < graph.length; v++) {
			// if node is not visited, can be reached from u, and the distance of distance[u] + graph[u][v] < distance[v], update
			if (!visited[v] && graph[u][v] > 0 && (distance[u] + graph[u][v]) < distance[v]) {
				distance[v] = distance[u] + graph[u][v]
			} 
		}
		visited[u] = true;
	}
	return distance;
}

private int getNodeThatsClosestAndNotVisited(int[] distance, boolean[] visited) {
	int minDistance = Integer.MAX_VALUE;
        int minDistanceVertex = -1;
	for (int i = 0; i < distance.length; i++) {
		// if node is not visited
		if (!visited[i]) {
			if (distance[i] < minDistance) {
				minDistance = distance[i];
				minDistanceVertex = i;
			}
		}
	}
	return minDistanceVertex;
}
```

So next up, then what is the time complexity and the space complexity?

```
Time: O(n^2) because we have the nested for loop where we go through all the nodes n times. Technically it's like O(n * 2n)
Space: O(n) because we store the additional arrays on top of the graph that are the size of the number of nodes. Technically O(n + n)Another data structure we can use to solve this problem is a priority queue. It’s almost identical to the above algorithm, but rather than us having to write this method `getNodeThatsClosestAndNotVisited`, we can store the distances in a queue where the top is always the node that’s the smallest distance. 
```

Some important things to remember with these graph problems - it’s all about re-expression. We’ve seen different ways of representing graphs, and all the ways are helpful for us to solve different problems.

```
No matter how the problem represents the graph, you can convert it to the format you feel most comfortable with
|---> so if you like the adjacency matrix, convert the linkedlist to this format, and create a mapping of node value to number if that helps you.
```
