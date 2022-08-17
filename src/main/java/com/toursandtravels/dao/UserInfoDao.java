package com.toursandtravels.dao;

import com.toursandtravels.model.UserInfo;
import com.toursandtravels.util.DBService;
import com.toursandtravels.util.TATException;
import com.toursandtravels.util.Util;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.toursandtravels.util.Util.validate;

public class UserInfoDao {
    // Create User
    public int create(UserInfo user) {
        validate(USER_INFO, user);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            int ix = setUserDetails(pstmt, user);

            int result = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next() == false) {
                throw Util.prepareException("TAT-00002", null, user.getUserName(), user.getMobileNo());
            }

            int userId = rs.getInt(USER_INFO_ID);
            return userId;
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00002", th, user.getUserName(), user.getMobileNo());
        } finally {
            DBService.getInstance().closeResource(rs, pstmt, conn);
        }
    }

    // Get User information by Id
    public static UserInfo getById(int userInfoId) {
        validate(USER_INFO_ID, userInfoId);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(GET_USER_INFO_BY_ID);
            pstmt.setInt(1, userInfoId);

            rs = pstmt.executeQuery();
            if (rs.next() ==  false) {
                throw Util.prepareException("TAT-00003", null, userInfoId);
            }
            return getUserDetails(rs);
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00004", th, userInfoId);
        } finally {
            DBService.getInstance().closeResource(rs, pstmt, conn);
        }
    }

    // Get Student information by Mobile Number
    public static UserInfo getByMobileNo(String mobileNo) {
        validate(MOBILE_NO, mobileNo);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(GET_USER_BY_MOBILE_NUMBER);
            pstmt.setString(1, mobileNo);

            rs = pstmt.executeQuery();

            if (rs.next() == false) {
                throw Util.prepareException("TAT-00005", null, mobileNo);
            }
            return getUserDetails(rs);
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00006", th, mobileNo);
        } finally {
            DBService.getInstance().closeResource(rs, pstmt, conn);
        }
    }

    // Get User information by userName
    public static UserInfo getByUserName(String userName) {
        validate(USER_NAME, userName);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(GET_USER_BY_USER_NAME);
            pstmt.setString(1, userName);

            rs = pstmt.executeQuery();
            if (rs.next() == false) {
                throw Util.prepareException("TAT-00008", null, userName);
            }
            return getUserDetails(rs);
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00009", th, userName);
        } finally {
            DBService.getInstance().closeResource(rs, pstmt, conn);
        }
    }

    //List All User Information
    public static List<UserInfo> getAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(GET_ALL_USERS);
            rs = pstmt.executeQuery();

            if (rs.next() == false) {
                throw Util.prepareException("TAT-00010", null);
            }
            List<UserInfo> studentList = new ArrayList<UserInfo>();
            do {
                studentList.add(getUserDetails(rs));
            } while (rs.next());

            return studentList;
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00010", th);
        } finally {
            DBService.getInstance().closeResource(rs, pstmt, conn);
        }
    }

    // Update Student By Id
    public static int update(String userInfoId, UserInfo userInfo) {
        validate(USER_INFO_ID, userInfoId);
        validate(USER_INFO, userInfo);

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(UPDATE_USER_BY_ID);

            int ix = setUserDetails(pstmt, userInfo);
            pstmt.setString(ix, userInfoId);

            int count = pstmt.executeUpdate();

            if (count <= 0) {
                throw Util.prepareException("TAT-00011", null, userInfoId);
            }
            return count;
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00012", th, userInfoId);
        } finally {
            DBService.getInstance().closeResource(null, pstmt, conn);
        }
    }


    // Delete Student By Id
    public static int deleteById(String userInfoId) {
        validate(USER_INFO_ID, userInfoId);

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(DELETE_USER_BY_ID);
            pstmt.setString(1, userInfoId);

            int count = pstmt.executeUpdate();

            if (count <= 0) {
                throw Util.prepareException("TAT-00013", null, userInfoId);
            }
            return count;
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00014", th, userInfoId);
        } finally {
            DBService.getInstance().closeResource(null, pstmt, conn);
        }
    }

    // Delete Student By Mobile Number
    public static int deleteByMobileNo(String mobileNo) {
        validate(MOBILE_NO, mobileNo);

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBService.getInstance().getConnection();
            pstmt = conn.prepareStatement(DELETE_USER_BY_MOBILE_NUMBER);
            pstmt.setString(1, mobileNo);

            int count = pstmt.executeUpdate();

            if (count <= 0) {
                throw Util.prepareException("TAT-00015", null, mobileNo);
            }
            return count;
        } catch (TATException ex) {
            throw ex;
        } catch (Throwable th) {
            throw Util.prepareException("TAT-00016", th, mobileNo);
        } finally {
            DBService.getInstance().closeResource(null, pstmt, conn);
        }
    }

    // To get student details
    private static UserInfo getUserDetails(ResultSet rs) throws SQLException {
        UserInfo user = new UserInfo();
        user.setUserName(rs.getString(USER_NAME));
        user.setEmailId(rs.getString(EMAIL_ID));
        user.setPassword(rs.getString(PASSWORD));
        user.setMobileNo(rs.getString(MOBILE_NO));
        user.setAdmin(rs.getBoolean(IS_ADMIN));
        user.setDateCreated(rs.getTimestamp(DATE_CREATED));
        user.setDateUpdated(rs.getTimestamp(DATE_UPDATED));
        return user;
    }

    // To prepare User Details
    private static int setUserDetails(PreparedStatement pstmt, UserInfo user) throws SQLException {
        int ix = 1;
        pstmt.setString(ix++, user.getUserName());
        pstmt.setString(ix++, user.getEmailId());
        pstmt.setString(ix++, user.getPassword());
        pstmt.setString(ix++, user.getMobileNo());
        pstmt.setBoolean(ix++, user.isAdmin());
        pstmt.setTimestamp(ix++, Timestamp.valueOf(LocalDateTime.now()));
        pstmt.setTimestamp(ix++, Timestamp.valueOf(LocalDateTime.now()));
        return ix;
    }

    private static final String COLUMN_NAMES = """
            user_info_id,
            user_name,
            email_id,
            password,
            mobile_no,
            is_admin,
            date_created,
            date_updated
            """;
    private static final String CREATE_USER = """
        INSERT INTO user_info( %s )
        values(default,?,?,?,?,?,?,?) """.formatted(COLUMN_NAMES);

    private static final String GET_USER_INFO_BY_ID = """
        SELECT %s
        FROM user_info
        WHERE user_info_id = ? """.formatted(COLUMN_NAMES);

    private static final String GET_USER_BY_MOBILE_NUMBER = """
        SELECT %s
        FROM user_info 
        WHERE mobile_no = ? """.formatted(COLUMN_NAMES);

    private static final String GET_USER_BY_USER_NAME = """
        SELECT %s
        FROM user_info 
        WHERE username = ? """.formatted(COLUMN_NAMES);

    private static final String GET_ALL_USERS = """
        SELECT %s
        FROM user_info """.formatted(COLUMN_NAMES);

    private static final String UPDATE_USER_BY_ID = """
        UPDATE user_info 
        SET 
            user_name = ?,
            email_id = ?,
            password = ?,
            mobile_no = ?,
            is_admin = ?,
            date_created = ?,
            date_updated = ?
        WHERE user_info_id = ? 
        """;

    private static final String DELETE_USER_BY_ID = """
        DELETE FROM user_info 
        WHERE user_info_id = ? 
        """;

    private static final String DELETE_USER_BY_MOBILE_NUMBER = """
        DELETE FROM user_info 
        WHERE mobile_no = ? 
        """;

    private static final String USER_INFO  = "UserInfo";
    private static final String USER_INFO_ID  = "user_info_id";
    private static final String USER_NAME  = "username";
    private static final String EMAIL_ID  = "email_id";
    private static final String PASSWORD  = "password";
    private static final String MOBILE_NO  = "mobile_no";
    private static final String IS_ADMIN  = "is_admin";
    private static final String DATE_CREATED  = "date_created";
    private static final String DATE_UPDATED  = "date_updated";
}
