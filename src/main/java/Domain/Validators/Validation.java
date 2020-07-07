package Domain.Validators;

@FunctionalInterface
public interface Validation<K> {
    ValidatorResult test(K param);
}