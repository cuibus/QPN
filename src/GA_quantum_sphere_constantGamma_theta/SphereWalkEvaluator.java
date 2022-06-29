package GA_quantum_sphere_constantGamma_theta;

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
import QuantumUtils.UtilsPrint;
import Tests.TestData;

public class SphereWalkEvaluator extends FitnessFunction {
	private final double POSITIVE_BIAS = 30;
	private final float thresholdProbabilityTarget = 0.55f;
	private final float thresholdProbabilityTrap = 0.3f;

	public double evaluate(IChromosome chr) {
		float[][] constantGamma = Mapping1(chr);
		float teta0 = (float)((DoubleGene)chr.getGene(0)).doubleValue();
		float teta1 = (float)((DoubleGene)chr.getGene(1)).doubleValue();
		//System.out.println("teta0: "+teta0 + "\t teta1: "+ teta1);
		double fitness = 0;
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon;step++) {
					Utils.step(p, constantGamma, false);
					fitness += UtilsGA.getTrapPenalization(p, thresholdProbabilityTrap);
					double bonification = UtilsGA.getTargetBonification(p, thresholdProbabilityTarget);
					fitness -= bonification;
					if (bonification >= 20) {
						break; // stop simulation
					}
				}
			}
		//System.out.println(fitness + POSITIVE_BIAS);
		return fitness + POSITIVE_BIAS;
	}

	public float[][] Mapping1(IChromosome chr){
		// returneaza gamma, cromozomul are 2 nr float intre [0, 2*pi]
		float teta0 = (float)((DoubleGene)chr.getGene(0)).doubleValue();
		float teta1 = (float)((DoubleGene)chr.getGene(1)).doubleValue();
		float[][] A = UtilsMatrix.getA(teta0, teta1);
		float[][] coin = TestData.gammaForCoinFromPaper;

		return UtilsMatrix.multiply(A, coin);
	}
	
	public String asStringOneLine(IChromosome chr) {
		float[][] gamma = Mapping1(chr);
		return UtilsPrint.toStringOneLine(gamma);
	}
	

}
