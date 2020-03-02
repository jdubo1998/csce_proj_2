import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.text.StyledDocument;
import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.util.Formatter;
import java.util.Locale;
import java.lang.StringBuilder;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacob
 */
public class dbGui extends javax.swing.JFrame {

    //Useful arrays and strings
    String[] tables = new String[2];
    String[] columns;
    String joinBy;
    String query;
    int season;

    // Username and Password holders
    String username;
    String password;
    String[] conferenceColumns = { "conference code", "subdivision", "name", "season" };
    String[] driveColumns = { "game code", "drive number", "team code", "start period", "start clock", "start spot",
            "start reason", "end period", "end clock", "end spot", "end reason", "plays", "yards", "time of possession",
            "red zone attempt", "season" };
    String[] gameColumns = { "game code", "date", "visit team code", "home team code", "stadium code", "site",
            "season" };
    String[] game_statisticsColumns = { "game code", "attendance", "duration", "season" };
    String[] kickoffColumns = { "game code", "play number", "team code", "player code", "attempt", "yards",
            "fair catch", "touchback", "downed", "out of bounds", "onside", "onside success", "season", "row number" };
    String[] kickoff_returnColumns = { "game code", "play number", "team code", "player code", "attempt", "yards",
            "touchdown", "fumble", "fumble lost", "safety", "fair catch", "season", "row number" };
    String[] passColumns = { "game code", "play number", "team code", "passer player code", "receiver player code",
            "attempt", "completion", "yards", "touchdown", "interception", "1st down", "dropped", "season",
            "row number" };
    String[] playColumns = { "game code", "play number", "period number", "clock", "offense team code",
            "defense team code", "offense points", "defense points", "down", "distance", "spot", "play type",
            "drive number", "drive play", "season", "row number" };
    String[] playerColumns = { "player code", "team code", "last name", "first name", "uniform number", "class",
            "position", "height", "weight", "home town", "home state", "home country", "last school", "season" };
    String[] player_game_statisticsColumns = { "player code", "game code", "rush att", "rush yard", "rush td",
            "pass att", "pass comp", "pass yard", "pass td", "pass int", "pass conv", "rec", "rec yards", "rec td",
            "kickoff ret", "kickoff ret yard", "kickoff ret td", "punt ret", "punt ret yard", "punt ret td", "fum ret",
            "fum ret yard", "fum ret td", "int ret", "int ret yard", "int ret td", "misc ret", "misc ret yard",
            "misc ret td", "field goal att", "field goal made", "off xp kick att", "off xp kick made", "off 2xp att",
            "off 2xp made", "def 2xp att", "def 2xp made", "safety", "points", "punt", "punt yard", "kickoff",
            "kickoff yard", "kickoff touchback", "kickoff out of bounds", "kickoff onside", "fumble", "fumble lost",
            "tackle solo", "tackle assist", "tackle for loss", "tackle for loss yard", "sack", "sack yard", "qb hurry",
            "fumble forced", "pass broken up", "kick punt blocked", "season", "row number" };
    String[] puntColumns = { "game code", "play number", "team code", "player code", "attempt", "yards", "blocked",
            "fair catch", "touchback", "downed", "out of bounds", "season", "row number" };
    String[] punt_returnColumns = { "game code", "play number", "team code", "player code", "attempt", "yards",
            "touchdown", "fumble", "fumble lost", "safety", "fair catch", "season", "row number" };
    String[] receptionColumns = { "game code", "play number", "team code", "player code", "reception", "yards",
            "touchdown", "1st down", "fumble", "fumble lost", "safety", "season", "row number" };
    String[] rushColumns = { "game code", "play number", "team code", "player code", "attempt", "yards", "touchdown",
            "1st down", "sack", "fumble", "fumble lost", "safety", "season", "row number" };
    String[] stadiumColumns = { "stadium code", "name", "city", "state", "capacity",
            "surface",
            "year opened", "season" };
    String[] teamColumns = {"team code", "name", "conference code", "season"};
    String[] team_game_statisticsColumns = {"team code", "game code", "rush att", "rush yard", "rush td", "pas att", "pass comp",
             "pass yard", "pass td", "pass int", "pass conb", "kickoff ret", "kickoff ret yard", "kickoff ret td", "punt reg", "punt ret yard",
             "punt ret td", "fum ret", "fum ret yard", "fum ret td", "int ret", "int ret yard", "int ret td", "misc ret", "misc ret yard",
             "misc ret td", "field goal att", "field goal made", "off xp kick att", "off xp kick made", "off 2xp kick att", "off 2xp kick made",
             "def 2xp att", "def 2xp made", "safety", "points", "punt", "punt yard", "kickoff", "kickoff yard", "kickoff touchback", "kickoff out-of-bounds",
             "kickoff onside", "fumble", "fumble lost", "tackle solo", "tackle assist", "tackle for loss", "tackle for loss yard", "sack", "sack yard", 
             "qb hurry", "fumble forced", "pass broken up", "kick/punt blocked", "1st down rush", "1st down pass", "1st down penalty", "time of possession",
             "penalty", "penalty yard", "third down att", "third down conv", "fourth down att", "fourth down conv", "red zone att", "red zone td", "red zone field goal",
             "season", "row number"};





    /**
     * Creates new form dbGui
     */
    public dbGui() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem4 = new javax.swing.JCheckBoxMenuItem();
        Table1ComboBox = new javax.swing.JComboBox<>();
        UsernameTextField = new javax.swing.JTextField();
        PasswordTextField = new javax.swing.JPasswordField();
        UsernameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        Column1ComboBox = new JCheckComboBox();
        TableLabel = new javax.swing.JLabel();
        ColumnLabel = new javax.swing.JLabel();
        Season1ComboBox = new javax.swing.JComboBox<>();
        SeasonLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DatabaseOutput = new javax.swing.JTextPane();
        LogoLabel = new javax.swing.JLabel();
        FileCheckBox = new javax.swing.JCheckBox();
        SubmitButton = new javax.swing.JButton();
        TeamNameLabel = new javax.swing.JLabel();
        Column2ComboBox = new JCheckComboBox();
        Season2ComboBox = new javax.swing.JComboBox<>();
        Table2ComboBox = new javax.swing.JComboBox<>();
        JoinLabel = new javax.swing.JLabel();
        JoinComboBox = new javax.swing.JComboBox<>();
        EnableJoin1CheckBox = new javax.swing.JCheckBox();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("jCheckBoxMenuItem3");

        jCheckBoxMenuItem4.setSelected(true);
        jCheckBoxMenuItem4.setText("jCheckBoxMenuItem4");
        
        ImageIcon Logo = new ImageIcon("resources\\teamlogo.png");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Table1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "conference", "drive", "game", "game_statistics", "kickoff",
                                                                                     "kickoff_return", "pass", "play", "player", "player_game_statistics",
                                                                                      "punt", "punt_return", "reception", "rush", "stadium", "team",
                                                                                       "team_game_statistics" }));
        Table1ComboBox.setToolTipText("");
        Table1ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    Table1ComboBoxActionPerformed(evt);
            }
        });

        UsernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameTextFieldActionPerformed(evt);
            }
        });

        PasswordTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordTextFieldActionPerformed(evt);
            }
        });

        UsernameLabel.setText("Username");

        PasswordLabel.setText("Password");

        Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        Column1ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Column1ComboBoxActionPerformed(evt);
            }
        });      

        TableLabel.setText("Table");

        ColumnLabel.setText("Column");

        Season1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013" }));
        Season1ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Season1ComboBoxActionPerformed(evt);
            }
        });

        SeasonLabel.setText("Season");

        DatabaseOutput.setEditable(false);
        jScrollPane1.setViewportView(DatabaseOutput);

        FileCheckBox.setText("File");
        FileCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileCheckBoxActionPerformed(evt);
            }
        });

        SubmitButton.setText("Submit");
        SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButtonActionPerformed(evt);
            }
        });

        TeamNameLabel.setText("Team2_904_cfb");

        Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));
        Column2ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Column2ComboBoxActionPerformed(evt);
            }
        });

        Season2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013" }));
        Season2ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Season2ComboBoxActionPerformed(evt);
            }
        });

        Table2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "conference", "drive", "game", "game_statistics", "kickoff",
                                                                                    "kickoff_return", "pass", "play", "player", "player_game_statistics",
                                                                                    "punt", "punt_return", "reception", "rush", "stadium", "team",
                                                                                    "team_game_statistics" }));
        Table2ComboBox.setToolTipText("");
        Table2ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Table2ComboBoxActionPerformed(evt);
            }
        });

        JoinLabel.setText("Join by:");
        LogoLabel.setIcon(new ImageIcon(Logo.getImage().getScaledInstance(163, 163, Image.SCALE_SMOOTH)));

        EnableJoin1CheckBox.setText(" ");
        EnableJoin1CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnableJoin1CheckBoxActionPerformed(evt);
            }
        });

        JoinComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"season"}));
        JoinComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JoinComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(PasswordLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(PasswordTextField))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(UsernameLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(240, 240, 240)
                                .addComponent(TeamNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                                .addGap(140, 140, 140))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(EnableJoin1CheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(Table1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(Column1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(TableLabel)
                                                .addGap(82, 82, 82)
                                                .addComponent(ColumnLabel)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(SeasonLabel)
                                            .addComponent(Season1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(Table2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(Column2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(Season2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(63, 63, 63)
                                .addComponent(JoinLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JoinComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(FileCheckBox)
                                .addGap(12, 12, 12)
                                .addComponent(SubmitButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(LogoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UsernameLabel)
                            .addComponent(TeamNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PasswordLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TableLabel)
                            .addComponent(ColumnLabel)
                            .addComponent(SeasonLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Season1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Column1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Table1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Table2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Column2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Season2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SubmitButton)
                            .addComponent(FileCheckBox)
                            .addComponent(JoinLabel)
                            .addComponent(JoinComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EnableJoin1CheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LogoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>                        

    private void Table1ComboBoxActionPerformed(java.awt.event.ActionEvent evt)  {
        // TODO add your handling code here:
        if ("conference".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(conferenceColumns));
        } 
        else if ("drive".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(driveColumns));
        } 
        else if ("game".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel( new javax.swing.DefaultComboBoxModel<>(gameColumns));
        } 
        else if ("game_statistics".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(game_statisticsColumns));
        } 
        else if ("kickoff".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(kickoffColumns));
        } 
        else if ("kickoff_return".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(kickoff_returnColumns));
        } 
        else if ("pass".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(passColumns));
        } 
        else if ("play".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(playColumns));
        } 
        else if ("player".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(playerColumns));
        } 
        else if ("player_game_statistics".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(player_game_statisticsColumns));
        } 
        else if ("punt".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(puntColumns));
        } 
        else if ("punt_return".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(punt_returnColumns));
        } 
        else if ("reception".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(receptionColumns));
        } 
        else if ("rush".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(rushColumns));
        } 
        else if ("stadium".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(stadiumColumns));
        } 
        else if ("team".equals(Table1ComboBox.getSelectedItem())){
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(teamColumns));
        }
        else {
            Column1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(team_game_statisticsColumns));
        }

        Column1ComboBox.setCheckedSize();
    }
                                              

    private void PasswordTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void UsernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void FileCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        username = UsernameTextField.getText();
        password = String.copyValueOf(PasswordTextField.getPassword());
        // set up a dbConnect object to talk to the database
        dbConnect conn = new dbConnect(username, password);

        // set up a query to use and what columns to use
        String[] col1 = Column1ComboBox.getChecked();
        String[] col2 = Column2ComboBox.getChecked();

        columns = new String[col1.length + col2.length];

        for(int i = 0; i < col1.length; i++) {
            columns[i] = col1[i];
        }
        for(int i = 0; i < col2.length; i++) {
            columns[i + col1.length] = col2[i];
        }

        joinBy = JoinComboBox.getSelectedItem().toString();

        tables[0] = Table1ComboBox.getSelectedItem().toString();
        tables[1] = Table2ComboBox.getSelectedItem().toString();
        query = conn.makeQuery(tables, columns, joinBy, col1.length, Season1ComboBox.getSelectedItem().toString());

        // send the query to the database
        String[] data = conn.sendQuery(query, columns);

        // split the data into a more usable format
        String[][] splitData = new String[columns.length][];

        for(int i = 0; i < columns.length; i++) {
            splitData[i] = data[i].split("\n");
        }

        //find largets string
        int[] maxlengths = new int[columns.length];

        for (int i = 0; i < columns.length; i++) {
            maxlengths[i] = columns[i].length();
        }

        for(int i = 0; i < splitData.length; i++) {
            for (int j = 0; j < splitData[i].length; j++){
                maxlengths[i] = Math.max(splitData[i][j].length(), maxlengths[i]);
            }
        }

        //Start formatting the output in an even way
        StringBuilder output = new StringBuilder();
        Formatter formated = new Formatter(output, Locale.US);

        formated.format("Table 1:%20s\tTable2:%20s\n", tables[0], tables[1]);
        for(int i = 0; i < columns.length; i++) {
            formated.format("%-" + maxlengths[i] + "s%5s|%5s", columns[i], " ", " ");
        }
        formated.format("\n");

        for (int i = 0; i < splitData[0].length; i++) {
            for (int j = 0; j < columns.length; j++) {
                formated.format("%-" + maxlengths[j] + "s%5s|%5s", splitData[j][i], " ", " ");
            }
            formated.format("\n");
        }

        formated.close();
        //end formating and output
        if (FileCheckBox.isSelected()) {
            try {
                File out = new File("output.txt");
                FileWriter outWrite = new FileWriter("output.txt");

                outWrite.write(output.toString());

                outWrite.close();
            } catch (IOException e) { System.out.println(e); }
        }
        DatabaseOutput.setText("");
        StyledDocument doc = DatabaseOutput.getStyledDocument();

        try {
            doc.insertString(0, output.toString(), null);
        } catch(Exception e) { System.out.println(e); }
    }                                            

    private void Column1ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        Column1ComboBox.setChecked(Column1ComboBox.getSelectedIndex());
    }                                               

    private void Season1ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void Column2ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                
        Column2ComboBox.setChecked(Column2ComboBox.getSelectedIndex());
    }                                               

    private void Season2ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void Table2ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
        if ("conference".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(conferenceColumns));
        } 
        else if ("drive".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(driveColumns));
        } 
        else if ("game".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel( new javax.swing.DefaultComboBoxModel<>(gameColumns));
        } 
        else if ("game_statistics".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(game_statisticsColumns));
        } 
        else if ("kickoff".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(kickoffColumns));
        } 
        else if ("kickoff_return".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(kickoff_returnColumns));
        } 
        else if ("pass".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(passColumns));
        } 
        else if ("play".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(playColumns));
        } 
        else if ("player".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(playerColumns));
        } 
        else if ("player_game_statistics".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(player_game_statisticsColumns));
        } 
        else if ("punt".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(puntColumns));
        } 
        else if ("punt_return".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(punt_returnColumns));
        } 
        else if ("reception".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(receptionColumns));
        } 
        else if ("rush".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(rushColumns));
        } 
        else if ("stadium".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(stadiumColumns));
        } 
        else if ("team".equals(Table2ComboBox.getSelectedItem())){
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(teamColumns));
        }
        else {
            Column2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(team_game_statisticsColumns));
        }

        Column2ComboBox.setCheckedSize();
    }                                              

    private void EnableJoin1CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
    }       
    
    private void JoinComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dbGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dbGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dbGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dbGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dbGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private JCheckComboBox Column1ComboBox;
    private JCheckComboBox Column2ComboBox;
    private javax.swing.JLabel ColumnLabel;
    private javax.swing.JTextPane DatabaseOutput;
    private javax.swing.JCheckBox EnableJoin1CheckBox;
    private javax.swing.JCheckBox FileCheckBox;
    private javax.swing.JComboBox<String> JoinComboBox;
    private javax.swing.JLabel JoinLabel;
    private javax.swing.JLabel LogoLabel;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JPasswordField PasswordTextField;
    private javax.swing.JComboBox<String> Season1ComboBox;
    private javax.swing.JComboBox<String> Season2ComboBox;
    private javax.swing.JLabel SeasonLabel;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JComboBox<String> Table1ComboBox;
    private javax.swing.JComboBox<String> Table2ComboBox;
    private javax.swing.JLabel TableLabel;
    private javax.swing.JLabel TeamNameLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JTextField UsernameTextField;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}
