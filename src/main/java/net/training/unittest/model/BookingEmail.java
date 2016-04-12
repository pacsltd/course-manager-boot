package net.training.unittest.model;
	
import org.joda.time.DateTime;

public class BookingEmail {

	private int bookingEmailId;

	private Booking booking;

	private DateTime emailSent;

	private String sentFrom;

	private String subject;
	private String message;

	public int getBookingEmailId() {
		return bookingEmailId;
	}

	public void setBookingEmailId(int bookingEmailId) {
		this.bookingEmailId = bookingEmailId;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public DateTime getEmailSent() {
		return emailSent;
	}

	public void setEmailSent(DateTime emailSent) {
		this.emailSent = emailSent;
	}

	public String getSentFrom() {
		return sentFrom;
	}

	public void setSentFrom(String sentFrom) {
		this.sentFrom = sentFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
