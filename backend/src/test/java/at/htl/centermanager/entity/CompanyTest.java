package at.htl.centermanager.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class CompanyTest {
    static Company testCompany;

    @BeforeAll
    static void beforeAll() {
        testCompany = new Company("McDonalds", 10, CompanyCategory.GASTRONOMY);
    }

    @Test
    public void testToString() {
        assertThat(testCompany.toString()).isEqualTo("Company{" +
                "name='" + testCompany.getName() + '\'' +
                ", employeeAmount=" + testCompany.getEmployeeAmount() +
                ", category=" + testCompany.getCategory() +
                '}');

    }

    @Test
    public void testEquals() {
        assertThat(testCompany).isEqualTo(new Company("McDonalds", 10, CompanyCategory.GASTRONOMY));

    }

}
