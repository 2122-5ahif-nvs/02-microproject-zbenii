package at.htl.centermanager.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@NamedQuery(name = "Shop.getById", query = "select s from Shop s where s.Id = ?1")
@XmlRootElement
public class Shop {

    @Column(name = "SIZE_M2")
    private double sizeM2;

    @Id
    @GeneratedValue
    private Integer Id;

    private String floor;

    @Override
    public String toString() {
        return "Shop{" +
                "size=" + sizeM2 +
                ", id=" + Id +
                ", floor='" + floor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop that = (Shop) o;
        return Id == that.Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    public Shop(double sizeM2, String floor) {
        setSizeM2(sizeM2);
        setFloor(floor);
    }

    public Shop() {
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public double getSizeM2() {
        return sizeM2;
    }

    public void setSizeM2(double size) {
        this.sizeM2 = size;
    }

    public int getId() {
        return Id;
    }

    public void setId(int number) {

        if (number == 0) {
            Id = null;
        } else {
            Id = number;
        }
    }


}
