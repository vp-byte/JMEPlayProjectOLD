package com.jmeplay;

/**
 * All resources for JMEPlay
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class Resources {
    private final static String baseEditor = "/com/jmeplay/";
    private final static String cssBase = baseEditor + "css/";
    private final static String iconsBase = baseEditor + "icons/";

    // CSS
    final static String cssEditor = cssBase + "editor.css";

    // ICONS
    final static String iconsConsoleClose = iconsBase + "close.svg";
    final static String iconsConsoleCopy = iconsBase + "copy.svg";
    final static String iconsConsoleDelete = iconsBase + "delete.svg";
    final static String iconsConsoleSelectAll = iconsBase + "selectall.svg";

    // KEYS FOR SETTINGS
    public final static String editorStageX = "editorStageX";
    public final static String editorStageY = "editorStageY";
    public final static String editorStageMinWidth = "editorStageMinWidth";
    public final static String editorStageMinHeight = "editorStageMinHeight";
    public final static String editorMaximized = "editorMaximized";

    // DEFAULT VALUES
    public final static Double editorDefaultStageX = null;
    public final static Double editorDefaultStageY = null;
    public final static Double editorDefaultStageMinWidth = null;
    public final static Double editorDefaultStageMinHeight = null;
    public final static Boolean editorDefaultMaximized = true;

}
