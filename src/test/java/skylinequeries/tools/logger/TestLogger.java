package skylinequeries.tools.logger;

public class TestLogger {
    public static void main(String[] args) {
        Logger consoleLogger = new ConsoleLogger();
        consoleLogger.log("Greetings from console logger", Logger.getLocation()[0],
                Logger.getLocation()[1], Integer.valueOf(Logger.getLocation()[2]));

        Logger fileLogger = new FileLogger();
        fileLogger.log("Greetings from file logger", Logger.getLocation()[0],
                Logger.getLocation()[1], Integer.valueOf(Logger.getLocation()[2]));
    }
}
