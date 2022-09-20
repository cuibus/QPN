package GA_quantum_sphere_constantGamma;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.IntegerGene;

import GAUtils.PopulationInitializatorConstantGamma;
import GAUtils.RandomUnitaryTransformOperator;
import QuantumUtils.PrintUtils;

public class QuantumSphereWalk {
	// a gamma matrix, constant in time and equal for each cell
	// the gamma matrix is calculated from real&imaginary part of the coefficients of the 2 qbits
	private static int nrGenes = 8; // 2 qbits = 4 complex numbers = 8 floats
	private static final int MAX_ALLOWED_EVOLUTIONS = 800;
	public static void main(String[] args) throws InvalidConfigurationException {
		Configuration conf = new DefaultConfiguration();
		Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
		conf.setFitnessEvaluator(new DeltaFitnessEvaluator());		// low value for fitness = better
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(true);
		Configuration.resetProperty(Configuration.S_GENETIC_OPERATORS);
		conf.addGeneticOperator(new RandomUnitaryTransformOperator());
		
		SphereWalkEvaluator ff = new SphereWalkEvaluator();
		conf.setFitnessFunction(ff);
		IChromosome sampleChromosome = new Chromosome(conf, new DoubleGene(conf, 0, 3), nrGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(500);
		Genotype population = PopulationInitializatorConstantGamma.InitialGenotype(conf, 0.5d);
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			population.evolve();
			IChromosome bestChrSoFar = population.getFittestChromosome();
			String chrOneLine = ff.asStringOneLine(bestChrSoFar);
			String trajectory = PrintUtils.toStringTrajectory(ff.Mapping1(bestChrSoFar));
			System.out.println(i+". Fitness: " + bestChrSoFar.getFitnessValue() + ", indiv: " + chrOneLine);
			System.out.println("Trajectory: " + trajectory);
		}
	}
	
	
}
