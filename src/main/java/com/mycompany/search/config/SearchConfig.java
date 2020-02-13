package com.mycompany.search.config;

/**
 * 
 * @author QQZhao
 *
 */
public class SearchConfig {
	
	private String iTunesSearchURL;
	private int iTunesSearchLimit;
	private String iTunesSearchEntity;
	
	private String googleBookSearchURL;
	private String googleSearchLimit;

	private int timedOutInSeconds;

	public String getGoogleSearchLimit() {
		return googleSearchLimit;
	}
	public void setGoogleSearchLimit(String googleSearchLimit) {
		this.googleSearchLimit = googleSearchLimit;
	}
	public String getiTunesSearchURL() {
		return iTunesSearchURL;
	}
	public void setiTunesSearchURL(String iTunesSearchURL) {
		this.iTunesSearchURL = iTunesSearchURL;
	}
	public int getiTunesSearchLimit() {
		return iTunesSearchLimit;
	}
	public void setiTunesSearchLimit(int iTunesSearchLimit) {
		this.iTunesSearchLimit = iTunesSearchLimit;
	}
	
	public String getiTunesSearchEntity() {
		return iTunesSearchEntity;
	}
	public void setiTunesSearchEntity(String iTunesSearchEntity) {
		this.iTunesSearchEntity = iTunesSearchEntity;
	}
	public String getGoogleBookSearchURL() {
		return googleBookSearchURL;
	}
	public void setGoogleBookSearchURL(String googleBookSearchURL) {
		this.googleBookSearchURL = googleBookSearchURL;
	}
	public int getTimedOutInSeconds() {
		return timedOutInSeconds;
	}
	public void setTimedOutInSeconds(int timedOutInSeconds) {
		this.timedOutInSeconds = timedOutInSeconds;
	}
	
	
	
}
