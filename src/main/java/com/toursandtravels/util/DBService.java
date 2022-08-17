package com.toursandtravels.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DBService {            
    private static final Logger logger = LogManager.getLogger(DBService.class); 
    
    private static final String DB_CONFIG_FILENAME = "config/db_config.properties";
    
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    
    private static String jdbcURL;
    private static String user;
    private static String password;         
    private static boolean useConnectionPool; 
    
    private static DBService instance;   
    private HikariDataSource dataSource;
        
    public static DBService getInstance() {
        // To ensure only one instance is created
        if (instance == null) {
            synchronized (DBService.class) {
                if (instance == null) {
                    instance = new DBService();
                }
            }
        }
        return instance;
    }
        
    private DBService() {
        initDBService();
    } 

    // To load and initialize Properties
    private void initDBService() {
        InputStream inputStreamObj = null;
        try {                        
            Properties props = new Properties();

            inputStreamObj = DBService.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILENAME);
            props.load(inputStreamObj);
            
            logger.info(MessageFormat.format(MSG_001, DB_CONFIG_FILENAME));

            jdbcURL = props.getProperty(DB_URL);
            validateProperties(DB_URL, jdbcURL); 
            
            user = props.getProperty(DB_USER);
            validateProperties(DB_USER, user); 
            
            password = props.getProperty(DB_PASSWORD);
            validateProperties(DB_PASSWORD, password); 
            
            useConnectionPool = Boolean.parseBoolean(props.getProperty(USE_CONNECTION_POOL)); 
            if (useConnectionPool) {
                dataSource = prepareConnectionPool(props);
                logger.info(MSG_002);
            } else {
                logger.info(MSG_003);
            }
        } catch (Throwable th) {
            throw errorMessage(MSG_004, th, DB_CONFIG_FILENAME);
        } finally {
            if (inputStreamObj != null) {
                try { 
                    inputStreamObj.close(); 
                } catch (Throwable th) {
                    logger.warn(MSG_005);
                }
            }
        }
    }
    
    // To prepare connectionPool properties
    private HikariDataSource prepareConnectionPool(Properties props) {
        try {
            HikariConfig config = new HikariConfig(); 
        
            config.setJdbcUrl(jdbcURL);
            config.setUsername(user);
            config.setPassword(password); 
            
            int intProperty = Integer.parseInt(props.getProperty(MAXIMUM_POOL_SIZE, "10"));
            validateProperties(MAXIMUM_POOL_SIZE, intProperty);
            config.setMaximumPoolSize(intProperty);  

            String stringProperty = props.getProperty(CACHE_PREP_STMTS, "true");
            validateProperties(CACHE_PREP_STMTS, stringProperty);
            config.addDataSourceProperty(CACHE_PREP_STMTS, Boolean.parseBoolean(stringProperty));
            
            intProperty = Integer.parseInt(props.getProperty(PREP_STMT_CACHE_SIZE, "250"));
            validateProperties(PREP_STMT_CACHE_SIZE, intProperty);
            config.addDataSourceProperty(PREP_STMT_CACHE_SIZE, intProperty);
             
            intProperty = Integer.parseInt(props.getProperty(PREP_STMT_CACHE_SQL_LIMIT, "2048"));
            validateProperties(PREP_STMT_CACHE_SQL_LIMIT, intProperty);                     
            config.addDataSourceProperty(PREP_STMT_CACHE_SQL_LIMIT, intProperty);
            
            stringProperty = props.getProperty(IDLE_TIMEOUT, "60000");
            validateProperties(IDLE_TIMEOUT, stringProperty);
            config.setIdleTimeout(TimeUnit.SECONDS.toMillis(Long.parseLong(stringProperty)));
            
            stringProperty = props.getProperty(VALIDATION_TIMEOUT, "5000");
            validateProperties(VALIDATION_TIMEOUT, stringProperty);
            config.setValidationTimeout(TimeUnit.SECONDS.toMillis(Long.parseLong(stringProperty)));
            
            intProperty = Integer.parseInt(props.getProperty(LEAK_DETECTION_THRESHOLD, "0"));
            validateProperties(LEAK_DETECTION_THRESHOLD, intProperty);   
            config.setLeakDetectionThreshold(intProperty);

            stringProperty = props.getProperty(CONNECTION_TIMEOUT, "3000");
            validateProperties(CONNECTION_TIMEOUT, stringProperty);
            config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(Long.parseLong(stringProperty)));
            
            intProperty = Integer.parseInt(props.getProperty(MINIMUM_IDLE, "10"));
            validateProperties(MINIMUM_IDLE, intProperty);   
            config.setMinimumIdle(intProperty);

            stringProperty = props.getProperty(MAXLIFE_TIME, "1800000");
            validateProperties(MAXLIFE_TIME, stringProperty);
            config.setMaxLifetime(TimeUnit.SECONDS.toMillis(Long.parseLong(stringProperty)));   
            
            stringProperty = props.getProperty(TRANSACTION_ISOLATION, "TRANSACTION_READ_COMMITTED");
            validateProperties(TRANSACTION_ISOLATION, stringProperty);
            config.setTransactionIsolation(stringProperty);                               

            return new HikariDataSource(config);
        } catch (Throwable th) {
            throw errorMessage(MSG_006, null);
        }
    }
        
    // To validate properties values
    private void validateProperties(String propKey, Object propValue) {
        if (propValue == null) {
            throw errorMessage(MSG_007, null, propKey);
        }

        if (propValue instanceof String value) {
            if (value.trim().length() <= 0) {
                throw errorMessage(MSG_008, null, propKey);
            }
        } else if (propValue instanceof Integer value) {
            if (value <= 0) {
                throw errorMessage(MSG_009, null, propKey, propValue);
            }
        }
    }
    
    private RuntimeException errorMessage(String msg, Throwable th, Object ...args) {
        return new RuntimeException(MessageFormat.format(msg, args), th);  
    }
    
    // Connection to Postgresql Database
    public Connection getConnection(){
        try {
            if (useConnectionPool) {
                return dataSource.getConnection();     
            } else {
                return DriverManager.getConnection(jdbcURL, user, password);
            }            
        } catch(Throwable th) {
            throw errorMessage(MSG_010, th, jdbcURL, user);
        }
    }
    
    public void closeResource(ResultSet result, PreparedStatement preparedStatement, Connection connection) {
        if (result != null) {
            try { 
                result.close();          
            } catch (Throwable th) {
                logger.warn(MSG_011);
            }
        }
        if (preparedStatement != null) {
            try { 
                preparedStatement.close(); 
            } catch (Throwable th) {
                logger.warn(MSG_012);
            }
        }
        if (connection != null) {
            try { 
                connection.close(); 
            } catch (Throwable th) {
                logger.warn(MSG_013);
            }
        }
    }
    
    // To close connection pool
    public void closeConnectionPool() {
        if (useConnectionPool) {
            if (dataSource != null) {
                try { 
                    dataSource.close();                 
                } catch (Throwable th) {
                    logger.warn(MSG_014);
                }
            }
        }
    }    
    
    
    private static final String MSG_001 = "Loaded {0} file";
    private static final String MSG_002 = "Connection Using Pool";
    private static final String MSG_003 = "Connection Using DriverManager";
    private static final String MSG_004 = "Exception occured while reading {0} file";
    private static final String MSG_005 = "Exception occured while closing InputStream";
    private static final String MSG_006 = "Exception occured while reading pooling properties";
    private static final String MSG_007 = "{0} is null";
    private static final String MSG_008 = "{0} is empty";
    private static final String MSG_009 = "Invalid {0} property as {1}";
    private static final String MSG_010 = "Failed to get connection. Please check your JdbcURL - {0}, User Name - {1}, Password";
    private static final String MSG_011 = "Exception occured while closing ResultSet";
    private static final String MSG_012 = "Exception occured while closing PreparedStatement";
    private static final String MSG_013 = "Exception occured while closing Connection";
    private static final String MSG_014 = "Exception occured while closing Connection Pool";
    
    
    private static final String USE_CONNECTION_POOL = "cp.useConnectionPool";
    private static final String MAXIMUM_POOL_SIZE = "cp.maximumPoolSize";
    private static final String CACHE_PREP_STMTS = "cp.cachePrepStmts";
    private static final String PREP_STMT_CACHE_SIZE = "cp.prepStmtCacheSize";
    private static final String PREP_STMT_CACHE_SQL_LIMIT = "cp.prepStmtCacheSqlLimit";
    private static final String IDLE_TIMEOUT = "cp.idleTimeout";
    private static final String VALIDATION_TIMEOUT = "cp.validationTimeout";
    private static final String LEAK_DETECTION_THRESHOLD = "cp.leakDetectionThreshold";
    private static final String CONNECTION_TIMEOUT = "cp.connectionTimeout";
    private static final String MINIMUM_IDLE = "cp.minimumIdle";
    private static final String MAXLIFE_TIME = "cp.maxLifetime";
    private static final String TRANSACTION_ISOLATION = "cp.transactionIsolation";    
}