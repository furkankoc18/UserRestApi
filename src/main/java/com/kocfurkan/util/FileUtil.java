package com.kocfurkan.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class FileUtil {

	public Properties getPropertiesFile(String baseUrl) {

		try {
			InputStream inputStream = new FileInputStream(baseUrl);
			Properties properties = new Properties();
			properties.load(inputStream);
			if (properties.isEmpty()) {
				System.out.println("Properties file is empty => " + baseUrl);
				return properties;
			} else {
				return properties;
			}
		} catch (Exception e) {
			System.out.println("Properties file not found => " + e.toString());
		}
		return null;
	}

}
