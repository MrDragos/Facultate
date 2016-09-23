//Sava 
//Dragos
//334CA

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Clasa ce reprezinta un thread worker.
 */
class ReduceWorker extends Thread {
	ReduceWorkPool wp;

	ArrayList<String> fileNames;
	ArrayList<Double> similarities;
	
	public ReduceWorker(ReduceWorkPool workpool) {
		this.wp = workpool;
		this.fileNames = new ArrayList<String>();
		this.similarities = new ArrayList<Double>();
	}

	/**
	 * Procesarea unei solutii partiale. Aceasta poate implica generarea unor
	 * noi solutii partiale care se adauga in workpool folosind putWork().
	 * Daca s-a ajuns la o solutie finala, aceasta va fi afisata.
	 */
	void processPartialSolution(ReducePartialSolution rps) {
		
		Integer totalWordsD = 0; //numar total de cuvinte din DOC
		Integer totalWordsF = 0; // numar total de cuvinte din fisierul cu care vrem 
								 // sa verificam similitudinea
		Double fd;	//voi tine local frecventa unui cuvant din director
		Double ff;	//voi tine frecventa cuvantului in celalalt fisier
		
		Double similarity = (double) 0;
		
		//calculez numar total de cuvinte
		for(Map.Entry<String, Integer> entry : rps.documentWordsCount.entrySet()){
			totalWordsD += entry.getValue();
		}
		
		for(Map.Entry<String, Integer> entry : rps.fileWordsCount.entrySet()){
			totalWordsF += entry.getValue();
		}
		
		//calculez gradul de similaritate dintre fisiere
		for(Map.Entry<String, Integer> entryD : rps.documentWordsCount.entrySet()){
			for(Map.Entry<String, Integer> entryF : rps.fileWordsCount.entrySet()){
				//verific daca cuvantul este comun celor 2 documente
				//eu practic fac intersectia uvintelor din documente
				if(entryD.getKey().equals(entryF.getKey())){
					fd = frequency(entryD.getValue(), totalWordsD);
					ff = frequency(entryF.getValue(), totalWordsF);
				
					//System.out.println(fd + " ------ " +ff);
					similarity += fd*ff;
					
				}
			}
		}
		

		rps.similarity = similarity;


		
		if(similarity > rps.X){
		//	System.out.println(similarity);
			fileNames.add(rps.fileName);
			similarities.add(rps.similarity/100);
		}
	
	}
	
	public Double frequency(Integer appearances,Integer total){
		return ((double)appearances)/((double)total)*100;
	}
	
	public ArrayList<String> getFileNames() {
		return fileNames;
	}

	public ArrayList<Double> getSimilarities() {
		return similarities;
	}

	public void run() {
		//System.out.println("Thread-ul worker " + this.getName() + " a pornit...");
		while (true) {
			ReducePartialSolution ps = wp.getWork();
			if (ps == null)
				break;
			
			processPartialSolution(ps);
		}
		//System.out.println("Thread-ul worker " + this.getName() + " s-a terminat...");
	}

	
	
}