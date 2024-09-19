import java.lang.Math;

public class Polynomial{
	public double[] coefficients;
	
	public Polynomial(){
		coefficients = new double[0];
	}

	public Polynomial(double[] c){
		coefficients = c;
	}

	public Polynomial add(Polynomial p){
		int maxLen = Math.max(coefficients.length, p.coefficients.length);
		double[] sum = new double[maxLen];
		for (int i = 0; i < maxLen; i++){
			if (i >= coefficients.length){
				sum[i] = p.coefficients[i];
			}
			else if (i >= p.coefficients.length){
				sum[i] = coefficients[i];
			}
			else{
				sum[i] = (coefficients[i] + p.coefficients[i]);
			}
		}
		Polynomial result = new Polynomial(sum);
		return result;
	}

	public double evaluate(double x){
		double result = 0.0;
		for (int i = 0; i < coefficients.length; i++){
			result += coefficients[i]*(Math.pow(x,i));
		}
		return result;
	}

	public boolean hasRoot(double r){
		return (evaluate(r) == 0.0);
	}
}