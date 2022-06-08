package alabuladong;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.*;

//图
public class GraphDemo {

    private static class Node {
        public int value;
        public int in;
        public int out;
        public ArrayList<Node> nexts;
        public ArrayList<Edge> edges;

        public Node(int v) {
            this.value = v;
            this.in = 0;
            this.out = 0;
            this.nexts = new ArrayList<>();
            this.edges = new ArrayList<>();
        }
    }

    private static class Edge {
        public int from;
        public int to;

        public Edge(int f, int t) {
            this.from = f;
            this.to = t;
        }
    }

    private static class Graph {
        public HashMap<Integer, Node> nodes;
        public HashSet<Edge> edges;

        public Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }

    public Graph createGraph(int[][] prerequisites) {
        Graph graph = new Graph();
        for (int i = 0; i < prerequisites.length; i++) {
            int from = prerequisites[i][1];
            int to = prerequisites[i][0];
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge edge = new Edge(from, to);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            fromNode.edges.add(edge);
            graph.edges.add(edge);
        }
        return graph;
    }

    public boolean sortGraph(int numCourses, Graph graph) {
        HashMap<Node, Integer> inMap = new HashMap<>();
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        List<Node> res = new ArrayList<>();
        int count = 0;
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            res.add(cur);
            count++;
            for (Node node : cur.nexts) {
                inMap.put(node, inMap.get(node) - 1);
                if (inMap.get(node) == 0) {
                    zeroInQueue.add(node);
                }
            }
        }
        if (res.isEmpty()) {
            return false;
        }
        System.out.println("count:" + count);
        System.out.print("res list:");
        for (int i = 0; i < res.size(); i++) {
            System.out.print(res.get(i).value + ",");
        }
        System.out.println();
        return count <= numCourses;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites.length == 0) return true;
        Graph graph = createGraph(prerequisites);
        return sortGraph(numCourses, graph);
    }

    public static void main(String[] args) {
        GraphDemo demo = new GraphDemo();
        int[][] c = new int[4][2];
        c[0][0] = 1;
        c[0][1] = 4;
        c[1][0] = 2;
        c[1][1] = 4;
        c[2][0] = 3;
        c[2][1] = 1;
        c[3][0] = 3;
        c[3][1] = 2;


        int[][] c2 = new int[4][2];
        c2[0][0] = 1;
        c2[0][1] = 0;
        c2[1][0] = 0;
        c2[1][1] = 1;


        boolean res = demo.canFinish(5, c2);
        System.out.println("res: " + res);
    }



}
