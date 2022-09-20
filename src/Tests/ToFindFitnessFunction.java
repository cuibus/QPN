package Tests;

import QuantumUtils.Point;
import QuantumUtils.Qpoz;
import QuantumUtils.Qvec;
import QuantumUtils.Utils;
import QuantumUtils.UtilsMatrix;
import QuantumUtils.UtilsPrint;
import QuantumUtils.WalkerUtils;

public class ToFindFitnessFunction {

	public static void main(String[] args) {
		float[] fitness = new float[] {0, 0, 0, 0, 0, 0, 0, 1};
		float psi_n = 0.8f; //neutral
		float psi_g = 0.7f; //target
		float psi_p = 0.1f; //trap
		float psi_pg = 0f; //target and trap
		/*float[][] m = UtilsMatrix.multiply(
				UtilsMatrix.multiply(TestData.shiftRight8, psi_g),
				UtilsMatrix.multiply(TestData.shiftLeft8, psi_p));
				*/
		//System.out.println("det(m)="+UtilsMatrix.determinantOfMatrix(m, 8)+"\n"+UtilsPrint.toString(m));
		/*float[][] matr = UtilsMatrix.multiply(UtilsMatrix.add(
				UtilsMatrix.multiply(TestData.shiftRight8, psi_g),
				UtilsMatrix.multiply(TestData.IdentityMatrix(8), psi_n),
				UtilsMatrix.multiply(TestData.shiftLeft8, -psi_p)
				), 1.0f);
				*/
		/*float[][] matr1 = UtilsMatrix.add(
				UtilsMatrix.multiply(TestData.shiftRight8,
						//3*WalkerUtils.psi_g(p) + 0.55f*WalkerUtils.psi_n(p)),
						//psi_g - psi_p - 0.1f*psi_n),
						psi_g),
				//UtilsMatrix.multiply(TestData.IdentityMatrix(8), WalkerUtils.psi_n(p)),
				UtilsMatrix.multiply(TestData.IdentityMatrix(8),
						//WalkerUtils.psi_p(p) + 0.45f*WalkerUtils.psi_n(p))
						1-psi_g)
				);
				*/
		/*
		float[][] matr1 = TestData.IdentityMatrix(8);
		float[][] matr2 = UtilsMatrix.add(
				UtilsMatrix.multiply(TestData.shiftLeft8,
						//3*WalkerUtils.psi_g(p) + 0.55f*WalkerUtils.psi_n(p)),
						//psi_g - psi_p - 0.1f*psi_n),
						psi_p),
				//UtilsMatrix.multiply(TestData.IdentityMatrix(8), WalkerUtils.psi_n(p)),
				UtilsMatrix.multiply(TestData.IdentityMatrix(8),
						//WalkerUtils.psi_p(p) + 0.45f*WalkerUtils.psi_n(p))
						1-psi_p)
				);
				*/
		//matr1 = UtilsMatrix.multiply(matr1, 1f/8f/ UtilsMatrix.determinantOfMatrix(matr1, 8));
		
		Qvec[] f = Utils.getFitnessInitial(16 /*length*/, 0 /*right*/, 8/*middle*/);
		float[][] A = UtilsMatrix.getAForFitness(psi_g, psi_p);
		System.out.println("determinant A: " + UtilsMatrix.determinantOfMatrix(A, 2));
		for (int step=0;step<TestData.timeHorizon-1;step++) {
			//System.out.println("step "+ step);
			Utils.stepLinear(f, A, true);
			System.out.println("step "+step+", Fitness: " + (f[0].getTotalProbability() - f[1].getTotalProbability())); //WalkerUtils.getTotalFitness(f));
		}
		/*
		System.out.println("inmultite: \n" + UtilsPrint.toString(UtilsMatrix.multiply(matr1,  matr2)));
		System.out.println("det(matr)="+UtilsMatrix.determinantOfMatrix(matr1, 8)+"\n"+UtilsPrint.toString(matr1));
		for (int step=0;step<16;step++) {
			System.out.println("step "+step+": "+String.format("%.4f", UtilsMatrix.toBase10(fitness)) + " | " + UtilsPrint.toString(fitness));
			fitness = UtilsMatrix.multiply(matr1, fitness);
			fitness = UtilsMatrix.multiply(matr2, fitness);
		}
		*/
	}

	

}
