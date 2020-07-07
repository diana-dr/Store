package Domain.Validators;

import Domain.Promotion;

import java.util.regex.Pattern;

public class PromotionValidator implements Validator<Promotion>{
    private static Pattern DATE_PATTERN = Pattern.compile(
            "^\\d{4}-\\d{2}-\\d{2}$");

    private static Validation<Promotion> correctStartDate = SimpleValidation.from((promotion) -> DATE_PATTERN.matcher(promotion.getStartPromotion()).matches(), "Invalid date!");
    private static Validation<Promotion> correctEndDate = SimpleValidation.from((promotion) -> DATE_PATTERN.matcher(promotion.getStartPromotion()).matches(), "Invalid date!");

    public void validate(Promotion promotion) throws ValidatorException {
        correctEndDate.test(promotion).throwIfInvalid();
        correctStartDate.test(promotion).throwIfInvalid();

    }
}
