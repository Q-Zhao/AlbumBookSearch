package com.mycompany.search.resultprocessor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mycompany.search.bean.SearchResult;

/**
 * This class sort the search result by title
 * @author QQZhao
 */

public class ResultTitleSorter implements IResultSorter {

	@Override
	public void sort(List<SearchResult> results) {
		
		// Sort search result by title
		Collections.sort(results, new Comparator<SearchResult>() {

			@Override
			public int compare(SearchResult result1, SearchResult result2) {
				return result1.getTitle().compareTo(result2.getTitle());
			}
			
		});
	}
}
