package at.htl.centermanager.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class ShopTest {
    static Shop testShop;

    @BeforeAll
    static void beforeAll() {
        testShop = new Shop(304.5, "1OG");
        testShop.setId(12);
    }

    @Test
    public void testToString() {
        assertThat(testShop.toString()).isEqualTo("Shop{" +
                "size=" + testShop.getSizeM2() +
                ", id=" + testShop.getId() +
                ", floor='" + testShop.getFloor() + '\'' +
                '}');

    }

    @Test
    public void testEquals() {
        Shop compare = new Shop(304.5, "1OG");
        compare.setId(12);
        assertThat(testShop.equals(compare))
                .isTrue();
    }
}
