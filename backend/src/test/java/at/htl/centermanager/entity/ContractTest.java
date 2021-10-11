package at.htl.centermanager.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class ContractTest {
    static Contract testContract;

    @BeforeAll
    static void beforeAll() {
        testContract = new Contract(LocalDate.of(2010, 8, 15), LocalDate.of(2021, 8, 15), new Shop(304.5, "1OG"), 7600.32, new Company("MediaMarkt", 20, CompanyCategory.TECHNOLOGY));
        testContract.setId(10);
    }

    @Test
    public void testToString() {
        assertThat(testContract.toString().equals("Contract{" +
                "id=" + testContract.getId() +
                ", contractSigned=" + testContract.getContractSigned() +
                ", contractEnd=" + testContract.getContractEnd() +
                ", shop=" + testContract.getShop() +
                ", rentalCost=" + testContract.getRentalCost() +
                ", company=" + testContract.getCompany() +
                ", currency='" + testContract.getCurrency() + '\'' +
                '}'))
                .isTrue();
    }

    @Test
    public void testEquals() {
        assertThat(testContract.equals(new Contract(LocalDate.of(2010, 8, 15), LocalDate.of(2021, 8, 15), new Shop(304.5, "1OG"), 7600.32, new Company("MediaMarkt", 20, CompanyCategory.TECHNOLOGY))))
                .isTrue();
    }
}
