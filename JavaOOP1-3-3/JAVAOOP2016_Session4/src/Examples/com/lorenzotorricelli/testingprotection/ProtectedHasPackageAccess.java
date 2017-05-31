package com.lorenzotorricelli.testingprotection;
import com.lorenzotorricelli.protection.*;




public class ProtectedHasPackageAccess extends ProtectedClass{
   public static void main(String[] args){
	   ProtectedHasPackageAccess p= new ProtectedHasPackageAccess();
	   System.out.println(p.i);   //protected in the base class. Visible from classes inheriting from outside the package. Try changing p
	   p.foo();
   }
}