package com.example;

import java.util.Scanner;

public class QuantityMeasurementApp {
	
	// Inner class to represent Feet Measurement
	public static class Feet {
		
		private final double value;
		
		public Feet(double value) {
			this.value = value;
		}

		@Override
		public int hashCode(){
			return Double.hashCode(value);
		}

		@Override
		public boolean equals(Object obj){
			//1. same reference
			if (this == obj) {
				return true;				
			}
			
			// 2. obj is null 
			if (obj == null) {				
				return false;
			}
			// 3. Type Check
			if(getClass() != obj.getClass()) {
				return false;
			}
			
			//4. Cast safely
			Feet other = (Feet) obj;
			
			//5. compare double values safely
			return Double.compare(this.value,other.value)==0;
		}	
	} 
	
	
	// Inner class to represent Inch measurement
	public static class Inches {
		private final double value;
		
		public Inches(double value) {
			this.value = value;
		}
		
		@Override
		public boolean equals(Object obj) {
			//1. same reference
			if (this == obj) {
				return true;				
			}
			
			// 2. obj is null 
			if (obj == null) {				
				return false;
			}
			// 3. Type Check
			if(getClass() != obj.getClass()) {
				return false;
			}
			
			//4. Cast safely
			Inches other = (Inches) obj;
			
			//5. compare double values safely
			return Double.compare(this.value,other.value)==0;
		}
		
		@Override
		public int hashCode() {
			return Double.hashCode(value);
		}
		
	}
	
	public static void demonstrateFeetEquality() {
	       Feet feet1 = new Feet(1.0);
	       Feet feet2 = new Feet(1.0);    
	       System.out.println("Feet Equal ("+feet1.equals(feet2)+")");
	}
	
	public static void demonstrateInchesEquality() {
			Inches inch1 = new Inches(1.0);
			Inches inch2 = new Inches(1.0);
			System.out.println("Inches Equal ("+ inch1.equals(inch2)+")");
	}
	
    public static void main(String[] args) {
    	demonstrateFeetEquality();
    	demonstrateInchesEquality();
    }
}
