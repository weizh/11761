package cmu11761.hw7;

public class LogB {

	private static final int K = G.K;
	private static int T=G.T;
	private static char[]O = G.O;
	private static int DSIZE = G.DSIZE;
	private static double[][] logb;

	public static void estimate(double[][][] psi, double[] psis) {
		// TODO Auto-generated method stub
		logb = new double[K][DSIZE];
		for(int i = 0 ; i < K; i ++){
			double den = 0.0d;
			for(int t = 0 ; t<T-2;t++){
				den += psi[i][0][t]+psi[i][1][t];
			}
			
			for(int k = 0 ; k < DSIZE; k ++){
				if(k<65 && k!=32)continue;
				double num = 0.0d;
				for(int t = 0; t <T-2;t++){
					if ((int)O[t]==k){
						num+=psi[i][0][t]+psi[i][1][t];
					}
				}
//				System.out.println(num+" "+den);
				logb[i][k]=Math.log(num) - Math.log(den);
			}
		}
	}

	public static double[][] getLogB() {
		// TODO Auto-generated method stub
		return logb;
	}

}
