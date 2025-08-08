package src.p2025_08_08;

abstract class ShapeClass{
	abstract void draw();
}
class Circ extends ShapeClass{
	void draw(){
		System.out.println("원을 그린다");
	}
}
class Rect extends ShapeClass{
	void draw(){
		System.out.println("사각형을 그린다");
	}
}
class Tria extends ShapeClass{
	void draw(){
		System.out.println("삼각형을 그린다");
	}
}
public class AbstractTest02{
	public static void main(String args[]){
		Circ c =  new Circ();
		Rect r = new Rect();
		Tria t=  new Tria();
	
		c.draw();
		r.draw();
		t.draw();
	}
}

