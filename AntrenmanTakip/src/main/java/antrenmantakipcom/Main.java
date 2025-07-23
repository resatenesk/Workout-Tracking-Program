package antrenmantakipcom;

import antrenmantakipcom.Business.Authorization.UserLoginFrame;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
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

        StackPane baslangicRoot = UserLoginFrame.getRoot(); // giriş ekranı
        rootStack.getChildren().add(baslangicRoot);

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

        TranslateTransition slideOut = new TranslateTransition(Duration.millis(200), eskiRoot);
        slideOut.setFromX(0);
        slideOut.setToX(-width);

        slideOut.setOnFinished(event -> {
            rootStack.getChildren().remove(eskiRoot);

            newRoot.setTranslateX(width);
            rootStack.getChildren().add(newRoot);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(350), newRoot);
            slideIn.setFromX(width);
            slideIn.setToX(0);
            slideIn.play();
        });

        slideOut.play();
    } else {
        newRoot.setTranslateX(width);
        rootStack.getChildren().add(newRoot);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(350), newRoot);
        slideIn.setFromX(width);
        slideIn.setToX(0);
        slideIn.play();
    }
}


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {

        launch(args);
    }
}
