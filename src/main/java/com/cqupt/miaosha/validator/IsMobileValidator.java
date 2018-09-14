package com.cqupt.miaosha.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.alibaba.druid.util.StringUtils;
import com.cqupt.miaosha.utils.ValidatorUtil;

/*
 * 自定义注解IsMobile的实现类
 */
public class IsMobileValidator implements  ConstraintValidator<IsMobile, String>{
	private boolean required = false;
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}
	//校验过程
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required){
			return ValidatorUtil.isMobile(value);
		}else{
			if(StringUtils.isEmpty(value)){
				return true;
			}else{
				return ValidatorUtil.isMobile(value);
			}
		}
	}
}


