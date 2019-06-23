package numericalmethods;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


/**
 * implementation of Euler's method for visualizing
 * first order differential equations
 * @author F Mahvari
 */


public class EulerVisualization extends BorderPane {

    private double stepSize =0.15;

    private Group group;

    private Pane infoPane;
    private Pane graphPane;

    private Group curveChunksGroup;

    private double initialX = 0;
    private double initialY = 0;

    private double coefX = 1;
    private double coefY = 1;

    private Slider iniXSlider;
    private Slider iniYSlider;
    private Slider aSlider;
    private Slider bSlider;


    public EulerVisualization(double initialX, double initialY){

        setPadding(new Insets(10, 10, 10, 10));

        this.initialX = initialX;
        this.initialY = initialY;

        group = new Group();
        infoPane = new StackPane();
        graphPane = new Pane();

        curveChunksGroup = new Group();

        infoPane.setPrefSize(350, 600);

        graphPane.setPrefSize(600, 600);

        //set the graph
        setGraph();

        //set the info
        setInfo();

    }

    private void setInfo(){

        BorderPane sliderContainer = new BorderPane();

        VBox sliderBox = new VBox();
        sliderBox.setPadding(new Insets(100, 10, 10, 80));
        sliderBox.setSpacing(40);

        VBox sliderLblsBox = new VBox();
        sliderLblsBox.setPadding(new Insets(100, 10, 10, 60));
        sliderLblsBox.setSpacing(33);

        //set sliders info
        setSlidersInfo(sliderLblsBox);

        setSliders();

        sliderBox.getChildren().addAll(aSlider, bSlider, iniXSlider, iniYSlider);

        Image equationImg = new Image(getClass().getResourceAsStream("/img/equation.gif"));
        ImageView equationView = new ImageView(equationImg);


        sliderContainer.setBottom(equationView);
        BorderPane.setAlignment(equationView, Pos.TOP_CENTER);
        BorderPane.setMargin(equationView, new Insets(10, 10, 150, 10));
        sliderContainer.setLeft(sliderLblsBox);
        sliderContainer.setCenter(sliderBox);


        infoPane.getChildren().addAll(sliderContainer);

        iniYSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                initialY = newValue.doubleValue();
                drawCurve();

            }
        });

        iniXSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                initialX = newValue.doubleValue();
                drawCurve();

            }
        });

        aSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                coefX = newValue.doubleValue();
                drawCurve();

            }
        });

        bSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                coefY = newValue.doubleValue();
                drawCurve();

            }
        });


        setLeft(infoPane);
    }

    private void setSliders(){

        aSlider = new Slider(0, 2, 0.25);
        aSlider.setBlockIncrement(0.25);
        aSlider.setMajorTickUnit(0.5);
        aSlider.setMinorTickCount(0);
        aSlider.setShowTickLabels(true);
        aSlider.setSnapToTicks(true);
        aSlider.setValue(1);

        bSlider = new Slider(0, 2, 0.25);
        bSlider.setBlockIncrement(0.25);
        bSlider.setMajorTickUnit(0.5);
        bSlider.setMinorTickCount(0);
        bSlider.setShowTickLabels(true);
        bSlider.setSnapToTicks(true);
        bSlider.setValue(1);

        iniXSlider = new Slider(0, 2, 0.5);
        iniXSlider.setBlockIncrement(0.5);
        iniXSlider.setMajorTickUnit(0.5);
        iniXSlider.setMinorTickCount(0);
        iniXSlider.setShowTickLabels(true);
        iniXSlider.setSnapToTicks(true);
        iniXSlider.setValue(0);

        iniYSlider = new Slider(0, 2, 0.5);
        iniYSlider.setBlockIncrement(0.5);
        iniYSlider.setMajorTickUnit(0.5);
        iniYSlider.setMinorTickCount(0);
        iniYSlider.setShowTickLabels(true);
        iniYSlider.setSnapToTicks(true);
        iniYSlider.setValue(0);

    }

    private void setSlidersInfo(VBox sliderLblsBox){


        ImageView aSliderTxt = new ImageView(
                new Image(getClass().getResourceAsStream("/img/a.gif")));
        ImageView bSliderTxt = new ImageView(
                new Image(getClass().getResourceAsStream("/img/b.gif")));
        ImageView iniXSliderTxt = new ImageView(
                new Image(getClass().getResourceAsStream("/img/x0.gif")));
        ImageView iniYSliderTxt = new ImageView(
                new Image(getClass().getResourceAsStream("/img/y0.gif")));

        sliderLblsBox.getChildren().addAll(aSliderTxt, bSliderTxt, iniXSliderTxt, iniYSliderTxt);
    }


    private void setGraph(){

        //equaion =>> y' = A*x^2 - B*y^2

        //draw grid
        drawGrid();

        //draw curve
        drawCurve();

        setRight(graphPane);
    }


    private void drawGrid(){

        Group gridGroup = new Group();

        Line xAxis = new Line(0, 600, 600, 600);
        Line yAxis = new Line(0, 0, 0, 600);

        xAxis.setStroke(Color.RED);
        yAxis.setStroke(Color.RED);

        gridGroup.getChildren().addAll(xAxis, yAxis);
        graphPane.getChildren().add(gridGroup);

        for(int i = 0; i <= 20; i++) {
            Line gridLineY = new Line(0, 600 * i / 20, 600, 600 * i / 20);
            Line gridLineX = new Line(600 * i / 20, 0, 600 * i / 20, 600);

            gridLineX.setStroke(Color.color(0, 0, 0, 0.15));
            gridLineY.setStroke(Color.color(0, 0, 0, 0.15));

            graphPane.getChildren().addAll(gridLineY, gridLineX);
        }

        for(int i=0; i<=5; i++){

            int yOffset =15;

            Text xAxisGridScale = new Text(120*i, 600 + yOffset, i+"");
            Text yAxisGridScale = new Text(0 - yOffset, 600 - 120*i, i+"");

            graphPane.getChildren().addAll(xAxisGridScale, yAxisGridScale);
        }
    }


    private void drawCurve(){

        //equaion =>> y' = A*(x-1)^2 - B*y^2
        //default A=1; B=1;

        curveChunksGroup.getChildren().clear();

        double xStart = initialX;
        double yStart =initialY;
        double yPrime;

        for(int i=1; i<=60; i++){

            yPrime = coefX*(xStart)* (xStart) - coefY*yStart*yStart;
            double xEnd;
            double yEnd;

            xEnd = xStart + stepSize;
            yEnd = yPrime*stepSize + yStart;

            if(xEnd*120>600 || 600-yEnd*120<0){
                break;
            }

            Line curveChunk = new Line(xStart*120, (600-yStart*120), xEnd*120, (600-yEnd*120));
            //graphPane.getChildren().add(curveChunk);
            curveChunksGroup.getChildren().addAll(curveChunk);

            //System.out.printf("xs=%f  ys=%f  xe=%f  ye=%f  y'=%f %n", xStart, yStart, xEnd, yEnd, yPrime);

            xStart =xEnd;
            yStart = yEnd;
        }

        graphPane.getChildren().add(curveChunksGroup);
    }
}
