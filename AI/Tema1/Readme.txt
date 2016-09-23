Sava
Dragos
342C4

Euristici:

g = numarul de mutari efectuate de la starea initiala
Pentru tranzitia dintr-o stare in alta , g-ul va creste cu 1

g(succesor(k)) = 1 +  g(k)

I. Prima euristica
- h1 = consta din numarul de blocuri ce se afla in calea celui rosu

Demonstratie admisibilitate:

- fie h* = numarul de mutari necesare pentru a debloca blocul rosu
  i)in cazul in care toate blocurile din calea lui "rosu" pot fi date la o parte(mutate) intr-o singura mutare fiecare, atunci 
    numarul de mutari necesare deblocarii lui rosu va fi egal cu numarul de obstacole => h = h*
 ii)altfel , daca pentru cel putin unul din blocurile obstacol numarul de mutari necesare mutarii acestuia de pe traiectoria lui
    rosu este mai mare ca 1 , atunci numarul total de mutari va fi strict mai mare decat numarul de obstacole => h* > h
    h nu va putea fi niciodata mai mare ca h*(nu poti muta 2 obstacole dintr-o mutare)

    Din i si ii => h(x) <= h*(x) , oricare ar fi x tabla de joc valida

II. A doua euristica
- h2 = este o pondere intre numarul de blocuri ce blocheaza calea lui "rosu"
       si gradul de libertate al acestor blocuri obstacol
  h2 = SUMA grad_libertate(x) , unde x apartine multimii de obstacole din calea lui "rosu"
       grad_libertate(x) = 1, daca poate fi mutat dintr-o singura mutare de pe linia lui rosu
                           2, daca este blocat de un alt bloc si vor fi necesare mai mult de 1 mutare

Demonstratie admisibilitate:

- si in acest caz h* va fi numarul total de mutari necesare pentru a debloca blocul rosu(de a ajunge la solutie)
  i)in cazul in care toate blocurile din calea lui "rosu" pot fi date la o parte(mutate) intr-o singura mutare fiecare, atunci
    h(x) = nr.de blocuri obstacol din calea lui rosu  , pt. ca fiecare bloc va avea grad_libertate = 1(am demonstrat egalitatea la punctul anterior)
    h*(x) = numarul de mutari necesare deblocarii lui rosu va fi egal cu numarul de obstacole => h = h*
 ii)altfel , fie f(k) = numarul de mutari necesare pentru a da la o parte blocul k din calea lui "rosu"
 			 pentru fiecare din aceste mutari , la randul lor vor fi probabil necesare alte mutari , si asa mai departe(recursiv) , 
 			 deci , putem deduce ca SUMA f(BLOCi)  <= h*, (0 <= i < numar_obstacole_rosu) , aceasta conditie va fi mereu adevarata

 			 Fie k un bloc obstacol pe calea lui rosu:
 			 - daca poate fi mutat de pe traiectoria lui "rosu" dintr-o mutare , atunci 
            grad_libertate(k) = 1 
            f(k) = 1
            deci f(k) = grad_libertate(k)
 			 -daca la randul lui e blocat de o piesa(piesa respectiva poate fi mutata dintr-o mutare) => nu poate fi mutat 
        de pe traiectoria lui "rosu" dintr-o mutare si atunci:
            grad_libertate(k) = 2
            f(k) = 2
            deci f = grad_libertate
 			 -daca la randul lui e blocat de o piesa si piesa respectiva e si ea blocata la randul ei de alte piese , atunci
            grad_libertate(k) = 2
            f(k) > 2
            deci f(k) > grad_libertate(k)

       Din cele de mai sus => f(k) >= grad_libertate(k) => Suma f >= h , dar mai sus am aratat ca SUMA f <= h* de unde rezulta
       ca h* >= h , deci euristica este admisibila


    Din i si ii => h(x) <= h*(x) , oricare ar fi x tabla de joc valida


Rezultate obtinute euristica1:

Se poate observa ca la toate testele algoritmul A* a fost mai bun ca BFS.
In unele cazuri DFS-ul a fost mai rapid , probabil datorita faptului ca se duce direct pe ramura cu solutia.
Acest lucru poate fi corectat prin aplicarea unei euristici mai bine informate.


+----------+----------+----------+----------+
|  board   |   DFS    |   BFS    |    A*    |
+----------+----------+----------+----------+
|         1|       861|       867|        94|
+----------+----------+----------+----------+
|         2|       374|      1069|       861|
+----------+----------+----------+----------+
|         3|        55|        85|        62|
+----------+----------+----------+----------+
|         4|      1116|      2231|      1115|
+----------+----------+----------+----------+
|         5|       812|       848|       326|
+----------+----------+----------+----------+
|         6|      1357|       517|       356|
+----------+----------+----------+----------+
|         7|       468|       601|       513|
+----------+----------+----------+----------+
|         8|       781|       791|       645|
+----------+----------+----------+----------+
|         9|      7373|      7935|      5659|
+----------+----------+----------+----------+
|        10|      5060|      7080|      4678|
+----------+----------+----------+----------+

Observatii: 

Executia ultimelor teste dureaza destul de mult(> 15 min).
Am atasat si o poza cu rezultatele obtinute.

Euristica2 este mult mai performanta.
Am testat si un Best-First-Search cu euuristica2 scoate rezultate mult mai bune decat A* cu euristica1.
Din pacate am un bug si A-star cu euristica2 nu functioneaza corect(nu mai am timp sa-l rezolv , sunt la limita cu timpul)