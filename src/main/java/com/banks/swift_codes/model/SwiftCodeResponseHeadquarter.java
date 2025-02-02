package com.banks.swift_codes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwiftCodeResponseHeadquarter extends SwiftCodeResponse {
    private List<BranchDTO> branches;

    @Data
    public static class BranchDTO {
        private String address;
        private String bankName;
        private String countryISO2;
        private boolean isHeadquarter;
        public String swiftCode;
    }
}
