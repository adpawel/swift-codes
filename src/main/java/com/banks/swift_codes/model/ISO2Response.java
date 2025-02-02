package com.banks.swift_codes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ISO2Response {
    private String countryISO2;
    private String countryName;
    private List<SwiftCodeDTO> swiftCodes;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SwiftCodeDTO {
        private String address;
        private String bankName;
        private String countryISO2;
        private boolean isHeadquarter;
        public String swiftCode;
    }
}
