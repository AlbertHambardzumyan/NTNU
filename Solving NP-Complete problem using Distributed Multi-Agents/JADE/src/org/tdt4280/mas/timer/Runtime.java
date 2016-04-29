// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package org.tdt4280.mas.timer;

/**
 * @author Albert Hambardzumyan
 */
public class Runtime {

    public static void print(long duration, String className) {
        System.out.println(className + " " + (double) duration / 1 + " nanoseconds");
    }
}
/**
 * millisecond
 * microsecond
 * nanosecond
 *
 *System.out.println(String.format("%-2d: %s", (i + 1), toString(endTime - startTime)));
 */
