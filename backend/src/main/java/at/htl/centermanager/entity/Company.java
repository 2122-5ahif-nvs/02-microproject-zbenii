package at.htl.centermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "Company.getByName", query = "select c from Company c where upper(c.name) = ?1")
@XmlRootElement
public class Company {

    @Id
    private String name;

    @Column(name = "EMP_AMOUNT")
    private int employeeAmount;

    private CompanyCategory category;

    //not working yet
    @JsonbTransient
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Contract> contracts = new ArrayList<>();

    public void addContract(Contract c) {
        contracts.add(c);
    }

    public List<Contract> getContracts() {
        return contracts;
    }


    public Company(String name, int employeeAmount, CompanyCategory category) {
        setName(name);
        setEmployeeAmount(employeeAmount);
        setCategory(category);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", employeeAmount=" + employeeAmount +
                ", category=" + category +
                '}';
    }

    public Company() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeeAmount(int employeeAmount) {
        this.employeeAmount = employeeAmount;
    }

    public void setCategory(CompanyCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getEmployeeAmount() {
        return employeeAmount;
    }

    public CompanyCategory getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return name.equals(company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
