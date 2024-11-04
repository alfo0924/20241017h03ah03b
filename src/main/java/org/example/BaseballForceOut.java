package org.example;

import java.util.*;

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

    public static void main(String[] args) {
        // 測試案例1: 空壘
        System.out.println("空壘情況:");
        System.out.println(getForceOutBases("x"));  // 預期輸出: [1B]

        // 測試案例2: 一壘有人
        System.out.println("\n一壘有人:");
        System.out.println(getForceOutBases("1B"));  // 預期輸出: [1B, 2B]

        // 測試案例3: 一、二壘有人
        System.out.println("\n一、二壘有人:");
        System.out.println(getForceOutBases("1B, 2B"));  // 預期輸出: [1B, 2B, 3B]

        // 測試案例4: 一、三壘有人
        System.out.println("\n一、三壘有人:");
        System.out.println(getForceOutBases("1B, 3B"));  // 預期輸出: [1B, 2B]

        // 測試案例5: 三壘有人
        System.out.println("\n三壘有人:");
        System.out.println(getForceOutBases("3B"));  // 預期輸出: [1B]

        // 測試案例6: 滿壘
        System.out.println("\n滿壘:");
        System.out.println(getForceOutBases("1B, 2B, 3B"));  // 預期輸出: [1B, 2B, 3B]
    }
}