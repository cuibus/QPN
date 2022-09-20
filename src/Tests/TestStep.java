package Tests;

import QuantumUtils.Point;
import QuantumUtils.Qpoz;
import QuantumUtils.Utils;
import QuantumUtils.PrintUtils;

public class TestStep {
	public static void testSimulate(float[][] gamma, int orientation, int x, int y) {
		Qpoz[] p = Utils.getQpozInitial(orientation,x,y);
		
		System.out.println("Initialization:");
		for (int i=0;i<4;i++)
			System.out.println("p["+i+"]: " + p[i].toString());
		
		for (int step=0;step<10;step++) {
			Utils.step(p,gamma, false);
			System.out.println("step "+step+".");
			for (int i=0;i<4;i++)
				System.out.println("p["+i+"]: " + p[i].toString());
		}
	}
	
	public static void main(String[] args) {
		float[] twoQbits = TestData.twoQbitsRo0;
		float[] twoQbitsRe = new float[] {twoQbits[0], twoQbits[2], twoQbits[4], twoQbits[6]};
		float[] twoQbitsIm = new float[] {twoQbits[1], twoQbits[3], twoQbits[5], twoQbits[7]};
		float[][] gamma = TestData.gammaForCoinFromPaper; // Utils.getGamma(twoQbitsRe, twoQbitsIm, 0); // 
		PrintUtils.printQbits(twoQbits);
		System.out.println("Gamma: \n"+ PrintUtils.toString(gamma));
		int orientation = 0;
		Point startPoint = new Point(1,1);
		Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
		System.out.println("initial: ");
		for (int i=0;i<4;i++)
			System.out.println("p["+i+"]: " + p[i].toString());
		
		for (int step=0;step<100;step++) {
			System.out.println("step "+ step);
			Utils.step(p, gamma, true);
		}
	}
}
