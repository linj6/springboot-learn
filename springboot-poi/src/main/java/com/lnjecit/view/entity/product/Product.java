package com.lnjecit.view.entity.product;

import java.util.Date;
import java.io.Serializable;

/**
 * author name: Linj
 * create time: 2017-12-16 19:48:50
 */ 
public class Product  implements Serializable {

	private Integer id;//产品id
	private String productName;//产品名称
	private String productCode;//产品编号
	private String brand;//品牌
	private String modelnumber;//型号/规格
	private double price;//产品价格
	private Integer inventoryQuantity;//库存数量
	private String productUnit;//产品单位
	private Integer status;//状态, 0:已保存; 1:待审核; 2:审核通过; 3:审核不通过;  4:上架; 5:下架;
	private Date createTime;//创建时间

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setProductName(String productName){
		this.productName=productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setProductCode(String productCode){
		this.productCode=productCode;
	}

	public String getProductCode(){
		return productCode;
	}

	public void setBrand(String brand){
		this.brand=brand;
	}

	public String getBrand(){
		return brand;
	}

	public void setModelnumber(String modelnumber){
		this.modelnumber=modelnumber;
	}

	public String getModelnumber(){
		return modelnumber;
	}

	public void setPrice(double price){
		this.price=price;
	}

	public double getPrice(){
		return price;
	}

	public void setInventoryQuantity(Integer inventoryQuantity){
		this.inventoryQuantity=inventoryQuantity;
	}

	public Integer getInventoryQuantity(){
		return inventoryQuantity;
	}

	public void setProductUnit(String productUnit){
		this.productUnit=productUnit;
	}

	public String getProductUnit(){
		return productUnit;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", productName='" + productName + '\'' +
				", productCode='" + productCode + '\'' +
				", brand='" + brand + '\'' +
				", modelnumber='" + modelnumber + '\'' +
				", price=" + price +
				", inventoryQuantity=" + inventoryQuantity +
				", productUnit='" + productUnit + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				'}';
	}
}
