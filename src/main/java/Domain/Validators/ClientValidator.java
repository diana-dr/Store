package Domain.Validators;

import Domain.Client;

import java.util.regex.Pattern;

public class ClientValidator implements Validator<Client> {
    private static Pattern DATE_PATTERN = Pattern.compile(
            "^\\d{4}-\\d{2}-\\d{2}$");

    private static Validation<Client> correctDate = SimpleValidation.from((Client) -> DATE_PATTERN.matcher(Client.getDate()).matches(), "Invalid date!");

    public void validate(Client client) throws ValidatorException {
        correctDate.test(client).throwIfInvalid();
    }
}
