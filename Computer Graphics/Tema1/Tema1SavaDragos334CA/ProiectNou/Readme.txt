Sava
Dragos
334CA

I. Utilizare

a) Input Tastatura

Taste :

-Up - deplaseaza nava inainte pe directia dorita
-Down - deplaseaza nava in sens invers directiei de inaintare(se misca cu spatele)
-Right - se roteste la dreapta
-Left - se roteste la stanga
-'a' - activeaza arma
-'z' - dezactiveaza arma

b) Interfata Grafica

- in partea de sus a ecranului apar vietile ramase si scorul
 -in partea de jos apare arena de joc

II. Implementare

Sistem opera : Windows 8.1
IDE : Visual Studio

Nota: in rezolvarea acestei teme am aut ca punct de pornire scheletul de cod din laboratorul 2 

a)Clase:

Spaceship :
	-in aceasta clasa am implementat cam tot ce tine de obiectul meu de tip nava
	-Campuri:
		-Point2d - center = centrul navetei
		-bool - activated = arma activata sau nu
			  - outOfBounds = indica daca nava a iesit sau nu din arena
			  - collided  = indica daca nava s-a ciocnit sau nu cu un obiect
			  
		-vector <Object2D*> components = in el tin toate obiectele care immi 	compun nava
		-vector <Point2D*> weaponPoints = in el tin puncte de pe laturile 
			triunghiului care imi defineste arma

		-float - rotate_speed = viteza de rotatie a navei
			  - translate_speed = viteza de translatie a navei
			  - angle = unghiul de rotaie al navei

		-int 	- upperLimit,lowerLimit,leftLimit,rightLimit = limitele arenei
				- lives_counter = numarul de vieti ramase(initial este 5)
		-Visual2d v2d = cadrul vizual in care se afla nava

	-Functii:
		-in constructor immi definesc obiectele 2D care imi compun nava
		-setBounds - seteaza limitele terenului
		-activateWeapon - seteaza campul activated pe true si incetineste viteza de deplasare a navei
		-deactivateWeapon - seteaza campul activated pe false si aduce viteza 	de deplasare a navei inapoi in limitele normale
		-distance - calculeaza distanta euclidiana dintre 2 puncte
		-isOut - verifica daca nava a iesit sau nu din teren(arena)
		-collisionDetection - verifica daca au aparut coliziuni intre nava si inamic
		-applyTranform - aplica functia Transform2D::applyTransform asupra tuturor obiectelor din vectorul components(obiecte ce compun nava)
		-lineEquation - calculeaza ecuatia dreptei data de 2 puncte si genereaza puncte pe aceasta
		-generateWeaponPoints - folosindu-se de lineEquation genereaza puncte pe laturile triunghiului care ii defineste arma si le adauga in lista


Enemy:
	-in aceasta clasa am implementat tot ce tine de inamic
	-Campuri:
		-letItLive = indica daca un obiect mai trebuie sau nu sa apare pe ecran(este false cand inamicul este lovit de arma navei)
		-center - centrul inamicului
		-radius - raza cercului care este descris de miscare circulara a inaicului in jurul propriului centru
		-type - indica tipul de inamic
		-float 	- rotate_speed = viteza de rotatie a inamicului
			  	- translate_speed = viteza de translatie a inamicului
		-outOfBounds - este true daca inamicul iese din spatiul de joc


	-Functii:
		-in constructor imi construiesc tipul de inamic in functie de tipul 
		 acestuia
		-applyTranform - aplica functia Transform2D::applyTransform asupra tuturor obiectelor din vectorul components(obiecte ce compun inamicul)
		-distance - calculeaza distanta dintre 2 puncte
		-weaponCollision- verifica daca inamicul se ciocneste cu arma
		-isOut - verifica daca inamicul a iesit sau nu din limitele in care era destinat sa se deplaseze
		-setBounds - seteaza limitele din care nu trebuie sa iasa inamicul
		

Modificari efectuate in clasele din Framework:

-DrawingWindow:
	-am suprascris functia addObject2D_to_Visual2D astfel incat sa poata primi ca parametri obiecte de tip Enemy si Spaceship
	-am adugat functia removeEnemy_from_Visual2D(Enemy *e, Visual2D *v) ,
	 care elimina din contextul vizual "v" toate obiectele de tip Object2D ce compun inamicul "e".


********
Alte functii...

In main.cpp pe langa functiile care erau deja create in scheletul de cod am implementat urmatoarele functii :

-updateScore - actualizeaza scorul in contextul vizual de sus
-updateLives - actualizeaza textul cu vietile in contextul vizual de sus
-startingEnemies - initializeaza inamicii ce apar la inceputul jocului 
-generateEnemies(cadran) - genereaza obiecte de tip inamic in cadranul specificat ca parametru
-cadranNava - decide in ce cadran se afla nava
-cadranInamic - alege in ce cadran sa fie generat inaicul(mereu aleg coltul opus celui in care se afla nava)
-activateWeapon - adauga arma in contextul vizual
-deactivateWeapon - sterge arma din contextul vizual



b)Designul obiectelor :

Nava este alcatuita din :
-un cerc mare
-o hexagrama
-in interiorul acesteia se afla un cerc plin
-iar in afara ei un triunghi(care indica care este partea din fata a navei)

Inamicul de tip 1 este alcatuit din:
-este alcatuit dintr-un cerc si 4 triunghiuri atasate la distante egale cu unul din varfuri
 pe circumferinta acestuia

Inamicul de tip 2 este alcatuit din:
-3 patrate fiecare asezat in interiorul celuilalt

Inamicul de tip 3 este alcatuit din:
-un patrat
-4 triunghiuri aflate in exteriorul patratului
-fiecare latura a patratului contituie o latura a unui triunghi

Asupra fiecarui obiect inamic voi aplica o miscare de rotatie in jurul propriului centru.
Astfel acestea vor descrie prin miscare lor un cerc, fapt ce imi va folosi la tratarea coliziunilor.

c)Implementare

In acest segent voi descrie pe scurt cum am gandit realizarea acestui joc.

-In functia onKey din main.cpp am implementat tot ce tine de deplasarea navei

-Pentru a verifica daca obiectul meu iese sau nu din spatiul de joc
 	verific la fiecare pas daca distanta dintre centrul obiectului(nava sau inamic) si una din margini
 	este mai mica decat raza.Daca urmeaza za iasa atunci nu mai incrementez tx,respectiv ty.

 - Pentru a verifica coliziunile dintre nava si inamici folosesc urmatorul mini-algoritm:
 	-dupa cum am precizat inainte , fiecare obiect inamic descrie prin miscarea sa de rotatie un cerc
 	- acest cerc are o raza
 	- eu verific daca dinstanta dintre centrul navei si al inamicului (in urma miscarii de translatie)
 	  este mai mica decat suma razelor celor 2 cercuri(cercul navei si cel al inamicului)
 	- daca e mai mica atunci exista coliziune
 	- daca nu , atunci nu exista coliziune
 	- daca exista coliziune , decrementez numarul de vieti

 - Pentru a verifica coliziunile dintre arma si inamici voi proceda astfel :
   - folosinduma de functia lineEquation(care imi genereaza ecuatia undei drepte data de 2 puncte)
     imi definesc puncte la distante egale pe laturile triunghiului care imi defineste arma
   - apoi pe acelasi principiu ca mai sus , verific daca distanta euclidiana dintre fiecare din aceste
     puncte si centrul inamicului este mai mica decat raza acestuia.
   - daca exista coliziune , atunci sterg obiectul inamic , si adaug la variabila total_score punctajul
     aferent fiecarui tip de obiect inamic

 Nota : de fiecare data cand creez un inamic , il adaug in vectorul "enemies".In momentul in care un inamic
 este lovit de arma , el este scos din acest vector.

 d)Comportament:

 -inamicul de tip 3 urmareste nava , se deplaseaza spre ea
 -inamicul de tip 2 copie miscarile navei(directia de deplasarea a acesteia)
 -inamicul de tip 1 se deplaseaza doar pe diagonala si se opreste in perete


e)Generare:
-un inamic nou este initiat odata la 10 secunde
-ei apar din coltul opus celui in care se afla nava
-initial sunt 4
-tipul este random

III. Testare

IDE : Visual Studio 2013 
Sistem de operare : Windows 8.1
Display-ul pe care am facut testele avea rezolutia de 1366x768

Programul recunoaste coliziunea dintre nava si inamic.Am efectuate mai multe teste pt aceasta.Totusi din motivele enuntate mai jos, am efectuat cateva modificari.
Am mai lasat prin cod unele afisari(cout<<...) coomentate in caz ca s-ar dori testarea unor functii/utilitati.

IV. Probleme aparute

Am intampinat pronleme in updatarea vietilor.Desi programul eu recunostea coliziunea dintre nava si inamic , imi decrementa cu mai mult de o unitate vietile.

Pentru a rezolbva aceast aproblema am presupus ca nava  se poate coicni o singura data cu acelasi inamic.

In clasa SpaceShip am implementat functia collisionDetection care primea ca parametru un obiect de tip Enemy si verifica daca exista coliziune intre acesta
si nava noastra.

Nota : dupa o anumita perioada de timp , cand spatiul de joc devine mai incarcat cu inamici ,
	   obiectele incep sa se deplaseze mai greu.De asemenea , viteza de rotatie incetineste.Nu am reusit sa imi dau seama de ce...

V. Continutul Arhivei

-lab2.sln este solutia Visual Studio a temei
-folderul lab2 contine:
	-framework-ul care era gata inclus in scheletul de laborator
	-Spaceship.h (header in care am implementat clasa Spaceship)
	-Enemy.h (header in care am implementat clasa Enemy)
	-fisierul sursa main.cpp
-restul folderelor din schelet...
-Readme




VI. Functionalitati
Functionalitati : 

-tema prevede o arena in care imi apar obiectele
-am un obiect de tip Spaceship(care reprezinta nava) si care se poate deplasa conform specificatiilor temei
-am specificat la sectiunea 1(Utilizare) cu poate fi controlata nava folosind tastele
-obiectul nu imi iese din ecran
-arma vine echipata cu o arma(care apare sub forma unui obiect) care poate fi activata/dezactivata in functie de preferintele utilizatorului
-am 3 tipuri de inamici care arata si se comporta diferit
-in momentul contactului dintre nava si un inamic se actualizeaza textul corespunzator vietilor din partea de sus a ercanului
-cand numarul de vieti este ehal cu 0 in partea de sus a ercanului va aparea "Game Over"



VII.Altceva

In viitor ar fi util daca am avea un barem de notare pentru tema.
