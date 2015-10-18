package reducer;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class MedicFileReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text medicFirstLastName, Iterable<Text> medicValues, Context context)
			throws IOException, InterruptedException {
		long revenue = 0;
		HashSet<String> medicalServices = new HashSet<String>();
		HashSet<String> patients = new HashSet<String>(); 
		for(Text medicValue:medicValues){
			String[] values = medicValue.toString().split(" ");
			patients.add(values[0]);
			revenue += Long.parseLong(values[1]);
			if(values.length > 2){
				for(int i=2;i<values.length;i++){
					medicalServices.add(values[i]);
				}
			}
		}
		
		String medicReport = patients.size()+ " "+medicalServices.size()+" "+revenue;	
		context.write(medicFirstLastName,new Text(medicReport.getBytes()));
	}

}
