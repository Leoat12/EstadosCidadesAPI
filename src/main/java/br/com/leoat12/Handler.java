package br.com.leoat12;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.log4j.Logger;

public class Handler implements RequestHandler<String, Estados> {

	private static final Logger LOG = Logger.getLogger(Handler.class);

	@Override
	public Estados handleRequest(String sigla, Context context) {

		LOG.info("Lambda function started new instance with input:" + sigla);
		
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
		
		Estados estado = mapper.load(Estados.class, sigla);

		LOG.info("Lambda function is about to end with output: " + estado);

		return estado;
	}
}
