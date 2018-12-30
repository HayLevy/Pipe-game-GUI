package pipeGame.view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pipeGame.viewmodel.PgViewModel;




import java.util.ArrayList;

public class LevelDisplayer extends GridPane {

	public StringProperty[][] data;
    PgViewModel viewModel;


    public void bindData() {
        this.data = new StringProperty[viewModel.data.length][viewModel.data[0].length];
        for(int i = 0; i < viewModel.data.length; i++) {
            for(int j = 0; j < viewModel.data[i].length; j++) {
                this.data[i][j] = new SimpleStringProperty();
                this.data[i][j].addListener((obs,old,nwv)->Platform.runLater(this::reDraw));
                this.data[i][j].bind(viewModel.data[i][j]);
            }
        }
    }


    public void setData(StringProperty[][] data) {
        setHgap(0);
        setVgap(0);
        this.data = data;
        getChildren().clear();
        reDraw();
    }

    public void reDraw() {
        if(data[0][0].getValue().isEmpty())
            return;
        getChildren().clear();
        setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(0), new Insets(0))));

        for(int i = 0; i < data.length; i++) {
            addRow(i);
            for(int j = 0; j < data[i].length; j++) {
                addColumn(j);
                StringProperty c = new SimpleStringProperty();
                c.set(data[i][j].getValue());

                if(viewModel.getthemetodisplay() == 1){
                	Node grid = new ButtonGrid(c);

                	int finalJ = j;
                    int finalI = i;
                    grid.setOnMouseClicked(e->rotate(finalI, finalJ,1));
                    add(grid,j,i);
                }
                else{
                	Node grid = new ButtonGrid2(c);

                	int finalJ = j;
                    int finalI = i;
                    grid.setOnMouseClicked(e->rotate(finalI, finalJ,1));
                    add(grid,j,i);
                }
            }
        }
    }

    public void viewSolution(ArrayList<String> solution) {

        for(String s: solution) {
            String vector[] =  s.split(",");
            System.out.println(s);
            this.rotate(Integer.valueOf(vector[0]),Integer.valueOf(vector[1]),Integer.valueOf(vector[2]));

        }

    }

    public void rotate(int row,int col,int times){
    	int l = data.length;
        for(int i = 0; i < times; i++) {
        	Integer count = Integer.parseInt(viewModel.steps.getValue()) + 1;
        	viewModel.steps.setValue(count.toString());
            switch(data[row][col].getValue()) {
                case "StringProperty [value: F]":
                	data[row][col].unbind();
                	data[row][col].setValue("StringProperty [value: 7]");
                	viewModel.data[row][col].setValue("StringProperty [value: 7]");;
                	data[row][col].bind(viewModel.data[row][col]);
                    break;
                case "StringProperty [value: 7]":
                	data[row][col].unbind();
                	data[row][col].setValue("StringProperty [value: J]");
                	viewModel.data[row][col].setValue("StringProperty [value: J]");;
                	data[row][col].bind(viewModel.data[row][col]);
                    break;
                case "StringProperty [value: J]":
                	data[row][col].unbind();
                	data[row][col].setValue("StringProperty [value: L]");
                	viewModel.data[row][col].setValue("StringProperty [value: L]");;
                	data[row][col].bind(viewModel.data[row][col]);
                    break;
                case "StringProperty [value: L]":
                	data[row][col].unbind();
                	data[row][col].setValue("StringProperty [value: F]");
                	viewModel.data[row][col].setValue("StringProperty [value: F]");;
                	data[row][col].bind(viewModel.data[row][col]);
                    break;
                case "StringProperty [value: |]":
                	data[row][col].unbind();
                	data[row][col].setValue("StringProperty [value: -]");
                	viewModel.data[row][col].setValue("StringProperty [value: -]");;
                	data[row][col].bind(viewModel.data[row][col]);
                    break;
                case "StringProperty [value: -]":
                	data[row][col].unbind();
                	data[row][col].setValue("StringProperty [value: |]");
                	viewModel.data[row][col].setValue("StringProperty [value: |]");;
                	data[row][col].bind(viewModel.data[row][col]);
                    break;

            }
        }
        reDraw();
    }
}

