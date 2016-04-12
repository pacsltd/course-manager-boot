package net.training.unittest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import net.training.unittest.model.Booking;
import net.training.unittest.model.BookingEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmailDao {

	@Autowired
	private BookingDao bookingDao;
	
	private JdbcTemplate jdbcTemplate;

	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createBookingEmail(Booking booking) {
		jdbcTemplate.update(
				"INSERT INTO BOOKING_EMAIL (BOOKING_ID) VALUES (?)",
				booking.getBookingId());
		System.out.println("Created Booking Email record !");
	}

	public List<BookingEmail> findAllNotSentEmails() {
		List<BookingEmail> results = jdbcTemplate.query(
				"SELECT * FROM BOOKING_EMAIL WHERE EMAIL_SENT IS NULL",
				new BookingEmailMapper(bookingDao));
		return results;
	}

	private static final class BookingEmailMapper implements
			RowMapper<BookingEmail> {

		private BookingDao bookingDao;

		public BookingEmailMapper(BookingDao bookingDao) {
			this.bookingDao = bookingDao;
		}
		
		public BookingEmail mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			BookingEmail bookingEmail = new BookingEmail();
			bookingEmail.setBookingEmailId(rs.getInt("BOOKING_EMAIL_ID"));
			bookingEmail.setBooking(bookingDao.getBookingById(rs.getInt("BOOKING_ID")));
			return bookingEmail;
		}
	}

	public void markEmailAsSent(BookingEmail bookingEmail) {
		jdbcTemplate.update(
				"UPDATE BOOKING_EMAIL SET EMAIL_SENT=? WHERE BOOKING_EMAIL_ID=?", new Date(), bookingEmail.getBookingEmailId());
		// TODO Auto-generated method stub
		
	}

}
