package at.htl.centermanager.repository;

import at.htl.centermanager.entity.Company;
import at.htl.centermanager.entity.Contract;
import at.htl.centermanager.entity.Shop;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ContractRepository implements PanacheRepository<Contract> {

    @Inject
    ShopRepository shopRepo;

    @Inject
    CompanyRepository companyRepo;

    public ContractRepository() {
       /*
        this.addContract(new Contract(LocalDate.of(2009,5,2),LocalDate.of(2021,5,2),"Bauhaus",60300.15,4));
        this.addContract(new Contract(LocalDate.of(2009,4,17),LocalDate.of(2021,4,17),"Interspar",26808.00,5));
        this.addContract(new Contract(LocalDate.of(2012,7,5),LocalDate.of(2021,7,5),"Intersport Winninger",16388.54,6));
        */
    }

    @Transactional
    @PostConstruct
    public void insertTestData() {
        Shop s = shopRepo.getShopById(1);
        Company c = companyRepo.getCompanyByName("Mediamarkt");
        persist(new Contract(LocalDate.of(2010, 2, 1), LocalDate.of(2021, 2, 1), s, 50100.50, c));

        s = shopRepo.getShopById(2);
        c = companyRepo.getCompanyByName("McDonalds");
        persist(new Contract(LocalDate.of(2010, 8, 15), LocalDate.of(2021, 8, 15), s, 7600.32, c));

        s = shopRepo.getShopById(3);
        c = companyRepo.getCompanyByName("Snipes");
        persist(new Contract(LocalDate.of(2016, 9, 30), LocalDate.of(2022, 9, 30), s, 4700.21, c));

        s = shopRepo.getShopById(4);
        c = companyRepo.getCompanyByName("Burgerista");
        persist(new Contract(LocalDate.of(2014, 10, 21), LocalDate.of(2022, 10, 21), s, 6500.09, c));

    }

    public List<Contract> getContractList() {
        return listAll();
    }

    @Transactional
    public boolean addContract(Contract c) {
        List<Contract> current = getContractList();

        //necessary for karate tests because database gets deleted
        if (current.size() == 0) {
            shopRepo.insertTestData();
            companyRepo.insertTestData();
            insertTestData();
            current = getContractList();
        }

        if (!current.contains(c)) {
            if ((shopRepo.getShopById(c.getShop().getId()) != null) && (companyRepo.getCompanyByName(c.getCompany().getName()) != null)) {
                if (current.stream().noneMatch(co -> co.getShop().getId() == c.getShop().getId())) {
                    persist(c);
                    return true;
                }
            }

        }
        return false;
    }

    public Contract getContractById(int id) {
        return find("#Contract.getById", id).singleResult();
    }

    @Transactional
    public boolean deleteContractById(int id) {
        Contract contract = getContractById(id);

        if (contract == null) {
            return false;
        }

        delete(contract);
        return true;
    }

    @Transactional
    public boolean updateContract(int id, Contract updated) {
        Contract previous = getContractById(id);

        if (previous == null
                || !(previous.getCompany().equals(updated.getCompany())) ||
                !(previous.getContractSigned().equals(updated.getContractSigned())) ||
                (getContractList().stream().filter(c -> c.getId() != id).anyMatch(c -> c.getShop().getId() == updated.getShop().getId()))) {
            return false;
        }

        previous.setContractEnd(updated.getContractEnd());
        previous.setCurrency(updated.getCurrency());
        previous.setRentalCost(updated.getRentalCost());
        previous.setShop(updated.getShop());

        return true;
    }

    public double getTotalRentForCompany(String compName) {
        TypedQuery<Double> query = this.getEntityManager().createNamedQuery("Contract.getTotalRentByCompName", Double.class)
                .setParameter(1, compName.toUpperCase());

        try {
            return query.getSingleResult();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public double getTotalRentSpaceForCompany(String compName) {
        TypedQuery<Double> query = this.getEntityManager().createNamedQuery("Contract.getTotalSpaceByCompName", Double.class)
                .setParameter(1, compName.toUpperCase());

        try {
            return query.getSingleResult();
        } catch (Exception ex) {
            return 0.0;
        }

    }

    public List<Contract> getContractsByCompName(String compName) {
        return find("#Contract.getByCompName", compName).list();
    }

    public List<Shop> getShopsByCompName(String compName) {

        TypedQuery<Shop> query = this.getEntityManager().createNamedQuery("Contract.getShopsByCompName", Shop.class)
                .setParameter(1, compName.toUpperCase());

        return Collections.unmodifiableList(query.getResultList());
    }

    public Object getInfoByCompName(String compName){
        return this.getEntityManager().createNamedQuery("Contract.getInfoByCompName")
                .setParameter(1,compName.toUpperCase()).getSingleResult();

    }
}
