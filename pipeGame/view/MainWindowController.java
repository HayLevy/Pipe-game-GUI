package pipeGame.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pipeGame.viewmodel.PgViewModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable, View {
    private PgViewModel viewModel;
    private LevelDisplayer levelDisplayer;

    @FXML
    Button solve;

    @FXML
    Text timer;

    @FXML
    BorderPane borderPane;

    @FXML
    Text steps;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        solve.setOnMouseClicked(event -> {
            try {
                viewModel.solve();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.viewModel = new PgViewModel();
        this.levelDisplayer = new LevelDisplayer();
        this.levelDisplayer.viewModel = this.viewModel;
        drawWindow();
        viewModel.currentWindow.addListener(e->drawWindow());
        Platform.runLater(()-> viewModel.timeLeft.addListener(event->{
            timer.setText(viewModel.timeLeft.getValue());

        }));
        Platform.runLater(()-> viewModel.steps.addListener(event->{
            steps.setText(viewModel.steps.getValue());

        }));

    }

    public void changeTheme(){
    	if(viewModel.getthemetodisplay() == 1)
    	{
    		viewModel.setthemetodisplay(2);
    		this.levelDisplayer.reDraw();
    	}
    	else{
    		viewModel.setthemetodisplay(1);
    		this.levelDisplayer.reDraw();
    	}
    }

    private void drawGameOver() {
        Button gameOver = new Button("You Lose!");
        gameOver.setOnAction(e->viewModel.startGame());
        gameOver.setRotate(0);
        new Thread(()-> {
                while (gameOver.getRotate() <= 1440) {
                    gameOver.setRotate(gameOver.getRotate() + 50.0);
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
        timer.setText("0");
        Platform.runLater(()->borderPane.setCenter(gameOver));
    }

    private void drawStart() {
        Button start = new Button("Start Game");
        start.setOnMouseClicked(e->Platform.runLater((()->viewModel.startGame())));
        borderPane.setCenter(start);
        viewModel.steps.setValue("0");
    }

    private void drawWin() {
        Button win = new Button("You win!   Steps: " + viewModel.steps.getValue());
        this.viewModel.data = null;
        win.setOnMouseClicked(e->Platform.runLater((()->viewModel.startGame())));
        borderPane.setCenter(win);
        viewModel.steps.setValue("0");
    }

    private void drawLevelDisplayer() {
        levelDisplayer.bindData();
        borderPane.setCenter(levelDisplayer);
    }

    private void drawWindow() {
        switch (viewModel.currentWindow.getValue()) {
            case "startView":
                drawStart();
                break;
            case "gameView":
                drawLevelDisplayer();
                break;
            case "overView":
                drawGameOver();
                break;
            case "winView":
                drawWin();
                break;
                default:
                    break;
        }
    }

    public void openConfigurationWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfigurationWindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Pipe Game - Configuration");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ignored) {}
    }

    public void openAboutWindow(){
    	try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutWindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Pipe Game - About");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ignored) {}
    }

    public void openFile(){
        viewModel.openFile();
        if(levelDisplayer.data != null){
        	levelDisplayer.bindData();
        }
    }

    public void saveFile(){
    	FileChooser fileChooser = new FileChooser();
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            viewModel.saveFile(file);
        }

    }
}
