package be.webtechie.element;

import be.webtechie.resistorcalculator.definition.ColorCode;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;

public class Resistor extends Pane {

    private final Pane bands;

    public Resistor() {
        this.setWidth(300);
        this.setHeight(100);

        Polygon resistor = new Polygon();
        resistor.getPoints().addAll(
                0.0, 50.0,
                25.0, 50.0,
                50.0, 5.0,
                85.0, 5.0,
                100.0, 20.0,
                200.0, 20.0,
                215.0, 5.0,
                250.0, 5.0,
                275.0, 50.0,
                300.0, 50.0,
                275.0, 50.0,
                250.0, 95.0,
                215.0, 95.0,
                200.0, 80.0,
                100.0, 80.0,
                85.0, 95.0,
                50.0, 95.0,
                25.0, 50.0,
                0.0, 50.0);
        resistor.setFill(Color.LIGHTGRAY);
        resistor.setStrokeWidth(2);
        resistor.setStroke(Color.DARKGRAY);

        QuadCurve curveLeft = new QuadCurve();
        curveLeft.setStartX(50);
        curveLeft.setStartY(5);
        curveLeft.setControlX(0);
        curveLeft.setControlY(50);
        curveLeft.setEndX(50);
        curveLeft.setEndY(95);
        curveLeft.setFill(Color.LIGHTGRAY);
        curveLeft.setStroke(Color.DARKGRAY);
        curveLeft.setStrokeWidth(2);

        QuadCurve curveRight = new QuadCurve();
        curveRight.setStartX(250);
        curveRight.setStartY(5);
        curveRight.setControlX(300);
        curveRight.setControlY(50);
        curveRight.setEndX(250);
        curveRight.setEndY(95);
        curveRight.setFill(Color.LIGHTGRAY);
        curveRight.setStroke(Color.DARKGRAY);
        curveRight.setStrokeWidth(2);

        bands = new Pane();
        bands.setPrefWidth(300);
        bands.setPrefHeight(100);

        this.getChildren().addAll(resistor, curveLeft, curveRight, bands);
    }

    public void setColors(List<ColorCode> colors) {
        bands.getChildren().removeAll();

        if (colors.size() < 4) {
            return;
        }

        Rectangle band1 = new Rectangle();
        band1.setX(55);
        band1.setY(5);
        band1.setWidth(20);
        band1.setHeight(90);
        band1.setFill(getColor(colors.get(0)));

        Rectangle band2 = new Rectangle();
        band2.setX(100);
        band2.setY(20);
        band2.setWidth(20);
        band2.setHeight(60);
        band2.setFill(getColor(colors.get(1)));

        Rectangle band3 = new Rectangle();
        band3.setX(125);
        band3.setY(20);
        band3.setWidth(20);
        band3.setHeight(60);
        band3.setFill(getColor(colors.get(2)));

        bands.getChildren().addAll(band1, band2, band3);

        Rectangle band4 = new Rectangle();
        bands.getChildren().add(band4);

        if (colors.size() == 4) {
            band4.setX(225);
            band4.setY(5);
            band4.setWidth(20);
            band4.setHeight(90);
            band4.setFill(getColor(colors.get(3)));
        } else {
            band4.setX(150);
            band4.setY(20);
            band4.setWidth(20);
            band4.setHeight(60);
            band4.setFill(getColor(colors.get(3)));

            Rectangle band5 = new Rectangle();
            bands.getChildren().add(band5);

            if (colors.size() == 5) {
                band5.setX(225);
                band5.setY(5);
                band5.setWidth(20);
                band5.setHeight(90);
                band5.setFill(getColor(colors.get(4)));
            } else {
                band5.setX(175);
                band5.setY(20);
                band5.setWidth(20);
                band5.setHeight(60);
                band5.setFill(getColor(colors.get(4)));

                Rectangle band6 = new Rectangle();
                bands.getChildren().add(band6);
                band6.setX(225);
                band6.setY(5);
                band6.setWidth(20);
                band6.setHeight(90);
                band6.setFill(getColor(colors.get(5)));
            }
        }
    }

    private Color getColor(ColorCode colorCode) {
        return Color.valueOf(String.format("#%06X", (0xFFFFFF & colorCode.getColor())));
    }
}
