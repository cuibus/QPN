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
import GAUtils.FloatProgressiveTransformOperator;
import GAUtils.GAUtils;
import QuantumUtils.PrintUtils;

public class QuantumSphereWalk {
	// initial: rCoin = randomCoinFromPaper
	// cromozom: contine teta0,teta1 pt gamma0, si teta2,teta3 pt U
	// gamma0 = A(teta0,teta1) * rCoin
	// U = U0 * A(teta2,teta3), unde U0=rCoin
	// gamma(t) = gamma(t-1) * U
	private static double[][] bestChrsSoFar = new double[][] {
		/*{4.820254478251766, 3.2330804041915577, 5.582254686781663, 2.4418253266389307 },
		{4.777817727628669, 3.222130648762466, 5.563257259302809, 2.4193164647863483},
		{4.777817913985048, 3.222941376669519, 5.552444591100617, 2.4085314681958394}, // prob 0.7500007748603821
		{4.716225696189905, 3.1598469920093475, 5.500181980755979, 3.924457089880656}, // prob 0.9979589581489563
		{1.5956064231199028, 1.5477396647067376, 1.5631732184629108, 0.02732166016872828}, // 0.98 step 11
		*/
		//{1.5062803192412026,	1.5158212431679399,	6.277452227997539,	1.575274491731558}
		//{1.970711,	0.172057,	5.517499,	3.816113},
		//{5.105799,	2.803489,	4.184666,	5.264229},
		//{0.098936,	0.117976,	5.426092,	3.997787},
		//{1.604711,	0.136218,	5.446864,	3.971793},
		//{1.622495,	1.675234,	0.00887,	1.554262},
		//{4.718726384,	1.826285065,	0.868891778,	5.209560892},
		//{6.133166669,	1.175452226,	4.349667865,	5.18826957},
		//{6.133166669,	1.142914041,	4.24701102,	5.18826957},
		//{6.133166669,	1.456657838,	0.868891778,	2.26962529},
		//{6.133166669,	1.456657838,	0.868891778,	2.25454962},
		{6.231307762,	1.515612706,	0.855429173,	2.266428197}

	};

	private static int nrGenes = 4;
	private static final int MAX_ALLOWED_EVOLUTIONS =1;
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
		conf.setPopulationSize(1);
		Genotype population = Genotype.randomInitialGenotype(conf);
		GAUtils.addChrToPopulation(population, bestChrsSoFar);
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			//population.evolve();
			IChromosome bestChrSoFar = population.getFittestChromosome();
			String chrOneLine = ff.asStringOneLine(bestChrSoFar);
			//String trajectory = UtilsPrint.toStringTrajectory(ff.Mapping1(bestChrSoFar));
			/*System.out.println(i+". Fitness: " + bestChrSoFar.getFitnessValue() 
			+ ", chr: " + UtilsPrint.toString(bestChrSoFar)
			+ ", probTarget: " + ff.getTargetMaxProbability(bestChrSoFar)
			+ ", probTrap: " + ff.getTrapMaxProbability(bestChrSoFar)
			+ ", step: " + ff.getStepMaxProb(bestChrSoFar)
			+ ", indiv: " + chrOneLine
			+ ", A "+ UtilsPrint.toStringOneLine(ff.MappingGamma0(bestChrSoFar))
			+ ", U "+ UtilsPrint.toStringOneLine(ff.MappingU(bestChrSoFar)));
			*///System.out.println("Trajectory: " + trajectory);
			String psiString = ff.getPsiString(bestChrSoFar);
			System.out.println(i+". " + bestChrSoFar.getFitnessValue() 
			+ "\t" + PrintUtils.toString(bestChrSoFar)
			//+ "" + ff.getTargetMaxProbability(bestChrSoFar)
			//+ "\t " + ff.getTrapMaxProbability(bestChrSoFar));
			+ "" + psiString);
		}
	}
}

