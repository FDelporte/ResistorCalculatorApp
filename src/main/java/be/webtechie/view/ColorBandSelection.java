package be.webtechie.view;

import be.webtechie.event.AppEventListener;
import be.webtechie.resistorcalculator.definition.ColorCode;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ColorBandSelection extends VBox {

    final int bandNumber;
    final AppEventListener appEventListener;
    final Label bandLabel;
    final ToggleGroup toggleGroup;

    public ColorBandSelection(int bandNumber, AppEventListener appEventListener) {
        this.bandNumber = bandNumber;
        this.appEventListener = appEventListener;
        this.toggleGroup = new ToggleGroup();

        this.bandLabel = new Label();
        bandLabel.getStyleClass().add("bandName");
        this.getChildren().add(bandLabel);

        HBox colorHolder = new HBox();
        this.getChildren().add(colorHolder);

        ColorToggleButton tbNone = new ColorToggleButton(toggleGroup, ColorCode.NONE);
        tbNone.setSelected(true);
        tbNone.setOnAction(this::selectionChange);
        colorHolder.getChildren().add(tbNone);

        for (ColorCode colorCode : Arrays.stream(ColorCode.values())
                .filter(c -> !c.equals(ColorCode.NONE))
                .collect(Collectors.toList())) {
            ColorToggleButton tbColor = new ColorToggleButton(toggleGroup, colorCode);
            tbColor.setOnAction(this::selectionChange);
            colorHolder.getChildren().add(tbColor);
        }

        this.setBandLabel();
    }

    private void selectionChange(ActionEvent actionEvent) {
        this.setBandLabel();
        this.appEventListener.onColorChange();
    }

    private void setBandLabel() {
        this.bandLabel.setText("Band " + bandNumber + this.getColorCodeLabel(this.getSelection()));
    }

    private String getColorCodeLabel(ColorCode colorCode) {
        if (colorCode.equals(ColorCode.NONE)) {
            return "";
        }
        String label = " - " + colorCode.name();
        if (colorCode.getValue() != null && this.bandNumber <= 4) {
            label += " - " + colorCode.getValue();
        }
        if (colorCode.getMultiplier() != null && this.bandNumber <= 4) {
            label += " - x" + colorCode.getMultiplier();
        }
        if (colorCode.getTolerance() != null && this.bandNumber == 5) {
            label += " - Tol. " + colorCode.getTolerance() + "%";
        }
        if (colorCode.getTemperatureCoefficient() != null && this.bandNumber == 6) {
            label += " - Temp. coeff. " + colorCode.getTemperatureCoefficient();
        }
        return label;
    }

    public ColorCode getSelection() {
        if (this.toggleGroup.getSelectedToggle() == null) {
            return ColorCode.NONE;
        }
        return ((ColorToggleButton) this.toggleGroup.getSelectedToggle()).getColorCode();
    }
}
