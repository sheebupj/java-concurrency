package concurrency.paremal;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class DivideAndSum extends RecursiveTask<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] dataArray;
	private int starInclusive;
	private int endInclusive;

	public DivideAndSum(int[] arrayDetl) {
		super();
		this.dataArray = arrayDetl;
		
	}

	public int[] getArrayDetl() {
		return dataArray;
	}

	public void setArrayDetl(int[] arrayDetl) {
		this.dataArray = arrayDetl;
	}

	public int getStarInclusive() {
		return starInclusive;
	}

	public void setStarInclusive(int starInclusive) {
		this.starInclusive = starInclusive;
	}

	public int getEndInclusive() {
		return endInclusive;
	}

	public void setEndInclusive(int endInclusive) {
		this.endInclusive = endInclusive;
	}

	@Override
	protected Integer compute() {
		starInclusive=0;
		endInclusive=dataArray.length;
		System.out.println(starInclusive + ":" + endInclusive);
		if (dataArray.length <= 2) {
			int sum = Arrays.stream(dataArray).sum();
			System.out.println("sum=" + sum);
			return sum;
		}
		int midPoint = (endInclusive - starInclusive) / 2;
		printArray(dataArray);System.out.println();
		int[] intARLeft = Arrays.copyOf(dataArray, midPoint+1);
		printArray(intARLeft);System.out.println();
		int[] intARRight = Arrays.copyOfRange(dataArray, midPoint+1, endInclusive);
		printArray(intARRight);
		DivideAndSum divideAndSumLeft = new DivideAndSum(intARLeft);
		DivideAndSum divideAndSumRight = new DivideAndSum(intARRight);
		divideAndSumRight.fork();
		return divideAndSumLeft.compute() + divideAndSumRight.join();
	}

	public static void main(String[] args) {
		int[] intArr = { 1,2,3,4,5,6,7,8,9,3,10,100,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		DivideAndSum divideAndSum = new DivideAndSum(intArr);
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		int result = forkJoinPool.invoke(divideAndSum);
		System.out.println(result);
	}

	void printArray(int[] intArr) {

		Arrays.stream(intArr).forEach(n -> System.out.print(n + "  "));
	}

}
