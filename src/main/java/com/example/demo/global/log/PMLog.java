package com.example.demo.global.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class PMLog {
    private static final Logger L = LoggerFactory.getLogger(PMLog.class);

    public static final Marker PROJECT_ALERT = MarkerFactory.getMarker("PROJECT_ALERT");
    public static final Marker PROJECT_WORK = MarkerFactory.getMarker("PROJECT_WORK");
    public static final Marker PROJECT_CREW = MarkerFactory.getMarker("PROJECT_CREW");
    public static final Marker TRUST_POINT = MarkerFactory.getMarker("TRUST_POINT");
    public static final Marker TOKEN_REISSUE = MarkerFactory.getMarker("TOKEN_REISSUE");

    public static void d(Object msg) {
        L.debug(msg == null ? "null" : msg.toString());
    }

    public static void d(String format, Object... arg) {
        L.debug(format, arg);
    }

    public static void d(Object msg, Throwable thrown) {
        L.debug(msg == null ? "null" : msg.toString(), thrown);
    }

    public static void d(Class<?> clazz, Object msg) {
        L.debug("..." + clazz.getSimpleName() + " : " + msg);
    }

    public static void d(Class<?> clazz, Object msg, Throwable thrown) {
        L.debug("..." + clazz.getSimpleName() + " : " + msg, thrown);
    }

    public static void d(Marker marker, Object msg) {
        L.debug(marker, msg == null ? "null" : msg.toString());
    }

    public static void d(Marker marker, String format, Object... arg) {
        L.debug(marker, format, arg);
    }

    public static void d(Marker marker, Object msg, Throwable thrown) {
        L.debug(marker, msg == null ? "null" : msg.toString(), thrown);
    }

    public static void d(Marker marker, Class<?> clazz, Object msg) {
        L.debug(marker, "..." + clazz.getSimpleName() + " : " + msg);
    }

    public static void d(Marker marker, Class<?> clazz, Object msg, Throwable thrown) {
        L.debug(marker, "..." + clazz.getSimpleName() + " : " + msg, thrown);
    }

    public static void i(Object msg) {
        String msg2 = msg == null ? "null" : msg.toString();
        L.info(msg2);
    }

    public static void i(Object msg, Throwable thrown) {
        String msg2 = msg == null ? "null" : msg.toString();
        L.info(msg2, thrown);
    }

    public static void i(Class<?> clazz, Object msg) {
        String msg2 = msg == null ? "null" : msg.toString();
        L.info("..." + clazz.getSimpleName() + " : " + msg2);
    }

    public static void i(Class<?> clazz, Object msg, Throwable thrown) {
        String msg2 = msg == null ? "null" : msg.toString();
        L.info("..." + clazz.getSimpleName() + " : " + msg2, thrown);
    }

    public static void i(Marker marker, Object msg) {
        String msg2 = msg == null ? "null" : msg.toString();
        L.info(marker, msg2);
    }

    public static void i(Marker marker, String format, Object... arg) {
        L.info(marker, format, arg);
    }

    public static void i(Marker marker, Object msg, Throwable thrown) {
        String msg2 = msg == null ? "null" : msg.toString();
        L.info(marker, msg2, thrown);
    }

    public static void i(Marker marker, Class<?> clazz, Object msg) {
        String msg2 = msg == null ? "null" : msg.toString();
        L.info(marker, "..." + clazz.getSimpleName() + " : " + msg2);
    }

    public static void i(Marker marker, Class<?> clazz, Object msg, Throwable thrown) {
        String msg2 = msg == null ? "null" : msg.toString();
        L.info(marker, "..." + clazz.getSimpleName() + " : " + msg2, thrown);
    }

    public static void e(Object msg) {
        L.error(msg == null ? "null" : msg.toString());
    }

    public static void e(String format, Object... arg) {
        L.error(format, arg);
    }

    public static void e(Object msg, Throwable thrown) {
        L.error(msg == null ? "null" : msg.toString(), thrown);
    }

    public static void e(Class<?> clazz, Object msg) {
        L.error("..." + clazz.getSimpleName() + " : " + msg);
    }

    public static void e(Class<?> clazz, Object msg, Throwable thrown) {
        L.error("..." + clazz.getSimpleName() + " : " + msg, thrown);
    }

    public static void e(Marker marker, Object msg) {
        L.error(marker, msg == null ? "null" : msg.toString());
    }

    public static void e(Marker marker, String format, Object... arg) {
        L.error(marker, format, arg);
    }

    public static void e(Marker marker, Object msg, Throwable thrown) {
        L.error(marker, msg == null ? "null" : msg.toString(), thrown);
    }

    public static void e(Marker marker, Class<?> clazz, Object msg) {
        L.error(marker, "..." + clazz.getSimpleName() + " : " + msg);
    }

    public static void e(Marker marker, Class<?> clazz, Object msg, Throwable thrown) {
        L.error(marker, "..." + clazz.getSimpleName() + " : " + msg, thrown);
    }

}
