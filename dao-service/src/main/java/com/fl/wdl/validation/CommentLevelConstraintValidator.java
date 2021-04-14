package com.fl.wdl.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CommentLevelConstraintValidator implements ConstraintValidator<CommentLevel,Integer>{

	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if(value == 0 || value == 1 || value == 2)return true;
		return false;
	}

}
