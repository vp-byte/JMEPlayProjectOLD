package com.jmeplay.plugin.console;

/**
 * All resources for console
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class Resources {
    private final static String baseConsole = "/com/jmeplay/plugin/console/";
    private final static String cssBase = baseConsole + "css/";
    private final static String iconsBase = baseConsole + "icons/";

    // CSS
    final static String cssConsole = cssBase + "jmeplay-console.css";

    // ICONS
    final static String iconsConsoleClose = iconsBase + "close.svg";
    final static String iconsConsoleCopy = iconsBase + "copy.svg";
    final static String iconsConsoleDelete = iconsBase + "delete.svg";
    final static String iconsConsoleSelectAll = iconsBase + "selectall.svg";

    // KEYS FOR SETTINGS
    final static String consoleToolsIconSize = "consoleToolsIconSize";
    final static String consoleToolsSpacing = "consoleToolsSpacing";
    final static String consoleWriteExceptions = "consoleWriteExceptions";

    // DEFAULT VALUES
    final static int consoleDefaultToolsIconSize = 24;
    final static int consoleDefaultToolsSpacing = 5;
    final static boolean consoleDefaultWriteExceptions = false;
}
