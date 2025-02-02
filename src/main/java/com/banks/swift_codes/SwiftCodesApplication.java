package com.banks.swift_codes;

import com.banks.swift_codes.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SwiftCodesApplication implements CommandLineRunner {

	@Autowired
	private DataImportService dataImportService;


    public static void main(String[] args) {
		SpringApplication.run(SwiftCodesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		String filePath = "/app/data.csv";
//
//		dataImportService.importBanksFromCSV(filePath);
	}

}
