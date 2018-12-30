package pipeGame.view;
import javafx.scene.paint.Color;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.*;



class ButtonGrid extends GridPane {
	ButtonGrid(StringProperty c) {
        for(int i = 0; i < 3; i++) {
            addRow(i);
            for(int j = 0; j < 3; j++) {
                addColumn(j);
                Pane pane = new Pane();
                pane.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
                Color color = null;
                double topLeft = 0, topRight = 0, bottomRight = 0, bottomLeft = 0;
                double r = 0.3;
                switch (c.getValue()) {
                    case "StringProperty [value: s]":
                            color = Color.DARKGREEN;
                            if(i==0 && j ==0)
                                topLeft = 1;
                            if(i==2 && j==0)
                                topRight = 1;
                            if(i==0&&j==2)
                                bottomLeft = 1;
                            if(i==2 && j==2)
                                bottomRight = 1;
                            if(i==1 && j==1)
                                topLeft = topRight = bottomRight = bottomLeft = 1;
                        break;
                    case "StringProperty [value: g]":
                            color = Color.DARKRED;
                            if(i==0 && j ==0)
                                topLeft = 1;
                            if(i==2 && j==0)
                                topRight = 1;
                            if(i==0&&j==2)
                                bottomLeft = 1;
                            if(i==2 && j==2)
                                bottomRight = 1;
                        break;
                    case "StringProperty [value: F]":
                        if((i==1 && j==2) || (i==1 && j==1) || (i==2 && j==1))
                            color = Color.DARKBLUE;
                        if(i==1 && j == 1) {
                            topLeft = r;
                        }
                        break;
                    case "StringProperty [value: 7]":
                        if((i==0 && j==1) || (i==1 && j==1) || (i==1 && j==2))
                            color = Color.DARKBLUE;
                        if(i==1 && j == 1) {
                            topRight = r;
                        }
                        break;
                    case "StringProperty [value: J]":
                        if((i==0 && j==1) || (i==1 && j==1) || (i==1 && j==0))
                            color = Color.DARKBLUE;
                        if(i==1 && j == 1) {
                            bottomRight = r;
                        }
                        break;
                    case "StringProperty [value: L]":
                        if((i==2 && j==1) || (i==1 && j==1) || (i==1 && j==0))
                            color = Color.DARKBLUE;
                        if(i==1 && j == 1) {
                            bottomLeft = r;
                        }
                        break;
                    case "StringProperty [value: |]":
                        if((i==1 && j==0) || (i==1 && j==1) || (i==1 && j==2))
                            color = Color.DARKBLUE;
                        break;
                    case "StringProperty [value: -]":
                        if((i==0 && j==1) || (i==1 && j==1) || (i==2 && j==1))
                            color = Color.DARKBLUE;
                        break;
                }
                pane.setBackground(new Background(new BackgroundFill(color, new CornerRadii(topLeft, topRight, bottomRight, bottomLeft,true), new Insets(0))));
                add(pane,i,j);

            }
        }

    }
}

class ButtonGrid2 extends GridPane {
	ButtonGrid2(StringProperty c) {
        for(int i = 0; i < 3; i++) {
            addRow(i);
            for(int j = 0; j < 3; j++) {
                addColumn(j);
                Pane pane = new Pane();
                pane.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
                Color color = null;
                double topLeft = 0, topRight = 0, bottomRight = 0, bottomLeft = 0;
                double r = 0.3;
                switch (c.getValue()) {
                    case "StringProperty [value: s]":
                            color = Color.CHARTREUSE;
                            if(i==0 && j ==0)
                                topLeft = 1;
                            if(i==2 && j==0)
                                topRight = 1;
                            if(i==0&&j==2)
                                bottomLeft = 1;
                            if(i==2 && j==2)
                                bottomRight = 1;
                            if(i==1 && j==1)
                                topLeft = topRight = bottomRight = bottomLeft = 1;
                        break;
                    case "StringProperty [value: g]":
                            color = Color.DEEPPINK;
                            if(i==0 && j ==0)
                                topLeft = 1;
                            if(i==2 && j==0)
                                topRight = 1;
                            if(i==0&&j==2)
                                bottomLeft = 1;
                            if(i==2 && j==2)
                                bottomRight = 1;
                        break;
                    case "StringProperty [value: F]":
                        if((i==1 && j==2) || (i==1 && j==1) || (i==2 && j==1))
                            color = Color.AQUA;
                        if(i==1 && j == 1) {
                            topLeft = r;
                        }
                        break;
                    case "StringProperty [value: 7]":
                        if((i==0 && j==1) || (i==1 && j==1) || (i==1 && j==2))
                            color = Color.AQUA;
                        if(i==1 && j == 1) {
                            topRight = r;
                        }
                        break;
                    case "StringProperty [value: J]":
                        if((i==0 && j==1) || (i==1 && j==1) || (i==1 && j==0))
                            color = Color.AQUA;
                        if(i==1 && j == 1) {
                            bottomRight = r;
                        }
                        break;
                    case "StringProperty [value: L]":
                        if((i==2 && j==1) || (i==1 && j==1) || (i==1 && j==0))
                            color = Color.AQUA;
                        if(i==1 && j == 1) {
                            bottomLeft = r;
                        }
                        break;
                    case "StringProperty [value: |]":
                        if((i==1 && j==0) || (i==1 && j==1) || (i==1 && j==2))
                            color = Color.AQUA;
                        break;
                    case "StringProperty [value: -]":
                        if((i==0 && j==1) || (i==1 && j==1) || (i==2 && j==1))
                            color = Color.AQUA;
                        break;
                }
                pane.setBackground(new Background(new BackgroundFill(color, new CornerRadii(topLeft, topRight, bottomRight, bottomLeft,true), new Insets(0))));
                add(pane,i,j);
            }
        }

    }
}
