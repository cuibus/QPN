package GA_quantum_sphere_constantGamma_theta;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;

import GAUtils.RandomUnitaryTransformOperator;
import GAUtils.FloatProgressiveTransformOperator;
import GAUtils.GAUtils;
import QuantumUtils.PrintUtils;

public class QuantumSphereWalk {
	// initial: rCoin = randomCoinFromPaper
	// cromozom: contine teta0,teta1 pt gamma
	// gamma = A(teta0,teta1) * rCoin
	private static double[][] bestChrsSoFar = new double[][] {
		{0.3880381635186323, 1.1160741122531173}
	};
	
	private static int nrGenes = 2;
	private static final int MAX_ALLOWED_EVOLUTIONS = 400;
	public static void main(String[] args) throws InvalidConfigurationException {
		Configuration conf = new DefaultConfiguration();
		Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
		conf.setFitnessEvaluator(new DeltaFitnessEvaluator());// low value for fitness = better
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(true);
		//Configuration.resetProperty(Configuration.S_GENETIC_OPERATORS);
		conf.addGeneticOperator(new FloatProgressiveTransformOperator());
		
		SphereWalkEvaluator ff= new SphereWalkEvaluator();
		conf.setFitnessFunction(ff);
		IChromosome sampleChromosome = new Chromosome(conf, new DoubleGene(conf, 0, 2*Math.PI), nrGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(500);
		Genotype population = Genotype.randomInitialGenotype(conf);
		GAUtils.addChrToPopulation(population, bestChrsSoFar);
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			population.evolve();
			IChromosome bestChrSoFar = population.getFittestChromosome();
			String chrOneLine = ff.asStringOneLine(bestChrSoFar);
			//String trajectory = UtilsPrint.toStringTrajectory(ff.Mapping1(bestChrSoFar));
			System.out.println(i+". Fitness: " + bestChrSoFar.getFitnessValue() 
					+ ", chr: " + PrintUtils.toString(bestChrSoFar)
					+ ", indiv: " + chrOneLine);
			//System.out.println("Trajectory: " + trajectory);
		}
	}
}
