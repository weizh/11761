package cmu11761.hw7;

public class LogA {

	private static final int K = G.K;
	private static int T = G.T;
	private static char[] O = G.O;

	private static double[][] loga;

	public static void estimate(double[][][] psi, double[] psis) {
		// TODO Auto-generated method stub
		loga = new double[K][K];

		for (int i = 0; i < K; i++) {

			double den = 0.0d;
			for (int t = 0; t < T - 2; t++) {
				den += psi[i][0][t] + psi[i][1][t];
			}

			for (int j = 0; j < K; j++) {
				double num = 0.0d;
				for (int t = 0; t < T - 2; t++) {
					num += psi[i][j][t];
				}
				loga[i][j] = Math.log(num) - Math.log(den);
			}
		}
	}

	public static double[][] getLogA() {
		// TODO Auto-generated method stub
		return loga;
	}
}
