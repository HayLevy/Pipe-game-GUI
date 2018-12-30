package pipeGame.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConfigurationWindowController implements Initializable {
	@FXML
    TextField address;

    public void save() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("pgGame.conf"));
        writer.println(address.getText());
        writer.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("pgGame.conf"));
            address.setText(scanner.nextLine());

        } catch (FileNotFoundException e) {
            address.setText("127.0.0.1:6400");
            e.printStackTrace();
        }
    }
}
