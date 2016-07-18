package pl.jitsolutions.jitash.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("phoneValidator")
public class PhoneValidator implements Validator {

	private static final String PHONE_PATTERN = "^([0-9]{9})$";

	private Pattern pattern;
	private Matcher matcher;

	public PhoneValidator(){
		pattern = Pattern.compile(PHONE_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		matcher = pattern.matcher(value.toString());

		if(!matcher.matches()){
			FacesMessage msg = new FacesMessage("Phone validation failed.", "Niepoprawny format telefonu");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}
}
