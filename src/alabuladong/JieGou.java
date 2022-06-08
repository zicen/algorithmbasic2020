package alabuladong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JieGou {
    class RandomizedSet {
        private ArrayList<Integer> data;
        private HashMap<Integer, Integer> indexMap;
        private Random random;

        public RandomizedSet() {
            data = new ArrayList<>();
            indexMap = new HashMap<>();
            random = new Random();
        }

        public boolean insert(int val) {
            if (indexMap.containsKey(val)) {
                return false;
            }
            data.add(val);
            int index = data.size() - 1;
            indexMap.put(val, index);
            return true;
        }

        public boolean remove(int val) {
            if (!indexMap.containsKey(val)) {
                return false;
            }
            int index = indexMap.get(val);
            int lastValue = data.get(data.size() - 1);
            swap(index, data.size() - 1);
            data.remove(data.size() - 1);

            indexMap.remove(val);
            indexMap.put(lastValue, index);
            return true;
        }

        private void swap(int i, int j) {
            int tmp = data.get(i);
            data.set(i, j);
            data.set(j, tmp);
        }

        public int getRandom() {
            int randomIndex = (int) (Math.random() * (data.size() + 1));
            return data.get(randomIndex);
        }
    }
}
