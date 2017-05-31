package com.lorenzotorricelli.monsters;
interface Monster {
	void damage();
}

interface DangerousMonster extends Monster {
	void destroy();
}

interface Lethal {
	void kill();
}

class FireBreathingMonster implements DangerousMonster {
	public void damage() {}
	public void destroy() {}
}

interface Vampire extends DangerousMonster, Lethal {
	void drinkBlood();
}

class Nosferatu implements Vampire {
	public void damage() {}
	public void destroy() {}
	public void kill() {}
	public void drinkBlood() {}
}

public class HorrorShow {
	static void polyMonster(Monster b) { 
		b.damage(); 
		}
	static void polyDangerousMonster(DangerousMonster d) {
		d.damage();
		d.destroy();
	}
	static void polyLethal(Lethal l) {
		l.kill(); 
		}
	
	public static void main(String[] args) {
		DangerousMonster Godzilla = new FireBreathingMonster(); //explicit upcast to interface
		polyMonster(Godzilla); //further implicit upcast to base interface
		polyDangerousMonster(Godzilla); //no second upcast
		Vampire klausKinski = new Nosferatu(); //upcast to base class
		polyMonster(klausKinski);  
		polyDangerousMonster(klausKinski);
		polyLethal(klausKinski);
		}
}
