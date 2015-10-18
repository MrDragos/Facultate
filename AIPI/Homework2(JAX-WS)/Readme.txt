Sava
Dragos
342C4

Tema a fost imlementata folosind JAX-WS.

Servicii web:
1. Reservation 	- este serviciul web oferit de  Medical Supply Broker(SERVER)
			   	- metodele lui vor apelate de Polyclinic(CLIENT)

2. Order       	- este serviciul web oferit de  Manufacturer(SERVER)
               	- metodele lui vor apelate de Medical Supply Broker(CLIENT)

3. Insurance    - este serviciul web oferit de  Health Insurance Provider(SERVER)
                - metodele lui vor apelate de Polyclinic(CLIENT)

4. Registration - este un serviciul web oferit tot de Medical Supply Broker(SERVER)
                - metodele lui vor apelate de Manufacturer(CLIENT)

Locațiile la care vor fi publicate serviciile web:

http://localhost:8080/reservation/Reservation?wsdl
http://localhost:8083/insurance/Insurance?wsdl
http://localhost:8082/order/Order?wsdl
http://localhost:8081/registration/Registration?wsdl

I. Health Insurance Provider

Baza de date:

- patient_state
- physicain_contract
- medical_service_state = starea unui serviciu medical
- expenses_history = cheltuieli

Metode apelate la distanta: 

int redeemMedicalService(int physicianId, int patientId, GregorianCalendar date, int medicalServiceId, double price);


II. Polyclinic:

pentru testarea serviciilor oferite de casa de asigurari decomentati si rulati metoda:
- testHealthInsuranceProvier()
- pentru testarea serviciilor oferite de broker si decomentati si rulati metoda:
- testMedicalSupplyBrokers

III. Medical Supply Broker

Baza de date:

- manufacturer
- medical_supply
- offer
- product_order
- unique_ids = tabela cu id-urile unice generate prntru rezervari

Metode apelate la distanta:

Pentru policlinica:

• getManufacturers
• getMedicalSupplies 
• makeReservation :
	- va genera un reservationID unic pe care il salveaza in tabela unique_ids
	- cu acesta va apelea metoda omonima din Manufacturer
	- primeste de la aceasta ca rezultat un Offer pe care il salveaza in baza de date locala
• cancelReservation :
	- apeleaza si intoarce rezultatul metodei omonime din Manufacturer
• getReservationStatus 
• makeOrder : 
	- apeleaza si intoarce rezultatul metodei omonime din Manufacturer
	- daca rezultatul e true , atunci va adauga noua comanda in tabela
• cancelOrder :
	- apeleaza si intoarce rezultatul metodei omonime din Manufacturer
	- daca rezultatul e true , atunci va marca comanda in baza de date locala ca fiind anulata
• getOrderStatus 

Pentru furnizori:

int registerManufacturer(string name, string description);
boolean unregisterManufacturer(int registrationId);

IV. Manufacturer



In momentul in care pornim serviciul pentru Manufacturer , trebuie introduse de la tastatura numele si descrierea producatorului respectiv.


Baza de date:

- manufacturer = tabela producator
- medical_supply = tabela produs medical
- product_order = tabela comenzi
- reservation = tabela rezervari

Metode apelate la distanta:

• getName: 
• getDescription: 
• getMedicalSupplies: 
• makeReservation: 
• cancelReservation: 
• makeOrder: 
• cancelOrder:

In principiu am respectat cerintele si verificarile/conditiile din enunt cand am implementat aceste metode.
Pentru mai multe detalii vedeti comeentariile din cod. 

Observatii:

- Pentru a testa apelul metodelor din Policlynic trebuie pornite atat Manufacturer cat si MedicalSupplyBroker
- Nu am mai adaugat teste pentru makeReservation, cancelReservation , makeOrder ,cancelOrder in main-ul Broker pentru ca ele  
oricum vor fi apelate de omoloagele lor din Polyclinic.