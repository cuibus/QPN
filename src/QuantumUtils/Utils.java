package QuantumUtils;


public class Utils {
	public static float ro= (float) (1/Math.sqrt(2.0));
	
	private static Qpoz[][] p_aux = initP_aux();
	private static Qpoz[][] initP_aux() {
		Qpoz[][] p_aux = new Qpoz[4][4];
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				p_aux[i][j] = new Qpoz(8);
		return p_aux;
	}
	
	/*
	public static HashMap<Point,float[][]> getAllGammas(float[] re, float[] im){
		//gets all the gamma matrices for all points (dimensions 8x8x(a0,b0, a1,b1), where a,b = complex nrs )
		HashMap<Point,float[][]> gs = new HashMap();
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++) {
				gs.put(new Point(i,j), getGamma(re,im,i*4));
			}
		return gs;
	}
	*/
	/*
	public static float[][] getGamma(float[] re, float im[], int startIndex) {
		//gets the Gamma matrix for one point (input = c0,c1, c2,c3 = nr complexe. = 2qbiti
		// StartIndex e folosit pt offset la matricea G
		// => (re, im) = coef in order: c0re, c0im, c1re, c1im, c2re, c2im, c3re, c3im, etc
		// => re si im trebuie sa aiba 4 elemente dupa startIndex
		float[][] g = new float[4][4];
		g[0][0] = re[startIndex+0]*re[startIndex+1]*re[startIndex+2]*re[startIndex+3];
		g[0][1] = re[startIndex+0]*re[startIndex+1]*re[startIndex+2]*im[startIndex+3];
		g[0][2] = re[startIndex+0]*re[startIndex+1]*im[startIndex+2]*re[startIndex+3];
		g[0][3] = re[startIndex+0]*re[startIndex+1]*im[startIndex+2]*im[startIndex+3];
		
		g[1][0] = re[startIndex+0]*im[startIndex+1]*re[startIndex+2]*re[startIndex+3];
		g[1][1] = re[startIndex+0]*im[startIndex+1]*re[startIndex+2]*im[startIndex+3];
		g[1][2] = re[startIndex+0]*im[startIndex+1]*im[startIndex+2]*re[startIndex+3];
		g[1][3] = re[startIndex+0]*im[startIndex+1]*im[startIndex+2]*im[startIndex+3];
		
		g[2][0] = im[startIndex+0]*re[startIndex+1]*re[startIndex+2]*re[startIndex+3];
		g[2][1] = im[startIndex+0]*re[startIndex+1]*re[startIndex+2]*im[startIndex+3];
		g[2][2] = im[startIndex+0]*re[startIndex+1]*im[startIndex+2]*re[startIndex+3];
		g[2][3] = im[startIndex+0]*re[startIndex+1]*im[startIndex+2]*im[startIndex+3];
		
		g[3][0] = im[startIndex+0]*im[startIndex+1]*re[startIndex+2]*re[startIndex+3];
		g[3][1] = im[startIndex+0]*im[startIndex+1]*re[startIndex+2]*im[startIndex+3];
		g[3][2] = im[startIndex+0]*im[startIndex+1]*im[startIndex+2]*re[startIndex+3];
		g[3][3] = im[startIndex+0]*im[startIndex+1]*im[startIndex+2]*im[startIndex+3];
		return g;
	}
*/
	public static float[][] getGammaFromReIm(float[] re, float im[]) {
		//gets the Gamma matrix for one point (input = c0,c1, c2,c3 = nr complexe. = 2qbiti
		// StartIndex e folosit pt offset la matricea G
		// => (re, im) = coef in order: c0re, c0im, c1re, c1im, c2re, c2im, c3re, c3im, etc
		// => re si im trebuie sa aiba 4 elemente dupa startIndex
		float[][] g = new float[4][4];
		g[0][0] = re[0]*re[1]*re[2]*re[3];
		g[0][1] = re[0]*re[1]*re[2]*im[3];
		g[0][2] = re[0]*re[1]*im[2]*re[3];
		g[0][3] = re[0]*re[1]*im[2]*im[3];
		
		g[1][0] = re[0]*im[1]*re[2]*re[3];
		g[1][1] = re[0]*im[1]*re[2]*im[3];
		g[1][2] = re[0]*im[1]*im[2]*re[3];
		g[1][3] = re[0]*im[1]*im[2]*im[3];
		
		g[2][0] = im[0]*re[1]*re[2]*re[3];
		g[2][1] = im[0]*re[1]*re[2]*im[3];
		g[2][2] = im[0]*re[1]*im[2]*re[3];
		g[2][3] = im[0]*re[1]*im[2]*im[3];
		
		g[3][0] = im[0]*im[1]*re[2]*re[3];
		g[3][1] = im[0]*im[1]*re[2]*im[3];
		g[3][2] = im[0]*im[1]*im[2]*re[3];
		g[3][3] = im[0]*im[1]*im[2]*im[3];
		return g;
	}
	
	public static void step(Qpoz[] p, float[][] gamma, boolean printOutVerbose) {
		// p are 4 componente: (X0,Y0),(X1,Y1),(X2,Y2),(X3,Y3) - una pt fiecare orientare, gamma este o matrice 4x4
		//t0-t3
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++) {
				p_aux[i][j].copyFrom(p[i]);
				p_aux[i][j].multiply(gamma[i][j], gamma[i][j]);	
			}

		//t0'-t3'
		p[0].add4(p_aux[0][0], p_aux[1][0], p_aux[2][0], p_aux[3][0]);
		p[0].X.shiftPositive();
		p[0].Y.shiftPositive();
		p[1].add4(p_aux[0][1], p_aux[1][1], p_aux[2][1], p_aux[3][1]);
		p[1].X.shiftNegative();
		p[1].Y.shiftPositive();
		p[2].add4(p_aux[0][2], p_aux[1][2], p_aux[2][2], p_aux[3][2]);
		p[2].X.shiftPositive();
		p[2].Y.shiftNegative();
		p[3].add4(p_aux[0][3], p_aux[1][3], p_aux[2][3], p_aux[3][3]);
		p[3].X.shiftNegative();
		p[3].Y.shiftNegative();

		if (printOutVerbose) {
			for (int i=0;i<4;i++)
				System.out.println("p["+i+"]: " + p[i].toString());
		}
		
		
		//prob test: checking total probability, should be close to 1
		float probX=0f;
		float probY=0f;
		for (int i=0;i<4;i++) {
			probX += p[i].X.getTotalProbability();
			probY += p[i].Y.getTotalProbability();
		}
		if (Math.abs(probX-1) > 0.01 || Math.abs(probY-1) > 0.01)
			throw new RuntimeException("probability error, probX="+probX+";probY="+probY);
	}
	
	public static void stepLinear(Qvec[] fi, float[][] A, boolean printOutVerbose) {
		// fi are 2 componente: fi_stanga, fi_dreapta - una pt fiecare orientare; fiecare Qvec are dimensiune 16
		Qvec fi_aux_dr0 = fi[0].createCopy();
		Qvec fi_aux_st0 = fi[1].createCopy();
		fi_aux_dr0.multiply(A[0][0], A[0][0]);
		fi_aux_st0.multiply(A[0][1], A[0][1]);
		
		Qvec fi_aux_dr1 = fi[0].createCopy();
		Qvec fi_aux_st1 = fi[1].createCopy();
		fi_aux_dr1.multiply(A[1][0], A[1][0]);
		fi_aux_st1.multiply(A[1][1], A[1][1]);
		
		fi[0].addQvec(fi_aux_dr0, fi_aux_st0);
		fi[0].shiftPositive();
		
		fi[1].addQvec(fi_aux_dr1, fi_aux_st1);
		fi[1].shiftNegative();

		if (printOutVerbose) {
			for (int i=0;i<2;i++)
				System.out.println("fi["+i+"]: " + fi[i].toString());
		}
		
		//prob test: checking total probability, should be close to 1
		float prob=0f;
		for (int i=0;i<2;i++) {
			prob += fi[i].getTotalProbability();
		}
		if (Math.abs(prob-1) > 0.01)
			throw new RuntimeException("probability error, prob="+prob);
	}
	
	public static Qpoz[] getQpozInitial(int orientation, int x, int y) {
		Qpoz[] qpoz = new Qpoz[4];
		for (int i=0;i<4;i++)
			qpoz[i] = new Qpoz(8);
		qpoz[orientation].X.re[x] = ro;
		qpoz[orientation].X.im[x] = ro;
		qpoz[orientation].Y.re[y] = ro;
		qpoz[orientation].Y.im[y] = ro;
		return qpoz;
	}
	
	public static Qvec[] getFitnessInitial(int length, int orientation, int pos) {
		Qvec[] qvec = new Qvec[2];
		for (int i=0;i<2;i++)
			qvec[i] = new Qvec(length);
		qvec[orientation].re[pos] = ro;
		qvec[orientation].im[pos] = ro;
		return qvec;
	}
}
