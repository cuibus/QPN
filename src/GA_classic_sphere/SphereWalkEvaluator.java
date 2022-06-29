package GA_classic_sphere;

import java.util.ArrayList;
import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

import QuantumUtils.Point;
import Tests.TestData;

public class SphereWalkEvaluator extends FitnessFunction {
	private final double POSITIVE_BIAS = 0;
	private final double BIAS_PER_STEP = 20;
	
	public double evaluate(IChromosome chr) {
		//int[][] individual = mapping(chr);
		int[] individual = mapping(chr);
		List<Point> trajectory = simulate(individual);
		double penalization = 0;
		//boolean reachedOneTarget = false;
		for (Point p : trajectory) {
			if (TestData.targetPoints.contains(p)) {
				//performance += 20;
				//reachedOneTarget = true;
			}
			else if (TestData.trapPoints.contains(p))
				penalization += 20; //reachedOneTarget ? 0 : -20;
			else penalization += 5;
			
			//performance += BIAS_PER_STEP;
		}
		return penalization + POSITIVE_BIAS;
	}
	
	public String toStringTrajectory(IChromosome chr) {
		//int[][] individual = mapping(chr);
		int[] individual = mapping(chr);
		List<Point> trajectory = simulate(individual);
		StringBuilder sb = new StringBuilder("");
		sb.append("[");
		for (Point p : trajectory)
			sb.append(p.x+",");
		sb.append("],[");
		for (Point p : trajectory)
			sb.append(p.y+",");
		sb.append("]");
		
		return sb.toString();
	}
	
	private List<Point> simulate(int[] orientationBiases){
		int orientation = TestData.startOrientations[0];
		Point position = new Point(TestData.startPoints.get(0).x, TestData.startPoints.get(0).y);
		List<Point> result = new ArrayList<Point>();
		result.add(position.clone());
		for (int i=0;i<TestData.timeHorizon;i++) {
			//orientation = (orientation + orientationBiases[position.x][position.y]) % 4;
			orientation = (orientation + orientationBiases[i]) % 4;
			switch (orientation) {
				case 0: position.x = limit0_7(position.x+1); break;//right
				case 1: position.y = limit0_7(position.y+1); break;//up
				case 2: position.x = limit0_7(position.x-1); break;//left
				case 3: position.y = limit0_7(position.y-1); break;//down
			}
			result.add(position.clone());
			if (TestData.targetPoints.contains(position))
				return result;
			
		}
		return result;
	}
	private int limit0_7(int value) {
		return (value + 8) % 8;
	}
	
	/*private int[][] mapping(IChromosome chr){
		int[][] orientationBiases = new int[8][8];
		for (int i=0;i<8;i++) 
			for (int j=0;j<8;j++)
				orientationBiases[i][j] = ((IntegerGene)chr.getGene(i*8+j)).intValue();
		return orientationBiases;
	}
	*/
	private int[] mapping(IChromosome chr){
		int[] orientationBiases = new int[TestData.timeHorizon];
		for (int i=0;i<TestData.timeHorizon;i++) 
			orientationBiases[i] = ((IntegerGene)chr.getGene(i)).intValue();
		return orientationBiases;
	}
	
}
