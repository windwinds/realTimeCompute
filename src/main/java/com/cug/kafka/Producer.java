package com.cug.kafka;

import java.util.Properties;
import java.util.Random;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.cug.config.ClusterConfig;
import com.cug.config.RaceUtils;
import com.cug.model.OrderMessage;

public class Producer {
	
	private static Random rand = new Random();
	//private static kafka.javaapi.producer.Producer<String, OrderMessage> orderProducer = null; 
	private static kafka.javaapi.producer.Producer<String, byte[]> orderProducer = null; 
	
	
	public static void main(String[] args){
		Properties properties = new Properties();
		properties.put("zookeeper.connect", ClusterConfig.Zookeeper);
		properties.put("serializer.class", "kafka.serializer.DefaultEncoder");
		//properties.put("serializer.class", "com.cug.model.JsonEncoder");
		properties.put("metadata.broker.list", ClusterConfig.BrokerList);
		
		//orderProducer = new kafka.javaapi.producer.Producer<String, OrderMessage>(new ProducerConfig(properties));
		orderProducer = new kafka.javaapi.producer.Producer<String, byte[]>(new ProducerConfig(properties));
		
		for (int i=0; i<10000; i++){
			final int platform = rand.nextInt(2);
			final OrderMessage orderMessage = ( platform == 0 ? OrderMessage.createTbaoMessage() : OrderMessage.createTmallMessage());
	        orderMessage.setCreateTime(System.currentTimeMillis());
	        
	        System.out.println(i+" "+orderMessage.toString());

	        byte[] bytes = RaceUtils.writeKryoObject(orderMessage);
	        orderProducer.send(new KeyedMessage<String, byte[]>("order", bytes));
	        //orderProducer.send(new KeyedMessage<String, OrderMessage>("order", orderMessage));
		}
				
	}
}
