//Sava 
//Dragos
//334CA

import java.util.*;

class ReducePartialSolution {
	
	String docName;		//numele documentului pentru care calculez similitudinea
	String fileName;	//numele fisierului
	Hashtable<String, Integer> fileWordsCount;	//frecventa pentru fiecare cuvant
	Hashtable<String, Integer> documentWordsCount;	//nr aparitii pentru fiecare cuvant
	Double X;
	public Double similarity;
	

	public ReducePartialSolution() {

	}

	public ReducePartialSolution(String fileName,Hashtable<String, Integer> documentWordsCount ,Hashtable<String, Integer> filewordsCount,Double X) {
		this.fileName = fileName;
		this.documentWordsCount = (Hashtable<String, Integer>) documentWordsCount.clone();
		this.X = X;
		this.similarity = (double) 0;
		this.fileWordsCount =(Hashtable<String, Integer>) filewordsCount.clone();
	}

	public String toString() {
			return fileName + "("  + similarity + "%)";
	}
	

	public String getFileName(){
		return fileName;
	}
}