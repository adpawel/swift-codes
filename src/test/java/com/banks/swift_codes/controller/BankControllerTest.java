package com.banks.swift_codes.controller;


import com.banks.swift_codes.model.Bank;
import com.banks.swift_codes.model.ISO2Response;
import com.banks.swift_codes.model.SwiftCodeResponse;
import com.banks.swift_codes.repository.BankRepository;
import com.banks.swift_codes.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private BankService bankService;

    @InjectMocks
    private BankController bankController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bankController).build();
    }

    @Test
    public void testGetBankBySwiftCodeNotFound() throws Exception {
        String swiftCode = "ABCDEF12XXX";
        when(bankRepository.findById(swiftCode)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/swift-codes/{swiftCode}", swiftCode))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateBank() throws Exception {
        Bank bank = new Bank("123 Street", "Test Bank", "PL", "Poland", true, "ABCDEF12XXX");
        when(bankRepository.save(any(Bank.class))).thenReturn(bank);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"123 Street\",\"bankName\":\"Test Bank\",\"countryISO2\":\"PL\",\"countryName\":\"Poland\",\"isHeadquarter\":true,\"swiftCode\":\"ABCDEF12XXX\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Bank with SWIFT code ABCDEF12XXX added successfully."));
    }

    @Test
    public void testGetBankByISO2CodeNotFound() throws Exception {
        String iso2code = "PL";
        when(bankRepository.findByCountryISO2(iso2code)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/swift-codes/country/{iso2code}", iso2code))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBankNotFound() throws Exception {
        String swiftCode = "ABCDEF12XXX";
        when(bankRepository.findById(swiftCode)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/swift-codes/{swiftCode}", swiftCode))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Bank with SWIFT code " + swiftCode + " not found."));
    }
}
