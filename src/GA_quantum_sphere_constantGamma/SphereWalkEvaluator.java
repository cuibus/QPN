package GA_quantum_sphere_constantGamma;

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
import QuantumUtils.UtilsPrint;
import Tests.TestData;

public class SphereWalkEvaluator extends FitnessFunction {
	private final double POSITIVE_BIAS = 100;
	private final float thresholdProbabilityTarget = 0.7f;
	private final float thresholdProbabilityTrap = 0.7f;

	public double evaluate(IChromosome chr) {
		float[][] constantGamma = Mapping1(chr);
		Utils.printChr(chr);
		System.out.println("constantGamma: \n " + UtilsPrint.toString(constantGamma));
		double fitness = 0;
		for (Point startPoint: TestData.startPoints)
			for (int orientation: TestData.startOrientations){
				Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
				for (int step=0;step<TestData.timeHorizon;step++) {
					Utils.step(p, constantGamma, false);
					fitness += UtilsGA.getTrapPenalization(p, thresholdProbabilityTrap);
					double bonification = UtilsGA.getTargetBonification(p, thresholdProbabilityTarget);
					if (bonification > 0) {
						fitness -= bonification;
						break; // stop simulation
					}
				}
			}
		return fitness + POSITIVE_BIAS;
	}

	public float[][] Mapping1(IChromosome chr){
		// cromozomul are 4x2 nr float
		float[] re = new float[4];
		float[] im = new float[4];
		for (int i=0;i<4;i++) {
			re[i] = (float)((DoubleGene)chr.getGene(2*i)).doubleValue();
			im[i] = (float)((DoubleGene)chr.getGene(2*i+1)).doubleValue();
		}
		return Utils.getGammaFromReIm(re, im);
	}
	
	public String asStringOneLine(IChromosome chr) {
		float[][] gamma = Mapping1(chr);
		return UtilsPrint.toStringOneLine(gamma);
	}
}
