package amybook;

import java.util.*;

public class SortDemo {
    public void quickSort(int[] arr, int left, int right) {
        if (left > right) {
            return;
        }
        int p = partition(arr, left, right);
        quickSort(arr, left, p - 1);
        quickSort(arr, p + 1, right);
    }

    public int partition(int[] arr, int left, int right) {
        // 1. 生成 startIndex 到 endIndex 的随机数
        int random = left + (int) (Math.random() * ((right - left) + 1));
        // 2. 交换 startIndex 和随机数索引的值
        swap(arr, left, random);
        int pivot = arr[left];
        int mark = left;
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] < pivot) {
                mark++;
                swap(arr, mark, i);
            }
        }
        swap(arr, mark, left);
        return mark;
    }

    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 洗牌算法，将输入的数组随机打乱
    public void shuffle(int[] arr) {
        Random random = new Random();
        int left = 0;
        int right = arr.length - 1;
        for (int i = left; i < right; i++) {
            int r = random.nextInt(right - i) + i;
            swap(arr, i, r);
        }
    }

    public void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public void merge(int[] arr, int left, int mid, int right) {
        int[] tmp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int index = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                tmp[index++] = arr[i++];
            } else {
                tmp[index++] = arr[j++];
            }
        }

        while (i <= mid) {
            tmp[index++] = arr[i++];
        }
        while (j <= right) {
            tmp[index++] = arr[j++];
        }

        for (int k = 0; k < tmp.length; k++) {
            arr[k + left] = tmp[k];
        }
    }

    public void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }
    }

    public void selectSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[i]) {
                    swap(nums, j, i);
                }
            }
        }
    }

    public void insertionSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int j = i;
            while (j > 0 && nums[j] < nums[j - 1]) {
                swap(nums, j, j - 1);
                j--;
            }
        }
    }

    //大根堆
    private static class MaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public MaxHeap(int limit) {
            this.limit = limit;
            heap = new int[limit];
            heapSize = 0;
        }

        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full!");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        public void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }

        public void heapify(int[] arr, int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int largest = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
                largest = arr[largest] < arr[index] ? index : largest;
                if (largest == index) {
                    break;
                }
                swap(arr, largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }

        public void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        public void heapSort(int[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }
            for (int i = arr.length - 1; i >= 0; i--) {
                heapify(arr, i, arr.length);
            }
            int heapSize = arr.length;
            swap(arr, 0, --heapSize);
            while (heapSize > 0) {
                heapify(arr, 0, heapSize);
                swap(arr, 0, --heapSize);
            }
        }
    }

    private static class HeapGreater<T> {
        private ArrayList<T> heap;
        private HashMap<T, Integer> indexMap;
        private int heapSize;
        private Comparator<? super T> comp;

        public HeapGreater(Comparator<? super T> comparator) {
            this.comp = comparator;
            heap = new ArrayList<>();
            indexMap = new HashMap<>();
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public int size() {
            return heapSize;
        }

        public boolean contains(T obj) {
            return indexMap.containsKey(obj);
        }

        public T peek() {
            return heap.get(0);
        }

        public void push(T obj) {
            heap.add(obj);
            indexMap.put(obj, heapSize);
            heapInsert(heapSize++);
        }

        public void remove(T obj) {
            T replace = heap.get(heapSize - 1);
            int index = indexMap.get(obj);
            indexMap.remove(obj);
            heap.remove(--heapSize);
            if (obj != replace) {
                heap.set(index, replace);
                indexMap.put(replace, index);
                resign(replace);
            }
        }

        public T pop() {
            T ans = heap.get(0);
            swap(0, heapSize - 1);
            indexMap.remove(ans);
            heap.remove(--heapSize);
            heapify(0);
            return ans;
        }

        public void resign(T obj) {
            heapInsert(indexMap.get(obj));
            heapify(indexMap.get(obj));
        }


        private void heapInsert(int index) {
            while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int largest = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
                largest = comp.compare(heap.get(largest), heap.get(index)) < 0 ? largest : index;
                if (largest == index) {
                    break;
                }
                swap(largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }

        private void swap(int i, int j) {
            T o1 = heap.get(i);
            T o2 = heap.get(j);
            heap.set(i, o2);
            heap.set(j, o1);
            indexMap.put(o2, i);
            indexMap.put(o1, j);
        }
    }

    public void countSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int max = arr[0];
        for (int value : arr) {
            max = Math.max(max, value);
        }
        int[] bucket = new int[max + 1];
        for (int k : arr) {
            bucket[k]++;
        }
        int i = 0;
        for (int j = 0; j < bucket.length; j++) {
            while (bucket[j]-- > 0) {
                arr[i++] = j;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4, 7, 3, 5, 6, 2, 8, 1};
        SortDemo sortDemo = new SortDemo();
        sortDemo.shuffle(nums);
        System.out.println(Arrays.toString(nums));
        sortDemo.quickSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
        sortDemo.shuffle(nums);
        sortDemo.mergeSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
        sortDemo.shuffle(nums);
        MaxHeap maxHeap = new MaxHeap(1000);
        maxHeap.heapSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
