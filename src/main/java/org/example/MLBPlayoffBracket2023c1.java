package org.example;

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MLBPlayoffBracket2023c1 {
    private static final Logger LOGGER = Logger.getLogger(MLBPlayoffBracket2023c1.class.getName());

    public static void main(String[] args) {
        Map<String, String> teamNames = null;
        Map<String, Integer> seeds2024 = null;
        try {
            teamNames = initializeTeamNames();
            Map<String, Integer> seeds2015 = initializeSeeds2015();
            Map<String, Integer> seeds2016 = initializeSeeds2016();
            Map<String, Integer> seeds2017 = initializeSeeds2017();
            Map<String, Integer> seeds2018 = initializeSeeds2018();
            Map<String, Integer> seeds2019 = initializeSeeds2019();
            Map<String, Integer> seeds2020 = initializeSeeds2020();
            Map<String, Integer> seeds2021 = initializeSeeds2021();
            Map<String, Integer> seeds2022 = initializeSeeds2022();
            Map<String, Integer> seeds2023 = initializeSeeds2023();
            seeds2024 = initializeSeeds2024();


            // 2015 Playoff Data
            String[] alTeams2015 = {"KC", "TOR", "TEX", "HOU", "NYY"};
            String[] alWinners2015 = {"HOU", "TOR", "KC", "TOR", "KC"};
            String[] nlTeams2015 = {"STL", "LAD", "NYM", "CHC", "PIT"};
            String[] nlWinners2015 = {"CHC", "NYM", "CHC", "NYM", "NYM"};

            // Validate and print 2015 bracket
            validateTeamsAndWinners(alTeams2015, alWinners2015, teamNames, seeds2015);
            validateTeamsAndWinners(nlTeams2015, nlWinners2015, teamNames, seeds2015);

            System.out.println("\n\n2015 MLB Playoff Bracket:\n\n");
            printBracket("AMERICAN LEAGUE", alTeams2015, alWinners2015, teamNames, seeds2015);
            System.out.println(" ---- KC " + teamNames.getOrDefault("KC", "Unknown Team"));
            printBracket("NATIONAL LEAGUE", nlTeams2015, nlWinners2015, teamNames, seeds2015);
            System.out.println("\n");


            // 2016 Playoff Data
            String[] alTeams2016 = {"TEX", "CLE", "BOS", "TOR", "BAL"};
            String[] alWinners2016 = {"TOR", "CLE", "CLE", "TOR", "CLE"};
            String[] nlTeams2016 = {"CHC", "WSH", "LAD", "SF", "NYM"};
            String[] nlWinners2016 = {"SF", "LAD", "CHC", "CHC", "CHC"};

            // Validate and print 2016 bracket
            validateTeamsAndWinners(alTeams2016, alWinners2016, teamNames, seeds2016);
            validateTeamsAndWinners(nlTeams2016, nlWinners2016, teamNames, seeds2016);

            System.out.println("\n\n2016 MLB Playoff Bracket:\n\n");
            printBracket("AMERICAN LEAGUE", alTeams2016, alWinners2016, teamNames, seeds2016);
            System.out.println(" ---- CHC " + teamNames.getOrDefault("CHC", "Unknown Team"));
            printBracket("NATIONAL LEAGUE", nlTeams2016, nlWinners2016, teamNames, seeds2016);
            System.out.println("\n");


            // 2017 Playoff Data
            String[] alTeams2017 = {"CLE", "HOU", "BOS", "NYY", "MIN"};
            String[] alWinners2017 = {"NYY", "HOU", "HOU", "HOU", "HOU"};
            String[] nlTeams2017 = {"LAD", "WSH", "CHC", "ARI", "COL"};
            String[] nlWinners2017 = {"ARI", "CHC", "LAD", "LAD", "LAD"};

            // Validate and print 2017 bracket
            validateTeamsAndWinners(alTeams2017, alWinners2017, teamNames, seeds2017);
            validateTeamsAndWinners(nlTeams2017, nlWinners2017, teamNames, seeds2017);

            System.out.println("\n\n2017 MLB Playoff Bracket:\n\n");
            printBracket("AMERICAN LEAGUE", alTeams2017, alWinners2017, teamNames, seeds2017);
            System.out.println(" ---- HOU " + teamNames.getOrDefault("HOU", "Unknown Team"));
            printBracket("NATIONAL LEAGUE", nlTeams2017, nlWinners2017, teamNames, seeds2017);
            System.out.println("\n");

            // 2018 Playoff Data
            String[] alTeams2018 = {"BOS", "HOU", "CLE", "NYY", "OAK"};
            String[] alWinners2018 = {"NYY", "HOU", "BOS", "BOS", "BOS"};
            String[] nlTeams2018 = {"MIL", "LAD", "ATL", "CHC", "COL"};
            String[] nlWinners2018 = {"COL", "LAD", "LAD", "MIL", "LAD"};
            // Validate and print 2018 bracket
            validateTeamsAndWinners(alTeams2018, alWinners2018, teamNames, seeds2018);
            validateTeamsAndWinners(nlTeams2018, nlWinners2018, teamNames, seeds2018);
            System.out.println("\n\n2018 MLB Playoff Bracket:\n\n");
            printBracket("AMERICAN LEAGUE", alTeams2018, alWinners2018, teamNames, seeds2018);
            System.out.println(" ---- BOS " + teamNames.getOrDefault("BOS", "Unknown Team"));
            printBracket("NATIONAL LEAGUE", nlTeams2018, nlWinners2018, teamNames, seeds2018);
            System.out.println("\n");

            // 2019 Playoff Data
            String[] alTeams2019 = {"HOU", "NYY", "MIN", "OAK", "TB"};
            String[] alWinners2019 = {"TB", "NYY", "HOU", "NYY", "HOU"};
            String[] nlTeams2019 = {"LAD", "ATL", "STL", "WSH", "MIL"};
            String[] nlWinners2019 = {"WSH", "STL", "WSH", "STL", "WSH"};

            // Validate and print 2019 bracket
            validateTeamsAndWinners(alTeams2019, alWinners2019, teamNames, seeds2019);
            validateTeamsAndWinners(nlTeams2019, nlWinners2019, teamNames, seeds2019);

            System.out.println("\n\n2019 MLB Playoff Bracket:\n\n");
            printBracket("AMERICAN LEAGUE", alTeams2019, alWinners2019, teamNames, seeds2019);
            System.out.println(" ---- WSH " + teamNames.getOrDefault("WSH", "Unknown Team"));
            printBracket("NATIONAL LEAGUE", nlTeams2019, nlWinners2019, teamNames, seeds2019);
            System.out.println("\n");


            // 2020 Playoff Data
            String[] alTeams2020 = {"TB", "OAK", "MIN", "CLE", "NYY", "HOU", "CHW", "TOR"};
            String[] alWinners2020 = {"TB", "HOU", "NYY", "TB", "TB", "HOU", "TB", "TB"};
            String[] nlTeams2020 = {"LAD", "ATL", "CHC", "SD", "STL", "MIA", "CIN", "MIL"};
            String[] nlWinners2020 = {"LAD", "ATL", "MIA", "LAD", "LAD", "ATL", "LAD", "LAD"};

            // Validate and print 2020 bracket
            validateTeamsAndWinners(alTeams2020, alWinners2020, teamNames, seeds2020);
            validateTeamsAndWinners(nlTeams2020, nlWinners2020, teamNames, seeds2020);

            System.out.println("\n\n2020 MLB Playoff Bracket:\n\n");
            printBracket2020("AMERICAN LEAGUE", alTeams2020, alWinners2020, teamNames, seeds2020);
            System.out.println(" ---- LAD " + teamNames.getOrDefault("LAD", "Unknown Team"));
            printBracket2020("NATIONAL LEAGUE", nlTeams2020, nlWinners2020, teamNames, seeds2020);
            System.out.println("\n");


            // 2021 Playoff Data
            String[] alTeams2021 = {"TB", "HOU", "CWS", "BOS", "NYY"};
            String[] alWinners2021 = {"BOS", "HOU", "HOU", "BOS", "HOU"};
            String[] nlTeams2021 = {"SF", "MIL", "ATL", "LAD", "STL"};
            String[] nlWinners2021 = {"LAD", "ATL", "ATL", "LAD", "ATL"};

            // Validate and print 2021 bracket
            validateTeamsAndWinners(alTeams2021, alWinners2021, teamNames, seeds2021);
            validateTeamsAndWinners(nlTeams2021, nlWinners2021, teamNames, seeds2021);

            System.out.println("\n\n2021 MLB Playoff Bracket:\n\n");
            printBracket("AMERICAN LEAGUE", alTeams2021, alWinners2021, teamNames, seeds2021);
            System.out.println(" ---- ATL " + teamNames.getOrDefault("ATL", "Unknown Team"));
            printBracket("NATIONAL LEAGUE", nlTeams2021, nlWinners2021, teamNames, seeds2021);
            System.out.println("\n");


            // 2022 Playoff Data
            String[] alTeams2022 = {"HOU", "NYY", "CLE", "SEA", "TB", "TOR"};
            String[] alWinners2022 = {"SEA", "CLE", "HOU", "NYY", "HOU", "HOU"};
            String[] nlTeams2022 = {"LAD", "ATL", "STL", "NYM", "SD", "PHI"};
            String[] nlWinners2022 = {"SD", "PHI", "PHI", "SD", "PHI", "PHI"};

            // Validate and print 2022 bracket
            validateTeamsAndWinners(alTeams2022, alWinners2022, teamNames, seeds2022);
            validateTeamsAndWinners(nlTeams2022, nlWinners2022, teamNames, seeds2022);
            System.out.println("\n\n2022 MLB Playoff Bracket:\n\n");
            printBracket("AMERICAN LEAGUE", alTeams2022, alWinners2022, teamNames, seeds2022);
            System.out.println("                               ---- HOU " + teamNames.getOrDefault("HOU", "Unknown Team"));
            printBracket("NATIONAL LEAGUE", nlTeams2022, nlWinners2022, teamNames, seeds2022);
            System.out.println("\n");

            // 2023 Playoff Data
            String[] alTeams2023 = {"BAL", "HOU", "MIN", "TB", "TEX", "TOR"};
            String[] alWinners2023 = {"TEX", "MIN", "TEX", "HOU", "TEX", "TEX"};
            String[] nlTeams2023 = {"ATL", "LAD", "MIL", "PHI", "AZ", "MIA"};
            String[] nlWinners2023 = {"AZ", "PHI", "PHI", "AZ", "AZ", "AZ"};


            // Validate and print 2023 bracket
            validateTeamsAndWinners(alTeams2023, alWinners2023, teamNames, seeds2023);
            validateTeamsAndWinners(nlTeams2023, nlWinners2023, teamNames, seeds2023);
            System.out.println("\n\n2023 MLB Playoff Bracket:\n\n");
            printBracket("AMERICAN LEAGUE", alTeams2023, alWinners2023, teamNames, seeds2023);
            System.out.println("                               ---- TEX " + teamNames.getOrDefault("TEX", "Unknown Team"));
            printBracket("NATIONAL LEAGUE", nlTeams2023, nlWinners2023, teamNames, seeds2023);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Error in input data: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error occurred", e);
        }

        // 2024 Playoff Data
        String[] alTeams2024 = {"NYY", "CLE", "HOU", "BAL", "KC", "DET"};
        String[] alWinners2024 = {"DET", "KC", "TBA", "TBA", "TBA", "TBA"};
        String[] nlTeams2024 = {"LAD", "PHI", "MIL", "SD", "ATL", "NYM"};
        String[] nlWinners2024 = {"NYM", "TBA", "TBA", "TBA", "TBA", "TBA"};

        // Validate and print 2024 bracket
        validateTeamsAndWinners(alTeams2024, alWinners2024, teamNames, seeds2024);
        validateTeamsAndWinners(nlTeams2024, nlWinners2024, teamNames, seeds2024);
        System.out.println("\n\n2024 MLB Playoff Bracket (Placeholder):\n\n");
        printBracket("AMERICAN LEAGUE", alTeams2024, alWinners2024, teamNames, seeds2024);
        System.out.println("                               ---- TBA 待定冠军");
        printBracket("NATIONAL LEAGUE", nlTeams2024, nlWinners2024, teamNames, seeds2024);
        System.out.println("\n");

    }



    static Map<String, String> initializeTeamNames() {
        Map<String, String> teamNames = new HashMap<>();
        teamNames.put("TEX", "德克薩斯遊騎兵");
        teamNames.put("HOU", "休斯頓太空人");
        teamNames.put("MIN", "明尼蘇達雙城");
        teamNames.put("TB", "坦帕灣光芒");
        teamNames.put("TOR", "多倫多藍鳥");
        teamNames.put("BAL", "巴爾的摩金鶯");
        teamNames.put("AZ", "亞利桑那響尾蛇");
        teamNames.put("PHI", "費城費城人");
        teamNames.put("MIL", "密爾瓦基釀酒人");
        teamNames.put("MIA", "邁阿密馬林魚");
        teamNames.put("LAD", "洛杉磯道奇");
        teamNames.put("ATL", "亞特蘭大勇士");
        teamNames.put("NYY", "紐約洋基");
        teamNames.put("CLE", "克里夫蘭守護者");
        teamNames.put("SEA", "西雅圖水手");
        teamNames.put("STL", "聖路易紅雀");
        teamNames.put("NYM", "紐約大都會");
        teamNames.put("SD", "聖地牙哥教士");
        teamNames.put("OAK", "奧克蘭運動家");
        teamNames.put("BOS", "波士頓紅襪");
        teamNames.put("WSH", "華盛頓國民");
        teamNames.put("KC", "堪薩斯市皇家");
        teamNames.put("CHC", "芝加哥小熊");
        teamNames.put("PIT", "匹茲堡海盜");
        teamNames.put("SF", "舊金山巨人");
        teamNames.put("DET", "底特律老虎");
        teamNames.put("LAA", "洛杉磯天使");
        teamNames.put("COL", "科羅拉多落磯");
        teamNames.put("ARI", "亞利桑那響尾蛇");
        teamNames.put("CHW", "芝加哥白襪");
        teamNames.put("CIN", "辛辛那提紅人");
        teamNames.put("CWS", "芝加哥白襪");
        teamNames.put("TBA", "待定");
        teamNames.put("TBA1", "待定1");
        teamNames.put("TBA2", "待定2");
        teamNames.put("TBA3", "待定3");
        teamNames.put("TBA4", "待定4");
        teamNames.put("TBA5", "待定5");
        teamNames.put("TBA6", "待定6");
        return teamNames;
    }

    private static Map<String, Integer> initializeSeeds2015() {
        Map<String, Integer> seeds = new HashMap<>();
        seeds.put("KC", 1);
        seeds.put("TOR", 2);
        seeds.put("TEX", 3);
        seeds.put("HOU", 5);
        seeds.put("NYY", 4);
        seeds.put("STL", 1);
        seeds.put("LAD", 2);
        seeds.put("NYM", 3);
        seeds.put("CHC", 3);
        seeds.put("PIT", 4);
        return seeds;
    }
    private static Map<String, Integer> initializeSeeds2016() {
        Map<String, Integer> seeds = new HashMap<>();
        seeds.put("TEX", 1);
        seeds.put("CLE", 2);
        seeds.put("BOS", 3);
        seeds.put("TOR", 4);
        seeds.put("BAL", 5);
        seeds.put("CHC", 1);
        seeds.put("WSH", 2);
        seeds.put("LAD", 3);
        seeds.put("SF", 4);
        seeds.put("NYM", 5);
        return seeds;
    }

    private static Map<String, Integer> initializeSeeds2017() {
        Map<String, Integer> seeds = new HashMap<>();
        seeds.put("CLE", 1);
        seeds.put("HOU", 2);
        seeds.put("BOS", 3);
        seeds.put("NYY", 4);
        seeds.put("MIN", 5);
        seeds.put("LAD", 1);
        seeds.put("WSH", 2);
        seeds.put("CHC", 3);
        seeds.put("ARI", 4);
        seeds.put("COL", 5);
        return seeds;
    }
    private static Map<String, Integer> initializeSeeds2018() {
        Map<String, Integer> seeds = new HashMap<>();
        seeds.put("BOS", 1);
        seeds.put("HOU", 2);
        seeds.put("CLE", 3);
        seeds.put("NYY", 4);
        seeds.put("OAK", 5);
        seeds.put("MIL", 1);
        seeds.put("LAD", 2);
        seeds.put("ATL", 3);
        seeds.put("CHC", 4);
        seeds.put("COL", 5);
        return seeds;
    }
    private static Map<String, Integer> initializeSeeds2019() {
        Map<String, Integer> seeds = new HashMap<>();
        seeds.put("HOU", 1);
        seeds.put("NYY", 2);
        seeds.put("MIN", 3);
        seeds.put("OAK", 4);
        seeds.put("TB", 5);
        seeds.put("LAD", 1);
        seeds.put("ATL", 2);
        seeds.put("STL", 3);
        seeds.put("WSH", 4);
        seeds.put("MIL", 5);
        return seeds;
    }


    static Map<String, Integer> initializeSeeds2020() {
        Map<String, Integer> seeds = new HashMap<>();
        seeds.put("TB", 1);
        seeds.put("OAK", 2);
        seeds.put("MIN", 3);
        seeds.put("CLE", 4);
        seeds.put("NYY", 5);
        seeds.put("HOU", 6);
        seeds.put("CHW", 7);
        seeds.put("TOR", 8);
        seeds.put("LAD", 1);
        seeds.put("ATL", 2);
        seeds.put("CHC", 3);
        seeds.put("SD", 4);
        seeds.put("STL", 5);
        seeds.put("MIA", 6);
        seeds.put("CIN", 7);
        seeds.put("MIL", 8);
        return seeds;
    }

    private static Map<String, Integer> initializeSeeds2021() {
        Map<String, Integer> seeds = new HashMap<>();
        seeds.put("TB", 1);
        seeds.put("HOU", 2);
        seeds.put("CWS", 3);
        seeds.put("BOS", 4);
        seeds.put("NYY", 5);
        seeds.put("SF", 1);
        seeds.put("MIL", 2);
        seeds.put("ATL", 3);
        seeds.put("LAD", 4);
        seeds.put("STL", 5);
        return seeds;
    }


    private static Map<String, Integer> initializeSeeds2022() {
        Map<String, Integer> seeds = new HashMap<>();
        seeds.put("HOU", 1);
        seeds.put("NYY", 2);
        seeds.put("CLE", 3);
        seeds.put("SEA", 5);
        seeds.put("TB", 6);
        seeds.put("TOR", 4);
        seeds.put("LAD", 1);
        seeds.put("ATL", 2);
        seeds.put("STL", 3);
        seeds.put("NYM", 4);
        seeds.put("SD", 5);
        seeds.put("PHI", 6);
        return seeds;
    }

    static Map<String, Integer> initializeSeeds2023() {
        Map<String, Integer> seeds = new HashMap<>();
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
        seeds.put("AZ", 5);
        seeds.put("MIA", 6);
        return seeds;
    }

    private static Map<String, Integer> initializeSeeds2024() {
        Map<String, Integer> seeds = new HashMap<>();
        seeds.put("NYY", 1);
        seeds.put("CLE", 2);
        seeds.put("HOU", 3);
        seeds.put("BAL", 4);
        seeds.put("KC", 5);
        seeds.put("DET", 6);
        seeds.put("LAD", 1);
        seeds.put("PHI", 2);
        seeds.put("MIL", 3);
        seeds.put("SD", 4);
        seeds.put("ATL", 5);
        seeds.put("NYM", 6);
        return seeds;
    }
    private static void validateTeamsAndWinners(String[] teams, String[] winners,
                                                Map<String, String> teamNames, Map<String, Integer> seeds) {
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
                System.out.printf("        %-3s ----- %-3s\n", team, winners[2]);
            } else if (i == 4) {
                System.out.printf("%-3s ----- %-3s -----\n", winners[1], winners[3]);
                System.out.printf("        %-3s ----- %-3s ----- %-3s\n", teams[0], winners[3], winners[4]);
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
                System.out.printf("        %-3s ----- %-3s\n", winners[1], winners[3]);
            } else if (i == 3) {
                System.out.printf("%-3s ----- %-3s -----\n", winners[2], winners[5]);
            } else if (i == 5) {
                System.out.printf("        %-3s ----- %-3s ----- %-3s\n", winners[0], winners[5], winners[7]);
            }
        }
    }
}