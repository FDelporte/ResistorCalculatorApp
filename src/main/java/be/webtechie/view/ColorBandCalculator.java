package be.webtechie.view;

import be.webtechie.event.AppEventListener;
import be.webtechie.resistorcalculator.definition.ColorCode;
import be.webtechie.resistorcalculator.util.Calculate;
import be.webtechie.resistorcalculator.util.Convert;
import be.webtechie.resistorcalculator.util.ResistorValue;
import be.webtechie.element.ColorBandSelection;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ArrayList;
import java.util.List;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ColorBandCalculator extends View implements AppEventListener {

    private final static PseudoClass ERROR = PseudoClass.getPseudoClass("error");

    private final ColorBandSelection band1;
    private final ColorBandSelection band2;
    private final ColorBandSelection band3;
    private final ColorBandSelection band4;
    private final ColorBandSelection band5;
    private final ColorBandSelection band6;

    private final Label result;

    public ColorBandCalculator() {
        getStylesheets().add(ColorBandCalculator.class.getResource("colorBandCalculator.css").toExternalForm());

        VBox holder = new VBox();
        holder.getStyleClass().add("mainbox");
        setCenter(holder);
        
        band1 = new ColorBandSelection(1, this);
        band2 = new ColorBandSelection(2, this);
        band3 = new ColorBandSelection(3, this);
        band4 = new ColorBandSelection(4, this);
        band5 = new ColorBandSelection(5, this);
        band6 = new ColorBandSelection(6, this);

        holder.getChildren().addAll(band1, band2, band3, band4, band5, band6);

        result = new Label();
        result.setWrapText(true);
        result.getStyleClass().add("result");
        holder.getChildren().add(result);

        onColorChange();
    }

    /**
     * Calculate the resistor value based on the selected colors. As soon as the first three are selected, the
     * calculation can be done.
     */
    @Override
    public void onColorChange() {
        List<ColorCode> colors = new ArrayList<>();
        if (band1.getSelection() == ColorCode.NONE
                || band2.getSelection() == ColorCode.NONE
                || band3.getSelection() == ColorCode.NONE
                || band4.getSelection() == ColorCode.NONE) {
            result.setText("Minimal first 4 bands are needed");
            result.pseudoClassStateChanged(ERROR, true);
            return;
        }
        colors.add(band1.getSelection());
        colors.add(band2.getSelection());
        colors.add(band3.getSelection());
        colors.add(band4.getSelection());
        if (band5.getSelection() != ColorCode.NONE) {
            colors.add(band5.getSelection());
            if (band6.getSelection() != ColorCode.NONE) {
                colors.add(band6.getSelection());
            }
        }
        calculateValue(colors);
    }

    /**
     * Calculate the resistor value for the given list of color codes.
     *
     * @param colors {@link List} of {@link ColorCode}
     */
    private void calculateValue(List<ColorCode> colors) {
        try {
            ResistorValue value = Calculate.resistorValue(colors);
            result.setText(
                    "Resistor value is "
                            + Convert.toOhmString(value.getOhm()) + "\n"
                            + (value.getTolerance() == 0 ? "" :
                            " with tolerance " + value.getTolerance() + "%\n")
                            + (value.getTemperatureCoefficient() == null ? "" :
                            " temperature coefficient " + value.getTemperatureCoefficient() + "ppm/K")
            );
            result.pseudoClassStateChanged(ERROR, false);
        } catch (IllegalArgumentException ex) {
            result.setText(ex.getMessage());
            result.pseudoClassStateChanged(ERROR, true);
        }
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> getApplication().getDrawer().open()));
        appBar.setTitleText("Resistor color calculator");
    }
}
