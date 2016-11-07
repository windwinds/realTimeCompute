package com.cug.storm;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.Scheme;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import com.cug.config.ClusterConfig;


public class TestTopology {
	
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException, InterruptedException{
		
		//kafka spout
		String topic = "order";
		String zkRoot = "kafkastorm";
		String spoutId = "mykafka";
		BrokerHosts brokerHosts = new ZkHosts(ClusterConfig.Zookeeper);
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot, spoutId);
//		spoutConfig.scheme = new SchemeAsMultiScheme(new Scheme() {
//			
//			public Fields getOutputFields() {
//				// TODO Auto-generated method stub
//				return new Fields("test");
//			}
//			
//			public List<Object> deserialize(ByteBuffer ser) {
//				// TODO Auto-generated method stub
//				byte[] bytes = ser.array();
//				return new Values(bytes);
//			}
//		});
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("kafkaSpout", new KafkaSpout(spoutConfig),1);
		
		//shuffle 随机分组获得randomSpout发送过来的tuple
		builder.setBolt("ComputeBolt", new ComputeBolt()).shuffleGrouping("kafkaSpout");
		
		
		
		Config conf = new Config();
		conf.setDebug(true);
		
		conf.setNumWorkers(2);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test", conf, builder.createTopology());
		Thread.sleep(10000);

		cluster.shutdown();
		
		//StormSubmitter.submitTopology("compute", conf, builder.createTopology());
		
		
	}

}
