package com.fastspark.fastspark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FastsparkApplication {

	public static void main(String[] args) {

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




		SpringApplication.run(FastsparkApplication.class, args);
	}

}
