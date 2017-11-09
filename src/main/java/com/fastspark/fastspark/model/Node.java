package com.fastspark.fastspark.model;

import java.sql.Timestamp;

/**
 * Created by nuwantha on 11/10/17.
 */


public class Node {

    private final String ip;
    private final int port;
    private long timeStamp;
    public Node(String ip, int port){
        this.timeStamp =  new Timestamp(System.currentTimeMillis()).getTime();
        this.ip = ip;
        this.port = port;
    }

    public String getIp(){
        return this.ip;
    }

    public int getPort(){
        return this.port;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long time) {
        this.timeStamp=time;
    }
}
