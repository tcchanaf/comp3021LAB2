package lab11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumbersTraditional {
	
	public static List<Integer> isOdd(List<Integer> numbers) {
		List<Integer> results = new ArrayList<Integer>();
		for (int n : numbers) {
			if (n % 2 != 0) results.add(n);
		}
		return results;
	}
	
	public static List<Integer> isPrime(List<Integer> numbers) {
		List<Integer> results = new ArrayList<Integer>();
		// TODO
		// Find out all the prime numbers 
		for(int n: numbers){
			int upperlimit = (int) Math.sqrt((double)n);
			boolean isPrime = true;
			for(int i = 2; i <= upperlimit; i++){
				if(n % i == 0) {
					isPrime = false;
					break;
				}
			}
			if(isPrime) results.add(n);
		}
		return results;
	}
	
	public static List<Integer> isPalindrome(List<Integer> numbers) {
		List<Integer> results = new ArrayList<Integer>();
		// TODO
		// Find out all the palindrome numbers, such as 484 and 121.

		for(int n: numbers){
			int numOfdigit = 1;
			boolean isPalidrome = true;
			while( n / (int)Math.pow(10, numOfdigit) != 0){
				numOfdigit++;
				}
			List<Integer> temp = new ArrayList<Integer>();
			int tempnum = n;
			for(int i = numOfdigit - 1; i >= 0; i--){
				
				temp.add( tempnum / (int)Math.pow(10,i) );
				tempnum -= tempnum / (int)Math.pow(10, i) * (int)Math.pow(10, i);
				
				
			}
			
			for(int i= 0; i < temp.size();i++){
				if(temp.get(i) != temp.get(temp.size() - 1 - i)){
					isPalidrome = false;
					break;
				}
			}
			if(isPalidrome){
				results.add(n);
			}
			
		}
		return results;
	}
		
	
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(480,514,484,389,709,935,328,169,649,300,685,429,243,532,308,87,25,282,91,415);
		
		System.out.println("The odd numbers are : " + isOdd(numbers));
		System.out.println("The prime numbers are : " + isPrime(numbers));
		System.out.println("The palindrome numbers are : " + isPalindrome(numbers));
		
	}
}
