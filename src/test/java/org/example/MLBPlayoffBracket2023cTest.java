package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MLBPlayoffBracket2023cTest {

    private MLBPlayoffBracket2023c bracket;
    private Map<String, String> teamNames;
    private Map<String, Integer> seeds;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        bracket = new MLBPlayoffBracket2023c();
        teamNames = new HashMap<>();
        seeds = new HashMap<>();
        System.setOut(new PrintStream(outContent));

        // 設置所有需要的隊伍資料
        teamNames.put("TB", "Tampa Bay Rays");
        teamNames.put("OAK", "Oakland Athletics");
        teamNames.put("MIN", "Minnesota Twins");
        teamNames.put("CLE", "Cleveland Guardians");
        teamNames.put("NYY", "New York Yankees");
        teamNames.put("HOU", "Houston Astros");
        teamNames.put("CWS", "Chicago White Sox");
        teamNames.put("TOR", "Toronto Blue Jays");
        teamNames.put("BAL", "Baltimore Orioles");
        teamNames.put("TEX", "Texas Rangers");
        teamNames.put("BOS", "Boston Red Sox");
        teamNames.put("LAD", "Los Angeles Dodgers");
        teamNames.put("ATL", "Atlanta Braves");
        teamNames.put("MIL", "Milwaukee Brewers");
        teamNames.put("PHI", "Philadelphia Phillies");
        teamNames.put("ARI", "Arizona Diamondbacks");
        teamNames.put("MIA", "Miami Marlins");

        // 設置所有需要的種子序號
        seeds.put("TB", 1);
        seeds.put("OAK", 2);
        seeds.put("MIN", 3);
        seeds.put("CLE", 4);
        seeds.put("NYY", 5);
        seeds.put("HOU", 6);
        seeds.put("CWS", 7);
        seeds.put("TOR", 8);
        seeds.put("BAL", 1);
        seeds.put("TEX", 5);
        seeds.put("BOS", 2);
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testValidateTeamsAndWinners() {
        try {
            // 設置2023年的測試資料
            teamNames.clear();
            teamNames.put("BAL", "Baltimore Orioles");
            teamNames.put("HOU", "Houston Astros");
            teamNames.put("MIN", "Minnesota Twins");
            teamNames.put("TB", "Tampa Bay Rays");
            teamNames.put("TEX", "Texas Rangers");
            teamNames.put("TOR", "Toronto Blue Jays");

            seeds.clear();
            seeds.put("BAL", 1);
            seeds.put("HOU", 2);
            seeds.put("MIN", 3);
            seeds.put("TB", 4);
            seeds.put("TEX", 5);
            seeds.put("TOR", 6);

            Method validateMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "validateTeamsAndWinners",
                    String[].class,
                    String[].class,
                    Map.class,
                    Map.class
            );
            validateMethod.setAccessible(true);

            // 測試案例1: 正確的輸入
            String[] validTeams = {"BAL", "HOU", "MIN"};
            String[] validWinners = {"TEX", "MIN"};
            assertDoesNotThrow(() ->
                    validateMethod.invoke(null, validTeams, validWinners, teamNames, seeds)
            );

            // 測試案例2: 重複的隊伍
            String[] duplicateTeams = {"BAL", "BAL", "MIN"};
            Exception exception = assertThrows(InvocationTargetException.class, () ->
                    validateMethod.invoke(null, duplicateTeams, validWinners, teamNames, seeds)
            );
            assertTrue(exception.getCause() instanceof IllegalArgumentException);
            assertEquals("Duplicate teams in input", exception.getCause().getMessage());

            // 測試案例3: 未知的隊伍
            String[] unknownTeams = {"BAL", "XXX", "MIN"};
            exception = assertThrows(InvocationTargetException.class, () ->
                    validateMethod.invoke(null, unknownTeams, validWinners, teamNames, seeds)
            );
            assertTrue(exception.getCause() instanceof IllegalArgumentException);
            assertTrue(exception.getCause().getMessage().startsWith("Unknown team:"));

            // 測試案例4: 未知的優勝者
            String[] unknownWinners = {"BAL", "ZZZ"};
            exception = assertThrows(InvocationTargetException.class, () ->
                    validateMethod.invoke(null, validTeams, unknownWinners, teamNames, seeds)
            );
            assertTrue(exception.getCause() instanceof IllegalArgumentException);
            assertTrue(exception.getCause().getMessage().startsWith("Unknown winner:"));

            // 測試案例5: 空陣列（有效的邊界情況）
            String[] emptyTeams = {};
            String[] emptyWinners = {};
            assertDoesNotThrow(() ->
                    validateMethod.invoke(null, emptyTeams, emptyWinners, teamNames, seeds)
            );

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }


    @Test
    void testProcessYear() {
        // 準備2023年的測試資料
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

        // 設置種子序號
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

        try {
            Method processYearMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "processYear",
                    int.class,
                    Map.class,
                    Map.class
            );
            processYearMethod.setAccessible(true);

            // 測試2023年的處理
            processYearMethod.invoke(null, 2023, teamNames, seeds);
            String output = outContent.toString();

            // 驗證基本輸出格式
            assertTrue(output.contains("2023 MLB Playoff Bracket:"));
            assertTrue(output.contains("(AMERICAN LEAGUE)"));
            assertTrue(output.contains("(NATIONAL LEAGUE)"));

            // 驗證特定隊伍資訊
            assertTrue(output.contains("BAL 1 Baltimore Orioles"));
            assertTrue(output.contains("TEX 5 Texas Rangers"));

            // 驗證優勝者資訊
            assertTrue(output.contains("TEX ----- TEX"));
            assertTrue(output.contains("---- TEX Texas Rangers"));

            outContent.reset();

            // 測試2024年（未來年份）的處理
            processYearMethod.invoke(null, 2024, teamNames, seeds);
            output = outContent.toString();
            assertTrue(output.contains("2024 MLB Playoff Bracket:"));

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    void testPrintBracket() {
        try {
            // 設置2023年美國聯盟的測試資料
            teamNames.clear();
            teamNames.put("BAL", "Baltimore Orioles");
            teamNames.put("HOU", "Houston Astros");
            teamNames.put("MIN", "Minnesota Twins");
            teamNames.put("TB", "Tampa Bay Rays");
            teamNames.put("TEX", "Texas Rangers");
            teamNames.put("TOR", "Toronto Blue Jays");

            seeds.clear();
            seeds.put("BAL", 1);
            seeds.put("HOU", 2);
            seeds.put("MIN", 3);
            seeds.put("TB", 4);
            seeds.put("TEX", 5);
            seeds.put("TOR", 6);

            Method printBracketMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "printBracket",
                    String.class,
                    String[].class,
                    String[].class,
                    Map.class,
                    Map.class
            );
            printBracketMethod.setAccessible(true);

            // 測試2023年美國聯盟的賽程
            String[] teams = {"BAL", "HOU", "MIN", "TB", "TEX", "TOR"};
            String[] winners = {"TEX", "MIN", "TEX", "MIN", "TEX"};

            printBracketMethod.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    teams,
                    winners,
                    teamNames,
                    seeds
            );

            String output = outContent.toString();

            // 驗證基本輸出格式
            assertTrue(output.contains("(AMERICAN LEAGUE)"));

            // 驗證隊伍資訊
            assertTrue(output.contains("BAL 1 Baltimore Orioles"));
            assertTrue(output.contains("HOU 2 Houston Astros"));
            assertTrue(output.contains("MIN 3 Minnesota Twins"));
            assertTrue(output.contains("TEX 5 Texas Rangers"));

            // 驗證優勝者資訊
            assertTrue(output.contains("TEX ----- TEX"));
            assertTrue(output.contains("MIN ----- TEX"));

            outContent.reset();

            // 測試空陣列的處理（邊界情況）
            String[] emptyTeams = {};
            String[] emptyWinners = {};
            printBracketMethod.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    emptyTeams,
                    emptyWinners,
                    teamNames,
                    seeds
            );

            output = outContent.toString();
            assertTrue(output.contains("(AMERICAN LEAGUE)"));

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
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

            String[] teams = {"TB", "OAK", "MIN", "CLE", "NYY", "HOU", "CWS", "TOR"};
            String[] winners = {"TB", "HOU", "MIN", "TB", "NYY", "TB", "TB", "TB"};

            printBracket2020Method.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    teams,
                    winners,
                    teamNames,
                    seeds
            );

            String output = outContent.toString();
            assertTrue(output.contains("(AMERICAN LEAGUE)"));
        } catch (Exception e) {
            fail("2020年版本列印測試失敗: " + e.getMessage());
        }
    }
}
