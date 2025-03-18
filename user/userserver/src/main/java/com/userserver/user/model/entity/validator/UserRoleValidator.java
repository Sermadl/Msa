package com.userserver.user.model.entity.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserRoleValidator implements ConstraintValidator<Enum, Enum> {
    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
//        if(value == UserRole.ADMIN){
//            return false;
//        }
//        return value != null;
        return true;
    }
}
