package com.lorenzotorricelli.decoupling;

public class Healing {
  public static void magicHealing(Antivirus c){
       c.diagnose();
       }
  
	public static void main(){
	 Antivirus mcAfee=new Antivirus();
	 Cure doctorHouse=new Doctor();
	// magicHealing(mcAfee);
	 magicHealing(mcAfee);
	}
}
