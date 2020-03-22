import java.util.Formatter;
import java.util.Locale;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class Questions {
    static void normal(Formatter formated, dbConnect conn, String[] tables, String[] columns, String joinBy, 
            int col1, String season) {

        String query = conn.makeQuery(tables, columns, joinBy, col1, season);

        // send the query to the database
        String[] data = conn.sendQuery(query, columns);

        // split the data into a more usable format
        String[][] splitData = new String[columns.length][];

        for (int i = 0; i < columns.length; i++) {
            splitData[i] = data[i].split("\n");
        }

        // find largets string
        int[] maxlengths = new int[columns.length];

        for (int i = 0; i < columns.length; i++) {
            maxlengths[i] = columns[i].length();
        }

        for (int i = 0; i < splitData.length; i++) {
            for (int j = 0; j < splitData[i].length; j++) {
                maxlengths[i] = Math.max(splitData[i][j].length(), maxlengths[i]);
            }
        }

        // Start formatting the output in an even way
        formated.format("Table 1:%20s\tTable2:%20s\n", tables[0], tables[1]);
        for (int i = 0; i < columns.length; i++) {
            formated.format("%-" + maxlengths[i] + "s%2s|%2s", columns[i], " ", " ");
        }
        formated.format("\n");

        for (int i = 0; i < splitData[0].length; i++) {
            for (int j = 0; j < columns.length; j++) {
                formated.format("%-" + maxlengths[j] + "s%2s|%2s", splitData[j][i], " ", " ");
            }
            formated.format("\n");
        }
    }

    static void q1(Formatter formated, dbConnect conn, String team1, String team2) {
        String team1code, team2code;
        String chain;
        String[] code;

        code = conn.sendQuery("SELECT \"team code\" FROM \"team\" WHERE name='" + team1 + "' GROUP BY \"team code\";", new String[] {"team code"});
        team1code = code[0].replace("\n", "");

        code = conn.sendQuery("SELECT \"team code\" FROM team WHERE name='" + team2 + "' GROUP BY \"team code\";", new String[] {"team code"});
        team2code = code[0].replace("\n", "");

        if (code[0].length() == 0) {
            formated.format("%s", "Invalid username or password.");
            return;
        }

        chain = getTeamChain(conn, team1code, team2code, new ArrayList<String>(), true);

        if (chain.equals("NULL")) {
            formated.format("%s has never beaten any team, thus the chain is null.", team1);
            return;
        }

        formated.format("%s", chain);
        // return chain;
    }

    private static String getTeamChain(dbConnect conn, String team1code, String team2code, ArrayList<String> ignore, boolean first) {
        String[] otherteam;
        String[] compare;
        String[] seasons;
        ignore.add(team1code);

        String[] querryresult = conn.sendQuery("SELECT game.\"season\",htc.\"team code\",vtc.\"team code\" FROM game " +
            "INNER JOIN team_game_statistics AS htc ON htc.\"game code\"=game.\"game code\" AND htc.\"team code\"=game.\"home team code\" " + 
            "INNER JOIN team_game_statistics AS vtc ON vtc.\"game code\"=game.\"game code\" AND vtc.\"team code\"=game.\"visit team code\" " +
            "WHERE " + 
            "(htc.\"team code\"=" + team1code + " AND (htc.\"points\" > vtc.\"points\")) OR " +
            "(vtc.\"team code\"=" + team1code + " AND (vtc.\"points\" > htc.\"points\")) ORDER BY game.\"season\";"
            , new String[] {"season", "htc.\"team code\"", "vtc.\"team code\""});

        if (querryresult[0].equals("")) {
            return "NULL";
        }

        seasons = querryresult[0].split("\n");

        otherteam = querryresult[1].split("\n");
        compare = querryresult[2].split("\n");

        /* Creates and array of each team team 1 beat in every season. */
        for (int i = 0; i < otherteam.length; i++) {
            if (otherteam[i].equals(team1code)) {
                otherteam[i] = compare[i];
            }
        }

        /* Iterates through the array of teams and sees if team 2 is one of those teams, if so returns when team 1 beat them. */
        for (int i = 0; i < otherteam.length; i++) {
            if (otherteam[i].equals(team2code)) {
                String[] name1 = conn.sendQuery("SELECT \"name\" FROM team WHERE \"team code\"="+team1code+" GROUP BY \"name\";", new String[] {"name"});
                String[] name2 = conn.sendQuery("SELECT \"name\" FROM team WHERE \"team code\"="+team2code+" GROUP BY \"name\";", new String[] {"name"});

                if (first) {
                    return name1[0].replace("\n", "") + " beat " + name2[0].replace("\n", "") + " " + seasons[i].replace("\n", "") + ".";
                } else {
                    return "and " + name1[0].replace("\n", "") + " beat " + name2[0].replace("\n", "") + " " + seasons[i].replace("\n", "") + ".";
                }
            }
        }

        /* Iterates through the array of teams and recursivly calls this function again, accept team1 is the next team. Doesn't call duplicate teams. */
        for (int i = 0; i < otherteam.length; i++) {
            if (!ignore.contains(otherteam[i])) {
                String[] name1 = conn.sendQuery("SELECT \"name\" FROM team WHERE \"team code\"="+team1code+" GROUP BY \"name\";", new String[] {"name"});
                String[] name2 = conn.sendQuery("SELECT \"name\" FROM team WHERE \"team code\"="+otherteam[i]+" GROUP BY \"name\";", new String[] {"name"});

                String link = getTeamChain(conn, otherteam[i], team2code, ignore, false);
                if (!link.equals("NULL") && !link.equals("")) {
                    return name1[0].replace("\n", "") + " beat " + name2[0].replace("\n", "") + " " + seasons[i].replace("\n", "") 
                        + ", " + link;
                }
            }
        }

        return "";
    }

    static void q2(Formatter formated) {

    }

    static void q3(Formatter formated, dbConnect conn, String team) {
        //get the team code needed
        String query = "SELECT \"team code\" FROM team WHERE name = '" + team + "';";
        String[] data = conn.sendQuery(query, "\"team code\"".split("\n"));
        String teamCode = data[0].split("\n")[0];

        //get the game codes that the team played in
        query = "SELECT \"game code\" FROM game WHERE \"visit team code\" = '" + teamCode + "' OR \"home team code\" = '" + teamCode + "';";
        data = conn.sendQuery(query, new String[] { "\"game code\"" });
        String[] gameCodes = data[0].split("\n");

        int maxYards = Integer.MIN_VALUE;
        String game = "";
        String bestTeam = "";

        //set up the columns
        String[] columns = new String[2];
        columns[0] = "rush yard";
        columns[1] = "team code";

        //find best team and game
        for (int i = 0; i < gameCodes.length; i++) {
            query = "SELECT \"rush yard\", \"team code\" FROM team_game_statistics WHERE NOT \"team code\" = '" + teamCode + "' AND \"game code\" = '" + gameCodes[i] + "';";

            data = conn.sendQuery(query, columns);

            int yards = Integer.parseInt(data[0].trim());
            if (yards > maxYards) {
                maxYards = yards;
                game = gameCodes[i];
                bestTeam = data[1].trim();
            }
        }

        //get name of best team
        query = "SELECT name FROM team WHERE \"team code\" = '" + bestTeam + "';";
        data = conn.sendQuery(query, new String[]{ "name" });
        String teamName = data[0].split("\n")[0];

        //get date of game
        query = "SELECT date FROM game WHERE \"game code\" = '" + game + "';";
        data = conn.sendQuery(query, new String[]{ "date" });
        String date = data[0].trim();

        //output
        formated.format("%s had %s rushing yards against %s during the game on %s.\n", teamName, maxYards, team, date);
    }

    static void q4(Formatter formated) {

    }

    static void q5(Formatter formated, dbConnect conn, String selection) {
        switch(selection) {
            case("players"):
                String query = "SELECT count(DISTINCT \"player code\"), \"home town\" FROM player GROUP BY \"home town\";";
                String[] data = conn.sendQuery(query, new String[] { "count(\"player code\")", "home town" });

                String[] counts = data[0].split("\n");
                String[] towns = data[1].split("\n");

                int max = Integer.MIN_VALUE;
                String town = "";

                for(int i = 0; i < counts.length; i++) {
                    if(Integer.parseInt(counts[i]) > max && !towns[i].equals("null")) {
                        max = Integer.parseInt(counts[i]);
                        town = towns[i];
                    }
                }

                formated.format("There are %s players from %s.", max, town);

                break;
            case("winners"):
                formated.format("winners not yet implemented");
                break;
        }
    }
}