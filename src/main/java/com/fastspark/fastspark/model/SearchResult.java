package com.fastspark.fastspark.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nuwantha on 11/12/17.
 */
@XmlRootElement
public class SearchResult {

    private String searchFile;

    public SearchResult(){

    }
    public SearchResult(String searchFile) {
        this.searchFile = searchFile;
    }

    @JsonProperty
    public String getSearchFile() {
        return searchFile;
    }

    public void setSearchFile(String searchFile) {
        this.searchFile = searchFile;
    }
}
