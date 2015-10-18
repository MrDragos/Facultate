Create database if not exists Grupa342C4_SavaDragos;
use Grupa342C4_SavaDragos;

CREATE TABLE IF NOT EXISTS Timetable (
    id                      INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    Monday                	VARCHAR(11),
    Tuesday                 VARCHAR(11),
    Wednesday             	VARCHAR(11),
    Thursday               	VARCHAR(11),
	Friday					VARCHAR(11),
	Saturday				VARCHAR(11),
	Sunday					VARCHAR(11)
);

CREATE TABLE IF NOT EXISTS Location (
    id                      INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name                 VARCHAR(40) NOT NULL,
    address                 VARCHAR(40) NOT NULL,
    phone_number            VARCHAR(10) NOT NULL,
    email                	VARCHAR(25) NOT NULL,
    timetable_id            INT(3) UNSIGNED NOT NULL,
	FOREIGN KEY(timetable_id) REFERENCES Timetable(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS speciality (
    id                INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	name              varchar(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS medical_service (
    id                      INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name              		varchar(100) NOT NULL,
    execution_time			INT(2) NOT NULL,
    price					DOUBLE(5,2) NOT NULL,
	speciality_id           INT(3) UNSIGNED NOT NULL,
	FOREIGN KEY(speciality_id) REFERENCES speciality(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Medic (
    id                      		INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    first_name                      varchar(20),
	last_name                       varchar(20),
	speciality                      varchar(40)
);

CREATE TABLE IF NOT EXISTS medic_location_assoc(
    id                      INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	medic_id      			INT(5) UNSIGNED,
    location_id        		INT(5) UNSIGNED,
	FOREIGN KEY(medic_id) REFERENCES Medic(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(location_id) REFERENCES Location(id) ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS evaluation(
    id                      INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	medic_id      			INT(5) UNSIGNED,
    value        			DOUBLE(3,2) NOT NULL,
	FOREIGN KEY(medic_id) REFERENCES Medic(id) ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS Patient (
	id                      		INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    first_name                      varchar(20) NOT NULL,
	last_name                       varchar(20) NOT NULL,
    address                         varchar(30),
    phone_number                    varchar(10) NOT NULL,
    email                           varchar(35),
    registered                      varchar(1)
);

CREATE TABLE IF NOT EXISTS generic_schedule (
    id                      				INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    weekday                					VARCHAR(25) NOT NULL,
    time_slot                				VARCHAR(11) NOT NULL,
    location             					VARCHAR(25),
    medic_id								INT(3) UNSIGNED ,
    FOREIGN KEY(medic_id) REFERENCES Medic(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS specific_schedule (
    id                                      INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    specific_date                           DATE NOT NULL,
    weekday                                 VARCHAR(25),
    time_slot                               VARCHAR(11),
    medic_id                				INT(3)  UNSIGNED ,
    holiday                                 VARCHAR(1),
    FOREIGN KEY(medic_id) REFERENCES Medic(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comment(
    id                                      INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    content                                 VARCHAR(255),
    medic_id                				INT(3)  UNSIGNED ,
    patient_id                				INT(3)  UNSIGNED ,
    FOREIGN KEY(medic_id) REFERENCES Medic(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(patient_id) REFERENCES Patient(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Reservation(
    id                              INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    location_id                     INT(3) UNSIGNED NOT NULL,
    patient_id                      INT(5) UNSIGNED NOT NULL,
    medic_id                        INT(3) UNSIGNED NOT NULL,
    reservation_date                DATE NOT  NULL,
    hour                         	VARCHAR(5) NOT NULL,
    state							VARCHAR(15) NOT NULL,
    FOREIGN KEY(location_id) REFERENCES Location(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(medic_id) REFERENCES Medic(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(patient_id) REFERENCES Patient(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reservation_medical_service_assoc(
    id                      INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	medic_id      			INT(5) UNSIGNED,
    reservation_id        	INT(5) UNSIGNED,
	FOREIGN KEY(medic_id) REFERENCES Medic(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(reservation_id) REFERENCES Reservation(id) ON UPDATE CASCADE ON DELETE CASCADE
);

#analize medicale efectuate de asistenti
#TODO
CREATE TABLE IF NOT EXISTS medical_report(
    id                          INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    patient_first_name              varchar(20) not null,
    patient_last_name               varchar(20) not null,
    medic_first_name                varchar(20) not null,
    medic_last_name                 varchar(20) not null,
    reservation_date                Date not null,
    medical_service                 varchar(20) not null,             
    istoric                         varchar(255),
    simptoms                        varchar(255),
    diagnosis                      	varchar(255),
    recomandations                  varchar(255),
    paraph_code                     varchar(6)
);

#consultatii/investigatii medicale efectuate de medici
CREATE TABLE IF NOT EXISTS medical_test(
    id                              INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    medical_service_name				VARCHAR(25) NOT NULL,
    location_id                     INT(3) UNSIGNED NOT NULL,
    patient_id                      INT(5) UNSIGNED NOT NULL,
    date               				DATE NOT  NULL,
    result							VARCHAR(25) NOT NULL,
    FOREIGN KEY(location_id) REFERENCES Location(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(patient_id) REFERENCES Patient(id) ON UPDATE CASCADE ON DELETE CASCADE
);

insert into Timetable(Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday) values('09:45-19:00','09:30-19:45','09:00-20:15','08:15-20:15','09:45-20:00','09:30-16:00','08:15-18:45');
insert into Timetable(Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday) values('09:30-18:15','09:00-17:15','08:30-17:00','09:15-18:00','09:00-19:45','08:45-20:15','07:15-20:00');
insert into Timetable(Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday) values('08:00-17:15','09:00-19:00','07:30-18:45','09:45-16:30','07:00-20:00','07:15-17:45','07:30-19:15');
insert into Timetable(Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday) values('07:45-19:45','09:30-16:00','08:30-18:00','09:30-19:15','07:45-18:45','09:00-17:15','07:15-16:00');
insert into Timetable(Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday) values('09:45-17:15','07:45-20:30','07:00-18:00','07:45-20:00','09:30-18:15','08:45-20:45','07:45-19:00');
insert into Timetable(Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday) values('08:45-20:15','07:30-17:30','09:00-20:30','07:15-17:15','08:15-19:45','09:30-16:30','08:15-19:30');

insert into Location(name,address,phone_number,email,timetable_id) values('Bucuresti','Bucuresti','0758954433','clinica1@gmail.com',1);
insert into Location(name,address,phone_number,email,timetable_id) values('Brasov','Brasov','0758954443','clinica2@gmail.com',2);
insert into Location(name,address,phone_number,email,timetable_id) values('Tulcea','Tulcea','0758955433','clinica3@gmail.com',3);
insert into Location(name,address,phone_number,email,timetable_id) values('Constanta','Constanta','0758754433','clinica4@gmail.com',4);
insert into Location(name,address,phone_number,email,timetable_id) values('Iasi','Iasi','0758954473','clinica5@gmail.com',5);
insert into Location(name,address,phone_number,email,timetable_id) values('Cluj','Cluj','0758954403','clinica6@gmail.com',6);

insert into speciality(name) values('stomatologie');
insert into speciality(name) values('oftalmologie');
insert into speciality(name) values('neurologie');
insert into speciality(name) values('dermatologie');
insert into speciality(name) values('psihologie');
insert into speciality(name) values('ortopedie-traumatologie');
insert into speciality(name) values('o.r.l');

insert into medical_service(name,execution_time,price,speciality_id) values('Aviz psihologic',30,80.0,5);
insert into medical_service(name,execution_time,price,speciality_id) values('Consiliere psihologica adult',30,135.0,5);
insert into medical_service(name,execution_time,price,speciality_id) values('Consiliere psihologica copil',30,135.0,5);
insert into medical_service(name,execution_time,price,speciality_id) values('Consultatie psihologie la domiciliu',30,195.0,5);
insert into medical_service(name,execution_time,price,speciality_id) values('Examen logopedic',30,120.0,5);
insert into medical_service(name,execution_time,price,speciality_id) values('Profil psihologic - QI',30,80.0,5);
insert into medical_service(name,execution_time,price,speciality_id) values('Psihodiagnostic',30,120.0,5);
insert into medical_service(name,execution_time,price,speciality_id) values('Psihoterapie',30,130.0,5);
insert into medical_service(name,execution_time,price,speciality_id) values('Psihoterapie de cuplu',30,180.0,5);

insert into medical_service(name,execution_time,price,speciality_id) values('Blink reflex',30,10.0,3);
insert into medical_service(name,execution_time,price,speciality_id) values('EMG - examen cu ac',30,300.0,3);
insert into medical_service(name,execution_time,price,speciality_id) values('EMG - viteza de conducere nervoasa',30,240.0,3);
insert into medical_service(name,execution_time,price,speciality_id) values('PEV auditive',30,270.0,3);
insert into medical_service(name,execution_time,price,speciality_id) values('PEV somestezice',30,270.0,3);
insert into medical_service(name,execution_time,price,speciality_id) values('PEV vizuale',30,270.0,3);
insert into medical_service(name,execution_time,price,speciality_id) values('Teste de sistem nervos autonom',30,230.0,3);

insert into medical_service(name,execution_time,price,speciality_id) values('Anestezie locala',30,10.0,1);
insert into medical_service(name,execution_time,price,speciality_id) values('Consultatie de control medic primar/ specialist',30,80.0,1);
insert into medical_service(name,execution_time,price,speciality_id) values('Consultatie de specialitate (Conservativa,chirurgie,ortodontie)',30,90.0,1);
insert into medical_service(name,execution_time,price,speciality_id) values('Consultatie ortodontie',30,100.0,1);
insert into medical_service(name,execution_time,price,speciality_id) values('Disjunctor',30,150.0,1);
insert into medical_service(name,execution_time,price,speciality_id) values('Tratament urgenta (pansament calmant, drenaj endodontic)',30,100.0,1);
insert into medical_service(name,execution_time,price,speciality_id) values('Albire endodontica',30,300.0,1);
insert into medical_service(name,execution_time,price,speciality_id) values('Albire profesionala',30,650.0,1);

insert into medical_service(name,execution_time,price,speciality_id) values('Biometrie',30,60.0,2);
insert into medical_service(name,execution_time,price,speciality_id) values('Biomicroscopie',30,75.0,2);
insert into medical_service(name,execution_time,price,speciality_id) values('Blefarochalasis - cura chirurgicala',30,670.0,2);
insert into medical_service(name,execution_time,price,speciality_id) values('Camp vizual',30,90.0,2);
insert into medical_service(name,execution_time,price,speciality_id) values('Consult oftalmologic complet, inclusiv prescriere de ochelari',30,130.0,2);
insert into medical_service(name,execution_time,price,speciality_id) values('Cura chirurgicala a colobomului',30,900.0,2);

insert into medical_service(name,execution_time,price,speciality_id) values('Dermatoscopie/leziune',30,40.0,4);
insert into medical_service(name,execution_time,price,speciality_id) values('Electrocoagulare leziune/veruca sau vegetatii veneriene',30,110.0,4);
insert into medical_service(name,execution_time,price,speciality_id) values('Electrocoagulare/excizie verucoame sau vegetatii mari/molluscum contagiosum/cmp',30,110.0,4);
insert into medical_service(name,execution_time,price,speciality_id) values('Eliminare tatuaj/cmp',30,130.0,4);
insert into medical_service(name,execution_time,price,speciality_id) values('Examinare cu lampa Wood',30,50.0,4);
insert into medical_service(name,execution_time,price,speciality_id) values('Excizie/electroexcizie tumora maligna unica',30,250.0,4);
insert into medical_service(name,execution_time,price,speciality_id) values('Excizie/electroexcizie/leziune; tumora benigna, chist sebaceu',30,120.0,4);

insert into medical_service(name,execution_time,price,speciality_id) values('Imobilizari in fasa elastica/membru(nou-nascut, sugar)',30,55.0,6);
insert into medical_service(name,execution_time,price,speciality_id) values('Taiere aparat gipsat membru inferior',30,60.0,6);
insert into medical_service(name,execution_time,price,speciality_id) values('Taiere aparat gipsat membru inferior (copil 1-14 ani)',30,40.0,6);
insert into medical_service(name,execution_time,price,speciality_id) values('Taiere aparat gipsat membru superior',30,40.0,6);
insert into medical_service(name,execution_time,price,speciality_id) values('Taiere aparat gipsat membru superior (copil 1-14 ani)',30,30.0,6);
insert into medical_service(name,execution_time,price,speciality_id) values('Taiere aparat gipsat membru superior / inferior (copil 0-1 an)',30,20.0,6);
insert into medical_service(name,execution_time,price,speciality_id) values('Talpa sustinere aparat gipsat',30,50.0,6);

insert into medical_service(name,execution_time,price,speciality_id) values('Pansament nazal',30,20.0,7);
insert into medical_service(name,execution_time,price,speciality_id) values('Prelevare biopsie',30,90.0,7);
insert into medical_service(name,execution_time,price,speciality_id) values('Punctie sinus maxilar',30,75.0,7);
insert into medical_service(name,execution_time,price,speciality_id) values('Redresarea piramidei nazale post traumatism recent',30,85.0,7);
insert into medical_service(name,execution_time,price,speciality_id) values('Sectionare sinechie septo - turbinala',30,35.0,7);
insert into medical_service(name,execution_time,price,speciality_id) values('Spalatura auriculara',30,35.0,7);
insert into medical_service(name,execution_time,price,speciality_id) values('Spalatura - aspiratie nazala',30,35.0,7);

insert into Medic(first_name,last_name,speciality) values('Luiza','Voinea','stomatologie');
insert into Medic(first_name,last_name,speciality) values('Laura','Cristea','stomatologie');
insert into Medic(first_name,last_name,speciality) values('Mihaela','Ghita','stomatologie');
insert into Medic(first_name,last_name,speciality) values('Nicusor','Stanciu','oftalmologie');
insert into Medic(first_name,last_name,speciality) values('Sava','Dragos','oftalmologie');
insert into Medic(first_name,last_name,speciality) values('Tanase','Gula','neurologie');
insert into Medic(first_name,last_name,speciality) values('Tanasel','Fotea','neurologie');
insert into Medic(first_name,last_name,speciality) values('Alexandru','Popescu','dermatologie');
insert into Medic(first_name,last_name,speciality) values('Ion','Cristea','dermatologie');
insert into Medic(first_name,last_name,speciality) values('Mihaela','Stanga','psihologie');
insert into Medic(first_name,last_name,speciality) values('Gabriel','Dumitriu','psihologie');   
insert into Medic(first_name,last_name,speciality) values('Marius','Oprea','ortopedie-traumatologie');
insert into Medic(first_name,last_name,speciality) values('Dragos','Calin','ortopedie-traumatologie');
insert into Medic(first_name,last_name,speciality) values('Cristian','Simion','o.r.l');
insert into Medic(first_name,last_name,speciality) values('Ana','Toader','o.r.l');

insert into evaluation(medic_id,value) values(1,3.57);
insert into evaluation(medic_id,value) values(2,4.57);
insert into evaluation(medic_id,value) values(3,3.50);
insert into evaluation(medic_id,value) values(4,4.50);
insert into evaluation(medic_id,value) values(5,4.77);
insert into evaluation(medic_id,value) values(6,4.75);
insert into evaluation(medic_id,value) values(7,4.87);
insert into evaluation(medic_id,value) values(8,4.50);
insert into evaluation(medic_id,value) values(9,4.65);
insert into evaluation(medic_id,value) values(10,4.57);
insert into evaluation(medic_id,value) values(11,4.97);
insert into evaluation(medic_id,value) values(12,4.17);
insert into evaluation(medic_id,value) values(13,4.58);
insert into evaluation(medic_id,value) values(14,4.57);
insert into evaluation(medic_id,value) values(15,5.00);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',1);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',1);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',1);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',1);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Brasov',1);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',1);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',1);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',2);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',2);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',2);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Brasov',2);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',2);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',2);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',2);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',3);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',3);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',3);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',3);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Brasov',3);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Brasov',3);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Brasov',3);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Brasov',4);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Brasov',4);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',4);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',4);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',4);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',4);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',4);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',5);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',5);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',5);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',5);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',5);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',5);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',5);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',6);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',6);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',6);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',6);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',6);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',6);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',6);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',7);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',7);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',7);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',7);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',7);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',7);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',7);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',8);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',8);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',8);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',8);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',8);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',8);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',8);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',9);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',9);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',9);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',9);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',9);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',9);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',9);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',10);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',10);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',10);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',10);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',10);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',10);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',10);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Iasi',11);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Iasi',11);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Iasi',11);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Iasi',11);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',11);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',11);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',11);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Cluj',12);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',12);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',12);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Constanta',12);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Iasi',12);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Iasi',12);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Iasi',12);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Tulcea',13);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Tulcea',13);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',13);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Tulcea',13);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Tulcea',13);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Tulcea',13);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Tulcea',13);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Tulcea',14);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Tulcea',14);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Tulcea',14);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Tulcea',14);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Tulcea',14);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Tulcea',14);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Tulcea',14);

insert into generic_schedule(weekday,time_slot,location,medic_id) values('luni','10:00-17:15','Bucuresti',15);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('marti','09:30-16:00','Bucuresti',15);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('miercuri','09:00-17:00','Bucuresti',15);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('joi','09:45-16:30','Bucuresti',15);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('vineri','09:45-18:15','Bucuresti',15);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('sambata','09:30-16:00','Bucuresti',15);
insert into generic_schedule(weekday,time_slot,location,medic_id) values('duminica','08:15-16:00','Bucuresti',15);


insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-08-24','-','-',1,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-08-24','-','-',1,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-08-24','-','-',1,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-07-23','-','-',2,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-07-24','-','-',2,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-07-25','-','-',2,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-04-23','-','-',3,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-04-24','-','-',3,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-02-13','-','-',4,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-02-14','-','-',4,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-25','-','-',4,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-26','-','-',4,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-22','-','-',5,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-23','-','-',5,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-03-23','-','-',6,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-02-10','-','-',7,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-02-11','-','-',7,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-02-12','-','-',7,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-02-13','-','-',7,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-25','-','-',8,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-26','-','-',8,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-27','-','-',8,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-28','-','-',8,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-23','-','-',10,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-27','-','-',12,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-28','-','-',12,'T');

insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-24','-','-',13,'T');
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-25','-','-',13,'T');
    
insert into specific_schedule(specific_date,weekday,time_slot,medic_id,holiday) values('2015-01-23','-','-',14,'T');
