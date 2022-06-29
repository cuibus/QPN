package GAUtils;

import org.jgap.Genotype;
import org.jgap.IChromosome;

import QuantumUtils.Point;
import QuantumUtils.Qpoz;
import Tests.TestData;

public class UtilsGA {
	// adds the given chromosomes (as double[]) to the population by replacing the values of the last chromosomes in that population
	// unfortunately, creating new chromosomes is very difficult, so we just replace existing ones
	public static void addChrToPopulation(Genotype population, double[][] chrsAsDoubleVector) {
		int popsize = population.getPopulation().size();
		for (int i=0;i<chrsAsDoubleVector.length;i++) {
			IChromosome chr = population.getPopulation().getChromosome(popsize-1-i);
			for (int gene=0;gene<chrsAsDoubleVector[i].length;gene++)
				chr.getGene(gene).setAllele(chrsAsDoubleVector[i][gene]);
		}
	}

	// gets the penalization if the probability of Qpoz at trap position is greater than threshold
	public static double getTrapPenalization(Qpoz[] p, float thresholdProbabilityTrap) {
		double penalization = 0;
		for (Point trapPoint: TestData.trapPoints) {
			float probability = 0;
			for (int orient=0;orient<3;orient++)
				probability += p[orient].getProbabilityForPosition(trapPoint.x, trapPoint.y);
			penalization += probability > thresholdProbabilityTrap ? 20 : 0;
		}
		return penalization;
	}
	
	// gets the bonification if the probability of Qpoz at target position is greater than threshold
	public static double getTargetBonification(Qpoz[] p, double thresholdProbabilityTarget) {
		double bonification = 0;
		for (Point targetPoint: TestData.targetPoints) {
			float probability = 0;
			for (int orient=0;orient<3;orient++)
				probability += p[orient].getProbabilityForPosition(targetPoint.x, targetPoint.y);
			bonification += probability > thresholdProbabilityTarget ? 20 : 0;
		}
		return bonification;
	}

}
