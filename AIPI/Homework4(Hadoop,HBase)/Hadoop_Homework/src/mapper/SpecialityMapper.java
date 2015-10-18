package mapper;

import java.io.IOException;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SpecialityMapper extends Mapper<Text, Text, Text, Text> {

	public void map(ImmutableBytesWritable specialityName, Text consultationValues, Context context)
			throws IOException, InterruptedException {
		//System.out.println(specialityName.toString());
		context.write(new Text(specialityName.get()), consultationValues);
	}

}
