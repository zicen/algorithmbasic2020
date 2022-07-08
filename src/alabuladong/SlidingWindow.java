package alabuladong;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.*;

public class SlidingWindow {
    /* 滑动窗口算法框架 */
    void slidingWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0;
        int valid = 0;
        while (right < s.length()) {
            // c 是将要移入窗口的字符
            char c = s.charAt(right);
            // 增大窗口
            right++;
            // 进行窗口内数据的一系列更新 Todo

            /*** debug 输出的位置 start**/
            System.out.println(String.format("window: [%d. %d)", left, right));
            /*** debug 输出的位置 end **/
            // 判断左侧窗口是否要收缩(这里为了不报错，假设收缩的时机是 valid == need.size 的时候)
            while (valid == need.size()) {
                // d 是即将移出窗口的字符
                char d = s.charAt(left);
                // 缩小窗口
                left++;
                // 进行窗口内数据的一系列更新 TODO
            }
        }
    }

    public boolean xxx(String s1, String s2) {
        Map<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            char tmp = s1.charAt(i);
            need.put(tmp, need.getOrDefault(tmp, 0) + 1);
        }
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0,start = 0;
        int n = s2.length();
        int valid = 0;
        while (right < n){
            char c = s2.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            while (right - left >= s1.length()) {
                if (valid == need.size()) {
                    return true;
                }
                char d = s2.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        return false;
    }

    // 最小覆盖子串
    public String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char tmp = t.charAt(i);
            need.put(tmp, need.getOrDefault(tmp, 0) + 1);
        }
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0, valid = 0;
        int start = 0, len = Integer.MAX_VALUE;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (need.get(c).equals(window.get(c))) {
                    valid++;
                }
            }
            while (valid == need.size()) {
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                char d = s.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    //找到字符串中所有字母异位词
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            need.put(p.charAt(i), need.getOrDefault(p.charAt(i), 0) + 1);
        }
        int left = 0;
        int right = 0;
        int valid = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            while (right - left >= p.length()) {
                if (valid == need.size()) {
                    res.add(left);
                }
                char d = s.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }

        return res;
    }

    //567 字符串排列
    //给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。
    public boolean checkInclusion(String s1, String s2) {
        Map<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            char tmp = s1.charAt(i);
            need.put(tmp, need.getOrDefault(tmp, 0) + 1);
        }
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int valid = 0;
        while (right < s2.length()) {
            char c = s2.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (need.get(c).equals(window.get(c))) {
                    valid++;
                }
            }

            while (right - left >= s1.length()) {
                if (valid == need.size()) {
                    return true;
                }
                char d = s2.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }

        }
        return false;
    }

    //3 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        int res = 0;
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);
            while (window.get(c) > 1) { // 说明重复了，需要收缩
                char d = s.charAt(left);
                left++;
                window.put(d, window.getOrDefault(d, 0) - 1);
            }
            res = Math.max(res, right - left);
        }
        return res;
    }

    // 滑动窗口最大值
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        LinkedList<Integer> window = new LinkedList<>();
        int[] res = new int[n - k + 1];
        int index = 0;
        for (int i = 0; i < n; i++) {
            while (!window.isEmpty() && nums[i] >= nums[window.peekLast()]) {
                window.pollLast();
            }
            window.addLast(i);
            if (window.peekFirst() == i - k) {
                window.pollFirst();
            }
            if (i >= k - 1) {
                res[index++] = nums[window.peekFirst()];
            }
        }
        return res;
    }
}
