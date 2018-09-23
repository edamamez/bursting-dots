
public class Boundary {
	private int x1, x2, y1, y2;
	
	public Boundary(int x1, int x2, int y1, int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public boolean on(int x, int y){
		return x >= x1 && x <= x2 && y >= y1 && y <= y2;  
	}
	
	public int getX1(){
		return x1;
	}
	
	public int getX2(){
		return x2;
	}
	
	public int getY1(){
		return y1;
	}
	
	public int getY2(){
		return y2;
	}
	
	public static void main(String[] args) {
		Boundary a = new Boundary(100, 200, 150, 250);
		
		System.out.println(a.on(150, 240));

	}

}
