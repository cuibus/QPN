package QuantumUtils;

public class Qbit2 extends Qvec {
	public Qbit2() {
		super(4); // two qbits have 4 coefs: c0*|0> + c1*|1>, c2*|0> + c3*|1>
	}
	
	public static Qbit2 GetRo() {
		Qbit2 qbit = new Qbit2();
		qbit.re[0] = Utils.ro;
		qbit.im[0] = Utils.ro;
		qbit.re[2] = Utils.ro;
		qbit.im[2] = Utils.ro;
		return qbit;
	}
	
	public void checkHilbert(float tolerance) {
		float prob_qbit1 = this.getProbabilityForPosition(0) + this.getProbabilityForPosition(1);
		if (Math.abs(prob_qbit1 - 1) > tolerance)
			throw new RuntimeException("Hilbert condition not met for qbit1: " + prob_qbit1);
		
		float prob_qbit2 = this.getProbabilityForPosition(2) + this.getProbabilityForPosition(3);
		if (Math.abs(prob_qbit2 - 1) > tolerance)
			throw new RuntimeException("Hilbert condition not met for qbit2: " + prob_qbit2);
	}
}
