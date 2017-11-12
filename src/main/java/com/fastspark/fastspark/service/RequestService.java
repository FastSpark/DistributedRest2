package com.fastspark.fastspark.service;

import com.fastspark.fastspark.model.Client;
import com.fastspark.fastspark.model.Node;
import com.sun.istack.internal.logging.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.misc.Cleaner;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by nuwantha on 11/10/17.
 */
@Service
public class RequestService {

    public void handleMessage(String message)  {
        String[] messagePart = message.split(" ");
        String[] sentNode;
        switch (messagePart[1]) {
            case "REGOK":
                Client.handleRegisterResponse(message);
                break;
            case "JOINOK": // join response message
                break;
            case "LEAVE": // leave response message
                Client.handleLeave(message);
                break;
            case "SER":
                Client.searchFiles(message);
                break;
            case "SEROK": // search response message
                Client.handleSearchFilesResponse(message);
                break;
            case "HEARTBEATOK": //haddle hearbeat ok
                Client.handleHeartBeatResponse(message);
                break;
            case "HEARTBEAT":
                break;
            case "FBM":
                sentNode = messagePart[3].split("\\:");
                Client.findNodeFromBucketReply(Integer.parseInt(messagePart[2]), new Node(sentNode[0], Integer.valueOf(sentNode[1])));
                break;
            case "FBMOK": //reply to FBM
                Client.receiveReplyFindNodeFromBucket(message);
                break;
            case "FNL":
                sentNode = messagePart[2].split(":");
                Client.findMyNodeListFromNodeReply(new Node(sentNode[0], Integer.valueOf(sentNode[1])));
                break;
            case "FNLOK": //reply to FNL
                Client.receiveReplyfindMyNodeListFromNode(message);
                break;
            case "CWN":
                Client.HandleConnectWithNodes(message);
                break;
        }


    }


    public  void initializeSearch(String msg) {
        //SEARCH_FILES file_name
        Client.setCurrentSearch(msg);
        msg="SEARCH_FILES " +msg;

        int begin_index = msg.split(" ")[0].length()+1;
        String file_name= msg.substring(begin_index);
        file_name=file_name.replace(' ', '_');
        String result_string = "";
        String net_message = "SER " + Client.getIp() + " " + Client.getPort() + " " + file_name + " 0";
        net_message = String.format("%04d", net_message.length() + 5) + " " + net_message;
        Client.searchFiles(net_message);
    }

    public void leave(){
        Client.unReg();
    }
}
