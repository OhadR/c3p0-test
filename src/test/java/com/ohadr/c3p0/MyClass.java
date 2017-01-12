package com.ohadr.c3p0;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * simulate a case where set.size() return negative number
 * @author ohadr
 *
 */
public class MyClass
{
    public static void main(String[] arg) 
    {
        final Set<Integer> toRemoveElement = new HashSet<Integer>();
        final Set<Integer> toStoreElements = new HashSet<Integer>();
        
        final Set<Integer> toRemoveElement_sync = ConcurrentHashMap.newKeySet();
        
        // populate collections for test
        for (int i = 0; i < 1000; i++) {
            Integer obj = new Integer(i);
            toRemoveElement.add(obj);
            toStoreElements.add(obj);
            toRemoveElement_sync.add(obj);
        }
        // two threads that will be using collection2 
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (Integer o : toStoreElements) {
                    removeObject(toRemoveElement_sync, o);		//using "toRemoveElement" causes concur. issue.
                }
            }
        };

        Thread newThread1 = new Thread(r);
        Thread newThread2 = new Thread(r);
        newThread1.start();
        newThread2.start();
    }

    //making this method "synchronized" also solves this concurency issue.
    private static /*synchronized*/ void removeObject(Set<Integer> toRemoveElement, Integer o) {
        toRemoveElement.remove(o);
        int size = toRemoveElement.size();
        if (size < 0) 
            System.out.println(size);
    }
}