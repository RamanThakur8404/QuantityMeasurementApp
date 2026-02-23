package com.example;

import java.util.Scanner;

public class QuantityMeasurementApp {
	
	public static class Feet{
		
		private final double value;
		
		public Feet(double value) {
			this.value = value;
		}

		public double getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			return Double.hashCode(value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			Feet other = (Feet) obj;
			return Double.compare(this.value,other.value)==0;
		}	
	}
	
    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
       int firstInput = input.nextInt();
       int secondInput = input.nextInt();
       
       Feet feet1 = new Feet(firstInput);
       Feet feet2 = new Feet(secondInput);
       
       System.out.println("Equal ("+feet1.equals(feet2)+")");
       
    }
}
