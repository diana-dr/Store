package Domain;

import java.util.List;
import java.util.Objects;

public class Store {
    private String name;
    private boolean openSunday;
    private List<Promotion> promotions;
    private List<Product> products;

    public Store(String name, boolean OpenSunday, List<Promotion> Promotions, List<Product> Products) {
        this.name = name;
        this.openSunday = OpenSunday;
        this.promotions = Promotions;
        this.products = Products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public boolean isOpenSunday() {
        return openSunday;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "store{" +
                "name='" + name + '\'' +
                ", OpenSunday=" + openSunday +
                ", Promotions=" + promotions +
                ", Products=" + products +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return openSunday == store.openSunday &&
                name.equals(store.name) &&
                promotions.equals(store.promotions) &&
                products.equals(store.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, openSunday, promotions, products);
    }
}
