package be.webtechie.view;

import be.webtechie.element.Resistor;
import be.webtechie.event.AppEventListener;
import be.webtechie.resistorcalculator.definition.ColorCode;
import be.webtechie.resistorcalculator.util.Calculate;
import be.webtechie.resistorcalculator.util.Convert;
import be.webtechie.resistorcalculator.util.ResistorValue;
import be.webtechie.element.ColorBandSelection;
import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.orientation.OrientationService;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import javafx.beans.Observable;
import javafx.css.PseudoClass;
import javafx.geometry.Dimension2D;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.text.html.Option;

public class ColorBandCalculator extends View implements AppEventListener {

    private static final PseudoClass ERROR = PseudoClass.getPseudoClass("error");

    private final VBox mainHolder = new VBox();

    private final ColorBandSelection band1;
    private final ColorBandSelection band2;
    private final ColorBandSelection band3;
    private final ColorBandSelection band4;
    private final ColorBandSelection band5;
    private final ColorBandSelection band6;

    private final Label result;

    private final Resistor resistor;

    private Optional<Orientation> orientation;
    private Dimension2D resolution;

    public ColorBandCalculator() {
        getStylesheets().add(ColorBandCalculator.class.getResource("colorBandCalculator.css").toExternalForm());

        mainHolder.getStyleClass().add("mainbox");
        setCenter(mainHolder);
        
        band1 = new ColorBandSelection(1, this);
        band2 = new ColorBandSelection(2, this);
        band3 = new ColorBandSelection(3, this);
        band4 = new ColorBandSelection(4, this);
        band5 = new ColorBandSelection(5, this);
        band6 = new ColorBandSelection(6, this);

        resistor = new Resistor();
        resistor.setScaleY(0.8);
        resistor.getStyleClass().add("resistor");

        result = new Label();
        result.setWrapText(true);
        result.getStyleClass().add("result");

        Services.get(OrientationService.class).ifPresent(service -> {
            service.orientationProperty().addListener(this::orientationChanged);
            orientation = service.getOrientation();
            System.out.println("Initial orientation set to: "
                    + (orientation.isEmpty() ? "unknown" : orientation.get().name()));
        });

        Services.get(DisplayService.class).ifPresent(service -> {
            resolution = service.getScreenResolution();
            System.out.println("Initial resolution set to: "
                    + (resolution == null ? "unknown" : resolution.getWidth() + "/" + resolution.getHeight()));
        });

        orientation = Optional.of(Orientation.HORIZONTAL);

        drawLayout();
        onColorChange();
    }

    private void orientationChanged(Observable observable) {
        Services.get(OrientationService.class).ifPresent(service -> {
            orientation = service.getOrientation();
            System.out.println("Current orientation: "
                    + (orientation.isEmpty() ? "unknown" : orientation.get().name()));
            drawLayout();
        });

        Services.get(DisplayService.class).ifPresent(service -> {
            resolution = service.getScreenResolution();
            System.out.println("Current resolution set to: "
                    + (resolution == null ? "unknown" : resolution.getWidth() + "/" + resolution.getHeight()));
        });
    }

    private void drawLayout() {
        mainHolder.getChildren().clear();
        if (orientation == null || orientation.isEmpty() || orientation.get().equals(Orientation.VERTICAL)) {
            // Portrait mode
            mainHolder.getChildren().addAll(band1, band2, band3, band4, band5, band6, resistor, result);
        } else {
            // Landscape mode
            double columnWidth = resolution == null ? 300 : (resolution.getWidth() / 2) - 25;
            System.out.println("This width: " + this.getWidth());
            System.out.println("Column width: " + columnWidth);
            VBox colors = new VBox();
            colors.setMinWidth(columnWidth);
            colors.setPrefWidth(columnWidth);
            colors.setMaxWidth(columnWidth);
            band1.setMaxWidth(columnWidth);
            colors.getChildren().addAll(band1, band2, band3, band4, band5, band6);
            VBox other = new VBox();
            other.setMinWidth(columnWidth);
            other.setPrefWidth(columnWidth);
            other.setMaxWidth(columnWidth);
            other.getChildren().addAll(resistor, result);
            HBox combined = new HBox();
            combined.setSpacing(25);
            combined.getChildren().addAll(colors, other);
            mainHolder.getChildren().add(combined);
        }
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
            resistor.setColors(new ArrayList<>());
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
        resistor.setColors(colors);
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
