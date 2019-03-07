package com.poc.log4j;

import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class FunctionalLog {
	/**
	 * Functional Logger
	 */
	private static Log logger;

	public Log initFileLogger() {

		if (logger == null) {

			String filename = "Test" + ".log";

			FunctionalLogFileLayout functionalLogFileLayout = new FunctionalLogFileLayout("treatmentCode", "userId",
					new Date(), null, null, "readCounterpartyAgentJob", filename.endsWith(".log"));

			Logger functionnalLogger = new AnacreditLogger(filename, functionalLogFileLayout.getPatternLayout());

			functionnalLogger.setLevel(Level.ALL);

			logger = (Log) functionnalLogger;
		}
		return logger;
	}

	public void writeMessageInLogFile() {
		for (int i = 0; i < 5; i++) {
			logger.error(RandomLogStringGenerator.getAlphaNumericString(22));
		}
	}
}
