package alabuladong;

import java.util.LinkedList;
import java.util.List;

public class TrieMap<V> {
    private static final int R = 256;
    private int size = 0;

    private static class TrieNode<V> {
        V val = null;
        TrieNode<V>[] children = new TrieNode[R];
    }

    private TrieNode<V> root = null;

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

    /***** 增/改 *****/

    // 在 Map 中添加 key
    public void put(String key, V val) {
        if (!containsKey(key)) {
            size++;
        }
        root = put(root, key, val, 0);
    }

    public TrieNode<V> put(TrieNode<V> node, String key, V val, int i) {
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

    /***** 删 *****/

    // 删除键 key 以及对应的值
    public void remove(String key) {
        if (!containsKey(key)) {
            return;
        }
        root = remove(root, key, 0);
        size--;
    }

    private TrieNode<V> remove(TrieNode<V> node, String key, int i) {
        if (node == null) {
            return null;
        }
        if (i == key.length()) {
            node.val = null;
        } else {
            char c = key.charAt(i);
            node.children[c] = remove(node.children[c], key, i + 1);
        }
        if (node.val != null) {
            return node;
        }
        for (int j = 0; j < R; j++) {
            if (node.children[j] != null) {
                return node;
            }
        }
        return null;
    }

    /***** 查 *****/

    // 搜索 key 对应的值，不存在则返回 null
    // get("the") -> 4
    // get("tha") -> null
    public V get(String key) {
        TrieNode<V> x = getNode(root, key);
        if (x == null || x.val == null) {
            return null;
        }
        return x.val;
    }

    // 判断 key 是否存在在 Map 中
    // containsKey("tea") -> false
    // containsKey("team") -> true
    public boolean containsKey(String key) {
        return get(key) != null;
    }

    // 在 Map 的所有键中搜索 query 的最短前缀
    // shortestPrefixOf("themxyz") -> "the"
    public String shortestPrefixOf(String query) {
        TrieNode<V> p = root;
        for (int i = 0; i < query.length(); i++) {
            if (p == null) {
                return "";
            }
            if (p.val != null) {
                return query.substring(0, i);
            }
            char c = query.charAt(i);
            p = p.children[c];
        }
        if (p != null && p.val != null) {
            return query;
        }
        return "";
    }


    // 在 Map 的所有键中搜索 query 的最长前缀
    // longestPrefixOf("themxyz") -> "them"
    public String longestPrefixOf(String query) {
        TrieNode<V> p = root;
        int max_len = 0;
        for (int i = 0; i < query.length(); i++) {
            if (p == null) {
                return "";
            }
            if (p.val != null) {
                // 找到一个键是 query 的前缀，更新前缀的最大长度
                max_len = i;
            }
            char c = query.charAt(i);
            p = p.children[c];
        }
        if (p != null && p.val != null) {
            return query;
        }
        return query.substring(0, max_len);
    }

    // 搜索所有前缀为 prefix 的键
    // keysWithPrefix("th") -> ["that", "the", "them"]
    public List<String> keysWithPrefix(String prefix) {
        List<String> res = new LinkedList<>();
        TrieNode<V> x = getNode(root, prefix);
        if (x == null) {
            return res;
        }
        travese(x, new StringBuilder(prefix), res);
        return res;
    }

    private void travese(TrieNode<V> node, StringBuilder sb, List<String> res) {
        if (node == null) {
            return;
        }
        if (node.val != null) {
            res.add(sb.toString());
        }
        for (int i = 0; i < R; i++) {
            sb.append(i);
            travese(node.children[i], sb, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    // 判断是和否存在前缀为 prefix 的键
    // hasKeyWithPrefix("tha") -> true
    // hasKeyWithPrefix("apple") -> false
    public boolean hasKeyWithPrefix(String prefix) {
        return getNode(root, prefix) != null;
    }

    // 通配符 . 匹配任意字符，搜索所有匹配的键
    // keysWithPattern("t.a.") -> ["team", "that"]
    public List<String> keysWithPattern(String pattern) {
        List<String> res = new LinkedList<>();
        travese(root, new StringBuilder(), pattern, 0, res);
        return res;
    }

    private void travese(TrieNode<V> node, StringBuilder path, String pattern, int i, List<String> res) {
        if (node == null) {
            return;
        }
        if (i == pattern.length()) {
            if (node.val != null) {
                res.add(path.toString());
            }
            return;
        }
        char c = pattern.charAt(i);
        if (c == '.') {
            for (int j = 0; j < R; j++) {
                path.append(j);
                travese(node.children[j], path, pattern, i + 1, res);
                path.deleteCharAt(path.length() - 1);
            }
        } else {
            path.append(c);
            travese(node.children[c], path, pattern, i + 1, res);
            path.deleteCharAt(path.length() - 1);
        }
    }

    // 通配符 . 匹配任意字符，判断是否存在匹配的键
    // hasKeyWithPattern(".ip") -> true
    // hasKeyWithPattern(".i") -> false
    public boolean hasKeyWithPattern(String pattern) {
        return hasKeyWithPattern(root, pattern, 0);
    }

    private boolean hasKeyWithPattern(TrieNode<V> node, String pattern, int i) {
        if (node == null) {
            return false;
        }
        if (i == pattern.length()) {
            return node.val != null;
        }
        char c = pattern.charAt(i);
        if (c != '.') {
            return hasKeyWithPattern(node.children[c], pattern, i + 1);
        }
        for (int j = 0; j < R; j++) {
            if (hasKeyWithPattern(node.children[j], pattern, i + 1)) {
                return true;
            }
        }
        return false;
    }

    // 返回 Map 中键值对的数量
    public int size() {
        return size;
    }
}
