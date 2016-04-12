package net.training.unittest.validator;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.training.unittest.model.Booking;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//TODO spring global date format
public class BookingValidator implements Validator {

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	/**
	 * This Validator validates *just* Booking instances
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class clazz) {
		return Booking.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {

		Booking booking = (Booking) obj; // TODO check that event is OK ?

		rejectIfEmptyOrWhitespace(errors, "firstName", "first.name.empty",
				"enter first name");
		rejectIfEmptyOrWhitespace(errors, "lastName", "last.name.empty",
				"enter last name");

		rejectIfEmptyOrWhitespace(errors, "email", "email.empty", "enter email");

		if (isNotBlank(booking.getEmail())) {
			Matcher emailMatcher = EMAIL_PATTERN.matcher(booking.getEmail());
			if (!emailMatcher.matches()) {
				errors.rejectValue("email", "email.not.valid.address",
						"email.not.valid.address");
			}
		}

		rejectIfEmptyOrWhitespace(errors, "confirmEmail",
				"email.confirm.empty", "enter confirmation email");

		if (isNotBlank(booking.getConfirmEmail())) {
			Matcher emailMatcher = EMAIL_PATTERN.matcher(booking
					.getConfirmEmail());
			if (!emailMatcher.matches()) {
				errors.rejectValue("confirmEmail",
						"email.confirm.not.valid.address",
						"email.confirm.not.valid.address");
			}
		}

		if (!errors.hasFieldErrors("email") && !errors.hasFieldErrors("confirmEmail")) {
			if (!booking.getEmail().equals(booking.getConfirmEmail())) {
				errors.rejectValue("confirmEmail", "email.confirm.not.same",
						"emails not the same");
			}
		}

		rejectIfEmptyOrWhitespace(errors, "company", "company.empty",
				"enter company");

	}
}
