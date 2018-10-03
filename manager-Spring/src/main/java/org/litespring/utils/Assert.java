package org.litespring.utils;

public abstract class Assert {

        public static void notNull(Object  object ,String message) throws IllegalAccessException {

            if(object == null) throw new IllegalAccessException(message);

        }
}
