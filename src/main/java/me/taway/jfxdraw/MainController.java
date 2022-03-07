package me.taway.jfxdraw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
/* TODO:
    1) Calculate distance between refreshes on paint brush and if its longer than 1/2 of size then fill it with circles of the same size!

*/
public class MainController {
//    DEBUG SHIT:
    boolean debugConsole;
//    UI Shit:
    @FXML
    private AnchorPane DrawingPane;
    @FXML
    private Text TextCoordinates;

//    Cringe background painting shit blah blah at least it works, eh ?
    ArrayList<Node> nodes = new ArrayList<>();
//    Shapes:
    Line line;
    Circle circle;

//    Tool that is currently selected:
    String tool;

//    Is starting position set for drawing circles/lines ?
    boolean startSet;

//    Size of a drawing brush:
    int size;

//    Colors:
    @FXML
    private ColorPicker ColorPickerPrimary;
    @FXML
    private ColorPicker ColorPickerSecondary;

//    INITIALIZATION:
    @FXML
    void initialize() {
//        MAKE A SIZE PICKER YOU LAZY PIECE OF SHIT!
        size = 10;
//        Set tool to avoid NullPointer exception shenanigans.
        tool = "Hand";
    }

    @FXML
    void DrawPaneMouseClicked(MouseEvent event) {
        if (event != null) {
//            Set coordinates:
            TextCoordinates.setText(event.getSceneX() + "," + event.getSceneY());
//            Drawing code:
            if (event.getButton() == MouseButton.PRIMARY) {

                if(!startSet && tool.equals("Line")) {
                    startSet = true;
                    line = new Line();

                    line.setStroke(ColorPickerPrimary.getValue());
                    line.setStrokeWidth(size);

                    line.setStartX(event.getX());
                    line.setStartY(event.getY());
                    line.setEndX(event.getX());
                    line.setEndY(event.getY());
                    DrawingPane.getChildren().add(line);
                } else if (startSet && tool.equals("Line")) {
                    startSet = false;
                    nodes.add(line);
                }

                if(!startSet && tool.equals("Circle")) {
                    startSet = true;
                    circle = new Circle();

                    circle.setStroke(ColorPickerPrimary.getValue());
                    circle.setStrokeWidth(size);
                    circle.setFill(ColorPickerSecondary.getValue());

                    circle.setCenterX(event.getX());
                    circle.setCenterY(event.getY());
                    circle.setRadius(0);
                    DrawingPane.getChildren().add(circle);
                } else if (startSet && tool.equals("Circle")) {
                    startSet = false;
                    nodes.add(circle);
                }
            }
        }
    }

    @FXML
    void DrawPaneMouseDrag(MouseEvent event) {
        if (event != null) {
//            Set coordinates:
            TextCoordinates.setText(event.getSceneX() + "," + event.getSceneY());
//            Code:
            if(tool.equals("Pencil")) {
                circle = new Circle();
                circle.setStroke(ColorPickerPrimary.getValue());
                circle.setStrokeWidth(size);
                circle.setFill(ColorPickerPrimary.getValue());

                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
                circle.setRadius(size/4);
                DrawingPane.getChildren().add(circle);
                nodes.add(circle);
            }
        }
    }

    @FXML
    void DrawPaneMouseMove(MouseEvent event) {
        if (event != null) {
//            Set coordinates:
            TextCoordinates.setText(event.getSceneX() + "," + event.getSceneY());
//            Code:
            if(startSet) {
                switch (tool) {
                    case ("Line") -> {
                        line.setEndX(event.getX());
                        line.setEndY(event.getY());
                    }
                    case("Circle") -> {
                        Point2D c = new Point2D(circle.getCenterX(), circle.getCenterY());
                        circle.setRadius(c.distance(event.getX(), event.getY()));
                    }
                    default -> {}
                }
            }
        }
    }

    @FXML
    void ToolBarButtonClicked(ActionEvent event) {
        if (event != null) {
//            DEBUG:
            if(debugConsole) {
                System.out.println(event.getSource().toString());
                System.out.println("Before: " + tool);
            }
//            ACTUAL CODE:
            if(event.getSource().toString().contains("Pencil")) {
                tool = "Pencil";
            } else if(event.getSource().toString().contains("Line")) {
                tool = "Line";
            } else if(event.getSource().toString().contains("Circle")) {
                tool = "Circle";
            } else {
                tool = "Hand";
            }
//            DEBUG:
            if(debugConsole) {
                System.out.println("After: " + tool);
            }
        }
    }
}
