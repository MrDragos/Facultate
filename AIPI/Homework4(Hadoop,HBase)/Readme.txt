Tema4 AIPI 2014
Sava
Dragos
342C4

Baza de date HBase:

- medic - tabela pentru medici
- patient - tabela pentru pacient
- specialities - asociere intre tabela de medici si cea de specialitati
- speciality - tabela cu specializarile
- medical_service - tabela cu serviciile medicale
		  - fiecare serviciu medical va avea ca referinta , id-ul specializarii de care apartine
- report - tabela cu rapoartele medicale
         - ea are referinte catre tabelele medic si pacient
	 - aceasta poate fi perceputa ca tabela unei consultatii
- investigations - realizeaza asocieri intre report si medical_service(serviciile medicale efectuate in cadrul acelei consultatii)

In proiectarea bazei de date am avut in vedere urmatoarele:
- un medic poate avea mai multe specializari
- un raport medical poate fi scris(o consultatie poate fi efectuata) doar de un singur medic
- serviciile medicale aferente unei consultatii(report) fac parte din aceeasi specializare
- o consultatie poate avea mai multe servicii medicale

Pentru folosirea paradigmei MapReduce am realizat 4 mapperi si 4 reduceri , cate 2 pt fiecare tip de raport.

Pentru generarea raport_detaliat :
- ConsultationValueMapper - acesta va primi datele aferente regiunii sale din tabela "investigations"
		          - input : <K,V> = <id_investigatie,valoare_investigatie>
                          - output: <K,V> = <id_report,valoare serviciu medical + id serviciu medical>

- ConsultationValueReducer  - va calcula valoarea totala a consultatie pentru care s-a geenrat raportul respectiv
		            - tot aici se va obtine nume si prenumele medicului care a scris raportul precum si pacientul
                            - input : <K,V> = <id_raport,valoare_investigatii aferente raportului respectiv>
                            - output: <K,V> = <nume_prenume_medic,valoare consultatie + id servicii_medicale+ id pacient>
                            - rezultatul va fi scris in fisierul consultationValue                       

- MedicMapper             - outputul reduce-ului anterior va deveni input pentru aceasta noua mapare
		          - acesta va mapa datele din fisierul consultationValue

- MedicFileReducer - input : <K,V> = <nume_prenume_medic,valoare consultatie + id servicii_medicale+ id pacient>
                   - output: <K,V> = <nume_prenume_medic,numar pacienti+numar servicii + valoare totala>
	           - adun valoarea tuturor consultatiilor efectuaate de medic
                   - numar pacientii distincti(folosesc un HashSet)
                   - numar serviciile distincte
                   - scriu raportul in fisier


Pentru generarea raport_sintetic :

- ConsultationSpecialityValueMapper - acesta va primi datele aferente regiunii sale din tabela "investigations"
		          - input : <K,V> = <id_investigatie,valoare_investigatie>
                          - output: <K,V> = <id_report,valoare serviciu medical + id serviciu medical>
		          - in principiu face acelasi lucru ca si ConsultationMapper

- ConsultationSpecialityValueReducer- va calcula valoarea totala a consultatie pentru care s-a generat raportul respectiv
				    - tot aici se va obtine id-ul medicului care a scris raportul precum si id-ul specializarii
                                    - obtin numele specializarii(stiu ca toate serviciile de aici au aceeasi specializare)
		                    - input : <K,V> = <id_raport,valoare_investigatii aferente raportului respectiv>
		                    - output: <K,V> = <nume_specializare,valoare consultatie + id_medic>
		                    - rezultatul va fi scris in fisierul consultationSpecialityValue                       

- SpecialityMapper              - outputul reduce-ului anterior va deveni input pentru aceasta noua mapare
		          	- acesta va mapa datele din fisierul consultationSpecialityValue

- SpecialityFileReducer - input : <K,V> = <nume_specializare,valoare consultatie + id_medic>
		           		- output: <K,V> = <nume_specializare,numar medici + valoare totala>
			   			- adun valoarea tuturor consultatiilor efectuate pentru specializarea respectiva
		           		- numar medicii distincti(folosesc un HashSet)
		           		- scriu raportul in fisier

Observatii:

- am modificat claseleCustomFileInputFormat si CustomFileInputFormatRecorder din utilities pentru a fi compatibile 
  cu implementarea mea(tuplul <K,V> nu mai este de forma <Text,LongWritable> ci devine  <Text,Text>)
- Tema a fost realizata in ubuntu , pe masina virtuala de la laborator.

