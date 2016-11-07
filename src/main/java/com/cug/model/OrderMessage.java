package com.cug.model;

import com.cug.model.TableItemFactory;

public class OrderMessage implements java.io.Serializable{

	private long orderId; //订单ID
    private String buyerId; //买家ID
    private String productId; //商品ID

    private String salerId; //卖家ID
    private long createTime; //13位数数，毫秒级时间戳，订单创建时间
    private double totalPrice;
    
    private OrderMessage(){
    	
    }

	
	public static OrderMessage createTbaoMessage() {
        OrderMessage msg =  new OrderMessage();
        msg.setOrderId(TableItemFactory.createOrderId());
        msg.setBuyerId(TableItemFactory.createBuyerId());
        msg.setProductId(TableItemFactory.createProductId());
        msg.setSalerId(TableItemFactory.createTbaoSalerId());
        msg.setTotalPrice(TableItemFactory.createTotalPrice());
        return msg;
    }

    public static OrderMessage createTmallMessage() {
        OrderMessage msg =  new OrderMessage();
        msg.setOrderId(TableItemFactory.createOrderId());
        msg.setBuyerId(TableItemFactory.createBuyerId());
        msg.setProductId(TableItemFactory.createProductId());
        msg.setSalerId(TableItemFactory.createTmallSalerId());
        msg.setTotalPrice(TableItemFactory.createTotalPrice());

        return msg;
    }
    
    public String toString(){
    	return "OrderMessage{" +
                "orderId=" + orderId +
                ", buyerId='" + buyerId + '\'' +
                ", productId='" + productId + '\'' +
                ", salerId='" + salerId + '\'' +
                ", createTime=" + createTime +
                ", totalPrice=" + totalPrice +
                '}';
    }

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSalerId() {
		return salerId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public byte[] toBytes(OrderMessage msg) {
		// TODO Auto-generated method stub
		return msg.toString().getBytes();
	}

}
