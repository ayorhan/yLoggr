package com.ayorhan.android.yloggr;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ayorhan
 * Date: 10/20/12
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
// TODO log levels -> Package - Class - Method -> Tags
// first look at in app log level, then system

//TODO different data types

public class Yloggr {

    private enum LogLevel{
        UNSET(-1),
        VERBOSE(Log.VERBOSE),   // 2
        DEBUG(Log.DEBUG),       // 3
        INFO(Log.INFO),         // 4
        WARN(Log.WARN),         // 5
        ERROR(Log.ERROR);       // 6

        private final int id;

        private static final Map<Integer, LogLevel> lookup = new HashMap<Integer, LogLevel>();

        static {
            for (LogLevel level : LogLevel.values()){
                lookup.put(level.getId(), level);
            }
        }
        private LogLevel(int id){
            this.id = id;
        }

        public int getId(){
            return id;
        }

        public static LogLevel get(int id){
            return lookup.get(id);
        }
    }

    static class LogLevelComparator implements Comparator<LogLevel> {
        @Override
        public int compare(LogLevel logLevel1, LogLevel logLevel2) {
            return logLevel1.getId() - logLevel2.getId();
        }
    }

    private static HashMap<String, LogLevel> tagMap = new HashMap<String, LogLevel>();

    private static final int DEFAULT_LEVEL = Log.INFO;

    //========= DEBUG ==========
    public static void d(String message){
        if (isLoggable("", LogLevel.DEBUG)){
            Log.d(getTag(), message);
        }
    }

    public static void d(String message, Throwable throwable){
        if (isLoggable("", LogLevel.DEBUG)){
            Log.d(getTag(), message, throwable);
        }
    }

    public static void d(String message, Object object){
        d(message + ":" + logObject(object));
    }

    public static void d(Object object){
        d(logObject(object));
    }

    // ========= INFO ========
    public static void i(String message){
        if (isLoggable("", LogLevel.INFO)){
            Log.i(getTag(), message);
        }
    }

    public static void i(String message, Throwable throwable){
        if (isLoggable("", LogLevel.INFO)){
            Log.i(getTag(), message, throwable);
        }
    }

    // ======== ERROR =========
    public static void e(String message){
        if (isLoggable("", LogLevel.ERROR)){
            Log.e(getTag(), message);
        }
    }

    public static void e(String message, Throwable throwable){
        if (isLoggable("", LogLevel.ERROR)){
            Log.e(getTag(), message, throwable);
        }
    }

    // ======== VERBOSE =======
    public static void v(String message){
        if (isLoggable("", LogLevel.VERBOSE)){
            Log.v(getTag(), message);
        }
    }

    public static void v(String message, Throwable throwable){
        if (isLoggable("", LogLevel.VERBOSE)){
            Log.v(getTag(), message, throwable);
        }
    }

    // ======== WARN ===========
    public static void w(String message){
        if (isLoggable("", LogLevel.WARN)){
            Log.w(getTag(), message);
        }
    }

    public static void w(String message, Throwable throwable){
        if (isLoggable("", LogLevel.WARN)){
            Log.w(getTag(), message, throwable);
        }
    }

    // ======= PRIVATE ======
    private static String getTag(){//TODO dont hardcode
        String fullClassName = Thread.currentThread().getStackTrace()[4].getClassName();
        String classNameShort = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[4].getLineNumber();

        return "ANDLOGGR" + ":" + classNameShort + "." + methodName + "()[line" + lineNumber+"]";
    }

    private static boolean isLoggable(String tag, LogLevel logLevel){
        return true;
        //isTagLoggable(tag, logLevel);
    }

    private static LogLevel getInAppTagLogLevel(String tag){
        return tagMap.containsKey(tag)? tagMap.get(tag) : LogLevel.UNSET;
    }

    private void setInAppTagLogLevel(String tag, LogLevel logLevel){
        tagMap.put(tag, logLevel);
    }

    private static boolean isTagLoggable(String tag, LogLevel logLevel){
        LogLevel level = getInAppTagLogLevel(tag);

        if (level.equals(LogLevel.UNSET))
            return Log.isLoggable(tag,logLevel.getId());

        LogLevelComparator comparator = new LogLevelComparator();
        return (comparator.compare(level,  LogLevel.get(DEFAULT_LEVEL)) == 1);
    }

    //TODO recursive call for custom objects
    private static String logObject(Object object) {
        StringBuilder output = new StringBuilder();

        if (object instanceof int[]){
            int [] temp = (int[]) object;
            output.append("[");
            for (int elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        } else if (object instanceof boolean[]){
            boolean [] temp = (boolean[]) object;
            output.append("[");
            for (boolean elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length() - 1, ']');
            return output.toString();
        } else if (object instanceof String[]){
            String [] temp = (String[]) object;
            output.append("[");
            for (String elem : temp)
                output.append("\"").append(elem).append("\"").append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        } else if (object instanceof long[]){
            long [] temp = (long[]) object;
            output.append("[");
            for (long elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        } else if (object instanceof char[]){
            char [] temp = (char[]) object;
            output.append("[");
            for (char elem : temp)
                output.append("\'").append(elem).append("\'").append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        } else if (object instanceof ArrayList){
            ArrayList list = (ArrayList) object;
            for (Object o : list){
                o.toString();
            }
            return null;

        } else { //Custom Object
            throw new IllegalArgumentException("This type is not supported by Yloggr.");
        }


    }
}