import java.util.*;


public class Sarsa {
	
	
    public static double delta = 0.8;
    public static double alpha = 0.1;
    
	public static int scenario = 1;
	public static int W;
	public static int H;
	public static double eps = 0.05;
	public static int episodes = 1000;
	public static int[] scores;
	public static HashSet<String> visitedStates;
	public static Board currentBoard;
	public static Board nextBoard;
	public static int currentBrick;
	public static int nextBrick;
	public static int bricksCount;
	public static int maxScore = 0;
	public static int states = 0;
	
	/*
	 * key = state+action
	 * state = board+brick-type
	 * action = rotation+left
	 * value = Q(s,a)
	 */
	
	public static Hashtable<String, Double> Q;
	/*
	 * key = state
	 */
	public static Hashtable<String, Integer> Ns;

	public static String  currentState;
	
	public static final int A = 0;
	public static final int B = 1;
	public static final int C = 2;
	public static final int D = 3;
	public static final int E = 4;
	public static final int F = 5;
	public static final int G = 6;
	
	public static int generateBrickType(int scenario){
		Random rand = new Random();
		switch(scenario){
			case 1:{
				return 0;
			}
			case 2:{
				int randomNumber = rand.nextInt(2);
				if(randomNumber == 0){
					return 0;
				}else{
					return 6;
				}
			}
			case 3:{
				int randomNumber = rand.nextInt(5);
				if(randomNumber == 0){
					return 0;
				}
				
				if(randomNumber == 1 || randomNumber == 2){
					return 1;
				}
				
				if(randomNumber == 3 || randomNumber == 4){
					return 2;
				}
				
				
			}
			default:{
				//scenario 4
				int randomNumber = rand.nextInt(12);
				
				if(randomNumber == 0){
					return 4;
				}
				
				if(randomNumber == 1){
					return 5;
				}
				
				if(randomNumber == 2 || randomNumber == 3){
					return 0;
				}
				
				return 3;
				
			}
		}
	}
	
	/*
	 * intoarce un string ce contine starea si actiunea
	 */
	
	public static String getActionStateString(Board board,int type,int left,int rotation){
		String key = "";
		key += type;
		key += board.height;
		key += board.width;
		key += board.toString();
		key += left;
		key += rotation;
		
		return key;
	}
	
	public static void learn(int scenario){
		Random rand = new Random();
	        //repet pentru un anumit numar de episoade
	    for (int i = 0; i < episodes; i++) { 
	    	bricksCount = 0;
	    	scores[i] = 0;
	    	// resetez tabla la stare initiala
	    	currentBoard = new Board(H,W);
	    	//generez o noua caramida
	    	currentBrick = generateBrickType(scenario);
	    	
	    	boolean gameOver = false;

	        do{
	        	states++;

	        	String state = "";
	    		state += currentBrick;
	    		state += H;
	    		state += W;
	    		state += currentBoard.toString();

	    		//Hashset set asigura ca fiecare stare este unica
	    		visitedStates.add(state);

	        	String action = eGreedy(currentBoard, currentBrick, eps);

	            int rotation = Integer.parseInt(action.substring(0,1));
	            int left = Integer.parseInt(action.substring(1,2));
            	

	            int score = currentBoard.putBrick(rotation, left, currentBrick);

	            scores[i] += score;

	            if(score >= 0){
	            	bricksCount++;
	            }else{
	            	break;
	            }
	            nextBrick = generateBrickType(scenario);
	            nextBoard = currentBoard;
	            
	            String nextState = "";
		    	nextState += nextBrick;
		    	nextState += H;
		    	nextState += W;
		    	nextState += nextBoard.toString();
	            
	            String aprim = eGreedy(nextBoard, nextBrick,eps);
	           
	            	Double q = 0.0;
	            	if(Q.containsKey(state+action)){
	            		q = Q.get(state+action);
	            	}
	                
	                Double qprim = 0.0;
	            	if(Q.containsKey(nextState+aprim)){
	            		qprim = Q.get(nextState+aprim);
	            	}

	                double r = (double)score;
	                if(r == 0.0)
	                	r = -0.04;
	                
	                Q.put(state+action, q + alpha*(r+delta*qprim-q));
	                
	                
	                currentBrick = nextBrick;
	            }while(bricksCount < 24) ;


	    	if(scores[i] > maxScore){
	            maxScore = scores[i];
	        }

	       	//afisez numarul de stari vizitate dupa fiecare 1000 de episoade si scorul maxim
	    	if(i%1000 == 999){
	    		System.out.println(visitedStates.size()+"\t\t"+maxScore);
	    	}
	        }
	}

	public static String argmaxQ(Board board,int brick){

		Vector<String> actions = board.validActions(brick);
		int nextRotate =0;
		int nextLeft =0;

        Double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actions.size(); i++) {
            String action = actions.get(i);

            int rotate = Integer.parseInt(action.substring(0, 1));
            int left = Integer.parseInt(action.substring(1, 2));
            String key = getActionStateString(board, brick,left ,rotate);
            Double value = 0.0;
            if(Q.contains(key)){
            	value = Q.get(key);
            }else{

            	Q.put(key, 0.0);
            }
            
            if (value >= maxValue){
                maxValue = value;
                nextRotate = rotate;
                nextLeft = left;

            }
        }

        String nextAction = ""+nextRotate+nextLeft;

        return nextAction;
	}
	
	public static Double maxQ(Board board,int brick) {
		Vector<String> actions = board.validActions(currentBrick);
        Double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actions.size(); i++) {
            String action = actions.get(i);
            int rotate = Integer.parseInt(action.substring(0, 1));
            int left = Integer.parseInt(action.substring(1, 2));
            String key = getActionStateString(board, brick,left ,rotate);
            Double value = 0.0;
            if(Q.contains(key)){
            	value = Q.get(key);
            }else{

            	Q.put(key, 0.0);
            }
            
            if (value > maxValue)
                maxValue = value;
        }
        return maxValue;
	}
	

	  public static String eGreedy(Board board,int brick,double eps){
		  Random rand = new Random();
		  double p = Math.abs(rand.nextDouble());
		  Vector<String> Aprim = board.validActions(brick);
		  
		  String a;
		  if(p<eps){
			 a = Aprim.get(rand.nextInt(Aprim.size()));
		  }else{
			 a = argmaxQ(board,brick); 
		  }
		  
		  return a;
	  }
	
	public static void main(String[] args) {

		scenario = Integer.parseInt(args[0]);
		eps = Double.parseDouble(args[1]);
		alpha =  Double.parseDouble(args[2]);
		delta = Double.parseDouble(args[3]);
		episodes = Integer.parseInt(args[4]);
		
		scores = new int[episodes];
		for(int i=0;i<episodes;i++){
			scores[i] = 0;
		}
		maxScore = Integer.MIN_VALUE;
		
		Q = new Hashtable<String, Double>();
		visitedStates = new HashSet<String>();
		scenario = Integer.parseInt(args[0]);
		currentBoard = new Board(H,W);
		int[] a = {1,2,3};
		switch(scenario){
			case 1:{
				H = 4;
				W = 4;
			}break;
			case 2:{
				H = 8;
				W = 5;
			}break;
			case 3:{
				H = 8;
				W = 5;
			}break;
			case 4:{
				H = 8;
				W = 6;
			}break;
		}
		


		//System.out.println("Afiseaza perechi de tipul nr_stari_vizitate/scor maxim");
		learn(scenario);


		//Codul de mai jos poate fi decomentat pentru testare
		/*
		int k=0;
		int max = Integer.MIN_VALUE;
		int maxmax = Integer.MIN_VALUE;
		int sum = 0;
		for(int i=0;i<scores.length;i++){
			if(scores[i]>0){
				k++;
				sum += scores[i];
			}
			if(max<scores[i]){
				max = scores[i];
			}
			
			if(maxmax<scores[i]){
				maxmax = scores[i];
			}
			
			if(i%1000 == 0){
				System.out.println("Episodes "+(i+1)+"-"+(i+1000)+" : "+max);
				max = -100;
			}
		}
		//System.out.println("Final: "+((double)100*(double)k/(double)episodes)+"%");
		System.out.println("MAxMAx: "+maxmax);
		System.out.println("Average: "+(double)sum/k);
		*/

	}
}
