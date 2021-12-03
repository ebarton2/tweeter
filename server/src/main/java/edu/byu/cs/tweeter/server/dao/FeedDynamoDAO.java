package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;

public class FeedDynamoDAO extends DynamoStatusDAO implements FeedDAO {

    @Override
    public List<Status> getFeed(String alias, Status lastStatus, int limit) throws ParseException {
        return getPage(alias, lastStatus, limit, FEED_TABLE);
    }

    public void updateFeedTableBatch(String alias, long epoch, String statusJSON, String[] people) {
        TableWriteItems items = new TableWriteItems(FEED_TABLE);

        for (int i = 0; i < people.length; ++i) {


            Item item = new Item()
                    .withPrimaryKey("alias", people[i], "time_stamp", epoch)
                    .withString("poster_alias", alias).withString("status", statusJSON);
            items.addItemToPut(item);

            if ((items.getItemsToPut() != null && items.getItemsToPut().size() == 25)
                    /*|| (i == people.length - 1 && items.getItemsToPut() != null && items.getItemsToPut().size() <= 25)*/) {
                loopBatchWrite(items);
                items = new TableWriteItems(FEED_TABLE);
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }

    }

    private void loopBatchWrite(TableWriteItems items) {
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);

        while(outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
        }
    }
}
