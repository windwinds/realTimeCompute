package com.cug.config;

public class ClusterConfig {
	
	//Kafka集群配置信息
	public static String Zookeeper = "192.168.1.202:2181,192.168.1.204:2181,192.168.1.205:2181";
	public static String BrokerList = "192.168.1.211:9092,192.168.1.212:9092,192.168.1.213:9092";
	public static String Topic = "TaoBaoOrder";
	
	//MongoDB配置信息
	public static String server1 = "192.168.1.211";
	public static String server2 = "192.168.1.212";
	public static String server3 = "192.168.1.213";
	public static int Port = 27017;
	public static int PoolSize = 100;
	public static int BlockSize = 100;
	

}
