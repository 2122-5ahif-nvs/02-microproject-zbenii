package at.htl.centermanager.boundary;

import at.htl.centermanager.entity.Company;
import at.htl.centermanager.entity.CompanyCategory;
import at.htl.centermanager.repository.CompanyRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class CompanyServiceTest {

    @Inject
    CompanyService companyService;
    @Inject
    CompanyRepository companyRepository;

    @Test
    public void testGetAllCompanies() {

        List<Company> actualList = given()
                .when()
                .get("/companies")
                .then()
                .log()
                .body()
                .statusCode(202)
                .extract()
                .jsonPath()
                .getList(".", Company.class);

        assertThat(actualList)
                .isEqualTo(companyRepository.getCompanyList());
    }

    @Test
    public void testGetSingleCompany() {
        Company actual = given()
                .when()
                .get("/companies/Mediamarkt")
                .then()
                .log()
                .body()
                .statusCode(202)
                .extract()
                .jsonPath()
                .getObject(".", Company.class);

        assertThat(actual)
                .isEqualTo(companyRepository.getCompanyByName("Mediamarkt"));
    }

    @Test
    public void testAddCompany() {

        Company company = new Company("XXL Sports", 25, CompanyCategory.SPORTS);

        String actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(company)
                .post("/companies")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Company: " + company.getName() + " added!");

        Company existing = new Company("MediaMarkt", 20, CompanyCategory.TECHNOLOGY);

        actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(existing)
                .post("/companies")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Company: " + existing.getName() + " already exists!");

    }

    @Test
    public void testDeleteCompany() {

        String actual = given()
                .when()
                .delete("/companies/Interspar")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Company: Interspar deleted!");

        actual = given()
                .when()
                .delete("/companies/test")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Company: test doesn't exist!");


    }

    @Test
    public void testUpdateCompany() {

        Company updated = new Company("Snipes", 10, CompanyCategory.CLOTHING);

        String actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(updated)
                .put("/companies/Snipes")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Company: " + updated.getName() + " updated!");

        actual = given()
                .contentType(ContentType.JSON)
                .when()
                .body(updated)
                .put("/companies/test")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(actual)
                .isEqualTo("Company: test doesn't exist!");
    }
}