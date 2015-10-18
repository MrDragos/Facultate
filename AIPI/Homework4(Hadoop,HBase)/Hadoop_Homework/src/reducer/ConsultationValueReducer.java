package reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import general.Constants;

public class ConsultationValueReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text reportId, Iterable<Text> medicalServicesValues, Context context)
			throws IOException, InterruptedException {
		long consultationValue = 0;
		ArrayList<String> medicalServices = new ArrayList<String>();
		for (Text medicalServiceValue: medicalServicesValues) {
			// calculate total consultation value
			String[] data = medicalServiceValue.toString().split(" ");
			//System.out.println(data[0]+" "+data[1]);
			medicalServices.add(data[0]);
			consultationValue += Long.parseLong(data[1]);
		}
		//remove duplicates
		HashSet<String> distinctServices = new HashSet<String>();
		distinctServices.addAll(medicalServices);
		medicalServices.clear();
		medicalServices.addAll(distinctServices);
		//Integer servicesNumber = distinctServices.size();
		
		Configuration configuration = HBaseConfiguration.create();
      	
		//get the personal identifier of the medic who has written the report
      	HTable report = new HTable(configuration, general.Constants.REPORT_TABLE_NAME);
      	Get get1 = new Get(reportId.toString().getBytes());
      	get1.addColumn(general.Constants.REFERENCE_FAMILY_COLUMN.getBytes(), general.Constants.MEDIC_ID_COLUMN.getBytes());
		Result result1 = report.get(get1);
		String personalMedicIdentifier = new String(result1.getValue(Constants.REFERENCE_FAMILY_COLUMN.getBytes(), general.Constants.MEDIC_ID_COLUMN.getBytes()));
		report.close();
		
		//get the personal identifier of the patient who has written the report
      	HTable report2 = new HTable(configuration, general.Constants.REPORT_TABLE_NAME);
      	Get get3 = new Get(reportId.toString().getBytes());
      	get3.addColumn(general.Constants.REFERENCE_FAMILY_COLUMN.getBytes(), general.Constants.PATIENT_ID_COLUMN.getBytes());
		Result result3 = report2.get(get3);
		String personalPatientIdentifier = new String(result3.getValue(Constants.REFERENCE_FAMILY_COLUMN.getBytes(), general.Constants.PATIENT_ID_COLUMN.getBytes()));
		report2.close();
		
      	// get the first name and the last name of the medic with the personal identifier previously obtained
      	
		HTable medic = new HTable(configuration, Constants.MEDIC_TABLE_NAME);
		Get get2 = new Get(personalMedicIdentifier.getBytes());
		get2.addColumn(general.Constants.APPELLATION_FAMILY_COLUMN.getBytes(),Constants.FIRST_NAME_COLUMN.getBytes());
		get2.addColumn(Constants.APPELLATION_FAMILY_COLUMN.getBytes(),Constants.LAST_NAME_COLUMN.getBytes());
		Result result2 = medic.get(get2);
		
		
		String medicFirstLastName = new String(result2.getValue(Constants.APPELLATION_FAMILY_COLUMN.getBytes(), Constants.FIRST_NAME_COLUMN.getBytes()))+
									" "+
									new String(result2.getValue(Constants.APPELLATION_FAMILY_COLUMN.getBytes(),Constants.LAST_NAME_COLUMN.getBytes()));
		medic.close();
		String reducerValue = personalPatientIdentifier+" "+consultationValue;
		//un serviciu medical poate fi realizat de un medic in cadrul altei consultatii
		//asa ca vom salva si id-urile distincte
		for(String serviceId:medicalServices){
			reducerValue += " ";
			reducerValue += serviceId;
		}
		//System.out.println("SFARSIT");
      	context.write(new Text(medicFirstLastName.getBytes()), new Text(reducerValue.getBytes()));
	}

}
