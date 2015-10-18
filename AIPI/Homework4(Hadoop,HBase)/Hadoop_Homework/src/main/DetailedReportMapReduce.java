package main;

import java.io.IOException;

import mapper.ConsultationSpecialityMapper;
import mapper.ConsultationValueMapper;
import mapper.MedicMapper;
import mapper.SpecialityMapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import reducer.ConsultationSpecialityValueReducer;
import reducer.ConsultationValueReducer;
import reducer.MedicFileReducer;
import reducer.SpecialityFileReducer;
import utilities.CustomFileInputFormat;
import general.Constants;

public class DetailedReportMapReduce {

	public static void main(String[] args) throws Exception {
		Configuration configuration = HBaseConfiguration.create();
		configuration.set(Constants.KEY_VALUE_SEPARATOR_PROPERTY_NAME, Constants.KEY_VALUE_SEPARATOR_VALUE);
		
		//START DETAIL REPORT
		
		Job consultationValue = Job.getInstance(configuration, Constants.CONSULTATION_VALUE_JOB_NAME);
		consultationValue.setJarByClass(DetailedReportMapReduce.class);
		Scan scan = new Scan();
		scan.setCaching(Constants.CACHING_VALUE);
		scan.setCacheBlocks(Constants.CACHE_BLOCKS_VALUE);
		TableMapReduceUtil.initTableMapperJob(
			Constants.INVESTIGATIONS_TABLE_NAME,
			scan,
			ConsultationValueMapper.class,
			Text.class,
			Text.class,
			consultationValue
		);
		consultationValue.setReducerClass(ConsultationValueReducer.class);
		consultationValue.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
		FileOutputFormat.setOutputPath(consultationValue, new Path(Constants.CONSULTATION_VALUE_OUTPUT_PATH));
		boolean billValueResult = consultationValue.waitForCompletion(true);
		if (!billValueResult)
			throw new IOException(Constants.CONSULTATION_VALUE_EXCEPTION_MESSAGE);
		
		Job userExpenses = Job.getInstance(configuration, Constants.RAPORT_DETALIAT_JOB_NAME);
		userExpenses.setJarByClass(DetailedReportMapReduce.class);

		userExpenses.setMapperClass(MedicMapper.class);
		userExpenses.setReducerClass(MedicFileReducer.class);
		userExpenses.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
		userExpenses.setOutputFormatClass(TextOutputFormat.class);

		
		userExpenses.setOutputKeyClass(Text.class);
		userExpenses.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(userExpenses, new Path(Constants.RAPORT_DETALIAT_OUTPUT_PATH));
		FileInputFormat.setInputPaths(userExpenses, new Path(Constants.CONSULTATION_VALUE_OUTPUT_PATH));
		userExpenses.setInputFormatClass(CustomFileInputFormat.class);
		boolean userExpensesResult = userExpenses.waitForCompletion(true);
		if (!userExpensesResult)
			throw new IOException(Constants.RAPORT_DETALIAT_EXCEPTION_MESSAGE);
		
		//END DETAIL REPORT
		
		//START SINTETIC REPORT
		
		Job consultationSpecialityValue = Job.getInstance(configuration, Constants.CONSULTATION_SPECIALITY_JOB_NAME);
		consultationSpecialityValue.setJarByClass(DetailedReportMapReduce.class);
		scan = new Scan();
		scan.setCaching(Constants.CACHING_VALUE);
		scan.setCacheBlocks(Constants.CACHE_BLOCKS_VALUE);
		TableMapReduceUtil.initTableMapperJob(
			Constants.INVESTIGATIONS_TABLE_NAME,
			scan,
			ConsultationSpecialityMapper.class,
			Text.class,
			Text.class,
			consultationSpecialityValue
		);
		consultationSpecialityValue.setReducerClass(ConsultationSpecialityValueReducer.class);
		consultationSpecialityValue.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
		FileOutputFormat.setOutputPath(consultationSpecialityValue, new Path(Constants.CONSULTATION_SPECIALITY_OUTPUT_PATH));
		boolean consultationSpecialityResult = consultationSpecialityValue.waitForCompletion(true);
		if (!consultationSpecialityResult)
			throw new IOException(Constants.CONSULTATION_SPECIALITY_EXCEPTION_MESSAGE);
		
		Job specialityExpenses = Job.getInstance(configuration, Constants.RAPORT_SINTETIC_JOB_NAME);
		specialityExpenses.setJarByClass(DetailedReportMapReduce.class);

		specialityExpenses.setMapperClass(SpecialityMapper.class);
		specialityExpenses.setReducerClass(SpecialityFileReducer.class);
		specialityExpenses.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
		specialityExpenses.setOutputFormatClass(TextOutputFormat.class);

		
		specialityExpenses.setOutputKeyClass(Text.class);
		specialityExpenses.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(specialityExpenses, new Path(Constants.RAPORT_SINTETIC_OUTPUT_PATH));
		FileInputFormat.setInputPaths(specialityExpenses, new Path(Constants.CONSULTATION_SPECIALITY_OUTPUT_PATH));
		specialityExpenses.setInputFormatClass(CustomFileInputFormat.class);
		boolean specialityExpensesResult = specialityExpenses.waitForCompletion(true);
		if (!specialityExpensesResult)
			throw new IOException(Constants.RAPORT_SINTETIC_EXCEPTION_MESSAGE);
		
		//END SINETIC REPORT
		
		
	}

}
