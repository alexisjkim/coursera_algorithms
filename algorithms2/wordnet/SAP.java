package algorithms2.wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

public class SAP {
    private Digraph graph;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        //  graph = G;

        graph = new Digraph(G.V());

        for (int i = 0; i < G.V(); i++) {
            Iterable<Integer> out_edges = G.adj(i);

            for (int j : out_edges) {
                graph.addEdge(i, j);
            }

        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0) throw new IllegalArgumentException();
        if (v > graph.V() - 1 || w > graph.V() - 1) throw new IllegalArgumentException();
        int ans = bfs(v, w);

        return ans;

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0) throw new IllegalArgumentException();
        if (v > graph.V() - 1 || w > graph.V() - 1) throw new IllegalArgumentException();
        int ans = sap(v,w);
        return ans;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        //   Iterator<Integer> v_iterator = v.iterator();
        Iterator<Integer> w_iterator = v.iterator();
        /*

        while (v_iterator.hasNext()) {
            int current = v_iterator.next();
            if (current < 0) throw new IllegalArgumentException();
            if (current > graph.V() - 1) throw new IllegalArgumentException();
        }
        while (w_iterator.hasNext()) {
            int current = w_iterator.next();
            if (current < 0) throw new IllegalArgumentException();
            if (current > graph.V() - 1) throw new IllegalArgumentException();
        }



        int ans = -1;
        BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(graph, v);

        while (w_iterator.hasNext()) {
            int current = w_iterator.next();
            int x = a.distTo(current);
            if (x < ans) {
                ans = x;
            }
            if (ans == -1) {
                ans = x;
            }
        }
        return ans;


        // Iterator<Integer> v_iterator = v.iterator();
        // int ans = -1;
        // while (v_iterator.hasNext()) {
        //     Iterator<Integer> w_iterator = w.iterator();
        //     while (w_iterator.hasNext()) {
        //         int current_length = bfs(v_iterator.next(), w_iterator.next());
        //         if (current_length != -1) {
        //             if (ans == -1) ans = current_length;
        //             if (ans > current_length) ans = current_length;
        //         }
        //     }
        // }

         */
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();

        Iterator<Integer> v_iterator = v.iterator();
        Iterator<Integer> w_iterator = v.iterator();

        while (v_iterator.hasNext()) {
            int current = v_iterator.next();
            if (current < 0) throw new IllegalArgumentException();
            if (current > graph.V() - 1) throw new IllegalArgumentException();
        }
        while (w_iterator.hasNext()) {
            int current = w_iterator.next();
            if (current < 0) throw new IllegalArgumentException();
            if (current > graph.V() - 1) throw new IllegalArgumentException();
        }
        /*
        Iterator<Integer> v_iterator = v.iterator();
        int ans = -1;
        while (v_iterator.hasNext()) {
            Iterator<Integer> w_iterator = w.iterator();
            while (w_iterator.hasNext()) {
                int current_length = sap(v_iterator.next(), w_iterator.next());
                if (current_length != -1) {
                    if (ans == -1) ans = current_length;
                    if (ans > current_length) ans = current_length;
                }
            }
        }
         */
        return -1;
    }

    private int sap (int v, int w) {
        int[] v_distance = new int[graph.V()];
        int[] w_distance = new int[graph.V()];

        for (int i = 0; i < v_distance.length; i++) {
            v_distance[i] = -1;
            w_distance[i] = -1;
        }

        v_distance[v] = 0;
        w_distance[w] = 0;

        Queue<Integer> v_queue = new Queue<>();
        Queue<Integer> w_queue = new Queue<>();
        v_queue.enqueue(v);
        w_queue.enqueue(w);

        while (!v_queue.isEmpty()) {
            int current = v_queue.dequeue();
            Iterable<Integer> v_adj = graph.adj(current);
            for (int i : v_adj) {
                if (v_distance[i] == -1) {
                    v_distance[i] = v_distance[current] + 1;
                    v_queue.enqueue(i);
                }
                if (v_distance[current] + 1 < v_distance[i]) {
                    v_distance[i] = v_distance[current] + 1;
                }
            }
        }

        while (!w_queue.isEmpty()) {
            int current = w_queue.dequeue();
            Iterable<Integer> w_adj = graph.adj(current);
            for (int i : w_adj) {
                if (w_distance[i] == -1) {
                    w_distance[i] = w_distance[current] + 1;
                    w_queue.enqueue(i);
                }
                if (w_distance[current] + 1 < w_distance[i]) {
                    w_distance[i] = w_distance[current] + 1;
                }
            }
        }

        int SAP_distance = 1000000;
        int ans = v;
        for (int i = 0; i < v_distance.length; i++) {
            if (v_distance[i] > -1 && w_distance[i] > -1 && v_distance[i] + w_distance[i] < SAP_distance) {
                SAP_distance = v_distance[i] + w_distance[i];
                ans = i;
            }
        }
        if (SAP_distance == 1000000) {
            SAP_distance = -1;
            ans = -1;
        }

        return ans;
    }

    private int bfs (int v, int w) {
        int[] v_distance = new int[graph.V()]; // values represent distance of v and w to that node (node is index)
        int[] w_distance = new int[graph.V()];

        for (int i = 0; i < v_distance.length; i++) { // initializing with -1, -1 means there is no connection
            v_distance[i] = -1;
            w_distance[i] = -1;
        }

        v_distance[v] = 0; // the starting node is initialized with 0,
        w_distance[w] = 0;

        Queue<Integer> v_queue = new Queue<>(); // queues used to avoid recursion
        Queue<Integer> w_queue = new Queue<>();
        v_queue.enqueue(v); //add start nodes to the queue
        w_queue.enqueue(w);

        while (!v_queue.isEmpty()) {
            int current = v_queue.dequeue();
            Iterable<Integer> v_adj = graph.adj(current);
            for (int i : v_adj) { // all the possible ways to go from the current node
                if (v_distance[i] == -1) { // if it hasn't been visited before
                    v_distance[i] = v_distance[current] + 1;
                    v_queue.enqueue(i);
                }
                if (v_distance[current] + 1 < v_distance[i]) { // if it has been visited, but this is a shorter path
                    v_distance[i] = v_distance[current] + 1;
                }
            }
        }

        while (!w_queue.isEmpty()) { // doing the same with w; finding the distances of the nodes to w
            int current = w_queue.dequeue();
            Iterable<Integer> w_adj = graph.adj(current);
            for (int i : w_adj) {
                if (w_distance[i] == -1) {
                    w_distance[i] = w_distance[current] + 1;
                    w_queue.enqueue(i);
                }
                if (w_distance[current] + 1 < w_distance[i]) {
                    w_distance[i] = w_distance[current] + 1;
                }
            }
        }

        int SAP_distance = 1000000; // the distance from v to w. initialize with something very high
        int ans = v; // the node used to measure distance
        for (int i = 0; i < v_distance.length; i++) {
            if (v_distance[i] > -1 && w_distance[i] > -1 && v_distance[i] + w_distance[i] < SAP_distance) { // if node is connected to v and w
                SAP_distance = v_distance[i] + w_distance[i]; // update distance and sap
                ans = i;
            }
        }
        if (SAP_distance == 1000000) {
            SAP_distance = -1;
            ans = -1;
        }

        return SAP_distance;
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
