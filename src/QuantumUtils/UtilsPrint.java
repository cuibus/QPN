package QuantumUtils;

import org.jgap.IChromosome;
import org.jgap.impl.DoubleGene;

import Tests.TestData;

public class UtilsPrint {
	public static String toString(float[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++)
				sb.append(matrix[i][j] + " ");
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static String toStringOneLine(float[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++)
				sb.append(matrix[i][j] + " ");
			sb.append("|");
		}
		return sb.toString();
	}
	
	public static String toString(IChromosome chr) {
		StringBuilder sb = new StringBuilder("");
		for (int i=0;i<chr.getGenes().length;i++) {
			sb.append(((DoubleGene)(chr.getGene(i))).doubleValue() + " ");
		}
		return sb.toString();
	}
	
	public static String toStringTrajectory(float[][] gamma) {
		StringBuilder result = new StringBuilder("qvec");
		int orientation = 0;
		Qpoz[] p = Utils.getQpozInitial(orientation, TestData.startPoints.get(0).x, TestData.startPoints.get(0).y);
		for (int step=0;step<TestData.timeHorizon;step++) {
			Utils.step(p, gamma, false);
			for (int orient=0;orient<4;orient++)
				result.append(p[orient].toString() + "|");
		}
		return result.toString();
	}

}
