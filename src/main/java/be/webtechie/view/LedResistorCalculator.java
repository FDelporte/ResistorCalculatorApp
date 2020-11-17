package be.webtechie.view;

import be.webtechie.resistorcalculator.util.Calculate;
import be.webtechie.resistorcalculator.util.Convert;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

public class LedResistorCalculator extends View {

    private final Spinner<Double> inputVoltage;
    private final Spinner<Double> ledVoltage;
    private final Spinner<Double> ledCurrent;

    private final Label result;

    public LedResistorCalculator() {
        getStylesheets().add(ColorBandCalculator.class.getResource("ledResistorCalculator.css").toExternalForm());

        VBox holder = new VBox();
        holder.getStyleClass().add("mainbox");
        holder.setSpacing(10);
        setCenter(holder);

        Label title = new Label("Led resistor calculator");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
        holder.getChildren().add(title);

        holder.getChildren().add(new Label("Input voltage (V)"));
        inputVoltage = new Spinner<>(0.1, 110, 3.3);
        inputVoltage.setPrefWidth(80);
        inputVoltage.setEditable(true);
        inputVoltage.valueProperty().addListener((obs, oldValue, newValue) -> calculateValue());
        holder.getChildren().add(inputVoltage);

        holder.getChildren().add(new Label("Led voltage (V)"));
        ledVoltage = new Spinner<>(0.1, 110, 2.2);
        ledVoltage.setPrefWidth(80);
        ledVoltage.setEditable(true);
        ledVoltage.valueProperty().addListener((obs, oldValue, newValue) -> calculateValue());
        holder.getChildren().add(ledVoltage);

        holder.getChildren().add(new Label("Led current (A)"));
        ledCurrent = new Spinner<>(0.001, 3, 0.02);
        ledCurrent.setPrefWidth(80);
        ledCurrent.setEditable(true);
        ledCurrent.valueProperty().addListener((obs, oldValue, newValue) -> calculateValue());
        holder.getChildren().add(ledCurrent);

        result = new Label();
        result.setWrapText(true);
        result.getStyleClass().add("result");
        holder.getChildren().add(result);

        calculateValue();
    }

    /**
     * Calculate the resistor value when one of the inputs changes
     */
    private void calculateValue() {
        try {
            long value = Calculate.resistorForLed(
                    inputVoltage.getValue(),
                    ledVoltage.getValue(),
                    ledCurrent.getValue());

            result.setText("A resistor with value " + Convert.toOhmString((double) value) + " is needed");
        } catch (IllegalArgumentException ex) {
            result.setText(ex.getMessage());
        }
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> getApplication().getDrawer().open()));
        appBar.setTitleText("LED resistor calculator");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }
}
