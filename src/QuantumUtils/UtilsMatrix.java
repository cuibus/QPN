package QuantumUtils;

public class UtilsMatrix {
	//multiplies two matrices
	public static float[][] multiply(float[][] mat1, float[][] mat2){
		float[][] res = new float[4][4];
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++) 
				for (int m=0;m<4;m++)
					res[i][j] += mat1[i][m]*mat2[m][j];
		return res;	
	}
	
	//gets the matrix denoted by A in the paper
	public static float[][] getA(float theta0, float theta1){
		return new float[][] {
			{(float)(Math.cos(theta0)), -(float)(Math.sin(theta0)), 0, 0},
			{(float)(Math.sin(theta0)), (float)(Math.cos(theta0)), 0, 0},
			{0, 0, (float)(Math.cos(theta1)), -(float)(Math.sin(theta1))},
			{0, 0, (float)(Math.sin(theta1)), (float)(Math.cos(theta1))},
		};
	}
}
