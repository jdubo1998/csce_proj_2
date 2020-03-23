import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
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

    static void q1(Formatter formated) {

    }

    static void q2(Formatter formated, dbConnect conn, String playerOne, String playerTwo) {
        String [] Q2Tables = {"player", "team"};
        String [] Q2Columns = {"player code", "first name", "last name", "home town", "home state", "last school", "name"};
        String Q2query = conn.Q2MakeQuery(playerOne, playerTwo, Q2Tables, Q2Columns);
        String [][] Q2Data = conn.Q2sendQuery(Q2query, Q2Columns);
        int i = 0, j = 0;
        String P1Name = Q2Data[0][1] + " " + Q2Data[0][2];
        String P2Name = Q2Data[1][1] + " " + Q2Data[1][2];

        // Checking if any of the players personal information matches such as same team, hometown, same high school
        if (Q2Data[0][6].equals(Q2Data[1][6])) {
            formated.format(P1Name + " (" + playerOne + ")" + " and " + P2Name + " (" + playerTwo + ")" + " both played for " + Q2Data[0][6] + ".");
        }
        else if (Q2Data[0][5].equals(Q2Data[1][5])) {
            formated.format(P1Name+ " (" + playerOne + ")" + " and " + P2Name + " (" + playerTwo + ")" + " both attended " + Q2Data[0][5] + ".");
        }
        else if (Q2Data[0][3].equals(Q2Data[1][3]) && Q2Data[0][4].equals(Q2Data[1][4])) {
            formated.format(P1Name + " (" + playerOne + ")" + " and " + P2Name + " (" + playerTwo + ")" + " are both from " + Q2Data[0][3] + ", " + Q2Data[0][4] + ".");
        }
        else {
            for (i = 0; i < Q2Tables.length; i++) {
                Q2Tables[i] = "null";
            }
            for (i = 0; i < Q2Columns.length; i++) {
                Q2Columns[i] = "null";
            }
            Q2query = conn.Q2MakeQuery(Q2Data[0][0], Q2Data[1][0], Q2Tables, Q2Columns);
            System.out.println(Q2query);
            String[][] newData = conn.Q2sendQuery(Q2query, Q2Columns);
            int counter = 0;
            for (i = 0; i < newData.length; i++) {
                for (j = 0; j< newData[i].length; j++) {
                }
                counter++;
            }
            String teamOne = Q2Data[0][6];
            String teamTwo = Q2Data[1][6];
            Boolean foundMatch = false;
            // Checks to see if their teams ever played against each other
            for (i = 0; i < counter; i++) {
                if (newData[i][3].equals(teamOne) && newData[i][4].equals(teamTwo) || newData[i][3].equals(teamTwo) && newData[i][4].equals(teamOne)) {
                    formated.format(P1Name + " (" + playerOne + ")" + " played for " + teamOne + " and " + P2Name + " (" + playerTwo + ")" + " played for " + teamTwo + ". These two team played against each other in " + newData[i][2] + ".");
                    return;
                }
            }
            String temp;
            String opponent;
            int nextIndex;
            j = 3;
            // If the teams never played against each other, this algorithm searches for a common team that both of the teams played
            for (i = 0; i < counter; i++) {
                if (!newData[i][j].equals(teamOne) && !newData[i][j].equals(teamTwo)) {
                    temp = newData[i][j];
                    opponent = newData[i][j + 1];
                    nextIndex = i + 1;
                    while ((nextIndex < counter) && (!foundMatch)) {
                        if (temp.equals(newData[nextIndex][j]) && !opponent.equals(newData[nextIndex][j + 1]) || temp.equals(newData[nextIndex][j + 1]) && !opponent.equals(newData[nextIndex][j])) {
                            foundMatch = true;
                            if (teamOne.equals(opponent)) {
                                formated.format(P1Name + " (" + playerOne + ")" + " played for " + teamOne + ", " + "which played against " + temp + " in " + newData[i][2] + ", which played against " + teamTwo +  " in " + newData[nextIndex][2] + ", which had " + P2Name + " (" + playerTwo + ")" + " on their team.");
                            }
                            else {
                                formated.format(P2Name + " (" + playerTwo + ")" + " played for " + teamTwo + ", " + "which played against " + temp + " in " + newData[i][2] + ", which played against " + teamOne +  " in " + newData[nextIndex][2] + ", which had " + P1Name + " (" + playerOne + ")" + " on their team.");
                            }
                            break;
                        }
                        else {
                            nextIndex++;
                        }
                    }
                }
            }
            
        }
    }

    static void q3(Formatter formated) {

    }

    static void q4(Formatter formated) {

    }
}