package com.cug.mongodb;

import java.util.Arrays;

import com.cug.config.ClusterConfig;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoManager {
	
	private static MongoClient mongoClient = null;
	
	public static MongoDatabase getDB(String dbName){
		return mongoClient.getDatabase(dbName);	
	}
	
	static{
		initDB();
	}
	
	private static void initDB(){
		 mongoClient = new MongoClient(Arrays.asList(
				   new ServerAddress(ClusterConfig.server1, ClusterConfig.Port),
				   new ServerAddress(ClusterConfig.server2, ClusterConfig.Port),
				   new ServerAddress(ClusterConfig.server3, ClusterConfig.Port)));
		 
		 //Gets a read preference that forces reads to a secondary if one is available, otherwise to the primary.
		 mongoClient.setReadPreference(ReadPreference.secondaryPreferred());
	}

}
