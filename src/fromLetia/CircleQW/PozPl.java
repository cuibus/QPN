package fromLetia.CircleQW;

//place instantiation
public class PozPl {
	String pz;
	public float[] reVec;
	public float[] imVec;
	public PozPl(String s) {
		pz=new String(s);
		reVec=new float[8];
		imVec=new float[8];
	}
	public void vecPrint() 
	{
		System.out.print("pl name "+ pz + " (re,im): ");
		for(int i=0;i<8;i++)
		{System.out.print( " ("+ reVec[i]+ ","+ imVec[i]+ ") ");}
	}
	
	public void rePrint()
	{
		System.out.print("pl name "+ pz + " reVec: ");
		for(int i=0;i<8;i++)
		{System.out.print(" i= "+ i+ " "+ reVec[i]);}	
	
	}
	public void imPrint()
	{
		System.out.print("pl name "+ pz + " imVec: ");
		for(int i=0;i<8;i++)
		{System.out.print(" i= "+ i+ " "+ imVec[i]);}	
	}
}
