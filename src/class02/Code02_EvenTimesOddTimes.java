package class02;

public class Code02_EvenTimesOddTimes {

	// arr中，只有一种数，出现奇数次
	public static void printOddTimesNum1(int[] arr) {
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		System.out.println(eor);
	}

	// arr中，有两种数，出现奇数次

	/**
	 * 核心思路
	 * 第一步：先通过异或拿到出现奇数次的两种数的结果 eor
	 * 第二步：通过 eor & (-eor) 可以提取出最右边的 1
	 * 第三步：因为有两种数，那么我们假设 a 和 b，既然这俩数异或之后可以得到 1，
	 * 那么说明 a, b 肯定在这一位上是不同的，那么我们假设 a 这一位是 1，b 这一位是 0，
	 * 那么可以将 arr 也同时划分为两块，一块是这一位 为 1 的，一块是这一位为 0 的，
	 * 那么我们只要循环异或数组的一块位置，就得到了 a 的值，得到了 a 就可以通过 eor 得到 b 了
	 *
	 */
	public static void printOddTimesNum2(int[] arr) {
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		// a 和 b是两种数
		// eor != 0
		// eor最右侧的1，提取出来
		// eor :     00110010110111000
		// -eor: 	 11001101001000111
		// rightOne :00000000000001000
		int rightOne = eor & (-eor); // 提取出最右的1
		
		
		int onlyOne = 0; // eor'
		for (int i = 0 ; i < arr.length;i++) {
			//  arr[1] =  111100011110000
			// rightOne=  000000000010000
			if ((arr[i] & rightOne) != 0) {
				onlyOne ^= arr[i];
			}
		}
		System.out.println(onlyOne + " " + (eor ^ onlyOne));
	}

	
	public static int bit1counts(int N) {
		int count = 0;
		
		//   011011010000
		//   000000010000     1
		
		//   011011000000
		// 
		
		
		
		while(N != 0) {
			int rightOne = N & ((~N) + 1);
			count++;
			N ^= rightOne;
			// N -= rightOne
		}
		
		
		return count;
		
	}
	
	
	public static void main(String[] args) {
		int a = 5;
		int b = 7;

		a = a ^ b;
		b = a ^ b;
		a = a ^ b;

		System.out.println(a);
		System.out.println(b);

		int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 }; // 只有 2 出现了奇数次
		printOddTimesNum1(arr1);

		int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 }; // 只有 3 和 2 出现了奇数次
		printOddTimesNum2(arr2);

	}

}
