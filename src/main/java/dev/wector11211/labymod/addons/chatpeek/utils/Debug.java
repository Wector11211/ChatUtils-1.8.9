package dev.wector11211.labymod.addons.chatpeek.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Debug {
    private static Logger logger = LogManager.getLogger();

    public static Logger logger() {
        return logger;
    }

    public static void logger(Logger logger) {
        Debug.logger = logger;
    }
}
