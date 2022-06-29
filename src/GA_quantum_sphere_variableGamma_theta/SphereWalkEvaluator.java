package GA_quantum_sphere_variableGamma_theta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.IntegerGene;

import GAUtils.UtilsGA;
import QuantumUtils.Point;
import QuantumUtils.Qpoz;
import QuantumUtils.Utils;
import QuantumUtils.UtilsMatrix;
import Tests.TestData;

public class SphereWalkEvaluator extends FitnessFunction {
	private final double POSITIVE_BIAS = 30;
	private final float thresholdProbabilityTarget = 0.7f;
	private final float thresholdProbabilityTrap = 0.3f;

	public double evaluate(IChromosome chr) {
		float[][] gamma = MappingGamma0(chr); // this is gamma0 at this point
		float[][] U = MappingU(chr);

		double fitness = 0;
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon;step++) {
					gamma = UtilsMatrix.multiply(U, gamma);
					Utils.step(p, gamma, false);
					fitness += UtilsGA.getTrapPenalization(p, thresholdProbabilityTrap);
					double targetProbability = getTargetPointsProbability(p);
					double bonification = targetProbability > thresholdProbabilityTarget ? 20 : 0;
					fitness -= bonification + targetProbability;
					if (bonification >= 20) {
						break; // stop simulation
					}
					fitness += 1; // penalize each step
				}
			}
		//System.out.println(fitness + POSITIVE_BIAS);
		return fitness + POSITIVE_BIAS;
	}


	private double getTargetPointsProbability(Qpoz[] p) {
		float probability = 0;
		for (Point targetPoint: TestData.targetPoints) {
			for (int orient=0;orient<3;orient++)
				probability += p[orient].getProbabilityForPosition(targetPoint.x, targetPoint.y);
		} 	
		return probability;
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
		
		sb.append("U: ");
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++)
				sb.append(U[i][j] + " ");
			sb.append("|");
		}
		return sb.toString();
	}
	
	public double getTargetMaxProbability(IChromosome chr) {
		float[][] gamma = MappingGamma0(chr); // this is gamma0 at this point
		float[][] U = MappingU(chr);
		
		double maxProbability = 0;
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon;step++) {
					gamma = UtilsMatrix.multiply(U, gamma);
					Utils.step(p, gamma, false);
					double targetProbability = getTargetPointsProbability(p);
					maxProbability = Math.max(maxProbability, targetProbability);
				}
			}
		return maxProbability;
	}
	
	public int getStepMaxProb(IChromosome chr) {
		float[][] gamma = MappingGamma0(chr); // this is gamma0 at this point
		float[][] U = MappingU(chr);
		
		double maxProbability = 0;
		int stp = 0;
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon;step++) {
					gamma = UtilsMatrix.multiply(U, gamma);
					Utils.step(p, gamma, false);
					double targetProbability = getTargetPointsProbability(p);
					if (maxProbability < targetProbability)
						stp = step;
					maxProbability = Math.max(maxProbability, targetProbability);
				}
			}
		return stp;
	}
}
