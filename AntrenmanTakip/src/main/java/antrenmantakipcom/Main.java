package antrenmantakipcom;

import antrenmantakipcom.Business.Authorization.UserLoginFrame;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Main extends Application {

    private static Stage primaryStage;
    private static Scene mainScene;
    private static final StackPane rootStack = new StackPane();

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // İlk ekran
        StackPane baslangicRoot = UserLoginFrame.getRoot(); // Ana ekran ilk açılacak ekran

        rootStack.getChildren().add(baslangicRoot); // StackPane'e ekle
        mainScene = new Scene(rootStack, 1200, 1000); // Scene'e StackPane'i ver

        mainScene.getStylesheets().add(getClass().getResource("/static/style.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Antrenman Takip");
        primaryStage.show();
    }

    public static void setRoot(javafx.scene.Node newRoot) {
        double width = mainScene.getWidth();

        newRoot.setTranslateX(width); // yeni root sağdan başlasın
        rootStack.getChildren().add(newRoot);

        // SLIDE IN
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(350), newRoot);
        slideIn.setFromX(width);
        slideIn.setToX(0);

        // SLIDE OUT (varsa)
        if (rootStack.getChildren().size() > 1) {
            Pane eskiRoot = (Pane) rootStack.getChildren().get(0);

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(350), eskiRoot);
            slideOut.setFromX(0);
            slideOut.setToX(-width);

            slideOut.setOnFinished(event -> {
                rootStack.getChildren().remove(eskiRoot);
                
            });

            slideOut.play();
        }

        slideIn.play();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
      
        launch(args);
    }
}
