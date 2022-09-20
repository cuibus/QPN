package QuantumUtils;

//Quantum Vector V
public class Qvec {
	public float[] re;
	public float[] im;
	public Qvec(int dm) {
		re=new float[dm];
		im=new float[dm];
	}

	public String toString(){
		StringBuilder s = new StringBuilder("");
		for(int i=0;i<re.length;i++)
			s.append("(" + re[i]+ ","+ im[i]+ ")");
		return s.append(" ").toString(); 
	}

	public Qvec multiply(float a, float b ){//multiply Qvec
		// multiply the re with a and im with b
		for(int i=0; i<re.length; i++){
			re[i] = a * re[i];
			im[i] = b * im[i];
		}
		return this;
	}

	public Qvec shiftPositive(){
		// circular shift positive 
		float tmpRe, tmpIm;
		tmpRe=re[re.length-1];
		tmpIm=im[re.length-1];
		for(int i=re.length-1;i>0;i--) {
			re[i] = re[i-1];
			im[i] = im[i-1];
		}
		re[0] = tmpRe;
		im[0] = tmpIm;
		return this;
	}

	public Qvec shiftNegativeLinear(){
		// linear shift negative
		for(int i=0;i<re.length-1;i++) {
			re[i] = re[i+1];
			im[i] = im[i+1];
		}
		return this;
	}

	public Qvec shiftPositiveLinear(){
		for(int i=re.length-1;i>0;i--) {
			re[i] = re[i-1];
			im[i] = im[i-1];
		}
		return this;
	}

	public Qvec shiftNegative(){
		// circular shift negative 
		float tmpRe, tmpIm;
		tmpRe=re[0];
		tmpIm=im[0];
		for(int i=0;i<re.length-1;i++) {
			re[i] = re[i+1];
			im[i] = im[i+1];
		}
		re[re.length-1] = tmpRe;
		im[re.length-1] = tmpIm;
		return this;
	}

	public void copyFrom(Qvec qv) { //copy qv in current Qvec
		for(int i=0;i<re.length;i++){
			re[i] = qv.re[i];
			im[i] = qv.im[i];
		}
	}
	public Qvec createCopy() {
		Qvec result = new Qvec(this.re.length);
		for(int i=0;i<re.length;i++){
			result.re[i] = this.re[i];
			result.im[i] = this.im[i];
		}
		return result;
	}
	public Qvec addQvec(Qvec x, Qvec y) { //add x and y and put result in this
		for(int i=0;i<re.length;i++) {
			re[i] = x.re[i] + y.re[i];
			im[i] = x.im[i] + y.im[i];
		}
		return this;
	}

	public float getProbabilityForPosition(int position) {
		//position must be between 0 and dm
		return this.re[position] * this.re[position] + this.im[position] * this.im[position];
	}

	public float getTotalProbability() {
		float prob = 0f;
		for (int i=0;i<this.re.length;i++) {
			prob += this.re[i] * this.re[i] + this.im[i] * this.im[i];
		}
		return prob;
	}

}
