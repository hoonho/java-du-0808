package src.p2025_08_08;

abstract class AbstractClass{
	abstract void Method01();
	void Method02(){
	   System.out.println("Method02:  추상클래스에서 구현");
	 }
}

class SubClass extends AbstractClass{	
	void Method01(){
		System.out.println("Method01: 서브클래스에서 구현된 추상메소드");
	}	
}
class AbstractTest01{
	public static void main(String args[]){
	   SubClass obj =  new SubClass();
	   obj.Method01();
	   obj.Method02();	   
	 }
}


