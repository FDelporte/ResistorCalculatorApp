package be.webtechie;

import static be.webtechie.ResistorCalculatorApp.PRIMARY_VIEW;
import static be.webtechie.ResistorCalculatorApp.SECONDARY_VIEW;
import static be.webtechie.ResistorCalculatorApp.THIRD_VIEW;

import com.gluonhq.attach.lifecycle.LifecycleService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.image.Image;

public class DrawerManager {

    public static void buildDrawer(MobileApplication app) {
        NavigationDrawer drawer = app.getDrawer();

        NavigationDrawer.Header header = new NavigationDrawer.Header(
                "Resistor calculator",
                "By webtechie.be",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("resistor.jpg"))));
        drawer.setHeader(header);

        drawer.getItems().addAll(
                new ViewItem(
                        "Resistor color calculator",
                        MaterialDesignIcon.COLOR_LENS.graphic(),
                        PRIMARY_VIEW,
                        ViewStackPolicy.SKIP),
                new ViewItem(
                        "LED resistor calculator",
                        MaterialDesignIcon.LIGHTBULB_OUTLINE.graphic(),
                        SECONDARY_VIEW),
                new ViewItem(
                        "About",
                        MaterialDesignIcon.INFO.graphic(),
                        THIRD_VIEW));

        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
    }
}
