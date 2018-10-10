package ca.mcgill.ecse420.a1;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MatrixMultiplication {

	private static final int NUMBER_THREADS = 8;
	private static final int MATRIX_SIZE = 4000;
	static double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
	static double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
	

	public static void main(String[] args) {

		// Generate two random matrices, same size

		long sc_start = System.nanoTime(); 
		double[][] r0 = sequentialMultiplyMatrix(a, b);
		long sc_end = System.nanoTime(); 
		long rt_s = sc_end - sc_start; 
		
		 
		long pc_start = System.nanoTime(); 
		double[][] r = parallelMultiplyMatrix(a, b);
		long pc_end = System.nanoTime(); 
		long rt_p = pc_end - pc_start; 
		
		System.out.println("Sequential Multiplication: " + rt_s + "ns");
		System.out.println("Parallel Multiplication: " + rt_p + "ns");
		System.out.println("Speedup: " + (rt_s/rt_p));
		
		
	}

	/**
	 * Returns the result of a sequential matrix multiplication The two matrices are
	 * randomly generated
	 * 
	 * @param a
	 *            is the first matrix
	 * @param b
	 *            is the second matrix
	 * @return the result of the multiplication
	 */
	public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b) {
		int row_a = a.length;
		int row_b = b.length;
		int col_a = a[0].length;
		int col_b = b[0].length;
		double[][] output = new double[row_a][col_b];
		for (int i = 0; i < row_a; i++) {
			for (int j = 0; j < row_b; j++) {
				for (int k = 0; k < col_a; k++) {
					output[i][j] = output[i][j] + a[i][k] * b[k][j];
				}
			}

		}
		return output;

	}

	/**
	 * Returns the result of a concurrent matrix multiplication The two matrices are
	 * randomly generated
	 * 
	 * @param a
	 *            is the first matrix
	 * @param b
	 *            is the second matrix
	 * @return the result of the multiplication
	 */
	public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {

		int row_a = a.length;
		int row_b = b.length;
		int col_a = a[0].length;
		int col_b = b[0].length;
		double[][] output = new double[row_a][col_b];
		//ExecutorService executor = Executors.newFixedThreadPool(NUMBER_THREADS);
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i = 0; i < row_a; i++) { 
			executor.execute(new ParallelTask(a, b, i, output));
			
		} 
		executor.shutdown();
		while(!executor.isTerminated()) {
			
		}
		/*for (int i = 0; i < row_a; i++) {
			compute(a, b, i, output);
		}*/
		

		return output;

	}

	

	/**
	 * Populates a matrix of given size with randomly generated integers between
	 * 0-10.
	 * 
	 * @param numRows
	 *            number of rows
	 * @param numCols
	 *            number of cols
	 * @return matrix
	 */
	private static double[][] generateRandomMatrix(int numRows, int numCols) {
		double matrix[][] = new double[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				matrix[row][col] = (double) ((int) (Math.random() * 10.0));
			}
		}
		return matrix;
	}
	
	
	
	

}

/*
 * The parallel task is defined as the computation of a single row in the resulted matrix 
 */
class ParallelTask implements Runnable {

	
	private double[][] a; 
	private double[][] b; 
	private int row_index; 
	private double[][] output; 
	public ParallelTask(double[][] a, double[][] b, int row_index, double[][] output) {
		this.a = a; 
		this.b = b; 
		this.row_index = row_index; 
		this.output = output; 

	}
	
	

	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		
		int row_b = b.length;
		int col_a = a[0].length;
		for (int j = 0; j < row_b; j++) {
			for (int k = 0; k < col_a; k++) {
				output[row_index][j] = output[row_index][j] + a[row_index][k] * b[k][j];
			}
		}
		

	}

	

}
