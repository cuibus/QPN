package GA_quantum_sphere_variableGamma_theta;

import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.impl.DoubleGene;

import QuantumUtils.Point;
import QuantumUtils.Qpoz;
import QuantumUtils.Utils;
import QuantumUtils.MatrixUtils;
import QuantumUtils.WalkerUtils;
import Tests.TestData;

public class SphereWalkEvaluator extends FitnessFunction {
	private final double POSITIVE_BIAS = 20;
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
					gamma = MatrixUtils.multiply(U, gamma);
					Utils.step(p, gamma, false);
					
					double trapProbability = WalkerUtils.getProbForPositionsTotal(p, TestData.trapPoints);
					fitness -= trapProbability > thresholdProbabilityTrap ? 20 : 0;
					double targetProbability = WalkerUtils.getProbForPositionsTotal(p, TestData.targetPoints);
					double bonification = targetProbability > thresholdProbabilityTarget ? 20 : 0;
					fitness += bonification + targetProbability;
					
					if (bonification >= 20) {
						break; // stop simulation
					}
					fitness -= 1; // penalize each step	
				}
			}
		//System.out.println(fitness + POSITIVE_BIAS);
		return fitness + POSITIVE_BIAS;
	}
	
	public String getPsiString(IChromosome chr) {
		float psi_p_min = 1, psi_p_max = 0;
		float psi_g_min = 1, psi_g_max = 0;
		float psi_n_min = 1, psi_n_max = 0;
		float[][] gamma = MappingGamma0(chr); // this is gamma0 at this point
		float[][] U = MappingU(chr); 
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon;step++) {
					gamma = MatrixUtils.multiply(U, gamma);
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
						break;
					}
				}
			}
		return String.format("%.6f\t%.6f",psi_g_max,psi_p_max);
	}

	public float[][] MappingGamma0(IChromosome chr){
		// returneaza gamma, cromozomul are 2 nr float [0, 2*pi]
		float teta0 = (float)((DoubleGene)chr.getGene(0)).doubleValue();
		float teta1 = (float)((DoubleGene)chr.getGene(1)).doubleValue();
		float[][] A = MatrixUtils.getA(teta0, teta1);
		float[][] coin = TestData.gammaForCoinFromPaper;

		float[][] result = MatrixUtils.multiply(A, coin);
		//System.out.println("A\n"+UtilsPrint.toStringOneLine(result));
		return result;
	}
	
	public float[][] MappingU(IChromosome chr){
		// returneaza gamma, cromozomul are 2 nr float [0, 2*pi]
		float teta2 = (float)((DoubleGene)chr.getGene(2)).doubleValue();
		float teta3 = (float)((DoubleGene)chr.getGene(3)).doubleValue();
		float[][] A = MatrixUtils.getA(teta2, teta3);
		float[][] coin = TestData.gammaForCoinFromPaper;

		float[][] result = MatrixUtils.multiply(A, coin);
		//System.out.println("U\n"+UtilsPrint.toStringOneLine(result));
		return result;
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
		return getMaxProbabilityFromSimulation(chr, TestData.targetPoints);
	}
	
	public double getTrapMaxProbability(IChromosome chr) {
		return getMaxProbabilityFromSimulation(chr, TestData.trapPoints);
	}
	private double getMaxProbabilityFromSimulation(IChromosome chr, List<Point> points) {
		float[][] gamma = MappingGamma0(chr); // this is gamma0 at this point
		float[][] U = MappingU(chr);
		
		double maxProbability = 0;
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon;step++) {
					gamma = MatrixUtils.multiply(U, gamma);
					Utils.step(p, gamma, false);
					double prob = WalkerUtils.getProbForPositionsTotal(p, points);
					maxProbability = Math.max(maxProbability, prob);
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
					gamma = MatrixUtils.multiply(U, gamma);
					Utils.step(p, gamma, false);
					double targetProbability = WalkerUtils.getProbForPositionsTotal(p, TestData.targetPoints);
					if (maxProbability < targetProbability)
						stp = step;
					maxProbability = Math.max(maxProbability, targetProbability);
				}
			}
		return stp;
	}
}
