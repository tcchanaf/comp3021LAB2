package lab10;

/**
 * 
 * COMP 3021
 * 
This is a class that prints the maximum value of a given array of 90 elements

This is a single threaded version.

Create a multi-thread version with 3 threads:

one thread finds the max among the cells [0,29] 
another thread the max among the cells [30,59] 
another thread the max among the cells [60,89]

Compare the results of the three threads and print at console the max value.

 * 
 * @author valerio
 *
 */
public class FindMax {
	// this is an array of 90 elements
	// the max value of this array is 9999
	static int[] array = { 1, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2, 3, 4543,
			234, 3, 454, 1, 2, 3, 1, 9999, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3, 1, 34, 5, 6, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3 };

	public static void main(String[] args) throws Throwable{
		new FindMax().printMax();
	}

	public void printMax() {
		// this is a single threaded version
//		int max = findMax(0, array.length - 1);
//		System.out.println("the max value is " + max);
		
		MyTask task1 = new MyTask(0);
		Thread t1 = new Thread(task1);
		t1.start();
		
		MyTask task2 = new MyTask(1);
		Thread t2 = new Thread(task2);
		t2.start();
		
		MyTask task3 = new MyTask(2);
		Thread t3 = new Thread(task3);
		t3.start();
		
		int max = Integer.max(task1.getoutput(), task2.getoutput());
		max = Integer.max(max, task3.getoutput());
		System.out.println("the max value is " + max);
	}
	

	
	class MyTask implements Runnable{
		
		private int arraypart;
		public int output = 0;
		
		MyTask(int arraypart){
			this.arraypart = arraypart;
		}
		
		public int getoutput(){return output;}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			output = findMax(arraypart*30, arraypart*30 + 29);
		}
		
		
	}
	/**
	 * returns the max value in the array within a give range [begin,range]
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private int findMax(int begin, int end) {
		// you should NOT change this function
		int max = array[begin];
		for (int i = begin + 1; i <= end; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
}
