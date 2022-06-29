package CircleQW;

public class ClMn {

	public static void main(String[] args) {
		System.out.println("Quantum discrete circular random walk");
		String poz= "X";
		float ro= (float) (1/Math.sqrt(2.0)); 
		String plc_1= "place_1";
		Qvec p2=new Qvec("plc_2", 8); //X
		p2.reVec[0]=ro;//init p2
		p2.imVec[0]=ro;
		p2.vecPrint();
	
		Qvec p3=new Qvec("plc_3",8);
		Qvec p4=new Qvec("plc_4",8);
		Qvec p5=new Qvec("plc_5",8);
		Qvec p6=new Qvec("plc_6",8);
		Qvec p7=new Qvec("plc_7",8);
		
		for(int j=1;j<20;j++) {//evolution
			//t1:
			p3.copy(p2);
			p3.multQ(ro, ro);
			p4.copy(p2);
			p4.multQ(ro, ro);
			
			//t2:
			p6.copy(p5);
			p6.multQ(-ro,-ro);
			p7.copy(p5);
			p7.multQ(ro, ro);
			
			//t3:
			p2.addQvec(p3,p7);
			p2.shiftP();
			
			//t4:
			p5.addQvec(p4, p6);
			p5.shiftN();
			
			float prob=0;
			for(int k=0;k<p2.dm; k++) {
				prob = prob+ p2.reVec[k]*p2.reVec[k] + p5.reVec[k]*p5.reVec[k]
						+p2.imVec[k]*p2.imVec[k]+ p5.imVec[k]*p5.imVec[k];   
			}
			System.out.println("j= "+ j+ "   prob= "+ prob);
			
			p2.vecPrint();
			p5.vecPrint();
			
		}
			
				
	}

}
