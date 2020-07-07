package Repository;

import Domain.Store;
import Domain.Product;
import Domain.Promotion;
import Domain.Validators.PromotionValidator;
import Domain.Validators.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreRepository {

    private List<String> fileNames;
    private List<Store> stores;

    public StoreRepository(List<String> fileNames) {
        this.fileNames = fileNames;
        this.stores = new ArrayList<>();
        loadData();
    }

    public List<Store> getStores() {
        return stores;
    }

    private void loadData() {
        for (String fileName: fileNames) {
            String[] tokens = fileName.split("[_.]");
            String storeName = tokens[1];

            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line = reader.readLine();
                boolean open = line.equals("Deschis");
                line = reader.readLine();

                List<Promotion> promotions = readPromotions(reader, storeName);

                while (!line.equals("**********"))
                    line = reader.readLine();
                line = reader.readLine();

                List<Product> products = new ArrayList<>();

                while (line != null) {
                    String[] pair = line.split(":");
                    Product product = new Product(pair[0], Float.parseFloat(pair[1]), storeName);
                    products.add(product);
                    line = reader.readLine();
                }
                Store store = new Store(storeName, open, promotions, products);
                this.stores.add(store);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<Promotion> readPromotions(BufferedReader reader, String storeName) {

        List<Promotion> promotions = new ArrayList<>();
        Validator<Promotion> validator = new PromotionValidator();
        try {
            String line = reader.readLine();
            String previous = line;

            while (!previous.equals("**********")) {
                List<Product> produse = new ArrayList<>();
                List<String> date = Arrays.asList(line.split(" "));
                String startdate = date.get(0);
                String enddate;

                if (date.size() == 1)
                    enddate = date.get(0);
                else enddate = date.get(1);

                line = reader.readLine();

                while (!line.equals("") && !line.equals("**********")) {
                    try {

                        String[] pair = line.split(":");
                        Product produs = new Product(pair[0], Float.parseFloat(pair[1]), storeName);
                        produse.add(produs);
                        line = reader.readLine();
                    }
                    catch (Exception e) {
                        line = reader.readLine();
                    }
                }

                Promotion promotion = new Promotion(produse, startdate, enddate);
                validator.validate(promotion);
                promotions.add(promotion);
                previous = line;
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotions;
    }
}

