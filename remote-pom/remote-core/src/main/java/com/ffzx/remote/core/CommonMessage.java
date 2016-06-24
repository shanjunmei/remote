package com.ffzx.remote.core;

public class CommonMessage {

	// 命令
	private String command;
	// 参数
	private Object data;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
