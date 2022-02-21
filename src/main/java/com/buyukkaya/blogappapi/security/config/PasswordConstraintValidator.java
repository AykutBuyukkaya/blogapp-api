package com.buyukkaya.blogappapi.security.config;

import com.buyukkaya.blogappapi.security.util.ValidPassword;
import lombok.SneakyThrows;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @SneakyThrows
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("passay.properties");

        properties.load(inputStream);
        MessageResolver resolver = new PropertiesMessageResolver(properties);

        PasswordValidator validator = new PasswordValidator(resolver,
                Arrays.asList(

                        new LengthRule(8, 64),
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        new WhitespaceRule()

                ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {

            return true;

        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);

        constraintValidatorContext
                .buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;

    }
}
