package common.data;

import java.util.Arrays;
import java.util.List;

public class example {
	List <Integer> numbers = Arrays.asList(1,2,3,4,5,6,7);
	
	public void noStream() {
		long count = 0;
		for (Integer number: numbers) {
			if (number % 2 == 0) {
				count++;
			}
		}
	}
	
	public void useStream() {
		long count = numbers.stream().filter(num -> num%2 ==0).count();
		System.out.println(count);
	}

}
