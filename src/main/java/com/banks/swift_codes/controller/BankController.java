package com.banks.swift_codes.controller;

import com.banks.swift_codes.model.Bank;
import com.banks.swift_codes.model.ISO2Response;
import com.banks.swift_codes.model.SwiftCodeResponse;
import com.banks.swift_codes.repository.BankRepository;
import com.banks.swift_codes.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/swift-codes")
@Validated
public class BankController {
    private final BankRepository bankRepository;
    private final BankService bankService;

    public BankController(BankRepository bankRepository, BankService bankService) {
        this.bankService = bankService;
        this.bankRepository = bankRepository;
    }

    @GetMapping("/{swiftCode}")
    public ResponseEntity<SwiftCodeResponse> getBankBySwiftCode(@PathVariable String swiftCode) {
        Optional<Bank> bank = bankRepository.findById(swiftCode);
        if(bank.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        SwiftCodeResponse response = bankService.getBankBySwiftCode(swiftCode, bank, bankRepository);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createBank(@RequestBody Bank bank) {
        bankRepository.save(bank);
        return ResponseEntity.ok(Map.of("message", "Bank with SWIFT code " + bank.getSwiftCode() + " added successfully."));
    }


    @GetMapping("/country/{iso2code}")
    public ResponseEntity<ISO2Response> getBankByISO2Code(@PathVariable String iso2code) {
        List<Bank> banks = bankRepository.findByCountryISO2(iso2code);
        if (banks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ISO2Response response = bankService.getBankByISO2Code(iso2code, banks);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<Map<String, String>> deleteBank(@PathVariable String swiftCode) {
        Optional<Bank> bank = bankRepository.findById(swiftCode);

        if (bank.isPresent()) {
            bankRepository.delete(bank.get());
            return ResponseEntity.ok(Map.of("message", "Bank " + swiftCode + " deleted successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Bank with SWIFT code " + swiftCode + " not found."));
        }
    }
}
