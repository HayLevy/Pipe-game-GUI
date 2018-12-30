package pipeGame.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.FileChooser;
import pipeGame.model.PgModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PgViewModel implements ViewModel {
    public StringProperty[][] data;
    public StringProperty timeLeft;
    public StringProperty currentWindow;
    public StringProperty steps;
    private PgModel model;
    private Timer timer;
    private TimerTask task;
    private int index;
    private int themetodisplay = 1;

    public PgViewModel() {
        this.model = new PgModel();
        timeLeft = new SimpleStringProperty();
        currentWindow = new SimpleStringProperty("startView");
        steps = new SimpleStringProperty();
        steps.setValue("0");
    }

    public void startGame() {
    	if(this.data == null){
    		this.setLevel(null);
    	}
        this.currentWindow.setValue("gameView");
        this.startTimer();
        this.model.win.addListener(e->{
            if(model.win.getValue().equals(true))
                this.winView();
        });
    }

    private void setLevel(StringProperty[][] level){
        // Set level if exists
        if(level != null) {
            this.data = new StringProperty[level.length][level[0].length];
            for (int i = 0; i < level.length; i++) {
                for (int j = 0; j < level[i].length; j++) {
                    this.data[i][j] = new SimpleStringProperty(String.valueOf(level[i][j]));
                    this.data[i][j].addListener(e->Platform.runLater(()->model.isGoal(dataToCharArray())));
                }
            }
        }
        // Get Random level from folder if not exists
        else {
            File folder = new File("src/defaults");
            List<File> listOfFiles = Arrays.asList(Objects.requireNonNull(folder.listFiles()));
            fileToData(listOfFiles.get(ThreadLocalRandom.current().nextInt(0,  listOfFiles.size())));
        }
    }

    private void fileToData(File file) {
        if (file != null) {
            System.out.println(file.getName());
            ArrayList<ArrayList<String>> newData = new ArrayList<>();
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            assert scanner != null;
            while (scanner.hasNext()) {
                ArrayList<String> row = new ArrayList<>();
                newData.add(row);
                for (char c : scanner.nextLine().toCharArray()) {
                    row.add(String.valueOf(c));
                }
            }
            StringProperty[][] result = new StringProperty[newData.size()][newData.get(0).size()];
            for (int i = 0; i < newData.size(); i++) {
                for (int j = 0; j < newData.get(0).size(); j++) {
                	result[i][j] = new SimpleStringProperty();
                    result[i][j].setValue(newData.get(i).get(j));
                }
            }
            this.setLevel(result);
        }
    }

    public void startTimer() {
        stopTimer();
        timer=new Timer();
        timeLeft.setValue("0");
        task=new TimerTask() {
            @Override
            public void run() {
                timeLeft.set(String.valueOf(Integer.valueOf(timeLeft.get())+1));
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    private void stopTimer() {
        if(timer!=null) {
            model.win.setValue(false);
            timeLeft.setValue("0");
            task.cancel();
            timer.cancel();
        }
    }

    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open pipe game file (.pg)");
        fileChooser.setInitialDirectory(new File("./"));
        File chosen = fileChooser.showOpenDialog(null);
        fileToData(chosen);
        this.startView();
    }

    public void saveFile(File file) {
        try {
        	String datatofile = this.dataToString();
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(datatofile);
            writer.close();
        } catch (IOException ex) {
        }
    }

    private String dataToString() {
        StringBuilder result = new StringBuilder();
        for(StringProperty[] line: data){
            for (StringProperty s: line){
                        result.append(s.getValue().charAt(23));
            }
            result.append("\n");
        }
        return result.toString();
    }

    public void rotate(int row,int col,int times){
        String c = data[row][col].get();
        for(int i = 0; i < times; i++) {
            switch(data[row][col].get()) {
                case "F":
                    c = "7";
                    break;
                case "7":
                    c = "J";
                    break;
                case "J":
                    c = "L";
                    break;
                case "L":
                    c = "F";
                    break;
                case "|":
                    c = "-";
                    break;
                case "-":
                    c = "|";
                    break;

            }
            data[row][col].setValue(c);
        }
    }

    private void startView() {
        this.currentWindow.setValue("startView");
        this.stopTimer();
    }

    private void winView() {
        if(!this.currentWindow.getValue().equals("winView")) {
            this.stopTimer();
            this.currentWindow.setValue("winView");
        }
    }

    public void solve() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for(StringProperty[] line: data) {
            for (StringProperty c : line) {
                stringBuilder.append(c.get());
            }
            stringBuilder.append("\n");
        }

        ArrayList<String> solution = model.solve(stringBuilder.toString());
        index = 0;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(index < solution.size()) {
                    String[] vector = solution.get(index).split(",");
                    int i = Integer.valueOf(vector[0]);
                    int j = Integer.valueOf(vector[1]);
                    int times = Integer.valueOf(vector[2]);
                    rotate(i,j,times);
                    index++;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 100);
    }

    private char[][] dataToCharArray() {
        int rows = data.length;
        int cols = data[0].length;
        char[][] result = new char[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                result[i][j] = data[i][j].getValue().charAt(23);
            }
        }
        return result;
    }

    public int getthemetodisplay(){
    	return this.themetodisplay;
    }

    public void setthemetodisplay(int set){
    	this.themetodisplay = set;
    }
}