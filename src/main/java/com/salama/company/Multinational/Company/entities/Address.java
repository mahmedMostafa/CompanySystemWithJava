package com.salama.company.Multinational.Company.entities;


import com.salama.company.Multinational.Company.entities.base.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String name;

    @ManyToMany(mappedBy = "addresses")
    private Set<Employee> employees;

    public Address() {
    }

    //because the employee is the owner of this relationship
    public void addEmployee(Employee employee){
        this.employees.add(employee);
        employee.getAddresses().add(this);
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
