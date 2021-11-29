package application.model;

/**
 * Class containing functions solely used for the purpose of constructing Finite Projective Planes with an order equal to a power of a prime
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class FPPHelper {
	
	private final static int[][] GF4add = {
	            	{0, 1, 2, 3},
	            	{1, 0, 3, 2},
	            	{2, 3, 0, 1},
	            	{3, 2, 1, 0}
	            };

	private final static int[][] GF4mul = {
	            	{0, 0, 0, 0},
	            	{0, 1, 2, 3},
	            	{0, 2, 3, 1},
	            	{0, 3, 1, 2}
	            };

	private final static int[][] GF8add = {
	            	{0, 1, 2, 3, 4, 5, 6, 7},
	            	{1, 0, 3, 2, 5, 4, 7, 6},
	            	{2, 3, 0, 1, 6, 7, 4, 5},
	            	{3, 2, 1, 0, 7, 6, 5, 4},
	            	{4, 5, 6, 7, 0, 1, 2, 3},
	            	{5, 4, 7, 6, 1, 0, 3, 2},
	            	{6, 7, 4, 5, 2, 3, 0, 1},
	            	{7, 6, 5, 4, 3, 2, 1, 0}
	            };

	private final static int[][] GF8mul = {
	            	{0, 0, 0, 0, 0, 0, 0, 0},
	            	{0, 1, 2, 3, 4, 5, 6, 7},
	            	{0, 2, 4, 6, 5, 7, 1, 3},
	            	{0, 3, 6, 5, 1, 2, 7, 4},
	            	{0, 4, 5, 1, 7, 3, 2, 6},
	            	{0, 5, 7, 2, 3, 6, 4, 1},
	            	{0, 6, 1, 7, 2, 4, 3, 5},
	            	{0, 7, 3, 4, 6, 1, 5, 2}
	            };

	private final static int[][] GF9add = {
	            	{0, 1, 2, 3, 4, 5, 6, 7, 8},
	            	{1, 2, 0, 4, 5, 3, 7, 8, 6},
	            	{2, 0, 1, 5, 3, 4, 8, 6, 7},
	            	{3, 4, 5, 6, 7, 8, 0, 1, 2},
	            	{4, 5, 3, 7, 8, 6, 1, 2, 0},
	            	{5, 3, 4, 8, 6, 7, 2, 0, 1},
	            	{6, 7, 8, 0, 1, 2, 3, 4, 5},
	            	{7, 8, 6, 1, 2, 0, 4, 5, 3},
	            	{8, 6, 7, 2, 0, 1, 5, 3, 4}
	            };

	private final static int[][] GF9mul = {
	            	{0, 0, 0, 0, 0, 0, 0, 0, 0},
	            	{0, 1, 2, 3, 4, 5, 6, 7, 8},
	            	{0, 2, 1, 6, 8, 7, 3, 5, 4},
	            	{0, 3, 6, 2, 5, 8, 1, 4, 7},
	            	{0, 4, 8, 5, 6, 1, 7, 2, 3},
	            	{0, 5, 7, 8, 1, 3, 4, 6, 2},
	            	{0, 6, 3, 1, 7, 4, 2, 8, 5},
	            	{0, 7, 5, 4, 2, 6, 8, 3, 1},
	            	{0, 8, 4, 7, 3, 2, 5, 1, 6}
	            };
	
	/**
	 * If the order is a power of a prime, a pre-determined value needs to be returned
	 * Otherwise, add the operands and modulo them as regular.
	 * 
	 * @param a Operand 1
	 * @param b Operand 2
	 * @param N Order of the plane
	 * @return Corresponding integer
	 */
	public static int add(int a, int b, int N) {
		switch (N) {
			case 4: return GF4add[a][b];
			case 8: return GF8add[a][b];
			case 9: return GF9add[a][b];
			default: return (a+b)%N;
		}

	}

	/**
	 * If the order is a power of a prime, a pre-determined value needs to be returned
	 * Otherwise, multiply the operands and modulo them as regular.
	 * 
	 * @param a Operand 1
	 * @param b Operand 2
	 * @param N Order of the plane
	 * @return Corresponding integer
	 */
	public static int mul(int a, int b, int N) {
		switch (N) {
			case 4: return GF4mul[a][b];
			case 8: return GF8mul[a][b];
			case 9: return GF9mul[a][b];
			default: return (a*b)%N;
		}
	}
}
