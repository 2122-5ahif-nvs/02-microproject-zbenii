package at.htl.centermanager.useCaseTest;

import at.htl.centermanager.repository.ContractRepository;
import com.intuit.karate.junit5.Karate;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

//run usecasetests single test only
@QuarkusTest
public class UseCaseTests {

    @Karate.Test
    @ApplicationScoped
    Karate testContract() {
        return Karate.run("contractTest/contractTest").relativeTo(getClass());
    }

}
