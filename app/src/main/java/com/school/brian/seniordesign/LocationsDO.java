package com.school.brian.seniordesign;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "seniordesignproject-mobilehub-709331117-Locations")

public class LocationsDO {
    private String _itemID;
    private Double _signalStrength;
    private String _category;
    private String _name;

    @DynamoDBHashKey(attributeName = "itemID")
    @DynamoDBAttribute(attributeName = "itemID")
    public String getItemID() {
        return _itemID;
    }

    public void setItemID(final String _itemID) {
        this._itemID = _itemID;
    }
    @DynamoDBRangeKey(attributeName = "signalStrength")
    @DynamoDBAttribute(attributeName = "signalStrength")
    public Double getSignalStrength() {
        return _signalStrength;
    }

    public void setSignalStrength(final Double _signalStrength) {
        this._signalStrength = _signalStrength;
    }
    @DynamoDBIndexHashKey(attributeName = "category", globalSecondaryIndexName = "Categories")
    public String getCategory() {
        return _category;
    }

    public void setCategory(final String _category) {
        this._category = _category;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }

}
