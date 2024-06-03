package med.voll.api.infra.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MustBeNullValidator.class)
public @interface MustBeNull {
    String message() default "O campo deve ser nulo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

