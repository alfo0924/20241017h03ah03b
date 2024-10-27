package org.example;



import java.util.Map;
import java.util.HashMap;

public class MLBPlayoffBracket2023cTest {
    private Map<String, String> teamNames;
    private Map<String, Integer> seeds2023;

    @BeforeEach
    void setUp() {
        // 初始化測試數據
        teamNames = new HashMap<>();
        teamNames.put("TEX", "德克薩斯遊騎兵");
        teamNames.put("HOU", "休斯頓太空人");
        teamNames.put("BAL", "巴爾的摩金鶯");
        teamNames.put("MIN", "明尼蘇達雙城");

        seeds2023 = new HashMap<>();
        seeds2023.put("BAL", 1);
        seeds2023.put("HOU", 2);
        seeds2023.put("MIN", 3);
        seeds2023.put("TEX", 5);
    }

    @Test
    void testValidateTeamsAndWinners_ValidInput() {
        String[] teams = {"BAL", "HOU", "MIN", "TEX"};
        String[] winners = {"TEX", "MIN", "TEX"};

        // 測試有效輸入不應拋出異常
        assertDoesNotThrow(() ->
                MLBPlayoffBracket2023c.validateTeamsAndWinners(teams, winners, teamNames, seeds2023)
        );
    }

    @Test
    void testValidateTeamsAndWinners_DuplicateTeams() {
        String[] teams = {"BAL", "BAL", "MIN", "TEX"};
        String[] winners = {"TEX", "MIN", "TEX"};

        // 測試重複隊伍應拋出異常
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                MLBPlayoffBracket2023c.validateTeamsAndWinners(teams, winners, teamNames, seeds2023)
        );
        assertEquals("Duplicate teams in input", exception.getMessage());
    }

    @Test
    void testValidateTeamsAndWinners_UnknownTeam() {
        String[] teams = {"BAL", "XXX", "MIN", "TEX"};
        String[] winners = {"TEX", "MIN", "TEX"};

        // 測試未知隊伍應拋出異常
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                MLBPlayoffBracket2023c.validateTeamsAndWinners(teams, winners, teamNames, seeds2023)
        );
        assertEquals("Unknown team: XXX", exception.getMessage());
    }

    @Test
    void testValidateTeamsAndWinners_UnknownWinner() {
        String[] teams = {"BAL", "HOU", "MIN", "TEX"};
        String[] winners = {"TEX", "XXX", "TEX"};

        // 測試未知獲勝者應拋出異常
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                MLBPlayoffBracket2023c.validateTeamsAndWinners(teams, winners, teamNames, seeds2023)
        );
        assertEquals("Unknown winner: XXX", exception.getMessage());
    }

    @Test
    void testInitializeTeamNames() {
        Map<String, String> teams = MLBPlayoffBracket2023c.initializeTeamNames();

        // 測試隊伍名稱初始化
        assertNotNull(teams);
        assertFalse(teams.isEmpty());
        assertEquals("德克薩斯遊騎兵", teams.get("TEX"));
        assertEquals("休斯頓太空人", teams.get("HOU"));
    }

    @Test
    void testInitializeSeeds2023() {
        Map<String, Integer> seeds = MLBPlayoffBracket2023c.initializeSeeds2023();

        // 測試種子排名初始化
        assertNotNull(seeds);
        assertFalse(seeds.isEmpty());
        assertEquals(1, seeds.get("BAL"));
        assertEquals(2, seeds.get("HOU"));
        assertEquals(3, seeds.get("MIN"));
    }

    @Test
    void testPrintBracket() {
        String[] teams = {"BAL", "HOU", "MIN", "TEX"};
        String[] winners = {"TEX", "MIN", "TEX"};

        // 測試打印功能不應拋出異常
        assertDoesNotThrow(() ->
                MLBPlayoffBracket2023c.printBracket("AMERICAN LEAGUE", teams, winners, teamNames, seeds2023)
        );
    }

    @Test
    void testPrintBracket2020() {
        String[] teams = {"TB", "OAK", "MIN", "CLE", "NYY", "HOU", "CHW", "TOR"};
        String[] winners = {"TB", "HOU", "NYY", "TB", "TB", "HOU", "TB", "TB"};
        Map<String, Integer> seeds2020 = new HashMap<>();
        // 初始化2020年的種子排名...

        // 測試2020特殊賽季打印功能不應拋出異常
        assertDoesNotThrow(() ->
                MLBPlayoffBracket2023c.printBracket2020("AMERICAN LEAGUE", teams, winners, teamNames, seeds2020)
        );
    }

    @Test
    void testTeamNamesConsistency() {
        Map<String, String> teams = MLBPlayoffBracket2023c.initializeTeamNames();

        // 測試team names的一致性
        assertTrue(teams.containsKey("TEX"));
        assertTrue(teams.containsKey("HOU"));
        assertTrue(teams.containsKey("MIN"));
        assertTrue(teams.containsKey("BAL"));

        // 測試中文名稱的正確性
        assertEquals("德克薩斯遊騎兵", teams.get("TEX"));
        assertEquals("休斯頓太空人", teams.get("HOU"));
        assertEquals("明尼蘇達雙城", teams.get("MIN"));
        assertEquals("巴爾的摩金鶯", teams.get("BAL"));
    }

    @Test
    void testSeedsConsistency() {
        Map<String, Integer> seeds = MLBPlayoffBracket2023c.initializeSeeds2023();

        // 測試種子排名的一致性
        assertEquals(1, seeds.get("BAL"));
        assertEquals(2, seeds.get("HOU"));
        assertEquals(3, seeds.get("MIN"));
        assertEquals(5, seeds.get("TEX"));
    }
}