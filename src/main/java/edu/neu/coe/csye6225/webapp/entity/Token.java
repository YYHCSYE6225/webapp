package edu.neu.coe.csye6225.webapp.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@DynamoDBTable(tableName = "one_time_token")
@Data
public class Token {
    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "username")
    String username;
    @DynamoDBAttribute(attributeName = "token")
    String token;
    @DynamoDBAttribute(attributeName = "ttl")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    Long ttl;
}
