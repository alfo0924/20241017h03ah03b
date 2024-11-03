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

        // 故意設置不完整的測試資料
        teamNames.put("NYY", "New York Yankees");
        teamNames.put("BOS", "Boston Red Sox");

        // 故意設置錯誤的種子序號
        seeds.put("NYY", 0);  // 無效的種子序號
        seeds.put("BOS", -1); // 負數種子序號
    }

    @Test
    void testReadTeamsWithInvalidFile() {
        try {
            Method readTeamsMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod("readTeams", String.class);
            readTeamsMethod.setAccessible(true);
            // 測試不存在的檔案
            Map<String, String> result = (Map<String, String>) readTeamsMethod.invoke(null, "nonexistent.csv");
            fail("應該要拋出檔案不存在的異常");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof IOException);
        }
    }

    @Test
    void testReadSeedsWithInvalidData() {
        try {
            Method readSeedsMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod("readSeeds", String.class);
            readSeedsMethod.setAccessible(true);
            // 測試錯誤格式的檔案
            Map<Integer, Map<String, Integer>> result = (Map<Integer, Map<String, Integer>>) readSeedsMethod.invoke(null, "invalid_playoffs.csv");
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

            // 測試重複的隊伍
            String[] teams = {"NYY", "NYY", "BOS"};
            String[] winners = {"NYY", "BOS"};

            assertThrows(IllegalArgumentException.class, () -> validateMethod.invoke(
                    null,
                    teams,
                    winners,
                    teamNames,
                    seeds
            ));

            // 測試未知的隊伍
            String[] unknownTeams = {"XXX", "YYY", "ZZZ"};
            assertThrows(IllegalArgumentException.class, () -> validateMethod.invoke(
                    null,
                    unknownTeams,
                    winners,
                    teamNames,
                    seeds
            ));
        } catch (Exception e) {
            fail("測試過程中發生未預期的異常");
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
            processYearMethod.invoke(null, 1905, teamNames, seeds);
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

            // 測試陣列長度不匹配
            String[] teams = {"NYY", "BOS"};
            String[] winners = {"NYY", "BOS", "TB"}; // 多餘的優勝者

            assertThrows(IndexOutOfBoundsException.class, () -> printBracketMethod.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    teams,
                    winners,
                    teamNames,
                    seeds
            ));
        } catch (Exception e) {
            fail("測試過程中發生未預期的異常");
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

            // 測試錯誤的隊伍數量（2020年應該要有8支隊伍）
            String[] teams = {"NYY", "BOS", "TB"};
            String[] winners = {"NYY", "BOS", "TB"};

            assertThrows(ArrayIndexOutOfBoundsException.class, () -> printBracket2020Method.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    teams,
                    winners,
                    teamNames,
                    seeds
            ));
        } catch (Exception e) {
            fail("測試過程中發生未預期的異常");
        }
    }
}