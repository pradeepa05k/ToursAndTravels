package com.toursandtravels.util;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Util {    
    private static Util instance;
    private Logger logger;  
   
    private Util() {
        logger = LogManager.getLogger(Util.class);
    }
    
    public static Util getInstance() {
        // To ensure only one instance is created
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }

    // To validate values
    public static void validate(String paramName, Object paramValue) {
        if (paramValue == null) {
            throw Util.prepareException("TAT-00001", null, paramName);
        }

        if (paramValue instanceof String value) {
            if (value.trim().length() <= 0) {
                throw Util.prepareException("TAT-00020", null, paramName);
            }
        } else if (paramValue instanceof Integer value) {
            if (value <= 0) {
                throw Util.prepareException("TAT-00019", null, paramName, paramValue);
            }
        }
    }
   
    // To prepare TATException
    public static TATException prepareException(String key, Throwable th, Object ...args) {
        String message = I18N.getInstance().getMessage(key, args);
        getInstance().getLogger().error(message, th);
        return new TATException(message, th);
    } 
}