Another question I want to do is not really directly from leetcode, but it’s one I’ve been asked before and I wanted to go over it here, since now we should be able to solve it 🙂

Given a graph that is represented by a Set<Node>, and the Nodes are connected to one another, can we determine the number of neighborhoods that the graph has?

The reason this question represents the graph as a Set rather than like a “linkedList” style is because not all the nodes are connected.

```
Show a graph and how you should be able to reach a node from another node
Show that if there's no path, then there's no way to get there
Talk about the graph being undirected, so you could start at any node and get to the other node (so a set is useful)
```

So what is a Node here

```
class Node {
	int value;
	Set<Node> neighboringNodes;
}
```

So now, let’s run through an example

Next, let’s think about how we can break down the problem

```
1. We should be able to see if one node can be reached from another
2. If two nodes are reachable from each other, then they are part of the same neighborhood
```

So there are two ways to approach the problem — I see that figuring out if one node can be reached from another one is a difficult problem that I don’t know how to approach right away, so I want to start at a higher level to focus on one problem at a time

```
1. For each node in the graph
2. Check which nodes in the graph are reachable from that node
3. If the node is reachable from node in question, have new node join that in neighborhood
```

So let’s try to write that out

```java
public int getNeighborhoods(Set<Node> graph) {
	List<Set<Node>> neighborhoods = new HashSet<>();
	Set<Node> alreadyInNeighborhood = new HashSet<>();
	for (Node node : graph) {
		if (!alreadyInNeighborhood.contains(node)) {
			Set<Node> neighborhood = new HashSet<>();
			for (Node n : graph) {
				if (isReachable(node, n)) {
					neighborhood.add(n);
					alreadyInNeighborhood.add(n);
				}
			}
			neighborhoods.add(neighborhood);
		}
	}
	return neighborhoods.size();
}
```

See, so we’ve got the overall logic out of the way, and we have something down. Now we just have to do the next part of figuring out if a node is reachable from another one.

So how can we do that?

```
1. Given two nodes, start and end, see if end is connected to start
2. start has a set of neighboring nodes
3. need to see if any of those neighboring nodes are connected to end
...
And do that until we've exhausted all the paths we can check
```

So what can we do about this?

```
Diagram it out

1. Start with a node
2. Check if any of the neighbors are the end node
3. If not, remember to check the rest
```

Now let’s try to convert this into code

```java
public boolean isReachable(Node start, Node end) {
	Set<Node> checked = new HashSet<>();
	Queue<Node> queue = new LinkedList<>();
	queue.add(start);
	while (!queue.isEmpty()) {
		Node current = queue.poll();
		if (current == end) {
			return true;
		}
		Set<Node> neighbors = current.getNeighbors();
		for (Node n : neighbors) {
			if (!checked.contains(n)) {
				queue.add(n);
			}
		}
		checked.add(current);
	}
	return false;
}
```

And so now that we have this, we finished the problem!  This above algorithm is called breadth-first-search 🙂

Now that we’re looking at this, let’s try to look at the runtime

```
Time: Nested for loop, so we know O(n^3), then for the isReachable, we have to check all the nodes until we reach end, so that would be O(n)
	|---> O(n^2 * n) ==> O(n^3)
Space: We store a queue with all the nodes in isReacahble, so O(n) there, And we also store everything in the isAlreadyInNeighborhood and the neighborhoods list, on top of graph, 
	|---> so O(4n) ==> O(n)
```
