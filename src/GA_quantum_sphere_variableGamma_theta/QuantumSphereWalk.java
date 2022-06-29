package GA_quantum_sphere_variableGamma_theta;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;

import GAUtils.RandomUnitaryTransformOperator;
import GAUtils.UtilsGA;
import GAUtils.QuantumInspiredGAForFloats.FloatProgressiveTransformOperator;
import QuantumUtils.UtilsPrint;

public class QuantumSphereWalk {
	// initial: rCoin = randomCoinFromPaper
	// cromozom: contine teta0,teta1 pt gamma0, si teta2,teta3 pt U
	// gamma0 = A(teta0,teta1) * rCoin
	// U = U0 * A(teta2,teta3), unde U0=rCoin
	// gamma(t) = gamma(t-1) * U
	private static double[][] bestChrsSoFar = new double[][] {
		{4.820254478251766, 3.2330804041915577, 5.582254686781663, 2.4418253266389307 },
		{4.777817727628669, 3.222130648762466, 5.563257259302809, 2.4193164647863483},
		{4.777817913985048, 3.222941376669519, 5.552444591100617, 2.4085314681958394}, // prob 0.7500007748603821
		{4.716225696189905, 3.1598469920093475, 5.500181980755979, 3.924457089880656}, // prob 0.9979589581489563
		{1.5956064231199028, 1.5477396647067376, 1.5631732184629108, 0.02732166016872828}, // 0.98 step 11
	};

	private static int nrGenes = 4;
	private static final int MAX_ALLOWED_EVOLUTIONS = 400;
	public static void main(String[] args) throws InvalidConfigurationException {
		Configuration conf = new DefaultConfiguration();
		Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
		conf.setFitnessEvaluator(new DeltaFitnessEvaluator()); // low value for fitness = better
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(true);
		conf.addGeneticOperator(new FloatProgressiveTransformOperator());

		SphereWalkEvaluator ff= new SphereWalkEvaluator();
		conf.setFitnessFunction(ff);
		IChromosome sampleChromosome = new Chromosome(conf, new DoubleGene(conf, 0, 2*Math.PI), nrGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(500);
		Genotype population = Genotype.randomInitialGenotype(conf);
		UtilsGA.addChrToPopulation(population, bestChrsSoFar);
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			population.evolve();
			IChromosome bestChrSoFar = population.getFittestChromosome();
			String chrOneLine = ff.asStringOneLine(bestChrSoFar);
			//String trajectory = UtilsPrint.toStringTrajectory(ff.Mapping1(bestChrSoFar));
			System.out.println(i+". Fitness: " + bestChrSoFar.getFitnessValue() 
			+ ", chr: " + UtilsPrint.toString(bestChrSoFar)
			+ ", prob: " + ff.getTargetMaxProbability(bestChrSoFar)
			+ ", step: " + ff.getStepMaxProb(bestChrSoFar)
			+ ", indiv: " + chrOneLine);
			//System.out.println("Trajectory: " + trajectory);
		}
	}
}

