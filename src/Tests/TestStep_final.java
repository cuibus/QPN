package Tests;

import java.util.List;

import QuantumUtils.Point;
import QuantumUtils.Qpoz;
import QuantumUtils.Utils;
import QuantumUtils.UtilsMatrix;
import QuantumUtils.UtilsPrint;

public class TestStep_final {
	private static double[] bestChr = new double[]
			{1.5956064231199028, 1.5477396647067376, 1.5631732184629108, 0.02732166016872828}; // 0.98 step 11
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
		float[][] A = UtilsMatrix.getA(teta0, teta1);
		float[][] coin = TestData.gammaForCoinFromPaper;
		float[][] gamma = UtilsMatrix.multiply(A, coin);
		
		System.out.println("initial qbits:");
		System.out.println(getQbitsFromTetas(bestChr));
		System.out.println("GAMMA0 = A(teta1,teta2) * coin_matrix : \n"+UtilsPrint.toString(gamma));
		float teta2 = (float)bestChr[2];
		float teta3 = (float)bestChr[3];
		A = UtilsMatrix.getA(teta2, teta3);
		float[][] U = UtilsMatrix.multiply(A, coin);
		System.out.println("U = A(teta3,teta4) * coin_matrix : \n"+UtilsPrint.toString(U));
		
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon;step++) {
					gamma = UtilsMatrix.multiply(U, gamma);
					System.out.println("\nstep "+ step);
					Utils.step(p, gamma, true);
					double targetProbability = getPointsProbability(p, TestData.targetPoints);
					double trapProbability = getPointsProbability(p, TestData.trapPoints);
					System.out.println(
							"target prob: " + String.format("%.4f", targetProbability) + ", " +
							"trap prob: " + String.format("%.4f", trapProbability));
				}
			}
	}
	
	private static double getPointsProbability(Qpoz[] p, List<Point> points) {
		float probability = 0;
		for (Point targetPoint: points) {
			for (int orient=0;orient<3;orient++)
				probability += p[orient].getProbabilityForPosition(targetPoint.x, targetPoint.y);
		}
		return probability;
	}
}
