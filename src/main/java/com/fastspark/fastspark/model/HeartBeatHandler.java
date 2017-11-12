package com.fastspark.fastspark.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by nuwantha on 11/10/17.
 */
public class HeartBeatHandler implements Runnable {

    @Override
    public void run() {
        try {
            while (true ){
                sendHeartBeat();
                Thread.sleep(500);
                Client.updateRountingTable();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     public void sendHeartBeat() throws IOException {
        String message = "HEARTBEAT " + Client.getIp() + " " + Client.getPort();
        message = String.format("%04d", message.length() + 5) + " " + message;
        ArrayList<Node> myNodeListWithoutMe = Client.getMyNodeList();
        Client.multicast(message, Client.getMyNodeList());
        Set<Integer> keySet = Client.getBucketTable().keySet();
        for (int key : keySet) {
            Client.unicast(message, Client.getBucketTable().get(key));
        }
    }
}
