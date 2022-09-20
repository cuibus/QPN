package HCQGA;

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
import GAUtils.FloatProgressiveTransformOperator;
import GAUtils.GAUtils;
import QuantumUtils.PrintUtils;

public class HCQGA {
	private static double[][] bestChrsSoFar = new double[][] {
		/*{4.820254478251766, 3.2330804041915577, 5.582254686781663, 2.4418253266389307 },
		{4.777817727628669, 3.222130648762466, 5.563257259302809, 2.4193164647863483},
		{4.777817913985048, 3.222941376669519, 5.552444591100617, 2.4085314681958394}, // prob 0.7500007748603821
		{4.716225696189905, 3.1598469920093475, 5.500181980755979, 3.924457089880656}, // prob 0.9979589581489563
		{1.5956064231199028, 1.5477396647067376, 1.5631732184629108, 0.02732166016872828}, // 0.98 step 11
		{1.50628,	1.515821,	6.277452,	1.575274},
		//{1.7821471403622233,	1.0699875403770012,	1.0093634586133353,	-0.6581077595594387},
		 * */
		//{4.327966744,	4.355129895,	4.691925679,	5.285935622},
		//{4.6922159,	0.926491918,	1.752213743,	4.315906295},
		//{5.785132141,	3.212869298,	3.069224527,	1.579819848},
		//{2.841270156,	4.073705799,	1.163401168,	2.024726583},
		//{1.533609729,	1.70351704,	4.715572125,	0.047902715},
		{1.533609729,	1.545308049,	4.715572125,	6.279499461},


	};
	private static int nrGenes = 4;
	private static final int MAX_ALLOWED_EVOLUTIONS = 400;
	public static void main(String[] args) throws InvalidConfigurationException {
		Configuration conf = new DefaultConfiguration();
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(true);
		Configuration.resetProperty(Configuration.S_GENETIC_OPERATORS);
		conf.addGeneticOperator(new FloatProgressiveTransformOperator());
		
		SphereWalkEvaluator ff = new SphereWalkEvaluator();
		conf.setFitnessFunction(ff);
		IChromosome sampleChromosome = new Chromosome(conf, new DoubleGene(conf,  0, 2*Math.PI), nrGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(500);
		Genotype population = Genotype.randomInitialGenotype(conf);
		GAUtils.addChrToPopulation(population, bestChrsSoFar);
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			population.evolve();
			IChromosome bestChrSoFar = population.getFittestChromosome();
			String chrOneLine = ff.asStringOneLine(bestChrSoFar);
			String psiString = ff.getPsiString(bestChrSoFar);
			//String trajectory = UtilsPrint.toStringTrajectory(ff.Mapping(bestChrSoFar));
			//System.out.println(i+". Fitness: " + bestChrSoFar.getFitnessValue() + ", psi: " + psiString + ", indiv: " + chrOneLine);
			//System.out.println("Trajectory: " + trajectory);
			
			System.out.println(i+". " + ff.evaluate(bestChrSoFar) 
			+ "\t" + PrintUtils.toString(bestChrSoFar)
			+ "" + psiString);
		}
	}
	
	
}
