package com.toursandtravels.util;

import java.util.ResourceBundle;
import java.util.Locale;

import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class I18N {
    private static final Logger logger = LogManager.getLogger(I18N.class);
    
    private static I18N instance;  
    private Locale currentLocale;
    private ResourceBundle bundle;
    
    private static final String BUNDLE = "messages/sms_messages";
    
    public static I18N getInstance() {
        // To ensure only one instance is created
        if (instance == null) {
            synchronized (I18N.class) {
                if (instance == null) {
                    instance = new I18N();
                }
            }
        }
        return instance;
    }
    
    private I18N() {
        init();
    }
    
    private void init() {     
        try {
            bundle = ResourceBundle.getBundle(BUNDLE, getLocale());            
        } catch (Throwable th) {
            throw new RuntimeException(formatMessage(MSG_001, BUNDLE, getLocale()));
        }
    }
    
    public void setLocale(String language, String country) {
        currentLocale = new Locale(language, country);
    }
    
    public Locale getLocale() {
        if(currentLocale != null) {
            logger.info(formatMessage(MSG_002, currentLocale));
            return currentLocale;
        } else {
            logger.info(formatMessage(MSG_003, Locale.getDefault()));      
            return Locale.getDefault();
        }
    }
    
    public String getMessage(String messageId, Object... args) {
        if(messageId == null || messageId.trim().length() <= 0) {
            return formatMessage(MSG_004, messageId);
        }
        try {
            return formatMessage(bundle.getString(messageId), args);            
        } catch(Throwable th) {
            return formatMessage(MSG_005, messageId, BUNDLE);
        }
    }
    
    private String formatMessage(String msg, Object ...args) {
        return MessageFormat.format(msg, args);  
    }
    
    private static final String MSG_001 = "Failed to get Bundle {0} for the given locale {1}";
    private static final String MSG_002 = "Given locale {0}";
    private static final String MSG_003 = "Using default locale ";
    private static final String MSG_004 = "Invalid Message Id - {0}";
    private static final String MSG_005 = "Failed to get message for the given {0} from the file {1}";
}