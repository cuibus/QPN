package SphereQuantumWalker_Letia;

//Quantum Vector of dm dimension 
//dm - dimension
//name - of Qvec
public class Qvec {
	String name;
	public float[] reVec;//real vector
	public float[] imVec; //imaginary vector
	int dm;// vector dimension
	public Qvec(String s, int dim) {
		dm=dim;
		name=new String(s);
		reVec=new float[dm];
		imVec=new float[dm];
	}
	
	public void vecPrint() //print Qvec
	{// print the re and im vectors
		System.out.print("Qvec name "+ name + " (re,im) " + "dim "+dm + "; i=0->dim : ");
		for(int i=0;i<dm;i++)
			System.out.print( " ("+ reVec[i]+ ","+ imVec[i]+ ") ");
		System.out.println(" ");
	}
	
	public void multQ(float a, float b )//multiply Qvec
	{// multiply the reVec with a and in=mVec with b
		for(int i=0; i<dm; i++)
		{
			reVec[i]= a * reVec[i];
			imVec[i]= b * imVec[i];
		}
	}
	public void shiftP() 
	{// circular shift positive 
		float tmp1, tmp2;
		tmp1=reVec[dm-1];
		tmp2=imVec[dm-1];
		for(int i=(dm-1);i>0;i--) {
			reVec[i]=reVec[i-1];
			imVec[i]=imVec[i-1];
		}
		reVec[0]=tmp1;
		imVec[0]=tmp2;
	}
	public void shiftN() 
	{// circular shift negative
		float tmp1, tmp2;
		tmp1=reVec[0];
		tmp2=imVec[0];
		for(int i=0;i<(dm-1);i++) {
			reVec[i]=reVec[i+1];
			imVec[i]=imVec[i+1];
		}
		reVec[dm-1]=tmp1;
		imVec[dm-1]=tmp2;
	}
	public void copy(Qvec qv) {
		//copy qv in current Qvec
		for(int i=0;i<dm;i++)
		{
			reVec[i]=qv.reVec[i];
			imVec[i]=qv.imVec[i];
		}
	}
	public void addQvec(Qvec x, Qvec y) {
		for(int i=0;i<dm;i++) {
			reVec[i]=x.reVec[i]+y.reVec[i];
			imVec[i]=x.imVec[i]+y.imVec[i];
		}
	}
}
