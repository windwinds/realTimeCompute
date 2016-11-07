package com.cug.storm;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;


public class RandomSpout extends BaseRichSpout{
	
	private SpoutOutputCollector collector;
	
	private Random rand;
	
	private String[] strs = new String[]{"a:im happy","b: im angry"};

	//初始化操作
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		this.rand = new Random();		
	}

	//发射tuple到bolt
	public void nextTuple() {
		// TODO Auto-generated method stub
		String toSay = strs[rand.nextInt(strs.length)];
		this.collector.emit(new Values(toSay));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("test"));
	}
	
	
}
