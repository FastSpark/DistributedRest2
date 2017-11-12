package com.fastspark.fastspark.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by nuwantha on 11/10/17.
 */

@XmlRootElement
public class Node {

    private  String ip;
    private  int port;
    private long timeStamp;
    private String bootrapIp;

    public Node(){


    }
    public Node(String ip, int port){
        this.timeStamp =  new Timestamp(System.currentTimeMillis()).getTime();
        this.ip = ip;
        this.port = port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIp(String ip) {

        this.ip = ip;
    }

    @JsonProperty
    public String getBootrapIp() {
        return bootrapIp;
    }

    public void setBootrapIp(String bootrapIp) {
        this.bootrapIp = bootrapIp;
    }

    @JsonProperty
    public String getIp(){
        return this.ip;
    }

    @JsonProperty
    public int getPort(){
        return this.port;
    }

    @JsonProperty
    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long time) {
        this.timeStamp=time;
    }
}
