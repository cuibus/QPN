package fromLetia.SphereQW;
//build an array of arDim of Qpoz
public class PlaceArray {
	int arDim=16;
	String name;
	int dm=8;
	public Qpoz[] arQpoz;
	public PlaceArray(String nm)
	{
		name=nm;
		arQpoz =new Qpoz[arDim];
		arQpoz[0]=new Qpoz("p_0-0",dm);
		arQpoz[1]=new Qpoz("p_0-1",dm);
		arQpoz[2]=new Qpoz("p_0-2",dm);
		arQpoz[3]=new Qpoz("p_0-3",dm);
				
		arQpoz[4]=new Qpoz("p_1-0",dm);
		arQpoz[5]=new Qpoz("p_1-1",dm);
		arQpoz[6]=new Qpoz("p_1-2",dm);
		arQpoz[7]=new Qpoz("p_1-3",dm);
		
		arQpoz[8]=new Qpoz("p_2-0",dm);
		arQpoz[9]=new Qpoz("p_2-1",dm);
		arQpoz[10]=new Qpoz("p_2-2",dm);
		arQpoz[11]=new Qpoz("p_2-3",dm);
		
		arQpoz[12]=new Qpoz("p_3-0",dm);
		arQpoz[13]=new Qpoz("p_3-1",dm);
		arQpoz[14]=new Qpoz("p_3-2",dm);
		arQpoz[15]=new Qpoz("p_3-3",dm);
	}
	
	public void printArrayPlace() {
		System.out.println("ArrayPlace: "+name);
		for(int i=0; i<arDim;i++) {
			arQpoz[i].qpozPrint();
		}
	}
}
