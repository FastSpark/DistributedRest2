package com.fastspark.fastspark.service;

import com.fastspark.fastspark.model.Client;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.*;

/**
 * Created by nuwantha on 11/10/17.
 */
@Service
public class RequestService {

    public int joinNode(Map<String, String> node) {
//        if(Client.neighborTable.contains(node)){
//            return 0;
//        }
//        else if(Client.neighborTable.size()<3){
//            Client.neighborTable.add(node);
//            return 0;
//        }
//        else {
//            return 9999;
//        }
        return 0;
    }


    public int leaveNode(Map<String, String> node) {
        // TODO Auto-generated method stub

//        if(Global.neighborTable.contains(node)) {
//            Global.neighborTable.remove(node);
//            System.out.println("Remove node - "+node);
//            return 0;
//        }else {
//            return 9999;
//        }
        return 0;
    }

    public Map<String, String> registerNode(String ip,String port,Map<String, String> message) {
        Map<String, String> result = new HashMap<>();
        DatagramSocket receiveSock = null;
        try{
//            receiveSock = new DatagramSocket(Integer.parseInt(Global.nodePort)+2);
            receiveSock.setSoTimeout(10000);
            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            String init_request = "REG " + ip + " " + port + " " + message.get("user");
            int length = init_request.length() + 5;
            init_request = String.format("%04d", length) + " " + init_request;
//            DatagramPacket regrequest = new DatagramPacket(init_request.getBytes(), init_request.getBytes().length, InetAddress.getByName(Global.bootstrapServerIp), Global.bootstrapServerPort);
//            receiveSock.send(regrequest);
            receiveSock.receive(incoming);
            receiveSock.close();
            byte[] data = incoming.getData();
            String s = new String(data, 0, incoming.getLength());
            System.out.println(s);
            String[] values = s.split(" ");
            String command = values[1];
            if(command.equals("REGOK")){
                int noOfNodes = Integer.parseInt(values[2]);
                if(noOfNodes == 0){
                    result.put("success", "true");
                    result.put("result", "Node Successfully registered");
                }
                else if(noOfNodes == 9999) {
                    result.put("success", "false");
                    result.put("result", "There is some error in the command");
                }
                else if(noOfNodes == 9998) {
                    result.put("success", "false");
                    result.put("result", "Already registered you, unregister first");
                }
                else if(noOfNodes == 9997) {
                    result.put("success", "false");
                    result.put("result", "Registered to another user, try a different IP and port");
                }
                else if(noOfNodes == 9996) {
                    result.put("success", "false");
                    result.put("result", "Can’t register, BS full");
                }
                else if(noOfNodes==1) {
                    String neighbourIp = values[3];
                    String neighbourPort = values[4];
                    String uri="http://"+neighbourIp+":"+neighbourPort+"/join";
                    RestTemplate restTemplate = new RestTemplate();
                    Map<String,String> node=new HashMap<>();
//                    node.put("ip", Global.nodeIp);
//                    node.put("port", Global.nodePort);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

                    HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
                    int answer = restTemplate.postForObject(uri, entity, Integer.class);

                    node.put("ip", neighbourIp);
                    node.put("port", neighbourPort);
                    if(answer==0) {
//                        Global.neighborTable.add(node);
                    }
                    System.out.println("Neighbour value "+node.get("ip")+" "+node.get("port"));
                    result.put("success", "true");
                    result.put("result", "Node Successfully registered");
                }
                else {
                    String neighbourIp = values[3];
                    String neighbourPort = values[4];
                    String uri="http://"+neighbourIp+":"+neighbourPort+"/join";
                    RestTemplate restTemplate = new RestTemplate();
                    Map<String,String> node=new HashMap<>();
//                    node.put("ip", Global.nodeIp);
//                    node.put("port", Global.nodePort);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

                    HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
                    int answer = restTemplate.postForObject(uri, entity, Integer.class);

                    Map<String, String> neighbour = new HashMap<String,String>();

                    if(answer==0) {
                        neighbour.put("ip", neighbourIp);
                        neighbour.put("port", neighbourPort);
//                        Global.neighborTable.add(neighbour);
                    }
                    System.out.println("My value "+node.get("ip")+" "+node.get("port"));

                    neighbourIp = values[5];
                    neighbourPort = values[6];
                    uri="http://"+neighbourIp+":"+neighbourPort+"/join";

                    answer = restTemplate.postForObject(uri, entity, Integer.class);

                    if(answer==0) {
                        Map<String, String> neighbour2 = new HashMap<String,String>();
                        neighbour2.put("ip", neighbourIp);
                        neighbour2.put("port", neighbourPort);
//                        Global.neighborTable.add(neighbour2);
                    }
                    System.out.println("My value "+node.get("ip")+" "+node.get("port"));
                    result.put("success", "true");
                    result.put("result", "Node Successfully registered");;

                }

            }


        }
        catch (SocketException e) {
            e.printStackTrace();
            receiveSock.close();
            result.put("success", "false");
            result.put("result", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            receiveSock.close();
            result.put("success", "false");
            result.put("result", e.toString());
        }
        return result;
    }


    public ArrayList<String> search(Map<String, String> node){
        String query = node.get("file_name");
        int numHops = Integer.parseInt(node.get("hops"))+1;
        node.put("hops", numHops+"");

        ArrayList<String> result = searchMyFiles(query);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
//                for(Map<String,String> neighbour: Global.neighborTable){
//                    String neighbourIp = neighbour.get("ip");
//                    String neighbourPort = neighbour.get("port");
//                    String uri="http://"+neighbourIp+":"+neighbourPort+"/search";
//                    HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
//                    //restTemplate.postForObject(uri, entity, String.class);
//                    restTemplate.postForLocation(uri, entity);
//                }
            }
        });
        requestThread.start();

        return result;
    }

    public ArrayList<String> searchMyFiles(String query){
        ArrayList<String> result = new ArrayList<>();
        String values[]=query.toLowerCase().split(" ");
        List<String> queryWords = new ArrayList<>();
        Collections.addAll(queryWords, values);

//        ArrayList<String> myFiles = Global.filesList;

//        for(String file: myFiles) {
//            List<String> subStrings = new ArrayList<>();
//            Collections.addAll(subStrings, file.toLowerCase().split(" "));
//            boolean check=subStrings.retainAll(queryWords);
//            if(!check || (subStrings.size() > 0)){
//                result.add(file);
//            }
//        }

        return result;
    }


    //First write leave request to neighbour table ip and ports then send UNREG to Bootstrap server
    public Map<String, String> unregisterNode(Map <String,String> message) {
        Map<String, String> result = new HashMap<>();
//        int size = Global.neighborTable.size();
//        for (int i = 0; i <size; i++) {
//
//            Map <String,String> neighbor = Global.neighborTable.get(i);
//            String neighborIp = neighbor.get("ip");
//            String neighborPort = neighbor.get("port");
//            String uri="http://"+neighborIp+":"+neighborPort+"/leave";
//            Map<String,String> node = new HashMap<>();
//            node.put("ip", Global.nodeIp);
//            node.put("port", Global.nodePort);
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
//            HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
//            int answer = restTemplate.postForObject(uri, entity, Integer.class);
//        }
//
//        DatagramSocket receiveSock = null;
//        try {
//            receiveSock = new DatagramSocket(Integer.parseInt(Global.nodePort)+2);
//            receiveSock.setSoTimeout(10000);
//            byte[] buffer = new byte[65536];
//            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
//            String unreg_request = "UNREG " + Global.nodeIp + " " + Global.nodePort + " " + message.get("username");
//
//            int length = unreg_request.length() + 5;
//            unreg_request = String.format("%04d", length) + " " + unreg_request;
//            DatagramPacket regrequest = new DatagramPacket(unreg_request.getBytes(), unreg_request.getBytes().length,
//                    InetAddress.getByName(Global.bootstrapServerIp), Global.bootstrapServerPort);
//            receiveSock.send(regrequest);
//            receiveSock.receive(incoming);
//            byte[] data = incoming.getData();
//            receiveSock.close();
//            String s = new String(data, 0, incoming.getLength());
//            String[] values = s.split(" ");
//            int value = Integer.parseInt(values[2]);
//            if(value == 0){
//                result.put("success", "true");
//                result.put("result", "Node Successfully unregistered");
//            }
//            else{
//                result.put("success", "false");
//                result.put("result", "Error while unregistering. IP and port may not be in the registry or command is incorrect");
//            }
////            Global.clear();
//            System.out.println(s);
//        } catch (SocketException e) {
//            receiveSock.close();
//            e.printStackTrace();
//            result.put("success", "false");
//            result.put("result", e.toString());
//        } catch (IOException e) {
//            receiveSock.close();
//            e.printStackTrace();
//            result.put("success", "false");
//            result.put("result", e.toString());
//        }

        return result;
    }

}
