package QuantumUtils;

import java.util.List;

import Tests.TestData;

public class WalkerUtils {
	public static float getProbForPosition(Qpoz[] p, int x, int y) {
		float prob = 0f;
		for (int orientX=0;orientX<4;orientX++)
			for (int orientY=0;orientY<4;orientY++)
				prob += p[orientX].X.getProbabilityForPosition(x) * p[orientY].Y.getProbabilityForPosition(y);

		return prob;
	}
	
	public static float getProbForPositionsTotal(Qpoz[] p, List<Point> points) {
		float prob = 0f;
		for (Point point: points)
			prob += getProbForPosition(p, point.x, point.y);
		return prob;
	}
	
	public static float[][] getProbForAllPositions(Qpoz[] p) {
		float[][] prob = new float[8][8];
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++) 
				prob[i][j] = getProbForPosition(p, i, j);
		return prob;
	}

	public static float psi_n(Qpoz[] p) { //neutral
		float psi_n = 0;
		for (int x=0;x<8;x++) 
			for (int y=0;y<8;y++)	
				if (!TestData.targetPoints.contains(TestData.allPoints[x][y]) &&
						!TestData.trapPoints.contains(TestData.allPoints[x][y]))
					psi_n += getProbForPosition(p, x, y);
		return psi_n;
	}
	public static float psi_g(Qpoz[] p) { // target
		float psi_g = 0;
		for (Point point: TestData.targetPoints) 
			psi_g += getProbForPosition(p, point.x, point.y);
		return psi_g;
	}
	public static float psi_p(Qpoz[] p) { // trap
		float psi_p = 0;
		for (Point point: TestData.trapPoints) 
			psi_p += getProbForPosition(p, point.x, point.y);
		return psi_p;
	}
	public static float psi_gp(Qpoz[] p) { // target&trap simultaneously
		return 0;
	}

	public static float[] getProbForFitness(Qvec[] fi) {
		float[] prob = new float[fi[0].re.length];
		for (int i=0;i<fi[0].re.length;i++)
			for (int orient=0;orient<2;orient++)
				prob[i] += fi[orient].getProbabilityForPosition(i);
		return prob;
	}
	
    public static float getTotalFitness(Qvec[] fi) {
		float[] prob = getProbForFitness(fi);
		float result = 0f;
		for (int i=0;i<prob.length;i++)
			result += i * prob[i];
		return result;
    }
}
