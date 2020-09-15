package com.prominentpixel.tyler.mapreduce;

import com.prominentpixel.tyler.aws.dynamodb.DynamoDBUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;

public class CCrawlDriver extends Configured implements Tool {

    public enum CCrawlCounter
    {
        InputRecords,           /** Number of Input records.*/
        InvalidRecords,         /** Number of Invalid records.*/
        RecordsInserted,        /** Number of Records inserted.*/
        InvalidKeyFormatRecords,/** Number of records with Invalid key format.*/
        UpdatedRecords,
        UnParsedRecords,
        LimitExceddedRecords,
        DroppedRecords
    }

    @Override
    public int run(String[] args) throws Exception {

        String dynamoDBEndPoint = args[0];
        String dynamoDBRegion = args[1];
        String dynamoDBTableName = args[2];

        Configuration configuration = new Configuration();
        configuration.set("DynamoDBEndPoint",dynamoDBEndPoint);
        configuration.set("DynamoDBRegion",dynamoDBRegion);

        //TODO:Only for Debugging remove before migrating to production
//        configuration.set("fs.defaultFS", "local");

        Job job = Job.getInstance(configuration);

        job.setJarByClass(CCrawlDriver.class);
        job.setMapperClass(CCrawlMapper.class);
        job.setNumReduceTasks(0);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        String outputPath = args[3];

        for(int index=4;index<args.length;index++){
            String inputPath = args[index];
            FileInputFormat.addInputPath(job,new Path(inputPath));
        }

        //TODO:Test against s3 deletion and update
       /* FileSystem fs = FileSystem.get(configuration);
        if(outputPath != null && !outputPath.isEmpty() && fs.exists(new Path(outputPath)))
        {
            fs.delete(new Path(outputPath),true);
        }
        fs.close();*/

        FileOutputFormat.setOutputPath(job,new Path(outputPath));

        int status  = job.waitForCompletion(true) ? 0 : 1;

        if(job.isSuccessful() && status==0){
            Long recordsInserted =   job.getCounters().findCounter(CCrawlCounter.RecordsInserted).getValue();
            DynamoDBUtils.writeCountersToDynamoDB(recordsInserted,dynamoDBEndPoint,dynamoDBRegion,dynamoDBTableName);
        }
         return status                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ;
    }

    public static void main(String[] args) {

        try{

            if(args.length >= 4 ){

                String awsDynamoDBEndpoint=args[0];
                String awsDynamoDBRegion=args[1];
                String awsDynamoDBTableName=args[2];

                if(null != awsDynamoDBEndpoint && !awsDynamoDBEndpoint.isEmpty()){

                    if(null != awsDynamoDBRegion && !awsDynamoDBRegion.isEmpty()){

                        if(null != awsDynamoDBTableName && !awsDynamoDBTableName.isEmpty()){
                            int result = ToolRunner.run(new Configuration(), new CCrawlDriver(), args);
                            System.out.println("job status ::"+result);
                        }
                        else{
                            System.err.println("Enter valid AWS DynamoDB Table Name");
                        }
                    }
                    else{
                        System.err.println("Enter valid AWS DynamoDB region");
                        System.exit(-1);
                    }
                }
                else{
                    System.err.println("Enter valid AWS DynamoDB Endpoint");
                    System.exit(-1);
                }
             }
            else{
                System.err.println("USAGE::<AWS_DYNAMODB_ENDPOINT> <REGION> <DynamoDBTableName> <OutputPath> <INPUTPATH1>..<INPUTPATH..N>");
                System.exit(-1);
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
