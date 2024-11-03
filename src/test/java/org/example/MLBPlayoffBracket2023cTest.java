package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class MLBPlayoffBracket2023cTest {

    private MLBPlayoffBracket2023c bracket;
    private Map<String, String> teamNames;
    private Map<String, Integer> seeds;

    @BeforeEach
    void setUp() {
        bracket = new MLBPlayoffBracket2023c();
        teamNames = new HashMap<>();
        seeds = new HashMap<>();

        // 設置測試用的隊伍資料
        teamNames.put("NYY", "New York Yankees");
        teamNames.put("BOS", "Boston Red Sox");
        teamNames.put("TB", "Tampa Bay Rays");
        teamNames.put("TOR", "Toronto Blue Jays");
        teamNames.put("HOU", "Houston Astros");

        // 設置測試用的種子序號
        seeds.put("NYY", 1);
        seeds.put("BOS", 2);
        seeds.put("TB", 3);
        seeds.put("TOR", 4);
        seeds.put("HOU", 5);
    }

    @Test
    void testReadTeams() {
        try {
            Method readTeamsMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod("readTeams", String.class);
            readTeamsMethod.setAccessible(true);
            Map<String, String> result = (Map<String, String>) readTeamsMethod.invoke(null, "teams.csv");
            assertNotNull(result);
            assertFalse(result.isEmpty());
        } catch (Exception e) {
            fail("讀取隊伍資料失敗: " + e.getMessage());
        }
    }

    @Test
    void testReadSeeds() {
        try {
            Method readSeedsMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod("readSeeds", String.class);
            readSeedsMethod.setAccessible(true);
            Map<Integer, Map<String, Integer>> result = (Map<Integer, Map<String, Integer>>) readSeedsMethod.invoke(null, "playoffs.csv");
            assertNotNull(result);
            assertFalse(result.isEmpty());
        } catch (Exception e) {
            fail("讀取種子資料失敗: " + e.getMessage());
        }
    }

    @Test
    void testValidateTeamsAndWinners() {
        try {
            Method validateMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "validateTeamsAndWinners",
                    String[].class,
                    String[].class,
                    Map.class,
                    Map.class
            );
            validateMethod.setAccessible(true);

            String[] teams = {"NYY", "BOS", "TB"};
            String[] winners = {"NYY", "BOS"};

            // 測試正確的輸入
            assertDoesNotThrow(() -> validateMethod.invoke(
                    null,
                    teams,
                    winners,
                    teamNames,
                    seeds
            ));

            // 測試重複的隊伍
            String[] duplicateTeams = {"NYY", "NYY", "TB"};
            assertThrows(IllegalArgumentException.class, () -> validateMethod.invoke(
                    null,
                    duplicateTeams,
                    winners,
                    teamNames,
                    seeds
            ));

            // 測試未知的隊伍
            String[] unknownTeams = {"NYY", "XXX", "TB"};
            assertThrows(IllegalArgumentException.class, () -> validateMethod.invoke(
                    null,
                    unknownTeams,
                    winners,
                    teamNames,
                    seeds
            ));
        } catch (Exception e) {
            fail("驗證測試失敗: " + e.getMessage());
        }
    }

    @Test
    void testProcessYear() {
        // 測試2023年的處理
        assertDoesNotThrow(() -> {
            Method processYearMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "processYear",
                    int.class,
                    Map.class,
                    Map.class
            );
            processYearMethod.setAccessible(true);
            processYearMethod.invoke(null, 2023, teamNames, seeds);
        });
    }

    @Test
    void testPrintBracket() {
        try {
            Method printBracketMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "printBracket",
                    String.class,
                    String[].class,
                    String[].class,
                    Map.class,
                    Map.class
            );
            printBracketMethod.setAccessible(true);

            String[] teams = {"NYY", "BOS", "TB", "TOR", "HOU"};
            String[] winners = {"NYY", "BOS", "TB", "NYY", "BOS"};

            assertDoesNotThrow(() -> printBracketMethod.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    teams,
                    winners,
                    teamNames,
                    seeds
            ));
        } catch (Exception e) {
            fail("列印測試失敗: " + e.getMessage());
        }
    }

    @Test
    void testPrintBracket2020() {
        try {
            Method printBracket2020Method = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "printBracket2020",
                    String.class,
                    String[].class,
                    String[].class,
                    Map.class,
                    Map.class
            );
            printBracket2020Method.setAccessible(true);

            String[] teams = {"TB", "NYY", "HOU", "BOS", "TOR", "OAK", "CLE", "MIN"};
            String[] winners = {"TB", "NYY", "HOU", "TB", "NYY", "TB", "TB", "TB"};

            assertDoesNotThrow(() -> printBracket2020Method.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    teams,
                    winners,
                    teamNames,
                    seeds
            ));
        } catch (Exception e) {
            fail("2020年版本列印測試失敗: " + e.getMessage());
        }
    }
}