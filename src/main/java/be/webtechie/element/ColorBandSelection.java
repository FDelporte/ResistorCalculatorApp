package be.webtechie.element;

import be.webtechie.event.AppEventListener;
import be.webtechie.resistorcalculator.definition.ColorCode;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
        colorHolder.getStyleClass().add("colorbox");
        this.getChildren().add(colorHolder);

        for (ColorCode colorCode : ColorCode.values()) {
            ColorCode code = colorCode;
            if (this.bandNumber == 5 && colorCode.getTolerance() == null) {
                code = ColorCode.NONE;
            }
            if (this.bandNumber == 6 && colorCode.getTemperatureCoefficient() == null) {
                code = ColorCode.NONE;
            }
            ColorToggleButton tbColor = new ColorToggleButton(toggleGroup, code);

            tbColor.minWidthProperty().bind(tbColor.prefWidthProperty());
            tbColor.maxWidthProperty().bind(tbColor.prefWidthProperty());
            tbColor.minHeightProperty().bind(tbColor.prefWidthProperty());
            tbColor.prefHeightProperty().bind(tbColor.prefWidthProperty());
            tbColor.maxHeightProperty().bind(tbColor.prefWidthProperty());
            tbColor.setOnAction(this::selectionChange);
            if (colorCode == ColorCode.NONE) {
                tbColor.setSelected(true);
                tbColor.setOnAction(this::selectionChange);
                colorHolder.getChildren().add(0, tbColor);
            } else {
                colorHolder.getChildren().add(tbColor);
            }

            sceneProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    if (getScene() != null) {
                        tbColor.prefWidthProperty().bind(Bindings.createDoubleBinding(() ->
                                (getScene().getWidth() - getPadding(colorHolder)) / ColorCode.values().length - 0.5,
                                widthProperty(), getScene().widthProperty()));
                        parentProperty().removeListener(this);
                    }
                }
            });
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

    private static double getPadding(Parent parent) {
        double padding = 0;
        while (parent instanceof Region) {
            Insets p = ((Region) parent).getPadding();
            padding += p.getLeft() + p.getRight();
            parent = parent.getParent();
        }
        return padding;
    }
}
