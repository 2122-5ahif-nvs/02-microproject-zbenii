package at.htl.centermanager.repository;

import at.htl.centermanager.entity.Company;
import at.htl.centermanager.entity.CompanyCategory;
import at.htl.centermanager.entity.Shop;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class ShopRepository implements PanacheRepository<Shop> {
    public ShopRepository() {
    }

    @Transactional
    @PostConstruct
    public void insertTestData() {
        persist(new Shop(304.5, "1OG"));
        persist(new Shop(102.4, "1OG"));
        persist(new Shop(57.3, "EG"));
        persist(new Shop(46.9, "1OG"));
        persist(new Shop(620.3, "2OG"));
        persist(new Shop(143.8, "EG"));
        persist(new Shop(104.2, "EG"));

    }

    public List<Shop> getShopList() {
        return listAll();
    }

    @Transactional
    public boolean addShop(Shop s) {
        if (getShopById(s.getId()) == null) {
            if (s.getId() == -1) {
                s.setId(0);
            }
            persist(s);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean deleteShopById(int id) {
        Shop shop = getShopById(id);

        if (shop == null) {
            return false;
        }

        delete(shop);

        return true;
    }

    @Transactional
    public boolean updateShop(int id, Shop updated) {
        Shop previous = getShopById(id);

        if (previous == null || previous.getId() != updated.getId()) {
            return false;
        }

        previous.setFloor(updated.getFloor());
        previous.setSizeM2(updated.getSizeM2());

        return true;
    }

    public Shop getShopById(int id) {
        return find("#Shop.getById", id).singleResult();
    }
}
