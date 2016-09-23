import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TestNeuralNet {
	
	public static int testNo;
	public static final int iterations = 100;
	public static int inputSize;
	public static int[][] inputDomain;
	public static int outputSize;
	public static int[][] outputDomain;
	public static Function function;
	public static ArrayList<double[]> trainingSet;
	
	public static void init(){
		switch (testNo) {
			case 1:{
		          inputSize = 1;
		          outputSize = 1;
		          int[][] input = {{-100, 100}};		    
		          int[][] output = {{-100, 100}};
		          inputDomain = input;
		          outputDomain = output;
			}
				break;
			case 2:{
		          inputSize = 2;
		          outputSize = 2;
		          int[][] input = {{-100, 100}, {-100, 100}};
		          int[][] output = {{-300, 300}, {-200, 200}};
		          inputDomain = input;
		          outputDomain = output;
			}
				break;
			case 3:{
		          inputSize = 3;
		          outputSize = 1;
		          int[][] input = {{-100, 100}, {-100, 100}, {-100, 100}};
		          int[][] output = {{-300, 300}};
		          inputDomain = input;
		          outputDomain = output;
			}
				break;
			case 4:{
		        inputSize = 1;
		        outputSize = 1;
		        int[][] input = {{-20, 20}};
		        int[][] output = {{0, 400}};
		        inputDomain = input;
		        outputDomain = output;
			}
				break;
		}
		
		function = new FunctionImpl(testNo);
	}
	
	public static void main(String[] args) {
		Random rand = new Random();
		ArrayList<ArrayList<Double>> errors = new ArrayList<ArrayList<Double>>(iterations);
		testNo = Integer.parseInt(args[0]);
		init();
		NeuralNet nn = new NeuralNet(inputSize, inputDomain, outputSize, outputDomain, function);
		nn.initNetwork();
		
		trainingSet = new ArrayList<double[]>();
		for(int t=0;t<iterations;t++){
			double[] input = new double[inputSize];
			//generate random input values while respecting the domain restrictions
			for(int i=0;i<inputSize;i++){
				double interval = (double)(inputDomain[i][1]-inputDomain[i][0]);
				
				input[i] = rand.nextDouble()*interval+(double)inputDomain[i][0];
				//input[i] = (double)rand.nextInt((int)interval)+(double)inputDomain[i][0];
				//System.out.println(input[i]);
				//scalez input-ul
				input[i] = input[i]/interval;
				//System.out.println(input[i]);
			}
			
			trainingSet.add(input);
			ArrayList<Double> temporalError = new ArrayList<Double>(); 
			for(int k=0;k<trainingSet.size();k++){
				nn.feedForward(trainingSet.get(k));
				
				nn.backPropagation();

				double[] output = nn.function.f(trainingSet.get(k));
				double err=0.0;
				for(int i=0;i<outputSize;i++){
					double d = output[i]-nn.y[i];

					err += d*d;
				}
				temporalError.add(err);

			}
			errors.add(temporalError);
		}
		/*
		for(int i=1;i<=iterations;i++){
			System.out.println("Test "+i+" :");
			ArrayList<Double> iterationError = errors.get(i-1);
			for(int j=0;j<iterationError.size();j++){
				System.out.println("Example "+j+" :\t"+iterationError.get(j));
			}
		}
		*/

		try{
			String filename = "test" + args[0];
			File file = new File(filename);
			FileWriter fw = new FileWriter(file);
			for(int i=0;i < iterations;i++){
				//fw.write((i+1) + " " + errors.get(i).get(i) + "\n");
				fw.write((i+1) + " " + errors.get(i).get(i) + "\n");
			}
			fw.close();
			Runtime runtime = Runtime.getRuntime();
			Process p = runtime.exec("./plot.sh " + filename);
			p.waitFor();
			file.delete();
		}catch(IOException e){
			e.printStackTrace();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		

		System.out.println("Final/Initial error: "+100.0*errors.get(iterations-1).get(iterations-1)/errors.get(0).get(0)+" %");

		
	}
}
