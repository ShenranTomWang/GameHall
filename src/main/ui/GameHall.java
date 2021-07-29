package ui;

import exceptions.InvalidChoiceRockPaperSizerException;
import model.*;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.swing.table.DefaultTableModel;

//This class is the app for GameHall, allows players to interact with the system
public class GameHall implements ActionListener, Observer {
    private static final String RPS_COMMAND = "RPS";
    private static final String GN_COMMAND = "GN";
    private static final String INFO_COMMAND = "info";
    private static final String SAVE_COMMAND = "save";
    private static final String LOAD_COMMAND = "load";
    private static final String NEW_COMMAND = "new";
    private static final String JSON_STORE = "./data/user.json";
    private static final String SOUND_ADDRESS = "./data/buttonClickedSound.wav";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private User user;
    private int questionsLeft;
    private GuessingNumber guessingNumber;
    private RockPaperSizer rps;

    //load or new frame
    private JFrame loadOrNewFrame;
    private JPanel loadOrNewPanel;
    private JButton newUserButton;
    private JButton loadUserButton;
    private JLabel loadOrNewLabel;

    //new user frame
    private JFrame newUserFrame;
    private JPanel newUserPanel;
    private JTextField newUserName;
    private JLabel newUserLabel;
    private JButton createNewUserBtn;

    //load user success frame
    private JFrame loadUserSuccessFrame;
    private JPanel loadUserSuccessPanel;
    private JLabel loadUserSuccessLabel;
    private JButton confirmLoadBtn;

    //load user fail frame
    private JFrame loadUserFailFrame;
    private JPanel loadUserFailPanel;
    private JLabel loadUserFailLabel;
    private JButton loadFBtn;

    //main frame
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel mainLabel;
    private JButton btnRPS;
    private JButton gn;
    private JButton save;
    private JButton info;
    private JLabel savedLabel;

    //RPS frame
    private JFrame frameRPS;
    private JPanel panelRPS;
    private JLabel labelRPS;
    private JButton rockButton;
    private JButton paperButton;
    private JButton sizerButton;

    //RPS result frame
    private JFrame frameResultRPS;
    private JPanel panelResultRPS;
    private JLabel labelPlayAgainRPS;
    private JButton againButtonRPS;
    private JButton exitButtonRPS;

    //add to favourite frame RPS
    private JFrame addToFavFrameRPS;
    private JPanel panelAddToFavRPS;
    private JLabel addToFavLabelRPS;
    private JLabel labelResultRPS;
    private JLabel labelWinLoseDrawRPS;
    private JButton addToFavButtonRPS;
    private JButton notAddToFavButtonRPS;

    //GN frame
    private JFrame frameGN;
    private JPanel panelGN;
    private JLabel instruction1GNLabel;
    private JLabel instruction2GNLabel;
    private JLabel instruction3GNLabel;
    private JLabel instruction4GNLabel;
    private JLabel instruction5GNLabel;
    private JLabel instruction6GNLabel;
    private JLabel questionsLeftLabel;
    private JLabel warningGNLabel;
    private JLabel answerGNLabel;
    private JTextField questionChoice;
    private JTextField numChoice;
    private JButton submitQuestionButtonGN;

    //GN result frame
    private JFrame frameGNResult;
    private JPanel panelGNResult;
    private JLabel labelResultGN;
    private JLabel labelWinLoseDrawGN;
    private JLabel addToFavLabelGN;
    private JButton addToFavButtonGN;
    private JButton notAddToFavButtonGN;

    //GN last try frame
    private JFrame frameGNLastTry;
    private JPanel panelGNLastTry;
    private JLabel labelGNLastTry;
    private JLabel warningGNLastTryLabel;
    private JLabel lastQuestionAnswer;
    private JTextField numGNLastTry;
    private JButton submitGNLastTry;

    //info frame
    private JFrame infoFrame;
    private JPanel infoPanel;
    private JLabel infoUserLabel;
    private JLabel infoPointsLabel;
    private JLabel infoGamesPlayedLabel;
    private JLabel infoWinRateLabel;
    private JButton infoBackButton;
    private JButton viewFavBtn;

    //fav frame
    private JFrame favFrame;
    private JButton favFrameBackBtn;
    private JButton seeOnlyWinsBtn;
    private JTable infoFavTable;
    private JScrollPane infoFavScrollPane;
    private DefaultTableModel infoFavTableModel;

    //onlyWins frame
    private JFrame onlyWinsFrame;
    private JButton onlyWinsFrameBackBtn;
    private JTable onlyWinsTable;
    private JScrollPane onlyWinsScrollPane;
    private DefaultTableModel onlyWinsTableModel;

    //EFFECTS: call runGameHall
    public GameHall() {
        initializeFields();
        initializeLoadOrNewGUI();
        initializeNewUserGUI();
        initializeLoadUserSuccessGUI();
        initializeLoadUserFailGUI();
        initializeMainGUI();
        initializeRockPaperSizerGUI();
        initializeFrameResultRPS();
        initializeAddToFavFrameRPS();
        initializeFrameGN();
        initializeFrameGNResult();
        initializeFrameGNLastTry();
        initializeInfoFrame();
        initializeFavFrame();
        initializeOnlyWinsFrame();

        loadOrNewFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: initialize fields
    public void initializeFields() {
        user = new User("user");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //REQUIRES: stringInput is one of NEW_COMMAND or LOAD_COMMAND
    //MODIFIES: this
    //EFFECTS: ask user to create new user if stringInput is NEW_COMMAND, or load data if
    //         stringInput is LOAD_COMMAND
    public void processLoadOrNewCommand(String stringInput) {
        if (stringInput.equals(NEW_COMMAND)) {
            newUserFrame.setVisible(true);
        } else if (stringInput.equals(LOAD_COMMAND)) {
            loadData();
            initializeFavourites();
            initializeOnlyWinsFavourites();
        }
    }

    //MODIFIES: this
    //EFFECTS: react to commands
    public void processCommand(String command) {
        if (command.equals(GN_COMMAND)) {
            guessingNumber = new GuessingNumber();
            guessingNumber.genCompNum();
            frameGN.setVisible(true);
        } else if (command.equals(RPS_COMMAND)) {
            frameRPS.setVisible(true);
        } else if (command.equals(INFO_COMMAND)) {
            updateInfo();
            infoFrame.setVisible(true);
        } else if (command.equals(SAVE_COMMAND)) {
            saveData();
        }
    }

    //MODIFIES: this
    //EFFECTS: saves the user data to Json file located at JSON_STORE
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            savedLabel.setText("Saved " + user.getName() + "'s data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            savedLabel.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads user from Json file located at JSON_STORE. Ask user to create
    //         new account if unable to load from file.
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void loadData() {
        try {
            user = jsonReader.read();
            user.addObserver(this);
            loadUserSuccessFrame.setVisible(true);
        } catch (IOException | JSONException e) {
            loadUserSuccessFrame.setVisible(false);
            loadUserFailFrame.setVisible(true);
        }
    }

    //REQUIRES: manQuestionChoice is one of 1, 2, or 3
    //MODIFIES: this
    //EFFECTS: answers questions and returns false if question is 1 or 2, otherwise returns true
    public boolean answerQuestion(int manQuestionChoice) {
        if (manQuestionChoice == 1) {
            if (guessingNumber.isCompNumGreater()) {
                respond("Answer to last question: Yes");
            } else {
                respond("Answer to last question: No");
            }
        } else if (manQuestionChoice == 2) {
            if (guessingNumber.isCompNumSmaller()) {
                respond("Answer to last question: Yes");
            } else {
                respond("Answer to last question: No");
            }
        } else {
            if (guessingNumber.isCompNumEqual()) {
                setManWin(guessingNumber);
            } else {
                setCompWin(guessingNumber);
            }
            frameGN.setVisible(false);
            frameGNResult.setVisible(true);
            return true;
        }
        return false;
    }

    public void respond(String s) {
        answerGNLabel.setText(s);
        lastQuestionAnswer.setText(s);
    }

    //REQUIRES: result is one of USER, DRAW, or COMPUTER
    //MODIFIES: this
    //EFFECTS: determine winner between computer and player
    public void processRPS(String result) {
        addToFavFrameRPS.setVisible(true);
        labelResultRPS.setText("You choose " + rps.getManChoice() + " and the computer choose " + rps.getCompChoice());
        if (result.equals(RockPaperSizer.USER)) {
            setManWin(rps);
        } else if (result.equals(RockPaperSizer.DRAW)) {
            setDraw(rps);
        } else if (result.equals(RockPaperSizer.COMPUTER)) {
            setCompWin(rps);
        }
    }

    //MODIFIES: game
    //EFFECTS: print "Congratulations! You defeated the computer!" and add1 to manWin, user points and numberOfGames
    public void setManWin(Game game) {
        if (game.getType().equals("Rock Paper Sizer")) {
            labelWinLoseDrawRPS.setText("Congratulations! You defeated the computer!");
        } else if (game.getType().equals("Guessing Number")) {
            labelWinLoseDrawGN.setText("Congratulations! You defeated the computer!");
        }
        game.addManWin(1);
        user.addNumberOfGames(1);
        user.addPoints(1);
    }

    //MODIFIES: game
    //EFFECTS: print "Draw!" and add1 to both manWin and compWin and user numberOfGames
    public void setDraw(Game game) {
        if (game.getType().equals("Rock Paper Sizer")) {
            labelWinLoseDrawRPS.setText("Draw!");
        } else if (game.getType().equals("Guessing Number")) {
            labelWinLoseDrawGN.setText("Draw");
        }
        user.addNumberOfGames(1);
        game.addCompWin(1);
        game.addManWin(1);
    }

    //MODIFIES: game
    //EFFECTS: print "The computer won!" and add1 to compWin and user numberOfGames
    public void setCompWin(Game game) {
        if (game.getType().equals("Rock Paper Sizer")) {
            labelWinLoseDrawRPS.setText("The computer won!");
        } else if (game.getType().equals("Guessing Number")) {
            labelWinLoseDrawGN.setText("The computer won!");
        }
        game.addCompWin(1);
        user.addNumberOfGames(1);
    }

    //EFFECTS: update infoFrame to print info
    public void updateInfo() {
        infoUserLabel.setText("User: " + user.getName());
        infoPointsLabel.setText("Player points: " + user.getPoints());
        infoGamesPlayedLabel.setText("Player games played: " + user.getNumberOfGames());
        infoWinRateLabel.setText("Player win rate: " + user.winRate() + "%");
    }

    //EFFECTS: display favourites in infoFavTable
    public void initializeFavourites() {
        for (Game g : user.getFavouriteRounds()) {
            if (g.getCompWin() == 1 && g.getManWin() == 0) {
                infoFavTableModel.addRow(new String[]{g.getType(), "Computer won"});
            } else if (g.getManWin() == 1 && g.getCompWin() == 0) {
                infoFavTableModel.addRow(new String[]{g.getType(), "User won"});
            } else if (g.getManWin() == 1 && g.getCompWin() == 1) {
                infoFavTableModel.addRow(new String[]{g.getType(), "Draw"});
            }
        }
    }

    //EFFECTS: display only wins in favourites in onlyWinsTable
    public void initializeOnlyWinsFavourites() {
        for (Game g : user.getFavouriteRounds()) {
            if (g.getManWin() == 1 && g.getCompWin() == 0) {
                onlyWinsTableModel.addRow(new String[]{g.getType(), "User won"});
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: initialize "new or load" GUI frame
    public void initializeLoadOrNewGUI() {
        loadOrNewFrameSetUp();
        loadOrNewFrameElementsSetUp();
        loadOrNewPanel.add(newUserButton);
        loadOrNewPanel.add(loadUserButton);
        loadOrNewPanel.add(loadOrNewLabel);
        loadOrNewFrame.add(loadOrNewPanel, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in loadOrNewFrame
    public void loadOrNewFrameElementsSetUp() {
        loadOrNewPanel = new JPanel();
        newUserButton = new JButton("new user");
        newUserButton.setBounds(50, 50, 90, 25);
        newUserButton.addActionListener(this);
        loadUserButton = new JButton("load data");
        loadUserButton.setBounds(150, 50, 90, 25);
        loadUserButton.addActionListener(this);
        loadOrNewLabel = new JLabel("Choose to load data or create new player");
        loadOrNewLabel.setBounds(10, 10, 300, 25);
        loadOrNewPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loadOrNewPanel.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: set up loadOrNewFrame
    public void loadOrNewFrameSetUp() {
        loadOrNewFrame = new JFrame("GameHall app");
        loadOrNewFrame.setLayout(new BorderLayout());
        loadOrNewFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        loadOrNewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadOrNewFrame.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: initialize "new user" GUI frame
    public void initializeNewUserGUI() {
        newUserFrameSetUp();
        newUserFrameElementsSetUp();
        newUserPanel.add(newUserLabel);
        newUserPanel.add(newUserName);
        newUserPanel.add(createNewUserBtn);
        newUserFrame.add(newUserPanel, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in newUserFrame
    public void newUserFrameElementsSetUp() {
        newUserPanel = new JPanel();
        createNewUserBtn = new JButton("OK");
        createNewUserBtn.setBounds(270, 90, 100, 30);
        createNewUserBtn.addActionListener(this);
        newUserName = new JTextField();
        newUserName.setBounds(20, 50, 300, 25);
        newUserLabel = new JLabel("Please enter user's name: ");
        newUserLabel.setBounds(10, 10, 300, 25);
        newUserPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newUserPanel.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: set up newUserFrame
    public void newUserFrameSetUp() {
        newUserFrame = new JFrame("GameHall app");
        newUserFrame.setLayout(new BorderLayout());
        newUserFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        newUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newUserFrame.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: initialize "load user success" GUI frame
    public void initializeLoadUserSuccessGUI() {
        loadUserSuccessFrameSetUp();
        loadUserSuccessFrameElementsSetUp();
        loadUserSuccessPanel.add(confirmLoadBtn);
        loadUserSuccessPanel.add(loadUserSuccessLabel);
        loadUserSuccessFrame.add(loadUserSuccessPanel, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in loadUserFrame
    public void loadUserSuccessFrameElementsSetUp() {
        loadUserSuccessPanel = new JPanel();
        confirmLoadBtn = new JButton("OK");
        confirmLoadBtn.setBounds(470, 180, 100, 30);
        confirmLoadBtn.addActionListener(this);
        loadUserSuccessLabel = new JLabel("Loaded data from " + JSON_STORE);
        loadUserSuccessLabel.setBounds(10, 10, 900, 25);
        loadUserSuccessPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loadUserSuccessPanel.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: set up loadUserFrame
    public void loadUserSuccessFrameSetUp() {
        loadUserSuccessFrame = new JFrame("GameHall app");
        loadUserSuccessFrame.setLayout(new BorderLayout());
        loadUserSuccessFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        loadUserSuccessFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadUserSuccessFrame.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: initialize "load user fail" GUI frame
    public void initializeLoadUserFailGUI() {
        loadUserFailFrameSetUp();
        loadUserFailElementsSetUp();
        loadUserFailPanel.add(loadFBtn);
        loadUserFailPanel.add(loadUserFailLabel);
        loadUserFailFrame.add(loadUserFailPanel, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up loadUserFailFrame
    public void loadUserFailFrameSetUp() {
        loadUserFailFrame = new JFrame("GameHall app");
        loadUserFailFrame.setLayout(new BorderLayout());
        loadUserFailFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        loadUserFailFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadUserFailFrame.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: set up elements of loadUserFailFrame
    public void loadUserFailElementsSetUp() {
        loadUserFailPanel = new JPanel();
        loadFBtn = new JButton("new user");
        loadFBtn.setBounds(470, 180, 100, 30);
        loadFBtn.addActionListener(this);
        loadUserFailLabel = new JLabel("Unable to read from file: " + JSON_STORE + " , please create a new user");
        loadUserFailLabel.setBounds(10, 10, 900, 25);
        loadUserFailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loadUserFailPanel.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: initialize "main" GUI frame
    public void initializeMainGUI() {
        mainFrameSetUp();
        mainFrameElementsSetUp();
        mainPanel.add(mainLabel);
        mainPanel.add(savedLabel);
        mainPanel.add(btnRPS);
        mainPanel.add(gn);
        mainPanel.add(save);
        mainPanel.add(info);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements of mainFrame
    public void mainFrameElementsSetUp() {
        mainPanel = new JPanel();
        btnRPS = new JButton("play rock paper sizer");
        btnRPS.addActionListener(this);
        btnRPS.setBounds(270, 60, 300, 60);
        gn = new JButton("play guessing number");
        gn.setBounds(270, 180, 300, 60);
        gn.addActionListener(this);
        save = new JButton("save data");
        save.setBounds(270, 300, 300, 60);
        save.addActionListener(this);
        info = new JButton("info");
        info.setBounds(270, 420, 300, 60);
        info.addActionListener(this);
        savedLabel = new JLabel();
        savedLabel.setBounds(350, 500, 300, 25);
        mainLabel = new JLabel("Please choose one of the following: ");
        mainLabel.setBounds(10, 10, 300, 25);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: set up mainFrame
    public void mainFrameSetUp() {
        mainFrame = new JFrame("GameHall app");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: initialize "rock paper sizer" GUI frame
    public void initializeRockPaperSizerGUI() {
        frameRockPaperSizerSetUp();
        frameRockPaperSizerElementsSetUp();
        panelRPS.add(labelRPS);
        panelRPS.add(rockButton);
        panelRPS.add(paperButton);
        panelRPS.add(sizerButton);
        frameRPS.add(panelRPS, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in frameRPS
    public void frameRockPaperSizerElementsSetUp() {
        panelRPS = new JPanel();
        rockButton = new JButton("rock");
        rockButton.setBounds(150, 60, 100, 30);
        rockButton.addActionListener(this);
        paperButton = new JButton("paper");
        paperButton.setBounds(270, 60, 100, 30);
        paperButton.addActionListener(this);
        sizerButton = new JButton("sizer");
        sizerButton.setBounds(390, 60, 100, 30);
        sizerButton.addActionListener(this);
        labelRPS = new JLabel("Pick your choice: ");
        labelRPS.setBounds(10, 10, 300, 25);
        panelRPS.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelRPS.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: set up frameRPS
    public void frameRockPaperSizerSetUp() {
        frameRPS = new JFrame("GameHall app");
        frameRPS.setLayout(new BorderLayout());
        frameRPS.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frameRPS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameRPS.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: initialize "rock paper sizer result" GUI frame
    public void initializeFrameResultRPS() {
        setUpFrameResultRPS();
        setUpElementsFrameResultRPS();
        panelResultRPS.add(againButtonRPS);
        panelResultRPS.add(exitButtonRPS);
        panelResultRPS.add(labelPlayAgainRPS);
        frameResultRPS.add(panelResultRPS, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up frameResultRPS
    public void setUpFrameResultRPS() {
        frameResultRPS = new JFrame("GameHall app");
        frameResultRPS.setLayout(new BorderLayout());
        frameResultRPS.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frameResultRPS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameResultRPS.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in frameResultRPS
    public void setUpElementsFrameResultRPS() {
        panelResultRPS = new JPanel();
        againButtonRPS = new JButton("yes");
        againButtonRPS.setBounds(270, 180, 100, 30);
        againButtonRPS.addActionListener(this);
        exitButtonRPS = new JButton("no");
        exitButtonRPS.setBounds(470, 180, 100, 30);
        exitButtonRPS.addActionListener(this);
        labelPlayAgainRPS = new JLabel("Play again?");
        labelPlayAgainRPS.setBounds(30, 150, 300, 25);
        panelResultRPS.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelResultRPS.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: initialize "result of GN" GUI frame
    public void initializeFrameGNResult() {
        frameGNResultSetUp();
        frameGNResultElementsSetUp();
        panelGNResult.add(labelResultGN);
        panelGNResult.add(labelWinLoseDrawGN);
        panelGNResult.add(addToFavButtonGN);
        panelGNResult.add(notAddToFavButtonGN);
        panelGNResult.add(addToFavLabelGN);
        frameGNResult.add(panelGNResult, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in frameGNResult
    private void frameGNResultElementsSetUp() {
        panelGNResult = new JPanel();
        addToFavButtonGN = new JButton("yes");
        addToFavButtonGN.setBounds(270, 180, 100, 30);
        addToFavButtonGN.addActionListener(this);
        notAddToFavButtonGN = new JButton("no");
        notAddToFavButtonGN.setBounds(470, 180, 100, 30);
        notAddToFavButtonGN.addActionListener(this);
        addToFavLabelGN = new JLabel("Add this round to favourite?");
        addToFavLabelGN.setBounds(10, 150, 300, 25);
        labelWinLoseDrawGN = new JLabel("");
        labelWinLoseDrawGN.setBounds(10, 70, 300, 25);
        labelResultGN = new JLabel("");
        labelResultGN.setBounds(10, 10, 300, 25);
        panelGNResult.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelGNResult.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: set up frameGNResult
    private void frameGNResultSetUp() {
        frameGNResult = new JFrame("GameHall app");
        frameGNResult.setLayout(new BorderLayout());
        frameGNResult.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frameGNResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGNResult.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: initialize "add to favourite" GUI frame
    public void initializeAddToFavFrameRPS() {
        addToFavFrameSetUpRPS();
        addToFavFrameElementsSetUpRPS();
        panelAddToFavRPS.add(labelResultRPS);
        panelAddToFavRPS.add(labelWinLoseDrawRPS);
        panelAddToFavRPS.add(addToFavButtonRPS);
        panelAddToFavRPS.add(notAddToFavButtonRPS);
        panelAddToFavRPS.add(addToFavLabelRPS);
        addToFavFrameRPS.add(panelAddToFavRPS, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in addToFavFrame
    private void addToFavFrameElementsSetUpRPS() {
        panelAddToFavRPS = new JPanel();
        addToFavButtonRPS = new JButton("yes");
        addToFavButtonRPS.setBounds(270, 180, 100, 30);
        addToFavButtonRPS.addActionListener(this);
        notAddToFavButtonRPS = new JButton("no");
        notAddToFavButtonRPS.setBounds(470, 180, 100, 30);
        notAddToFavButtonRPS.addActionListener(this);
        addToFavLabelRPS = new JLabel("Add this round to favourite?");
        addToFavLabelRPS.setBounds(10, 150, 300, 25);
        labelWinLoseDrawRPS = new JLabel("");
        labelWinLoseDrawRPS.setBounds(10, 70, 300, 25);
        labelResultRPS = new JLabel("");
        labelResultRPS.setBounds(10, 10, 300, 25);
        panelAddToFavRPS.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelAddToFavRPS.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: set up addToFavFrame
    private void addToFavFrameSetUpRPS() {
        addToFavFrameRPS = new JFrame("GameHall app");
        addToFavFrameRPS.setLayout(new BorderLayout());
        addToFavFrameRPS.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        addToFavFrameRPS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addToFavFrameRPS.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: initialize "guessing number game" GUI frame
    public void initializeFrameGN() {
        questionsLeft = GuessingNumber.MAX_QUESTIONS;
        frameGNSetUp();
        frameGNElementsSetUp();
        panelGN.add(submitQuestionButtonGN);
        panelGN.add(instruction1GNLabel);
        panelGN.add(instruction2GNLabel);
        panelGN.add(instruction3GNLabel);
        panelGN.add(instruction4GNLabel);
        panelGN.add(instruction5GNLabel);
        panelGN.add(instruction6GNLabel);
        panelGN.add(warningGNLabel);
        panelGN.add(answerGNLabel);
        panelGN.add(questionChoice);
        panelGN.add(numChoice);
        panelGN.add(questionsLeftLabel);
        frameGN.add(panelGN, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in frameGN
    public void frameGNElementsSetUp() {
        panelGN = new JPanel();
        submitQuestionButtonGN = new JButton("ok");
        submitQuestionButtonGN.setBounds(470, 400, 100, 30);
        submitQuestionButtonGN.addActionListener(this);
        labelGNSetUp();
        questionChoice = new JTextField();
        questionChoice.setBounds(30, 250, 300, 25);
        numChoice = new JTextField();
        numChoice.setBounds(30, 300, 300, 25);
        panelGN.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelGN.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: set up labels im frameGN
    public void labelGNSetUp() {
        initializeGNLabels();
        warningGNLabel.setBounds(10, 430, 500, 25);
        answerGNLabel.setBounds(10, 400, 500, 25);
        instruction1GNLabel.setText("The computer has chosen a number between 0 to 100. Guess what number it is!");
        instruction1GNLabel.setBounds(10, 10, 500, 25);
        instruction2GNLabel.setText("Please select one of your questions (1, 2 or 3): ");
        instruction2GNLabel.setBounds(10, 40, 300, 25);
        instruction3GNLabel.setText("1. Is this number greater than x?");
        instruction3GNLabel.setBounds(10, 70, 300, 25);
        instruction4GNLabel.setText("2. Is this number smaller than x?");
        instruction4GNLabel.setBounds(10, 100, 300, 25);
        instruction5GNLabel.setText("3. Is this number x?");
        instruction5GNLabel.setBounds(10, 130, 300, 25);
        instruction6GNLabel.setText("By selecting 3 (or numbers not 123), you will not be able to ask more questions.");
        instruction6GNLabel.setBounds(10, 160, 500, 25);
        questionsLeftLabel.setText("You have " + questionsLeft + " questions left. Put question # in first box.");
        questionsLeftLabel.setBounds(10, 190, 500, 25);
    }

    //MODIFIES: this
    //EFFECTS: initialize Labels in frameGN
    public void initializeGNLabels() {
        instruction1GNLabel = new JLabel();
        instruction2GNLabel = new JLabel();
        instruction3GNLabel = new JLabel();
        instruction4GNLabel = new JLabel();
        instruction5GNLabel = new JLabel();
        instruction6GNLabel = new JLabel();
        questionsLeftLabel = new JLabel();
        warningGNLabel = new JLabel("");
        answerGNLabel = new JLabel("");
    }

    //MODIFIES: this
    //EFFECTS: set up frameGN
    public void frameGNSetUp() {
        frameGN = new JFrame("GameHall app");
        frameGN.setLayout(new BorderLayout());
        frameGN.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frameGN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGN.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: initialize "GN last try" GUI frame
    public void initializeFrameGNLastTry() {
        setUpFrameGNLastTry();
        setUpElementsFrameGNLastTry();
        panelGNLastTry.add(submitGNLastTry);
        panelGNLastTry.add(numGNLastTry);
        panelGNLastTry.add(labelGNLastTry);
        panelGNLastTry.add(warningGNLastTryLabel);
        panelGNLastTry.add(lastQuestionAnswer);
        frameGNLastTry.add(panelGNLastTry, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in frameGNLastTry
    public void setUpElementsFrameGNLastTry() {
        panelGNLastTry = new JPanel();
        submitGNLastTry = new JButton("submit");
        submitGNLastTry.setBounds(270, 200, 100, 30);
        submitGNLastTry.addActionListener(this);
        numGNLastTry = new JTextField();
        numGNLastTry.setBounds(150, 130, 300, 25);
        labelGNLastTry = new JLabel("You used all your questions! Please enter your answer");
        labelGNLastTry.setBounds(10, 70, 500, 25);
        warningGNLastTryLabel = new JLabel("");
        warningGNLastTryLabel.setBounds(10, 300, 300, 25);
        lastQuestionAnswer = new JLabel("");
        lastQuestionAnswer.setBounds(10, 30, 500, 25);
        panelGNLastTry.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelGNLastTry.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: set up frameGNLastTry
    public void setUpFrameGNLastTry() {
        frameGNLastTry = new JFrame("GameHall app");
        frameGNLastTry.setLayout(new BorderLayout());
        frameGNLastTry.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frameGNLastTry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGNLastTry.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: initialize "info frame" GUI frame
    public void initializeInfoFrame() {
        setUpInfoFrame();
        setUpElementsInfoFrame();
        infoPanel.add(infoUserLabel);
        infoPanel.add(infoBackButton);
        infoPanel.add(infoPointsLabel);
        infoPanel.add(infoGamesPlayedLabel);
        infoPanel.add(infoWinRateLabel);
        infoPanel.add(viewFavBtn);
        infoFrame.add(infoPanel, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: set up elements in infoFrame
    public void setUpElementsInfoFrame() {
        infoPanel = new JPanel();
        infoUserLabel = new JLabel("");
        infoUserLabel.setBounds(30, 50, 500, 25);
        infoPointsLabel = new JLabel("");
        infoPointsLabel.setBounds(30, 100, 500, 25);
        infoGamesPlayedLabel = new JLabel("");
        infoGamesPlayedLabel.setBounds(30, 150, 500, 25);
        infoWinRateLabel = new JLabel("");
        infoWinRateLabel.setBounds(30, 200, 500, 25);
        infoBackButton = new JButton("back");
        infoBackButton.addActionListener(this);
        infoBackButton.setBounds(10, 10, 70, 30);
        viewFavBtn = new JButton("See favourite games");
        viewFavBtn.setBounds(30, 250, 200, 25);
        viewFavBtn.addActionListener(e -> {
            actionEvent();
            infoFrame.setVisible(false);
            favFrame.setVisible(true);
        });
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: initialize favFrame
    public void initializeFavFrame() {
        favFrameSetUp();
        favFrameElementsSetUp();
        favFrame.setLayout(new FlowLayout());
        favFrame.add(favFrameBackBtn);
        favFrame.add(seeOnlyWinsBtn);
        favFrame.add(infoFavScrollPane);
    }

    //MODIFIES: this
    //EFFECTS: set up favFrame
    public void favFrameSetUp() {
        favFrame = new JFrame("GameHall app");
        favFrame.setLayout(new BorderLayout());
        favFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        favFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        favFrame.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: set up infoFavTable
    public void favFrameElementsSetUp() {
        favFrameBtnSetUp();
        infoFavTableModel = new DefaultTableModel();
        infoFavTableModel.setColumnIdentifiers(new String[]{"Game type", "Result"});
        infoFavTable = new JTable() {
            @Override
            public boolean isCellEditable(int data, int columns) {
                return false;
            }
        };
        infoFavTable.setModel(infoFavTableModel);
        infoFavTable.setPreferredScrollableViewportSize(new Dimension(900, 300));
        infoFavTable.setFillsViewportHeight(true);
        infoFavTable.setBounds(0, 40, 900, 300);
        infoFavScrollPane = new JScrollPane(infoFavTable);
    }

    //MODIFIES: this
    //EFFECTS: set up buttons in favFrame
    public void favFrameBtnSetUp() {
        favFrameBackBtn = new JButton("back");
        favFrameBackBtn.setBounds(200, 5, 200, 30);
        favFrameBackBtn.addActionListener(e -> {
            actionEvent();
            favFrame.setVisible(false);
            infoFrame.setVisible(true);
        });
        seeOnlyWinsBtn = new JButton("see only wins");
        seeOnlyWinsBtn.setBounds(200, 5, 200, 30);
        seeOnlyWinsBtn.addActionListener(e -> {
            actionEvent();
            favFrame.setVisible(false);
            onlyWinsFrame.setVisible(true);
        });
    }

    //MODIFIES: this
    //EFFECTS: initialize favFrame
    public void initializeOnlyWinsFrame() {
        onlyWinsFrameSetUp();
        onlyWinsFrameElementsSetUp();
        onlyWinsFrame.setLayout(new FlowLayout());
        onlyWinsFrame.add(onlyWinsFrameBackBtn);
        onlyWinsFrame.add(onlyWinsScrollPane);
    }

    public void onlyWinsFrameSetUp() {
        onlyWinsFrame = new JFrame("GameHall app");
        onlyWinsFrame.setLayout(new BorderLayout());
        onlyWinsFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        onlyWinsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        onlyWinsFrame.setVisible(false);
    }

    public void onlyWinsFrameElementsSetUp() {
        onlyWinsFrameBackBtn = new JButton("back");
        onlyWinsFrameBackBtn.setBounds(200, 5, 200, 30);
        onlyWinsFrameBackBtn.addActionListener(e -> {
            actionEvent();
            onlyWinsFrame.setVisible(false);
            favFrame.setVisible(true);
        });
        onlyWinsTableModel = new DefaultTableModel();
        onlyWinsTableModel.setColumnIdentifiers(new String[]{"Game type", "Result"});
        onlyWinsTable = new JTable() {
            @Override
            public boolean isCellEditable(int data, int columns) {
                return false;
            }
        };
        onlyWinsTable.setModel(onlyWinsTableModel);
        onlyWinsTable.setPreferredScrollableViewportSize(new Dimension(900, 300));
        onlyWinsTable.setFillsViewportHeight(true);
        onlyWinsScrollPane = new JScrollPane(onlyWinsTable);
        onlyWinsScrollPane.setBounds(0, 40, 900, 300);
    }

    //MODIFIES: this
    //EFFECTS: set up infoFrame
    public void setUpInfoFrame() {
        infoFrame = new JFrame("GameHall app");
        infoFrame.setLayout(new BorderLayout());
        infoFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        infoFrame.setVisible(false);
    }

    //EFFECTS: play a sound
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        actionEvent();
        if (e.getSource() == newUserButton || e.getSource() == loadUserButton) {
            loadOrNewFrameActionPerformed(e);
        } else if (e.getSource() == loadFBtn || e.getSource() == confirmLoadBtn || e.getSource() == createNewUserBtn) {
            loadUserActionPerformed(e);
        } else if (e.getSource() == btnRPS || e.getSource() == gn || e.getSource() == save || e.getSource() == info) {
            mainFrameActionPerformed(e);
        } else if (e.getSource() == rockButton || e.getSource() == paperButton || e.getSource() == sizerButton) {
            frameRockPaperSizerActionPerformed(e);
        } else if (e.getSource() == addToFavButtonRPS || e.getSource() == notAddToFavButtonRPS) {
            frameAddToFavActionPerformed(e);
        } else if (e.getSource() == againButtonRPS || e.getSource() == exitButtonRPS) {
            frameResultRockPaperSizerActionPerformed(e);
        } else if (e.getSource() == submitQuestionButtonGN) {
            frameGNActionPerformed(e);
        } else if (e.getSource() == submitGNLastTry) {
            frameGNLastTryActionPerformed();
        } else if (e.getSource() == addToFavButtonGN || e.getSource() == notAddToFavButtonGN) {
            frameGNResultActionPerformed(e);
        } else if (e.getSource() == infoBackButton) {
            infoFrameActionPerformed();
        }
    }

    //REQUIRES: e.getSource == loadFailButton or confirmLoadButton
    //MODIFIES: this
    //EFFECTS: perform actions related to loadOrNewFrame
    public void loadUserActionPerformed(ActionEvent e) {
        if (e.getSource() == loadFBtn) {
            loadUserFailFrameActionPerformed();
        } else if (e.getSource() == confirmLoadBtn) {
            loadUserSuccessFrameActionPerformed();
        } else {
            newUserFrameActionPerformed();
        }
    }

    //MODIFIES: this
    //EFFECTS: perform actions invoked in infoFrame
    public void infoFrameActionPerformed() {
        infoFrame.setVisible(false);
        mainFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: perform actions that will be performed for every ActionEvent
    public void actionEvent() {
        playSound(SOUND_ADDRESS);
        warningGNLabel.setText("");
        savedLabel.setText("");
    }

    //MODIFIES: this
    //EFFECTS: perform actions in loadUserSuccessFrame
    public void loadUserSuccessFrameActionPerformed() {
        loadUserSuccessFrame.setVisible(false);
        mainFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: perform actions in loadUserFailFrame
    public void loadUserFailFrameActionPerformed() {
        loadUserFailFrame.setVisible(false);
        newUserFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: perform actions in newUserFrame
    public void newUserFrameActionPerformed() {
        user = new User(newUserName.getText());
        user.addObserver(this);
        newUserFrame.setVisible(false);
        mainFrame.setVisible(true);
    }

    //REQUIRES: e.getSource == addToFavButtonGN or == notAddToFavButtonGN
    //MODIFIES: this
    //EFFECTS: handle actions invoked by buttons in frameGNResult
    public void frameGNResultActionPerformed(ActionEvent e) {
        frameGNResult.setVisible(false);
        if (e.getSource() == addToFavButtonGN) {
            user.addFavouriteRounds(guessingNumber);
        }
        mainFrame.setVisible(true);
    }

    //REQUIRES: e.getSource == submitGNLastTry
    //MODIFIES: this
    //EFFECTS: handle actions invoked by buttons in frameGNLastTry
    public void frameGNLastTryActionPerformed() {
        try {
            int num = Integer.parseInt(numGNLastTry.getText());
            lastTry(num);
            frameGNLastTry.setVisible(false);
        } catch (NumberFormatException ex) {
            warningGNLastTryLabel.setText("Invalid input!");
        }
    }

    //REQUIRES: e.getSource == submitQuestionButtonGN
    //MODIFIES: this
    //EFFECTS: handle actions invoked by game buttons in frameGN
    public void frameGNActionPerformed(ActionEvent e) {
        try {
            int choice = Integer.parseInt(questionChoice.getText());
            int num = Integer.parseInt(numChoice.getText());
            questionsLeft--;
            guessingNumber.setManNum(num);
            if (answerQuestion(choice)) {
                frameGN.setVisible(false);
                lastTry(num);
            }
            if (questionsLeft < 1) {
                frameGN.setVisible(false);
                frameGNLastTry.setVisible(true);
            }
            questionsLeftLabel.setText("You have " + questionsLeft + " questions left. Put question # in first box.");
        } catch (NumberFormatException exception) {
            warningGNLabel.setText("Invalid input! You have wasted one question");
            questionsLeft--;
            questionsLeftLabel.setText("You have " + questionsLeft + " questions left. Put question # in first box.");
            if (questionsLeft < 1) {
                frameGN.setVisible(false);
                frameGNLastTry.setVisible(true);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: judge guessing number game
    private void lastTry(int num) {
        frameGNResult.setVisible(true);
        questionsLeft = guessingNumber.MAX_QUESTIONS;
        labelResultGN.setText("You chose " + num + " and the computer chose " + guessingNumber.getCompNum());
        if (guessingNumber.getCompNum() == num) {
            setManWin(guessingNumber);
        } else {
            setCompWin(guessingNumber);
        }
    }

    //REQUIRES: e.getSource == btnRPS, GN, save, or info
    //MODIFIES: this
    //EFFECTS: handle actions invoked by game buttons in mainFrame
    public void mainFrameActionPerformed(ActionEvent e) {
        if (e.getSource() == btnRPS) {
            mainFrame.setVisible(false);
            processCommand(RPS_COMMAND);
        } else if (e.getSource() == gn) {
            mainFrame.setVisible(false);
            processCommand(GN_COMMAND);
        } else if (e.getSource() == save) {
            processCommand(SAVE_COMMAND);
        } else {
            mainFrame.setVisible(false);
            processCommand(INFO_COMMAND);
        }
    }

    //REQUIRES: e.getSource == addToFavButtonRPS or == notAddToFavButtonRPS
    //MODIFIES: this
    //EFFECTS: handle actions invoked by game buttons in AddToFavFrame
    public void frameAddToFavActionPerformed(ActionEvent e) {
        addToFavFrameRPS.setVisible(false);
        if (e.getSource() == addToFavButtonRPS) {
            user.addFavouriteRounds(rps);
        }
        frameResultRPS.setVisible(true);
    }

    //REQUIRES: e.getSource == againButtonRPS or == exitButtonRPS
    //MODIFIES: this
    //EFFECTS: handle actions invoked by frameResultRPS
    public void frameResultRockPaperSizerActionPerformed(ActionEvent e) {
        frameResultRPS.setVisible(false);
        if (e.getSource() == againButtonRPS) {
            processCommand(RPS_COMMAND);
        } else {
            mainFrame.setVisible(true);
        }
    }

    //REQUIRES: e.getSource == rockButton, == sizerButton or == paperButton
    //MODIFIES: this
    //EFFECTS: handle actions invoked by frameRPS
    public void frameRockPaperSizerActionPerformed(ActionEvent e) {
        try {
            frameRPS.setVisible(false);
            if (e.getSource() == rockButton) {
                rps = new RockPaperSizer();
                rps.setManChoice(RockPaperSizer.ROCK);
            } else if (e.getSource() == sizerButton) {
                rps = new RockPaperSizer();
                rps.setManChoice(RockPaperSizer.SIZER);
            } else if (e.getSource() == paperButton) {
                rps = new RockPaperSizer();
                rps.setManChoice(RockPaperSizer.PAPER);
            }
            rps.genCompChoice();
            processRPS(rps.judge());
        } catch (InvalidChoiceRockPaperSizerException ex) {
            System.out.println("InvalidChoiceRockPaperSizerException is thrown");
        }
    }

    //REQUIRES: e.getSource == newUserButton or == loadUserButton
    //MODIFIES: this
    //EFFECTS: handle actions invoked by loadOrNewFrame
    public void loadOrNewFrameActionPerformed(ActionEvent e) {
        if (e.getSource() == newUserButton) {
            loadOrNewFrame.setVisible(false);
            processLoadOrNewCommand(NEW_COMMAND);
        } else if (e.getSource() == loadUserButton) {
            loadOrNewFrame.setVisible(false);
            processLoadOrNewCommand(LOAD_COMMAND);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Game g = (Game) arg;
        if (g.getManWin() == 1 && g.getCompWin() == 0) {
            infoFavTableModel.addRow(new String[]{g.getType(), "User won"});
            onlyWinsTableModel.addRow(new String[]{g.getType(), "User won"});
        } else if (g.getManWin() == g.getCompWin()) {
            infoFavTableModel.addRow(new String[]{g.getType(), "Draw"});
        } else {
            infoFavTableModel.addRow(new String[]{g.getType(), "Computer won"});
        }
    }
}