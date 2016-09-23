//Sava 
//Dragos
//334CA

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * Clasa ce reprezinta un thread worker.
 */
class MapWorker extends Thread {
	MapWorkPool workPool;

	public MapWorker(MapWorkPool workpool) {
		this.workPool = workpool;
	}

	/**
	 * Procesarea unei solutii partiale. Aceasta poate implica generarea unor
	 * noi solutii partiale care se adauga in workpool folosind putWork().
	 * Daca s-a ajuns la o solutie finala, aceasta va fi afisata.
	 */
	void processPartialSolution(MapPartialSolution mps) {
	
		try {
			//Datorita clasei RandomAccessFile
			//citirea din fisier se va face in paralel
			RandomAccessFile file = new RandomAccessFile(mps.fileName, "r");
		
			String word = new String("");
			Integer poz = 0;
			byte c;
			
			file.seek(mps.start);
			file.read(mps.sequence);
			
			//verific daca inceputul fragmentului nu include cuvinte si 
			//incepe cu un delimitator
			//daca da , voi numara numarul de pozitii pana la primul cuvant
			//primul interval , cel cu 0 stiu sigur ca incepe cu cuvinte
			if(mps.start!=0){
				while(true){
					if (!isLetter(mps.sequence[poz]) && isLetter(mps.sequence[poz+1])){
						poz++;
						break;
					}
					poz++;
				}
			}
			
			//extrag civintele din fragmentul extras
			for(int i=poz; i<mps.size; i++){
				if (!isLetter(mps.sequence[i])){
					addWordNumber(word,mps);
					word = new String("");
					continue;
				}
				word += (char)mps.sequence[i];
				
			}

			//daca nu ma aflu la sfarsitul fisierului
			//voi citi si dupa sfarsitul fragmentului
			//pentru a ma asigura ca nu voi sfarsi fragmentul 
			//in mijlocul unui cuvant
			if (mps.end != mps.fileEnd-1){
				while(true){
					c = file.readByte();
					if (!isLetter(c)){
						addWordNumber(word,mps);
						break;
					}
					word += (char)c;
					
				}
			}
			
			
			//inchid fisierul
			file.close();

		
		} catch (FileNotFoundException e) {
			System.out.println("Fisierul "+mps.fileName+" nu exista !");
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	public void addWordNumber(String word,MapPartialSolution mps){
		
		int nrAppearences;
		word = word.toLowerCase();
		if(!word.equals("")){
			if((Main.map.get(mps.fileName)).containsKey(word)){
				nrAppearences = Main.map.get(mps.fileName).get(word);
				//System.out.println("BAU");
				Main.map.get(mps.fileName).put(word, nrAppearences+1);
			}else{
				Main.map.get(mps.fileName).put(word, 1);
			}
		}
	}
	
	//am considerat ca cifrele nu sunt cuvinte
	public boolean isLetter(byte c){
		Character letter = (char)c;
		//aici ma mai joc un pic cu delimitatorii
		//return Character.isLetter(letter) || (letter.equals('-'));
		//return Character.isAlphabetic(c);
		return Character.isLetter(c);
	}
	

	
	public void run() {
		//System.out.println("Thread-ul worker " + this.getName() + " a pornit...");
		while (true) {
			MapPartialSolution ps = workPool.getWork();
			if (ps == null)
				break;
			
			processPartialSolution(ps);
		}
		//System.out.println("Thread-ul worker " + this.getName() + " s-a terminat...");
	}

	
}
