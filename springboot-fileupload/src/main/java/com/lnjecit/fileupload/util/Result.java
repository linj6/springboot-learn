package com.lnjecit.fileupload.util;

public class Result {

	private int status;
	private String info;
	private Object data;


	public Result(ResultCode resultCode, Object data) {
		this(resultCode);
		this.data = data;
	}


	public Result(ResultCode resultCode) {
		this.status = resultCode.getStatus();
		this.info = resultCode.getInfo();
	}
	
	public Result(int status,String info,Object data) {
		this.status = status;
		this.info = info;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}