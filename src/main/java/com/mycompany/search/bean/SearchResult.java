package com.mycompany.search.bean;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

	private String type;
	private List<String> creaters;
	private String title;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public void setCreaters(List<String> creaters) {
		this.creaters = creaters;
	}
	public List<String> getCreaters() {
		if (this.creaters == null) {
            this.creaters = new ArrayList<String>();
        }
        return this.creaters;
	}
	
	
}
