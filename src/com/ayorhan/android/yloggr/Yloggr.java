package com.ayorhan.android.yloggr;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ayorhan
 * Date: 10/20/12
 * Time: 2:00 PM
 *
 * API for sending log output.
 *
 * Generally, use the yLoggr.v() yLoggr.d() yLoggr.i() yLoggr.w() and yLoggr.e() methods.
 *
 * The order in terms of verbosity, from least to most is ERROR, WARN, INFO, DEBUG, VERBOSE.
 * Verbose should never be compiled into an application except during development.
 * Debug logs are compiled in but stripped at runtime. Error, warning and info logs are always kept.
 *
 * Tip: You don't need to declare a TAG, yLoggr will automatically grab the class name as the TAG.
 * For example, if the class name is MyActivity.java, TAG will be MyActivity.
 *
 * Tip: Don't forget that when you make a call like
 *
 *      yLoggr.v("index=" + i);
 *
 * that when you're building the string to pass into yLoggr.d, the compiler uses a StringBuilder and
 * at least three allocations occur: the StringBuilder itself, the buffer, and the String object.
 *
 * Realistically, there is also another buffer allocation and copy, and even more pressure on the gc.
 * That means that if your log message is filtered out, you might be doing significant work and
 * incurring significant overhead.
 */

/*
    TODO #1 In app general log level - if set
    TODO #2 In app TAG log level
 */
public class YLoggr {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private static final int DEFAULT_LEVEL = Log.INFO;

    private static final String INFO_STACK_STRING = "logObject";
    private static final String TAG_STACK_STRING = "android.app.Activity";
    private static final String YLOGGR = "yLoggr";
    private static final String UNKNOWN = "Unknown";

    /**
     * Send a DEBUG log message.
     * @param object The object you would like logged.
     */
    public static void d(Object object){
        if (isLoggable(getTag(), DEBUG)){
            Log.d(getTag(), logObject(object));
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     * @param object The object you would like logged.
     * @param tr An exception to log
     */
    public static void d(Object object, Throwable tr){
        if (isLoggable(getTag(), DEBUG)){
            Log.d(getTag(), logObject(object), tr);
        }
    }

    /**
     * Send an ERROR log message.
     * @param object The object you would like logged.
     */
    public static void e(Object object){
        if (isLoggable(getTag(), ERROR)){
            Log.e(getTag(), logObject(object));
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     * @param object The object you would like logged.
     * @param tr An exception to log
     */
    public static void e(Object object, Throwable tr){
        if (isLoggable(getTag(), ERROR)){
            Log.e(getTag(), logObject(object), tr);
        }
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     * @return
     */
    public static String getStackTraceString(Throwable tr){
        return Log.getStackTraceString(tr);
    }

    /**
     * Send an INFO log message.
     * @param object The object you would like logged.
     */
    public static void i(Object object){
        if (isLoggable(getTag(), INFO)){
            Log.i(getTag(), logObject(object));
        }
    }

    /**
     * Send an INFO log message and log the exception.
     * @param object The object you would like logged.
     * @param tr An exception to log
     */
    public static void i(Object object, Throwable tr){
        if (isLoggable(getTag(), INFO)){
            Log.i(getTag(), logObject(object), tr);
        }
    }

       /*
        Checks to see whether or not a log for the specified tag is loggable at the specified level.
        The default level of any tag is set to INFO. This means that any level above and including INFO
        will be logged. Before you make any calls to a logging method you should check to see if your
        tag should be logged. You can change the default level by setting a system property:
        'setprop log.tag.<YOUR_LOG_TAG> <LEVEL>' Where level is either
        VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT, or SUPPRESS. SUPPRESS will turn off all logging for
        your tag. You can also create a local.prop file that with the following in it:
        'log.tag.<YOUR_LOG_TAG>=<LEVEL>' and place that in /data/local.prop.
     */

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     * @param tag The tag to check.
     * @param logLevel The level to check.
     * @return Whether or not that this is allowed to be logged.
     * @throws IllegalArgumentException is thrown if the tag.length() > 23.
     */
    public static boolean isLoggable(String tag, Integer logLevel){
        return Log.isLoggable(tag, logLevel);
    }

    /**
     * Low-level logging call.
     * @param priority The priority/type of this log message
     * @param object The message you would like logged.
     * @return The number of bytes written.
     */
    public static int println(int priority, Object object){
        return Log.println(priority, getTag(), logObject(object));
    }

    /**
     * Send a VERBOSE log message.
     * @param object The object you would like logged.
     */
    public static void v(Object object){
        if (isLoggable(getTag(), VERBOSE)){
            Log.v(getTag(), logObject(object));
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     * @param object The object you would like logged.
     * @param tr An exception to log
     */
    public static void v(Object object, Throwable tr){
        if (isLoggable(getTag(), VERBOSE)){
            Log.v(getTag(), logObject(object), tr);
        }
    }

    /**
     * Log a WARN exception
     * @param tr An exception to log
     */
    public static void w(Throwable tr){
        if (isLoggable(getTag(), WARN)){
            Log.w(getTag(), tr);
        }
    }

    /**
     * Send a WARN log message.
     * @param object The object you would like logged.
     */
    public static void w(Object object){
        if (isLoggable(getTag(), WARN)){
            Log.w(getTag(), logObject(object));
        }
    }

    /**
     * Send a WARN log message and log the exception.
     * @param object The object you would like logged.
     * @param tr An exception to log
     */
    public static void w(Object object, Throwable tr){
        if (isLoggable(getTag(), WARN)){
            Log.w(getTag(), logObject(object), tr);
        }
    }

    /**
     * Send a WARN log message.
     * @param object The object you would like logged.
     */
    public static void wtf(Object object){
        if (isLoggable(getTag(), ASSERT)){
            Log.wtf(getTag(), logObject(object));
        }
    }

    /**
     * Send a WARN log message and log the exception.
     * @param object The object you would like logged.
     * @param tr An exception to log
     */
    public static void wtf(Object object, Throwable tr){
        if (isLoggable(getTag(), ASSERT)){
            Log.wtf(getTag(), logObject(object), tr);
        }
    }

    /**
     * Generate log tag automatically.
     * @return tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     */
    private static String getTag(){
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackIndex = getStackIndex(trace, false);
        if (stackIndex != -1){
            String fullClassName = Thread.currentThread().getStackTrace()[stackIndex].getClassName();
            String classNameShort = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            return classNameShort;
        }
        return YLOGGR;
    }

    /**
     * Generate log info automatically.
     * @param object Object desired to be logged.
     * @return info
     */
    private static String getInfo(Object object){
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String info = "%s(){%s}[line%d] ";
        int stackIndex = getStackIndex(trace, true);
        if (stackIndex != -1){
            String methodName = Thread.currentThread().getStackTrace()[stackIndex].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[stackIndex].getLineNumber();
            return String.format(info, methodName, object.getClass().getSimpleName(), lineNumber);
        }
        return String.format(info, UNKNOWN, UNKNOWN, -1);
    }

    /**
     * Get stack index
     * @param trace trace
     * @param info flag that shows info or tag
     * @return stackIndex
     */
    private static int getStackIndex(StackTraceElement[] trace, boolean info) {
        int stackIndex = -1;
        for (int i=0; i<trace.length; i++){
            if (info)
                if(trace[i].getMethodName().equals(INFO_STACK_STRING)){
                    stackIndex = i + 2;
                    break;
                }
            else
                if(trace[i].getClassName().equals(TAG_STACK_STRING)){
                    stackIndex = i - 1;
                    break;
                }
        }
        return stackIndex;
    }



    //TODO recursive call for custom objects

    /**
     * Object log generator
     * @param object Object desired to be logged.
     * @return Object log string
     */
    private static String logObject(Object object) {
        StringBuilder output = new StringBuilder();
        output.append(getInfo(object));
        if (object instanceof Integer){
            output.append(String.format("%d", (Integer) object));
            return output.toString();
        } else if (object instanceof Boolean){
            output.append(String.format("%b", (Boolean) object));
            return output.toString();
        } else if (object instanceof Double){
            output.append(String.format("%f", (Double) object));
            return output.toString();
        } else if (object instanceof Float){
            output.append(String.format("%f", (Float) object));
            return output.toString();
        } else if (object instanceof Byte){
            output.append(String.format("%h", (Byte) object));
            return output.toString();
        } else if (object instanceof String){
            output.append(String.format("%s", (String) object));
            return output.toString();
        } else if (object instanceof Character){
            output.append(String.format("%c", (Character) object));
            return output.toString();
        } else if (object instanceof Long){
            output.append(String.format("%d", ((Long) object)));
            return output.toString();
        } else if (object instanceof Short){
            output.append(String.format("%d", ((Short) object)));
            return output.toString();
        } else if (object instanceof boolean[]){
            boolean [] temp = (boolean[]) object;
            output.append("[");
            for (boolean elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length() - 1, ']');
            return output.toString();
        } else if (object instanceof Boolean[]){
            Boolean [] temp = (Boolean[]) object;
            output.append("[");
            for (Boolean elem : temp)
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
        } else if (object instanceof Byte[]){
            Byte[] temp = (Byte[]) object;
            output.append("[");
            for (Byte elem : temp)
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
        } else if (object instanceof Short[]){
            Short [] temp = (Short[]) object;
            output.append("[");
            for (Short elem : temp)
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
        } else if (object instanceof Character[]){
            Character [] temp = (Character[]) object;
            output.append("[");
            for (Character elem : temp)
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
        } else if (object instanceof Integer[]){
            Integer [] temp = (Integer[]) object;
            output.append("[");
            for (Integer elem : temp)
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
        } else if (object instanceof Long[]){
            Long [] temp = (Long[]) object;
            output.append("[");
            for (Long elem : temp)
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
        } else if (object instanceof Float[]){
            Float [] temp = (Float[]) object;
            output.append("[");
            for (Float elem : temp)
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
        } else if (object instanceof Double[]){
            Double [] temp = (Double[]) object;
            output.append("[");
            for (Double elem : temp)
                output.append(elem).append(",");
            output.setCharAt(output.length()-1, ']');
            return output.toString();
        } else if (object instanceof String[]){
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
        } else { //Custom
            for (Field f : object.getClass().getFields()){
                Class fieldType = f.getType();
                String fieldName = f.getName();

                if(fieldType.isArray()){
                    Class componentType = fieldType.getComponentType();

                    if (componentType == Boolean.class ||
                            componentType == Boolean.TYPE ||
                            componentType == Integer.class ||
                            componentType == Integer.TYPE ||
                            componentType == Long.class ||
                            componentType == Long.TYPE ||
                            componentType == Float.class ||
                            componentType == Float.TYPE ||
                            componentType == Double.class ||
                            componentType == Double.TYPE ||
                            componentType == String.class) {


                    } else {

                    }

                } else if (Collection.class.isAssignableFrom(fieldType)){
                    Type genericType = f.getGenericType();
                    Class componentType = (Class) ((ParameterizedType) genericType)
                            .getActualTypeArguments()[0];
                } else{

                }
            }
            return "This type is not supported by YLoggr.";
        }
    }
}