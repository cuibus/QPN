package QuantumUtils;

public class UtilsQbit {
	public static float[] multiplyA(float[] qbit, float theta) {
		// qbit trebuie sa aiba 1qbit, adica 4 elemente: c0re,c0im,c1re,c1im
		double[][] A = new double[][] {
			{ Math.cos(theta) , -Math.sin(theta) },
			{ Math.sin(theta) , Math.cos(theta) }
		};
		float[] result = new float[4];
		result[0] = (float)(A[0][0]*qbit[0] + A[0][1]*qbit[2]);
		result[1] = (float)(A[0][0]*qbit[1] + A[0][1]*qbit[3]);
		result[2] = (float)(A[1][0]*qbit[0] + A[1][1]*qbit[2]);
		result[3] = (float)(A[1][0]*qbit[1] + A[1][1]*qbit[3]);
		return result;
	}

	public static float[] multiplyA1A2(float[] qbits, float theta1, float theta2) {
		// qbits trebuie sa aiba 2qbit, adica 8 elemente: c0,c1,c2,c3 (re si im), in ordine:
		// c0re, c0im, c1re, c1im, c2re, c2im, c3re, c3im, etc
		double[][] A1 = new double[][] {
			{ Math.cos(theta1) , -Math.sin(theta1) },
			{ Math.sin(theta1) , Math.cos(theta1) }
		};
		double[][] A2 = new double[][] {
			{ Math.cos(theta2) , -Math.sin(theta2) },
			{ Math.sin(theta2) , Math.cos(theta2) }
		};
		float[] result = new float[4];
		result[0] = (float)(A1[0][0]*qbits[0] + A1[0][1]*qbits[2]);
		result[1] = (float)(A1[0][0]*qbits[1] + A1[0][1]*qbits[3]);
		result[2] = (float)(A1[1][0]*qbits[0] + A1[1][1]*qbits[2]);
		result[3] = (float)(A1[1][0]*qbits[1] + A1[1][1]*qbits[3]);
		
		result[4] = (float)(A2[0][0]*qbits[4] + A2[0][1]*qbits[6]);
		result[5] = (float)(A2[0][0]*qbits[5] + A2[0][1]*qbits[7]);
		result[6] = (float)(A2[1][0]*qbits[4] + A2[1][1]*qbits[6]);
		result[7] = (float)(A2[1][0]*qbits[5] + A2[1][1]*qbits[7]);
		return result;
	}
	
	public static float[] multiplyA(float[] qbits, float[] theta) {
		// qbits trebuie sa aiba n qbits, adica 4*n elemente: c0,c1,c2,c3 (re si im), in ordine:
		// c0re, c0im, c1re, c1im, c2re, c2im, c3re, c3im, etc
		// => avem nevoie de n argumente theta
		if (theta.length * 4 != qbits.length) throw new RuntimeException("unmatching dimensions: qbits:" + qbits.length + ", theta:"+theta.length);

		float[] result = new float[qbits.length];
		for (int i=0;i<theta.length;i++) {
			double[][] A = new double[][] {
				{ Math.cos(theta[i]) , -Math.sin(theta[i]) },
				{ Math.sin(theta[i]) , Math.cos(theta[i]) }
			};
		
			result[i*4+0] = (float)(A[0][0]*qbits[i*4+0] + A[0][1]*qbits[i*4+2]);
			result[i*4+1] = (float)(A[0][0]*qbits[i*4+1] + A[0][1]*qbits[i*4+3]);
			result[i*4+2] = (float)(A[1][0]*qbits[i*4+0] + A[1][1]*qbits[i*4+2]);
			result[i*4+3] = (float)(A[1][0]*qbits[i*4+1] + A[1][1]*qbits[i*4+3]);
		}
		return result;
	}
	
	// Qbit si Qbit2 sunt numai pt verificare
	public static Qbit multiplyA(Qbit qbit, float theta) {
		// qbit trebuie sa aiba 2 elemente: c0,c1 (numere complexe)
		double[][] A = new double[][] {
			{ Math.cos(theta) , -Math.sin(theta) },
			{ Math.sin(theta) , Math.cos(theta) }
		};
		qbit.re[0] = (float)(A[0][0]*qbit.re[0] + A[0][1]*qbit.re[1]);
		qbit.im[0] = (float)(A[0][0]*qbit.im[0] + A[0][1]*qbit.im[1]);
		qbit.re[1] = (float)(A[1][0]*qbit.re[0] + A[1][1]*qbit.re[1]);
		qbit.im[1] = (float)(A[1][0]*qbit.im[0] + A[1][1]*qbit.im[1]);
		return qbit;
	}
	
	public static Qbit2 multiplyA1A2(Qbit2 qbit, float theta1, float theta2) {
		// qbit trebuie sa aiba 4 elemente: c0,c1, c2,c3 (numere complexe)
		double[][] A1 = new double[][] {
			{ Math.cos(theta1) , -Math.sin(theta1) },
			{ Math.sin(theta1) , Math.cos(theta1) }
		};
		double[][] A2 = new double[][] {
			{ Math.cos(theta2) , -Math.sin(theta2) },
			{ Math.sin(theta2) , Math.cos(theta2) }
		};
		qbit.re[0] = (float)(A1[0][0]*qbit.re[0] + A1[0][1]*qbit.re[1]);
		qbit.im[0] = (float)(A1[0][0]*qbit.im[0] + A1[0][1]*qbit.im[1]);
		qbit.re[1] = (float)(A1[1][0]*qbit.re[0] + A1[1][1]*qbit.re[1]);
		qbit.im[1] = (float)(A1[1][0]*qbit.im[0] + A1[1][1]*qbit.im[1]);
		
		qbit.re[2] = (float)(A2[0][0]*qbit.re[2] + A2[0][1]*qbit.re[3]);
		qbit.im[2] = (float)(A2[0][0]*qbit.im[2] + A2[0][1]*qbit.im[3]);
		qbit.re[3] = (float)(A2[1][0]*qbit.re[2] + A2[1][1]*qbit.re[3]);
		qbit.im[3] = (float)(A2[1][0]*qbit.im[2] + A2[1][1]*qbit.im[3]);
		return qbit;
	}
}
