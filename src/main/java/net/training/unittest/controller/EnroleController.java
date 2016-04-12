package net.training.unittest.controller;

import net.training.unittest.dao.BookingDao;
import net.training.unittest.dao.EventDao;
import net.training.unittest.model.Booking;
import net.training.unittest.service.BookingService;
import net.training.unittest.validator.BookingValidator;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EnroleController {

	@Autowired
	BookingService bookingService;

	@Autowired
	private EventDao eventDao;

	@Autowired
	private BookingDao bookingDao;

	@RequestMapping(value = "/enrole/{eventId}", method = RequestMethod.GET)
	public String enrole(Model model, @PathVariable int eventId) {
		Booking booking = new Booking();
		// booking.setFirstName("winnie the poo");
		model.addAttribute(booking);
		booking.setEvent(eventDao.getEventById(eventId));
		return "enrole";
	}
	//dd

	@RequestMapping(value = "/enrole", method = RequestMethod.POST)
	public String enrole(Model model, @ModelAttribute("booking") Booking booking, Errors errors, RedirectAttributes redirectAttrs) {
		new BookingValidator().validate(booking, errors);
		if (!errors.hasErrors()) {
			bookingService.createBooking(booking);
			return "redirect:/enroled/" + booking.getBookingId();
		} else {
			return "enrole"; 
			// TODO can't I just return null or "" or use a constant??
		}
	}

	@RequestMapping(value = "/enroled/{bookingId}", method = RequestMethod.GET)
	public String enroled(Model model, @PathVariable int bookingId) {
		Booking booking = bookingDao.getBookingById(bookingId);
		model.addAttribute(booking);
		return "enroled";
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleAllException(Exception ex) {
		ModelAndView model = new ModelAndView("errorPage");
		model.addObject("errorMsg", "unable to retrieve the correct Event record from database");
		return model;

	}

}
