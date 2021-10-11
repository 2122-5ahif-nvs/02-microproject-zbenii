package at.htl.centermanager.entity;

import at.htl.centermanager.LocalDateDeserializer;
import at.htl.centermanager.LocalDateSerializer;
import at.htl.centermanager.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Contract.getById", query = "select co from Contract co where co.id = ?1"),
        @NamedQuery(name = "Contract.getByCompName", query = "select co from Contract co where upper( co.company.name) like ?1"),
        @NamedQuery(name = "Contract.getTotalRentByCompName", query = "select sum(co.rentalCost) from Contract co where upper(co.company.name) like ?1"),
        @NamedQuery(name = "Contract.getTotalSpaceByCompName", query = "select sum(co.shop.sizeM2) from Contract co where upper(co.company.name) like ?1"),
        @NamedQuery(name = "Contract.getShopsByCompName", query = "select co.shop from Contract co where upper( co.company.name) like ?1"),
        @NamedQuery(name = "Contract.getInfoByCompName", query = "select co,co.shop from Contract co where upper( co.company.name) like ?1")
})
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "DATE_SIGNED")
    private LocalDate contractSigned;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "DATE_END")
    private LocalDate contractEnd;

    @ManyToOne
    private Shop shop;

    @Column(name = "RENT_COST")
    private double rentalCost;

    @ManyToOne
    @JoinColumn(name = "company_name")
    private Company company;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String currency;

    public Contract(LocalDate contractSigned, LocalDate contractEnd, Shop shop, double rentalCost, Company company) {
        setContractEnd(contractEnd);
        setContractSigned(contractSigned);
        setRentalCost(rentalCost);
        setCompany(company);
        setShop(shop);
        setCurrency("€");
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", contractSigned=" + contractSigned +
                ", contractEnd=" + contractEnd +
                ", shop=" + shop +
                ", rentalCost=" + rentalCost +
                ", company=" + company +
                ", currency='" + currency + '\'' +
                '}';
    }

    public Contract() {
    }

    public LocalDate getContractSigned() {
        return contractSigned;
    }

    public LocalDate getContractEnd() {
        return contractEnd;
    }


    public double getRentalCost() {
        return rentalCost;
    }

    public void setContractSigned(LocalDate contractSigned) {
        this.contractSigned = contractSigned;
    }

    public void setContractEnd(LocalDate contractEnd) {
        this.contractEnd = contractEnd;
    }

    public void setRentalCost(double rentalCost) {
        this.rentalCost = rentalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return Objects.equals(shop, contract.shop) && Objects.equals(company, contract.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractSigned, contractEnd, shop, rentalCost, company, currency);
    }
}
