package com.prominentpixel.tyler.mapreduce.contactmerge;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
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

public class CCrawlContactMergeDriver extends Configured implements Tool {

    public enum CCrawlCounter
    {
        InputRecords,
        InvalidRecords,
        RecordsInserted,
        InvalidKeyFormatRecords,
        UpdatedRecords,
        UnParsedRecords,
        LimitExceddedRecords,
        DroppedRecords
    }

    @Override
    public int run(String[] args) throws Exception {


        Configuration configuration = new Configuration();
        configuration.set("DynamoDBEndPoint",args[0]);
        configuration.set("DynamoDBRegion",args[1]);

        //TODO:Only for Debugging remove before migrating to production
//        configuration.set("fs.defaultFS", "local");

        Job job = Job.getInstance(configuration);

        job.setJarByClass(CCrawlContactMergeDriver.class);
        job.setMapperClass(CCrawlContactMergeMapper.class);
        job.setNumReduceTasks(0);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        for(int index=3;index<args.length;index++){
            String inputPath = args[index];
            FileInputFormat.addInputPath(job,new Path(inputPath));
        }

        String outputPath = args[2];
        //TODO:Test against s3 deletion and update
        /*FileSystem fs = FileSystem.get(configuration);
        if(outputPath != null && !outputPath.isEmpty() && fs.exists(new Path(outputPath)))
        {
            fs.delete(new Path(outputPath),true);
        }*/
        FileOutputFormat.setOutputPath(job,new Path(outputPath));

        int status  = job.waitForCompletion(true) ? 0 : 1;

//        fs.close();

        return status                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ;
    }

    public static void main(String[] args) {

        try{

            if(args.length >= 3 ){

                String awsDynamoDBEndpoint=args[0];
                String awsRegion=args[1];

                if(null != awsDynamoDBEndpoint && !awsDynamoDBEndpoint.isEmpty()){

                    if(null != awsRegion && !awsRegion.isEmpty()){

                        int result = ToolRunner.run(new Configuration(), new CCrawlContactMergeDriver(), args);
                        System.out.println("job status ::"+result);
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
                System.err.println("USAGE::<AWS_DYNAMODB_ENDPOINT> <REGION> <OUTPUTPATH> <INPUTPATH1>..<INPUTPATH..N>");
                System.exit(-1);
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
