package at.htl.centermanager.repository;

import at.htl.centermanager.entity.Company;
import at.htl.centermanager.entity.CompanyCategory;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyRepositoryTest {


    @Inject
    CompanyRepository repo;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    @Transactional
    @Order(1)
    public void testGetAllCompanies() {
        assertThat(repo.getCompanyList().size()).isEqualTo(7);
    }

    @Transactional
    @Test
    @Order(2)
    public void testGetCompany() {
        assertThat(repo.getCompanyByName("Burgerista"))
                .isNotNull();
        assertThat(repo.getCompanyByName("Burgerista").getName())
                .isEqualTo("Burgerista");
        assertThat(repo.getCompanyByName("ThisShouldntWork"))
                .isEqualTo(null);
    }

    @Transactional
    @Test
    @Order(3)
    public void testAddCompany() {
        Company test1 = new Company("Test1", 0, CompanyCategory.MEDICINE);
        Company test2 = new Company("Test2", 10, CompanyCategory.LIVING_AND_HOUSEHOLD);
        Company test3 = new Company("MediaMarkt", 20, CompanyCategory.TECHNOLOGY);

        boolean isAdded1 = repo.addCompany(test1);
        boolean isAdded2 = repo.addCompany(test2);
        boolean isAdded3 = repo.addCompany(test3);

        assertThat(repo.getCompanyList().size())
                .isEqualTo(9);
        assertThat(repo.getCompanyByName("Test1"))
                .isEqualTo(test1);
        assertThat(isAdded1)
                .isTrue();
        assertThat(repo.getCompanyByName("Test2"))
                .isEqualTo(test2);
        assertThat(isAdded2)
                .isTrue();
        assertThat(isAdded3)
                .isFalse();

    }

    @Test
    @Transactional
    @Order(4)
    public void testDeleteCompany() {
        boolean del1 = repo.deleteCompanyByName("Interspar");
        boolean del2 = repo.deleteCompanyByName("ThisShouldntWork");

        assertThat(repo.getCompanyList().size())
                .isEqualTo(8);
        assertThat(del1)
                .isTrue();
        assertThat(del2)
                .isFalse();
    }

    @Test
    @Transactional
    @Order(5)
    public void testUpdateCompany() {

        Company previous = repo.getCompanyByName("MediaMarkt");
        Company newCompany = new Company("MediaMarkt", 0, CompanyCategory.BOOK_AND_PAPER);

        boolean isUpdated = repo.updateCompany("MediaMarkt", newCompany);
        boolean isUpdated2 = repo.updateCompany("Snipes", newCompany);

        assertThat(newCompany)
                .isEqualTo(repo.getCompanyByName("Mediamarkt"));
        assertThat(isUpdated)
                .isTrue();
        assertThat(isUpdated2)
                .isFalse();

    }

}