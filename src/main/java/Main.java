import model.Address;
import model.Client;
import org.apache.commons.lang3.RandomUtils;
import repositories.ClientRepository;

public class Main {
    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepository();
        clientRepository.add(new Client("ach", "to", "NBD",
                new Address(RandomUtils.nextLong(), "ll", "ll", "ll")));
        System.out.println("Hello world");
    }
}
