package com.sorog.carrent.validator;

import com.sorog.carrent.dto.MakeOrderDTO;
import com.sorog.carrent.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MakeOrderDTO order = (MakeOrderDTO) target;

        if (order.dateRentStart.compareTo(order.dateRentEnd) == 1) {
            errors.rejectValue("dateStart", "dateEnd < dateStart");
        }

    }
}
