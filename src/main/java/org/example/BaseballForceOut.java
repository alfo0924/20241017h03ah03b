package org.example;

import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;

public class BaseballForceOut {
    public static List<String> getForceOutBases(String baseState) {
        List<String> forceOutBases = new ArrayList<>();
        Set<String> occupiedBases = new HashSet<>(Arrays.asList(baseState.split(", ")));

        // 一壘永遠是可封殺的
        forceOutBases.add("1B");

        // 如果一壘有人,二壘也是可封殺的
        if (occupiedBases.contains("1B")) {
            forceOutBases.add("2B");

            // 如果一二壘都有人,三壘也是可封殺的
            if (occupiedBases.contains("2B")) {
                forceOutBases.add("3B");
            }
        }

        return forceOutBases;
    }
}


