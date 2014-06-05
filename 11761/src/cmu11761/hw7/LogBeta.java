package cmu11761.hw7;

import java.io.IOException;

public class LogBeta {
	private static double loglikelihood = 0.0d;
	private static double[][] logbeta;
	static int T = G.T;
	static int K = G.K;
	static char[] O = G.O;

	public static void main(String argv[]) throws NumberFormatException, IOException {
		G.initialize();
		LogBeta.train(G.logA, G.logB, G.logpi);
		// G.printMatrix(G.logA);
		// G.printMatrix(G.logB);
	}

	public static void train(double[][] loga, double[][] logb, double[] logPi) {
		// TODO Auto-generated method stub

		logbeta = new double[K][T];

		// logbeta has been implicitly initialized to be zero.
		for (int t = T - 2; t > -1; t--) {
			for (int i = 0; i < K; i++) {
				double b0 = (loga[i][0] + logb[0][O[t + 1]] + logbeta[0][t + 1]);
				double b1 = (loga[i][1] + logb[1][O[t + 1]] + logbeta[1][t + 1]);
				// System.out.println("b0 is "+b0);
				// System.out.println("b1 is "+b1);
				logbeta[i][t] = G.logadd(b0, b1);
				// G.print (logb[0][O[t+1]]);
			}
		}
		// G.printMatrix(logbeta);
		loglikelihood = G.logadd(logPi[0] + logb[0][0] + logbeta[0][0], logPi[1] + logb[1][0] + logbeta[1][0]);

//		System.out.println(loglikelihood);
//		System.out.println((loglikelihood / (double) T));
	}

	public static double[][] getLogBeta() {
		// TODO Auto-generated method stub
		return logbeta;
	}

	public static double getAvgll() {
		// TODO Auto-generated method stub
		return (loglikelihood / (double) T);
	}

}
