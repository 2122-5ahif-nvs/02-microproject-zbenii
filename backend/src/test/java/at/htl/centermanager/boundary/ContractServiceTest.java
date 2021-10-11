package at.htl.centermanager.boundary;

import at.htl.centermanager.entity.Contract;
import at.htl.centermanager.entity.Shop;
import at.htl.centermanager.repository.CompanyRepository;
import at.htl.centermanager.repository.ContractRepository;
import at.htl.centermanager.repository.ShopRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class ContractServiceTest {

    @Inject
    ContractService contractService;

    @Inject
    ContractRepository contractRepository;

    @Inject
    ShopRepository shopRepo;

    @Inject
    CompanyRepository compRepo;

    @Test
    public void testGetAllContracts() {

        List<Contract> actualList = given()
                .when()
                .get("/contracts")
                .then()
                .log()
                .body()
                .statusCode(202)
                .extract()
                .jsonPath()
                .getList(".", Contract.class);

        assertThat(actualList)
                .isEqualTo(contractRepository.getContractList());
    }

    @Test
    public void testGetSingleContract() {
        Contract actual = given()
                .when()
                .get("/contracts/1")
                .then()
                .log()
                .body()
                .statusCode(202)
                .extract()
                .jsonPath()
                .getObject(".", Contract.class);

        assertThat(actual)
                .isEqualTo(contractRepository.getContractById(1));
    }

    @Test
    public void testGetContractsForCompany() {
        List<Shop> actualList = given()
                .when()
                .get("/contracts/byCompany/MediaMarkt")
                .then()
                .log()
                .body()
                .statusCode(202)
                .extract()
                .jsonPath()
                .getList(".", Shop.class);

        assertThat(actualList)
                .isEqualTo(contractRepository.getShopsByCompName("MediaMarkt"));

    }

    @Test
    public void testAddContract() {
        /*
        Contract contract = new Contract(LocalDate.of(2020,12,12),LocalDate.of(2021,2,1),"Pizza Hut",10354.43,10);

        String actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(contract)
                .post("/contracts")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Contract for "+contract.getCompany().getName()+" added!");

        Contract existing = new Contract(LocalDate.of(2010,2,1),LocalDate.of(2021,2,1),"Mediamarkt",50100.50,0);

        actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(existing)
                .post("/contracts")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Contract for "+existing.getCompany().getName()+" couldn't be added!");

         */
    }


    @Test
    public void testDeleteContract() {
        /*
        String actual = given()
                .when()
                .delete("/contracts/Burgerista")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Contract for Burgerista deleted!");

        actual = given()
                .when()
                .delete("/contracts/test")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Contract for test doesn't exist!");

         */
    }

    @Test
    public void testUpdateContract() {
        /*
        Contract updated = new Contract(LocalDate.of(2016,9,30),LocalDate.of(2022,9,30),"Snipes",5000.21,2);

        String actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(updated)
                .put("/contracts/Snipes")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Contract for "+ updated.getCompany().getName()+ " updated!");

        actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(updated)
                .put("/contracts/test")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Contract for test couldn't be updated!");

         */
    }

}
