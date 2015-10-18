package mapper;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;

import general.Constants;

public class ConsultationSpecialityMapper extends TableMapper<Text, Text> {

	public void map(ImmutableBytesWritable investigationsId, Result investigationValue, Context context)
			throws IOException, InterruptedException {
			Text reportId = new Text(investigationValue.getValue(Constants.REFERENCE_FAMILY_COLUMN.getBytes(), Constants.REPORT_ID_COLUMN.getBytes()));
			String medicalServiceId = new String(investigationValue.getValue(Constants.REFERENCE_FAMILY_COLUMN.getBytes(), Constants.MEDICAL_SERVICE_ID_COLUMN.getBytes()));       		
			Configuration configuration = HBaseConfiguration.create();
			
			// get the medical service price from the corresponding HBase table 
			
			HTable medicalService = new HTable(configuration,Constants.MEDICAL_SERVICE_TABLE_NAME);
			Get get = new org.apache.hadoop.hbase.client.Get(medicalServiceId.getBytes());
			get.addColumn(Constants.DETAIL_FAMILY_COLUMN.getBytes(), Constants.PRICE_COLUMN.getBytes());
			Result result = medicalService.get(get);
			String price = new String(result.getValue(Constants.DETAIL_FAMILY_COLUMN.getBytes(), Constants.PRICE_COLUMN.getBytes()));
			//long billProductValue = Long.parseLong(price);
			//System.out.println(price);
			medicalService.close();
			String mapperValue = medicalServiceId + " "+ price;
			//key = reportID
			//
			
    	context.write(reportId, new Text(mapperValue.getBytes()));
    	
	}

}
