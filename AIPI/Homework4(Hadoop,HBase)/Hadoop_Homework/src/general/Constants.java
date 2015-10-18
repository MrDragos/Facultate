package general;

public interface Constants {

	final static String		FILE									= "file";
	final static String		TABLE									= "table";
	final static String		RESULTS_DESTINATION						= FILE; // available options: FILE, TABLE
	
	final static int 		REDUCE_TASKS_NUMBER						= 1;
	final static int		CACHING_VALUE							= 500;
	final static boolean	CACHE_BLOCKS_VALUE						= false;
	
	final static String		KEY_VALUE_SEPARATOR_PROPERTY_NAME		= "mapreduce.input.keyvaluelinerecordreader.key.value.separator";
	final static String 	KEY_VALUE_SEPARATOR_VALUE				= "\t";
	
	final static String		SPECIALITIES_TABLE_NAME					= "specialities";
	
	
	final static String		SPECIALITY_TABLE_NAME					= "speciality";
	final static String		PATIENT_TABLE_NAME						= "patient";
	final static String		REPORT_TABLE_NAME						= "report";
	final static String		INVESTIGATIONS_TABLE_NAME				= "investigations";
	final static String		MEDICAL_SERVICE_TABLE_NAME				= "medical_service";
	
	final static String		REFERENCE_FAMILY_COLUMN					= "reference";
	final static String		DETAIL_FAMILY_COLUMN					= "detail";
	
	final static String		REPORT_ID_COLUMN						= "report_id";
	final static String		MEDIC_ID_COLUMN							= "medic_id";
	final static String		PATIENT_ID_COLUMN						= "patient_id";
	final static String		MEDICAL_SERVICE_ID_COLUMN				= "medical_service_id";
	final static String		SPECIALITY_ID_COLUMN					= "speciality_id";
	final static String		PRICE_COLUMN							= "price";
	final static String		NAME_COLUMN								= "name";
	
	final static String		MEDIC_TABLE_NAME						= "medic";
	final static String		APPELLATION_FAMILY_COLUMN				= "appellation";	
	final static String		FIRST_NAME_COLUMN						= "first_name";
	final static String		LAST_NAME_COLUMN						= "last_name";
	
	final static String 	CONSULTATION_VALUE_JOB_NAME						= "consultationjob";
	final static String 	CONSULTATION_VALUE_OUTPUT_PATH					= "hdfs://localhost:9000/user/aipi2014/consultationValue";
	final static String 	CONSULTATION_VALUE_EXCEPTION_MESSAGE			= "Error running investigation value job!";
	
	final static String 	RAPORT_DETALIAT_JOB_NAME					= "raportdetaliatjob";
	final static String 	RAPORT_DETALIAT_OUTPUT_PATH					= "hdfs://localhost:9000/user/aipi2014/raportDetaliat";
	final static String 	RAPORT_DETALIAT_EXCEPTION_MESSAGE			= "Error running detailed report job!";

	final static String 	CONSULTATION_SPECIALITY_JOB_NAME					= "specialityjob";
	final static String 	CONSULTATION_SPECIALITY_OUTPUT_PATH					= "hdfs://localhost:9000/user/aipi2014/consultationSpecialityValue";
	final static String 	CONSULTATION_SPECIALITY_EXCEPTION_MESSAGE			= "Error running investigation value job!";
	
	final static String 	RAPORT_SINTETIC_JOB_NAME					= "raportsinteticjob";
	final static String 	RAPORT_SINTETIC_OUTPUT_PATH					= "hdfs://localhost:9000/user/aipi2014/raportSintetic";
	final static String 	RAPORT_SINTETIC_EXCEPTION_MESSAGE			= "Error running sintetic report job!";
	
}
