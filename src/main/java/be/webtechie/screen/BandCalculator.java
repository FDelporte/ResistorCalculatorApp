package be.webtechie.screen;

import be.webtechie.event.AppEventListener;
import be.webtechie.resistorcalculator.definition.ColorCode;
import be.webtechie.resistorcalculator.util.Calculate;
import be.webtechie.resistorcalculator.util.Convert;
import be.webtechie.resistorcalculator.util.ResistorValue;
import be.webtechie.view.ColorBandSelection;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BandCalculator extends View implements AppEventListener {

    private final ColorBandSelection band1;
    private final ColorBandSelection band2;
    private final ColorBandSelection band3;
    private final ColorBandSelection band4;
    private final ColorBandSelection band5;
    private final ColorBandSelection band6;

    private final Label result;

    public BandCalculator() {
        this.getStylesheets().add("colorView.css");

        VBox holder = new VBox();
        holder.setSpacing(10);
        holder.prefWidthProperty().bind(this.widthProperty());
        this.getChildren().add(holder);

        this.band1 = new ColorBandSelection(1, this);
        this.band2 = new ColorBandSelection(2, this);
        this.band3 = new ColorBandSelection(3, this);
        this.band4 = new ColorBandSelection(4, this);
        this.band5 = new ColorBandSelection(5, this);
        this.band6 = new ColorBandSelection(6, this);

        holder.getChildren().addAll(this.band1, this.band2, this.band3, this.band4, this.band5, this.band6);

        this.result = new Label();
        this.result.getStyleClass().add("result");
        holder.getChildren().add(result);

        this.onColorChange();
    }

    /**
     * Calculate the resistor value based on the selected colors. As soon as the first three are selected, the
     * calculation can be done.
     */
    @Override
    public void onColorChange() {
        List<ColorCode> colors = new ArrayList<>();
        if (this.band1.getSelection() == ColorCode.NONE
                || this.band2.getSelection() == ColorCode.NONE
                || this.band3.getSelection() == ColorCode.NONE
                || this.band4.getSelection() == ColorCode.NONE) {
            this.result.setText("Minimal first 4 bands are needed");
            return;
        }
        colors.add(this.band1.getSelection());
        colors.add(this.band2.getSelection());
        colors.add(this.band3.getSelection());
        colors.add(this.band4.getSelection());
        if (this.band5.getSelection() != ColorCode.NONE) {
            colors.add(this.band5.getSelection());
            if (this.band6.getSelection() != ColorCode.NONE) {
                colors.add(this.band6.getSelection());
            }
        }
        this.calculateValue(colors);
    }

    /**
     * Calculate the resistor value for the given list of color codes.
     *
     * @param colors {@link List} of {@link ColorCode}
     */
    private void calculateValue(List<ColorCode> colors) {
        try {
            ResistorValue value = Calculate.resistorValue(colors);
            this.result.setText(
                    "Resistor value is "
                            + Convert.toOhmString(value.getOhm()) + "\n"
                            + (value.getTolerance() == 0 ? "" :
                            " with tolerance " + value.getTolerance() + "%\n")
                            + (value.getTemperatureCoefficient() == null ? "" :
                            " temperature coefficient " + value.getTemperatureCoefficient() + "ppm/K")
            );
        } catch (IllegalArgumentException ex) {
            this.result.setText(ex.getMessage());
        }
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
        appBar.setTitleText("Resistor calculator");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }

}
