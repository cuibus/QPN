package QuantumUtils;

public class VectorUtils {
	
	public static float[] shiftNegative(float[] vec) {
		float[] res = new float[vec.length];
		for (int i=0;i<vec.length-1;i++)
			res[i+1] = vec[i];
		res[0] = vec[vec.length-1];
		return res;
	}
	
	public static float sum(float[] vec) {
		float res = 0;
		for (int i=0;i<vec.length;i++)
			res += vec[i];
		return res;
	}
	
	public static float toBase10(float[] vec) {
		float result = 0;
		for (int i=0;i<vec.length;i++)
			result += vec[i]*Math.pow(2, i);
		return result;
	}
	
    public static float toBase10(Qvec qvec) {
		float result = 0;
		for (int i=0;i<qvec.re.length;i++)
			result += qvec.getProbabilityForPosition(i)*Math.pow(2, i);
		return result;
	}
}
