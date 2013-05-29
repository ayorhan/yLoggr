package com.ayorhan.android.yloggr;

import android.util.Log;

import java.security.PublicKey;
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

public class YLoggr {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private static final int DEFAULT_LEVEL = Log.INFO;
    private static int logLevel = DEFAULT_LEVEL;

    static class LogLevelComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer integer, Integer integer2) {
            return integer - integer2;
        }
    }

    private static HashMap<String, Integer> tagMap = new HashMap<String, Integer>();


    //========= DEBUG ==========
    public static void d(String message){
        if (isLoggable("", DEBUG)){
            Log.d(getTag(), message);
        }
    }

    public static void d(String message, Throwable throwable){
        if (isLoggable("", DEBUG)){
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
        if (isLoggable("", INFO)){
            Log.i(getTag(), message);
        }
    }

    public static void i(String message, Throwable throwable){
        if (isLoggable("", INFO)){
            Log.i(getTag(), message, throwable);
        }
    }

    // ======== ERROR =========
    public static void e(String message){
        if (isLoggable("", ERROR)){
            Log.e(getTag(), message);
        }
    }

    public static void e(String message, Throwable throwable){
        if (isLoggable("", ERROR)){
            Log.e(getTag(), message, throwable);
        }
    }

    // ======== VERBOSE =======
    public static void v(String message){
        if (isLoggable("", VERBOSE)){
            Log.v(getTag(), message);
        }
    }

    public static void v(String message, Throwable throwable){
        if (isLoggable("", VERBOSE)){
            Log.v(getTag(), message, throwable);
        }
    }

    // ======== WARN ===========
    public static void w(String message){
        if (isLoggable("", WARN)){
            Log.w(getTag(), message);
        }
    }

    public static void w(String message, Throwable throwable){
        if (isLoggable("", WARN)){
            Log.w(getTag(), message, throwable);
        }
    }

    // ======= PRIVATE ======
    private static String getTag(){ //TODO dont hardcode
        String fullClassName = Thread.currentThread().getStackTrace()[4].getClassName();
        String classNameShort = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[4].getLineNumber();


        return "YLoggr" + ":" + classNameShort + "." + methodName + "()[line" + lineNumber+"]";
    }

    private static boolean isLoggable(String tag, Integer logLevel){
        return true;
        //isTagLoggable(tag, logLevel);
    }

    private static Integer getInAppTagLogLevel(String tag){
        return tagMap.containsKey(tag)? tagMap.get(tag) : -1;
    }

    private void setInAppTagLogLevel(String tag, Integer logLevel){
        tagMap.put(tag, logLevel);
    }

    private static boolean isTagLoggable(String tag, Integer logLevel){
        Integer level = getInAppTagLogLevel(tag);

        if (level.equals(-1))
            return Log.isLoggable(tag,logLevel);

        LogLevelComparator comparator = new LogLevelComparator();
        return (comparator.compare(level,  DEFAULT_LEVEL) == 1);
    }

    //TODO recursive call for custom objects
    private static String logObject(Object object) {
        StringBuilder output = new StringBuilder();

        if (object instanceof boolean[]){
            boolean [] temp = (boolean[]) object;
            output.append("[");
            for (boolean elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length() - 1, ']');
            return output.toString();
        } else if (object instanceof byte[]){
            byte [] temp = (byte[]) object;
            output.append("[");
            for (byte elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        } else if (object instanceof short[]){
            short [] temp = (short[]) object;
            output.append("[");
            for (short elem : temp)
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
        } else if (object instanceof int[]){
            int [] temp = (int[]) object;
            output.append("[");
            for (int elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        } else if (object instanceof long[]){
            long [] temp = (long[]) object;
            output.append("[");
            for (long elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        } else if (object instanceof float[]){
            float [] temp = (float[]) object;
            output.append("[");
            for (float elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        } else if (object instanceof double[]){
            double [] temp = (double[]) object;
            output.append("[");
            for (double elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        }else if (object instanceof String[]){
            String [] temp = (String[]) object;
            output.append("[");
            for (String elem : temp)
                output.append("\"").append(elem).append("\"").append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        }   else if (object instanceof ArrayList){
            ArrayList list = (ArrayList) object;
            for (Object o : list){
                o.toString();
            }
            return null;

        } else if (object instanceof Integer){
             return null;
        } else { //Custom Object
            throw new IllegalArgumentException("This type is not supported by YLoggr.");
        }


    }
}