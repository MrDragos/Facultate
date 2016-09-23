
public class FunctionImpl implements Function{
	
	public static int testNo;
	public FunctionImpl(int testNo) {
		this.testNo = testNo;
	}

	@Override
	public double[] f(double[] input) {
		double[] output;
		switch(testNo){
			case 1:{
				double x = input[0];
				output = new double[1];
				output[0] = -x;
				return output;
			}
			case 2:{
				output = new double[2];
				double x = input[0];
				double y = input[1];
				output[0] = x-2*y;
				output[1] = x+y;
				return output;
			}
			case 3:{
				output = new double[1];
				double x = input[0];
				double y = input[1];
				double z = input[2];
				output[0] = x+y+z;
				return output;
			}
			default:{
				output = new double[1];
				double x = input[0];
				output[0] = x*x;
				return output;
			}
		}
	}
}
