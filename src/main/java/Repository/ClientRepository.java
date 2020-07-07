package Repository;

import Domain.Client;
import Domain.Validators.ClientValidator;
import Domain.Validators.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ClientRepository {

    private List<String> fileNames;
    private List<Client> clients;
    private Validator<Client> validator = new ClientValidator();

    public ClientRepository(List<String> fileNames) {
        this.fileNames = fileNames;
        this.clients = new ArrayList<>();
        loadData();
    }

    public List<Client> getClients() {
        return clients;
    }

    private void loadData() {
        for (String fileName: fileNames) {

            String[] tokens = fileName.split("[ _.]");
            String lastName = tokens[1];
            String firstName = tokens[2];
            String date = tokens[3];

            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line = reader.readLine();
                List<String> Products = Arrays.asList(line.split(";"));

                Map<String, Integer> stores = new HashMap<>();
                line = reader.readLine();

                while (line != null) {
                    try {
                        String[] pair = line.split(":");
                        stores.putIfAbsent(pair[0], Integer.parseInt(pair[1]));
                        line = reader.readLine();

                    } catch (Exception e) {
                        line = reader.readLine();
                    }
                }
                Client client = new Client(lastName, firstName, date, Products, stores);
                validator.validate(client);
                clients.add(client);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
