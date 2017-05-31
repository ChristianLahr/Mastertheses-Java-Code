
package com.lorenzotorricelli.useful;


                  
public class Print{                       /** Must be public, otherwise they have default package access and don't compile */
 public static void print(Object o){
    System.out.println(o);
  }

     public static void print(){   /** Overload. Print linebreak */
    System.out.print("\n");
    }
 
 public static void printn(Object o){   //Print without newline
    System.out.print(o);
    }
}

