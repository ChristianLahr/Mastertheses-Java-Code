package com.lorenzotorricelli.constructortemperature;

class Temperature{    //Illustrates constructor using this. . I am defining a constructor saying that the data field .temp of the object g
	// generating the call must be equal to the passed value denominated temp, thus allowing (or resolving name clashes)
	float temp;
	Temperature(float t){
		temp=t;
	}
}