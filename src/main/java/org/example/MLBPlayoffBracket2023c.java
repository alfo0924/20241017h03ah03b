package org.example;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MLBPlayoffBracket2023c {
    private static final Logger LOGGER = Logger.getLogger(MLBPlayoffBracket2023c.class.getName());

    // 讀取隊伍資料
    private static Map<String, String> readTeams(String filePath) throws IOException {
        Map<String, String> teamNames = new HashMap<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        MLBPlayoffBracket2023c.class.getClassLoader().getResourceAsStream(filePath),
                        StandardCharsets.UTF_8))) {
            String line;
            br.readLine(); // 跳過標題行
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                teamNames.put(data[0], data[1]);
            }
        }
        return teamNames;
    }

    // 讀取季後賽數據
    private static Map<Integer, Map<String, Integer>> readSeeds(String filePath) throws IOException {
        Map<Integer, Map<String, Integer>> yearSeeds = new HashMap<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        MLBPlayoffBracket2023c.class.getClassLoader().getResourceAsStream(filePath),
                        StandardCharsets.UTF_8))) {
            String line;
            br.readLine(); // 跳過標題行
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int year = Integer.parseInt(data[0]);
                String team = data[2];
                int seed = Integer.parseInt(data[3]);

                yearSeeds.computeIfAbsent(year, k -> new HashMap<>())
                        .put(team, seed);
            }
        }
        return yearSeeds;
    }

    private static void validateTeamsAndWinners(String[] teams, String[] winners,
                                                Map<String, String> teamNames,
                                                Map<String, Integer> seeds) {
        Set<String> uniqueTeams = new HashSet<>(Arrays.asList(teams));
        if (uniqueTeams.size() != teams.length) {
            throw new IllegalArgumentException("Duplicate teams in input");
        }

        for (String team : teams) {
            if (!teamNames.containsKey(team) || !seeds.containsKey(team)) {
                throw new IllegalArgumentException("Unknown team: " + team);
            }
        }

        for (String winner : winners) {
            if (!teamNames.containsKey(winner)) {
                throw new IllegalArgumentException("Unknown winner: " + winner);
            }
        }
    }

    private static void printBracket(String league, String[] teams, String[] winners,
                                     Map<String, String> teamNames, Map<String, Integer> seeds) {
        System.out.println("(" + league + ")");
        for (int i = 0; i < teams.length; i++) {
            String team = teams[i];
            System.out.printf("%-3s %d %s -----\n", team, seeds.get(team), teamNames.get(team));
            if (i == 1) {
                System.out.printf("%-3s ----- %-3s -----\n", winners[0], winners[2]);
            } else if (i == 2) {
                System.out.printf(" %-3s ----- %-3s\n", team, winners[2]);
            } else if (i == 4) {
                System.out.printf("%-3s ----- %-3s -----\n", winners[1], winners[3]);
                System.out.printf(" %-3s ----- %-3s ----- %-3s\n", teams[0], winners[3], winners[4]);
            }
        }
    }

    private static void printBracket2020(String league, String[] teams, String[] winners,
                                         Map<String, String> teamNames, Map<String, Integer> seeds) {
        System.out.println("(" + league + ")");
        for (int i = 0; i < teams.length; i++) {
            String team = teams[i];
            System.out.printf("%-3s %d %s -----\n", team, seeds.get(team), teamNames.get(team));
            if (i == 1) {
                System.out.printf("%-3s ----- %-3s -----\n", winners[0], winners[3]);
            } else if (i == 2) {
                System.out.printf(" %-3s ----- %-3s\n", winners[1], winners[3]);
            } else if (i == 3) {
                System.out.printf("%-3s ----- %-3s -----\n", winners[2], winners[5]);
            } else if (i == 5) {
                System.out.printf(" %-3s ----- %-3s ----- %-3s\n", winners[0], winners[5], winners[7]);
            }
        }
    }

    public static void main(String[] args) {
        try {
            // 讀取隊伍和種子數據
            Map<String, String> teamNames = readTeams("teams.csv");
            Map<Integer, Map<String, Integer>> yearSeeds = readSeeds("playoffs.csv");

            // 處理每一年的數據
            processYear(2015, teamNames, yearSeeds.get(2015));
            processYear(2016, teamNames, yearSeeds.get(2016));
            processYear(2017, teamNames, yearSeeds.get(2017));
            processYear(2018, teamNames, yearSeeds.get(2018));
            processYear(2019, teamNames, yearSeeds.get(2019));
            processYear(2020, teamNames, yearSeeds.get(2020));
            processYear(2021, teamNames, yearSeeds.get(2021));
            processYear(2022, teamNames, yearSeeds.get(2022));
            processYear(2023, teamNames, yearSeeds.get(2023));
            processYear(2024, teamNames, yearSeeds.get(2024));

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading CSV files", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error occurred", e);
        }
    }
    static void processYear(int year, Map<String, String> teamNames, Map<String, Integer> seeds) {
        System.out.println("\n\n" + year + " MLB Playoff Bracket:\n");

        switch (year) {
            case 2015:
                String[] alTeams2015 = {"KC", "TOR", "TEX", "HOU", "NYY"};
                String[] alWinners2015 = {"HOU", "TOR", "KC", "TOR", "KC"};
                String[] nlTeams2015 = {"STL", "LAD", "NYM", "CHC", "PIT"};
                String[] nlWinners2015 = {"CHC", "NYM", "CHC", "NYM", "NYM"};
                printBracket("AMERICAN LEAGUE", alTeams2015, alWinners2015, teamNames, seeds);
                System.out.println(" ---- KC " + teamNames.get("KC"));
                printBracket("NATIONAL LEAGUE", nlTeams2015, nlWinners2015, teamNames, seeds);
                break;

            case 2016:
                String[] alTeams2016 = {"TEX", "CLE", "BOS", "TOR", "BAL"};
                String[] alWinners2016 = {"TOR", "CLE", "TOR", "CLE", "CLE"};
                String[] nlTeams2016 = {"CHC", "WSH", "LAD", "NYM", "SF"};
                String[] nlWinners2016 = {"SF", "LAD", "CHC", "LAD", "CHC"};
                printBracket("AMERICAN LEAGUE", alTeams2016, alWinners2016, teamNames, seeds);
                System.out.println(" ---- CHC " + teamNames.get("CHC"));
                printBracket("NATIONAL LEAGUE", nlTeams2016, nlWinners2016, teamNames, seeds);
                break;

            case 2017:
                String[] alTeams2017 = {"CLE", "HOU", "BOS", "NYY", "MIN"};
                String[] alWinners2017 = {"NYY", "HOU", "HOU", "HOU", "HOU"};
                String[] nlTeams2017 = {"LAD", "WSH", "CHC", "ARI", "COL"};
                String[] nlWinners2017 = {"ARI", "CHC", "LAD", "CHC", "LAD"};
                printBracket("AMERICAN LEAGUE", alTeams2017, alWinners2017, teamNames, seeds);
                System.out.println(" ---- HOU " + teamNames.get("HOU"));
                printBracket("NATIONAL LEAGUE", nlTeams2017, nlWinners2017, teamNames, seeds);
                break;

            case 2018:
                String[] alTeams2018 = {"BOS", "HOU", "CLE", "NYY", "OAK"};
                String[] alWinners2018 = {"NYY", "HOU", "BOS", "HOU", "BOS"};
                String[] nlTeams2018 = {"MIL", "LAD", "ATL", "COL", "CHC"};
                String[] nlWinners2018 = {"COL", "LAD", "MIL", "LAD", "LAD"};
                printBracket("AMERICAN LEAGUE", alTeams2018, alWinners2018, teamNames, seeds);
                System.out.println(" ---- BOS " + teamNames.get("BOS"));
                printBracket("NATIONAL LEAGUE", nlTeams2018, nlWinners2018, teamNames, seeds);
                break;

            case 2019:
                String[] alTeams2019 = {"HOU", "NYY", "MIN", "TB", "OAK"};
                String[] alWinners2019 = {"TB", "NYY", "HOU", "NYY", "HOU"};
                String[] nlTeams2019 = {"LAD", "ATL", "STL", "WSH", "MIL"};
                String[] nlWinners2019 = {"WSH", "STL", "WSH", "STL", "WSH"};
                printBracket("AMERICAN LEAGUE", alTeams2019, alWinners2019, teamNames, seeds);
                System.out.println(" ---- WSH " + teamNames.get("WSH"));
                printBracket("NATIONAL LEAGUE", nlTeams2019, nlWinners2019, teamNames, seeds);
                break;

            case 2020:
                String[] alTeams2020 = {"TB", "OAK", "MIN", "CLE", "NYY", "HOU", "CWS", "TOR"};
                String[] alWinners2020 = {"NYY", "HOU", "TB", "TB", "HOU", "TB", "TB", "TB"};
                String[] nlTeams2020 = {"LAD", "ATL", "CHC", "SD", "STL", "MIA", "CIN", "MIL"};
                String[] nlWinners2020 = {"LAD", "ATL", "MIA", "LAD", "SD", "LAD", "LAD", "LAD"};
                printBracket2020("AMERICAN LEAGUE", alTeams2020, alWinners2020, teamNames, seeds);
                System.out.println(" ---- LAD " + teamNames.get("LAD"));
                printBracket2020("NATIONAL LEAGUE", nlTeams2020, nlWinners2020, teamNames, seeds);
                break;

            case 2021:
                String[] alTeams2021 = {"TB", "HOU", "CWS", "BOS", "NYY"};
                String[] alWinners2021 = {"BOS", "CWS", "HOU", "CWS", "HOU"};
                String[] nlTeams2021 = {"SF", "MIL", "ATL", "LAD", "STL"};
                String[] nlWinners2021 = {"LAD", "ATL", "SF", "ATL", "ATL"};
                printBracket("AMERICAN LEAGUE", alTeams2021, alWinners2021, teamNames, seeds);
                System.out.println(" ---- ATL " + teamNames.get("ATL"));
                printBracket("NATIONAL LEAGUE", nlTeams2021, nlWinners2021, teamNames, seeds);
                break;

            case 2022:
                String[] alTeams2022 = {"HOU", "NYY", "CLE", "SEA", "TB", "TOR"};
                String[] alWinners2022 = {"SEA", "CLE", "HOU", "CLE", "HOU"};
                String[] nlTeams2022 = {"LAD", "ATL", "STL", "NYM", "SD", "PHI"};
                String[] nlWinners2022 = {"SD", "PHI", "PHI", "PHI", "PHI"};
                printBracket("AMERICAN LEAGUE", alTeams2022, alWinners2022, teamNames, seeds);
                System.out.println(" ---- HOU " + teamNames.get("HOU"));
                printBracket("NATIONAL LEAGUE", nlTeams2022, nlWinners2022, teamNames, seeds);
                break;

            case 2023:
                String[] alTeams2023 = {"BAL", "HOU", "MIN", "TB", "TEX", "TOR"};
                String[] alWinners2023 = {"TEX", "MIN", "TEX", "MIN", "TEX"};
                String[] nlTeams2023 = {"ATL", "LAD", "MIL", "PHI", "ARI", "MIA"};
                String[] nlWinners2023 = {"PHI", "ARI", "PHI", "ARI", "ARI"};
                printBracket("AMERICAN LEAGUE", alTeams2023, alWinners2023, teamNames, seeds);
                System.out.println(" ---- TEX " + teamNames.get("TEX"));
                printBracket("NATIONAL LEAGUE", nlTeams2023, nlWinners2023, teamNames, seeds);
                break;

            case 2024:
                // 2024年的資料可以在未來添加
                break;
        }
    }
}