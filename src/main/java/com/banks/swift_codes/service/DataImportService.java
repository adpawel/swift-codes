package com.banks.swift_codes.service;

import com.banks.swift_codes.model.Bank;
import com.banks.swift_codes.repository.BankRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataImportService {

    @Autowired
    private BankRepository bankRepository;

    @Transactional
    public void importBanksFromCSV(String filePath) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(new FileReader(filePath));
        String[] nextRecord;

        // Pomijamy nagłówek
        csvReader.readNext();

        List<Bank> banks = new ArrayList<>();

        while ((nextRecord = csvReader.readNext()) != null) {
            Bank bank = new Bank();

            bank.setCountryISO2(nextRecord[0]);
            bank.setSwiftCode(nextRecord[1]);
            bank.setBankName(nextRecord[3]);
            bank.setAddress(nextRecord[4]);
            bank.setCountryName(nextRecord[6]);
            bank.setHeadquarter(nextRecord[1].endsWith("XXX"));

            banks.add(bank);
        }

        csvReader.close();

        bankRepository.saveAll(banks);
    }
}

