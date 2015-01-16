package skylinequeries.tools.logger;

import skylinequeries.tools.Immutable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

/**
 * Log messages to a file.
 *
 * @author Vinh Nguyen
 */
public class FileLogger extends Logger implements Immutable {

    /**
     * The logging file.
     */
    public File logFile;

    /**
     * Line feed.
     */
    public String lineFeed;

    /**
     * Constructor.
     */
    public FileLogger() {
        logFile = getLogFile();
        try {
            if ((logFile != null) && logFile.exists()) logFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lineFeed = System.getProperty("line.separator");
    }

    /**
     * Returns the log file.
     * (For simplicity, we just hardcode the file path here)
     *
     * @return the log file
     */
    protected File getLogFile() {
        String filename = "src/test/log/skyline.log";
        return new File(filename);
    }

    /**
     * Appends the given string to the log file.
     *
     * @param s the string to append
     */
    protected void append(String s) {
        BufferedWriter writer;
        if (logFile == null) return;
        try {
            writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(s);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(String message, String currentClass, String method, int lineNo) {
        append(DATE_FORMAT.format(new Date()) + " | " + currentClass + " | " + method + " | Line: " + lineNo
                + lineFeed + message + lineFeed);
    }
}
