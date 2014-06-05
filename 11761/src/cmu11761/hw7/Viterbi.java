package cmu11761.hw7;

public class Viterbi {

	private static double[][] logalpha;
	private static double loglikelihood = 0.0;
	static int T;
	static int K = G.K;
	static char[] O = G.O;
	// True for 1, False for state 0.
	private static boolean bestsequence[];

	public static void decode(double[][] loga, double[][] logb, double[] logpi, char[] OBSV) {

		// TODO Auto-generated method stub
		O = OBSV;
		T = OBSV.length;
		bestsequence = new boolean[T];

		logalpha = new double[K][T];
		logalpha[0][0] = logpi[0] + logb[0][O[0]];
		logalpha[1][0] = logpi[1] + logb[1][O[0]];
		if (logalpha[0][0] > logalpha[1][0])
			bestsequence[0] = false;
		else
			bestsequence[0] = true;
		for (int t = 1; t < T; t++) {
			for (int j = 0; j < K; j++) {
				double logps0 = logalpha[0][t - 1] + loga[0][j];
				double logps1 = logalpha[1][t - 1] + loga[1][j];
				logalpha[j][t] = Math.max(logps0, logps1) + logb[j][O[t]];
			}
			bestsequence[t]= logalpha[0][t]>logalpha[1][t]? false:true;

		}
		// G.printMatrix(logalpha);
//		loglikelihood = G.logadd(logalpha[0][T - 1], logalpha[1][T - 1]);
		// System.out.println(loglikelihood);
		// System.out.println((loglikelihood/(double)T));
	}

	public static double[][] getLogAlpha() {
		// TODO Auto-generated method stub
		return logalpha;
	}

	public static double getAvgll() {
		// TODO Auto-generated method stub
		return (loglikelihood / (double) T);
	}

	public static boolean[] getBestSequence() {
		// TODO Auto-generated method stub
		return bestsequence;
	}

}
