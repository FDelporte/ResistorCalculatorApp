package be.webtechie.view;

import be.webtechie.resistorcalculator.definition.ColorCode;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ColorToggleButton extends ToggleButton {

    private final ColorCode colorCode;

    public ColorToggleButton(ToggleGroup toggleGroup, ColorCode colorCode) {
        this.colorCode = colorCode;
        this.setToggleGroup(toggleGroup);
        this.getStyleClass().add("colorButton");
        if (colorCode.getColor() != null) {
            this.setStyle("-fx-background-color: " + this.getHexColor(colorCode.getColor()));
        }
    }

    public ColorCode getColorCode() {
        return this.colorCode;
    }

    private String getHexColor(Integer value) {
        return value == null ? "" : String.format("#%06X", (0xFFFFFF & value));
    }
}
