package com.banks.swift_codes.repository;

import com.banks.swift_codes.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, String> {
    List<Bank> findByCountryISO2(String iso2Code);

    @Query("SELECT b FROM Bank b WHERE b.swiftCode LIKE :prefix% AND b.isHeadquarter = false")
    List<Bank> findBySwiftCodeStartingWith(@Param("prefix") String prefix);
}
