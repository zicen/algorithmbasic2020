package class08;

import javax.swing.tree.TreeNode;

public class TrieMap<V> {
    //ASCII count
    private static final int R = 256;

    private static class TrieNode<V> {
        V val = null;
        TrieNode<V>[] children = new TrieNode[R];
    }

    private int size = 0;
    private TrieNode<V> root = null;

    public void put(String key, V val) {
        if (!containsKey(key)) {
            size++;
        }
        root = put(root, key, val, 0);

    }

    private TrieNode<V> put(TrieNode<V> node, String key, V val, int i) {
        if (node == null) {
            node = new TrieNode<>();
        }
        if (i == key.length()) {
            node.val = val;
            return node;
        }
        char c = key.charAt(i);
        node.children[c] = put(node.children[c], key, val, i + 1);
        return node;
    }

    private TrieNode<V> getNode(TrieNode<V> node, String key) {
        TrieNode<V> p = node;
        for (int i = 0; i < key.length(); i++) {
            if (p == null) {
                return null;
            }
            char c = key.charAt(i);
            p = p.children[c];
        }
        return p;
    }

    private V get(String key) {
        TrieNode<V> x = getNode(root, key);
        if (x == null || x.val == null) {
            return null;
        }

        return x.val;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }
    // 判断是否存在前缀为 prefix 的键
    public boolean hasKeyWithPrefix(String prefix) {
        return getNode(root, prefix) != null;
    }

}
