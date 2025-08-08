package src.p2025_08_08;

interface Hello{
  void sayHello(String name);
}
interface GoodBye{
  void sayGoodBye(String name);
}

class SubClass implements GoodBye, Hello {
  public void sayHello(String name){
      System.out.println(name+"씨 안녕하세요!");
  }
  public void sayGoodBye(String name){
      System.out.println(name+"씨 안녕히 가세요!");
  }
}
class AbstractTest03{
   public static void main(String[] args) {
     SubClass test= new SubClass();
     test.sayHello("홍길동");
     test.sayGoodBye("홍길동");
   }   
}                                                       
