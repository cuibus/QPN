package GAUtils;

import java.util.List;
import org.jgap.GeneticOperator;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.impl.DoubleGene;


public class FloatProgressiveTransformOperator implements GeneticOperator{

	// mutates all genes (double) in the chromosomes in the following way:
	// small change = high probability
	// big change = small probability
	public void operate(Population a_population, List a_candidateChromosomes) {
		int popsize = a_population.size();
        for ( int i = 0; i < popsize; i++ ){
        	IChromosome chr = (IChromosome)a_population.getChromosome(i).clone();
        	// mutate a gene with a value between 0 and 2*pi: small value = more probable, big value = less probable
        	int geneIndexToMutate = (int)(Math.random() * (chr.getGenes().length));
        	double newValue = getNewValue(((DoubleGene)(chr.getGene(geneIndexToMutate))).doubleValue());
        	chr.getGene(geneIndexToMutate).setAllele(newValue);
        	
        	a_candidateChromosomes.add( chr );
        }
	}
	
	public double getNewValue(double oldValue) {
		double randomPiPi = Math.pow(Math.random(), 6) * Math.PI;
		randomPiPi = Math.random() > 0.5 ? randomPiPi: -randomPiPi;
		//System.out.println("randompipi: " + randomPiPi);
    	double newValue = oldValue + randomPiPi;
    	return (newValue + 2*Math.PI ) % (2*Math.PI); 
	}
	
	/*
	// test method:
	public static void main(String[] args) {
		FloatProgressiveTransformOperator op = new FloatProgressiveTransformOperator();
		double value = Math.random() * 2 * Math.PI;
		for (int i=0;i<100;i++) {
			double newValue = op.getNewValue(value);
			double variation = Math.abs((value - newValue) / value);
			value = newValue;
			System.out.println(value + " variation: "+ variation);
		}
	}
	*/
}
