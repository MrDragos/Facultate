Create database if not exists Grupa342C4_SavaDragos_HealthInsuranceProvider;
use Grupa342C4_SavaDragos_HealthInsuranceProvider;

#Health Insurance Provider

CREATE TABLE IF NOT EXISTS physician_contract (
    id                      INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    physician_id            INT(3) UNSIGNED NOT NULL,
    expiration_date         DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS patient_state (
    id                      INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    patient_id              INT(5) UNSIGNED NOT NULL,
    insurance         		VARCHAR(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS  medical_service_state(
    id                      INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    medical_service_id      INT(3) UNSIGNED NOT NULL,
    discount           		INT(5) NOT NULL,
    supported         		VARCHAR(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS  expenses_history(
    id                      INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    medical_service_id      INT(3) NOT NULL,
    discount_date           DATE NOT NULL,
    expense           		INT(5) NOT NULL
);

INSERT INTO physician_contract(physician_id,expiration_date) VALUES(1,'2015-12-11');
INSERT INTO physician_contract(physician_id,expiration_date) VALUES(2,'2016-12-11');
INSERT INTO physician_contract(physician_id,expiration_date) VALUES(3,'2014-12-11');
INSERT INTO physician_contract(physician_id,expiration_date) VALUES(4,'2015-2-21');
INSERT INTO physician_contract(physician_id,expiration_date) VALUES(5,'2015-11-10');
INSERT INTO physician_contract(physician_id,expiration_date) VALUES(6,'2015-10-11');
INSERT INTO physician_contract(physician_id,expiration_date) VALUES(7,'2016-3-11');
INSERT INTO physician_contract(physician_id,expiration_date) VALUES(8,'2012-12-11');
INSERT INTO physician_contract(physician_id,expiration_date) VALUES(9,'2014-2-21');
INSERT INTO physician_contract(physician_id,expiration_date) VALUES(10,'2015-11-10');


INSERT INTO patient_state(patient_id,insurance) VALUES(1,'T');
INSERT INTO patient_state(patient_id,insurance) VALUES(2,'F');
INSERT INTO patient_state(patient_id,insurance) VALUES(3,'T');
INSERT INTO patient_state(patient_id,insurance) VALUES(4,'F');
INSERT INTO patient_state(patient_id,insurance) VALUES(5,'T');
INSERT INTO patient_state(patient_id,insurance) VALUES(6,'T');
INSERT INTO patient_state(patient_id,insurance) VALUES(7,'F');
INSERT INTO patient_state(patient_id,insurance) VALUES(8,'T');
INSERT INTO patient_state(patient_id,insurance) VALUES(9,'F');
INSERT INTO patient_state(patient_id,insurance) VALUES(10,'T');

INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(1,5000,"T");
INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(2,3500,"F");
INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(3,2500,"T");
INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(4,500,"F");
INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(5,1500,"T");
INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(6,5400,"T");
INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(7,500,"T");
INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(8,2500,"F");
INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(9,3500,"T");
INSERT INTO medical_service_state(medical_service_id,discount,supported) VALUES(10,1500,"T");

INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(1,'2013-11-10',300);
INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(2,'2013-11-10',500);
INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(3,'2013-11-10',400);
INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(4,'2013-11-12',100);
INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(5,'2013-11-13',500);
INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(6,'2013-11-14',300);
INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(7,'2012-11-15',400);
INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(8,'2014-11-16',800);
INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(9,'2012-11-17',900);
INSERT INTO expenses_history(medical_service_id,discount_date,expense) VALUES(10,'2011-11-17',600);



