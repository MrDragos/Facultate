Sava
Dragos
342C4

I. Baza de date
Tabele:
	- abilities = tabela care realizeaza asocierea intre abilitati si utilizatori
	- ability = tabela abilitati
	- clinic = tabela clinici
	- investigation = tabela investigatii
	- medical_service = tabela servicii medicale
	- patient = tabela pacienti
	- raport = tabela rapoarte
	- reservation = tabela rezervari
	- schedule = tabela orar general(generic)
	- specific_schedule = tabela orare specifice
	- services = asociere intre clinici si servicii medicale
	- speciality = tabela specialitati
	- specialities = tabela de asociere intre intre utilizatori si specialitati
	- user = tabela utilizatori
	- timetable = tabela cu orarul pentru fiecare clinica in parte

II.Gestiune utilizatori

- Am implementat un sistem de control al drepturilor de acces al utilizatorilor în cadrul sistemului informatic pe baza rolurilor
- Ajutandu-ma de modelul din laborator , am implementat operatii de insert,delete,update asupra utilizatorilor pentru admin si super-admin

III.Modul pentru gestiunea resurselor umane

- Fiecare utilizator poate sa isi vada propriul orar accesand din meniu Personal Info -> Schedule
- HR-ul poate sa caute un utilizator , apeland User -> Search User :
	*aici completeaza nume,prenume,pozitie si cauta apasand butonul search
	*apeland general Schedule poate vedea orarul general
	*apasand Specific Schedule poate vedea orarul particularizat(tot aici poate lua concediu , am pus 2 DatePickere pt data de inceput si sfarsit a perioadei de concediu)
	* pentru a vedea concedii se apasa butonul Vacation

IV.Modul de operaţii financiar contabile
	*am implementat integral partea de interfata grafica pentru acest modul
	*fiecare utilizator isi poate vedea salariul 

V.Modul pentru gestiunea activităților operaționale

	A.Receptioner
		*poate adauga utilizatori in sistem Operation->Database Management->patient
		*pentru a inregistra un pacient , campul registered poate fi updatat la true
		*poate programa pacienti Operation->Database Management->reservation
		*se poate emite bon fiscal pentru o consultatie apasand butonul BonFiscal
	B.Asistent medical
		*poate vedea lista de pacienti integistrati/programati
	C.Medic
		*poate vedea lista de pacienti integistrati/programati la el
		*poate scrie si parafa rapoarte
		*poate consulta istoricul