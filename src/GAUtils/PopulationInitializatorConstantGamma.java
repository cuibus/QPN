package GAUtils;

import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

public class PopulationInitializatorConstantGamma {
	// initializes the population with chromosomes filled with constant values
	public static Genotype InitialGenotype(Configuration conf, double constantValue) throws InvalidConfigurationException {
		Genotype genotype = Genotype.randomInitialGenotype(conf);
		for (int i=0;i<conf.getPopulationSize();i++) {
			IChromosome chr = genotype.getPopulation().getChromosome(i);
			//modify this chromosome
			for (int g=0;g<chr.getGenes().length;g++) {
				chr.getGene(g).setAllele(constantValue);
			}
		}
		return genotype;
	}
}
