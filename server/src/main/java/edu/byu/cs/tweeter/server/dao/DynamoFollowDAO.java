package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DynamoFollowDAO extends DynamoDBDAO {
    protected List<String> follows(String alias, String lastAlias, int limit, Index index, String handle, String sort) throws Exception {
        Table table = getTable(FOLLOWS_TABLE);
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#fh", handle); //TODO

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":ff", alias);

        List<String> aliases = new ArrayList<>();
        do {
            QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#fh = :ff").withNameMap(nameMap)
                    .withValueMap(valueMap).withScanIndexForward(true).withMaxResultSize(limit);
            if (lastAlias != null) {
                querySpec = querySpec.withExclusiveStartKey( //TODO
                        new PrimaryKey(handle, alias, sort, lastAlias));
            }
            Iterator<Item> iterator;
            ItemCollection<QueryOutcome> items;
            Item item;

            if (index != null) items = index.query(querySpec);
            else items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                aliases.add(item.getString(sort));
            }
            if(aliases.size() == 0) {
                return aliases;
            }
            lastAlias = aliases.get(aliases.size()-1);

        } while(lastAlias == null);
        return aliases;
    }
}
