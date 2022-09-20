package fromLetia.SphereQW;
//stores X and Y position by two Qvec vector of dm dimension
public class Qpoz {
	Qvec X, Y, tempQ1, tempQ2, tempQ3, tempQ4;
	int dm;
	String pozName;//p0,p1,p2,p3
	public Qpoz(String nm, int dim) {
		pozName=nm;
		dm=dim;
		X=new Qvec(pozName+" X ",dm);
		Y=new Qvec(pozName+" Y ",dm);
		tempQ1=new Qvec("tmp1",dm);
		tempQ2=new Qvec("tmp2",dm);
		tempQ3=new Qvec("tmp3",dm);
		tempQ4=new Qvec("tmp4",dm);
	}
	public void qpozPrint() {
		System.out.println(pozName);
		X.vecPrint();
		Y.vecPrint();
	}
	public void qpozCopy(Qpoz qp) {
		X.copy(qp.X);
		Y.copy(qp.Y);
	}
	
	public void qpozAdd(Qpoz qp1, Qpoz qp2, Qpoz qp3, Qpoz qp4) {
		//add 4 Qpoz vectors  
		tempQ1.copy(qp1.X);
		tempQ2.copy(qp2.X);
		tempQ3.copy(qp3.X);
		tempQ4.copy(qp4.X);
				
		X.addQvec(tempQ1, tempQ2);
		X.addQvec(X, tempQ3);
		X.addQvec(X, tempQ4);
		
		tempQ1.copy(qp1.Y);
		tempQ2.copy(qp2.Y);
		tempQ3.copy(qp3.Y);
		tempQ4.copy(qp4.Y);
		
		Y.addQvec(tempQ1, tempQ2);
		Y.addQvec(Y, tempQ3);
		Y.addQvec(Y, tempQ4);
	}

}
