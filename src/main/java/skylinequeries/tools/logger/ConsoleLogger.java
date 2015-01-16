package skylinequeries.tools.logger;

import skylinequeries.tools.Immutable;

import java.util.Date;

/**
 * Log messages to the console.
 *
 * @author Vinh Nguyen
 */
public class ConsoleLogger extends Logger implements Immutable {

    /**
     * Constructor.
     */
    public ConsoleLogger() {

    }

    @Override
    public void log(String message, String currentClass, String method, int lineNo) {
        System.err.println(DATE_FORMAT.format(new Date()) + " | " + currentClass + " | " + method + " | Line: " + lineNo
                + "\n" + message);
    }
}
