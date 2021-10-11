package at.htl.centermanager.boundary;

import at.htl.centermanager.entity.Shop;
import at.htl.centermanager.repository.ShopRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class ShopServiceTest {

    @Inject
    ShopService shopService;

    @Inject
    ShopRepository shopRepository;


    @Test
    void testGetAllShops() {

        List<Shop> actualList = given()
                .when()
                .get("/shops")
                .then()
                .log()
                .body()
                .statusCode(202)
                .extract()
                .jsonPath()
                .getList(".", Shop.class);

        assertThat(actualList)
                .isEqualTo(shopRepository.getShopList());

    }

    @Test
    void testGetSingleShop() {
        Shop actual = given()
                .when()
                .get("/shops/1")
                .then()
                .log()
                .body()
                .statusCode(202)
                .extract()
                .jsonPath()
                .getObject(".", Shop.class);

        assertThat(actual)
                .isEqualTo(shopRepository.getShopById(1));

    }

    @Test
    void testAddShop() {

        Shop shop = new Shop(55.5, "2OG");
        shop.setId(-1);

        String actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(shop)
                .post("/shops")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo(shopRepository.getShopById(8).toString() + " added!");

        Shop existing = new Shop(221.2, "EG");
        existing.setId(3);

        actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(existing)
                .post("/shops")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo(existing.toString() + " already exists!");

    }

    @Test
    void testDeleteShop() {

        String actual = given()
                .when()
                .delete("/shops/7")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Shop with id 7 deleted!");

        actual = given()
                .when()
                .delete("/shops/120")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Shop with id 120 doesn't exist!");


    }

    @Test
    void testUpdateShop() {

        Shop updated = new Shop(230.5, "1OG");
        updated.setId(1);

        String actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(updated)
                .put("/shops/1")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Shop with id " + updated.getId() + " updated!");

        actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(updated)
                .put("/shops/120")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Shop with id 120 doesn't exist!");

    }
}