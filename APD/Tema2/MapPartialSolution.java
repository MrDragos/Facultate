//Sava 
//Dragos
//334CA

import java.util.Hashtable;
import java.io.*;

class MapPartialSolution {
	
	Integer start;	//offset de inceput secventa
	Integer end;	//offset de sfarsit secventa
	Integer fileEnd;	//sfarsit fisier
	Integer size;
	byte sequence[];
	String fileName;	//numele fisierului din care provine sectiunea mea
	Hashtable<String , Integer> wordsIndex;	//numar aparitii pentru fiecare cuvant
	
	public MapPartialSolution(String fileName,Integer startOffset,Integer endOffset,Integer fileSize) {
		this.fileName = fileName; 
		this.start = startOffset;
		this.end = endOffset;
		this.size = endOffset - startOffset;
		//as fi pus automat size = D , dar exista sansa ca ultima secventa
		//sa aibe o lungime mai mica decat D
		sequence = new byte[size];
		fileEnd = fileSize;
	}

	public String toString() {
		return fileName;
	}
}