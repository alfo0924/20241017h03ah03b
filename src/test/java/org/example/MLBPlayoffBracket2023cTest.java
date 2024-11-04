package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class MLBPlayoffBracket2023cTest {

    private MLBPlayoffBracket2023c bracket;
    private Map<String, String> teamNames;
    private Map<String, Integer> seeds;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
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
        // 設置2023年美國聯盟隊伍資料
        teamNames.put("BAL", "Baltimore Orioles");
        teamNames.put("HOU", "Houston Astros");
        teamNames.put("MIN", "Minnesota Twins");
        teamNames.put("TB", "Tampa Bay Rays");
        teamNames.put("TEX", "Texas Rangers");
        teamNames.put("TOR", "Toronto Blue Jays");
        teamNames.put("ATL", "Atlanta Braves");
        teamNames.put("LAD", "Los Angeles Dodgers");
        teamNames.put("MIL", "Milwaukee Brewers");
        teamNames.put("PHI", "Philadelphia Phillies");
        teamNames.put("ARI", "Arizona Diamondbacks");
        teamNames.put("MIA", "Miami Marlins");

        // 設置2023年種子序號
        seeds.put("BAL", 1);
        seeds.put("HOU", 2);
        seeds.put("MIN", 3);
        seeds.put("TB", 4);
        seeds.put("TEX", 5);
        seeds.put("TOR", 6);
        seeds.put("ATL", 1);
        seeds.put("LAD", 2);
        seeds.put("MIL", 3);
        seeds.put("PHI", 4);
        seeds.put("ARI", 5);
        seeds.put("MIA", 6);

        // 測試2023年的處理
        MLBPlayoffBracket2023c.processYear(2023, teamNames, seeds);

        // 獲取輸出結果
        String output = outContent.toString();

        // 驗證標題和聯盟名稱
        assertTrue(output.contains("2023 MLB Playoff Bracket:"));
        assertTrue(output.contains("(AMERICAN LEAGUE)"));
        assertTrue(output.contains("(NATIONAL LEAGUE)"));

        // 驗證美國聯盟隊伍和種子序號
        assertTrue(output.contains("BAL 1 Baltimore Orioles"));
        assertTrue(output.contains("HOU 2 Houston Astros"));
        assertTrue(output.contains("MIN 3 Minnesota Twins"));
        assertTrue(output.contains("TB 4 Tampa Bay Rays"));
        assertTrue(output.contains("TEX 5 Texas Rangers"));
        assertTrue(output.contains("TOR 6 Toronto Blue Jays"));

        // 驗證國家聯盟隊伍和種子序號
        assertTrue(output.contains("ATL 1 Atlanta Braves"));
        assertTrue(output.contains("LAD 2 Los Angeles Dodgers"));
        assertTrue(output.contains("MIL 3 Milwaukee Brewers"));
        assertTrue(output.contains("PHI 4 Philadelphia Phillies"));
        assertTrue(output.contains("ARI 5 Arizona Diamondbacks"));
        assertTrue(output.contains("MIA 6 Miami Marlins"));

        // 驗證比賽結果
        assertTrue(output.contains("TEX ----- TEX"));
        assertTrue(output.contains("MIN ----- TEX"));
        assertTrue(output.contains("PHI ----- ARI"));
        assertTrue(output.contains("ARI ----- ARI"));

        // 驗證最終冠軍
        assertTrue(output.contains("---- TEX " + teamNames.get("TEX")));
    }
    @Test
    void testProcessYearWithInvalidYear() {
        // 測試無效年份
        MLBPlayoffBracket2023c.processYear(2024, teamNames, seeds);
        String output = outContent.toString();

        // 驗證輸出包含2024年的標題但沒有其他內容
        assertTrue(output.contains("2024 MLB Playoff Bracket:"));
        assertFalse(output.contains("AMERICAN LEAGUE"));
        assertFalse(output.contains("NATIONAL LEAGUE"));
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