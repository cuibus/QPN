package GA_classic_sphere;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class RealSphereWalk{
	// cazul necuantic: avem o matrice de 8x8, in fiecare celula exista "o prisma" care roteste directia X si directia Y
	// delta rotatie = un nr intre [0,3] => rotatie cu 0, 90, 180, 270 grade
	// => 2 gene pt fiecare celula
	private static int nrGenes = 16;
	private static final int MAX_ALLOWED_EVOLUTIONS = 800;
	public static void main(String[] args) throws InvalidConfigurationException {
		Configuration conf = new DefaultConfiguration();
		Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
		// low value for fitness = better
		conf.setFitnessEvaluator(new DeltaFitnessEvaluator());
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(true);
		SphereWalkEvaluator ff = new SphereWalkEvaluator();
		conf.setFitnessFunction(ff);
		IChromosome sampleChromosome = new Chromosome(conf, new IntegerGene(conf, 0, 3), nrGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(500);
		Genotype population = Genotype.randomInitialGenotype(conf);
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			population.evolve();
			IChromosome bestChrSoFar = population.getFittestChromosome();
			String chrOneLine = asStringOneLine(bestChrSoFar);
			String trajectory = ff.toStringTrajectory(bestChrSoFar);
			System.out.println(i+". Fitness: " + bestChrSoFar.getFitnessValue() + 
					", trajectory: " + trajectory +
					", indiv: " + chrOneLine);
		}
	}
	
	public static String asStringOneLine(IChromosome chr) {
		StringBuilder sb = new StringBuilder("");
		for (int i=0;i<nrGenes;i++) {
			//if (i%8 == 0) sb.append("|");
			sb.append(((IntegerGene)chr.getGene(i)).intValue());
			sb.append(" ");
		}
		return sb.toString();
	}
}
