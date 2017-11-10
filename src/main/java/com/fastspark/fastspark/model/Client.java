package com.fastspark.fastspark.model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nuwantha on 11/10/17.
 */
public class Client {

    private static int k=3;
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
    private static String currentSearch;
    private static DefaultListModel<String> searchFilesResultListModel= new DefaultListModel<>();

    public static int getK() {
        return k;
    }

    public static void setK(int k) {
        Client.k = k;
    }

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

    public static void displayRoutingTable() {
        if (myNodeList.isEmpty() && bucketTable.isEmpty()) {
            System.out.println("Tables are empty");
        } else {
            System.out.println("Nodes list in the Bucket:");
            for (Node node : myNodeList) {
                System.out.println("\t" + node.getIp() + ":" + node.getPort());
            }

            System.out.println("Nodes list from other Buckets:");
            Iterator entries = bucketTable.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                Integer key = (Integer) entry.getKey();
                Node node = (Node) entry.getValue();
                System.out.println("Bucket " + key + " : " + node.getIp() + ":" + node.getPort());
            }
        }


    }

    public static void storeNode(String ip, String port) throws IOException {
        Node newNode = new Node(ip, Integer.parseInt(port));
        int bucketId = Math.abs((ip + ":" + port).hashCode()) % k;
        bucketTable.put(bucketId, newNode);
        if (bucketId == Client.myBucketId) {

            findMyNodeListFromNode(newNode);
        }
        displayRoutingTable();
    }

    public static void findMyNodeListFromNode(Node node) {
        String fileList = " ";
        for (int i = 0; i < Client.myFileList.size(); i++) {
            fileList += myFileList.get(i) + ":";
        }
        //FNL: Find Node List
        String message = "FNL" + " " + Client.ip + ":" + Integer.toString(Client.port) + fileList;
        message = String.format("%04d", message.length() + 5) + " " + message;

//        Map<String, String> neighbour = new HashMap<String,String>();

//        if(answer==0) {
//            neighbour.put("ip", neighbourIp);
//            neighbour.put("port", neighbourPort);
//            Global.neighborTable.add(neighbour);
//        }



    }

    public static void findNodeFromBucket(int bucketId) {
        //FBM: Find Bucket Member 0011 FBM 01
        String message = "FBM " + bucketId + " " + Client.ip + ":" + Integer.toString(Client.port);
        message = String.format("%04d", message.length() + 5) + " " + message;

        // request from available my nodes
        multicast(message, myNodeList);

        // request from nodes from other buckets
        for (int i = 0; i < k; i++) {
            if (Client.bucketTable.containsKey(i) && i != Client.myBucketId) {
                unicast(message, Client.bucketTable.get(i));
            }
        }
    }

    public static void multicast(String message, ArrayList<Node> nodesList) {
        for (Node node : nodesList) {
            if (node.getIp().equals(Client.ip) && node.getPort() == Client.port) { // ignore sending own
                continue;
            }
            byte[] buffer = message.getBytes();
            String uri="http://"+node.getIp()+":"+node.getPort()+"/Request";
            RestTemplate restTemplate = new RestTemplate();
            Map<String,String> sendMessage=new HashMap<>();
            sendMessage.put("message", message);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<Map> entity = new HttpEntity<Map>(sendMessage,headers);
            String reply= restTemplate.postForObject(uri, entity, String.class);
            Client.handleMessage(reply);

        }
    }

    public static void handleMessage(String message)  {
            String[] messagePart = message.split(" ");
            String[] sentNode;
            switch (messagePart[1]) {
                case "REGOK":
                    //handle  response from bootstrap
                    System.out.println(message);
//                    handleRegisterResponse(message);
                    break;
                case "UNROK": // handle unregister response
                    break;
                case "JOINOK": // join response message
                    break;
                case "LEAVEOK": // leave response message
                    handleLeaveOk(message);
                    break;
                case "LEAVE": // leave response message
                    handleLeave(message);
                    break;
                case "SER":
                    searchFiles(message);
                    break;
                case "SEROK": // search response message
                    System.out.println(message);
                    handleSearchFilesResponse(message);
                    break;
                case "HEARTBEATOK": //haddle hearbeat ok
                    System.out.println(message);
                    handleHeartBeatResponse(message);
                    break;
                case "HEARTBEAT":
                    break;
                //this.client.
                case "FBM": //multicast message to find a node from a bucket
                    System.out.println(message);
                    sentNode = messagePart[3].split("\\:");
                    findNodeFromBucketReply(Integer.parseInt(messagePart[2]), new Node(sentNode[0], Integer.valueOf(sentNode[1])));
                    break;
                case "FBMOK": //reply to FBM
                    receiveReplyFindNodeFromBucket(message);
                    break;
                case "FNL": // unicast message to find myNodeList from node
                    System.out.println(message);
                    sentNode = messagePart[2].split(":");
                    findMyNodeListFromNodeReply(new Node(sentNode[0], Integer.valueOf(sentNode[1])));
                    break;
                case "FNLOK": //reply to FNL
                    receiveReplyfindMyNodeListFromNode(message);
                    break;
                case "CWN":
                    HandleConnectWithNodes(message);
                    break;
            }
        }


    public static void handleLeaveOk(String message) {
        System.out.println("Leave Ok Received");
        int messageType = Integer.parseInt(message.split(" ")[2]);
        if (messageType == 0) {
            String sendMeessage = "LEAVE " + Client.getIp() + " " + Client.getPort();
            message = String.format("%04d", sendMeessage.length() + 5) + " " + sendMeessage;
            multicast(sendMeessage, myNodeList);

            System.exit(0);
        } else if (messageType == 9999) {
//            System.out.println("error while adding new node to routing table");
        }
    }

    public static void unicast(String message, Node node) {
        String uri="http://"+node.getIp()+":"+node.getPort()+"/request";
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> sendMessage=new HashMap<>();
        sendMessage.put("message", message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<Map> entity = new HttpEntity<Map>(sendMessage,headers);
        String reply= restTemplate.postForObject(uri, entity, String.class);
        Client.handleMessage(reply);
    }

    public static void handleLeave(String message) {
        System.out.println("call leave");
        String[] splitMesseageList = message.split(" ");
        String ip = splitMesseageList[2];
        int port = Integer.parseInt(splitMesseageList[3]);
        // leave wena eka nodelist eken ain karan ona eke ekek nm.
        ArrayList<Node> tem = new ArrayList<>();
        for (Node node : myNodeList) {
            if (!node.getIp().equals(ip) && node.getPort() != port) {
                tem.add(node);
            } else {
                for (String file : fileDictionary.keySet()) {
                    ArrayList<String> temFileNodeList = new ArrayList<String>();
                    for (String username : fileDictionary.get(file)) {
                        String[] split = username.split(":");
                        String temIp = split[0];
                        int temPort = Integer.parseInt(split[1]);
                        if (temIp != ip && temPort != port) {
                            temFileNodeList.add(username);
                        }
                    }
                    fileDictionary.replace(file, temFileNodeList);
                }
            }
        }


        for (int key : bucketTable.keySet()) {
            Node neighbour = bucketTable.get(key);
            if (neighbour.getIp() == ip && neighbour.getPort() == port) {
                bucketTable.remove(key);
                findNodeFromBucket(key);
            }
        }
    }

    public static void findMyNodeListFromNodeReply(Node fromNode){
        String message = "FNLOK ";

        // make myNodeList string
        for (int i = 0; i < Client.myNodeList.size(); i++) {
            message += Client.myNodeList.get(i).getIp() + ":" + Integer.toString(Client.myNodeList.get(i).getPort()) + " ";
        }

        // make fileDictionary string
        String FD = ";";
        for (String key : fileDictionary.keySet()) {
            ArrayList<String> nodesList = fileDictionary.get(key);
            FD += key + "=";
            for (int i = 0; i < nodesList.size(); i++) {
                FD += nodesList.get(i) + ",";
            }
            FD += "|";
        }

        message += FD;

        message = String.format("%04d", message.length() + 5) + " " + message;
        unicast(message, fromNode);

        // add that new node to myNodeList
        Boolean isAlreadyInMyNodeList = false;
        // ignore if it's already in myNodeList
        for (int j = 0; j < myNodeList.size(); j++) {
            if (myNodeList.get(j).getIp().equals(fromNode.getIp()) && myNodeList.get(j).getPort() == fromNode.getPort()) {
                isAlreadyInMyNodeList = true;
            }
        }
        if (!isAlreadyInMyNodeList) {
            myNodeList.add(fromNode);
        }

//        this.displayRoutingTable();

    }

    public static void receiveReplyfindMyNodeListFromNode(String message) {
//        System.out.println(message);
        String[] arr = message.split("\\;");
//        System.out.println(arr[0]);
        String[] split_msg = arr[0].split(" ");
        int numOfNodes = split_msg.length - 2;

        String fileList = arr[1];
//        System.out.println("File LIst *************** " + fileList);

        // save files to fileDicationary
        String[] records = fileList.split("\\|");
        for (int i = 0; i < records.length; i++) {
            if (records[i].length() < 2) {
                continue;
            }
            String[] a1 = records[i].split("\\=");
            String fileName = a1[0];
            if (a1.length < 2) {
                continue;
            }
            String[] nodes = a1[1].split("\\,");

            ArrayList<String> nodesContainingFile = Client.fileDictionary.get(fileName);
            if (nodesContainingFile == null) {
                nodesContainingFile = new ArrayList<>();
            }
            for (int j = 0; j < nodes.length; j++) {
                if (nodes.length < 1) {
                    continue;
                }
                if (!nodesContainingFile.contains(nodes[j])) {
                    nodesContainingFile.add(nodes[j]);
                }

            }
            Client.fileDictionary.put(fileName, nodesContainingFile);
        }

        // display filedic
        for (String key : fileDictionary.keySet()) {
//            System.out.println(key);
            ArrayList<String> get = fileDictionary.get(key);
            for (String string : get) {
//                System.out.print(string + " ");
//                System.out.println("");
            }
        }
        for (int i = 0; i < numOfNodes; i++) {
            String[] nodeDetails = split_msg[i + 2].split("\\:");
//            System.out.println(nodeDetails[0]);

            Boolean isAlreadyInMyNodeList = false;
            // ignore if it's already in myNodeList
            for (int j = 0; j < myNodeList.size(); j++) {
                if (myNodeList.get(j).getIp().equals(nodeDetails[0]) && myNodeList.get(j).getPort() == Integer.valueOf(nodeDetails[1])) {
                    isAlreadyInMyNodeList = true;
                }
            }
            if (!isAlreadyInMyNodeList) {
                Node nodeInList = new Node(nodeDetails[0], Integer.valueOf(nodeDetails[1]));
                myNodeList.add(nodeInList);
            }
        }

        // send myfile list to all the nodes in myNodeList
//        connectWithNodes();
//
//        refreshDataInClient();
//        this.displayRoutingTable();
        status = "1";
    }


    public static void initializeSearch(String msg) throws IOException {
        //SEARCH_FILES file_name
        String file_name = msg.split(" ")[1];
        String result_string = "";

        //length SEROK no_files IP port hops filename1 filename2 ... ...
//        ArrayList<String> results = new ArrayList<String>();
//        Pattern p = Pattern.compile(".*\\\\b" + file_name + "\\\\b.*");
//        Set<String> keys = fileDictionary.keySet();
//        Iterator<String> iterator = keys.iterator();
//
//        while (iterator.hasNext()) {
//            String candidate = iterator.next();
//            Matcher m = p.matcher(candidate);
//            if (m.matches()) {
//                results.add(candidate);
//                result_string.concat(candidate + " ");
//            }
//        }
//        System.out.println(result_string);
        String net_message = "SER " + Client.getIp() + " " + Client.getPort() + " " + msg.split(" ")[1] + " 0";
        net_message = String.format("%04d", net_message.length() + 5) + " " + net_message;
        searchFiles(net_message);
    }

    public static void searchFiles(String message) {
        //length SER IP port file_name hops
        String[] split = message.split(" ");
        String file_name = split[4];
        String result_string = "";
        String source_ip = split[2];
        int source_port = Integer.parseInt(split[3]);

        int hop_count = 0;
        if (split.length == 6) {
            hop_count = Integer.valueOf(split[5]);
        }

        //length SEROK tofind no_files IP port hops filename1 filename2 ... ...
        ArrayList<String> results = new ArrayList<String>();
        Pattern p = Pattern.compile("[a-zA-Z\\s]*" + file_name + "[a-zA-Z\\s]*",Pattern.CASE_INSENSITIVE);

        Set<String> keys = new HashSet<>(myFileList);
        Iterator<String> iterator = keys.iterator();

        ArrayList<String> nodes = new ArrayList<>();
        ArrayList<Node> nodelist = new ArrayList<>();

        //search in my files list
        while (iterator.hasNext()) {
            String candidate = iterator.next();
            Matcher m = p.matcher(candidate);
            if (m.matches()) {
                results.add(candidate);
//                System.out.println(candidate);
                result_string = result_string.concat(candidate + ",");
            }
        }
        if (results.size() > 0) {
            System.out.println("in if");

            String ret_message = "SEROK " + file_name + " " + results.size() + " " + Client.getIp() + " " + Client.getPort() + " " + (hop_count++) + " " + result_string;
            ret_message = String.format("%04d", ret_message.length() + 5) + " " + ret_message;
//            System.out.println(ret_message);
            unicast(ret_message, new Node(source_ip, source_port));
        } else {

            System.out.println("in else");

            keys = fileDictionary.keySet();
            iterator = keys.iterator();

            boolean found = false;

            System.out.println("before iterator");

            for (String key : keys) {
                System.out.println(key);
            }

            while (iterator.hasNext()) {
                System.out.println("a");

                String candidate = iterator.next();
                Matcher m = p.matcher(candidate);
                if (m.matches()) {
                    System.out.println("b");
                    nodes = fileDictionary.get(candidate);
                    for (String node : nodes) {
                        nodelist.add(new Node(node.split(":")[0], Integer.parseInt(node.split(":")[1])));
                    }
                    for (Node node : nodelist) {
                        System.out.println(node.getIp()+" - "+node.getPort());

                    }

                    for (int i = 0; i < nodelist.size(); i++) {
                        if(nodelist.get(i).getIp()==ip && nodelist.get(i).getPort()==port){
                            nodelist.remove(i);
                        }
                    }
                    //need to send search ok. not multicast
                    multicast(message, nodelist);
                    found = true;
                }
            }

            if (!found) {
                Collection<Node> values = bucketTable.values();
                ArrayList<Node> temValues= new ArrayList<>();

                Iterator<Node> setIterator = values.iterator();
                while (setIterator.hasNext()){
                    Node next = setIterator.next();
                    if (next.getIp()!=Client.ip || next.getPort()!=Client.port) {
                        temValues.add(next);
                    }
                }
                values.remove(new Node(Client.ip,Client.port));
                multicast(message, temValues);
            }
        }
    }

    public static synchronized void updateRountingTable() throws IOException {
        ArrayList<Node> temNodeList = new ArrayList<>();
//        System.out.println("start");
        for (Node node : myNodeList) {
            if (new Timestamp(System.currentTimeMillis()).getTime() - node.getTimeStamp() < 10000) {
                temNodeList.add(node);
            } else {
//                System.out.println("remove one"+ node.getIp()+" "+node.getPort());
                for (String file : fileDictionary.keySet()) {
//                    System.out.println("change file dictonary");
                    ArrayList<String> temFileNodeList = new ArrayList<String>();
                    for (String username : fileDictionary.get(file)) {
                        String[] split = username.split(":");
                        String ip = split[0];
                        int port = Integer.parseInt(split[1]);
                        if (ip != node.getIp() && port != node.getPort()) {
                            temFileNodeList.add(username);
//                            System.out.println("removed files"+username);
                        }
                    }
                    fileDictionary.replace(file, temFileNodeList);
                }

            }
        }

//        System.out.println("myNodeList " + myNodeList.size());
//        System.out.println("myTemList " + temNodeList.size());
        myNodeList = temNodeList;
        Iterator<Integer> it = bucketTable.keySet().iterator();
        while (it.hasNext())
        {
            Integer next = it.next();
            Node node = bucketTable.get(next);
            if (new Timestamp(System.currentTimeMillis()).getTime() - node.getTimeStamp() > 10000) {
//            System.out.println("time to response in bucket table " + (timestamp.getTime() - neighbour.getTimeStamp()));
//            System.out.println("before remove" + bucketTable.keySet());
                it.remove();
//            System.out.println("after remove" + bucketTable.keySet());
                findNodeFromBucket(next);
            }

        }
//        for (int key : bucketTable.keySet()) {
//            Node neighbour = bucketTable.get(key);
////            System.out.println("time now" + new Timestamp(System.currentTimeMillis()).getTime());
////            System.out.println("neighour time :" + neighbour.getTimeStamp());
////            System.out.println("time to response in bucket table " + (new Timestamp(System.currentTimeMillis()).getTime() - neighbour.getTimeStamp()));
//            if (new Timestamp(System.currentTimeMillis()).getTime() - neighbour.getTimeStamp() > 10000) {
////            System.out.println("time to response in bucket table " + (timestamp.getTime() - neighbour.getTimeStamp()));
////            System.out.println("before remove" + bucketTable.keySet());
//                bucketTable.remove(key);
////            System.out.println("after remove" + bucketTable.keySet());
//                findNodeFromBucket(key);
//            }
//
//        }
        // if my bucket table does not have connect ti some bucket we need to update that
        Set<Integer> keySet = bucketTable.keySet();
        for (int i = 0; i < k; i++) {
            if (!keySet.contains(i) && i != myBucketId) {
                findNodeFromBucket(i);
            }
        }
    }


    private static void connectWithNodes()  {
        // make fileDictionary string
        String FD = ";";
        for (String key : fileDictionary.keySet()) {
            ArrayList<String> nodesList = fileDictionary.get(key);
            FD += key + "=";
            for (int i = 0; i < nodesList.size(); i++) {
                FD += nodesList.get(i) + ",";
            }
            FD += "|";
        }

        // make bucketTable string
        String BL = ";";
        for (Integer key : bucketTable.keySet()) {
            Node bucketNode = bucketTable.get(key);
            BL += key + "=" + bucketNode.getIp() + ":" + bucketNode.getPort() + "|";
        }

        String message = "CWN " + ip + ":" + Integer.toString(port) + FD + " " + BL;
        message = String.format("%04d", message.length() + 5) + " " + message;

        multicast(message, myNodeList);
    }

    public static void HandleConnectWithNodes(String message) {
//        System.out.println(message);
        String[] arr = message.split("\\;");
        String[] split_msg = arr[0].split(" ");
        String[] nodeDetails = split_msg[2].split("\\:");
        String[] bucketList = arr[2].split("\\|");

        // save node in myNodeList
        Boolean isAlreadyInMyNodeList = false;
        for (int j = 0; j < myNodeList.size(); j++) {
            if (myNodeList.get(j).getIp().equals(nodeDetails[0]) && myNodeList.get(j).getPort() == Integer.valueOf(nodeDetails[1])) {
                isAlreadyInMyNodeList = true;
            }
        }
        if (!isAlreadyInMyNodeList) {
            Node nodeInList = new Node(nodeDetails[0], Integer.valueOf(nodeDetails[1]));
            myNodeList.add(nodeInList);
        }

        // update bucket table
        for (int i = 0; i < bucketList.length; i++) {
            if (bucketList[i].length() < 2) {
                continue;
            }
            String[] a1 = bucketList[i].split("\\=");
            String bucketNo = a1[0];
            if (a1.length < 1) {
                continue;
            };
            String[] bNode = a1[1].split("\\:");
            bucketTable.put(Integer.valueOf(bucketNo), new Node(bNode[0], Integer.valueOf(bNode[1])));
        }

        // update MyFileDictionary
        String fileList = arr[1];
//        System.out.println("File List *************** " + fileList);

        // save files to fileDicationary
        String[] records = fileList.split("\\|");
        for (int i = 0; i < records.length; i++) {
            if (records[i].length() < 2) {
                continue;
            }
            String[] a1 = records[i].split("\\=");
            String fileName = a1[0];
            if (a1.length < 1) {
                continue;
            };
            String[] nodes = a1[1].split("\\,");

            ArrayList<String> nodesContainingFile = fileDictionary.get(fileName);
            if (nodesContainingFile == null) {
                nodesContainingFile = new ArrayList<>();
            }
            for (int j = 0; j < nodes.length; j++) {
                if (nodes.length < 1) {
                    continue;
                }
                if (!nodesContainingFile.contains(nodes[j])) {
                    nodesContainingFile.add(nodes[j]);
                }
            }
            fileDictionary.put(fileName, nodesContainingFile);
        }
            displayRoutingTable();

    }

    public static void handleHeartBeatResponse(String message) {
        //length HEARTBEATOK IP_address port_no
        boolean is_Change = false;
        ArrayList<Node> temNodeList = new ArrayList<Node>();
        String[] splitMessage = message.split(" ");
        String ip = splitMessage[2];
        int port = Integer.parseInt(splitMessage[3]);
        for (Node node : myNodeList) {
            if (node.getIp().equals(ip) && node.getPort() == port) {
                node.setTimeStamp(new Timestamp(System.currentTimeMillis()).getTime());
                is_Change = true;
            }
            temNodeList.add(node);
        }
        myNodeList = temNodeList;

        if (!is_Change) {
            for (int key : bucketTable.keySet()) {
                Node node = bucketTable.get(key);
                if (node.getIp().equals(ip) && node.getPort() == port) {
                    node.setTimeStamp(new Timestamp(System.currentTimeMillis()).getTime());
                    bucketTable.replace(key, node);
                }
            }
        }
    }

    public static void sendHeartBeatReply(String message) throws IOException {
        String newMessage = "HEARTBEATOK " + ip + " " +port;
        newMessage = String.format("%04d", newMessage.length() + 5) + " " + newMessage;
        String[] splitMessage = message.split(" ");
        String userName= splitMessage[2]+":"+splitMessage[3];
        int bucketId = userName.hashCode()%k;
        Node node = new Node(splitMessage[2], Integer.parseInt(splitMessage[3]));

        boolean containsKey = bucketTable.containsKey(bucketId);
        if(containsKey==false){
            bucketTable.put(bucketId, node);
        }

        unicast(newMessage, node);
    }

    public static void receiveReplyFindNodeFromBucket(String message){
        String[] split_msg = message.split(" ");
        if ("null".equals(split_msg[3])) {
            return;
        }
        Node bucket_node = new Node(split_msg[3], Integer.valueOf(split_msg[4]));
        bucketTable.put(Integer.valueOf(split_msg[2]), bucket_node);
        // Node is still initializing and the returned node is a node from my bucket
        if (split_msg[2].equals(Integer.toString(myBucketId))) {
            // request myNodeList from that node
            findMyNodeListFromNode(bucket_node);
        }

        displayRoutingTable();
    }


    public static void handleSearchFilesResponse(String message) {
        //0056 SEROK American 1 10.10.13.152 3 1 American Pickers

        String[] split = message.split(" ", 4);

        String filename = split[2];

        if (currentSearch.equals(filename)) {

            split = split[3].split(" ", 2);
            int resultCount = Integer.parseInt(split[0]);

            split = split[1].split(" ", 4);
            String ip = split[0];
            String port = split[1];
            String hops= split[2];

            String fileSet = split[3];
            String[] split1 = fileSet.split(",");

            for (String string : split1) {
                searchFilesResultListModel.addElement(string + " - " + ip + ":" + port+" ("+hops+")");
            }
        }
    }

    public static void findNodeFromBucketReply(int bucketId, Node fromNode) {
        //FBMOK: Find Bucket Member OK
        if (fromNode.getIp().equals(ip) && fromNode.getPort() == port) {
//            System.out.println("Ignoring msg");
            return;
        }
//        System.out.println("Finding NOde !!!!");
        Node nodeFromBucket = null;
        String message = null;
        if (bucketTable.get(bucketId) != null) {
//            System.out.println("Node found" + bucketTable.get(bucketId).getPort());
            nodeFromBucket = bucketTable.get(bucketId);
            message = "FBMOK" + bucketId + " " + nodeFromBucket.getIp() + " " + nodeFromBucket.getPort();
        } else {
//            System.out.println("Node not found!");
            message = "FBMOK " + bucketId + " null null";
        }
        message = String.format("%04d", message.length() + 5) + " " + message;
//        System.out.println(fromNode.getIp() + fromNode.getPort());
        unicast(message, fromNode);
    }

//    private void refreshDataInClient() {
//        this.filesTableModel.setRowCount(0);
//
//        for (Map.Entry<String, ArrayList<String>> entry : fileDictionary.entrySet()) {
//            String filename = entry.getKey();
//            ArrayList<String> nodes = entry.getValue();
//
//            String nodesList = "";
//            for (String node : nodes) {
//                nodesList += (node + " ");
//            }
//            Object row[] = {filename, nodesList};
//            this.filesTableModel.addRow(row);
//        }
//
//        this.nodesTableModel.setRowCount(0);
//
//        String[] rowData = new String[]{"Bucket " + this.myBucketId, ""};
//        for (int i = 0; i < this.myNodeList.size(); i++) {
//            rowData[1] += (this.myNodeList.get(i).getIp() + ":" + this.myNodeList.get(i).getPort() + " ");
//        }
//        nodesTableModel.addRow(rowData);
//
//        for (int key : this.bucketTable.keySet()) {
//            if(key != this.myBucketId){
//                Node get = this.bucketTable.get(key);
//                nodesTableModel.addRow(new String[]{"Bucket " + key, (this.bucketTable.get(key).getIp() + ":" + this.bucketTable.get(key).getPort())});
//            }
//        }
//    }
//
//
}
