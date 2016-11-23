package lab11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NumbersLambda {
	
	// Find numbers with certain properties in a unified form
	// The property is specified in Predicate
	
	public static List<Integer> findNumbers(List<Integer> list, Predicate<Integer> predicate) {
		List<Integer> results = new ArrayList<Integer>();
		for (int n : list) {
			if (predicate.test(n)) results.add(n);
		}
		return results;
	}
	
	public static List<Integer> calculateScore(List<Integer> list, Function<Integer, Integer> function) {
		// TODO: Task 3
		
		List<Integer> temp = new ArrayList<Integer>();
		
		for(int n : list){
			temp.add(function.apply(n));
		}
		return temp;
	}
	
	public static Function<Integer, Integer> policy() {
		// TODO: Task 3
		return x -> {
			int score = 0;
			if(isOdd().test(x)) score += 1;
			if(isPrime().test(x)) score += 2;
			if(isPalindrome().test(x)) score += 4;
			return score;
		};
		
	}
	
	public static Predicate<Integer> isOdd() {
		// TODO: Task 2
		return x -> x % 2 != 0;
	}
	
	public static Predicate<Integer> isPrime() {
		return n -> {
			int upperlimit = (int) Math.sqrt((double)n);
			boolean isPrime = true;
			for(int i = 2; i <= upperlimit; i++){
				if(n % i == 0) {
					isPrime = false;
					break;
				}
			}
			return isPrime;
		};
		// TODO: Task 2
	}
				
			
	
	public static Predicate<Integer> isPalindrome() {
		// TODO: Task 2
			return n -> {
				// TODO
				// Find out all the palindrome numbers, such as 484 and 121.
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
					return isPalidrome;};
	}
	
//	public static Function<Integer, Integer> policy() {
//		// TODO: Task 3
//	}
	
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(480,514,484,389,709,935,328,169,649,300,685,429,243,532,308,87,25,282,91,415);
		
		System.out.println("The odd numbers are : " + findNumbers(numbers,isOdd()));
		System.out.println("The prime numbers are : " + findNumbers(numbers,isPrime()));
		System.out.println("The palindrome numbers are : " + findNumbers(numbers,isPalindrome()));
		
		System.out.println("The score of the all numbers are :" + calculateScore(numbers, policy()));
	}
}
