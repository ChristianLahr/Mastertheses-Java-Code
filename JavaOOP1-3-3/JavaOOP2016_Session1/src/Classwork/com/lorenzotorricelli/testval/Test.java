package com.lorenzotorricelli.testval;

	
	

public class Test {
boolean eval(int testval, int begin, int end){ 
	    if(testval < begin || testval > end)
	      return false;
	    else return true;
	   }
boolean ternaryEval(int testval, int begin, int end){ 
  return  (testval < begin || testval > end) ? false : true;
   }
}
	   