package com.lorenzotorricelli.nestedclasses;

public class NestTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
          Outer.StaticInner si=new Outer.StaticInner(); //creating static inner new
          int h=si.getData();
          System.out.println(h);
	}

}

class Outer{
	static private int k=9;
	public static class StaticInner{
		StaticInner(){
			System.out.println("I am a static Inner!");
			
		}
		int getData(){
			System.out.println("but I can still access fields (provided they are static)");
			return k;
		}
		}
	}
