package QuantumUtils;

import org.jgap.IChromosome;
import org.jgap.impl.DoubleGene;

import Tests.TestData;

public class UtilsPrint {
	public static String toString(float[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<matrix.length;i++) {
			for (int j=0;j<matrix[0].length;j++)
				sb.append(String.format("%.4f", matrix[i][j]) + " ");
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static String toStringOneLine(float[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<4;i++) {
			for (int j=0;j<4;j++)
				sb.append(String.format("%.4f", matrix[i][j]) + " ");
			sb.append("|");
		}
		return sb.toString();
	}
	
	public static String toString(float[] vec) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<vec.length;i++) 
			sb.append(String.format("%.4f", vec[i]) + " ");

		return sb.toString();
	}
	
	public static String toString(IChromosome chr) {
		StringBuilder sb = new StringBuilder("");
		for (int i=0;i<chr.getGenes().length;i++) {
			sb.append(((DoubleGene)(chr.getGene(i))).doubleValue() + "\t");
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

	
	public static void printQbits(float[] qbits) {
		System.out.println("qbits:");
		for (int i=0;i<qbits.length;i=i+4) {
			System.out.print("("+qbits[i]+"+i*"+qbits[i+1]+")*|0> + ("+qbits[i+2]+"+i*"+qbits[i+3]+")*|1> ; ");
		}
		System.out.println();
	}
	
	public static void printChr(IChromosome chr) {
		System.out.println("chr:");
		for (int i=0;i<chr.getGenes().length;i++) {
			System.out.print(chr.getGene(i).getAllele() + " ");
		}
		System.out.println();
	}
	
	public static String getPsiFormatted(Qpoz[] p) {
			return String.format("%.4f", WalkerUtils.psi_n(p)) + " "
				 + String.format("%.4f", WalkerUtils.psi_g(p)) + " "
			     + String.format("%.4f", WalkerUtils.psi_p(p)) + " "
				 + String.format("%.4f", WalkerUtils.psi_gp(p));
	}
	
}
