package com.cug.mongodb;


import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestMongo {
	
	public static void main(String[] strs){
		MongoDatabase db = MongoManager.getDB("test");
		
		//MongoCollection<Document> collection = db.getCollection("user");
		MongoCollection<Document> collection = MongoManager.getDB("test").getCollection("miniteData");
		Document d = new Document();
		
		d.append("name", "lyc");
		d.append("password", "123456");
		
		collection.insertOne(d);
		
	}
	
		
	   
}
