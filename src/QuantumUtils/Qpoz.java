package QuantumUtils;
//stores X and Y position by two Qvec vector of dm dimension
public class Qpoz {
	Qvec X, Y;
	int dm;
	public Qpoz(int dim) {
		dm=dim;
		X=new Qvec(dm);
		Y=new Qvec(dm);
	}
	public String toString(){
		return "[X=" + X.toString() + ",Y=" + Y.toString() + "]"; 
	}
	public void copyFrom(Qpoz qp) { //copy qvector qp in this 
		X.copyFrom(qp.X);
		Y.copyFrom(qp.Y);
	}
	
	public Qpoz multiply(float a, float b ){//multiply Qvec
		// multiply the re with a and im with b
		this.X.multiply(a, b);
		this.Y.multiply(a, b);
		return this;
	}
	
	public Qpoz add4(Qpoz qp1, Qpoz qp2, Qpoz qp3, Qpoz qp4) {
		//add 4 Qpoz vectors  
		X.addQvec(qp1.X, qp2.X);
		X.addQvec(X, qp3.X);
		X.addQvec(X, qp4.X);

		Y.addQvec(qp1.Y, qp2.Y);
		Y.addQvec(Y, qp3.Y);
		Y.addQvec(Y, qp4.Y);
		return this;
	}
	public float getProbabilityForPosition(int x, int y) {
		return this.X.getProbabilityForPosition(x)*this.Y.getProbabilityForPosition(y);
	}
}
