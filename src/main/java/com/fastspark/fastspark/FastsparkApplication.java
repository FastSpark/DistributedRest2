package com.fastspark.fastspark;

import java.util.*;

import com.fastspark.fastspark.model.Client;
import com.fastspark.fastspark.model.HeartBeatHandler;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@SpringBootApplication
public class FastsparkApplication {

	public static void main(String[] args) {
	    int k=3;
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
        System.out.println(Client.getPort()+":"+Client.getIp());
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
        application.run(args);
        Thread heartBeatHandeler = new Thread(new HeartBeatHandler());
        heartBeatHandeler.start();

    }

}
