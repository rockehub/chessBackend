package br.rockethub.utils.validations.validators;

import br.rockethub.chessbackend.authentication.data.PasswordChangeForm;
import br.rockethub.utils.validations.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        PasswordChangeForm form = (PasswordChangeForm) obj;
        return form.getNewPassword().equals(form.getConfirmationPassword());
    }
}