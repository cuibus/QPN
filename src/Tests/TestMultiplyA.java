package Tests;

import QuantumUtils.Qbit;
import QuantumUtils.UtilsQbit;

public class TestMultiplyA {
	public static String toStr(float[] vector) {
		String s = "";
		for (int i=0;i<vector.length;i++)
			s += vector[i] + " ";
		return s;
	}
	
	public static void multiplyAOneQbit() {
		float[] oneQbit = TestData.oneQbitRo;
		
		for (int step = 0; step<100;step++) {
			System.out.println(step + ". Before multiply: " + toStr(oneQbit));
			checkHilbertOneQbit(oneQbit, 0.01f);
			
			float theta = 4.889314f; //TestData.getRandomTheta();
			System.out.println(step + ". Theta: "+ theta);
			oneQbit = UtilsQbit.multiplyA(oneQbit, new float[] { theta });
			
			System.out.println(step + ". After multiply: " + toStr(oneQbit));
			checkHilbertOneQbit(oneQbit, 0.01f);
		}
	}
	
	public static void multiplyATwoQbits() {
		//float[] twoQbits = TestData.twoQbitsRo0;
		float[] twoQbits = TestData.twoQbitsHalf;
		for (int step = 0; step<100;step++) {
			System.out.println(step + ". Before multiply: " + toStr(twoQbits));
			checkHilbertTwoQbits(twoQbits, 0.01f);
			
			float theta1 = TestData.getRandomTheta();
			float theta2 = TestData.getRandomTheta();
			System.out.println(step + ". Theta1: "+ theta1 + ", theta2: "+ theta2);
			twoQbits = UtilsQbit.multiplyA(twoQbits, new float[] { theta1, theta2 });
			
			System.out.println(step + ". After multiply: " + toStr(twoQbits));
			checkHilbertTwoQbits(twoQbits, 0.01f);
		}
	}
	public static void main(String[] args) {
		//multiplyAOneQbit();
		multiplyATwoQbits();
	}
	
	public static void checkHilbertOneQbit(float[] oneqbit, float tolerance) {
		float prob = oneqbit[0]*oneqbit[0] + oneqbit[1]*oneqbit[1] + oneqbit[2]*oneqbit[2] + oneqbit[3]*oneqbit[3];
		if (Math.abs(prob - 1) > tolerance) throw new RuntimeException("Hilbert condition not fulfilled: prob="+prob);
	}
	
	public static void checkHilbertTwoQbits(float[] twoqbit, float tolerance) {
		float prob1 = twoqbit[0]*twoqbit[0] + twoqbit[1]*twoqbit[1] + twoqbit[2]*twoqbit[2] + twoqbit[3]*twoqbit[3];
		if (Math.abs(prob1 - 1) > tolerance) throw new RuntimeException("Hilbert condition not fulfilled for first qbit: prob="+prob1);
		
		float prob2 = twoqbit[4]*twoqbit[4] + twoqbit[5]*twoqbit[5] + twoqbit[6]*twoqbit[6] + twoqbit[7]*twoqbit[7];
		if (Math.abs(prob2 - 1) > tolerance) throw new RuntimeException("Hilbert condition not fulfilled for second qbit: prob="+prob2);
	}
	
}
