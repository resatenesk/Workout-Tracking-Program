package antrenmantakipcom;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainScreen {
    static Label isimLabel;
    static Button antrenman_olustur;
    static Button antrenman_ekle;
    static Button antrenman_grafikleri;
    static Button gunluk_besin_ekle;
    static Button besin_grafikleri;
    static Button cikis_yap;
    static String username;
    static BorderPane root;
    static Button createMealButton;
   

    public static void setUsername(String username) {
        MainScreen.username = username;
    }

    
    public static BorderPane getRoot() {

        root = new BorderPane();
        bilesenler();
        ayarlamalar();
        return root;
    }

    public static void bilesenler() {

        if (MainScreen.username == null) {
            isimLabel = new Label("Welcome");
            isimLabel.setMinWidth(120);
        } else {
            isimLabel = new Label("Welcome " + MainScreen.username.substring(0, 1).toUpperCase()
                    + MainScreen.username.substring(1, username.length()) + " !");
            isimLabel.setMinWidth(120);
        }

        Image image2 = new Image(UserLoginFrame.class.getResourceAsStream("/ICONS/olustur.png"));
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);

        antrenman_olustur = new Button("Create Workout Program", imageView2);
        antrenman_olustur.setMinWidth(150);
        antrenman_olustur.setMinHeight(50);

        antrenman_olustur.setOnAction(e -> {
            try {
                WorkoutStyleAndDaySelectionScreen ekran = new WorkoutStyleAndDaySelectionScreen();
                ekran.setUsername(username);
                Main.setRoot(ekran.getRoot());
            } catch (Exception e1) {
            }

        });
        Label label = new Label("Antrenman Oluşturuldu...");
        label.setVisible(false);
        Image image1 = new Image(UserLoginFrame.class.getResourceAsStream("/ICONS/ekle.png"));
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);

        antrenman_ekle = new Button("Add Workout", imageView1);
        antrenman_ekle.setMinWidth(150);
        antrenman_ekle.setMinHeight(50);
        antrenman_ekle.setOnAction(e -> {
            AddWorkoutScreen ant = new AddWorkoutScreen(username);
            Main.setRoot(ant.getRoot());
        });

        Image image3 = new Image(UserLoginFrame.class.getResourceAsStream("/ICONS/ikon3.png"));
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitWidth(30);
        imageView3.setFitHeight(30);

        antrenman_grafikleri = new Button("Workout Graphics", imageView3);
        antrenman_grafikleri.setMinWidth(150);
        antrenman_grafikleri.setMinHeight(50);

        antrenman_grafikleri.setOnAction(e -> {
            ShowWorkoutGraphsScreen antGra = new ShowWorkoutGraphsScreen(username);
            Main.setRoot(antGra.getRoot());
        });

        Image image4 = new Image(UserLoginFrame.class.getResourceAsStream("/ICONS/food.png"));
        ImageView imageView4 = new ImageView(image4);
        imageView4.setFitWidth(30);
        imageView4.setFitHeight(30);
        gunluk_besin_ekle = new Button("Add Daily Food Values", imageView4);
        gunluk_besin_ekle.setOnAction(e -> {
            DailyMacroAndFoodValuesScreen daily = new DailyMacroAndFoodValuesScreen(username);
            Main.setRoot(daily.getPane());
        });
        gunluk_besin_ekle.setMinWidth(150);
        gunluk_besin_ekle.setMinHeight(50);

        Image image5 = new Image(UserLoginFrame.class.getResourceAsStream("/ICONS/meal.png"));
        ImageView imageView5 = new ImageView(image5);
        imageView5.setFitWidth(30);
        imageView5.setFitHeight(30);

        createMealButton = new Button("Create Special Meals/Foods", imageView5);
        createMealButton.setMinWidth(150);
        createMealButton.setMinHeight(50);
        createMealButton.setOnAction(e -> {
            CreateSpecialMealCreateSpecialFood meal = new CreateSpecialMealCreateSpecialFood(username);
            Main.setRoot(meal.getPane());
        });

        Image image6 = new Image(UserLoginFrame.class.getResourceAsStream("/ICONS/calgraph.png"));
        ImageView imageView6 = new ImageView(image6);
        imageView6.setFitWidth(30);
        imageView6.setFitHeight(30);

        besin_grafikleri = new Button("Show Food Graphics", imageView6);
        besin_grafikleri.setOnAction(e -> {
            ShowFoodGraphs graphs = new ShowFoodGraphs(username);
            Main.setRoot(graphs.getPane());
        });
        besin_grafikleri.setMinWidth(150);
        besin_grafikleri.setMinHeight(50);

        Image imageC = new Image(MainScreen.class.getResourceAsStream("/ICONS/logout.png"));
        ImageView imageViewC = new ImageView(imageC);
        imageViewC.setFitWidth(30);
        imageViewC.setFitHeight(30);
        cikis_yap = new Button("Exit", imageViewC);
        cikis_yap.setId("cikis_butonlari");

        cikis_yap.setMinWidth(150);
        cikis_yap.setOnAction(e -> {
            try {
                Main.setRoot(UserLoginFrame.getRoot());
            } catch (Exception ex) {
            }
        });

    }

    public static void gosterBasariMesaji(String mesaj) {
        Label bildirimLabel = new Label(mesaj);
        bildirimLabel.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 10px;");
        bildirimLabel.setOpacity(0); // ilk başta görünmez

        // Fade In
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), bildirimLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Fade Out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(800), bildirimLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(2)); // 2 saniye bekle sonra kaybol

        VBox altKutu = new VBox(bildirimLabel);
        altKutu.setAlignment(Pos.BOTTOM_CENTER);
        altKutu.setPadding(new Insets(10));

        root.setBottom(altKutu); // ekranın altına ekle

        fadeIn.setOnFinished(event -> fadeOut.play());
        fadeIn.play();
    }

    public static void ayarlamalar() {
        VBox genelKutu = new VBox(30);
        genelKutu.setAlignment(Pos.CENTER);

        HBox isim_goruntuleme_kutusu = new HBox(10);
        isim_goruntuleme_kutusu.setAlignment(Pos.TOP_CENTER);
        isim_goruntuleme_kutusu.setPadding(new Insets(-200, 0, 0, 0));
        isim_goruntuleme_kutusu.getChildren().addAll(isimLabel);

        HBox antrenman_butonlari_kutusu = new HBox(20);
        antrenman_butonlari_kutusu.setAlignment(Pos.CENTER);
        antrenman_butonlari_kutusu.getChildren().addAll(antrenman_olustur, antrenman_ekle, antrenman_grafikleri);

        HBox besin_butonlari_kutusu = new HBox(20);
        besin_butonlari_kutusu.setAlignment(Pos.CENTER);
        besin_butonlari_kutusu.getChildren().addAll(gunluk_besin_ekle, createMealButton, besin_grafikleri);

        genelKutu.getChildren().addAll(isim_goruntuleme_kutusu, antrenman_butonlari_kutusu, besin_butonlari_kutusu);

        root.setCenter(genelKutu);
        root.setBottom(cikis_yap);
    }

}
