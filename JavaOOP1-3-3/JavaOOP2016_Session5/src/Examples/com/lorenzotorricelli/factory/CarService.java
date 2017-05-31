package com.lorenzotorricelli.factory;



interface Service {
	void fix();
	void repair();
}

interface ServicePoint{
	Service getService();
}

class AlphaRomeoService implements Service {
	AlphaRomeoService() {}; // Package access
	public void fix() {System.out.println("Alpha Romeo mechanics repairing");}
	public void repair() {System.out.println("Alpha romeo mechanics fixing");}
}

class AlphaRomeoServicePoint implements ServicePoint {
	public Service getService() {
		return new AlphaRomeoService();
	}
}

class HyundaiService implements Service {
	HyundaiService() {}; // Package access
	public void fix() {System.out.println("Hyundai mechanics repairing");
	}
	public void repair() {System.out.println("Hyundai mechanics fixing");
	}
}

class HyundaiServicePoint implements ServicePoint {
	public Service getService() { //return type Service 
		return new HyundaiService(); //returned object upcast
	}
}


public class CarService{

static void consumerService(ServicePoint serv){
    Service s=serv.getService();
    s.fix();
    s.repair();
}
	
public static void main(String[] args) {
	consumerService(new AlphaRomeoServicePoint()); //implicit upcast to the Servicepoint interface
	// Implementations are completely interchangeable: same interface shared by both the "ServicPoint"
	//arguments
	consumerService(new HyundaiServicePoint()); //implicit upcast to ServicePoint interface
}
//Is another form of  encapsulation "no need of constructing the repairman", you only deal with the
// desk office



} 