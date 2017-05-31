package com.lorenzotorricelli.breakcontinue;


	public class BreakAndContinue {
		public static void main(String[] args) {
		for(int i = 0; i < 30; i++) {
		if(i ==  27) break; // Out of for loop
		if(i % 2 != 0) continue; // Next iteration. Does not execute print statement
		System.out.print(i + " ");
		}
}
	}
