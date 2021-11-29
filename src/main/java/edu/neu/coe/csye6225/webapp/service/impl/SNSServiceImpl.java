package edu.neu.coe.csye6225.webapp.service.impl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import edu.neu.coe.csye6225.webapp.service.SNSService;
import org.springframework.stereotype.Service;

@Service
public class SNSServiceImpl implements SNSService {
    private String snsTopicARN="arn:aws:sns:us-east-1:254269847591:email-verification-topic";
    private String accessKeyId="AKIATWM5J7ATTTV7DDQ3";
    private String secretAccessKey="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";

    private AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(accessKeyId, secretAccessKey)
    );

    private AmazonSNS amazonSNS = AmazonSNSClientBuilder.standard()
            .withCredentials(awsCredentialsProvider)
            .withRegion(Regions.US_EAST_1)
            .build();
    @Override
    public void publishMessage(String message) {
        PublishRequest request = new PublishRequest(snsTopicARN,message);
        amazonSNS.publish(request);
    }
}
