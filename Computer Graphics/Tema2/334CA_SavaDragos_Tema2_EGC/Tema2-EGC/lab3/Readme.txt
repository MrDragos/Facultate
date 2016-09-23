Sava 
Dragos
334CA

I. Utilizare

a) Input Tastatura

Taste :

-Up - deplaseaza masina inainte pe axa Oz si creste viteza
-Down - deplaseaza masina inapoi pe axa Oz si scade viteza
-Right - deplaseaza masina la dreapta pe axa Ox
-Left - deplaseaza masina la stanga pe axa Ox
-'a' - accelereaza(mareste viteza masinii)
-'r' - reduce viteza masinii

b) Interfata Grafica

- in partea de sus a ecranului apar :
	-vietile ramase
	-distanta parcursa
	-timpul scurs de la inceperea jocului
	-scorul
 -in partea de jos apar cele trei benzi pe care se va deplasa masina

II. Implementare

Sistem opera : Windows 8.1
IDE : Visual Studio

Nota: in rezolvarea acestei teme am aut ca punct de pornire scheletul de cod din laboratorul 3

a)Clase:

Car :
	-in aceasta clasa am implementat cam tot ce tine de obiectul meu de tip masina
	-in constructor imi creez obiectul masina
	-am reprezentat masina sub forma unei prisme trapezoide


	Campuri:(doar cele mai importante)
		- lifesNumber	= numarul de vieti al masinii
		- circumRadius = raza cercului circumscris bazei masinii
		- center	=	centrul fetei care constituie baza(va avea mereu y = 0)
		- components = un vector de Object3D in care retin componentele
					   care imi alcatuiesc masina(asta in eventualitatea in 
					   care vrem ca masina sa fie mai complexa, alcatuita din imbinarea mai multor figuri,din pacate nu am mai reusit acest lucru)
		

	-Functii:
		-distance	-	calculeaza distanta dintre 2 puncte(2D sau 3D)
		-addCar_to_Visual2D - adauga obiectul masina incontextul vizual primit ca parametru
		-removeCar_from_Visual2D - sterge obiectul masina din incontextul vizual primit ca parametru
		-applyTransform	-	aplica transformarea asupra tuturor componentelor obiectului masina
		-collisionDetection - verifica daca masina noastra s-a lovit de un obstacol sau vehicul
		-outrunn - verifica daca masina a depasit un obiect sau vehicul fara sa se ciocneasca de acesta




Obstacle:

	-in aceasta clasa am implementat tot ce tine de inamic
	-Campuri:

		-x,y,z sunt coordonatele punctului de unde se incepe desenarea obiectului(start point)
		- center	=	centrul fetei care constituie baza(va avea mereu y = 0)
		- components = un vector de Object3D in care retin componentele
					   care imi alcatuiesc masina/obstacolul
		-tx,ty,tz - imi indica cu cat se va deplasa obiectul pe axele respective(umblu doar pe Oz)
		-type	-	imi indica tipul obiectului
				- 1 si 2 vor avea comportament de vehicule (viteza constanta , se aproprie mult mai
				  greu de masina decat restul obiectelor)
				- restul vor constitui obstacole

	-Functii:
		
		-in constructor imi construiesc obstacole in functie de tipul acestora
		-addObstacle_to_Visual2D - adauga obiectul in contextul vizual primit ca parametru
		-removeObstacle_from_Visual2D - sterge obiectul din incontextul vizual primit ca parametru
		-applyTranform - aplica functia Transform3D::applyTransform asupra tuturor obiectelor din vectorul components(obiecte ce compun inamicul)
		-distance - calculeaza distanta dintre 2 puncte(2D sau 3D)


- Clase pentru reprezentarea diferitelor forme geometrice (cred ca le-am dat nume destul de sugestive)
	-	Line3D	
	-	Rectangle3D
	-	Parallelipiped
	-	Pyramid (nota : piramida mea are baza patrat)
	-   Sphere
	-	TrapezoidPrism
	-   Cube

Toate clasele de mai sus extind clasa Object3D


Modificari efectuate in clasele din Framework:

- nu am efectuat modificari considerabile asupra claselor din framework



In main.cpp pe langa functiile care erau deja create in scheletul de cod am implementat urmatoarele functii :

-updateScore - actualizeaza scorul in contextul vizual de sus
-updateLives - actualizeaza textul cu vietile in contextul vizual de sus
-updateTime	 - actualizeaza ceasul la fiecare secunda
-updateDistance - actualizeaza constant portiunea de drum parcursa de masina
-drawRoad - deseneaza drumul
-drawGrass - dezeneaza iarba din marginile drumului
-gameOver - afiseaza textul de "Game Over" in contextul vizual de sus
-generateObstacles - genereaza obstacole pe care apo le adauga in vectorul obstacles




b)Designul obiectelor :

- masina este reprezentata prtintr-o prisma
- Vehicule:
	- tip 1 - este reprezentat printr-un cub
	- tip 2 - este reprezentat printr-un paralellipiped dreptunghic
-Obstacole :
	- tip 3 - este o piramida
	- tip 4 - este o sfera



c)Implementare

In acest segment voi descrie pe scurt cum am gandit realizarea acestui joc.



 Nota : de fiecare data cand creez un obstacol/vehicul , il adaug in vectorul "obstacles".In momentul in care un obiect
 iese din raza mea vizuala , il elimin din contextul vizual.

- pentru generare de obiecte Obstacle(aici includ si obiecte dinamice, si statice) am implementat
  functia generateObstacles() in main.cpp
- initial am vrut sa generez un nou obiect , la un interval fix de timp , dar din pacate am intampinat
  dificultati , asa ca am ales ca solutie de avarie sa imi generez la inceput toate obiectele , sa le pun intr-un vector , si sa am o variabila(nSize) care sa indice in onIdle() asupra cator elemente din vector aplic transformari(ea este incrementata la fiecare 7 secunde , deci la fiecare 7 secunde apare un nou
  obiect in cadrul meu vizual).

  -In functia onKey din main.cpp am implementat tot ce tine de deplasarea masinii:

  - up - masina se deplaseaza pe Oz inainte
  - down - masina se deplaseaza pe Oz inapoi
    Cele 2 functionalitati de mai sus(up si down) , nu erau absolut necesare.
    Totusi ele nu se limiteaza la simpla deplasare pe axa Oz ,  cimodifica si viteza
    masinii , creand senzatia de acceleratie.
  - left si right - deplasarea masinii pe axa Ox
  - 'a' - acceleratia(creste viteza)
  - 'r' - reduce viteza

 In principiu termenul folosit de mine de "deplasare" a masinii mele este un pic cam fortat.
 
 In cea mai mare parte(cu exceptia momentelor cand apa tastele) masina mea este statica iar
 restul obiectelor, vin spre ea(se translateaza pe axa Oz ).Pentru a simula viteza , incrementez
 la fiecare pas tz cu CarSpeed.Astfel se creeaza un efect realist , pe masura ce se aproprie de mine 
 obiectele se maresc.
 
 Obiectele statice au tz incrementat cu CarSpeed(care este variabil) , in timp ce obievtele dinamice
 au viteza constanta(tz este incrementat cu VehicleSpeed care nu se mai modifica).VehicleSpeed este mult
 mai mic decat CarSpeed , astfel obiectele dinamice se aprprie ma greu de mine ,  dand senzatia ca masinii ii este greu sa le ajunga din urma.
 
 Pentru tratarea coliziunilor am folosit uratorul algoritm :
 -fiecare obiect (aici includ si masina) are in clasa lui un camp numit center(care indica
  centrul bazei figurii) si un camp circumradius , care este raza cercului circumscris 
  figurii ce reprezinta fata de jos a obiectului(baza)
 - pentru a trata coliziunea dintre masina si celelalte obiecte verific daca suma dintre
   razele circumscrise cercurilor celor 2 obiecte este mai mare decat distanta dintre centrele bazelor
   daca este mai mare , atunci coliziunea s-a produs

Pentru a simula depasirea , la fel ca mai sus , ma orientez dupa pozitia centrului bazei fiecarui obiect.
Sa zicem ca c->tz este coordonata pe axa Oz a centrului masinii si o->tz a centrului obstacolului.
De asta se va ocupa functia overrun din clasa Car.Daca c->tz < o->tz inseamna ca masina mea  a trecet deja 
de obiect.Daca aceasta s-a intamplat fara existenta unei coliziuni intre cele 2 , atunci inseamna ca depasirea a avut loc cu succes si incrementez scorul.Din pacate , se pare a functioneaza doar,
cand "depasesc" obstacole detip piramida.

Jocul se termina cand parcurg cei 1000 de km.

 Pentru masina am folosit o proiectie perspectiva diferita , fata de cea folosita pentru sosea si obiecte.

III. Testare

IDE : Visual Studio 2013 
Sistem de operare : Windows 8.1
Display-ul pe care am facut testele avea rezolutia de 1366x768

IV. Probleme aparute

V. Continutul Arhivei

-lab3.sln este solutia Visual Studio a temei
-folderul lab3 contine:
	-framework-ul care era gata inclus in scheletul de laborator
	-main.cpp
	-Car.h
	-Obstacle.h
	-Cube.h
	-Paralellipiped.h
	-Rectangle3D.h
	-Pyramid.h
	-Sphere.h
	-Line3D.h
	-Rectangle3D.h

-restul folderelor din schelet...
-Readme

VI. Functionalitati
Functionalitati : 

-tema permite deplasarea masinii pe axele Ox si Oz
-am mai multe tipuri de obstacole
-fiecare obiect isi pastreaza banda
-am si vehicule dinamice(au viteza constanta, nu schimba banda)
-odata ce masina se deplaseaza pe axa Oz , accelerez (obiectele vin cu viteza mai mare spre mine
 simuland astfel un efect real)
-putem mari viteza si fara a ne deplasa masina pe oz , apasand tasta 'A'
-pentru reducerea vitezei se apasa tasta 'R'(reduce)
-obiectele sunt generate random la fiecare 7 secunde
-in partea de sus a ercanului sunt actualizate in mod dinamic distanta parcursa si timpul
-odata ce accelerez , distanta parcursa creste mult mai rapid
-de asemenea sunt actualizate scorul si numarul de vieti


VII.Altceva

In viitor ar fi util daca am avea un barem orientativ de notare pentru tema.
Astfel am putea sti 
