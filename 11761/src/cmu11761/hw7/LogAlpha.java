package cmu11761.hw7;

public class LogAlpha {

	private static double [][] logalpha;
	private static double loglikelihood = 0.0;
	static int T = G.T; 
	static int K = G.K;
	static char[] O = G.O;
	
	public static void train(double[][] loga, double[][] logb,double[]logpi) {
		// TODO Auto-generated method stub
		logalpha = new double[K][T];
		logalpha[0][0] = logpi[0] + logb[0][O[0]];
		logalpha[1][0] = logpi[1] + logb[1][O[0]];
		
		for(int t = 1; t <T; t++)
			for(int j = 0; j <K;j++){
				double logd = G.logadd(logalpha[0][t-1]+loga[0][j],logalpha[1][t-1]+loga[1][j]);
				logalpha[j][t] = logd + logb[j][O[t]];
			}
	//	G.printMatrix(logalpha);
		loglikelihood = G.logadd(logalpha[0][T-1],logalpha[1][T-1]);
//		System.out.println(loglikelihood);
//		System.out.println((loglikelihood/(double)T));

	}

	public static double[][] getLogAlpha() {
		// TODO Auto-generated method stub
		return logalpha;
	}

	public static double getAvgll() {
		// TODO Auto-generated method stub
		return (loglikelihood/(double)T);
	}
	
	

}
