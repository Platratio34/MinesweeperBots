package peterLibrary;

public class Arrays {
	
	//print arrays
	//1D
	public static void printArray (int[] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ", ");
		}
	}
	public static void printArray (String[] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ", ");
		}
	}
	public static void printArray (char[] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ", ");
		}
	}
	public static void printArray (double[] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ", ");
		}
	}
	public static void printArray (float[] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ", ");
		}
	}
	
	//2D
	public static void printArray (int[][] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j< array[1].length;j++) {
				System.out.print(array[i][j] + ", ");
			}
			System.out.print("\n");
		}
	}
	public static void printArray (String[][] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j< array[1].length;j++) {
				System.out.print(array[i][j] + ", ");
			}
			System.out.print("\n");
		}
	}
	public static void printArray (char[][] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j< array[1].length;j++) {
				System.out.print(array[i][j] + ", ");
			}
			System.out.print("\n");
		}
	}
	public static void printArray (double[][] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j< array[1].length;j++) {
				System.out.print(array[i][j] + ", ");
			}
			System.out.print("\n");
		}
	}
	public static void printArray (float[][] array) {
		System.out.print("\n");
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j< array[1].length;j++) {
				System.out.print(array[i][j] + ", ");
			}
			System.out.print("\n");
		}
	}
}
