package QuantumUtils;

public class Qbit extends Qvec {
	public Qbit() {
		super(2); // one qbit has 2 coefs: c0*|0> + c1*|1>
		this.re[0] = 1; // creates Ket0 by default
	}
	
	public static Qbit GetRo() {
		Qbit qbit = new Qbit();
		qbit.re[0] = Utils.ro;
		qbit.im[0] = Utils.ro;
		return qbit;
	}
	public static Qbit Ket0() {
		Qbit qbit = new Qbit();
		qbit.re[0] = 1;
		return qbit;
	}
	public static Qbit fromArray(float[] arr) {
		Qbit qbit = new Qbit();
		qbit.re[0] = arr[0];
		qbit.im[0] = arr[1];
		qbit.re[1] = arr[2];
		qbit.im[1] = arr[3];
		return qbit;
	}
	
	public void checkHilbert(float tolerance) {
		float prob_qbit = this.getTotalProbability();
		if (Math.abs(prob_qbit - 1) > tolerance)
			throw new RuntimeException("Hilbert condition not met for qbit: " + prob_qbit);
	}
	public Qbit rotate(float[][] A) {
		this.re[0] = A[0][0] * re[0] + A[0][1] * re[1];
		this.im[0] = A[0][0] * im[0] + A[0][1] * im[1];
		this.re[1] = A[1][0] * re[0] + A[1][1] * re[1];
		this.im[1] = A[1][0] * im[0] + A[1][1] * im[1];
		return this;		
	}
	public float getAngle() {
		return /*(float)Math.atan(
				(this.im[1]*this.im[1] + this.re[1]*this.re[1]) /
				(this.im[0]*this.im[0] + this.re[0]*this.re[0]));*/
				(float)Math.atan(this.re[1]/this.re[0]);
	}
}
