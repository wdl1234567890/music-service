package com.fl.wdl.validation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fl.wdl.pojo.Comment;

public class FromCommentNotNullConstraintValidator implements ConstraintValidator<FromCommentNotNull,Comment>{

	@Override
	public boolean isValid(Comment comment, ConstraintValidatorContext context) {
		if(comment == null || comment.getId() == null || comment.getId().equals(""))return false;
		return true;
	}

}
