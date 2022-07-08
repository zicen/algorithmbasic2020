package ainterviewbook;

public class Trie {
    public class Node {
        public int pass;
        public int end;
        public Node[] nexts;

        public Node() {
            pass = 0;
            end = 0;
            nexts = new Node[26];
        }
    }

    private Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        if (word == null) {
            return;
        }
        char[] chars = word.toCharArray();
        int index = 0;
        Node node = root;
        node.pass++;
        for (int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            if (node.nexts[index] == null) {
                node.nexts[index] = new Node();
            }
            node = node.nexts[index];
            node.pass++;
        }
        node.end++;
    }

    public void delete(String word) {
        if (search(word) > 0) {
            char[] chars = word.toCharArray();
            Node node = root;
            node.pass--;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (--node.nexts[index].pass == 0) {
                    node.nexts[index] = null;
                    return;
                }
                node = node.nexts[index];
            }
            node.end--;
        }
    }

    public int search(String word) {
        if (word == null) return 0;
        char[] chars = word.toCharArray();
        Node node = root;
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            if (node.nexts[index] == null){
                return 0;
            }
            node = node.nexts[index];
        }
        return node.end;
    }

    public int prefixNumber(String pre) {
        if (pre == null) return 0;
        char[] chars = pre.toCharArray();
        Node node = root;
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            if (node.nexts[index] == null) {
                return 0;
            }
            node = node.nexts[index];
        }
        return node.pass;
    }

}
