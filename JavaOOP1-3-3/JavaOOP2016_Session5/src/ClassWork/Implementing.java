
public class Implementing extends Root implements MyFirstInterface, SecondInterface  {
      public void whatever(){
    	  System.out.println("Do as you please...");
      };
      void oldSchoolMethod(){System.out.println("A lame old base method");};
      public void anotherMethod(){
    		 System.out.println("Cool, I can implement multiple interfaces");
    		 
    		 
    	 };
     
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         Implementing i=new Implementing();
         i.whatever();
         i.anotherMethod();
         i.oldSchoolMethod();
	}

}


abstract class Root{
  abstract void oldSchoolMethod();
}