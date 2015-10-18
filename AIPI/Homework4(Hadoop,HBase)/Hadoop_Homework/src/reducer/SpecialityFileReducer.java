package reducer;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class SpecialityFileReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text specialityName, Iterable<Text> specialityValues, Context context)
			throws IOException, InterruptedException {

		long revenue = 0;
		HashSet<String> specialityMedics = new HashSet<String>();

		for(Text specialityValue:specialityValues){
			String[] values = specialityValue.toString().split(" ");
			specialityMedics.add(values[0]);
			revenue += Long.parseLong(values[1]);
			//System.out.println("sp: "+values[0]+" "+values[1]);
		}
		
		String specialityReport = specialityMedics.size()+ " "+revenue;	
		context.write(specialityName,new Text(specialityReport.getBytes()));
	}

}
