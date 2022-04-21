package com.aiccj.abtest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class ExceptionPrinter {

    public final static String packagePrefixName = "exPackagePrefixs";

    private final static List<String> packagePrefixs = new LinkedList<>();

    private final static Logger logger = LoggerFactory.getLogger(ExceptionPrinter.class);

    static {
        String props = System.getProperty(packagePrefixName);
        if (props == null || props.trim().equalsIgnoreCase("")) {
            throw new NullPointerException(String.format("缺少系统参数 %s ", packagePrefixName));
        }
        String[] strings = props.split("[,]");
        for (String string : strings) {
            if (string == null || string.trim().equalsIgnoreCase("")) {
                continue;
            }
            packagePrefixs.add(string.trim());
        }
        if (packagePrefixs.size() < 1) {
            throw new NullPointerException(String.format("系统参数 %s 中没有找到有效的package地址列表",
                packagePrefixName));
        }
        logger.info("获取到异常日志的包前缀信息 {} 个", packagePrefixs.size());
    }

    /**
     * 将异常信息打印到 StringBuffer 中
     *
     * @param e
     * @param buffer
     * @param otherExDepth 非本项目的异常打印深度
     */
    public final static void exPrintToStr(Throwable e, StringBuffer buffer, int otherExDepth) {
        buffer.append(e.getClass().getName())
            .append(": ")
            .append(e.getMessage())
            .append("\n");
        StackTraceElement[] stackTraces = e.getStackTrace();
        if (stackTraces != null && stackTraces.length > 0) {
            int i = 1;
            for (StackTraceElement stackTrace : stackTraces) {
                if (i <= otherExDepth || isProjectPackage(stackTrace.getClassName())) {
                    buffer.append("\tat ")
                        .append(stackTrace.getClassName())
                        .append(".")
                        .append(stackTrace.getMethodName())
                        .append("(")
                        .append(stackTrace.getFileName())
                        .append(":")
                        .append(stackTrace.getLineNumber())
                        .append(")\n");
                    i++;
                }
            }
        }
        Throwable cause = e.getCause();
        if (cause != null) {
            buffer.append("Caused by: ");
            ExceptionPrinter.exPrintToStr(cause, buffer, otherExDepth);
        }
    }


    /**
     * 将异常信息转为string
     *
     * @param e
     * @param otherExDepth
     * @return
     */
    public final static String exConvertToStr(Throwable e, int otherExDepth) {
        StringBuffer buffer = new StringBuffer();
        exPrintToStr(e, buffer, otherExDepth);
        return buffer.toString();
    }

    private final static boolean isProjectPackage(String className) {
        for (String packagePrefix : packagePrefixs) {
            if (className.contains(packagePrefix)) {
                return true;
            }
        }
        return false;
    }

}
