package alabuladong;

import java.util.LinkedHashMap;

public class LruCache {
    int cap;
    LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();
    public LruCache(int capacity) {
        this.cap = capacity;
    }
    public int get(int key) {
        if(!cache.containsKey(key)) {
            return -1;
        }
        makeRecently(key);
        return cache.get(key);
    }

    public void put(int key, int val) {
        if (cache.containsKey(key)) {
            cache.put(key, val);
            makeRecently(key);
            return;
        }
        if (cache.size() > this.cap) {
            int oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        cache.put(key, val);
    }

    public void makeRecently(int key){
        int val = cache.get(key);
        cache.remove(key);
        cache.put(key, val);
    }
}
