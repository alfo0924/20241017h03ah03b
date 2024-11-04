package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.MLBPlayoffBracket2023c1.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class MLBPlayoffBracket2023c1Test {
    private MLBPlayoffBracket2023c bracket;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        bracket = new MLBPlayoffBracket2023c();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testInitializeTeamNames() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, String> teamNames = (Map<String, String>) MLBPlayoffBracket2023c.class
                .getDeclaredMethod("initializeTeamNames")
                .invoke(null);

        assertNotNull(teamNames);
        assertEquals("德克薩斯遊騎兵", teamNames.get("TEX"));
        assertEquals("休斯頓太空人", teamNames.get("HOU"));
        assertEquals("明尼蘇達雙城", teamNames.get("MIN"));
    }

    @Test
    public void testInitializeSeeds2023() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, Integer> seeds = (Map<String, Integer>) MLBPlayoffBracket2023c.class
                .getDeclaredMethod("initializeSeeds2023")
                .invoke(null);

        assertNotNull(seeds);
        assertEquals(1, seeds.get("BAL"));
        assertEquals(2, seeds.get("HOU"));
        assertEquals(3, seeds.get("MIN"));
    }

    @Test
    public void testValidateTeamsAndWinners_ValidInput() {
        String[] teams = {"TEX", "HOU", "MIN"};
        String[] winners = {"TEX", "HOU", "TEX"};
        Map<String, String> teamNames = initializeTeamNames();
        Map<String, Integer> seeds = initializeSeeds2023();

        assertDoesNotThrow(() -> {
            MLBPlayoffBracket2023c.class
                    .getDeclaredMethod("validateTeamsAndWinners",
                            String[].class, String[].class,
                            Map.class, Map.class)
                    .invoke(null, teams, winners, teamNames, seeds);
        });
    }

    @Test
    public void testValidateTeamsAndWinners_DuplicateTeams() {
        String[] teams = {"TEX", "TEX", "MIN"};
        String[] winners = {"TEX", "HOU", "TEX"};
        Map<String, String> teamNames = initializeTeamNames();
        Map<String, Integer> seeds = initializeSeeds2023();

        assertThrows(IllegalArgumentException.class, () -> {
            MLBPlayoffBracket2023c.class
                    .getDeclaredMethod("validateTeamsAndWinners",
                            String[].class, String[].class,
                            Map.class, Map.class)
                    .invoke(null, teams, winners, teamNames, seeds);
        });
    }

    @Test
    public void testValidateTeamsAndWinners_UnknownTeam() {
        String[] teams = {"TEX", "HOU", "INVALID"};
        String[] winners = {"TEX", "HOU", "TEX"};
        Map<String, String> teamNames = initializeTeamNames();
        Map<String, Integer> seeds = initializeSeeds2023();

        assertThrows(IllegalArgumentException.class, () -> {
            MLBPlayoffBracket2023c.class
                    .getDeclaredMethod("validateTeamsAndWinners",
                            String[].class, String[].class,
                            Map.class, Map.class)
                    .invoke(null, teams, winners, teamNames, seeds);
        });
    }

    @Test
    public void testPrintBracket() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String league = "AMERICAN LEAGUE";
        String[] teams = {"TEX", "HOU", "MIN", "TB", "BAL"};
        String[] winners = {"TEX", "MIN", "TEX", "HOU", "TEX"};
        Map<String, String> teamNames = initializeTeamNames();
        Map<String, Integer> seeds = initializeSeeds2023();

        MLBPlayoffBracket2023c.class
                .getDeclaredMethod("printBracket",
                        String.class, String[].class, String[].class,
                        Map.class, Map.class)
                .invoke(null, league, teams, winners, teamNames, seeds);

        String output = outContent.toString();
        assertTrue(output.contains("AMERICAN LEAGUE"));
        assertTrue(output.contains("TEX"));
        assertTrue(output.contains("HOU"));
    }

    @Test
    public void testPrintBracket2020() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String league = "AMERICAN LEAGUE";
        String[] teams = {"TB", "OAK", "MIN", "CLE", "NYY", "HOU", "CHW", "TOR"};
        String[] winners = {"TB", "HOU", "NYY", "TB", "TB", "HOU", "TB", "TB"};
        Map<String, String> teamNames = initializeTeamNames();
        Map<String, Integer> seeds = initializeSeeds2020();

        MLBPlayoffBracket2023c.class
                .getDeclaredMethod("printBracket2020",
                        String.class, String[].class, String[].class,
                        Map.class, Map.class)
                .invoke(null, league, teams, winners, teamNames, seeds);

        String output = outContent.toString();
        assertTrue(output.contains("AMERICAN LEAGUE"));
        assertTrue(output.contains("TB"));
        assertTrue(output.contains("HOU"));
    }

    @Test
    public void testMain() {
        assertDoesNotThrow(() -> {
            MLBPlayoffBracket2023c.main(new String[]{});
        });
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}