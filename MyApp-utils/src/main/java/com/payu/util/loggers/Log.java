package com.payu.util.loggers;

import org.apache.log4j.*;

import com.payu.util.properties.PropertiesFile;

import java.util.Enumeration;


public class Log {

    private static final Logger MAIN = Logger.getRootLogger();

    // Initialize log4j on class load
    static {
        try {

            PropertyConfigurator.configure(PropertiesFile.getProperties("com.payu.util.loggers.Log", "log4j.properties"));
            Logger.getRootLogger().info("LOG4J INIT OK");


        } catch (Exception e) {
            Logger.getRootLogger().fatal("Cannot initialize log", e);
        } finally {

        }
    }

    public static Logger getLogger() {
        Logger logger;
        String name = Thread.currentThread().getName();
        if (name.startsWith("__")) {
            // Named thread, use appropriate appender.
            String[] parts=name.split("__");
            //String logName = name.substring(2);
            String logName = parts[1];
            logger = Logger.getLogger(logName);
            if (!logger.getAllAppenders().hasMoreElements()) {
                createNewAppender(logName, logger);
            }
        } else {
            logger = MAIN;
        }
        return logger;
    }
/*	public static Logger getLogger() {
		Logger logger;
		String name = Thread.currentThread().getMessage()[4].getClassName() +"_"+ Thread.currentThread().getName();
		logger = Logger.getLogger(name);
		if (!logger.getAllAppenders().hasMoreElements()) {
				createNewAppender(name, logger);
		} else {
			logger = MAIN;
		}
		return logger;
	}*/

    private static void log(Priority p, Object o, Throwable t) {
        try {


            Logger logger = getLogger();
            logger.log(p, o, t);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void createNewAppender(String logName, Logger l) {

        try {
            if (!l.getAllAppenders().hasMoreElements()) {
                // Copy the file appender changing file name -- this only done
                // once
                l.setAdditivity(false);
                Appender appender = MAIN.getAppender("file");
                if (appender instanceof FileAppender) {
                    FileAppender fileAppender = (FileAppender) appender;
                    // Keep base path
                    final String FILE = fileAppender.getFile();
                    String filePath = FILE.substring(0, FILE.lastIndexOf(System
                            .getProperty("file.separator")) + 1);
                    FileAppender newFileAppender;
                    if(fileAppender instanceof DailyRollingFileAppender)
                    {
                        PatternLayout conv = new PatternLayout("%d %5p [%t] {%x} - %m%n");
                        DailyRollingFileAppender newrfa = new DailyRollingFileAppender(conv, (new StringBuilder(String.valueOf(filePath))).append(logName).append(".log").toString(), ((DailyRollingFileAppender)fileAppender).getDatePattern());
                        newFileAppender = newrfa;
                    } else
                    if(fileAppender instanceof DatedFileAppender)
                    {
                        PatternLayout conv = new PatternLayout("%d %5p [%t] {%x} - %m%n");
                        DatedFileAppender newrfa = new DatedFileAppender(filePath, logName, ".log");
                        newrfa.setLayout(conv);
                        newFileAppender = newrfa;
                    } else
                    {
                        newFileAppender = null;
                    }
                    if (newFileAppender != null) {
                        newFileAppender.setBufferedIO(fileAppender
                                .getBufferedIO());
                        newFileAppender.setBufferSize(fileAppender
                                .getBufferSize());
                        newFileAppender.setEncoding(fileAppender.getEncoding());
                        newFileAppender.setErrorHandler(fileAppender
                                .getErrorHandler());
                        newFileAppender.setImmediateFlush(fileAppender
                                .getImmediateFlush());
                        newFileAppender.setThreshold(fileAppender
                                .getThreshold());
                        newFileAppender.setName(logName);
                        newFileAppender.activateOptions();
                        l.addAppender(newFileAppender);

                        // Inherit the remaining of the appenders defined in
                        // root, if any
                        @SuppressWarnings("rawtypes")
                        Enumeration apps = MAIN.getAllAppenders();
                        while (apps.hasMoreElements()) {
                            Appender a = (Appender) apps.nextElement();
                            if (!a.getName().equals("file")) {
                                // skip file, already have our own
                                l.addAppender(a);
                            }
                        }
                        MAIN.info("Created new Log named " + logName);
                        MAIN.info("Output file for " + logName + ":" + filePath
                                + logName + ".log");

                    } else {
                        l.warn("Don't know how to handle appender: "
                                + fileAppender.getClass().getName());
                    }
                } else {
                    l.warn("Don't know how to handle appender: "
                            + appender.getClass().getName());
                }
            }

        } catch (Exception e) {
			/*
			 * The new logger cannot be created, write in root logger
			 */
            String name = Thread.currentThread().getName();
            MAIN.fatal("Can't create thread log named " + name, e);
        }
    }

    public static synchronized void createNewAppender(String logName, Logger l,
                                                      Priority p, Object o, Throwable t) {
        createNewAppender(logName, l);
        l.log(p, o, t);
    }

    public static boolean isDebugEnabled() {
        Logger logger = getLogger();
        return logger.isDebugEnabled();
    }

    public static void info(Object o) {
        Log.log(Level.INFO, o, null);
    }

    public static void info(Throwable t) {
        Log.log(Level.INFO, null, t);
    }

    public static void info(Object o, Throwable t) {
        Log.log(Level.INFO, o, t);
    }

    public static void error(Object o) {
        Log.log(Level.ERROR, o, null);
    }

    public static void error(Throwable t) {
        Log.log(Level.ERROR, null, t);
    }

    public static void error(Object o, Throwable t) {
        Log.log(Level.ERROR, o, t);
    }

    public static void fatal(Object o) {
        Log.log(Level.FATAL, o, null);
    }

    public static void fatal(Throwable t) {
        Log.log(Level.FATAL, null, t);
    }

    public static void fatal(Object o, Throwable t) {
        Log.log(Level.FATAL, o, t);
    }

    public static void debug(Object o) {
        Log.log(Level.DEBUG, o, null);
    }

    public static void debug(Throwable t) {
        Log.log(Level.DEBUG, null, t);
    }

    public static void debug(Object o, Throwable t) {
        Log.log(Level.DEBUG, o, t);
    }
}
