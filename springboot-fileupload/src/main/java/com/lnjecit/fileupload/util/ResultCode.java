package com.lnjecit.fileupload.util;

public enum ResultCode {

	SUCCESS(1, "请求成功"), WARN(-1, "网络异常，请稍后重试"),ERROR(0, "请求失败");

	private int status;
	private String info;

	ResultCode(int status, String info) {
		this.status = status;
		this.info = info;
	}

	public int getStatus() {
		return status;
	}

	public String getInfo() {
		return info;
	}
}