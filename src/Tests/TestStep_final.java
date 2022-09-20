package Tests;

import QuantumUtils.MatrixUtils;
import QuantumUtils.Point;
import QuantumUtils.Qpoz;
import QuantumUtils.Utils;
import QuantumUtils.PrintUtils;
import QuantumUtils.WalkerUtils;

public class TestStep_final {
	private static double[] bestChr = new double[]
			//{1.5956064231199028, 1.5477396647067376, 1.5631732184629108, 0.02732166016872828}; // 0.98 step 11
			//{1.506280319,	1.515821243,	6.277452228,	1.575274492};
			//{1.533609729,	1.545308049,	4.715572125,	6.279499461};
			{6.231307762,	1.515612706,	0.855429173,	2.266428197};
	
	// fiecare coeficient = arctg(beta/alfa), unde beta^2+alfa^2 = 1;
	// => tan(coef) = beta/alfa => b = tan(coef)*a =>
	// tan^2*a^2 + a^2 = 1 => a = sqrt(1/(1+tan^2))
	public static String getQbitsFromTetas(double[] tetas) {
		StringBuilder sb = new StringBuilder();
		for (double teta:tetas) {
			double b_supra_a = Math.tan(teta);
			double a = Math.sqrt(1/(1+b_supra_a*b_supra_a));
			double b = Math.tan(teta) * a;
			sb.append(a + "*|0>+" + b + "*|1>\n");
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		float teta0 = (float)bestChr[0];
		float teta1 = (float)bestChr[1];
		float[][] A = MatrixUtils.getA(teta0, teta1);
		float[][] coin = TestData.gammaForCoinFromPaper;
		float[][] gamma = MatrixUtils.multiply(A, coin);
		
		System.out.println("initial qbits:");
		System.out.println(getQbitsFromTetas(bestChr));
		System.out.println("GAMMA0 = A(teta1,teta2) * coin_matrix : \n"+PrintUtils.toString(gamma));
		
		float teta2 = (float)bestChr[2];
		float teta3 = (float)bestChr[3];
		A = MatrixUtils.getA(teta2, teta3);
		float[][] U = MatrixUtils.multiply(A, coin);
		System.out.println("U = A(teta3,teta4) * coin_matrix : \n"+PrintUtils.toString(U));
		
		Point startPoint = TestData.startPoints.get(0);
		int startOrientation = TestData.startOrientations[0];
		Qpoz[] p = Utils.getQpozInitial(startOrientation, startPoint.x, startPoint.y);
		
		//System.out.println("psi step000: " + UtilsPrint.getPsiFormatted(p));
		//System.out.println(UtilsPrint.toString(WalkerUtils.getProbForAllPositions(p)));
		
		for (int step=0;step<TestData.timeHorizon;step++) {
			gamma = MatrixUtils.multiply(U, gamma);
			//System.out.println("\nstep "+ step);
			Utils.step(p, gamma, false);
			//System.out.println("psi (neutral,target,trap, &), step "+step+": " + UtilsPrint.getPsiFormatted(p));
			//System.out.println(UtilsPrint.toString(WalkerUtils.getProbForAllPositions(p)));
			float[][] prob = WalkerUtils.getProbForAllPositions(p);
			System.out.println("step "+step+", " + getMaxProbString(prob) + 
					", all probabilities:\n" + PrintUtils.toString(prob));
			
		}
	}
	
	private static String getMaxProbString(float[][] prob) {
		int imax=0,jmax = 0;
		float probmax = prob[0][0];
		for (int i=0;i<prob.length;i++)
			for (int j=0;j<prob[0].length;j++)
				if (prob[i][j] > probmax) {
					probmax = prob[i][j];
					imax = i;
					jmax = j;
				}
		return String.format("maxProbability: %.4f at pos (%d,%d)", probmax, imax, jmax);
	}

}
