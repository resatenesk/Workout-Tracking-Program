package antrenmantakipcom.Business.Utilities.Functions.Concrete;

import antrenmantakipcom.MainScreen;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageFunction {

    private ImageFunction() {
    }

    public static ImageView LoadImage(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        Image image = new Image(MainScreen.class.getResourceAsStream(path));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        return imageView;
    }

    public static ImageView LoadTooltip(String path,String tooltipText) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        Image image = new Image(ImageFunction.class.getResourceAsStream(path));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-cursor: hand;");
        Tooltip passwordTooltip = new Tooltip(tooltipText);
        Tooltip.install(imageView, passwordTooltip);
        return imageView;
    }
}
