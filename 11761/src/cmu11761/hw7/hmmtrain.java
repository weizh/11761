package cmu11761.hw7;

import java.io.BufferedReader;
import java.io.IOException;

import util.GetReader;

public class hmmtrain {

	static double dfactor = Math.log(2.0d);

//This is the main entrance of the code.
	public static void main(String argv[]) throws NumberFormatException, IOException {
		// G.initialize();
		G.ramdomInitialize();
		double logA[][] = G.logA;
		double logB[][] = G.logB;
		double logPi[] = G.logpi;

		// G.printMatrix(logA);

		double newalphaavg, alphaavg = 0.0;
		int i = 0;
		while (true) {
			System.out.print("Iteration " + (i++) + " :");
			// G.printMatrix(logA);
			// G.printMatrix(logB);

			LogAlpha.train(logA, logB, logPi);
			LogBeta.train(logA, logB, logPi);

			newalphaavg = LogAlpha.getAvgll();
			// double newbetaavg = LogBeta.getAvgll();
			G.print((newalphaavg / dfactor));
			// G.print(newbetaavg/dfactor);
			double newLogAlpha[][] = LogAlpha.getLogAlpha();
			double newLogBeta[][] = LogBeta.getLogBeta();

			Psi.train(logA, logB, logPi, newLogAlpha, newLogBeta);
			double[] psis = Psi.getPsiS();
			double[][][] psi = Psi.getPsi();

			LogPi.estimate(psi, psis);
			LogA.estimate(psi, psis);
			LogB.estimate(psi, psis);
			double[] newlogPi = LogPi.getLogPi();
			double newlogA[][] = LogA.getLogA();
			double newlogB[][] = LogB.getLogB();

			if (G.maxdiff(logA, newlogA) > 1E-5)
			// if(Math.abs(newalphaavg-alphaavg)>1E-6d)
			{
				logPi = newlogPi;
				logA = newlogA;
				logB = newlogB;
				alphaavg = newalphaavg;
			} else {
				logPi = newlogPi;
				logA = newlogA;
				logB = newlogB;
				alphaavg = newalphaavg;
				break;
			}

		}
		System.out.println("original A is:");
		G.printOrigMatrix(G.logA);
		System.out.println("New A is:");
		G.printOrigMatrix(logA);
		// G.printOrigMatrix(G.logB);
		System.out.println("new B is:");
		G.printOrigMatrix(logB);
		System.out.println("final Avgll is:");

		// Q8 Test the corpus
//		BufferedReader r = GetReader.getFileReader("teststringQ8.txt");
		BufferedReader r = GetReader.getFileReader("stringQ14.txt");

		String s = r.readLine();
		r.close();
		G.O = s.toCharArray();
		G.T = G.O.length;

		// Use the natrual HMM to test Q8
		// LogAlpha.train(G.logA, G.logB, logPi);
		// Use the EM trained HMM to test Q8
		LogAlpha.train(logA, logB, logPi);
		G.print(LogAlpha.getAvgll() / dfactor);

		// Q9 Viterbi Decoding:
		r = GetReader.getFileReader("stringQ9.txt");
		s = r.readLine();
		System.out.println("{" + s + "}");
		char[] OBSV = s.toCharArray();
		r.close();

		System.out.println("\n{ Veterbi Decode ");

		Viterbi.decode(logA, logB, logPi, OBSV);
		G.print(Viterbi.getAvgll() / dfactor);
		boolean[] seq = Viterbi.getBestSequence();
		G.printDecode(seq, OBSV);

		System.out.println("\n{ Naive Decode ");
		// Q10
		Naive.decode(logA, logB, logPi, OBSV);
		boolean[] naiveseq = Naive.getBestSequence();
		G.printDecode(naiveseq, OBSV);

	}
}
