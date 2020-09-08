package be.webtechie;

import be.webtechie.resistorcalculator.definition.ColorCode;
import be.webtechie.resistorcalculator.util.Calculate;
import be.webtechie.resistorcalculator.util.Convert;
import be.webtechie.resistorcalculator.util.ResistorValue;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ColorView extends View {

    private final Button bt4Bands;
    private final Button bt5Bands;
    private final Button bt6Bands;

    private final HBox holderBands;

    private final Label result;

    public ColorView() {
        this.getStylesheets().add("colorView.css");

        VBox holder = new VBox();
        holder.setSpacing(10);
        holder.prefWidthProperty().bind(this.widthProperty());
        this.getChildren().add(holder);

        HBox buttonHolder = new HBox();
        buttonHolder.prefWidthProperty().bind(holder.widthProperty());
        buttonHolder.setSpacing(10);
        buttonHolder.setAlignment(Pos.CENTER);
        this.bt4Bands = new Button("4 Bands");
        this.bt4Bands.setStyle("-fx-min-height: 40px; -fx-min-width: 90px;");
        this.bt4Bands.setOnAction((e) -> this.drawBands(4));
        this.bt5Bands = new Button("5 Bands");
        this.bt5Bands.setStyle("-fx-min-height: 40px; -fx-min-width: 90px;");
        this.bt5Bands.setOnAction((e) -> this.drawBands(5));
        this.bt6Bands = new Button("6 Bands");
        this.bt6Bands.setStyle("-fx-min-height: 40px; -fx-min-width: 90px;");
        this.bt6Bands.setOnAction((e) -> this.drawBands(6));
        buttonHolder.getChildren().addAll(this.bt4Bands, this.bt5Bands, this.bt6Bands);
        holder.getChildren().add(buttonHolder);

        this.holderBands = new HBox();
        this.holderBands.prefWidthProperty().bind(holder.widthProperty());
        this.holderBands.setSpacing(5);
        this.holderBands.setAlignment(Pos.CENTER);
        holder.getChildren().add(this.holderBands);

        this.drawBands(4);

        Label title = new Label("Resistor value calculator (3, 4, 5 or 6 bands)");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
        getChildren().add(title);

        HBox colorSelection = new HBox();
        colorSelection.setSpacing(10);
        getChildren().add(colorSelection);

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(this::clear);

        result = new Label();
        result.setStyle("-fx-font-size: 14px; -fx-font-weight: bold");
        getChildren().add(result);


    }

    private void drawBands(int numberOfBands) {
        this.holderBands.getChildren().clear();

        for (int i = 1; i <= numberOfBands; i++) {
            VBox holderColors = new VBox();
            holderColors.setSpacing(10);
            this.holderBands.getChildren().add(holderColors);

            ToggleGroup toggleGroup = new ToggleGroup();
            for (ColorCode colorCode : Arrays.stream(ColorCode.values())
                    .filter(c -> !c.equals(ColorCode.NONE))
                    .collect(Collectors.toList())) {
                ToggleButton color = new ToggleButton();
                color.setToggleGroup(toggleGroup);
                if (colorCode.equals(ColorCode.BLACK)) {
                    color.setSelected(true);
                }
                color.setOnAction(this::calculateValue);
                color.getStyleClass().add("colorButton");
                color.setStyle("-fx-background-color: " + this.getHexColor(colorCode.getColor()));
                holderColors.getChildren().add(color);
            }
        }
    }

    private String getHexColor(Integer value) {
        return value == null ? "" : String.format("#%06X", (0xFFFFFF & value));
    }


    /**
     * Callback to render the items in the dropdown list with a color box and the name of the color.
     */
    Callback<ListView<ColorCode>, ListCell<ColorCode>> cellFactory = new Callback<>() {
        @Override
        public ListCell<ColorCode> call(ListView<ColorCode> l) {
            return new ListCell<>() {
                @Override
                protected void updateItem(ColorCode item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        HBox holder = new HBox();
                        holder.setSpacing(10);
                        setGraphic(holder);

                        VBox colorBox = new VBox();
                        colorBox.setMinWidth(20);
                        if (item.getColor() != null) {
                            colorBox.setStyle(
                                    "-fx-background-color: " + Convert.toHexColorString(item.getColor()) + ";");
                        }
                        holder.getChildren().add(colorBox);

                        holder.getChildren().add(new Label(item.name()));
                    }
                }
            };
        }
    };

    /**
     * Calculate the resistor value based on the selected colors. As soon as the first three are selected, the
     * calculation can be done.
     *
     * @param actionEvent
     */
    private void calculateValue(ActionEvent actionEvent) {
        List<ColorCode> colors = new ArrayList<>();

        setAllComboBoxColors();

        /*
        if (band1.getValue() != null) {
            colors.add(band1.getValue());
        } else {
            return;
        }

        if (band2.getValue() != null) {
            colors.add(band2.getValue());
        } else {
            return;
        }

        if (band3.getValue() != null) {
            colors.add(band3.getValue());
        } else {
            return;
        }

        if (band4.getValue() != null) {
            colors.add(band4.getValue());
        } else {
            calculateValue(colors);
        }

        if (band5.getValue() != null) {
            colors.add(band5.getValue());
        } else {
            calculateValue(colors);
        }

        if (band6.getValue() != null) {
            colors.add(band6.getValue());
            calculateValue(colors);
        } else {
            calculateValue(colors);
        }
        */
    }

    /**
     * Show the selected color for all the combo boxes.
     */
    private void setAllComboBoxColors() {
        /*setComboBoxColor(band1);
        setComboBoxColor(band2);
        setComboBoxColor(band3);
        setComboBoxColor(band4);
        setComboBoxColor(band5);
        setComboBoxColor(band6);*/
    }

    /**
     * Show the selected color of the given combo box.
     *
     * @param comboBox
     */
    private void setComboBoxColor(ComboBox<ColorCode> comboBox) {
        if (comboBox.getValue() == null || comboBox.getValue().getColor() == null) {
            comboBox.setStyle("-fx-border-width: 0px;");
        } else {
            comboBox.setStyle("-fx-border-width: 0 0 10px 0; -fx-border-color: "
                    + Convert.toHexColorString(comboBox.getValue().getColor()) + ";");
        }
    }

    /**
     * Calculate the resistor value for the given list of color codes.
     *
     * @param colors
     */
    private void calculateValue(List<ColorCode> colors) {
        try {
            ResistorValue value = Calculate.resistorValue(colors);

            result.setText(
                    "Resistor value is "
                            + Convert.toOhmString(value.getOhm())
                            + (value.getTolerance() == 0 ? "" : " with tolerance " + value.getTolerance() + "%")
                            + (value.getTemperatureCoefficient() == null ? ""
                            : ", temperature coefficient " + value.getTemperatureCoefficient() + "ppm/K")
            );
        } catch (IllegalArgumentException ex) {
            result.setText(ex.getMessage());
        }
    }

    /**
     * Clear all the combo boxes.
     *
     * @param actionEvent
     */
    private void clear(ActionEvent actionEvent) {
        /*band1.setValue(null);
        band2.setValue(null);
        band3.setValue(null);
        band4.setValue(null);
        band5.setValue(null);
        band6.setValue(null);*/

        setAllComboBoxColors();

        result.setText("");
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
        appBar.setTitleText("Resistor calculator");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }

}
