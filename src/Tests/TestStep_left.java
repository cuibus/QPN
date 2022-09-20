package Tests;

import java.util.List;

import QuantumUtils.Point;
import QuantumUtils.Qpoz;
import QuantumUtils.Utils;
import QuantumUtils.PrintUtils;
import QuantumUtils.WalkerUtils;

public class TestStep_left {
	
	public static void main(String[] args) {
	
		float[][] gamma = TestData.shiftLeft4;

		Point startPoint = TestData.startPoints.get(0);
		int startOrientation = TestData.startOrientations[0];
		Qpoz[] p = Utils.getQpozInitial(startOrientation, startPoint.x, startPoint.y);
		
		System.out.println("psi step000: " + PrintUtils.getPsiFormatted(p));
		System.out.println(PrintUtils.toString(WalkerUtils.getProbForAllPositions(p)));
		
		for (int step=0;step<TestData.timeHorizon;step++) {
			System.out.println("\nstep "+ step);
			Utils.step(p, gamma, true);
			double targetProbability = getPointsProbability(p, TestData.targetPoints);
			double trapProbability = getPointsProbability(p, TestData.trapPoints);
			System.out.println(
					"target prob: " + String.format("%.4f", targetProbability) + ", " +
							"trap prob: " + String.format("%.4f", trapProbability));
			System.out.println("psi step "+step+": " + PrintUtils.getPsiFormatted(p));
			System.out.println(PrintUtils.toString(WalkerUtils.getProbForAllPositions(p)));
		}
	}
	
	private static double getPointsProbability(Qpoz[] p, List<Point> points) {
		float probability = 0;
		for (Point targetPoint: points) {
			for (int orient=0;orient<3;orient++)
				probability += p[orient].getProbabilityForPosition(targetPoint.x, targetPoint.y);
		}
		return probability;
	}
}
