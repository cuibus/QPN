package QuantumUtils;

import Tests.TestData;

public class MatrixUtils {

	// adds several matrices
	public static float[][] add(float[][] ... mat ){
		int nrMatrices = mat.length;
		float[][] result = new float[mat[0].length][mat[0][0].length];
		for (int i=0;i<mat[0].length;i++)
			for (int j=0;j<mat[0][0].length;j++) 
				for (int m=0;m<nrMatrices;m++)
					result[i][j] += mat[m][i][j];
		return result;	
	}

	//multiplies two matrices
	public static float[][] multiply(float[][] mat1, float[][] mat2){
		float[][] res = new float[mat1.length][mat2[0].length];
		for (int i=0;i<mat1.length;i++)
			for (int j=0;j<mat1[0].length;j++) 
				for (int m=0;m<mat2[0].length;m++)
					res[i][j] += mat1[i][m]*mat2[m][j];
		return res;	
	}

	//multiplies a matrix with a vector
	public static float[] multiply(float[][] mat, float[] vec){
		float[] res = new float[vec.length];
		for (int i=0;i<mat.length;i++)
			for (int j=0;j<mat[0].length;j++) 
				res[i] += mat[i][j]*vec[j];
		return res;	
	}

	//multiplies a matrix with a scalar
	public static float[][] multiply(float[][] mat, float m){
		float[][] res = new float[mat.length][mat[0].length];
		for (int i=0;i<mat.length;i++)
			for (int j=0;j<mat[0].length;j++) 
				res[i][j] = m*mat[i][j];
		return res;	
	}

	private static float cos(float theta) { return (float)Math.cos(theta);}
	private static float sin(float theta) { return (float)Math.sin(theta);}

	//gets the matrix 2x2 denoted by A(theta)
	public static float[][] getA(float theta){
		return new float[][] {
			{cos(theta), -sin(theta)},
			{sin(theta), cos(theta)}
		};
	}

	//gets the matrix 4x4 denoted by A(theta0,theta1)
	public static float[][] getA(float theta0, float theta1){
		return new float[][] {
			{cos(theta0), -sin(theta0), 0, 0},
			{sin(theta0), cos(theta0), 0, 0},
			{0, 0, cos(theta1), -sin(theta1)},
			{0, 0, sin(theta1), cos(theta1)},
		};
	}

	//gets the matrix 4x4 denoted by A(theta0..3) in the paper (HCQGA), multiplied by psi_n,psi_g,psi_p,psi_gp (could be obtained by a kroneker product)
	public static float[][] getA(
			float theta0, float theta1, float theta2, float theta3,
			float psi_n, float psi_g, float psi_p, float psi_gp
			){
		return new float[][] {
			{psi_n*cos(theta0), -psi_n*sin(theta0), psi_g*cos(theta1), -psi_g*sin(theta1)},
			{psi_n*sin(theta0), psi_n*cos(theta0),  psi_g*sin(theta1), psi_g*cos(theta1)},
			{psi_p*cos(theta2), -psi_p*sin(theta2), psi_gp*cos(theta3), -psi_gp*sin(theta3)},
			{psi_p*sin(theta2), psi_p*cos(theta2),  psi_gp*sin(theta3), psi_gp*cos(theta3)},
		};
	}	
	
	public static float[][] getAForFitness(float psi_g, float psi_p){
    	double theta = Math.asin(psi_g - psi_p);
    	float a0 = TestData.ro * (float)(Math.cos(theta) - Math.sin(theta));
    	float a1 = TestData.ro * (float)(Math.cos(theta) + Math.sin(theta));
    	float[][] A = new float[][] {
    		{a0,  a1},
    		{a1, -a0},
    	};
    	return A;
    }

	public static float[][] getKronekerProduct(float[][] A, float[][] B){
		float[][] res = new float[A.length*B.length][A[0].length*B[0].length];
		for (int i=0;i<A.length;i++)
			for (int j=0;j<A[0].length;j++)
				for (int m=0;m<B.length;m++)
					for (int n=0;n<B[0].length;n++)
						res[i*B.length+m][j*B[0].length+n] = A[i][j]*B[m][n];
		return res;
	}

	public static float getDeterminant(float mat[][]) {
		if (mat.length!= mat[0].length) throw new RuntimeException("Cannot compute determinant for " + mat.length +"x" + mat[0].length + " matrix");
		return getDeterminant(mat, mat.length);
	}
	
	private static void getCofactor(float mat[][], float temp[][],int p, int q, int n){
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col];
                    if (j == n - 1) {j = 0;i++;}
                }
            }
        }
    }
 
    private static float getDeterminant(float mat[][], int n){
        float determinant = 0f;
        if (n == 1)
            return mat[0][0];
        float temp[][] = new float[mat.length][mat.length];
        float sign = 1f;
        for (int f = 0; f < n; f++) {
            getCofactor(mat, temp, 0, f, n);
            determinant += sign * mat[0][f] * getDeterminant(temp, n - 1);
            sign = -sign;
        }
 
        return determinant;
    }
    
    // use the main method to test stuff in this class
	public static void main(String[] args) {
		float[][] A = new float[][] {
			{1, 2, 5},
			{3, 4, 6}
		};
		float[][] B = new float[][] {
			{0, 5},
			{6, 7},
		};
		System.out.println(PrintUtils.toString(getKronekerProduct(A, B)));
	}
}
