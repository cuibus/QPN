package fromLetia.SphereQW;

public class Walker2Main {

	public static void main(String[] args) {
		System.out.println("Quantum discrete circular random walk on sphere");
		 
		float ro= (float) (1/Math.sqrt(2.0)); 
		 
		float probX=0f;
		float probY=0f;
			
		Qpoz qp0=new Qpoz("plc_0",8);
		Qpoz qp1=new Qpoz("plc_1",8);
		Qpoz qp2=new Qpoz("plc_2",8);
		Qpoz qp3=new Qpoz("plc_3",8);
		
		qp0.X.reVec[0]=ro;
		qp0.X.imVec[0]=ro;
		qp0.Y.reVec[0]=ro;
		qp0.Y.imVec[0]=ro;
		
		System.out.println("printing qp0 to qp3");
		qp0.qpozPrint();
		qp1.qpozPrint();
		qp2.qpozPrint();
		qp3.qpozPrint();
		
		PlaceArray p4=new PlaceArray("p4"); 
		System.out.println("printing p4 place array");
		p4.printArrayPlace();
		
		for(int j=1;j<50;j++) {
			System.out.println("TIC: "+j);
			//t0
			p4.arQpoz[0].X.copy(qp0.X);
			p4.arQpoz[0].X.multQ(-0.5f, -0.5f);
			p4.arQpoz[0].Y.copy(qp0.Y);
			p4.arQpoz[0].Y.multQ(-0.5f,-0.5f);
			p4.arQpoz[1].X.copy(qp0.X);
			p4.arQpoz[1].X.multQ(0.5f, 0.5f);
			p4.arQpoz[1].Y.copy(qp0.Y);
			p4.arQpoz[1].Y.multQ(0.5f,0.5f);
			p4.arQpoz[2].X.copy(qp0.X);
			p4.arQpoz[2].X.multQ(0.5f,0.5f);
			p4.arQpoz[2].Y.copy(qp0.Y);
			p4.arQpoz[2].Y.multQ(0.5f, 0.5f);
			p4.arQpoz[3].X.copy(qp0.X);
			p4.arQpoz[3].X.multQ(0.5f,0.5f);
			p4.arQpoz[3].Y.copy(qp0.Y);
			p4.arQpoz[3].Y.multQ(0.5f, 0.5f);
			//t1
			p4.arQpoz[4].X.copy(qp1.X);
			p4.arQpoz[4].X.multQ(0.5f,0.5f);
			p4.arQpoz[4].Y.copy(qp1.Y);
			p4.arQpoz[4].Y.multQ(0.5f,0.5f);
			p4.arQpoz[5].X.copy(qp1.X);
			p4.arQpoz[5].X.multQ(-0.5f,-0.5f);
			p4.arQpoz[5].Y.copy(qp1.Y);
			p4.arQpoz[5].Y.multQ(-0.5f,-0.5f);
			p4.arQpoz[6].X.copy(qp1.X);
			p4.arQpoz[6].X.multQ(0.5f,0.5f);
			p4.arQpoz[6].Y.copy(qp1.Y);
			p4.arQpoz[6].Y.multQ(0.5f,0.5f);
			p4.arQpoz[7].X.copy(qp1.X);
			p4.arQpoz[7].X.multQ(0.5f,0.5f);
			p4.arQpoz[7].Y.copy(qp1.Y);
			p4.arQpoz[7].Y.multQ(0.5f,0.5f);
			//t2
			p4.arQpoz[8].X.copy(qp2.X);
			p4.arQpoz[8].X.multQ(0.5f,0.5f);
			p4.arQpoz[8].Y.copy(qp2.Y);
			p4.arQpoz[8].Y.multQ(0.5f,0.5f);
			p4.arQpoz[9].X.copy(qp2.X);
			p4.arQpoz[9].X.multQ(0.5f,0.5f);
			p4.arQpoz[9].Y.copy(qp2.Y);
			p4.arQpoz[9].Y.multQ(0.5f,0.5f);
			p4.arQpoz[10].X.copy(qp2.X);
			p4.arQpoz[10].X.multQ(-0.5f,-0.5f);
			p4.arQpoz[10].Y.copy(qp2.Y);
			p4.arQpoz[10].Y.multQ(-0.5f,-0.5f);
			p4.arQpoz[11].X.copy(qp2.X);
			p4.arQpoz[11].X.multQ(0.5f,0.5f);
			p4.arQpoz[11].Y.copy(qp2.Y);
			p4.arQpoz[11].Y.multQ(0.5f,0.5f);
			//t3
			p4.arQpoz[12].X.copy(qp3.X);
			p4.arQpoz[12].X.multQ(0.5f,0.5f);
			p4.arQpoz[12].Y.copy(qp3.Y);
			p4.arQpoz[12].Y.multQ(0.5f,0.5f);
			p4.arQpoz[13].X.copy(qp3.X);
			p4.arQpoz[13].X.multQ(0.5f,0.5f);
			p4.arQpoz[13].Y.copy(qp3.Y);
			p4.arQpoz[13].Y.multQ(0.5f,0.5f);
			p4.arQpoz[14].X.copy(qp3.X);
			p4.arQpoz[14].X.multQ(0.5f,0.5f);
			p4.arQpoz[14].Y.copy(qp3.Y);
			p4.arQpoz[14].Y.multQ(0.5f,0.5f);
			p4.arQpoz[15].X.copy(qp3.X);
			p4.arQpoz[15].X.multQ(-0.5f,-0.5f);
			p4.arQpoz[15].Y.copy(qp3.Y);
			p4.arQpoz[15].Y.multQ(-0.5f,-0.5f);
			
			p4.arQpoz[0].qpozPrint();
			
			System.out.println("exec t0' at TIC: " +j);
			//t0'
			qp0.qpozAdd(p4.arQpoz[0], p4.arQpoz[4], p4.arQpoz[8], p4.arQpoz[12]);
			qp0.X.shiftP();
			qp0.Y.shiftP();
			qp0.X.vecPrint();
			qp0.Y.vecPrint();
			//t1'
			qp1.qpozAdd(p4.arQpoz[1], p4.arQpoz[5], p4.arQpoz[9], p4.arQpoz[13]);
			qp1.X.shiftN();
			qp1.Y.shiftP();
			qp1.X.vecPrint();
			qp1.Y.vecPrint();
			//t2'
			qp2.qpozAdd(p4.arQpoz[2], p4.arQpoz[6], p4.arQpoz[10], p4.arQpoz[14]);
			qp2.X.shiftP();
			qp2.Y.shiftN();
			qp2.X.vecPrint();
			qp2.Y.vecPrint();
			//t3'
			qp3.qpozAdd(p4.arQpoz[3], p4.arQpoz[7], p4.arQpoz[11], p4.arQpoz[15]);
			qp3.X.shiftN();
			qp3.Y.shiftN();
			qp3.X.vecPrint();
			qp3.Y.vecPrint();
			
			//prob test: checking total probability, should be close to 1
			probX=0f;
			probY=0f;
			for(int i=0;i<8;i++) {
				probX=probX+qp0.X.reVec[i]*qp0.X.reVec[i] + qp0.X.imVec[i]*qp0.X.imVec[i];
				probX=probX+qp1.X.reVec[i]*qp1.X.reVec[i] + qp1.X.imVec[i]*qp1.X.imVec[i];
				probX=probX+qp2.X.reVec[i]*qp2.X.reVec[i] + qp2.X.imVec[i]*qp2.X.imVec[i];
				probX=probX+qp3.X.reVec[i]*qp3.X.reVec[i] + qp3.X.imVec[i]*qp3.X.imVec[i];
				
				probY=probY+qp0.Y.reVec[i]*qp0.Y.reVec[i] + qp0.Y.imVec[i]*qp0.Y.imVec[i];
				probY=probY+qp1.Y.reVec[i]*qp1.Y.reVec[i] + qp1.Y.imVec[i]*qp1.Y.imVec[i];
				probY=probY+qp2.Y.reVec[i]*qp2.Y.reVec[i] + qp2.Y.imVec[i]*qp2.Y.imVec[i];
				probY=probY+qp3.Y.reVec[i]*qp3.Y.reVec[i] + qp3.Y.imVec[i]*qp3.Y.imVec[i];
			}
			System.out.println("probX= "+probX+ " & probY= "+probY);
		}
		
		
	}

}
