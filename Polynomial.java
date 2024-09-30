import java.lang.Math;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Polynomial{
	public double[] coefficients;
	public int[] exponents;
	
	public Polynomial(){
		coefficients = new double[0];
		exponents = new int[0];
	}

	public Polynomial(double[] c, int[] e){
		coefficients = c;
		exponents = e;
	}

	public Polynomial(File file){
        Scanner scanner = new Scanner(file);
        String polyString = scanner.nextLine();
        scanner.close();

        String[] terms = polyString.split("(?=[+-])");

        int len = terms.length;
        coefficients = new double[len];
        exponents = new int[len];

        for (int i = 0; i < len; i++) {
            String term = terms[i].replaceAll("x\\^", "x");
            double coefficient;
            int exponent;

            if (term.contains("x")) {
                String[] parts = term.split("x");
                coefficient = parts[0].isEmpty() || parts[0].equals("+") ? 1: parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]);
                exponent = parts.length == 2 ? Integer.parseInt(parts[1]) : 1;
            } else {
                coefficient = Double.parseDouble(part);
                exponent = 0;
            }
        
        coefficients[i] = coefficient;
        exponents[i] = exponent;
        }
    }

	public Polynomial add(Polynomial p){
		int i = 0;
		int j = 0;
		int maxLen = coefficients.length + p.coefficients.length;
		double[] resultCoeff = new double[maxLen];
		int[] resultExp = new int[maxLen];
		int pos = 0;

		while(i < coefficients.length || j < p.coefficients.length)
			if(i >= coefficients.length || exponents[i] > p.exponents[j]){
				if(!resultExp.contains(p.exponents[j])){
					resultCoeff[pos] = p.coefficients[j];
					resultExp[pos] = p.exponents[j];
				}
				else{
					index = resultExp.indexOf(p.exponents[j]);
					resultCoeff[index] += p.coefficients[j];
				}
				j++;
			else if(j >= p.coefficients.length || exponents[i] < p.exponents[j]){
				if(!resultExp.contains(exponents[i])){
					resultCoeff[pos] = exponents[i];
					resultExp[pos] = exponents[i];
				}
				else{
					index = resultExp.indexOf(p.exponents[i]);
					resultCoeff[index] += coefficients[i];
				}
				i++;
			}
			else if(exponents[i] == p.exponents[j]){
				resultCoeff[pos] = coefficients[i] + p.coefficients[j];
				resultExp[pos] = exponents[i];
				i++;
				j++;
			}
			pos++;
		}
		int count = 0;
		for(int i = 0; i < resultCoeff.length; i++){
			if(resultCoeff[i] == 0){
				count++;
			}
		}
		double[] coeff = new double[resultCoeff.length-count];
		int[] exp = new int[resultCoeff.length-count];

		for(int i = 0; i < resultCoeff.length; i++){
			if(resultCoeff[i] != 0){
				coeff[i] = resultCoeff[i];
				exp[i] = resultExp[i];
			}
		}

		Polynomial result = new Polynomial(coeff, exp);
		return result;
	}

	public double evaluate(double x){
		double result = 0.0;
		for (int i = 0; i < coefficients.length; i++){
			result += coefficients[i]*(Math.pow(x,exponents[i]));
		}
		return result;
	}

	public boolean hasRoot(double r){
		return (evaluate(r) == 0.0);
	}

	public Polynomial multiply(Polynomial p){
        int maxExp = exponents[exponents.length - 1] + p.exponents[p.exponents.length - 1];
        double[] sumCoeff = new double[maxExp + 1];

        int i = 0;
        while (i < coefficients.length) {
            for (int j = 0; j < p.coefficients.length; j++) {
                double currentC = coefficients[i] * p.coefficients[j];
                int currentE = exponents[i] + p.exponents[j];  
                sumCoeff[currentE] += currentC;        
            }
        	i++;
        }

        int terms = 0;
        for (double coefficient: sumCoeff) {
            if (coefficient != 0) {
                terms++;
            }
        }

        double[] resultCoeff = new double[terms];
        int[] resultExp = new int[terms];
        
        int j = 0;
        for (int k = 0; k < sumCoeff.length; k++) {
            if (sumCoeff[k] != 0) {
                resultCoeff[j] = sumCoefficients[j];
                resultExp[j] = j;
                j++;
            }
        }

        return new Polynomial(resultCoeff, resultExp);
	}

	public void saveToFile(String file) throws IOException {
        FileWriter writer = new FileWriter(file);

        for (int i = 0; i < coefficients.length; i++) {
            double coefficient = coefficients[i];
            int exponent = exponents[i];

            if (coefficient > 0 && i != 0) {
                writer.write("+");
            }

            if (exponent == 0) {
                writer.write(String.valueOf(coefficient));
            } 
			else if (exponent == 1) {
                writer.write(coefficient + "x");
            } 
			else {
                writer.write(coefficient + "x" + exponent);
            }
        }
    	writer.close();
    }
}