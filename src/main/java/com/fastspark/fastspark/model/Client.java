package com.fastspark.fastspark.model;

import java.net.DatagramSocket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nuwantha on 11/10/17.
 */
public class Client {

    private static int myBucketId;
    private static String status; //whether node is intializing or up
    private static String ip;
    private static int port;
    private static String userName; //hash(ip+port)
    private static Map<Integer, Node> bucketTable =new HashMap<>(); //bucket and the node I know from that bucket
    private static Map<String, ArrayList<String>> fileDictionary =new HashMap<>(); //filename: nodelist
    private static ArrayList<String> myFileList =new ArrayList<>(); //filenames with me
    private static ArrayList<Node> myNodeList =new ArrayList<>(); //nodes in my bucket
    private static Timestamp timestamp;
    private static DatagramSocket datagramSocket;

    public static int getMyBucketId() {
        return myBucketId;
    }

    public static void setMyBucketId(int myBucketId) {
        Client.myBucketId = myBucketId;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Client.status = status;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Client.ip = ip;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Client.port = port;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Client.userName = userName;
    }

    public static Map<Integer, Node> getBucketTable() {
        return bucketTable;
    }

    public static void setBucketTable(Map<Integer, Node> bucketTable) {
        Client.bucketTable = bucketTable;
    }

    public static Map<String, ArrayList<String>> getFileDictionary() {
        return fileDictionary;
    }

    public static void setFileDictionary(Map<String, ArrayList<String>> fileDictionary) {
        Client.fileDictionary = fileDictionary;
    }

    public static ArrayList<String> getMyFileList() {
        return myFileList;
    }

    public static void setMyFileList(ArrayList<String> myFileList) {
        Client.myFileList = myFileList;
    }

    public static ArrayList<Node> getMyNodeList() {
        return myNodeList;
    }

    public static void setMyNodeList(ArrayList<Node> myNodeList) {
        Client.myNodeList = myNodeList;
    }

    public static Timestamp getTimestamp() {
        return timestamp;
    }

    public static void setTimestamp(Timestamp timestamp) {
        Client.timestamp = timestamp;
    }

    public static DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public static void setDatagramSocket(DatagramSocket datagramSocket) {
        Client.datagramSocket = datagramSocket;
    }
}
