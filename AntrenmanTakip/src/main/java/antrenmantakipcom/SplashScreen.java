package antrenmantakipcom;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SplashScreen {

    public static void show(Runnable onFinish) {

        Image image = new Image(SplashScreen.class.getResource("/ICONS/rotate.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Image image2 = new Image(SplashScreen.class.getResource("/ICONS/welcome.png").toExternalForm());
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(300);
        imageView2.setFitHeight(100);

        VBox vbox = new VBox(10, imageView,imageView2);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vbox);
        root.setStyle("-fx-background-color: transparent;");
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1000, 400);
        scene.setFill(Color.TRANSPARENT); // Scene’in kendisini de transparan yap
        Stage splashStage = new Stage(StageStyle.TRANSPARENT);
        splashStage.setScene(scene);
        splashStage.setAlwaysOnTop(true);
        splashStage.centerOnScreen();
        splashStage.show();

        // Döndürme animasyonu
        RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), imageView);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.play();

        // 3 saniye sonra splash kapanır, asıl uygulama başlar

        PauseTransition wait = new PauseTransition(Duration.seconds(3));
        wait.setOnFinished(e -> {
            splashStage.close();
            onFinish.run();
        });
        wait.play();
    }
}
