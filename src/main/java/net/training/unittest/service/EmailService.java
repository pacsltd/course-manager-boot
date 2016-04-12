package net.training.unittest.service;

import java.util.List;

import net.training.unittest.dao.EmailDao;
import net.training.unittest.model.Booking;
import net.training.unittest.model.BookingEmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	private EmailDao emailDao;

	@Autowired
	private MailSender mailSender;

	@Scheduled(fixedRate = 4000)
	public void work() {
		System.err.println("Chceking for unsent emails ....");
		
		//List<BookingEmail> bookingEmails = emailDao.findAllNotSentEmails();
		//for (BookingEmail bookingEmail : bookingEmails) {
			//TODO temp sendEmail(bookingEmail);	
		//}
		
		
		System.err.println("Doing some email work");
	}
	
	private void sendEmail(BookingEmail bookingEmail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		Booking booking = bookingEmail.getBooking();
		mailMessage.setFrom("bookings@java-experts.co.uk"); //TODO is this correct //TODO put in app context
		mailMessage.setTo(booking.getEmail());
		mailMessage.setBcc("bookings@chezandrews.com");
		mailMessage.setSubject("Java Expert course booking id=" + bookingEmail.getBookingEmailId());
		
		StringBuilder sb = new StringBuilder();
		sb.append("Hello ").append(booking.getFirstName() + ",");
		sb.append("\nThank you for booking on to our course - now pay up and listen !");
		sb.append("\nYou will need to bring sandwiches and your own coffee.");
		sb.append("\n\nYour message '"+ booking.getMessage() +"' will be listened to by our team.");
		sb.append("\n\nref:" + bookingEmail.getBookingEmailId() + "/" + booking.getBookingId());
		sb.append("\n\nJava Experts Limted - company number 999333445");
		
		mailMessage.setText(sb.toString());
		
		try {
			mailSender.send(mailMessage);
			emailDao.markEmailAsSent(bookingEmail);
		} catch (MailException e) {
			// TODO log the error
			LOGGER.error("Unable to send email " + bookingEmail);
			e.printStackTrace();
		}
	}

	
}
