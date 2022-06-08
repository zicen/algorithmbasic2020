package alabuladong;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树相关
 * https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247487126&idx=1&sn=4de13e66397bc35970963c5a1330ce18&chksm=9bd7f09eaca0798853c41fba05ad5fa958b31054eba18b69c785ae92f4bd8e4cc7a2179d7838&scene=21#wechat_redirect
 */
public class Tree {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private static class Node {
        int val;
        Node left;
        Node right;
        Node next;

        Node(int v) {
            this.val = v;
        }
    }

    //翻转二叉树
    TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    // 填充二叉树节点的右侧指针
    Node connect(Node root) {
        if (root == null) return null;
        connectTwoNode(root.left, root.right);
        return root;
    }

    void connectTwoNode(Node node1, Node node2) {
        if (node1 == null || node2 == null) return;

        node1.next = node2;
        connectTwoNode(node1.left, node1.right);
        connectTwoNode(node2.left, node2.right);
        connectTwoNode(node1.right, node2.left);
    }


    // 二叉树展开为链表
    void flatten(TreeNode root) {
        if (root == null) return;

        flatten(root.left);
        flatten(root.right);

        TreeNode left = root.left;
        TreeNode right = root.right;

        root.left = null;
        root.right = left;

        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        p.right = right;
    }

    //最大二叉树
    TreeNode constructMaximumBinaryTree(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    TreeNode build(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int index = -1;
        int maxVal = Integer.MIN_VALUE;
        for (int i = left; i <= right; i++) {
            if (maxVal < nums[i]) {
                index = i;
                maxVal = nums[i];
            }
        }
        TreeNode root = new TreeNode(maxVal);
        root.left = build(nums, left, index - 1);
        root.right = build(nums, index + 1, right);
        return root;

    }

    // 通过前序和中序遍历结果构造二叉树 105
    TreeNode buildByTraverse(int[] preorder, int preStart, int preEnd,
                             int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }
        int rootVal = preorder[preStart];
        int index = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == rootVal) {
                index = i;
                break;
            }
        }

        int leftSize = index - inStart;
        TreeNode root = new TreeNode(rootVal);
        root.left = buildByTraverse(preorder, preStart + 1, preStart + leftSize,
                inorder, inStart, index - 1);
        root.right = buildByTraverse(preorder, preStart + leftSize + 1, preEnd,
                inorder, index + 1, inEnd);
        return root;
    }

    // 通过后续和中序遍历结果构造二叉树
    TreeNode buildByTraverse2(
            int[] inorder, int inStart, int inEnd,
            int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd) return null;
        int rootVal = postorder[postEnd];
        int index = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == rootVal) {
                index = i;
                break;
            }
        }
        int leftSize = index - inStart;
        TreeNode root = new TreeNode(rootVal);
        root.left = buildByTraverse2(inorder, inStart, index - 1,
                postorder, postStart, postStart + leftSize - 1);
        root.right = buildByTraverse2(inorder, index + 1, inEnd,
                postorder, postStart + leftSize, postEnd - 1);
        return root;
    }

    // 寻找重复子树
    HashMap<String, Integer> memo = new HashMap<>();
    LinkedList<TreeNode> res = new LinkedList<>();

    List<TreeNode> findDuplicateSubtree(TreeNode root) {
        traverse(root);
        return res;
    }

    String traverse(TreeNode root) {
        if (root == null) {
            return "#";
        }
        String left = traverse(root.left);
        String right = traverse(root.right);
        String subTree = left + "," + right + "," + root.val;
        int freq = memo.getOrDefault(subTree, 0);
        if (freq == 1) {
            res.add(root);
        }
        memo.put(subTree, freq + 1);
        return subTree;
    }

    // 算出二叉树有多少节点
    int countTree(TreeNode root) {
        if (root == null) return 0;
        int left = countTree(root.left);
        int right = countTree(root.right);
        return left + right + 1;
    }

    // 二叉树的序列化和反序列化(前序遍历解法)
    private class Codec {
        //序列化
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();
        }

        private void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("#").append(",");
                return;
            }
            sb.append(root.val).append(",");
            serialize(root.left, sb);
            serialize(root.right, sb);
        }

        //反序列化
        public TreeNode deserialize(String data) {
            LinkedList<String> nodes = new LinkedList<>();
            String[] split = data.split(",");

            for (String s :
                    split) {
                nodes.addLast(s);
            }
            return deserialize(nodes);
        }

        private TreeNode deserialize(LinkedList<String> nodes) {
            if (nodes.isEmpty()) return null;
            String first = nodes.removeFirst();
            if (first.equals("#")) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(first));
            root.left = deserialize(nodes);
            root.right = deserialize(nodes);
            return root;
        }
    }

    // 二叉树的序列化和反序列化(后序遍历解法)
    private class Codex2 {
        //序列化
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();
        }

        void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("#").append(",");
                return;
            }
            serialize(root.left, sb);
            serialize(root.right, sb);
            sb.append(root.val).append(",");
        }

        TreeNode deserialize(String data) {
            LinkedList<String> nodes = new LinkedList<>();
            String[] split = data.split(",");
            for (String s : split) {
                nodes.addLast(s);
            }
            return deserialize(nodes);
        }

        TreeNode deserialize(LinkedList<String> nodes) {
            if (nodes.isEmpty()) return null;
            String last = nodes.removeLast();
            if (last.equals("#")) return null;
            TreeNode root = new TreeNode(Integer.parseInt(last));
            root.right = deserialize(nodes);
            root.left = deserialize(nodes);
            return root;
        }
    }

    // 二叉树的序列化和反序列化(中序遍历解法，只能实现序列化，无法反序列化)
    private class Codex3 {
        void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("#").append(",");
                return;
            }
            serialize(root.left, sb);
            sb.append(root.val).append(",");
            serialize(root.right, sb);
        }
    }

    // 层级遍历解法
    private static class Codex4 {
        String serialize(TreeNode root) {
            if (root == null) return "";
            StringBuilder sb = new StringBuilder();
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);
            while (!q.isEmpty()) {
                TreeNode cur = q.poll();
                if (cur == null) {
                    sb.append("#").append(",");
                    continue;
                }
                sb.append(cur.val).append(",");
                q.offer(cur.left);
                q.offer(cur.right);
            }
            return sb.toString();
        }

        TreeNode deserialize(String data) {
            if (data.isEmpty()) return null;
            String[] nodes = data.split(",");
            TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);
            for (int i = 1; i < nodes.length; i++) {
                TreeNode parent = q.poll();
                String left = nodes[i++];
                if (!left.equals("#")) {
                    parent.left = new TreeNode(Integer.parseInt(left));
                    q.offer(parent.left);
                } else {
                    parent.left = null;
                }

                String right = nodes[i++];
                if (!right.equals("#")) {
                    parent.right = new TreeNode(Integer.parseInt(right));
                    q.offer(parent.right);
                } else {
                    parent.right = null;
                }
            }
            return root;
        }
    }

    void traverseByQueue(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            System.out.println(cur.val);
            if (cur.left != null) {
                queue.offer(cur.left);
            }
            if (cur.right != null) {
                queue.offer(cur.right);
            }
        }
    }
}
