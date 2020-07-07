package Domain;

import java.util.List;
import java.util.Objects;

public class Promotion {
    private List<Product> products;
    private String startPromotion;
    private String endPromotion;

    public Promotion(List<Product> Products, String StartPromotion, String EndPromotion) {
        this.products = Products;
        this.startPromotion = StartPromotion;
        this.endPromotion = EndPromotion;
    }

    public String getEndPromotion() {
        return endPromotion;
    }

    public String getStartPromotion() {
        return startPromotion;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Promotie{" +
                "Products=" + products +
                ", StartPromotion='" + startPromotion + '\'' +
                ", EndPromotion='" + endPromotion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return products.equals(promotion.products) &&
                startPromotion.equals(promotion.startPromotion) &&
                Objects.equals(endPromotion, promotion.endPromotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, startPromotion, endPromotion);
    }
}
