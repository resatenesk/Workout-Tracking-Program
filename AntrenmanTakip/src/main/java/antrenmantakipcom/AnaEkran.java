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

public class AnaEkran {
    static Label isimLabel;
    static Button antrenman_olustur;
    static Button antrenman_ekle;
    static Button antrenman_grafikleri;
    static Button gunluk_besin_ekle;
    static Button besin_grafikleri;
    static Button cikis_yap;
    static String username;
    static BorderPane root;

    public static void setUsername(String username) {
        AnaEkran.username = username;
    }

    public static BorderPane getRoot() {
        root = new BorderPane();
        bilesenler();
        ayarlamalar();
        return root;
    }

    public static void bilesenler() {
        if (AnaEkran.username == null) {
            isimLabel = new Label("Hoşgeldiniz");
            isimLabel.setMinWidth(120);
        } else {
            isimLabel = new Label("Hoşgeldin " + AnaEkran.username + " !");
            isimLabel.setMinWidth(120);
        }

        Image image2 = new Image(Main.class.getResourceAsStream("/ICONS/olustur.png"));
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(20);
        imageView2.setFitHeight(20);

        antrenman_olustur = new Button("Antrenman Oluştur", imageView2);
        antrenman_olustur.setMinWidth(120);
        antrenman_olustur.setOnAction(e -> {
            try {
                AntrenmanTurVeGunBelirlemeEkrani ekran = new AntrenmanTurVeGunBelirlemeEkrani();
                ekran.setUsername(username);
                AnaKontrolEkrani.setRoot(ekran.getRoot());
            } catch (Exception e1) {
            }

        });
        Label label = new Label("Antrenman Oluşturuldu...");
        label.setVisible(false);
        Image image1 = new Image(Main.class.getResourceAsStream("/ICONS/ekle.png"));
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(20);
        imageView1.setFitHeight(20);

        antrenman_ekle = new Button("Antrenmanlar Ekle", imageView1);
        antrenman_ekle.setMinWidth(120);
        antrenman_ekle.setOnAction(e -> {
            AntrenmanEkleme ant = new AntrenmanEkleme(username);
            AnaKontrolEkrani.setRoot(ant.getRoot());
        });

        Image image3 = new Image(Main.class.getResourceAsStream("/ICONS/ikon3.png"));
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitWidth(20);
        imageView3.setFitHeight(20);

        antrenman_grafikleri = new Button("Antrenman Grafikleri", imageView3);
        antrenman_grafikleri.setMinWidth(120);
        antrenman_grafikleri.setOnAction(e -> {
            AntrenmanGrafikleriGoster antGra = new AntrenmanGrafikleriGoster(username);
            AnaKontrolEkrani.setRoot(antGra.getRoot());
        });
        gunluk_besin_ekle = new Button("Günlük Kalori ve Makro Ekle");
        gunluk_besin_ekle.setMinWidth(120);
        besin_grafikleri = new Button("Besin Grafikleri Görüntüle");
        besin_grafikleri.setMinWidth(120);
        cikis_yap = new Button("Çıkış Yap");
        cikis_yap.setMinWidth(120);
        cikis_yap.setOnAction(e -> {
            try {
                AnaKontrolEkrani.setRoot(Main.getRoot());
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
        besin_butonlari_kutusu.getChildren().addAll(gunluk_besin_ekle, besin_grafikleri, cikis_yap);

        genelKutu.getChildren().addAll(isim_goruntuleme_kutusu, antrenman_butonlari_kutusu, besin_butonlari_kutusu);

        root.setCenter(genelKutu);
    }

}
