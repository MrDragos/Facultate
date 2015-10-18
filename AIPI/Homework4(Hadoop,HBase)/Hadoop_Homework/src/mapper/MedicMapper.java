package mapper;

import java.io.IOException;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MedicMapper extends Mapper<Text, Text, Text, Text> {

	public void map(ImmutableBytesWritable medicFirstLastName, Text consultationValues, Context context)
			throws IOException, InterruptedException {
		//System.out.println(medicFirstLastName.toString());
		context.write(new Text(medicFirstLastName.get()), consultationValues);
	}

}
