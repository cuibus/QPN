package GAUtils;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.jgap.GeneticOperator;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.impl.DoubleGene;

import QuantumUtils.UtilsQbit;


public class RandomUnitaryTransformOperator implements GeneticOperator{

	public void operate(Population a_population, List a_candidateChromosomes) {
		if (!a_population.isSorted())
			throw new RuntimeException("not sorted");
		
        for ( int i = 0; i < a_population.size(); i++ ){
        	IChromosome chr = (IChromosome)a_population.getChromosome(i).clone();
        	// TODO: mutate only if fitness is big enough
        		
        	float[] chrv = new float[chr.getGenes().length];
        	for (int j=0;j<chrv.length;j++) {
        		chrv[j] = (float)((DoubleGene)chr.getGene(j)).doubleValue();
        	}
        	float[] thetas = new float[chrv.length / 4];
        	for (int t=0;t<thetas.length;t++) {
        		thetas[t] = (float)(Math.random() * 2 * Math.PI);
        	}
        	chrv = UtilsQbit.multiplyA(chrv, thetas);
        	for (int j=0;j<chrv.length;j++) {
        		((DoubleGene)chr.getGene(j)).setAllele(chrv[j]);
        	}
        	
        	a_candidateChromosomes.add( chr );
        }
	}
}
