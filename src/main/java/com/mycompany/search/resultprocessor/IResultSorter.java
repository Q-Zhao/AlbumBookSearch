package com.mycompany.search.resultprocessor;

import java.util.List;

import com.mycompany.search.bean.SearchResult;

public interface IResultSorter {

	public void sort(List<SearchResult> unsortedRsults);
}
