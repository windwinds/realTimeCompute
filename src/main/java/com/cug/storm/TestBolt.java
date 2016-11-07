package com.cug.storm;

import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.bson.Document;

import com.cug.config.RaceUtils;
import com.cug.model.OrderMessage;
import com.cug.mongodb.MongoManager;
import com.mongodb.client.MongoCollection;

public class TestBolt implements IBasicBolt{

	private transient MongoCollection<Document> collection;

	public void prepare(Map stormConf, TopologyContext context) {
		// TODO Auto-generated method stub
		this.collection = MongoManager.getDB("test").getCollection("testData");
	}

	public void execute(Tuple input, BasicOutputCollector collector) {
		// TODO Auto-generated method stub
		byte[] bytes = input.getBinaryByField("test");
		OrderMessage orderMessage = RaceUtils.readKryoObject(OrderMessage.class, bytes);
		
		//String salerId = orderMessage.getSalerId();
		Long millisTime = orderMessage.getCreateTime();
		double price = orderMessage.getTotalPrice();
		
		Document doc = new Document();		
		doc.put("time",millisTime);
		doc.put("price", price);
		collection.insertOne(doc);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("test2"));
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

}
