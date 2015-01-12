package skylinequeries.tools.logger;

/**
 * An abstract class for loggers, each specific logger
 * method should extend this class.
 *
 * @author Vinh Nguyen
 */
public abstract class Logger {

    /**
     * Constructor.
     */
    public Logger() {

    }

    /**
     * Returns the location the logging happened.
     *
     * @return the classname, the method, and the line number that generated the logging event
     */
    public static String[] getLocation() {
        String[] result = new String[3];
        Throwable throwable = new Throwable();
        StackTraceElement[] traces;

        throwable.fillInStackTrace();
        traces = throwable.getStackTrace();

        for (StackTraceElement trace : traces) {
            if (trace.getClassName().equals(Logger.class.getName())) continue;
            result[0] = trace.getClassName();
            result[1] = trace.getMethodName();
            result[2] = "" + trace.getLineNumber();
            break;
        }

        return result;
    }

    /**
     * Performs logging action.
     *
     * @param message      the message to log
     * @param currentClass the classname originating the log event
     * @param method       the method originating the log event
     * @param lineNo       the line number originating the log event
     */
    public abstract void log(String message, String currentClass, String method, int lineNo);

    /**
     * Performs logging action.
     *
     * @param message      the message to log
     * @param currentClass the classname originating the log event
     * @param method       the method originating the log events
     */
    public abstract void log(String message, String currentClass, String method);
}
