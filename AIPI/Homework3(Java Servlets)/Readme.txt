Sava
Dragos
342C4

Pentru rezolvarea temei am folosit ca punct d eplecare rezolvarea laboratorului 7(Servlets).

I. 	HomeServlet

- initial am dorit sa am cate un servlet pentru fiecare pagina dar am decis sa am unul singur
- in functie de parametrii request-ului HTTP , va afisa continutul dinamic(corespunzator paginii respective)

II.	Am 4 clase cu interfetele grafice: 

a) HomeGraphicUserInterface

- aceasta va fi pagina principala
- va contine 3 butoane(imagini) , fiecare redirectand catre una din celelalte pagini

b) MedicalServiceGraphicUserInterface

- aceasta va fi pagina pentru lista de servicii medicale
- pentru fiecare serviciu medicala am afisat denumirea,timpul de executie si pretul
- serviciile pot fi sortate in functie de specializare

c) MedicalTeamGraphicUserInterface

- aceasta va fi pagina pentru lista de medici
- pentru fiecare medic am afisat programul general de lucru, eventualele date existente
  in programul generic , precum si serviciile medicale oferite

d) LocationGraphicUserInterfirectace

- aceasta va fi pagina pentru lista de locatii
- pentru fiecare locatie am afisat o scurta descriere,programul d elucru, lista de medici si lista de specialitati


