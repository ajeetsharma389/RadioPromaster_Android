package com.example.swiftradiopromaster_android;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class MySingleton {
    private static MySingleton uniqInstance;
    public static List<getJsonData> finalArray;
    private MySingleton() {
    }

    public static MySingleton getInstance() {
        if (uniqInstance == null) {
            synchronized(MySingleton.class) {
                // check again to avoid multi-thread access
                if (uniqInstance == null) {
                    uniqInstance = new MySingleton();
                    finalArray =  new ArrayList<getJsonData>();
                }
            }
        }
        return uniqInstance;
    }
}

