package at.htl.centermanager.repository;

import at.htl.centermanager.entity.Shop;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShopRepositoryTest {

    @Inject
    ShopRepository repo;

    @BeforeAll
    static void beforeAll() {

    }

    @Test
    public void testSingleton() {

    }

    @Test
    @Transactional
    @Order(1)
    public void testGetAllShops() {
        assertThat(repo.getShopList().size())
                .isEqualTo(7);
    }

    @Test
    @Transactional
    @Order(2)
    public void testGetShop() {
        assertThat(repo.getShopById(3)).isNotNull();
        assertThat(repo.getShopById(3).getSizeM2())
                .isEqualTo(57.3);
        assertThat(repo.getShopById(123))
                .isNull();
    }

    @Test
    @Transactional
    @Order(3)
    public void testAddShop() {
        Shop test1 = new Shop(44.2, "EG");
        test1.setId(-1);
        Shop test2 = new Shop(100.3, "1OG");
        test2.setId(1);

        boolean isAdded1 = repo.addShop(test1);
        boolean isAdded2 = repo.addShop(test2);

        assertThat(repo.getShopList().size())
                .isEqualTo(8);
        assertThat(repo.getShopById(8))
                .isEqualTo(test1);
        assertThat(isAdded1)
                .isTrue();
        assertThat(isAdded2)
                .isFalse();
    }

    @Test
    @Transactional
    @Order(4)
    public void testDeleteShop() {
        boolean del1 = repo.deleteShopById(7);
        boolean del2 = repo.deleteShopById(102);

        assertThat(repo.getShopList().size())
                .isEqualTo(7);
        assertThat(del1)
                .isTrue();
        assertThat(del2)
                .isFalse();
    }

    @Test
    @Transactional
    @Order(5)
    public void testUpdateShop() {
        Shop previous = repo.getShopById(1);
        Shop newShop = new Shop(300.3, "1OG");
        newShop.setId(2);

        boolean isUpdated = repo.updateShop(2, newShop);
        boolean isUpdated2 = repo.updateShop(5, newShop);

        Shop actual = repo.getShopById(2);

        assertThat(newShop)
                .isEqualTo(actual);
        assertThat(isUpdated)
                .isTrue();
        assertThat(isUpdated2)
                .isFalse();
    }

}
