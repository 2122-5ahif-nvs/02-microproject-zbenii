package at.htl.centermanager.repository;

import at.htl.centermanager.entity.Contract;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTest {

    @Inject
    ContractRepository contractRepo;

    @Inject
    CompanyRepository compRepo;

    @Inject
    ShopRepository shopRepo;

    @Test
    @Order(1)
    public void correctEntityAmounts() {
        assertThat(shopRepo.getShopList().size())
                .isEqualTo(7);
        assertThat(compRepo.getCompanyList().size())
                .isEqualTo(7);
        assertThat(contractRepo.getContractList().size())
                .isEqualTo(4);
    }

    @Test
    @Order(2)
    public void contractsUseCorrectEntities() {
        Contract test = contractRepo.getContractById(1);
        assertThat(test.getCompany())
                .isEqualTo(compRepo.getCompanyByName(test.getCompany().getName()));

        assertThat(test.getShop())
                .isEqualTo(shopRepo.getShopById(test.getShop().getId()));
    }

    @Test
    @Order(3)
    public void companyRentsCorrectShops() {
        contractRepo.addContract(new Contract(LocalDate.of(2010, 2, 3), LocalDate.of(2021, 2, 3), shopRepo.getShopById(5), 30100.5, compRepo.getCompanyByName("MediaMarkt")));

        assertThat(contractRepo.getShopsByCompName("MediaMarkt").size())
                .isEqualTo(2);
        assertThat(contractRepo.getTotalRentForCompany("MediaMarkt"))
                .isEqualTo(80201.0);
        assertThat(contractRepo.getTotalRentSpaceForCompany("MediaMarkt"))
                .isEqualTo(924.8);
    }


}
