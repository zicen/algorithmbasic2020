package alabuladong;

import java.util.*;

//图
public class GraphDemo2 {

    // 建图函数
    List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
       List<Integer>[] graph = new LinkedList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new LinkedList<Integer>();
        }
        for (int[] edge: prerequisites
             ) {
            int from = edge[1];
            int to = edge[0];
            graph[from].add(to);
        }
        return graph;
    }

    boolean canFinish(int numCourses, int[][] prerequisites) {
        // 建图，有向边代表「被依赖」关系
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        // 构建入度数组
        int[] indegree = new int[numCourses];
        for (int[] edge : prerequisites) {
            int from = edge[1], to = edge[0];
            // 节点 to 的入度加一
            indegree[to]++;
        }

        // 根据入度初始化队列中的节点
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                // 节点 i 没有入度，即没有依赖的节点
                // 可以作为拓扑排序的起点，加入队列
                q.offer(i);
            }
        }

        // 记录遍历的节点个数
        int count = 0;
        // 开始执行 BFS 循环
        while (!q.isEmpty()) {
            // 弹出节点 cur，并将它指向的节点的入度减一
            int cur = q.poll();
            count++;
            for (int next : graph[cur]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    // 如果入度变为 0，说明 next 依赖的节点都已被遍历
                    q.offer(next);
                }
            }
        }

        // 如果所有节点都被遍历过，说明不成环
        return count == numCourses;
    }

    public static void main(String[] args) {
        GraphDemo2 demo = new GraphDemo2();
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


        boolean res = demo.canFinish(5, c);
        System.out.println("res: " + res);
    }



}
