package com.banks.swift_codes.service;

import com.banks.swift_codes.model.Bank;
import com.banks.swift_codes.model.ISO2Response;
import com.banks.swift_codes.model.SwiftCodeResponse;
import com.banks.swift_codes.model.SwiftCodeResponseHeadquarter;
import com.banks.swift_codes.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    public SwiftCodeResponse getBankBySwiftCode(String swiftCode, Optional<Bank> bank, BankRepository bankRepository){
        SwiftCodeResponse response = null;

        if(bank.isPresent()){
            if(bank.get().isHeadquarter()){
                List<Bank> branches = bankRepository.findBySwiftCodeStartingWith(swiftCode.toUpperCase().substring(0, 8));
                List<SwiftCodeResponseHeadquarter.BranchDTO> branchDTOList = branches.stream()
                        .map(branch -> {
                            SwiftCodeResponseHeadquarter.BranchDTO dto = new SwiftCodeResponseHeadquarter.BranchDTO();
                            dto.setAddress(branch.getAddress());
                            dto.setBankName(branch.getBankName());
                            dto.setCountryISO2(branch.getCountryISO2());
                            dto.setHeadquarter(branch.isHeadquarter());
                            dto.setSwiftCode(branch.getSwiftCode());
                            return dto;
                        }).toList();

                response = new SwiftCodeResponseHeadquarter();
                setAllAttributes(bank, response);
                ((SwiftCodeResponseHeadquarter) response).setBranches(branchDTOList);
            }
            else{
                response = new SwiftCodeResponse();
                setAllAttributes(bank, response);
            }
        }

        return response;
    }

    private void setAllAttributes(Optional<Bank> bank, SwiftCodeResponse response) {
        if(bank.isPresent()){
            response.setBankName(bank.get().getBankName());
            response.setAddress(bank.get().getAddress());
            response.setCountryISO2(bank.get().getCountryISO2());
            response.setCountryName(bank.get().getCountryName());
            response.setHeadquarter(bank.get().isHeadquarter());
            response.setSwiftCode(bank.get().getSwiftCode());
        }
    }

    public ISO2Response getBankByISO2Code(String iso2code, List<Bank> banks){
        List<ISO2Response.SwiftCodeDTO> swiftCodeDTOList = banks.stream()
                .map(bank -> {
                    ISO2Response.SwiftCodeDTO dto = new ISO2Response.SwiftCodeDTO();
                    dto.setBankName(bank.getBankName());
                    dto.setAddress(bank.getAddress());
                    dto.setCountryISO2(bank.getCountryISO2());
                    dto.setHeadquarter(bank.isHeadquarter());
                    dto.setSwiftCode(bank.getSwiftCode());
                    return dto;
                })
                .toList();

        ISO2Response response = new ISO2Response();
        response.setCountryISO2(iso2code);
        response.setCountryName(banks.get(0).getCountryName());
        response.setSwiftCodes(swiftCodeDTOList);

        return response;
    }

}
