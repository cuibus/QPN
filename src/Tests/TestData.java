package Tests;

import java.util.ArrayList;
import java.util.List;

import QuantumUtils.Point;

public class TestData {
	public static float ro = (float) (1/Math.sqrt(2.0));
	public static float[] oneQbitRo = new float[] { ro, ro, 0, 0 }; // c0re,c0im,c1re,c1im
	public static float[] twoQbitsRoRo = new float[] { ro, ro, 0, 0, ro, ro, 0, 0}; // c0re,c0im,c1re,c1im, c2re,c2im,c3re,c3im
	public static float[] twoQbitsRo0 = new float[] { ro, 0, ro, 0, ro, 0, ro, 0}; // c0re,c0im,c1re,c1im, c2re,c2im,c3re,c3im
	public static float[] twoQbitsHalf = new float[] { 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f}; // c0re,c0im,c1re,c1im, c2re,c2im,c3re,c3im
	public static float[] twoQbitsRoAll = new float[] { ro, ro, ro, ro, ro, ro, ro, ro}; // c0re,c0im,c1re,c1im, c2re,c2im,c3re,c3im
	public static float[] twoQbits1p2 = new float[] { 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f,}; // c0re,c0im,c1re,c1im, c2re,c2im,c3re,c3im
	
	public static float[][] gammaForCoinFromPaper = new float[][] {
		{-0.5f, 0.5f, 0.5f, 0.5f},
		{0.5f, -0.5f, 0.5f, 0.5f},
		{0.5f, 0.5f, -0.5f, 0.5f},
		{0.5f, 0.5f, 0.5f, -0.5f}};
	
	public static List<Point> startPoints = new ArrayList<Point>() { { add(new Point(1,1)); } };
	public static int[] startOrientations = new int[] { 2 };
	public static List<Point> targetPoints = new ArrayList<Point>() { { add(new Point(5,5));} };
	public static List<Point> trapPoints = new ArrayList<Point>() { { add(new Point(3,3)); add(new Point(2,4)); add(new Point(4,2)); } };
	public static int timeHorizon = 16;	
		
	public static float getRandomTheta() { return (float)(Math.random()*2 * Math.PI); }
	public static float[] getRandomThetas(int n) {
		float[] thetas = new float[n];
		for (int i=0;i<n;i++) 
			thetas[i] = getRandomTheta();
		return thetas;
	}
	

}
