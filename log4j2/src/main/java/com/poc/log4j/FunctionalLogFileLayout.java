package com.poc.log4j;

import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 */
public class FunctionalLogFileLayout {
	/** treatment code */
	private String treatmentCode;

	/** user id */
	private String userId;

	/** treatment's start date */
	private Date startDate;

	/** treatment's parameters */
	private Map<String, Object> parameters;

	/** user language */
	private String language;

	/** treatment label that correspond to treatment code */
	private String treatmentName;

	private static final String MASK_LOAD_STATS = "%LOAD_STATS%";

	/** Backslash and Tabs */
	private String ls = System.getProperty("line.separator");

	public PatternLayout patternLayout;

	/**
	 * Constructor.
	 * 
	 * @param pTreatmentCode    treatment code (must be managed by
	 *                          {@link #getHeader()} function)
	 * @param pUserId           user id
	 * @param pStartDate        begin timestamp
	 * @param pParameters       parameters (if known, else put null and parameters
	 *                          will be printed when we closed the file). Parameters
	 *                          must correspond to the one managed in
	 *                          {@link #getHeader()} for the treatment code
	 * @param pLanguage         user language
	 * @param pTreatmentName    treatment label that correspond to treatment code
	 * @param pReportingContext the reporting context.
	 * @param pHideHeader       true to hide header (manage case when we reopen a
	 *                          log file yet closed)
	 */
	public FunctionalLogFileLayout(String pTreatmentCode, String pUserId, Date pStartDate,
			Map<String, Object> pParameters, String pLanguage, String pTreatmentName, boolean pHideHeader) {
		patternLayout = getLayout();
		treatmentCode = pTreatmentCode;
		userId = pUserId;
		startDate = pStartDate;
		parameters = pParameters;
		language = pLanguage;

		switch (pTreatmentName) {

		case "readCounterpartyAgentJob":
			treatmentName = "Load of Counterparties agent";
			break;

		case "readParameterValueJob":
			treatmentName = "Load of Parameter value";
			break;

		case "readReportingUnitAgentJob":
			treatmentName = "Load of Reporting unit agent";
			break;

		case "purgeDataJob":
			treatmentName = "Purge Database tables";
			break;

		default:
			treatmentName = pTreatmentName;
		}
	}

	/**
	 * @return
	 */
	protected PatternLayout getLayout() {
		LoggerContext ctx = getLoggerContext();
		Configuration config = ctx.getConfiguration();
		return PatternLayout.newBuilder().withConfiguration(config).withPattern("%d{HH:mm:ss.SSS} %level %msg%n")
				.withHeader("header").withFooter("footer").build();
	}

	/**
	 * @return
	 */
	public static LoggerContext getLoggerContext() {
		return (LoggerContext) LogManager.getContext(false);
	}

	/**
	 * @return
	 */
	public PatternLayout getPatternLayout() {
		return patternLayout;
	}
}