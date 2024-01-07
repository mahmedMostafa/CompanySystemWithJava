package com.salama.company.Multinational.Company.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.salama.company.Multinational.Company.entities.base.BaseEntity;
import com.salama.company.Multinational.Company.entities.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(
        name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"}, name = "UniquePersonInformation")
        }
)
public class Employee extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "employee_name")
    private String name;

    @NotNull
    private int age;

    @NotNull
    private float salary;

    @NotBlank
    @Email
    @Column(name = "email", unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id", unique = true)
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    @JsonBackReference
    private List<Employee> managees;

    //The Employee is the owner of this relationship
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "passport_id", referencedColumnName = "id", unique = true)
    //unique is important here to complete the mapping
    private Passport passport;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", referencedColumnName = "id") //default is nullable
    private Department department;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "new_employee_skills",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id", referencedColumnName = "skillId", unique = true)}
    )
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "employee_addresses",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "address_id", referencedColumnName = "addressId")}
    )
    private Set<Address> addresses = new HashSet<>();

    /*
     * This will create a separate table to store the employee nicknames with a table name of employee_nicknames
     * the nickname field will be the column name, the joined column will be the employee_id which references the id
     * the employee_id is a composite of the employee id and the nickname itself
     * we could also use it with embeddable classes
     */
    @ElementCollection
    @CollectionTable(name = "employee_nicknames", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "nickname")
    private Collection<String> nicknames = new ArrayList<>();

    public Employee() {
    }

    public Employee(String name, int age, float salary, String email, String password, Passport passport, Department department) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.email = email;
        this.password = password;
        this.passport = passport;
        this.department = department;
    }

    public Employee(String name, int age, float salary, String email) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getManagees() {
        return managees;
    }

    public void setManagees(List<Employee> managees) {
        this.managees = managees;
    }

    public Collection<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(Collection<String> nicknames) {
        this.nicknames = nicknames;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", manager=" + manager +
                ", managees=" + managees +
                ", passport=" + passport +
                ", department=" + department +
                ", skills=" + skills +
                ", addresses=" + addresses +
                ", nicknames=" + nicknames +
                '}';
    }


    //since this is an entity, we only care about the id for two entities to be equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
