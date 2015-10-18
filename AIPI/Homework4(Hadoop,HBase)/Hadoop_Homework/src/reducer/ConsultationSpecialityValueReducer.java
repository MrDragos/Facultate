package reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import general.Constants;

public class ConsultationSpecialityValueReducer extends Reducer<Text, Text, Text, Text> {

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
      	HTable report1 = new HTable(configuration, general.Constants.REPORT_TABLE_NAME);
      	Get get1 = new Get(reportId.toString().getBytes());
      	get1.addColumn(general.Constants.REFERENCE_FAMILY_COLUMN.getBytes(), general.Constants.MEDIC_ID_COLUMN.getBytes());
		Result result1 = report1.get(get1);
		String personalMedicIdentifier = new String(result1.getValue(Constants.REFERENCE_FAMILY_COLUMN.getBytes(), general.Constants.MEDIC_ID_COLUMN.getBytes()));
		report1.close();
		
		//get the speciality id of the medic who has written the report and performed the medical services
      	//stim ca toate serviciile medicale fac parte dina ceeasi specializare
		String medicalServiceId = medicalServices.get(0);
		HTable report2 = new HTable(configuration, general.Constants.MEDICAL_SERVICE_TABLE_NAME);
      	Get get2 = new Get(medicalServiceId.getBytes());
      	get1.addColumn(general.Constants.REFERENCE_FAMILY_COLUMN.getBytes(), general.Constants.SPECIALITY_ID_COLUMN.getBytes());
		Result result2 = report2.get(get2);
		String specialityId = new String(result2.getValue(Constants.REFERENCE_FAMILY_COLUMN.getBytes(), general.Constants.SPECIALITY_ID_COLUMN.getBytes()));
		report2.close();
		
		//get the speciality name
      	HTable report3 = new HTable(configuration, general.Constants.SPECIALITY_TABLE_NAME);
      	Get get3 = new Get(specialityId.getBytes());
      	get1.addColumn(general.Constants.DETAIL_FAMILY_COLUMN.getBytes(), general.Constants.NAME_COLUMN.getBytes());
		Result result3 = report3.get(get3);
		String specialityName = new String(result3.getValue(Constants.DETAIL_FAMILY_COLUMN.getBytes(), general.Constants.NAME_COLUMN.getBytes()));
		report3.close();
		
		
		//am presupus ca in cadrul unei consultatii
		//un medic poate oferi mai multe servicii , dar toate facand parte din aceeasi specializare
		String reducerValue = personalMedicIdentifier+" "+consultationValue;

		
		//System.out.println("SFARSIT");
      	context.write(new Text(specialityName.getBytes()), new Text(reducerValue.getBytes()));
	}

}
