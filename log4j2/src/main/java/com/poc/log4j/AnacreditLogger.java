package com.poc.log4j;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class AnacreditLogger extends Logger implements Log {

	String name;

	/**
	 * @param name
	 * @param layout
	 */
	public AnacreditLogger(String name, PatternLayout layout) {
		super(getLoggerContext(), name, new StringFormatterMessageFactory());
		this.name = name;
		attachAppender(layout);
		getLoggerContext().updateLoggers();
	}

	public void attachAppender(PatternLayout layout) {
		LoggerContext ctx = getLoggerContext();
		Configuration config = ctx.getConfiguration();
		Appender appender = getAppender(config, layout);
		appender.start();
		config.addAppender(appender);
		this.get().addAppender(appender, Level.ALL, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isErrorEnabled() {
		return super.isErrorEnabled();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFatalEnabled() {
		return super.isFatalEnabled();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWarnEnabled() {
		return super.isWarnEnabled();
	}

	public static LoggerContext getLoggerContext() {
		return (LoggerContext) LogManager.getContext(false);
	}

	/**
	 * @param config
	 * @return
	 */
	protected PatternLayout getLayout(Configuration config) {
		return PatternLayout.newBuilder().withConfiguration(config).withPattern("%d{HH:mm:ss.SSS} %level %msg%n")
				.withHeader("header").withFooter("footer").build();
	}

	/**
	 * @param config
	 * @param layout
	 * @return
	 */
	protected RollingFileAppender getAppender(Configuration config, PatternLayout layout) {
		DefaultRolloverStrategy dfs = DefaultRolloverStrategy.newBuilder().withMax("2").withConfig(config).build();
		return RollingFileAppender.newBuilder().setConfiguration(config).withName(name).withStrategy(dfs)
				.withFilePattern(name).withPolicy(SizeBasedTriggeringPolicy.createPolicy(Long.toString(1)))
				.withImmediateFlush(true).withLayout(layout).withFileName(name).build();
	}
}
