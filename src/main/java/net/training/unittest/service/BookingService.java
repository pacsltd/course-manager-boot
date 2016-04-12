package net.training.unittest.service;

import net.training.unittest.dao.BookingDao;
import net.training.unittest.dao.EventDao;
import net.training.unittest.dao.EmailDao;
import net.training.unittest.model.Booking;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingService {

	@Autowired
	private EventDao eventDao;

	@Autowired
	private BookingDao bookingDao;

	@Autowired
	private EmailDao emailDao;

	/**
	 * //TODO make transactional
	 * Creates the booking.
	 * 1) Insert a booking record
	 * 2) Decrement the number of available places left for the Event
	 * 3) Create an email record
	 * 
	 * All done in one atomic transaction
	 * 
	 * @param booking the booking
	 */
	public void createBooking(Booking booking) {

		booking.setBookingDate(new LocalDate());
		booking.setEvent(eventDao.getEventById(booking.getEvent().getEventId()));

		bookingDao.createBooking(booking);

		eventDao.decrementPlacesLeft(booking.getEvent());
				
		emailDao.createBookingEmail(booking);
	}

}
