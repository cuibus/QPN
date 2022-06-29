package QuantumUtils;

public class Qbit extends Qvec {
	public Qbit() {
		super(2); // one qbit has 2 coefs: c0*|0> + c1*|1>
	}
	
	public static Qbit GetRo() {
		Qbit qbit = new Qbit();
		qbit.re[0] = Utils.ro;
		qbit.im[0] = Utils.ro;
		return qbit;
	}
	
	public void checkHilbert(float tolerance) {
		float prob_qbit = this.getTotalProbability();
		if (Math.abs(prob_qbit - 1) > tolerance)
			throw new RuntimeException("Hilbert condition not met for qbit: " + prob_qbit);
	}
}
