package com.tester;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CompatWarning {
    private static final String WARNING =
            "COMPAT locale provider will be removed in a future release";
    private static boolean logged;

    public static void main(String[] args) throws Throwable {
        File conf = new File("/Users/tvoniadka/tmp/compatlog.properties");
        if (!conf.canRead()) {
            throw new IOException("Can't read config file: " + conf.getAbsolutePath());
        }
        System.setProperty("java.util.logging.config.file", conf.getAbsolutePath());
        DateFormat.getInstance();
        if (!logged) {
            throw new RuntimeException("COMPAT warning message was not emitted");
        }
    }

    public static class CheckWarning extends Handler {
        @Override
        public void publish(LogRecord record) {
            var level = record.getLevel();
            var msg = record.getMessage();
            System.out.printf("""
                LogRecord emitted:
                    Level: %s
                    Message: %s
                """, level, msg);
            if (level == Level.WARNING && WARNING.equals(msg)) {
                logged = true;
            }
        }

        @Override
        public void flush() {}
        @Override
        public void close() throws SecurityException {}
    }
}