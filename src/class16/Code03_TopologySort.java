package class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 拓扑排序
public class Code03_TopologySort {

    // directed graph and no loop
    public static List<Node> sortedTopology(Graph graph) {
        // key 某个节点   value 剩余的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 只有剩余入度为0的点，才进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        List<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }


    public static List<Node> sortedTopology2(Graph graph) {
        HashMap<Node, Integer> inMap = new HashMap<>();
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()
        ) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        List<Node> res = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            res.add(cur);
            for (Node node : cur.nexts
            ) {
                inMap.put(node, inMap.get(node) - 1);
                if (inMap.get(node) == 0) {
                    zeroInQueue.add(node);
                }
            }
        }
        return res;

    }
}
