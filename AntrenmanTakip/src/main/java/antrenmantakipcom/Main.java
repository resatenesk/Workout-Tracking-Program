package antrenmantakipcom;

import antrenmantakipcom.Business.Authorization.UserLoginFrame;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static Stage primaryStage;
    private static Scene mainScene;
    private static final StackPane rootStack = new StackPane();

    @Override
    public void start(Stage stage) throws Exception {
        SplashScreen.show(() -> {
            primaryStage = stage;
            Image icon = new Image(getClass().getResourceAsStream("/ICONS/ikon3.png"));
            primaryStage.getIcons().add(icon);

            StackPane baslangicRoot = UserLoginFrame.getRoot(); // giriş ekranı
            rootStack.getChildren().add(baslangicRoot);
            rootStack.setId("rootBackgroundGeneral");

            mainScene = new Scene(rootStack, 1200, 800);
            mainScene.getStylesheets().add(getClass().getResource("/static/style.css").toExternalForm());

            primaryStage.setScene(mainScene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Workout Tracking App From Reshad Ghazi");
            primaryStage.show();
        });
    }

    public static void setRoot(javafx.scene.Node newRoot) {
        double width = mainScene.getWidth();

        if (!rootStack.getChildren().isEmpty()) {
            javafx.scene.Node eskiRoot = rootStack.getChildren().get(0);

            // Slide-out + Fade-out eskiRoot
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(200), eskiRoot);
            slideOut.setFromX(0);
            slideOut.setToX(-width);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), eskiRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            ParallelTransition outTransition = new ParallelTransition(slideOut, fadeOut);

            outTransition.setOnFinished(event -> {
                rootStack.getChildren().remove(eskiRoot);
                rootStack.getChildren().clear();
                newRoot.setTranslateX(width);
                newRoot.setOpacity(0); // Şeffaf başlasın
                rootStack.getChildren().add(newRoot);

                // Slide-in + Fade-in newRoot
                TranslateTransition slideIn = new TranslateTransition(Duration.millis(350), newRoot);
                slideIn.setFromX(width);
                slideIn.setToX(0);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(350), newRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);

                ParallelTransition inTransition = new ParallelTransition(slideIn, fadeIn);
                inTransition.play();
            });

            outTransition.play();
        } else {
            newRoot.setTranslateX(width);
            newRoot.setOpacity(0); // Şeffaf başlasın
            rootStack.getChildren().add(newRoot);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(350), newRoot);
            slideIn.setFromX(width);
            slideIn.setToX(0);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(350), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            ParallelTransition inTransition = new ParallelTransition(slideIn, fadeIn);
            inTransition.play();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {

        launch(args);
    }
}
