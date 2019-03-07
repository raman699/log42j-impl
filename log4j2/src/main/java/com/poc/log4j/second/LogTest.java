package com.poc.log4j.second;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LogTest
{

    public static void main(String... s)
    {
        LoggerContext ctx = getLoggerContext();
        Configuration config = ctx.getConfiguration();

        PatternLayout layout = getLayout(config);

        Appender appender = getAppender(config, layout);

        appender.start();
        config.addAppender(appender);

        Logger pLogger = getLogger(config, appender, layout);
        pLogger.error("Error Message One");
    }

    /**
     * @param config
     * @return
     */
    protected static PatternLayout getLayout(Configuration config)
    {
        return PatternLayout.newBuilder().withConfiguration(config).withPattern("%d{HH:mm:ss.SSS} %level %msg%n")
            .build();
    }

    /**
     * @param config
     * @param layout
     * @return
     */
    protected static RollingFileAppender getAppender(Configuration config, PatternLayout layout)
    {
        return RollingFileAppender.newBuilder().setConfiguration(config).withName("programmaticFileAppender")
            .withFilePattern("").withPolicy(SizeBasedTriggeringPolicy.createPolicy(Long.toString(1))).withLayout(layout)
            .withFileName("java.log").build();
    }



    public static Logger getLogger(Configuration config, Appender appender, Layout layout)
    {
        LoggerConfig loggerConfig =
            LoggerConfig.createLogger(false, Level.INFO, "programmaticLogger", "true", getAppenderRef(), null, config,
                null);
        loggerConfig.addAppender(appender, null, null);
        config.addLogger("programmaticLogger", loggerConfig);
        getLoggerContext().updateLoggers();

        Logger pLogger = LogManager.getLogger("programmaticLogger");

        return pLogger;
    }

    protected static LoggerContext getLoggerContext()
    {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        return ctx;
    }

    /**
     * @return
     */
    protected static AppenderRef[] getAppenderRef()
    {
        AppenderRef ref = AppenderRef.createAppenderRef("programmaticFileAppender", null, null);
        AppenderRef[] refs = new AppenderRef[] { ref };

        return refs;
    }
}
