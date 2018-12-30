package pipeGame.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class PgModel implements Model {
    public BooleanProperty win;

    private int numRows;
    private int numCols;

    public PgModel() {
        win = new SimpleBooleanProperty(false);
    }

    private int weight(String request) {
        int result = 0;
        for(int i = 0; i < request.length(); i++) {
            char c = request.charAt(i);
            if (c == 'L' || c == 'F' || c == '7' || c == 'J')
                result += 2;
            if (c == '|' || c == '-')
                result++;
        }
        return result;
    }

    private String getIp() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("pgGame.conf"));
        return scanner.nextLine().split(":")[0];
    }

    private int getPort() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("pgGame.conf"));
        return Integer.valueOf(scanner.nextLine().split(":")[1]);
    }

    public ArrayList<String> solve(String request) throws IOException {
        Socket s = new Socket(getIp(), getPort());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream());

        out.println(request += "done");
        out.flush();

        String answer = in.readLine();
        ArrayList<String> solution = new ArrayList<>();

        while(!answer.equals("done")) {
            solution.add(answer);
            answer = in.readLine();
        }

        in.close();
        out.close();
        s.close();

        return solution;
    }

    public void isGoal(char[][] data) {
        numRows = data.length;
        numCols = data[0].length;

        loop: for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                if(data[i][j] == 's') {
                    this.win.setValue(findAWay(i,j,data));
                    break loop;
                }
            }
        }
    }

    private boolean findAWay(int i, int j, char[][] originalLevel) {
        char[][] level = new char[originalLevel.length][originalLevel[0].length];
        for(int x = 0; x < level.length; x++) {
            System.arraycopy(originalLevel[x], 0, level[x], 0, level[0].length);
        }

        char part = level[i][j];
        level[i][j] = ' ';

        switch (part) {
            case 'g':
                return true;
            case 's':
                return  up(i, j, level) && findAWay(i - 1, j, level) ||
                        down(i, j, level) && findAWay(i + 1, j, level) ||
                        right(i, j, level) && findAWay(i, j + 1, level) ||
                        left(i, j, level) && findAWay(i, j - 1, level);
            case '|':
                return  up(i, j, level) && findAWay(i - 1, j, level) ||
                        down(i, j, level) && findAWay(i + 1, j, level);
            case '-':
                return  right(i, j, level) && findAWay(i, j + 1, level) ||
                        left(i, j, level) && findAWay(i, j - 1, level);
            case 'L':
                return  up(i, j, level) && findAWay(i - 1, j, level) ||
                        right(i, j, level) && findAWay(i, j + 1, level);
            case 'F':
                return  down(i, j, level) && findAWay(i + 1, j, level) ||
                        right(i, j, level) && findAWay(i, j + 1, level);
            case '7':
                return  down(i, j, level) && findAWay(i + 1, j, level) ||
                        left(i, j, level) && findAWay(i, j - 1, level);
            case 'J':
                return  up(i, j, level) && findAWay(i - 1, j, level) ||
                        left(i, j, level) && findAWay(i, j - 1, level);
            default:
                return false;
        }
    }
        private boolean up ( int i, int j, char[][] level){
            if (isInBound(i - 1, j)) {
                switch (level[i - 1][j]) {
                    case '7':
                    case '|':
                    case 'F':
                    case 'g':
                        return true;
                }
            }
            return false;
        }
        private boolean right ( int i, int j, char[][] level){
            if (isInBound(i, j + 1)) {
                switch (level[i][j + 1]) {
                    case '7':
                    case 'J':
                    case '-':
                    case 'g':
                        return true;
                }
            }
            return false;
        }
        private boolean left ( int i, int j, char[][] level){
            if (isInBound(i, j - 1)) {
                switch (level[i][j - 1]) {
                    case 'F':
                    case 'L':
                    case '-':
                    case 'g':
                        return true;
                }
            }
            return false;
        }
        private boolean down ( int i, int j, char[][] level){
            if (isInBound(i + 1, j)) {
                switch (level[i + 1][j]) {
                    case 'J':
                    case 'L':
                    case '|':
                    case 'g':
                        return true;
                }
            }
            return false;
        }
        private boolean isInBound(int i, int j){
            return (i >= 0 && i < numRows &&
                    j >= 0 && j < numCols);
        }
}
