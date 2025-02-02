package com.banks.swift_codes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "banks")
public class Bank {
    private String address;
    @Column(nullable = false)
    private String bankName;
    @Column(
            nullable = false,
            length = 2
    )
    @Size( min = 2, max = 2, message = "Country ISO 2 name must be 2 characters long")
    private String countryISO2;
    @Column(nullable = false)
    private String countryName;
    private boolean isHeadquarter;
    @Id
    @Column(
            nullable = false,
            length = 11
    )
    @Size( min = 11, max = 11, message = "SWIFT code must be 11 characters long")
    private String swiftCode;
}
