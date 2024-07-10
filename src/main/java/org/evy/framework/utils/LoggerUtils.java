
package org.evy.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evy.framework.enums.LogType;

import java.util.EnumMap;
import java.util.Map;

/**
 * Utility class for logging messages with different log levels and log types using Apache Log4j.
 * <p>
 * Provides methods to log messages as INFO, ERROR, WARN, and DEBUG based on {@link LogType}.
 */

public final class LoggerUtils {

    private static final Logger logger = LogManager.getLogger(LoggerUtils.class);

    // Maps LogType to corresponding logging actions using functional interfaces
    private static final Map<LogType, LogAction> logsMaps = new EnumMap<>(LogType.class);

    static {
        // Initialize logsMaps with logging actions for each LogType
        logsMaps.put(LogType.INFO, (clazz, message) -> logger.info(getLogMessage(clazz, message)));
        logsMaps.put(LogType.ERROR, (clazz, message) -> logger.error(getLogMessage(clazz, message)));
        logsMaps.put(LogType.WARN, (clazz, message) -> logger.warn(getLogMessage(clazz, message)));
        logsMaps.put(LogType.DEBUG, (clazz, message) -> logger.debug(getLogMessage(clazz, message)));
    }

    /**
     * Constructs a log message with the class name and the provided message.
     *
     * @param clazz   The class where the logging method is called.
     * @param message The log message to be formatted.
     * @return The formatted log message containing the class name and the message.
     */
    private static String getLogMessage(Class<?> clazz, String message) {
        return clazz.getSimpleName() + ": " + message;
    }

    /**
     * Logs a message with the specified log type for the given class.
     * Uses the corresponding logging action based on the provided {@link LogType}.
     * <p>
     * If no specific logging action is defined for the log type, falls back to INFO level logging.
     *
     * @param clazz   The class where the logging method is called.
     * @param logType The type of log message (INFO, ERROR, WARN, DEBUG).
     * @param message The log message to be logged.
     */
    public static void log(Class<?> clazz, LogType logType, String message) {
        try {
            logsMaps.getOrDefault(logType, (c, msg) -> logger.info(getLogMessage(c, msg))).log(clazz, message);
        } catch (Exception e) {
            logger.error("Error logging message in class {} with log type {}", clazz.getSimpleName(), logType, e);
        }
    }

    /**
     * Functional interface for defining logging actions.
     */
    @FunctionalInterface
    private interface LogAction {
        void log(Class<?> clazz, String message);
    }

}
