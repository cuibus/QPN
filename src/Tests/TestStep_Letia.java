package Tests;

import QuantumUtils.Point;
import QuantumUtils.Qpoz;
import QuantumUtils.Utils;
import QuantumUtils.PrintUtils;

public class TestStep_Letia {
	public static float ro = Utils.ro;
	public static float [][] imat2 = new float[][] { {1, 0}, {0, 1}};
	public static float [][] imat4 = new float[][] { {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
	public static float [][] had4 = new float[][] {{ro, ro, 0, 0}, {ro, -ro, 0, 0}, {0, 0, ro, ro}, {0, 0, ro, -ro}};
	
	public static float[][] multiply(float[][] mat1, float[][] mat2){
		float[][] res = new float[4][4];
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++) 
				for (int m=0;m<4;m++)
					res[i][j] += mat1[i][m]*mat2[m][j];
		return res;	
	}
	public static float[][] getA(float theta0, float theta1){
		return new float[][] {
			{(float)(Math.cos(theta0)), -(float)(Math.sin(theta0)), 0, 0},
			{(float)(Math.sin(theta0)), (float)(Math.cos(theta0)), 0, 0},
			{0, 0, (float)(Math.cos(theta1)), -(float)(Math.sin(theta1))},
			{0, 0, (float)(Math.sin(theta1)), (float)(Math.cos(theta1))},
		};
	}
	
	public static void main(String[] args) {
		float theta0 = TestData.getRandomTheta();
		float theta1 = TestData.getRandomTheta();
		float[][] gamma = multiply(getA(theta0, theta1),  TestData.gammaForCoinFromPaper);
		
		System.out.println("gamma: \n" + PrintUtils.toString(gamma));
		int orientation = 0;
		Point startPoint = new Point(1,1);
		Qpoz[] p = Utils.getQpozInitial(orientation, startPoint.x, startPoint.y);
		System.out.println("initial: ");
		for (int i=0;i<4;i++)
			System.out.println("p["+i+"]: " + p[i].toString());
		
		for (int step=0;step<50;step++) {
			System.out.println("step "+ step);
			Utils.step(p, gamma, true);
		}
	}
}
