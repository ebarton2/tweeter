package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.net.JsonSerializer;

public abstract class DynamoStatusDAO extends DynamoDBDAO {

    private final static String SQS_URL_POST_STATUS = "https://sqs.us-west-1.amazonaws.com/340065643546/PostStatus";

    protected List<Status> getPage(String alias, Status lastStatus, int limit, String type) throws ParseException {
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#a", "alias");

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":ua", alias);

        List<Status> statuses = new ArrayList<>();

        do {
            QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#a = :ua")
                    .withNameMap(nameMap).withValueMap(valueMap)
                    .withScanIndexForward(false).withMaxResultSize(limit);
            // Changed .withScanIndexForward(true) to false
            if (lastStatus != null) {
                querySpec = querySpec.withExclusiveStartKey(
                        new PrimaryKey("alias", alias,
                                "time_stamp", getStatusTimeEpoch(lastStatus)));
            }
            Iterator<Item> iterator;
            ItemCollection<QueryOutcome> items;
            Item item;
            Table table = getTable(type);

            items = table.query(querySpec);
            iterator = items.iterator();

            while(iterator.hasNext()) {
                item = iterator.next();
                statuses.add(JsonSerializer.deserialize(item.getString("status"), Status.class));
            }
            if(statuses.size() == 0) {
                return statuses;
            }
            lastStatus = statuses.get(statuses.size()-1);

        } while (lastStatus == null);
        //Collections.reverse(statuses);
        return statuses;
    }

    public boolean getPostStatus(String alias, Status status, int limit) throws ParseException {
        Table storyTable = getTable(STORY_TABLE);
        status.getUser().setImageBytes(null);
        String statusJSON = JsonSerializer.serialize(status);
        long epoch = getStatusTimeEpoch(status);

        // This directly deals with the post to a user's story
        PutItemOutcome outcome = storyTable.putItem(new Item()
                .withPrimaryKey("alias", alias,
                        "time_stamp", epoch)
                .withString("status", statusJSON));
        // Successfully updates the Story DB

        // Now we start dealing with the feed table

        Map<String, MessageAttributeValue> attributeValueMap = new HashMap<>();
        attributeValueMap.put("alias", new MessageAttributeValue().withDataType("String").withStringValue(alias));
        String limitString = Integer.toString(limit);
        attributeValueMap.put("limit", new MessageAttributeValue().withDataType("Number").withStringValue(limitString));
        String epochString = Long.toString(epoch);
        attributeValueMap.put("time_stamp", new MessageAttributeValue().withDataType("Number").withStringValue(epochString));


        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(SQS_URL_POST_STATUS)
                .withMessageBody(statusJSON)
                .withMessageAttributes(attributeValueMap)
                .withDelaySeconds(0);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

        String msgId = send_msg_result.getMessageId();
        System.out.println("Message ID: " + msgId);

        // We'll need to replace the table and the loop with a call to an SQS message
        //Table feedTable = getTable(FEED_TABLE);
        /*for(int i = 0; i < people.size(); ++i) {
            feedTable.putItem(new Item().withPrimaryKey("alias", people.get(i),
                    "time_stamp", epoch)
                    .withString("poster_alias", alias)
                    .withString("status", statusJSON));
        }*/
        return true;
    }


    protected long getStatusTimeEpoch(Status status) throws ParseException {
        String dateTime = status.getDate();
        SimpleDateFormat df = new SimpleDateFormat("MMM d yyyy h:mm aaa");
        Date date = df.parse(dateTime);
        long epoch = date.getTime();
        return epoch;
    }
}
