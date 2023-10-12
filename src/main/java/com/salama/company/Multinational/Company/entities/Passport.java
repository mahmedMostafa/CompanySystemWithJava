package com.salama.company.Multinational.Company.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_country_name")
    @JsonProperty("origin_country_name")
    @NotBlank
    private String originCountryName;

    @Column(name = "passport_number")
    @JsonProperty("passport_number")
    @NotNull
    private Long passportNumber;

    @Column(name = "countries_visited")
    @JsonProperty("countries_visited")
    private List<String> countriesVisited;

    @OneToOne(mappedBy = "passport")
    @JsonIgnore
    private Employee employee;

    public Passport() {
    }

    public Passport(Long id, String originCountryName, Long passportNumber, List<String> countriesVisited) {
        this.id = id;
        this.originCountryName = originCountryName;
        this.passportNumber = passportNumber;
        this.countriesVisited = countriesVisited;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginCountryName() {
        return originCountryName;
    }

    public void setOriginCountryName(String originCountryName) {
        this.originCountryName = originCountryName;
    }

    public Long getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(Long passportNumber) {
        this.passportNumber = passportNumber;
    }

    public List<String> getCountriesVisited() {
        return countriesVisited;
    }

    public void setCountriesVisited(List<String> countriesVisited) {
        this.countriesVisited = countriesVisited;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", originCountryName='" + originCountryName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", countriesVisited=" + countriesVisited +
                '}';
    }
}
