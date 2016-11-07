package com.cug.model;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

public class OrderEncoder implements Encoder<OrderMessage>{

	public OrderEncoder(VerifiableProperties verifiableProperties){
		
	}
	
	public byte[] toBytes(OrderMessage msg) {
		// TODO Auto-generated method stub
		return msg.toString().getBytes();
	}

}
