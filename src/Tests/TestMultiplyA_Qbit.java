package Tests;

import QuantumUtils.MatrixUtils;
import QuantumUtils.Qbit;
import QuantumUtils.PrintUtils;
import QuantumUtils.QbitUtils;

public class TestMultiplyA_Qbit {
	
	public static void multiplyAOneQbit() {
		float[] oneQbit = TestData.oneQbitRo;
		
		for (int step = 0; step<100;step++) {
			System.out.println(step + ". Before multiply: " + PrintUtils.toString(oneQbit));
			checkHilbertOneQbit(oneQbit, 0.01f);
			
			float theta = 0.01f; //TestData.getRandomTheta();
			System.out.println(step + ". Theta: "+ theta);
			oneQbit = QbitUtils.multiplyA(oneQbit, new float[] { theta });
			
			System.out.println(step + ". After multiply: " + PrintUtils.toString(oneQbit));
			checkHilbertOneQbit(oneQbit, 0.01f);
		}
	}
	
	public static void rotateOneQbit() {
		Qbit qbit = Qbit.Ket0();
		
		for (int step = 0; step<200;step++) {
			System.out.println(step + ". Before multiply: " + qbit.toString());
			qbit.checkHilbert(1f);
			
			float theta = 0.1f; //TestData.getRandomTheta();
			System.out.println(step + ".Rotate"+ qbit.getAngle() +"with theta: "+ theta);
			qbit = qbit.rotate(MatrixUtils.getA(theta));
			
			System.out.println(step + ". After multiply: " + qbit.toString());
			qbit.checkHilbert(1f);
		}
	}
	
	public static void multiplyATwoQbits() {
		//float[] twoQbits = TestData.twoQbitsRo0;
		float[] twoQbits = TestData.twoQbitsHalf;
		for (int step = 0; step<100;step++) {
			System.out.println(step + ". Before multiply: " + PrintUtils.toString(twoQbits));
			checkHilbertTwoQbits(twoQbits, 0.01f);
			
			float theta1 = TestData.getRandomTheta();
			float theta2 = TestData.getRandomTheta();
			System.out.println(step + ". Theta1: "+ theta1 + ", theta2: "+ theta2);
			twoQbits = QbitUtils.multiplyA(twoQbits, new float[] { theta1, theta2 });
			
			System.out.println(step + ". After multiply: " + PrintUtils.toString(twoQbits));
			checkHilbertTwoQbits(twoQbits, 0.01f);
		}
	}
	public static void main(String[] args) {
		//multiplyAOneQbit();
		//multiplyATwoQbits();
		rotateOneQbit();
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
