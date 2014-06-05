package cmu11761.hw7;

public class LogPi {

	private static final int K = G.K;
	private static int T=G.T;
	private static char[]O = G.O;
	private static double[] pi;
	
	
	public static void estimate(double[][][] psi, double[] psis) {
		// TODO Auto-generated method stub
		pi = new double[K];
		
		double total = psis[0]+psis[1];
		pi[0] = psis[0]/total;
		pi[1] = psis[1]/total;
	}


	public static double[] getLogPi() {
		// TODO Auto-generated method stub
		return pi;
	}
	
	
}
