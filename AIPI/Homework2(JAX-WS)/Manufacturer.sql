
Create database if not exists Grupa342C4_SavaDragos_Manufacturer;
use Grupa342C4_SavaDragos_Manufacturer;

CREATE TABLE IF NOT EXISTS Manufacturer (
    id                      INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name                    VARCHAR(35) NOT NULL,
    description             VARCHAR(255) NOT NULL,
    state                   VARCHAR(12)
);

CREATE TABLE IF NOT EXISTS Reservation (
    id                      INT(10) UNSIGNED  PRIMARY KEY NOT NULL,
    medical_supply_name     VARCHAR(25) NOT NULL,
    medical_supply_quantity VARCHAR(100) NOT NULL,
    state					VARCHAR(8) NOT NULL,
    manufacturer_id      	INT(3) UNSIGNED NOT NULL, 
    date                    DATE,
    FOREIGN KEY(manufacturer_id) REFERENCES manufacturer(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Product_Order(
	id                     	INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	reservation_id          INT(10) UNSIGNED NOT NULL,
	state					VARCHAR(8) NOT NULL,
	date                    DATE,
	FOREIGN KEY(reservation_id) REFERENCES reservation(id) ON UPDATE CASCADE ON DELETE CASCADE
); 


CREATE TABLE IF NOT EXISTS Medical_Supply (
    id                      INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name                    VARCHAR(35) NOT NULL,
    manufacturer_id         INT(3) UNSIGNED NOT NULL,
    description             VARCHAR(255),
    price                   INT(5) NOT NULL,
    stock                   INT(5) NOT NULL,
    execution_time          int(3) NOT NULL,
    FOREIGN KEY(manufacturer_id) REFERENCES manufacturer(id) ON UPDATE CASCADE ON DELETE CASCADE
 );

INSERT INTO manufacturer(name,description,state) VALUES('Theranos','Theranos is a privately held health technology and medical laboratory services company based in Palo Alto, California','registered');
INSERT INTO manufacturer(name,description,state) VALUES('D. Medical Industries','D. Medical Industries is a publicly traded holding company, headquartered in Israel','unregistered');
INSERT INTO manufacturer(name,description,state) VALUES('Synovis Life Technologies','Synovis Life Technologies is a Minneapolis-based manufacturer of various medical devices.','registered');
INSERT INTO manufacturer(name,description,state) VALUES('Biotronik' ,'Biotronik is a privately held multinational biomedical technology company headquartered in Berlin, Germany.','unregistered');
INSERT INTO manufacturer(name,description,state) VALUES('Technicare','Technicare, formerly known as Ohio Nuclear, was a maker of CT, DR and MRI scanners and other medical imaging equipment.','registered');


INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Medical scissors',1,'It is a medical supply',100,50,20);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Stethoscope',1,'It is a medical supply',200,40,30);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Reflex hammer',1,'It is a medical supply',300,30,40);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Syringe aspirator',1,'It is a medical supply',400,20,50);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Electrocardiography machine',1,'It is a medical supply',500,10,60);

INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Ear syringe',2,'It is a medical supply',400,40,70);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Blood lancet',2,'It is a medical supply',300,20,60);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Ear plug',2,'It is a medical supply',600,30,40);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Cane',2,'It is a medical supply',700,40,20);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Blood transfusion equipment',2,'It is a medical supply',100,50,20);

INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Surgical mesh',3,'It is a medical supply',800,50,25);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Ergometer',3,'It is a medical supply',100,50,10);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Scoliometer',3,'It is a medical supply',100,10,30);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Goniometer',3,'It is a medical supply',100,10,22);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Glasse',3,'It is a medical supply',10,50,23);

INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Glasses',4,'It is a medical supply',120,20,20);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Contact Lenses',4,'It is a medical supply',140,20,20);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Anaesthesiometer',4,'It is a medical supply',120,20,40);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Endotrach tube',4,'It is a medical supply',110,20,20);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Anaesthesia mask',4,'It is a medical supply',180,20,20);

INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Cart',5,'It is a medical supply',100,10,10);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Bed',5,'hospital beds, cribs, or bassinets; including mattresses, overlays, pillows, and bumpers',200,20,20);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Antiseptic wipes',5,'It is a medical supply',350,30,12);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Otology sponges',5,'It is a medical supply',500,50,2);
INSERT INTO medical_supply(name,manufacturer_id,description,price,stock,execution_time) VALUES('Catheter',5,'It is a medical supply',1200,50,2);


