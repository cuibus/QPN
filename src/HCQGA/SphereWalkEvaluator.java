package HCQGA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.IntegerGene;

import GAUtils.UtilsGA;
import QuantumUtils.Point;
import QuantumUtils.Qbit;
import QuantumUtils.Qpoz;
import QuantumUtils.Qvec;
import QuantumUtils.Utils;
import QuantumUtils.UtilsMatrix;
import QuantumUtils.UtilsPrint;
import QuantumUtils.WalkerUtils;
import Tests.TestData;

public class SphereWalkEvaluator extends FitnessFunction {
	private final double POSITIVE_BIAS = 10;
	
	private final float thresholdProbabilityTarget = 0.7f;

	public double evaluate(IChromosome chr) {
		float[][] gamma = MappingGamma0(chr); // this is gamma0 at this point
		float[][] U = MappingU(chr);

		//Qvec[] f = Utils.getFitnessInitial(32 /*length*/, 0 /*right*/, 15/*middle*/);
		float fitness_overall = 0;
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				//Qbit[] fitness = new Qbit[10];
				//for (int i=0;i<10;i++) fitness[i] = Qbit.Ket0();
				//int currentFitnessIndex = 4;
				//float angle_cumulat = 50;
				//Qbit fitness = Qbit.Ket0();
				Qvec[] f = Utils.getFitnessInitial(32/*length*/, 1/*orientation*/, 16/*middle*/);
				
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon-1;step++) {
					gamma = UtilsMatrix.multiply(U, gamma);
					Utils.step(p, gamma, false);
					
					/*float theta = (float)WalkerUtils.psi_g(p) - WalkerUtils.psi_p(p) - 0.05f*WalkerUtils.psi_n(p);
					angle_cumulat += step*0.5* theta;
					*/
					float[][] A = UtilsMatrix.getAForFitness(WalkerUtils.psi_g(p), WalkerUtils.psi_p(p));
					Utils.stepLinear(f, A, false);
					
					/*if (WalkerUtils.psi_p(p) > 0.7)
						fitness = UtilsMatrix.multiply(TestData.shiftLeft8, fitness);
					if (WalkerUtils.psi_g(p) > 0.7)
						fitness = UtilsMatrix.multiply(TestData.shiftRight8, fitness);
						*/
					/*float[][] matr = UtilsMatrix.add(
							UtilsMatrix.multiply(TestData.shiftRight8,
									//3*WalkerUtils.psi_g(p) + 0.55f*WalkerUtils.psi_n(p)),
									(float)(WalkerUtils.psi_g(p) - WalkerUtils.psi_p(p) - 0.1*WalkerUtils.psi_n(p))),
							//UtilsMatrix.multiply(TestData.IdentityMatrix(8), WalkerUtils.psi_n(p)),
							UtilsMatrix.multiply(TestData.IdentityMatrix(8),
									//WalkerUtils.psi_p(p) + 0.45f*WalkerUtils.psi_n(p))
									//1-WalkerUtils.psi_g(p))
									0)
							);
			*/
					//float[][] shL = UtilsMatrix.multiply(TestData.shiftRight8, WalkerUtils.psi_g(p))
				   // fitness = UtilsMatrix.multiply(matr, fitness);
					
					//fitness_overall += WalkerUtils.psi_g(p) - WalkerUtils.psi_p(p) - 0.1f*WalkerUtils.psi_n(p); 
					//float theta = (float)Math.atan(WalkerUtils.psi_g(p) - WalkerUtils.psi_p(p) - 0.1f*WalkerUtils.psi_n(p));
					//fitness = fitness.rotate(UtilsMatrix.getA(theta));
					/*
					float theta0 = (float)Math.atan(WalkerUtils.psi_g(p) / ( 1.0d * WalkerUtils.psi_n(p))); // k1 = coefficient for fulfilling target conditions
					float theta1 = (float)Math.atan(WalkerUtils.psi_p(p) / ( 1.0d * WalkerUtils.psi_n(p))); // k2 = coefficient for fulfilling trap conditions
					fitness = UtilsMatrix.shift(
							UtilsMatrix.multiply(
								UtilsMatrix.multiply(
										TestData.gammaForCoinFromPaper,
										UtilsMatrix.getA(theta0, theta1)
								),
								fitness
							));
							*/
					//System.out.println(UtilsPrint.toString(fitness));
					//try {System.in.read();}catch (IOException e) {};
					//fitness_overall = fitness.re[1];
					if (WalkerUtils.psi_g(p) > thresholdProbabilityTarget) {
						
						break;
					}
					fitness_overall += f[0].getTotalProbability() - f[1].getTotalProbability();

				}
				//fitness_overall += UtilsMatrix.toBase10(f[0]);
				//fitness_overall += UtilsMatrix.sum(fitness);
				//fitness_overall += UtilsMatrix.toBase10(fitness);
				//fitness_overall += Math.max(0, (int)(angle_cumulat / (Math.PI / 2)));
				//fitness_overall += WalkerUtils.getTotalFitness(f);
			}
		//System.out.println(fitness + POSITIVE_BIAS);
		return fitness_overall + POSITIVE_BIAS;
	}
	
	public String getPsiString(IChromosome chr) {
		float psi_p_min = 1, psi_p_max = 0;
		float psi_g_min = 1, psi_g_max = 0;
		float psi_n_min = 1, psi_n_max = 0;
		int maxStep = TestData.timeHorizon;
		float[][] gamma = MappingGamma0(chr); // this is gamma0 at this point
		float[][] U = MappingU(chr); 
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon;step++) {
					gamma = UtilsMatrix.multiply(U, gamma);
					Utils.step(p, gamma, false);

					float psi_p = WalkerUtils.psi_p(p);
					float psi_g = WalkerUtils.psi_g(p);
					float psi_n = WalkerUtils.psi_n(p);
					psi_p_min = Math.min(psi_p_min, psi_p);
					psi_p_max = Math.max(psi_p_max, psi_p);
					psi_g_min = Math.min(psi_g_min, psi_g);
					psi_g_max = Math.max(psi_g_max, psi_g);
					psi_n_min = Math.min(psi_n_min, psi_n);
					psi_n_max = Math.max(psi_n_max, psi_n);
					
					if (WalkerUtils.psi_g(p) > thresholdProbabilityTarget) {
						maxStep = step;
						break;
					}
				}
			}
		/*return String.format("[%.2f-%.2f][%.2f-%.2f][%.2f-%.2f] maxstep "+maxStep,
				psi_n_min, psi_n_max,
				psi_g_min, psi_g_max,
				psi_p_min, psi_p_max);
				*/
		return String.format("%.6f\t%.6f",psi_g_max,psi_p_max);
	}
	
	
	public float[][] MappingGamma0(IChromosome chr){
		// returneaza gamma, cromozomul are 2 nr float [0, 2*pi]
		float teta0 = (float)((DoubleGene)chr.getGene(0)).doubleValue();
		float teta1 = (float)((DoubleGene)chr.getGene(1)).doubleValue();
		float[][] A = UtilsMatrix.getA(teta0, teta1);
		float[][] coin = TestData.gammaForCoinFromPaper;

		return UtilsMatrix.multiply(A, coin);
	}
	
	public float[][] MappingU(IChromosome chr){
		// returneaza gamma, cromozomul are 2 nr float [0, 2*pi]
		float teta2 = (float)((DoubleGene)chr.getGene(2)).doubleValue();
		float teta3 = (float)((DoubleGene)chr.getGene(3)).doubleValue();
		float[][] A = UtilsMatrix.getA(teta2, teta3);
		float[][] coin = TestData.gammaForCoinFromPaper;

		return UtilsMatrix.multiply(A, coin);
	}
	
	
	public String asStringOneLine(IChromosome chr) {
		StringBuilder sb = new StringBuilder("");
		float[][] gamma0 = MappingGamma0(chr);
		float[][] U = MappingU(chr);
		sb.append("gamma0: ");
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++)
				sb.append(gamma0[i][j] + " ");
			sb.append("|");
		}
		
		sb.append(" U: ");
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++)
				sb.append(U[i][j] + " ");
			sb.append("|");
		}
		return sb.toString();
	}
}
