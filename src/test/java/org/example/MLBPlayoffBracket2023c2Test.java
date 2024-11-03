package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MLBPlayoffBracket2023c2Test {

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
    void testReadTeamsWithInvalidFile() {
        try {
            Method readTeamsMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod("readTeams", String.class);
            readTeamsMethod.setAccessible(true);
            // 測試不存在的檔案
            readTeamsMethod.invoke(null, "nonexistent.csv");
            fail("應該要拋出檔案不存在的異常");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof IOException);
        }
    }

    @Test
    void testReadSeedsWithCorruptedData() {
        try {
            Method readSeedsMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod("readSeeds", String.class);
            readSeedsMethod.setAccessible(true);
            // 測試損壞的檔案
            readSeedsMethod.invoke(null, "corrupted_playoffs.csv");
            fail("應該要拋出資料格式錯誤的異常");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof IOException);
        }
    }

    @Test
    void testValidateTeamsAndWinnersWithErrors() {
        try {
            Method validateMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "validateTeamsAndWinners",
                    String[].class,
                    String[].class,
                    Map.class,
                    Map.class
            );
            validateMethod.setAccessible(true);

            // 測試空陣列
            String[] emptyTeams = {};
            String[] winners = {"NYY", "BOS"};
            assertThrows(IllegalArgumentException.class, () -> validateMethod.invoke(
                    null, emptyTeams, winners, teamNames, seeds
            ));

            // 測試null值
            assertThrows(NullPointerException.class, () -> validateMethod.invoke(
                    null, null, winners, teamNames, seeds
            ));

            // 測試winners陣列比teams陣列長
            String[] teams = {"NYY", "BOS"};
            String[] longWinners = {"NYY", "BOS", "TB"};
            assertThrows(IllegalArgumentException.class, () -> validateMethod.invoke(
                    null, teams, longWinners, teamNames, seeds
            ));
        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    void testProcessYearWithInvalidYear() {
        assertThrows(IllegalArgumentException.class, () -> {
            Method processYearMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "processYear",
                    int.class,
                    Map.class,
                    Map.class
            );
            processYearMethod.setAccessible(true);
            // 測試無效的年份
            processYearMethod.invoke(null, 1900, teamNames, seeds);
        });
    }

    @Test
    void testPrintBracketWithInvalidData() {
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

            // 測試winners陣列長度不正確
            String[] teams = {"NYY", "BOS", "TB", "TOR", "HOU"};
            String[] invalidWinners = {"NYY", "BOS"}; // 長度不足

            assertThrows(IndexOutOfBoundsException.class, () -> printBracketMethod.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    teams,
                    invalidWinners,
                    teamNames,
                    seeds
            ));

            // 測試空聯盟名稱
            assertThrows(IllegalArgumentException.class, () -> printBracketMethod.invoke(
                    null,
                    "",
                    teams,
                    invalidWinners,
                    teamNames,
                    seeds
            ));
        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    void testPrintBracket2020WithInvalidTeamCount() {
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

            // 測試隊伍數量不正確（2020年應該要有8支隊伍）
            String[] teams = {"TB", "NYY", "HOU"};  // 只有3支隊伍
            String[] winners = {"TB", "NYY", "HOU"};

            assertThrows(IllegalArgumentException.class, () -> printBracket2020Method.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    teams,
                    winners,
                    teamNames,
                    seeds
            ));
        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    void testWithNullMaps() {
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

            // 測試空的Map物件
            assertThrows(NullPointerException.class, () -> validateMethod.invoke(
                    null,
                    teams,
                    winners,
                    null,  // null teamNames
                    seeds
            ));
        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }
}