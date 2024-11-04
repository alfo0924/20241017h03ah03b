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

public class MLBPlayoffBracket2023c2Test {

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
            // 故意設置錯誤的測試資料
            teamNames.clear();  // 清空原有資料
            teamNames.put("TB", "Tampa Bay Rays");
            teamNames.put("HOU", "Houston Astros");

            seeds.clear();  // 清空原有資料
            seeds.put("TB", -1);  // 無效的種子序號
            seeds.put("HOU", 0);  // 無效的種子序號

            Method validateMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "validateTeamsAndWinners",
                    String[].class,
                    String[].class,
                    Map.class,
                    Map.class
            );
            validateMethod.setAccessible(true);

            // 測試案例1: 使用不存在的隊伍
            String[] invalidTeams = {"XXX", "YYY", "ZZZ"};
            String[] invalidWinners = {"AAA", "BBB"};
            assertThrows(IllegalArgumentException.class, () ->
                    validateMethod.invoke(null, invalidTeams, invalidWinners, teamNames, seeds)
            );

            // 測試案例2: 測試重複的隊伍
            String[] duplicateTeams = {"TB", "TB", "HOU"};
            String[] winners = {"TB", "HOU"};
            Exception exception = assertThrows(InvocationTargetException.class, () ->
                    validateMethod.invoke(null, duplicateTeams, winners, teamNames, seeds)
            );
            assertTrue(exception.getCause() instanceof IllegalArgumentException);
            assertEquals("Duplicate teams in input", exception.getCause().getMessage());

            // 測試案例3: 測試未知的隊伍
            String[] unknownTeams = {"TB", "XXX", "HOU"};
            exception = assertThrows(InvocationTargetException.class, () ->
                    validateMethod.invoke(null, unknownTeams, winners, teamNames, seeds)
            );
            assertTrue(exception.getCause() instanceof IllegalArgumentException);
            assertTrue(exception.getCause().getMessage().startsWith("Unknown team:"));

            // 測試案例4: 測試不在種子序號中的隊伍
            seeds.remove("HOU");  // 移除HOU的種子序號
            String[] validTeams = {"TB", "HOU"};
            exception = assertThrows(InvocationTargetException.class, () ->
                    validateMethod.invoke(null, validTeams, winners, teamNames, seeds)
            );
            assertTrue(exception.getCause() instanceof IllegalArgumentException);
            assertTrue(exception.getCause().getMessage().startsWith("Unknown team:"));

            // 測試案例5: 測試null值
            assertThrows(NullPointerException.class, () ->
                    validateMethod.invoke(null, null, winners, teamNames, seeds)
            );

            // 測試案例6: 測試空陣列
            String[] emptyTeams = {};
            String[] emptyWinners = {};
            validateMethod.invoke(null, emptyTeams, emptyWinners, teamNames, seeds);

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    void testProcessYear() {
        // 故意設置錯誤或不完整的測試資料
        teamNames.put("BAL", "Baltimore Orioles");
        teamNames.put("HOU", "Houston Astros");
        // 缺少必要的隊伍資料

        // 設置錯誤的種子序號
        seeds.put("BAL", 0);  // 無效的種子序號
        seeds.put("HOU", -1); // 負數種子序號

        try {
            Method processYearMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "processYear",
                    int.class,
                    Map.class,
                    Map.class
            );
            processYearMethod.setAccessible(true);

            // 測試2023年的處理，但資料不完整
            processYearMethod.invoke(null, 2023, teamNames, seeds);
            String output = outContent.toString();

            // 驗證錯誤的輸出格式
            assertTrue(output.contains("2023 MLB Playoff Bracket:")); // 這會失敗
            assertTrue(output.contains("WRONG LEAGUE")); // 這會失敗
            assertTrue(output.contains("INVALID TEAM")); // 這會失敗

            // 驗證不存在的隊伍資訊
            assertTrue(output.contains("XXX 1 Unknown Team")); // 這會失敗
            assertTrue(output.contains("YYY 5 Missing Team")); // 這會失敗

            // 驗證錯誤的優勝者資訊
            assertTrue(output.contains("ZZZ ----- ZZZ")); // 這會失敗
            assertTrue(output.contains("---- AAA Unknown Rangers")); // 這會失敗

            outContent.reset();

            // 測試無效年份
            processYearMethod.invoke(null, -1, teamNames, seeds); // 這會拋出異常
            output = outContent.toString();
            assertTrue(output.contains("Invalid Year")); // 這會失敗

        } catch (Exception e) {
            fail("預期會發生異常: " + e.getMessage());
        }
    }


    @Test
    void testPrintBracket() {
        try {
            // 故意設置錯誤的測試資料
            teamNames.clear();  // 清空原有資料
            teamNames.put("BAL", "Baltimore Orioles");
            // 缺少其他隊伍資料

            seeds.clear();  // 清空原有資料
            seeds.put("BAL", -1);  // 無效的種子序號

            Method printBracketMethod = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "printBracket",
                    String.class,
                    String[].class,
                    String[].class,
                    Map.class,
                    Map.class
            );
            printBracketMethod.setAccessible(true);

            // 測試案例1: 使用不完整的隊伍陣列
            String[] invalidTeams = {"BAL", "XXX", "YYY"};
            String[] invalidWinners = {"BAL", "XXX"};
            assertThrows(Exception.class, () ->
                    printBracketMethod.invoke(
                            null,
                            "AMERICAN LEAGUE",
                            invalidTeams,
                            invalidWinners,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例2: winners陣列長度不匹配
            String[] teams = {"BAL", "HOU", "MIN", "TB", "TEX"};
            String[] shortWinners = {"TEX", "MIN"};  // 長度不足
            assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                    printBracketMethod.invoke(
                            null,
                            "AMERICAN LEAGUE",
                            teams,
                            shortWinners,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例3: 空的聯盟名稱
            assertThrows(IllegalArgumentException.class, () ->
                    printBracketMethod.invoke(
                            null,
                            "",
                            teams,
                            shortWinners,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例4: null參數
            assertThrows(NullPointerException.class, () ->
                    printBracketMethod.invoke(
                            null,
                            null,
                            teams,
                            shortWinners,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例5: 使用不存在於seeds中的隊伍
            String[] teamsWithoutSeeds = {"BAL", "XXX", "MIN"};
            String[] winnersForInvalidTeams = {"BAL", "MIN"};
            assertThrows(NullPointerException.class, () ->
                    printBracketMethod.invoke(
                            null,
                            "AMERICAN LEAGUE",
                            teamsWithoutSeeds,
                            winnersForInvalidTeams,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例6: 使用空陣列
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
            String output = outContent.toString();
            assertTrue(output.contains("(AMERICAN LEAGUE)"));
            assertTrue(output.isEmpty());  // 這應該會失敗，因為至少會輸出聯盟名稱

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

    @Test
    void testPrintBracket2020() {
        try {
            // 故意設置錯誤的測試資料
            teamNames.clear();  // 清空原有資料
            teamNames.put("TB", "Tampa Bay Rays");
            teamNames.put("NYY", "New York Yankees");
            // 缺少其他必要的隊伍資料

            seeds.clear();  // 清空原有資料
            seeds.put("TB", -1);  // 無效的種子序號
            seeds.put("NYY", 0);  // 無效的種子序號

            Method printBracket2020Method = MLBPlayoffBracket2023c.class.getDeclaredMethod(
                    "printBracket2020",
                    String.class,
                    String[].class,
                    String[].class,
                    Map.class,
                    Map.class
            );
            printBracket2020Method.setAccessible(true);

            // 測試案例1: 使用不完整的隊伍陣列（2020年需要8支隊伍）
            String[] invalidTeams = {"TB", "NYY", "MIN"};  // 隊伍數量不足
            String[] invalidWinners = {"TB", "NYY"};
            assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                    printBracket2020Method.invoke(
                            null,
                            "AMERICAN LEAGUE",
                            invalidTeams,
                            invalidWinners,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例2: winners陣列長度不匹配
            String[] teams = {"TB", "OAK", "MIN", "CLE", "NYY", "HOU", "CWS", "TOR"};
            String[] shortWinners = {"TB", "NYY", "MIN"};  // winners長度不足
            assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                    printBracket2020Method.invoke(
                            null,
                            "AMERICAN LEAGUE",
                            teams,
                            shortWinners,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例3: 使用未知的隊伍
            String[] unknownTeams = {"TB", "XXX", "YYY", "ZZZ", "NYY", "AAA", "BBB", "CCC"};
            String[] unknownWinners = {"TB", "XXX", "YYY", "ZZZ", "NYY", "AAA", "BBB", "CCC"};
            assertThrows(NullPointerException.class, () ->
                    printBracket2020Method.invoke(
                            null,
                            "AMERICAN LEAGUE",
                            unknownTeams,
                            unknownWinners,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例4: 空的聯盟名稱
            assertThrows(IllegalArgumentException.class, () ->
                    printBracket2020Method.invoke(
                            null,
                            "",
                            teams,
                            shortWinners,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例5: 使用null參數
            assertThrows(NullPointerException.class, () ->
                    printBracket2020Method.invoke(
                            null,
                            null,
                            teams,
                            shortWinners,
                            teamNames,
                            seeds
                    )
            );

            // 測試案例6: 錯誤的優勝者順序
            String[] invalidWinnerOrder = {"TB", "NYY", "MIN", "CLE", "HOU", "CWS", "TOR", "TB"};
            printBracket2020Method.invoke(
                    null,
                    "AMERICAN LEAGUE",
                    teams,
                    invalidWinnerOrder,
                    teamNames,
                    seeds
            );
            String output = outContent.toString();
            assertTrue(output.contains("TB ----- TB")); // 這應該會失敗，因為優勝者順序錯誤

        } catch (Exception e) {
            fail("測試過程中發生未預期的異常: " + e.getMessage());
        }
    }

}
