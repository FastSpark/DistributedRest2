package com.fastspark.fastspark.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.DatagramSocket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nuwantha on 11/12/17.
 */
@XmlRootElement
public class ClientForm {

    private  int k=3;
    private String bootrapIp;
    private  int myBucketId;
    private  String status; //whether node is intializing or up
    private  String ip;
    private  int port;
    private  String userName; //hash(ip+port)
    private  Map<Integer, Node> bucketTable; 
    private  Map<String, ArrayList<String>> fileDictionary;
    private  ArrayList<String> myFileList;
    private  ArrayList<Node> myNodeList;
    private String searchResult;
    private String currentSearch;


    public ClientForm(){
        this.currentSearch=Client.getCurrentSearch();
        this.searchResult=Client.getSearchResult();
        this.bootrapIp=Client.getBootrapIp();
        this.k=Client.getK();
        this.myBucketId=Client.getMyBucketId();
        this.status=Client.getStatus();
        this.ip=Client.getIp();
        this.port=Client.getPort();
        this.userName=Client.getUserName();
        this.bucketTable=Client.getBucketTable();
        this.fileDictionary=Client.getFileDictionary();
        this.myFileList=Client.getMyFileList();
        this.myNodeList=Client.getMyNodeList();

    }
    @JsonProperty
    public String getCurrentSearch() {
        return currentSearch;
    }

    @JsonProperty
    public String getSearchResult() {
        return searchResult;
    }

    @JsonProperty
    public String getBootrapIp() {
        return bootrapIp;
    }

    @JsonProperty
    public int getK() {
        return k;
    }
    @JsonProperty
    public int getMyBucketId() {
        return myBucketId;
    }
    @JsonProperty
    public String getStatus() {
        return status;
    }
    @JsonProperty
    public String getIp() {
        return ip;
    }
    @JsonProperty
    public int getPort() {
        return port;
    }
    @JsonProperty
    public String getUserName() {
        return userName;
    }
    @JsonProperty
    public Map<Integer, Node> getBucketTable() {
        return bucketTable;
    }
    @JsonProperty
    public Map<String, ArrayList<String>> getFileDictionary() {
        return fileDictionary;
    }
    @JsonProperty
    public ArrayList<String> getMyFileList() {
        return myFileList;
    }
    @JsonProperty
    public ArrayList<Node> getMyNodeList() {
        return myNodeList;
    }
}
