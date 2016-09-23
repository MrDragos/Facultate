//Sava 
//Dragos
//334CA

import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

	static String inputFile, outputFile;
	static Integer NT;	//number of threads
	static Integer D;	//dimensiunea fragmentelor
	static Integer ND;	//numar documente
	static String DOC;	//numele documentului pentru care se doreste determinare
						//gradului de plagiere
	static Double X;	//prag similitudine
	
	//aici voi tine rezultatele finale
	//key = numele directorului(fisierului) 
	//value = gradul de similitudine
	static Hashtable<String, Double> finalMap;
	static ArrayList<Map.Entry<String, Double>> finalResults;
	static ArrayList<String> finalDocuments;
	static ArrayList<Double> finalSimilarities;
	
	//key = numele direcotului
	//value = Hashtabel - key = cuvant
	//					- value = numar aparitii/frecventa
	static Hashtable<String, Hashtable<String, Integer>> map;
	static ArrayList<String> documents;
	static MapWorkPool mapWorkPool;
	static ReduceWorkPool reduceWorkPool;
	
	Hashtable<String, Integer> frequency(Hashtable<String, Integer> nrAppearances){
		
		Hashtable<String, Integer> frequencies = new Hashtable<String,Integer>();
		Integer size = nrAppearances.size();	//numar total de cuvinte din documente
		Integer frequency;
		
		for (Map.Entry<String, Integer> entry : nrAppearances.entrySet()){
			frequency = entry.getValue()/size*100;
			frequencies.put(entry.getKey(), frequency);
		}
		
		return frequencies;
	}
	

	public static void main(String args[]) throws IOException {
		
		NT = Integer.parseInt(args[0]);
		inputFile = args[1];
		outputFile = args[2];
		Integer numDocs;	//numar documente

		String line = null;
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
		//File output = new File(outputFile);
		DOC = bufferedReader.readLine();
		D = Integer.parseInt(bufferedReader.readLine());
		X = Double.parseDouble(bufferedReader.readLine());
		ND = Integer.parseInt(bufferedReader.readLine());
		
		documents = new ArrayList<String>(ND);
		
		for(int i=0;i<ND;i++)
			documents.add(bufferedReader.readLine());
		
		numDocs = documents.size();
		
		bufferedReader.close();
	
		MapWorker mapWorker;
		MapPartialSolution mps;
		mapWorkPool = new MapWorkPool(NT);
		MapWorker[] mapWorkers = new MapWorker[NT];

		//trebuie sa fac o functie de add pt cand bag chestii aici
		//si sa am grija sa incrementex nr de cuvinte
		map = new Hashtable<String,Hashtable<String, Integer>>();
		ArrayList<Integer> limits;
		//inainte sa pornesc workerii
		//voi initializa map-ul in care vor adauga toti chestii
		Integer fileSize;
		File file;
		for(int i=0;i<ND;i++){
			//fiecare document va avea o intrare in tabela
			map.put(documents.get(i), new Hashtable<String,Integer>());
			file = new File(documents.get(i));
			fileSize = (int) file.length();
			
			limits = new ArrayList<Integer>();
			
			//pentru fiecare fisier voi crea fragmente
			for(int j=0;j<fileSize;j += D-1){
				limits.add(j);
			}
			
			//verific daca ultimul fragment este pus in lista
			if(limits.get(limits.size()-1) != fileSize-1)
				limits.add(fileSize-1);
			
			
			for(int k =0;k<limits.size()-1;k++){
				
				//creez pentru fiecare fragmen o solutie partiala
				mps = new MapPartialSolution(documents.get(i), limits.get(k), limits.get(k+1),fileSize);
				//adaug solutia partiala in WorkPool
				mapWorkPool.putWork(mps);
			
			}
		}
		
		for (int i = 0; i < NT; i ++) {
			mapWorkers[i] = new MapWorker(mapWorkPool);
		}
		
		//pornesc workerii pt Map
		for (int i = 0; i < NT; i ++) {
			mapWorkers[i].start();
		}
		
		//fac join
		for (int i = 0; i < NT; i ++) {
			try {
				mapWorkers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

		ReduceWorker reduceWorker;
		ReducePartialSolution rps;
		reduceWorkPool = new ReduceWorkPool(NT);
		ReduceWorker[] reduceWorkers = new ReduceWorker[NT];
		String tempFileName;
		
		
		for(int i=0; i<ND; i++){
			//nu vreau sa calculez gradul de similitudine
			//al documentului cu el insusi
			tempFileName = documents.get(i);
			if(!DOC.equals(tempFileName)){
				rps = new ReducePartialSolution(tempFileName,map.get(DOC),map.get(tempFileName),X);
				reduceWorkPool.putWork(rps);
			}
		}

		for (int i = 0; i < NT; i ++) {
			reduceWorkers[i] = new ReduceWorker(reduceWorkPool);
		}
		
		//pornesc workerii pt Reduce
		for (int i = 0; i < NT; i ++) {
			reduceWorkers[i].start();
		}
		
		//fac join
		for (int i = 0; i < NT; i ++) {
			try {
				reduceWorkers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//System.out.println(map);
		
		//iau rezultatele din workerii pt Reduce
		//practic fac reuniunea rezultatelor
		
		finalDocuments = new ArrayList<String>();
		finalSimilarities = new ArrayList<Double>();
		
		for(int i=0;i<NT;i++){
			finalDocuments.addAll(reduceWorkers[i].getFileNames());
			finalSimilarities.addAll(reduceWorkers[i].getSimilarities());
		}
		
		finalMap = new Hashtable<String,Double>();
		
		for(int i=0;i<finalDocuments.size();i++){
			finalMap.put(finalDocuments.get(i), finalSimilarities.get(i));
		}
		
		//acum sortez Hashtabelul dupa similitudini
		
		finalResults = new ArrayList(finalMap.entrySet());
	    Collections.sort(finalResults, new Comparator<Map.Entry<String, Double>>(){

	         public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
	            return o2.getValue().compareTo(o1.getValue());
	        }});
		
		
		
//************************************************************************************
	//Scriu in fisier rezultatul

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
		bufferedWriter.write("Rezultate pentru: ("+DOC+")\n\n");
		
		for(int i=0;i<finalResults.size();i++){
			//sciu nume fisier
			bufferedWriter.write(finalResults.get(i).getKey()+ " ");
			//trunchez similitudinea
			BigDecimal bdSimilarity = new BigDecimal(finalResults.get(i).getValue() );
			bdSimilarity = bdSimilarity.setScale(3, RoundingMode.FLOOR);
			bufferedWriter.write("("+bdSimilarity+ "%)\n");
		}

		bufferedWriter.close();
		
		
	}
	
}
