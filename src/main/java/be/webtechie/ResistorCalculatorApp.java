package be.webtechie;

import be.webtechie.view.About;
import be.webtechie.view.ColorBandCalculator;
import be.webtechie.view.LedResistorCalculator;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ResistorCalculatorApp extends MobileApplication {

    public static final String PRIMARY_VIEW = HOME_VIEW;
    public static final String SECONDARY_VIEW = "LEDResistorCalculator";
    public static final String THIRD_VIEW = "About";

    @Override
    public void init() {
        addViewFactory(PRIMARY_VIEW, ColorBandCalculator::new);
        addViewFactory(SECONDARY_VIEW, LedResistorCalculator::new);
        addViewFactory(THIRD_VIEW, About::new);
        DrawerManager.buildDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
        scene.getStylesheets()
                .add(ResistorCalculatorApp.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons()
                .add(new Image(ResistorCalculatorApp.class.getResourceAsStream("icon.png")));
    }

    public static void main(String args[]) {
        launch(args);
    }
}
