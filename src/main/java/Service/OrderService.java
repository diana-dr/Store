package Service;

import Domain.Client;
import Domain.Product;
import Domain.Promotion;
import Domain.Store;
import Repository.ClientRepository;
import Repository.StoreRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {

    private ClientRepository clientsRepo;
    private StoreRepository storesRepo;

    public OrderService(ClientRepository clients, StoreRepository Stores) {
        this.clientsRepo = clients;
        this.storesRepo = Stores;
    }

    public void orderProcessing() {
        Map<Client, List<Product>> orders = new HashMap<>();
        for (Client client: clientsRepo.getClients()) {
            Map<String, Integer> stores = client.getStores();

            DayOfWeek day = LocalDate.parse(client.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfWeek();

            storesRepo.getStores().forEach(s -> {
                if (!s.isOpenSunday() && day.equals(DayOfWeek.SUNDAY))
                    stores.remove(s.getName());
            });

            List<Map.Entry<String, Integer>> filteredStores = new ArrayList<>(stores.entrySet());
            filteredStores.sort(Map.Entry.comparingByValue());
            List<Product> products = findBestOption(client, filteredStores);

            List<String> product_names = new ArrayList<>();
            for (Product product : products)
                product_names.add(product.getName());

            for (String shoppingItem : client.getProducts()) {
                if (!product_names.contains(shoppingItem)) {
                    Product product = new Product(shoppingItem, (float) 0, "");
                    products.add(product);
                }
            }

            orders.put(client, products);
        }

        Map<Client, List<Product>> sortedOrders = sortOrders(orders);
        writeToFile(sortedOrders);
    }

    public Map<Client, List<Product>> sortOrders(Map<Client, List<Product>> orders) {
        List<Map.Entry<Client, List<Product>>> list = new LinkedList<>(orders.entrySet());

        list.sort(this::compareTo);

        Map<Client, List<Product>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Client, List<Product>> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public int compareTo(Map.Entry<Client, List<Product>> order1, Map.Entry<Client, List<Product>> order2) {
        float total1 = findTotal(order1);
        float total2 = findTotal(order2);

        if (total1 - total2 > 0)
            return -1;
        else if (total1 - total2 < 0)
            return 1;
        else
            return order1.getKey().getLastName().compareTo(order2.getKey().getLastName());
    }

    public float findTotal(Map.Entry<Client, List<Product>> order) {
        float total = 0;
        for (Product product : order.getValue())
            total += product.getPrice();
        return total;
    }

    public List<Product> findBestOption(Client client, List<Map.Entry<String, Integer>> filtered) {
        List<Product> products = new ArrayList<>();
        List<String> shoppingList = client.getProducts();
        List<String> filteredStores = filtered.stream().map(Map.Entry::getKey).collect(Collectors.toList());

        try {
            products = findPromotions(client, filteredStores);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Product> finalProducts = products;
        filteredStores.forEach(store_name ->
            storesRepo.getStores().forEach( store -> {
                if (store.getName().equals(store_name))
                    addProducts(finalProducts, shoppingList, store.getProducts());
            }));

        return products;
    }

    public void addProducts(List<Product> products, List<String> shoppingList, List<Product> products2) {
        for (Product product : products2)
            if (shoppingList.contains(product.getName())) {
                boolean contains = false;
                for (Product p : products)
                    if (p.getName().equals(product.getName())) {
                        contains = true;
                        break; }
                if (!contains)
                    products.add(product);
            }
    }

    public List<Product> findPromotions(Client client, List<String> filteredStores) throws ParseException {
        List<String> shoppingList = client.getProducts();
        List<Product> products = new ArrayList<>();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(client.getDate());

        for (String store_name : filteredStores)
            for (Store store : storesRepo.getStores())
                if (store.getName().equals(store_name))
                    for (Promotion promotion : store.getPromotions()) {

                        Date start = new SimpleDateFormat("yyyy-MM-dd").parse(promotion.getStartPromotion());
                        Date end = new SimpleDateFormat("yyyy-MM-dd").parse(promotion.getEndPromotion());
                        if (date.compareTo(start) >= 0 && date.compareTo(end) <= 0)
                            addProducts(products, shoppingList, promotion.getProducts());
                    }
        return products;
    }

    public void writeToFile(Map<Client, List<Product>> orders) {
        String filename = "comenzi.txt";
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<Client, List<Product>> order : orders.entrySet()) {

                Client client = order.getKey();
                List<Product> products = order.getValue();
                float total = findTotal(order);

                bufferedWriter.write(client.getFirstName() + " " + client.getLastName() + "\n");
                bufferedWriter.write("Suma totala cumparaturi: " + df.format(total) + "\n");

                for (Product entity : products) {
                    if (entity.getPrice() != 0)
                        bufferedWriter.write(String.valueOf(entity));
                    else bufferedWriter.write(entity.getName() + ": indisponibil");

                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
