package com.cug.storm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cug.config.RaceUtils;
import com.cug.model.OrderMessage;
import com.cug.mongodb.MongoManager;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;


public class ComputeBolt implements IBasicBolt  {

	public static final Logger LOG = LoggerFactory.getLogger(ComputeBolt.class);
	//transient关键字标记的成员变量不参与序列化过程
	private transient MongoCollection<Document> collection;
	private HashMap<String, Double> hashMap;
		
	public void prepare(Map stormConf, TopologyContext context) {
		// TODO Auto-generated method stub
		collection = MongoManager.getDB("test").getCollection("miniteData");
		hashMap = new HashMap<String, Double>();
	}

	public void execute(Tuple tuple, BasicOutputCollector collector) {
		// TODO Auto-generated method stub
		byte[] bytes = tuple.getBinary(0);
		OrderMessage orderMessage = RaceUtils.readKryoObject(OrderMessage.class, bytes);
		
		String salerId = orderMessage.getSalerId();
		String type = salerId.split("_")[0];
		
		Long millisTime = orderMessage.getCreateTime();
		double price = orderMessage.getTotalPrice();
		//ms
		Long minuteTime = (millisTime / 1000 / 60) * 60;
		String key;
		if("tm".equals(type)){
			 key = "tm_" + minuteTime;
		}else{
			 key = "tb_" + minuteTime;
		}
		if(hashMap.containsKey(key)){
			double oldprice = hashMap.get(key);
			hashMap.put(key, oldprice+price);
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set", new BasicDBObject().append("money", hashMap.get(key)));
			BasicDBObject searchQuery = new BasicDBObject().append("miniteTime", key);		
			collection.updateOne(searchQuery, newDocument);
		}else{
			hashMap.put(key, price);
			Document res1 = new Document();				
			res1.put("miniteTime", key);
			res1.put("money", hashMap.get(key));
			collection.insertOne(res1);
		}								
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public void cleanup() {
		// TODO Auto-generated method stub
		
	}


}
