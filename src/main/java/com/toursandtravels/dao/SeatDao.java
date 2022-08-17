package com.toursandtravels.dao;

import com.toursandtravels.model.Seat;
import com.toursandtravels.model.UserInfo;
import com.toursandtravels.util.DBService;
import com.toursandtravels.util.TATException;
import com.toursandtravels.util.Util;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.toursandtravels.util.Util.validate;

public class SeatDao {
    // Create Seat
    public int create(Seat seat) {
        validate(SEAT, seat);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(CREATE_SEAT, Statement.RETURN_GENERATED_KEYS);
            int ix = setSeatDetails(pstmt, seat);

            int result = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next() == false) {
                throw Util.prepareException("TAT-00002", null, seat.getSeatName(), seat.getSeatType());
            }

            int userId = rs.getInt(SEAT_ID);
            return userId;
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00002", th, seat.getSeatName(), seat.getSeatType());
        } finally {
            DBService.getInstance().closeResource(rs, pstmt, conn);
        }
    }

    // Get Seat information by Id
    public static Seat getById(int seatId) {
        validate(SEAT_ID, seatId);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(GET_SEAT_BY_ID);
            pstmt.setInt(1, seatId);

            rs = pstmt.executeQuery();
            if (rs.next() ==  false) {
                throw Util.prepareException("TAT-00003", null, seatId);
            }
            return getSeatDetails(rs);
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00004", th, seatId);
        } finally {
            DBService.getInstance().closeResource(rs, pstmt, conn);
        }
    }


    // Get User information by userName
    public static Seat getBySeatName(String seatName) {
        validate(SEAT_NAME, seatName);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(GET_SEAT_BY_SEAT_NAME);
            pstmt.setString(1, seatName);

            rs = pstmt.executeQuery();
            if (rs.next() == false) {
                throw Util.prepareException("TAT-00008", null, seatName);
            }
            return getSeatDetails(rs);
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00009", th, seatName);
        } finally {
            DBService.getInstance().closeResource(rs, pstmt, conn);
        }
    }

    //List All User Information
    public static List<Seat> getAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(GET_ALL_SEATS);
            rs = pstmt.executeQuery();

            if (rs.next() == false) {
                throw Util.prepareException("TAT-00010", null);
            }
            List<Seat> seatList = new ArrayList<Seat>();
            do {
                seatList.add(getSeatDetails(rs));
            } while (rs.next());

            return seatList;
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00010", th);
        } finally {
            DBService.getInstance().closeResource(rs, pstmt, conn);
        }
    }

    // Update Seat By Id
    public static int update(String seatId, Seat seat) {
        validate(SEAT_ID, seatId);
        validate(SEAT, seat);

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(UPDATE_USER_BY_ID);

            int ix = setSeatDetails(pstmt, seat);
            pstmt.setString(ix, seatId);

            int count = pstmt.executeUpdate();

            if (count <= 0) {
                throw Util.prepareException("TAT-00011", null, seatId);
            }
            return count;
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00012", th, seatId);
        } finally {
            DBService.getInstance().closeResource(null, pstmt, conn);
        }
    }


    // Delete Seat By Id
    public static int deleteById(String seatId) {
        validate(SEAT_ID, seatId);

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(DELETE_SEAT_BY_ID);
            pstmt.setString(1, seatId);

            int count = pstmt.executeUpdate();

            if (count <= 0) {
                throw Util.prepareException("TAT-00013", null, seatId);
            }
            return count;
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00014", th, seatId);
        } finally {
            DBService.getInstance().closeResource(null, pstmt, conn);
        }
    }


    // To get Seat details
    private static Seat getSeatDetails(ResultSet rs) throws SQLException {
        Seat seat = new Seat();
        seat.setSeatId(rs.getString(SEAT_ID));
        seat.setBusId(rs.getString(BUS_ID));
        seat.setSeatName(rs.getString(SEAT_NAME));
        seat.setSeatType(rs.getString(SEAT_TYPE));
        seat.setBooked(rs.getBoolean(IS_BOOKED));
        seat.setUserCreated(rs.getString(USER_CREATED));
        seat.setUserUpdated(rs.getString(USER_UPDATED));
        seat.setDateCreated(rs.getTimestamp(DATE_CREATED));
        seat.setDateUpdated(rs.getTimestamp(DATE_UPDATED));
        return seat;
    }

    // To prepare Seat Details
    private static int setSeatDetails(PreparedStatement pstmt, Seat seat) throws SQLException {
        int ix = 1;
        pstmt.setString(ix++, seat.getSeatId());
        pstmt.setString(ix++, seat.getBusId());
        pstmt.setString(ix++, seat.getSeatName());
        pstmt.setString(ix++, seat.getSeatType());
        pstmt.setBoolean(ix++, seat.isBooked());
        pstmt.setString(ix++, seat.getUserCreated());
        pstmt.setString(ix++, seat.getUserUpdated());
        pstmt.setTimestamp(ix++, Timestamp.valueOf(LocalDateTime.now()));
        pstmt.setTimestamp(ix++, Timestamp.valueOf(LocalDateTime.now()));
        return ix;
    }

    private static final String COLUMN_NAMES = """
            seat_id,
            bus_id,
            seat_name,
            seat_type,
            is_booked,
            user_created,
            user_updated,
            date_created,
            date_updated
            """;
    private static final String CREATE_SEAT = """
        INSERT INTO seat( %s )
        values(default,?,?,?,?,?,?,?,?) """.formatted(COLUMN_NAMES);

    private static final String GET_SEAT_BY_ID = """
        SELECT %s
        FROM seat
        WHERE seat_id = ? """.formatted(COLUMN_NAMES);

    private static final String GET_SEAT_BY_SEAT_NAME = """
        SELECT %s
        FROM seat 
        WHERE seat_name = ? and bus_id = ?""".formatted(COLUMN_NAMES);

    private static final String GET_ALL_SEATS = """
        SELECT %s
        FROM seat """.formatted(COLUMN_NAMES);

    private static final String UPDATE_USER_BY_ID = """
        UPDATE seat 
        SET 
            bus_id = ?,
            seat_name = ?,
            seat_type = ?,
            is_booked = ?,
            user_created = ?,
            user_updated = ?,
            date_created = ?,
            date_updated = ?
        WHERE seat_id = ? 
        """;

    private static final String DELETE_SEAT_BY_ID = """
        DELETE FROM seat 
        WHERE seat_id = ? 
        """;

    private static final String DELETE_SEAT_BY_SEAT_NAME = """
        DELETE FROM seat 
        WHERE seat_name = ? and bus_id = ?
        """;

    private static final String SEAT  = "Seat";
    private static final String SEAT_ID  = "seat_id";
    private static final String BUS_ID  = "bus_id";
    private static final String SEAT_NAME  = "seat_name";
    private static final String SEAT_TYPE  = "seat_type";
    private static final String IS_BOOKED  = "is_booked";
    private static final String USER_CREATED  = "user_created";
    private static final String USER_UPDATED  = "user_updated";
    private static final String DATE_CREATED  = "date_created";
    private static final String DATE_UPDATED  = "date_updated";
}
