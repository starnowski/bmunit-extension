/**
 *     Bmunit-extension, implementation of junit4 rule for library BMUnit.
 *     Copyright (C) 2019  Szymon Tarnowski
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 */
package com.github.starnowski.bmunit.extension.utils;

public class BMUnitUtils {

    public static boolean createJoin(Object key, int max)
    {
        return new BMUnitHelperWithoutRuleReference().createJoin(key, max);
    }

    public static boolean joinEnlist(Object key) {
        return new BMUnitHelperWithoutRuleReference().joinEnlist(key);
    }

    public static boolean joinWait(Object key, int count) {
        return new BMUnitHelperWithoutRuleReference().joinWait(key, count);
    }

    public static boolean joinWait(Object key, int count, long millis) {
        return new BMUnitHelperWithoutRuleReference().joinWait(key, count, millis);
    }

    public static boolean createCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().createCounter(o);
    }

    public static boolean createCounter(Object o, int count){
        return new BMUnitHelperWithoutRuleReference().createCounter(o, count);
    }

    public static boolean deleteCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().deleteCounter(o);
    }

    public static int incrementCounter(Object o, int amount){
        return new BMUnitHelperWithoutRuleReference().incrementCounter(o, amount);
    }

    public static int incrementCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().incrementCounter(o);
    }

    public static int decrementCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().decrementCounter(o);
    }

    public static int readCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().readCounter(o);
    }

    public static int readCounter(Object o, boolean zero){
        return new BMUnitHelperWithoutRuleReference().readCounter(o, zero);
    }

    public static boolean createRendezvous(Object identifier, int expected) {
        return new BMUnitHelperWithoutRuleReference().createRendezvous(identifier, expected);
    }

    public static int rendezvous(Object identifier)
    {
        return new BMUnitHelperWithoutRuleReference().rendezvous(identifier);
    }

    public static int getRendezvous(Object identifier, int expected) {
        return new BMUnitHelperWithoutRuleReference().getRendezvous(identifier, expected);
    }

    public static boolean isRendezvous(Object identifier, int expected) {
        return new BMUnitHelperWithoutRuleReference().isRendezvous(identifier, expected);
    }
}
