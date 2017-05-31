package com.lorenzotorricelli.gasses;

public class Gas{
   private double temperature=315.6; //Kelvin 
  // private double temperature=32.9; //NOW, Celsius, as a chnge of design of our Colleagues...

   
   final double gayLussacConstant=1.5;
   
   
   void setTemperature(double t){ //Temperature, given in Kelvin
	  temperature=t;
	  
   }

//  private  void celsiusToKelvin(){ //Returns temperature in Kelvin if Temperature inputted in celsius Celsius
	//  setTemperature(temperature+273.15);            
	  
  // }
   
   

   
   double getTemperature(){
	//    celsiusToKelvin(); 
	   return temperature; 
   }
  

   double getPressure(){ //Encapsulated fields. Changing the input form Kelvin to Celsius 
 //  celsiusToKelvin(); 
  return temperature*gayLussacConstant;
  }
}

      

   
   
   //}
//}
