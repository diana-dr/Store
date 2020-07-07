package Domain;

import java.text.DecimalFormat;
import java.util.Objects;

public class Product {
    private String name;
    private Float price;
    private String store;

    public Product(String Name, Float Price, String store) {
        this.name = Name;
        this.price = Price;
        this.store = store;
    }

    public String getStore() {
        return store;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);

        return store + ": " + name + ":" + df.format(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name) &&
                price.equals(product.price) &&
                store.equals(product.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, store);
    }
}
