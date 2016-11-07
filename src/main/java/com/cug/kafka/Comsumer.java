package com.cug.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.cug.config.ClusterConfig;
import com.cug.config.RaceUtils;
import com.cug.model.OrderMessage;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class Comsumer {
	
	private static ConsumerConnector orderConsumer; 
	
	public static void main(String[] str){
		Properties prop = new Properties();  
        prop.put("zookeeper.connect", ClusterConfig.Zookeeper);  
        prop.put("group.id","order");  
        prop.put("zookeeper.session.timeout.ms","400");  
        prop.put("zookeeper.sync.time.ms","200");  
        prop.put("auto.commit.interval.ms","1000");  
  
        orderConsumer = kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(prop));  
		
        Map<String,Integer> topicCountMap = new HashMap<String, Integer>();  
        topicCountMap.put("order",new Integer(1));  
        Map<String,List<KafkaStream<byte[],byte[]>>> consumerMap = orderConsumer.createMessageStreams(topicCountMap);  
        KafkaStream<byte[],byte[]> stream = consumerMap.get("order").get(0);  
  
        ConsumerIterator<byte[],byte[]> it = stream.iterator();  
        int count=1;
        while(it.hasNext()){    
        	byte[] bytes = it.next().message();
        	
        	OrderMessage orderData = RaceUtils.readKryoObject(OrderMessage.class, bytes);

            System.out.println(count+" "+ orderData.toString());
            count++;
            
        }  
		
	}

}
