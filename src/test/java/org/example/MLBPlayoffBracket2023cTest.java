package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
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

        // 設置更完整的測試用隊伍資料
        teamNames.put("NYY", "New York Yankees");
        teamNames.put("BOS", "Boston Red Sox");
        teamNames.put("TB", "Tampa Bay Rays");
        teamNames.put("TOR", "Toronto Blue Jays");
        teamNames.put("HOU", "Houston Astros");
        teamNames.put("TEX", "Texas Rangers");
        teamNames.put("MIN", "Minnesota Twins");
        teamNames.put("BAL", "Baltimore Orioles");

        // 設置對應的種子序號
        seeds.put("NYY", 1);
        seeds.put("BOS", 2);
        seeds.put("TB", 3);
        seeds.put("TOR", 4);
        seeds.put("HOU", 5);
        seeds.put("TEX", 6);
        seeds.put("MIN", 3);
        seeds.put("BAL", 1);
    }

    @Test
    void testReadTeams() {
        try {
            Method readTeamsMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod("readTeams", String.class);
            readTeamsMethod.setAccessible(true);
            Map<String, String> result = (Map<String, String>) readTeamsMethod.invoke(null, "teams.csv");
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertTrue(result.containsKey("NYY"));
            assertTrue(result.containsKey("BOS"));
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
            assertTrue(result.containsKey(2023));
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

            // 使用2023年實際的隊伍資料
            String[] teams = {"BAL", "HOU", "MIN"};
            String[] winners = {"TEX", "MIN"};

            // 測試正確的輸入
            assertDoesNotThrow(() -> validateMethod.invoke(
                    null,
                    teams,
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
        assertDoesNotThrow(() -> {
            Method processYearMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "processYear",
                    int.class,
                    Map.class,
                    Map.class
            );
            processYearMethod.setAccessible(true);
            processYearMethod.invoke(null, 23, teamNames, seeds);
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

            // 使用2023年實際的隊伍資料
            String[] teams = {"BAL", "HOU", "MIN", "TB", "TEX", "TOR"};
            String[] winners = {"TEX", "MIN", "TEX", "MIN", "TEX"};

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

            // 使用2020年實際的隊伍資料
            String[] teams = {"TB", "OAK", "MIN", "CLE", "NYY", "HOU", "CWS", "TOR"};
            String[] winners = {"NYY", "HOU", "TB", "TB", "HOU", "TB", "TB", "TB"};

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