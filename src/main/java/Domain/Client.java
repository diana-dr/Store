package Domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Client {
    private String lastName;
    private String firstName;
    private String date;
    private List<String> products;
    private Map<String, Integer> stores;

    public Client(String LastName, String FirstName, String date,
                  List<String> products,Map<String, Integer> stores) {
        this.lastName = LastName;
        this.firstName = FirstName;
        this.date = date;
        this.products = products;
        this.stores = stores;
    }

    public Map<String, Integer> getStores() {
        return stores;
    }

    public List<String> getProducts() {
        return products;
    }

    public String getDate() {
        return date;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "LastName='" + lastName + '\'' +
                ", FirstName='" + firstName + '\'' +
                ", data='" + date + '\'' +
                ", products=" + products +
                ", stores=" + stores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return lastName.equals(client.lastName) &&
                firstName.equals(client.firstName) &&
                date.equals(client.date) &&
                products.equals(client.products) &&
                stores.equals(client.stores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, date, products, stores);
    }
}
