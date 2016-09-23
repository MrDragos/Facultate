import java.util.Random;

public class NeuralNet {
	
	public static double[] theta;
	public static final double alpha = 0.5;	//momentum
	public static final double learningRate = 0.85;
	public static int inputSize;
	public int[][] inputDomain;
	public static int outputSize;
	public int[][] outputDomain;
	public Function function;
	public static final int numberOfLayers = 3;
	public static int[] neuronsPerLayer;
	public double[] h1;	// output of the input-Layer
	public double[] h2; // output of the hidden-Layer
	public double[] y;  // output of the output-Layer and also the final output of the network
	public double[] d;	// expected output
	
	public double[][] w1;
	public double[][] w2;
	public double[][] w3;
	
	public double[] errors1;
	public double[] errors2;
	public double[] errors3;
	
	public double[] input;
	
	//public double[][] weightDiff1;
	//public double[][] weightDiff2;
	//public double[][] weightDiff3;
	
	public double[][] oldWeights1;
	public double[][] oldWeights2;
	public double[][] oldWeights3;
	
	
	
	public NeuralNet(int inputSize,int[][] inputDomain,int outputSize,int[][] outputDomain,Function function) {
		this.inputSize = inputSize;
		this.inputDomain = inputDomain;
		this.outputSize = outputSize;
		this.outputDomain = outputDomain;
		this.function = function;
		neuronsPerLayer = new int[3];
		neuronsPerLayer[0] = inputSize;
		neuronsPerLayer[1] = inputSize*5;
		neuronsPerLayer[2] = outputSize;
		y = new double[outputSize];
		h1 = new double[neuronsPerLayer[0]];
		h2 = new double[neuronsPerLayer[1]];
		
		w1 = new double[inputSize+1][neuronsPerLayer[0]];
		w2 = new double[neuronsPerLayer[0]+1][neuronsPerLayer[1]];
		w3 = new double[neuronsPerLayer[1]+1][neuronsPerLayer[2]];
		
		
		//weightDiff1 = new double[inputSize][neuronsPerLayer[0]];
		//weightDiff2 = new double[neuronsPerLayer[0]][neuronsPerLayer[1]];
		//weightDiff3 = new double[neuronsPerLayer[1]][neuronsPerLayer[2]];
		
		oldWeights1 = new double[inputSize+1][neuronsPerLayer[0]];
		oldWeights2 = new double[neuronsPerLayer[0]+1][neuronsPerLayer[1]];
		oldWeights3 = new double[neuronsPerLayer[1]+1][neuronsPerLayer[2]];
		
		d = new double[outputSize];
		input = new double[inputSize];
		
		errors1 = new double[neuronsPerLayer[0]];
		errors2 = new double[neuronsPerLayer[1]];
		errors3 = new double[neuronsPerLayer[2]];
	}
	
	public void initNetwork(){
		
		Random rand = new Random();
		 //initialize weights with small random values between -0.1 and 0.1
		for(int i=0;i<=inputSize;i++){
			for(int j=0;j<neuronsPerLayer[0];j++){
				this.w1[i][j] = -1.0+2.0*rand.nextDouble();

			}
		}
		
		for(int i=0;i<=neuronsPerLayer[0];i++){
			for(int j=0;j<neuronsPerLayer[1];j++){
				this.w2[i][j] = (-1.0+2.0*rand.nextDouble())/10.0;
			}
		}
		
		for(int i=0;i<=neuronsPerLayer[1];i++){
			for(int j=0;j<neuronsPerLayer[2];j++){
				this.w3[i][j] = (-1.0+2.0*rand.nextDouble())/10.0;
			}
		}

	}
	
	public void feedForward(double[] input){
		
		for(int j=0;j<neuronsPerLayer[0];j++){
			double sum = 1.0*w1[0][j];
			for(int i=1;i<=inputSize;i++){
				sum += w1[i][j]*input[i-1];
			}
			h1[j] = sgn(sum);
		}
		
		for(int j=0;j<neuronsPerLayer[1];j++){
			double sum = 1.0*w2[0][j];
			for(int i=1;i<=neuronsPerLayer[0];i++){
				sum += w2[i][j]*h1[i-1];
			}
			h2[j] = sgn(sum);
		}
		
		for(int j=0;j<neuronsPerLayer[2];j++){
			double sum = 1.0*w3[0][j];
			for(int i=1;i<=neuronsPerLayer[1];i++){
				sum += w3[i][j]*h2[i-1];
			}
			y[j] = sgn(sum);
			//System.out.println(y);
		}
		
		this.input = input;
		//expected output
		d = function.f(input);
	}
	
	public double[] output(){
		return y;
	}
	
	public void backPropagation(){
		computeErrors();
		//update weights 
		double diff;
		
		for(int j=0;j<neuronsPerLayer[0];j++){
			diff = w1[0][j] - oldWeights1[0][j];
			oldWeights1[0][j] = w1[0][j];
			w1[0][j] += learningRate*errors1[j]*1.0;
		}
		
		for(int i=1;i<=inputSize;i++){
			for(int j=0;j<neuronsPerLayer[0];j++){
				diff = w1[i][j] - oldWeights1[i][j];
				oldWeights1[i][j] = w1[i][j];
				w1[i][j] += learningRate*errors1[j]*input[i-1];
			}
		}
		
		for(int j=0;j<neuronsPerLayer[1];j++){
			diff = w2[0][j] - oldWeights2[0][j];
			oldWeights2[0][j] = w2[0][j];
			w2[0][j] += learningRate*errors2[j]*1.0;
		}
		
		for(int i=1;i<=neuronsPerLayer[0];i++){
			for(int j=0;j<neuronsPerLayer[1];j++){
				diff = w2[i][j] - oldWeights2[i][j];
				oldWeights2[i][j] = w2[i][j];
				w2[i][j] += learningRate*errors2[j]*h1[i-1];
			}
		}
		
		for(int j=0;j<neuronsPerLayer[2];j++){
			diff = w3[0][j] - oldWeights3[0][j];
			oldWeights3[0][j] = w3[0][j];
			w3[0][j] += learningRate*errors3[j]*1.0;
		}
		
		for(int i=1;i<=neuronsPerLayer[1];i++){
			for(int j=0;j<neuronsPerLayer[2];j++){
				diff = w3[i][j] - oldWeights3[i][j];
				oldWeights3[i][j] = w3[i][j];
				w3[i][j] += learningRate*errors3[j]*h2[i-1];
			}
		}
	}
	
	public void computeErrors(){
		
		for(int j=0;j<neuronsPerLayer[2];j++){
			errors3[j] = y[j]*(1-y[j])*(d[j]-y[j]);
		}
		
		for(int j=0;j<neuronsPerLayer[1];j++){
			double sum2 = 0.0;
			//in viitor va trebui sa incep de la 1
			for(int i=0;i<neuronsPerLayer[2];i++){
				sum2 += errors3[i]*w3[j+1][i];
			}
			errors2[j] = h2[j]*(1-h2[j])*sum2;
		}
		
		for(int j=0;j<neuronsPerLayer[0];j++){
			double sum1 = 0.0;
			//in viitor va trebui sa incep de la 1
			for(int i=0;i<neuronsPerLayer[1];i++){
				sum1 += errors2[i]*w2[j+1][i];
			}
			errors1[j] = h1[j]*(1-h1[j])*sum1;
		}
	}
	
	//sigmoid function
	public double sgn(double x){
		//return 1.0/(1.0+Math.exp(-x));
		return 1.0/(1.0+Math.exp(-x));
	}
}
