package com.mycompany.search.bean;

public class SearchStatusDetails {

	private StatusCode statusCode;
	private String source;
	private String timeComsumed;
	private int record;
	
	public StatusCode getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTimeComsumed() {
		return timeComsumed;
	}
	public void setTimeComsumed(String timeComsumed) {
		this.timeComsumed = timeComsumed;
	}
	public int getRecord() {
		return record;
	}
	public void setRecord(int record) {
		this.record = record;
	}
	
}
