package class08;

// 前缀树节点类型
public class Node1 {
    public int pass;
    public int end;
    public Node1[] nexts;

    public Node1() {
        pass = 0;
        end = 0;
        nexts = new Node1[26];
    }
}
