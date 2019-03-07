package com.poc.log4j;

public class FunctionalLogTest
{
    public static void main(String... s)
    {
        FunctionalLog log = new FunctionalLog();
        log.initFileLogger();
        log.writeMessageInLogFile();
    }
}
