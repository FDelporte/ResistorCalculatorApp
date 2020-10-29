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
        this.getStylesheets().add("be/webtechie/view/ledResistorCalculator.css");

        VBox holder = new VBox();
        this.getChildren().add(holder);
        holder.setSpacing(10);

        Label title = new Label("Led resistor calculator");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
        holder.getChildren().add(title);

        holder.getChildren().add(new Label("Input voltage (V)"));
        this.inputVoltage = new Spinner<>(0.1, 110, 3.3);
        this.inputVoltage.setPrefWidth(80);
        this.inputVoltage.setEditable(true);
        this.inputVoltage.valueProperty().addListener((obs, oldValue, newValue) -> calculateValue());
        holder.getChildren().add(this.inputVoltage);

        holder.getChildren().add(new Label("Led voltage (V)"));
        this.ledVoltage = new Spinner<>(0.1, 110, 2.2);
        this.ledVoltage.setPrefWidth(80);
        this.ledVoltage.setEditable(true);
        this.ledVoltage.valueProperty().addListener((obs, oldValue, newValue) -> calculateValue());
        holder.getChildren().add(this.ledVoltage);

        holder.getChildren().add(new Label("Led current (A)"));
        this.ledCurrent = new Spinner<>(0.001, 3, 0.02);
        this.ledCurrent.setPrefWidth(80);
        this.ledCurrent.setEditable(true);
        this.ledCurrent.valueProperty().addListener((obs, oldValue, newValue) -> calculateValue());
        holder.getChildren().add(this.ledCurrent);

        this.result = new Label();
        this.result.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
        holder.getChildren().add(this.result);

        this.calculateValue();
    }

    /**
     * Calculate the resistor value when one of the inputs changes
     */
    private void calculateValue() {
        try {
            long value = Calculate.resistorForLed(
                    this.inputVoltage.getValue(),
                    this.ledVoltage.getValue(),
                    this.ledCurrent.getValue());

            this.result.setText("A resistor with value " + Convert.toOhmString((double) value) + " is needed");
        } catch (IllegalArgumentException ex) {
            this.result.setText(ex.getMessage());
        }
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> getApplication().getDrawer().open()));
        appBar.setTitleText("LED resistor calculator");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }
}
