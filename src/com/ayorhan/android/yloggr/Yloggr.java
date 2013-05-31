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
    private static int logLevel = DEFAULT_LEVEL;

    /**
     *
     * @param object Object desired to be logged.
     */
    public static void d(Object object){
        if (isLoggable("", DEBUG)){
            Log.d(getTag(), logObject(object));
        }
    }

    /**
     *
     * @param object Object desired to be logged.
     * @param throwable Throwable desired to be logged.
     */
    public static void d(Object object, Throwable throwable){
        if (isLoggable("", DEBUG)){
            Log.d(getTag(), logObject(object), throwable);
        }
    }

    /**
     *
     * @param object Object desired to be logged.
     */
    public static void i(Object object){
        if (isLoggable("", INFO)){
            Log.i(getTag(), logObject(object));
        }
    }

    /**
     *
     * @param object Object desired to be logged.
     * @param throwable Throwable desired to be logged.
     */
    public static void i(Object object, Throwable throwable){
        if (isLoggable("", INFO)){
            Log.i(getTag(), logObject(object), throwable);
        }
    }

    /**
     *
     * @param object Object desired to be logged.
     */
    public static void e(Object object){
        if (isLoggable("", ERROR)){
            Log.e(getTag(), logObject(object));
        }
    }

    /**
     *
     * @param object Object desired to be logged.
     * @param throwable Throwable desired to be logged.
     */
    public static void e(Object object, Throwable throwable){
        if (isLoggable("", ERROR)){
            Log.e(getTag(), logObject(object), throwable);
        }
    }

    /**
     *
     * @param object Object desired to be logged.
     */
    public static void v(Object object){
        if (isLoggable("", VERBOSE)){
            Log.v(getTag(), logObject(object));
        }
    }

    /**
     *
     * @param object Object desired to be logged.
     * @param throwable Throwable desired to be logged.
     */
    public static void v(Object object, Throwable throwable){
        if (isLoggable("", VERBOSE)){
            Log.v(getTag(), logObject(object), throwable);
        }
    }

    /**
     *
     * @param object Object desired to be logged.
     */
    public static void w(Object object){
        if (isLoggable("", WARN)){
            Log.w(getTag(), logObject(object));
        }
    }

    /**
     *
     * @param object Object desired to be logged.
     * @param throwable Throwable desired to be logged.
     */
    public static void w(Object object, Throwable throwable){
        if (isLoggable("", WARN)){
            Log.w(getTag(), logObject(object), throwable);
        }
    }

    /**
     *
     * @return tag
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
     *
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
     *
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
     *
     * @param tag Tag
     * @param logLevel LogLevel
     * @return boolean
     */
    private static boolean isLoggable(String tag, Integer logLevel){
        return true;
        //isTagLoggable(tag, logLevel);
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