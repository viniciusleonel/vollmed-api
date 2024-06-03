package med.voll.api.infra.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MustBeNullValidator implements ConstraintValidator<MustBeNull, Object> {
    @Override
    public void initialize(MustBeNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value == null;
    }
}

