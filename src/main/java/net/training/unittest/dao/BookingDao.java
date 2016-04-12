package net.training.unittest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.training.unittest.model.Booking;
import net.training.unittest.model.Event;

import org.apache.derby.tools.sysinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class BookingDao {

	@Autowired
	private EventDao eventDao;

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert insertBooking;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.insertBooking = new SimpleJdbcInsert(dataSource).withTableName("BOOKING").usingGeneratedKeyColumns("BOOKING_ID");
	}

	// TODO do not include past dates
	// TODO only display Events with ?0 places left
	// TODO show number of places left when displaying upcoming courses
	// TODO findBy
	public List<Booking> getAllEnabledBookings() {
		List<Booking> results = jdbcTemplate.query(
				"SELECT * FROM BOOKING WHERE ENABLED=TRUE ORDER BY BOOKING_ID",
				new BookingMapper(eventDao));

		return results;
	}

	public Booking getBookingById(int bookingId) {//TODO findBy
		Booking result = jdbcTemplate.queryForObject(
				"SELECT * FROM BOOKING WHERE BOOKING_ID=?", new BookingMapper(
						eventDao), bookingId);
		return result;
	}
	//TODO show x places left in RHS panel

	public void createBooking(Booking booking) {
		Map<String, Object> parameters = new HashMap<String, Object>(10);
		
		parameters.put("FIRST_NAME", booking.getFirstName());
		parameters.put("LAST_NAME", booking.getLastName());
		parameters.put("EMAIL_ADDRESS", booking.getEmail());
		parameters.put("COMPANY", booking.getCompany());
		parameters.put("MESSAGE", booking.getMessage());
		parameters.put("BOOKING_DATE", booking.getBookingDate().toString()); //TODO not sure this is correct
		parameters.put("EVENT_ID", booking.getEvent().getEventId());
		
		Number newId = insertBooking.executeAndReturnKey(parameters);
		booking.setBookingId(newId.intValue());
		System.out.println("Created Booking record !");
	}

	private static final class BookingMapper implements RowMapper<Booking> {

		private EventDao eventDao;

		public BookingMapper(EventDao eventDao) {
			this.eventDao = eventDao;
		}

		public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
			Booking booking = new Booking();
			booking.setBookingId(rs.getInt("BOOKING_ID"));
			booking.setFirstName(rs.getString("FIRST_NAME"));
			booking.setLastName(rs.getString("LAST_NAME"));
			booking.setEmail(rs.getString("EMAIL_ADDRESS"));
			booking.setCompany(rs.getString("COMPANY"));
			booking.setMessage(rs.getString("MESSAGE"));
			// DATE ?
			Event event = eventDao.getEventById(rs.getInt("EVENT_ID"));
			booking.setEvent(event);
			return booking;
		}
	}
}
