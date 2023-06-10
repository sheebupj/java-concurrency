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

	public DivideAndSum(int[] arrayDetl, int starInclusive, int endInclusive) {
		super();
		this.dataArray = arrayDetl;
		this.starInclusive = starInclusive;
		this.endInclusive = endInclusive;
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
		System.out.println(starInclusive + ":" + endInclusive);
		if (dataArray.length <= 2) {
			int sum = Arrays.stream(dataArray).sum();
			System.out.println("sum=" + sum);
			return sum;
		}
		int midPoint = (endInclusive - starInclusive) / 2;
		int[] intARLeft = Arrays.copyOf(dataArray, midPoint+1);
		int[] intARRight = Arrays.copyOfRange(dataArray, midPoint+1, dataArray.length);
		DivideAndSum divideAndSumLeft = new DivideAndSum(intARLeft, 0, intARLeft.length);
		DivideAndSum divideAndSumRight = new DivideAndSum(intARRight, 0, intARRight.length);
		divideAndSumRight.fork();
		return divideAndSumLeft.compute() + divideAndSumRight.join();
	}

	public static void main(String[] args) {
		int[] intArr = { 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		DivideAndSum divideAndSum = new DivideAndSum(intArr, 0, 51);
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		int result = forkJoinPool.invoke(divideAndSum);
		System.out.println(result);
	}

	void printArray(int[] intArr) {

		Arrays.stream(intArr).forEach(n -> System.out.print(n + "  "));
	}

}
