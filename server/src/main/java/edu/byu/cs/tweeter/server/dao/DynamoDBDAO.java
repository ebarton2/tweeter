package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.net.JsonSerializer;

public abstract class DynamoDBDAO {

    protected final static String REGION = "us-west-1";
    protected final static String USER_TABLE = "users";
    protected final static String AUTH_TABLE = "authentication";
    protected final static String FOLLOWS_TABLE = "follows";
    protected final static String STORY_TABLE = "story";
    protected final static String FEED_TABLE = "feed";

    protected final DynamoDB dynamoDB;

    public DynamoDBDAO() {
        AmazonDynamoDB client = AmazonDynamoDBAsyncClientBuilder.standard().withRegion(REGION).build();
        dynamoDB = new DynamoDB(client);
    }



    protected Table getTable(String tableType) {
        Table table = dynamoDB.getTable(tableType);
        return table;
    }

    protected String setS3ImageFile(String image, String alias) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(REGION).build();
        String bucket = "twibberimages";
        String filename = alias + "_image";
        byte[] data = Base64.getDecoder().decode(image);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(data.length);
        metadata.setContentType("image/jpeg");

        PutObjectRequest request = new PutObjectRequest(bucket, filename, inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(request);
        String imageURL = s3.getUrl(bucket, filename).toString();
        System.out.println(imageURL);
        return imageURL;
    }


}