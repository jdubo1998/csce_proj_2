import java.awt.Image;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.text.StyledDocument;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Locale;
import java.lang.StringBuilder;
import java.util.LinkedHashSet;
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
public class dbGuiV2 extends javax.swing.JFrame {

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
    String[] stadiumColumns = { "stadium code", "name", "city", "state", "capacity", "surface", "year opened",
            "season" };
    String[] teamColumns = { "team code", "name", "conference code", "season" };
    String[] team_game_statisticsColumns = { "team code", "game code", "rush att", "rush yard", "rush td", "pas att",
            "pass comp", "pass yard", "pass td", "pass int", "pass conb", "kickoff ret", "kickoff ret yard",
            "kickoff ret td", "punt reg", "punt ret yard", "punt ret td", "fum ret", "fum ret yard", "fum ret td",
            "int ret", "int ret yard", "int ret td", "misc ret", "misc ret yard", "misc ret td", "field goal att",
            "field goal made", "off xp kick att", "off xp kick made", "off 2xp kick att", "off 2xp kick made",
            "def 2xp att", "def 2xp made", "safety", "points", "punt", "punt yard", "kickoff", "kickoff yard",
            "kickoff touchback", "kickoff out-of-bounds", "kickoff onside", "fumble", "fumble lost", "tackle solo",
            "tackle assist", "tackle for loss", "tackle for loss yard", "sack", "sack yard", "qb hurry",
            "fumble forced", "pass broken up", "kick/punt blocked", "1st down rush", "1st down pass",
            "1st down penalty", "time of possession", "penalty", "penalty yard", "third down att", "third down conv",
            "fourth down att", "fourth down conv", "red zone att", "red zone td", "red zone field goal", "season",
            "row number" };
    String[] teamNames = {"Abilene Christian","Air Force","Akron","Alabama","Alabama A&M","Alabama St.","Albany (N.Y.)",
            "Alcorn St.","Appalachian State","Arizona","Arizona State","Arkansas","Arkansas-Pine Bluff","Arkansas State",
            "Army","Auburn","Austin Peay","Ball State","Baylor","Bethune-Cookman","Boise State","Boston College",
            "Bowling Green","Brown","Bryant","Bucknell","Buffalo","Butler","BYU","California","Cal Poly","Campbell",
            "Central Arkansas","Central Conn. St.","Central Michigan","Charleston So.","Chattanooga","Cincinnati","Citadel",
            "Clemson","Coastal Carolina","Colgate","Colorado","Colorado State","Columbia","Connecticut","Cornell",
            "Dartmouth","Davidson","Dayton","Delaware","Delaware State","Drake","Duke","Duquesne","East Carolina",
            "Eastern Illinois","Eastern Kentucky","Eastern Michigan","Eastern Washington","Elon","Florida","Florida A&M",
            "Florida Atlantic","Florida International","Florida State","Fordham","Fresno State","Furman","Gardner-Webb",
            "Georgetown","Georgia","Georgia Southern","Georgia State","Georgia Tech","Grambling","Hampton","Harvard",
            "Hawai'i","Holy Cross","Houston","Howard","Idaho","Idaho State","Illinois","Illinois State","Indiana",
            "Indiana State","Iowa","Iowa State","Jackson State","Jacksonville","Jacksonville State","James Madison",
            "Kansas","Kansas State","Kent State","Kentucky","Lafayette","Lamar","Lehigh","Liberty","Louisiana-Lafayette",
            "Louisiana-Monroe","Louisiana Tech","Louisville","LSU","Maine","Marist","Marshall","Maryland","Massachusetts",
            "McNeese State","Memphis","Miami (Florida)","Miami (Ohio)","Michigan","Michigan State","Middle Tennessee",
            "Minnesota","Mississippi","Mississippi State","Mississippi Valley State","Missouri","Missouri State","Monmouth",
            "Montana","Montana State","Morehead State","Morgan State","Murray State","Navy","N.C. Central","Nebraska",
            "Nevada","New Hampshire","New Mexico","New Mexico State","Nicholls State","Norfolk State","North Carolina",
            "North Carolina A&T","North Carolina State","North Dakota","North Dakota State","Northern Arizona","Northern Colorado",
            "Northern Illinois","Northern Iowa","North Texas","Northwestern","Northwestern State","Notre Dame","Ohio",
            "Ohio State","Oklahoma","Oklahoma State","Old Dominion","Oregon","Oregon State","Penn State","Pennsylvania",
            "Pittsburgh","Portland State","Prairie View A&M","Presbyterian","Princeton","Purdue","Rhode Island","Rice","Richmond",
            "Robert Morris","Rutgers","Sacramento State","Sacred Heart","Samford","Sam Houston State","San Diego","San Diego State",
            "San Jose State","Savannah State","SMU","South Alabama","South Carolina","South Carolina State","South Dakota",
            "South Dakota State","Southeastern Louisiana","Southeast Missouri State","Southern Illinois","Southern Mississippi",
            "Southern University","Southern Utah","South Florida","Stanford","Stephen F. Austin","St. Francis (Pa.)","Stony Brook",
            "Syracuse","TCU","Temple","Tennessee","Tennessee-Martin","Tennessee State","Tennessee Tech","Texas","Texas A&M",
            "Texas Southern","Texas State","Texas Tech","Toledo","Towson","Troy","Tulane","Tulsa","UAB","UC Davis","UCF","UCLA",
            "UNLV","USC","Utah","Utah State","UTEP","UTSA","Valparaiso","Vanderbilt","Villanova","Virginia","Virginia Military",
            "Virginia Tech","Wagner","Wake Forest","Washington","Washington State","Weber State","Western Carolina","Western Illinois",
            "Western Kentucky","Western Michigan","West Virginia","William & Mary","Wisconsin","Wofford","Wyoming","Yale","Youngstown State"
    };

    // String [] commonElements;
    ArrayList<String> commonElements = new ArrayList<String>();
    String[] JoinArray;

    /**
     * Creates new form dbGui
     */
    public dbGuiV2() {
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
        TableLabel1 = new javax.swing.JLabel();
        Table1ComboBox1 = new javax.swing.JComboBox<>();
        ColumnLabel1 = new javax.swing.JLabel();
        Column1ComboBox1 = new javax.swing.JComboBox<>();
        UsernameTextField = new javax.swing.JTextField();
        PasswordTextField = new javax.swing.JPasswordField();
        UsernameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DatabaseOutput = new javax.swing.JTextPane();
        Logo = new javax.swing.JLabel();
        TeamNameLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        TableLabel = new javax.swing.JLabel();
        DefaultTableBox1 = new javax.swing.JComboBox<>();
        DefaultTableBox2 = new javax.swing.JComboBox<>();
        ColumnLabel = new javax.swing.JLabel();
        DefaultColumnBox1 = new JCheckComboBox();
        DefaultColumnBox2 = new JCheckComboBox();
        SeasonLabel = new javax.swing.JLabel();
        DefaultSeasonBox = new javax.swing.JComboBox<>();
        JoinLabel = new javax.swing.JLabel();
        DefaultJoinBox = new javax.swing.JComboBox<>();
        DefaultCheckBox = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        TableLabel2 = new javax.swing.JLabel();
        Q1Box1 = new javax.swing.JComboBox<>();
        ColumnLabel2 = new javax.swing.JLabel();
        Q1Box2 = new javax.swing.JComboBox<>();
        TableLabel3 = new javax.swing.JLabel();
        TableLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        TableLabel5 = new javax.swing.JLabel();
        TableLabel6 = new javax.swing.JLabel();
        TableLabel7 = new javax.swing.JLabel();
        ColumnLabel3 = new javax.swing.JLabel();
        Q2TextField1 = new javax.swing.JTextField();
        Q2TextField2 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        TableLabel8 = new javax.swing.JLabel();
        TableLabel9 = new javax.swing.JLabel();
        TableLabel10 = new javax.swing.JLabel();
        Q3Box = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        TableLabel11 = new javax.swing.JLabel();
        TableLabel12 = new javax.swing.JLabel();
        TableLabel13 = new javax.swing.JLabel();
        Q4Box = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        TableLabel14 = new javax.swing.JLabel();
        TableLabel15 = new javax.swing.JLabel();
        TableLabel16 = new javax.swing.JLabel();
        BonusBox = new javax.swing.JComboBox<>();
        FileCheckBox = new javax.swing.JCheckBox();
        SubmitButton = new javax.swing.JButton();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("jCheckBoxMenuItem3");

        jCheckBoxMenuItem4.setSelected(true);
        jCheckBoxMenuItem4.setText("jCheckBoxMenuItem4");

        TableLabel1.setText("Table");

        ImageIcon logoImage = new ImageIcon("resources//teamlogo.png");
        Logo.setIcon(new ImageIcon(logoImage.getImage().getScaledInstance(163, 163, Image.SCALE_SMOOTH)));

        jTabbedPane1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                TabbedChanged(e);
            }
        });

        Table1ComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(conferenceColumns));
        Table1ComboBox1.setToolTipText("");
        Table1ComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Table1ComboBox1ActionPerformed(evt);
            }
        });

        ColumnLabel1.setText("Column");

        Column1ComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(conferenceColumns));
        Column1ComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Column1ComboBox1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 204, 204));

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

        DatabaseOutput.setEditable(false);
        DatabaseOutput.setFont(new Font("monospaced", Font.PLAIN, 12));
        DatabaseOutput.setText("Please enter username and password, select options from above, and then hit submit.");
        jScrollPane1.setViewportView(DatabaseOutput);

        TeamNameLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        TeamNameLabel.setText("College Football Statistics Database");

        TableLabel.setText("Table");

        DefaultTableBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "conference", "drive", "game",
                "game_statistics", "kickoff", "kickoff_return", "pass", "play", "player", "player_game_statistics",
                "punt", "punt_return", "reception", "rush", "stadium", "team", "team_game_statistics" }));
        DefaultTableBox1.setToolTipText("");
        DefaultTableBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefaultTableBox1ActionPerformed(evt);
            }
        });

        DefaultTableBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "conference", "drive", "game",
                "game_statistics", "kickoff", "kickoff_return", "pass", "play", "player", "player_game_statistics",
                "punt", "punt_return", "reception", "rush", "stadium", "team", "team_game_statistics" }));
        DefaultTableBox2.setToolTipText("");
        DefaultTableBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefaultTableBox2ActionPerformed(evt);
            }
        });

        ColumnLabel.setText("Column");

        DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "conference code", "subdivision", "name", "season" }));
        DefaultColumnBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefaultColumnBox1ActionPerformed(evt);
            }
        });

        DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "conference code", "subdivision", "name", "season" }));
        DefaultColumnBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefaultColumnBox2ActionPerformed(evt);
            }
        });

        SeasonLabel.setText("Season");

        DefaultSeasonBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013" }));
        DefaultSeasonBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefaultSeasonBoxActionPerformed(evt);
            }
        });

        JoinLabel.setText("Join by:");

        DefaultJoinBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefaultJoinBoxActionPerformed(evt);
            }
        });

        DefaultCheckBox.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(DefaultCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DefaultTableBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DefaultTableBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TableLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DefaultColumnBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ColumnLabel)
                    .addComponent(DefaultColumnBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(SeasonLabel)
                        .addComponent(DefaultSeasonBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(JoinLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DefaultJoinBox, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ColumnLabel)
                            .addComponent(SeasonLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DefaultColumnBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DefaultSeasonBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DefaultColumnBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JoinLabel)
                            .addComponent(DefaultJoinBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(TableLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DefaultTableBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DefaultTableBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DefaultCheckBox))
                        .addGap(19, 19, 19))))
        );

        jTabbedPane1.addTab("Default", jPanel1);

        TableLabel2.setText("Victory Chain:");

        Q1Box1.setModel(new javax.swing.DefaultComboBoxModel<>(teamNames));
        Q1Box1.setToolTipText("");
        Q1Box1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Q1Box1ActionPerformed(evt);
            }
        });

        ColumnLabel2.setText("Team 2");

        Q1Box2.setModel(new javax.swing.DefaultComboBoxModel<>(teamNames));
        Q1Box2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Q1Box2ActionPerformed(evt);
            }
        });

        TableLabel3.setText("Team 1");

        TableLabel4.setText("A chain that gives you bragging rights of how the Team 1 is better than Team 2.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Q1Box1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TableLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Q1Box2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ColumnLabel2))
                        .addGap(264, 264, 264))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TableLabel2)
                            .addComponent(TableLabel4))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ColumnLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Q1Box2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(TableLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Q1Box1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Q1", jPanel2);

        TableLabel5.setText("Shortest Chain:");

        TableLabel6.setText("A chain that is the shortest relation between Player 1 and Player 2.");

        TableLabel7.setText("Player 1");

        ColumnLabel3.setText("Player 2");

        Q2TextField1.setText("Player 1");
        Q2TextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Q2TextField1ActionPerformed(evt);
            }
        });

        Q2TextField2.setText("Player 2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(TableLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                        .addComponent(ColumnLabel3)
                        .addGap(343, 343, 343))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TableLabel5)
                            .addComponent(TableLabel6))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Q2TextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Q2TextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(262, 262, 262))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(ColumnLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Q2TextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(TableLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Q2TextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );

        jTabbedPane1.addTab("Q2", jPanel3);

        TableLabel8.setText("Rushing Yards:");

        TableLabel9.setText("Returns the opponent of a given team which had the most rushing yards.");

        TableLabel10.setText("Team");

        Q3Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "conference" }));
        Q3Box.setToolTipText("");
        Q3Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Q3BoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Q3Box, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TableLabel10))
                        .addGap(264, 437, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TableLabel8)
                            .addComponent(TableLabel9))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TableLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Q3Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Q3", jPanel4);

        TableLabel11.setText("Home Field Advantage:");

        TableLabel12.setText("Rates each team in a conference win playing at Home. 100 is Best and 0 is worst.");

        TableLabel13.setText("Conference");

        Q4Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "conference" }));
        Q4Box.setToolTipText("");
        Q4Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Q4BoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Q4Box, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TableLabel13))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TableLabel11)
                            .addComponent(TableLabel12))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TableLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Q4Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Q4", jPanel5);

        TableLabel14.setText("HomeTown:");

        TableLabel15.setText("Given a Filter, this returns the HomeTown that is highest rating. ");

        TableLabel16.setText("Filter");

        BonusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "players", "winners" }));
        BonusBox.setToolTipText("");
        BonusBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BonusBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BonusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TableLabel16))
                        .addGap(264, 437, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TableLabel14)
                            .addComponent(TableLabel15))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BonusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bonus", jPanel6);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SubmitButton)
                                    .addComponent(FileCheckBox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(UsernameLabel)
                                    .addComponent(PasswordLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TeamNameLabel)
                                .addGap(70, 70, 70)))
                        .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TeamNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(FileCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SubmitButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UsernameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PasswordLabel)
                            .addComponent(PasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

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
        // Useful arrays and strings
        String[] tables = new String[2];
        String[] columns;
        String joinBy;

        // Username and Password holders
        String username;
        String password;
        
        username = UsernameTextField.getText();
        password = String.copyValueOf(PasswordTextField.getPassword());
        // set up a dbConnect object to talk to the database
        dbConnect conn = new dbConnect(username, password);

        // Start formatting the output in an even way
        StringBuilder output = new StringBuilder();
        Formatter formated = new Formatter(output, Locale.US);

        switch(jTabbedPane1.getSelectedIndex()) {
            case (0):
                // set up a query to use and what columns to use
                String[] col1 = DefaultColumnBox1.getChecked();
                String[] col2 = DefaultColumnBox2.getChecked();

                columns = new String[col1.length + col2.length];

                for (int i = 0; i < col1.length; i++) {
                    columns[i] = col1[i];
                }
                for (int i = 0; i < col2.length; i++) {
                    columns[i + col1.length] = col2[i];
                }

                joinBy = DefaultJoinBox.getSelectedItem().toString();

                tables[0] = DefaultTableBox1.getSelectedItem().toString();
                tables[1] = DefaultTableBox2.getSelectedItem().toString();

                Questions.normal(formated, conn, tables, columns, joinBy, col1.length,
                        DefaultSeasonBox.getSelectedItem().toString());
                break;
            case (1):
                Questions.q1(formated, conn, Q1Box1.getSelectedItem().toString(), Q1Box2.getSelectedItem().toString());
                // DatabaseOutput.setText(Questions.q1(conn, Q1Box1.getSelectedItem().toString(), Q1Box2.getSelectedItem().toString()));
                break;
            case (2):
                formated.format("Q2 not implemented yet");
                break;
            case (3):
                Questions.q3(formated, conn, Q3Box.getSelectedItem().toString());
                break;
            case (4):
                formated.format("Q4 not implemented yet");
                break;
            case (5):
                Questions.q5(formated, conn, BonusBox.getSelectedItem().toString());
                break;
            default:
                System.out.println("How did you get here?");
                break;


        }

        formated.close();
        // end formating and output
        if (FileCheckBox.isSelected()) {
            try {
                File out = new File("output.txt");
                FileWriter outWrite = new FileWriter("output.txt");

                outWrite.write(output.toString());

                outWrite.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        DatabaseOutput.setText("");
        StyledDocument doc = DatabaseOutput.getStyledDocument();

        try {
            doc.insertString(0, output.toString(), null);
        } catch (Exception e) {
            System.out.println(e);
        }
    }                                            

    private void DefaultJoinBoxActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void DefaultSeasonBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void DefaultColumnBox2ActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        DefaultColumnBox2.setChecked(DefaultColumnBox2.getSelectedIndex());
    }                                                 

    private void DefaultColumnBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        DefaultColumnBox1.setChecked(DefaultColumnBox1.getSelectedIndex());
    }                                                 

    private void DefaultTableBox2ActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        if ("conference".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(conferenceColumns));
        } else if ("drive".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(driveColumns));
        } else if ("game".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(gameColumns));
        } else if ("game_statistics".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(game_statisticsColumns));
        } else if ("kickoff".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(kickoffColumns));
        } else if ("kickoff_return".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(kickoff_returnColumns));
        } else if ("pass".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(passColumns));
        } else if ("play".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(playColumns));
        } else if ("player".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(playerColumns));
        } else if ("player_game_statistics".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(player_game_statisticsColumns));
        } else if ("punt".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(puntColumns));
        } else if ("punt_return".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(punt_returnColumns));
        } else if ("reception".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(receptionColumns));
        } else if ("rush".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(rushColumns));
        } else if ("stadium".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(stadiumColumns));
        } else if ("team".equals(DefaultTableBox2.getSelectedItem())) {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(teamColumns));
        } else {
            DefaultColumnBox2.setModel(new javax.swing.DefaultComboBoxModel<>(team_game_statisticsColumns));
        }

        DefaultColumnBox2.setCheckedSize();

        for (int i = 0; i < DefaultColumnBox1.getItemCount(); i++) {
            for (int j = 0; j < DefaultColumnBox2.getItemCount(); j++) {
                if (DefaultColumnBox1.getItemAt(i) == DefaultColumnBox2.getItemAt(j)) {
                    DefaultJoinBox.addItem((String) DefaultColumnBox1.getItemAt(i));
                    break;
                }
            }
        }
    }                                                

    private void DefaultTableBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        if ("conference".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(conferenceColumns));
        } else if ("drive".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(driveColumns));
        } else if ("game".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(gameColumns));
        } else if ("game_statistics".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(game_statisticsColumns));
        } else if ("kickoff".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(kickoffColumns));
        } else if ("kickoff_return".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(kickoff_returnColumns));
        } else if ("pass".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(passColumns));
        } else if ("play".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(playColumns));
        } else if ("player".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(playerColumns));
        } else if ("player_game_statistics".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(player_game_statisticsColumns));
        } else if ("punt".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(puntColumns));
        } else if ("punt_return".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(punt_returnColumns));
        } else if ("reception".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(receptionColumns));
        } else if ("rush".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(rushColumns));
        } else if ("stadium".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(stadiumColumns));
        } else if ("team".equals(DefaultTableBox1.getSelectedItem())) {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(teamColumns));
        } else {
            DefaultColumnBox1.setModel(new javax.swing.DefaultComboBoxModel<>(team_game_statisticsColumns));
        }

        DefaultColumnBox1.setCheckedSize();
    }                                                

    private void Table1ComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void Column1ComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void Q1Box1ActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void Q1Box2ActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void Q3BoxActionPerformed(java.awt.event.ActionEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void Q4BoxActionPerformed(java.awt.event.ActionEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void BonusBoxActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void Q2TextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }
    
    private void TabbedChanged(ChangeEvent e) {
        // Username and Password holders
        String username;
        String password;

        username = UsernameTextField.getText();
        password = String.copyValueOf(PasswordTextField.getPassword());
        // set up a dbConnect object to talk to the database

        if (!username.isEmpty()) {
            dbConnect conn = new dbConnect(username, password);

            /* Start setting up the question 3 input box */
            String[] teams = conn.sendQuery("SELECT name FROM team", new String[] { "name" });
            teams = teams[0].split("\n");

            LinkedHashSet<String> removeDups = new LinkedHashSet<>(Arrays.asList(teams));

            teams = removeDups.toArray(new String[] {});

            Q3Box.setModel(new javax.swing.DefaultComboBoxModel<>(teams));
            /* Finish setting up the question 3 input box */
        }
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
            java.util.logging.Logger.getLogger(dbGuiV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dbGuiV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dbGuiV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dbGuiV2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dbGuiV2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JComboBox<String> BonusBox;
    private javax.swing.JComboBox<String> Column1ComboBox1;
    private javax.swing.JLabel ColumnLabel;
    private javax.swing.JLabel ColumnLabel1;
    private javax.swing.JLabel ColumnLabel2;
    private javax.swing.JLabel ColumnLabel3;
    private javax.swing.JTextPane DatabaseOutput;
    private javax.swing.JCheckBox DefaultCheckBox;
    private JCheckComboBox DefaultColumnBox1;
    private JCheckComboBox DefaultColumnBox2;
    private javax.swing.JComboBox<String> DefaultJoinBox;
    private javax.swing.JComboBox<String> DefaultSeasonBox;
    private javax.swing.JComboBox<String> DefaultTableBox1;
    private javax.swing.JComboBox<String> DefaultTableBox2;
    private javax.swing.JCheckBox FileCheckBox;
    private javax.swing.JLabel JoinLabel;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JPasswordField PasswordTextField;
    private javax.swing.JComboBox<String> Q1Box1;
    private javax.swing.JComboBox<String> Q1Box2;
    private javax.swing.JTextField Q2TextField1;
    private javax.swing.JTextField Q2TextField2;
    private javax.swing.JComboBox<String> Q3Box;
    private javax.swing.JComboBox<String> Q4Box;
    private javax.swing.JLabel SeasonLabel;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JComboBox<String> Table1ComboBox1;
    private javax.swing.JLabel TableLabel;
    private javax.swing.JLabel TableLabel1;
    private javax.swing.JLabel TableLabel10;
    private javax.swing.JLabel TableLabel11;
    private javax.swing.JLabel TableLabel12;
    private javax.swing.JLabel TableLabel13;
    private javax.swing.JLabel TableLabel14;
    private javax.swing.JLabel TableLabel15;
    private javax.swing.JLabel TableLabel16;
    private javax.swing.JLabel TableLabel2;
    private javax.swing.JLabel TableLabel3;
    private javax.swing.JLabel TableLabel4;
    private javax.swing.JLabel TableLabel5;
    private javax.swing.JLabel TableLabel6;
    private javax.swing.JLabel TableLabel7;
    private javax.swing.JLabel TableLabel8;
    private javax.swing.JLabel TableLabel9;
    private javax.swing.JLabel TeamNameLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JTextField UsernameTextField;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration                   
}
