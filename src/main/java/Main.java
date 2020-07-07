import Repository.ClientRepository;
import Repository.StoreRepository;
import Service.OrderService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> filenames = new ArrayList<>();
        filenames.add("resources/comanda_Florescu Ana_2020-04-30.txt");
        filenames.add("resources/comanda_Ionescu Maria_2020-04-15.txt");
        filenames.add("resources/comanda_Popescu Dan_2020-04-26.txt");
        ClientRepository clientRepository = new ClientRepository(filenames);

        List<String> filenames2 = new ArrayList<>();
        filenames2.add("resources/magazin_Auchan.txt");
        filenames2.add("resources/magazin_Lidl.txt");
        filenames2.add("resources/magazin_Mega Image.txt");
        StoreRepository storeRepository = new StoreRepository(filenames2);

        OrderService orderService = new OrderService(clientRepository, storeRepository);
        orderService.orderProcessing();
    }
}
