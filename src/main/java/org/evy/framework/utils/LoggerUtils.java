package org.evy.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evy.framework.enums.LogType;

import java.util.EnumMap;
import java.util.Map;

public final class LoggerUtils {

    private static final Logger logger= LogManager.getLogger(LoggerUtils.class);

    private static final Map<LogType,LogAction> logsMaps =new EnumMap<>(LogType.class);

    static{
        logsMaps.put(LogType.INFO,(clazz, message) -> logger.info(getLogMessage(clazz,message)));
        logsMaps.put(LogType.ERROR,(clazz, message) -> logger.error(getLogMessage(clazz,message)));
        logsMaps.put(LogType.WARN,(clazz, message) -> logger.warn(getLogMessage(clazz,message)));
        logsMaps.put(LogType.DEBUG,(clazz, message) -> logger.warn(getLogMessage(clazz,message)));
    }

    private static String getLogMessage(Class<?> clazz, String message) {
        return clazz.getSimpleName() + ": " + message;
    }

    public static void log(Class<?> clazz, LogType logType, String message) {
        try {
            logsMaps.getOrDefault(logType, (c, msg) -> logger.info(getLogMessage(c, msg))).log(clazz, message);
        } catch (Exception e) {
            logger.error("Error logging message in class {} with log type {}", clazz.getSimpleName(), logType, e);
        }
    }

    @FunctionalInterface
    private interface LogAction{
        void log(Class<?>clazz,String message);
    }

}
