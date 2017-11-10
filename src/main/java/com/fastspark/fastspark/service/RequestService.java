package com.fastspark.fastspark.service;

import com.fastspark.fastspark.model.Client;
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

//    public int joinNode(Map<String, String> node) {
////        if(Client.neighborTable.contains(node)){
////            return 0;
////        }
////        else if(Client.neighborTable.size()<3){
////            Client.neighborTable.add(node);
////            return 0;
////        }
////        else {
////            return 9999;
////        }
//        return 0;
//    }
//
//
//    public int leaveNode(Map<String, String> node) {
//        // TODO Auto-generated method stub
//
////        if(Global.neighborTable.contains(node)) {
////            Global.neighborTable.remove(node);
////            System.out.println("Remove node - "+node);
////            return 0;
////        }else {
////            return 9999;
////        }
//        return 0;
//    }
//
    public Map<String, String> registerNode() {
        Map<String, String> result = new HashMap<>();
        DatagramSocket receiveSock = null;
        String username=Client.getIp()+":"+Client.getPort();
        String msg = " REG " + Client.getIp() + " " + Client.getPort() + " " + username;
        msg = "00" + Integer.toString(msg.length()) + msg;

        try {
            receiveSock = new DatagramSocket(Client.getPort()-1);
            receiveSock.setSoTimeout(10000);
            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

            DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(Client.getIp()), 55555);
            receiveSock.send(datagramPacket);
            receiveSock.receive(incoming);
            receiveSock.close();
            String receivedMessage = new String(incoming.getData(), 0, incoming.getLength());

            String[] messagePart = receivedMessage.split(" ");
            String command=messagePart[1];
            if(command.equals("REGOK")) {
                switch (messagePart[2]) {
                    case "0":
                        result.put("success", "true");
                        result.put("result", "Node Successfully registered");
                        System.out.println("You are the first node, registered successfully with BS!");
                        Client.displayRoutingTable();
                        Client.setStatus("1");
                        break;
                    case "1":
                        result.put("success", "true");
                        result.put("result", "Node Successfully registered case1");
                        Client.storeNode(messagePart[3], messagePart[4]);
                        Client.displayRoutingTable();
                        Client.setStatus("1");
                        break;
                    case "2":
                        result.put("success", "true");
                        result.put("result", "Node Successfully registered case2");
                        Client.storeNode(messagePart[3], messagePart[4]);
                        Client.storeNode(messagePart[5], messagePart[6]);

                        // complete bucketTable (including my own bucket if it's empty)

                        for (int i = 0; i < Client.getK(); i++) {
                            if (!Client.getBucketTable().containsKey(i)) {
                                Client.findNodeFromBucket(i);
                            }
                        }
                        // time out to complete receiving replies for findNodeFromBucket
                        try {
                            Thread.sleep(8000);  // Tune this

                        } catch (InterruptedException ex) {
                            try {
                                Logger.getLogger(Class.forName(Client.class.getName())).log(Level.SEVERE, null, ex);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "9999":
                        result.put("success", "false");
                        result.put("result", "There is some error in the command");
                        System.exit(0);
                        break;
                    case "9998":
                        System.out.println("failed, already registered! attempting unregister first");
                        result.put("success", "false");
                        result.put("result", "Already registered you, unregister first");
                        break;
                    case "9997":
                        result.put("success", "false");
                        result.put("result", "Registered to another user, try a different IP and port");
                        System.exit(0);
                        // TODO
                        break;
                    case "9996":
                        result.put("success", "false");
                        result.put("result", "Can’t register, BS full");
                        System.exit(0);
                    default:
                        break;
                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

//
//    public ArrayList<String> search(Map<String, String> node){
//        String query = node.get("file_name");
//        int numHops = Integer.parseInt(node.get("hops"))+1;
//        node.put("hops", numHops+"");
//
//        ArrayList<String> result = searchMyFiles(query);
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
//
//        Thread requestThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
////                for(Map<String,String> neighbour: Global.neighborTable){
////                    String neighbourIp = neighbour.get("ip");
////                    String neighbourPort = neighbour.get("port");
////                    String uri="http://"+neighbourIp+":"+neighbourPort+"/search";
////                    HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
////                    //restTemplate.postForObject(uri, entity, String.class);
////                    restTemplate.postForLocation(uri, entity);
////                }
//            }
//        });
//        requestThread.start();
//
//        return result;
//    }
//
//    public ArrayList<String> searchMyFiles(String query){
//        ArrayList<String> result = new ArrayList<>();
//        String values[]=query.toLowerCase().split(" ");
//        List<String> queryWords = new ArrayList<>();
//        Collections.addAll(queryWords, values);
//
////        ArrayList<String> myFiles = Global.filesList;
//
////        for(String file: myFiles) {
////            List<String> subStrings = new ArrayList<>();
////            Collections.addAll(subStrings, file.toLowerCase().split(" "));
////            boolean check=subStrings.retainAll(queryWords);
////            if(!check || (subStrings.size() > 0)){
////                result.add(file);
////            }
////        }
//
//        return result;
//    }
//
//
//    //First write leave request to neighbour table ip and ports then send UNREG to Bootstrap server
//    public Map<String, String> unregisterNode(Map <String,String> message) {
//        Map<String, String> result = new HashMap<>();
////        int size = Global.neighborTable.size();
////        for (int i = 0; i <size; i++) {
////
////            Map <String,String> neighbor = Global.neighborTable.get(i);
////            String neighborIp = neighbor.get("ip");
////            String neighborPort = neighbor.get("port");
////            String uri="http://"+neighborIp+":"+neighborPort+"/leave";
////            Map<String,String> node = new HashMap<>();
////            node.put("ip", Global.nodeIp);
////            node.put("port", Global.nodePort);
////            RestTemplate restTemplate = new RestTemplate();
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
////            HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
////            int answer = restTemplate.postForObject(uri, entity, Integer.class);
////        }
////
////        DatagramSocket receiveSock = null;
////        try {
////            receiveSock = new DatagramSocket(Integer.parseInt(Global.nodePort)+2);
////            receiveSock.setSoTimeout(10000);
////            byte[] buffer = new byte[65536];
////            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
////            String unreg_request = "UNREG " + Global.nodeIp + " " + Global.nodePort + " " + message.get("username");
////
////            int length = unreg_request.length() + 5;
////            unreg_request = String.format("%04d", length) + " " + unreg_request;
////            DatagramPacket regrequest = new DatagramPacket(unreg_request.getBytes(), unreg_request.getBytes().length,
////                    InetAddress.getByName(Global.bootstrapServerIp), Global.bootstrapServerPort);
////            receiveSock.send(regrequest);
////            receiveSock.receive(incoming);
////            byte[] data = incoming.getData();
////            receiveSock.close();
////            String s = new String(data, 0, incoming.getLength());
////            String[] values = s.split(" ");
////            int value = Integer.parseInt(values[2]);
////            if(value == 0){
////                result.put("success", "true");
////                result.put("result", "Node Successfully unregistered");
////            }
////            else{
////                result.put("success", "false");
////                result.put("result", "Error while unregistering. IP and port may not be in the registry or command is incorrect");
////            }
//////            Global.clear();
////            System.out.println(s);
////        } catch (SocketException e) {
////            receiveSock.close();
////            e.printStackTrace();
////            result.put("success", "false");
////            result.put("result", e.toString());
////        } catch (IOException e) {
////            receiveSock.close();
////            e.printStackTrace();
////            result.put("success", "false");
////            result.put("result", e.toString());
////        }
//
//        return result;
//    }

}
