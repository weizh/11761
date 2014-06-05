package cmu11761.hw7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import util.GetReader;

public class G {

	public static int T;
	public static final int K = 2;
	public static final int Z = 27;
	public static final int DSIZE = 91;
	public static int alphabet[];// map index to alphabet

	public static char[] O;
	public static double[][] logA;
	public static double[][] logB;
	public static double logpi[];
	public static double[][] A;
	public static double[][] B;
	public static double pi[];
	private static Random random;
	static {
		random = new Random();
		random.setSeed(System.currentTimeMillis());
		try {
			// read observed sequence
			BufferedReader r = 
//					for training
//					GetReader.getFileReader("string.txt");
//					for Q11
					GetReader.getFileReader("stringQ11.txt");
			
			String s = r.readLine();
			O = s.toCharArray();
			T = O.length;
			r.close();
			// initialize A and B
			logA = new double[K][K];
			A = new double[K][K];
			logpi = new double[2];
			pi = new double[2];
			/**
			 * B is longer than 27 length. Its length is 91, index from 0
			 * to 90. So we can use char to directly index B. However,
			 * some of the place is zero for B. Keep in mind.
			 */
			logB = new double[K][DSIZE];
			B = new double[K][DSIZE];

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String argv[]) {
		System.out.println(G.O[4]);
		System.out.println(G.K);
		System.out.println((int) ('A') + " " + (int) (' '));
		System.out.println();
		try {
			G.initialize();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void print(double a) {
		System.out.println(a);
	}

	static void initialize() throws NumberFormatException, IOException {
		logA[0][0] = Math.log(0.297821156093d);
		logA[0][1] = Math.log(0.702178843907d);
		logA[1][0] = Math.log(0.715418438694d);
		logA[1][1] = Math.log(0.284581561306d);

		A[0][0] = 0.3;
		A[0][1] = (0.7);
		A[1][0] = (0.28);
		A[1][1] = (0.72);

		logpi[0] = logpi[1] = Math.log(0.5d);

		pi[0] = pi[1] = 0.5d;

		/**
		 * B is longer than 27 length. Its length is 91, index from 0 to 90.
		 * So we can use char to directly index B. However, some of the
		 * place is zero for B. Keep in mind.
		 */
		logB = new double[K][91];

		B = new double[K][91];

		BufferedReader r = GetReader.getFileReader("emis1.txt");
		String s;
		String[] a;
		while ((s = r.readLine()) != null) {
			a = s.split("	");
			char c = a[0].charAt(0);
			double p = Double.parseDouble(a[1]);

			logB[0][(int) c] = Math.log(p);
			B[0][(int) c] = p;
		}
		r.close();
		r = GetReader.getFileReader("emis2.txt");
		while ((s = r.readLine()) != null) {
			a = s.split("	");
			char c = a[0].charAt(0);
			double p = Double.parseDouble(a[1]);
			logB[1][(int) c] = Math.log(p);
			B[1][(int) c] = p;
		}
		// for(int i=0;i<91;i++){
		// System.out.print(B[0][i]+"	"); System.out.print(B[1][i]);
		// System.out.println();
		// }
	}

	static void ramdomInitialize() throws NumberFormatException, IOException {
		// initialize A
		for (int i = 0; i < K; i++) {
			double sum = 0.0d;
			for (int j = 0; j < K; j++) {
				A[i][j] = random.nextDouble();
				sum += A[i][j];
			}
			for (int j = 0; j < K; j++) {
				A[i][j] = A[i][j] / sum;
				logA[i][j] = Math.log(A[i][j]);
			}
		}
		// initialize pi.
		pi[0] = random.nextDouble();
		pi[1] = 1 - pi[0];

		logpi[0] = Math.log(pi[0]);
		logpi[1] = Math.log(pi[1]);

		/**
		 * B is longer than 27 length. Its length is 91, index from 0 to 90.
		 * So we can use char to directly index B. However, some of the
		 * place is zero for B. Keep in mind.
		 */
		B = new double[K][DSIZE];
		logB = new double[K][DSIZE];
		for (int i = 0; i < K; i++) {
			double sumb = 0.0d;
			for (int j = 0; j < DSIZE; j++) {
				if (j < 65 && j != 32)
					continue;
				B[i][j] = random.nextDouble();
				sumb += B[i][j];
			}
			for (int j = 0; j < DSIZE; j++) {
				if (j < 65 && j != 32)
					continue;
				B[i][j] = B[i][j]/sumb;
				logB[i][j]=Math.log(B[i][j]);
			}
		}
	}

	public static double logadd(double logl, double logr) {
		// TODO Auto-generated method stub
		if (logl > logr) {
			return logl + Math.log1p(1 + Math.expm1(logr - logl));
		} else if (logl < logr) {
			return logr + Math.log1p(1 + Math.expm1(logl - logr));
		} else {
			return logl + Math.log(2);
		}
	}

	public static double maxdiff(double[][] logA2, double[][] logA) {
		// TODO Auto-generated method stub
		double diff = 0.0d;
		for (int i = 0; i < logA.length; i++) {
			for (int j = 0; j < logA[i].length; j++) {
				diff = Math.max(Math.abs(logA[i][j] - logA2[i][j]), diff);
			}
		}
		return diff;
	}

	public static void printOrigMatrix(double[][] a) {
		// TODO Auto-generated method stub
		for (int i = 0; i < a[0].length; i++) {
			for (int j = 0; j < a.length; j++)
				System.out.print(Math.exp(a[j][i]) + "	");
			System.out.println();
		}
	}

	static void printMatrix(double[][] a) {
		for (int i = 0; i < a[0].length; i++) {
			for (int j = 0; j < a.length; j++)
				System.out.print(a[j][i] + "	");
			System.out.println();
		}

	}

	public static void printDecode(boolean[] seq, char[] OBSV) {
		// TODO Auto-generated method stub
		for (int i1 = 0; i1 < seq.length; i1++) {
			if (seq[i1] == false)
				System.out.print(OBSV[i1]+"/V ");
			else
				System.out.print(OBSV[i1]+"/C ");
		}

	}

}
