package net.training.unittest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import net.training.unittest.model.Event;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EventDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// TODO do not include past dates
	//TODO findBy
	public List<Event> getAllEnabledEventsByDate() {
		List<Event> results = jdbcTemplate.query(
				"SELECT * FROM EVENT WHERE ENABLED=TRUE ORDER BY EVENT_DATE",
				new EventMapper());

		return results;
	}

	//TODO findBy
	public Event getEventById(int eventId) {
		Event result = jdbcTemplate.queryForObject(
				"SELECT * FROM EVENT WHERE EVENT_ID=?", new EventMapper(),
				eventId);
		return result;
	}

	//TODO findBy
	public int getPlacesLeft(int eventId) {
		int result = jdbcTemplate.queryForObject(
				"SELECT PLACES_LEFT FROM EVENT WHERE EVENT_ID=?",
				Integer.class, eventId);
		return result;
	}

	public void setPlacesLeft(int eventId, int placesLeft) {
		jdbcTemplate.update("UPDATE EVENT SET PLACES_LEFT=? WHERE EVENT_ID=?",placesLeft, eventId);
	}

	private static final class EventMapper implements RowMapper<Event> {
		public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
			Event event = new Event();
			event.setEventId(rs.getInt("EVENT_ID"));
			event.setCity(rs.getString("CITY"));
			event.setDate(new LocalDate(rs.getDate("EVENT_DATE")));
			event.setPlacesLeft(rs.getInt("PLACES_LEFT"));
			event.setEnabled(rs.getBoolean("ENABLED"));
			return event;
		}
	}

	public void decrementPlacesLeft(Event event) {
		int placesLeft = getPlacesLeft(event.getEventId());
		setPlacesLeft(event.getEventId(), placesLeft - 1);
	}

}
