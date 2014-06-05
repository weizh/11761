package cmu11761.hw7;

public class Psi {

	private static int K = G.K;
	private static int T = G.T;
	private static char[] O = G.O;
	private static double[][][] Psi;

	private static double logPsiS[];
	private static double PsiS[];

	public static void train(double[][] logA, double[][] logB, double[] logPi, double[][] logalpha, double[][] logbeta) {
		// TODO Auto-generated method stub
		logPsiS = new double[K];
		PsiS = new double[K];
		Psi = new double[K][K][T - 1];
		logPsiS[0] = logPi[0] + logB[0][O[1]] + logbeta[0][1];
		logPsiS[1] = logPi[1] + logB[1][O[1]] + logbeta[1][1];
		double logsum = G.logadd(logPsiS[0], logPsiS[1]);
		logPsiS[0] -= logsum;
		logPsiS[1] -= logsum;

		PsiS[0] = Math.expm1(logPsiS[0]) + 1;
		PsiS[1] = 1 - PsiS[0];
		for (int t = 0; t < T - 1; t++) {
			double z00 = logalpha[0][t] + logA[0][0] + logB[0][O[t + 1]] + logbeta[0][t + 1];
			double z01 = logalpha[0][t] + logA[0][1] + logB[1][O[t + 1]] + logbeta[1][t + 1];
			double z10 = logalpha[1][t] + logA[1][0] + logB[0][O[t + 1]] + logbeta[0][t + 1];
			double z11 = logalpha[1][t] + logA[1][1] + logB[1][O[t + 1]] + logbeta[1][t + 1];
			Psi[0][0][t] = 1 / (4 + Math.expm1(z01 - z00) + Math.expm1(z10 - z00) + Math.expm1(z11 - z00));
			Psi[0][1][t] = 1 / (4 + Math.expm1(z00 - z01) + Math.expm1(z10 - z01) + Math.expm1(z11 - z01));
			Psi[1][0][t] = 1 / (4 + Math.expm1(z01 - z10) + Math.expm1(z00 - z10) + Math.expm1(z11 - z10));
			Psi[1][1][t] = 1 / (4 + Math.expm1(z01 - z11) + Math.expm1(z10 - z11) + Math.expm1(z00 - z11));

		}
	}

	public static double[] getPsiS() {
		// TODO Auto-generated method stub
		return PsiS;
	}

	public static double[][][] getPsi() {
		// TODO Auto-generated method stub
		return Psi;
	}
}
