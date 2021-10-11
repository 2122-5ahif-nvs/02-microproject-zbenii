package at.htl.centermanager.repository;

import at.htl.centermanager.entity.Company;
import at.htl.centermanager.entity.CompanyCategory;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.persistence.*;
import javax.transaction.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class CompanyRepository implements PanacheRepository<Company> {

    public CompanyRepository() {
    }

    @Transactional
    @PostConstruct
    public void insertTestData() {
        persist(new Company("MediaMarkt", 20, CompanyCategory.TECHNOLOGY));
        persist(new Company("McDonalds", 10, CompanyCategory.GASTRONOMY));
        persist(new Company("Snipes", 8, CompanyCategory.CLOTHING));
        persist(new Company("Burgerista", 6, CompanyCategory.GASTRONOMY));
        persist(new Company("Bauhaus", 25, CompanyCategory.LIVING_AND_HOUSEHOLD));
        persist(new Company("Interspar", 20, CompanyCategory.FOOD));
        persist(new Company("Intersport Winninger", 17, CompanyCategory.SPORTS));

    }

    public List<Company> getCompanyList() {
        return listAll();
    }

    @Transactional
    public boolean addCompany(Company c) {
        if (getCompanyByName(c.getName()) == null) {
            persist(c);
            return true;
        }

        return false;
    }

    @Transactional
    public List<Company> addCompanies(List<Company> comps){
        List<Company> added = new LinkedList<>();
        comps.forEach(c -> {
        if (getCompanyByName(c.getName()) == null) {
            persist(c);
            added.add(c);
        }
        });

        return added;
    }

    @Transactional
    public boolean deleteCompanyByName(String compName) {
        Company company = getCompanyByName(compName);

        if (company == null) {
            return false;
        }

        delete(company);

        return true;
    }

    @Transactional
    public boolean updateCompany(String compName, Company updated) {
        Company previous = getCompanyByName(compName);
        if (previous == null || !previous.getName().toUpperCase().equals(updated.getName().toUpperCase())) {
            return false;
        }

        previous.setCategory(updated.getCategory());
        previous.setEmployeeAmount(updated.getEmployeeAmount());

        return true;
    }

    public Company getCompanyByName(String compName) {
        try {
            return this.find("#Company.getByName", compName.toUpperCase()).singleResult();
        }catch (Exception e){
            return null;
        }
    }
}
