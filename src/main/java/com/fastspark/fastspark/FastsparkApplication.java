package com.fastspark.fastspark;

import java.io.IOException;
import java.net.*;
import java.util.*;

import com.fastspark.fastspark.model.Client;
import com.fastspark.fastspark.model.HeartBeatHandler;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FastsparkApplication {

	public static void main(String[] args) {
		int k = 3;
		SpringApplication application = new SpringApplication(FastsparkApplication.class);
		final String[] fileList = {
				"Adventures of Tintin",
				"Jack and Jill",
				"Glee",
				"The Vampire Diaries",
				"King Arthur",
				"Windows XP",
				"Harry Potter7",
				"Kung Fu Panda",
				"Lady Gaga",
				"Twilight",
				"Windows 8",
				"Mission Impossible",
				"Turn Up The Music",
				"Super Mario",
				"American Pickers",
				"Microsoft Office 2010",
				"Happy Feet",
				"Modern Family",
				"American Idol",
				"Hacking for Dummies"
		};

		//read bootrap server ip and node ip


		String ip = null;


		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("IP Address : " + ip);
		System.out.print("Input Port : ");
		String input = scanner.nextLine();
		int port = Integer.parseInt(input);

		Client.setPort(port);
		Client.setIp(ip);
		Client.setBootrapIp(ip);

		Map<String, Object> map = new HashMap<>();
		map.put("server.port", input);
		application.setDefaultProperties(map);

		String address = ip + ":" + port;
		int myBucketId = address.hashCode();
		myBucketId = myBucketId % k;
		Client.setMyBucketId(myBucketId);

		Map<String, ArrayList<String>> fileDictionary = new HashMap<>();

		int randomFileCount = new Random().nextInt(3) + 3;
		System.out.println("Initializing node with " + randomFileCount + " files...");
		ArrayList<String> myFileList = new ArrayList<>();

		for (int i = 0; i < randomFileCount; i++) {
			int randomIndex = new Random().nextInt(fileList.length);
			String selectedFile = fileList[randomIndex];
			myFileList.add(selectedFile);

			ArrayList<String> nodesContainingFile = fileDictionary.get(selectedFile);
			if (nodesContainingFile == null) {
				nodesContainingFile = new ArrayList<>();
			}
			nodesContainingFile.add(address);
			fileDictionary.put(selectedFile, nodesContainingFile);
		}

		Client.setMyFileList(myFileList);
		Client.setFileDictionary(fileDictionary);

		Map<String, String> result = new HashMap<>();
		DatagramSocket receiveSock = null;
		String username = ip + ":" + port;
		String msg = " REG " + ip + " " + port + " " + username;
		msg = "00" + Integer.toString(msg.length()) + msg;

		try {
			receiveSock = new DatagramSocket(Client.getPort() - 1);
			receiveSock.setSoTimeout(10000);
			byte[] buffer = new byte[65536];
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(Client.getIp()), 55555);
			receiveSock.send(datagramPacket);
			receiveSock.receive(incoming);
			receiveSock.close();
			String receivedMessage = new String(incoming.getData(), 0, incoming.getLength());
			application.run(args);
			Client.handleRegisterResponse(receivedMessage);

			Thread heartBeatHandeler = new Thread(new HeartBeatHandler());
			heartBeatHandeler.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}