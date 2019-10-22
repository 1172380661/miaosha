package my.miaosha.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import my.miaosha.util.VaildatorUtil;

public class IsMobileValitor implements ConstraintValidator<IsMobile, Long>{

	private boolean required;
	
	@Override
	public void initialize(IsMobile constraintAnnotation) {
		this.required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if(required) {
			return VaildatorUtil.isMobile(value);
		}else {
			if(value == 0) {
				return true;
			}else {
				return VaildatorUtil.isMobile(value);
			}
		}
	}

}
