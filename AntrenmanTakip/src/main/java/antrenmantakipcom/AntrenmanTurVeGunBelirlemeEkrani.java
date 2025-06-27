package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AntrenmanTurVeGunBelirlemeEkrani {

    static String username;
    static ComboBox<String> program_turu_combo_box;
    static ComboBox<Integer> gun_sayisi_combo_box;
    static String program_tipi;
    static int program_gun_sayisi;
    static String secilenTip;
    static Label uyariLabel;
    static Label label1;
    static Label label2;
    static Button button;
    static int antrenman_id;
    static int user_id;
    static Button cikis_yap;
    static BorderPane root;

    public static void setUsername(String username) {
        AntrenmanTurVeGunBelirlemeEkrani.username = username;
    }

    public Pane getRoot() {
        root = new BorderPane();
        bilesenler();
        ayarlamalar();
        return root;
    }

    public void bilesenler() {
        cikis_yap = new Button("Geri Dön");
        cikis_yap.setOnAction(e -> {
            try {
                AnaKontrolEkrani.setRoot(AnaEkran.getRoot());

            } catch (Exception ex) {
            }
        });
        program_turu_combo_box = new ComboBox<>();
        gun_sayisi_combo_box = new ComboBox<>();
        uyariLabel = new Label();
        label1 = new Label("Antrenman Tipini Belirleyiniz:");
        label1.setStyle("-fx-font-size:20px");
        label2 = new Label("Gün sayısını belirleyiniz:");
        label2.setStyle("-fx-font-size: 20px; -fx-padding: 0 0 0 50;");
        uyariLabel.setStyle("-fx-font-size:10px");
        program_turu_combo_box.setPrefWidth(150);
        gun_sayisi_combo_box.setPrefWidth(150);
        program_turu_combo_box.setPrefHeight(35);
        gun_sayisi_combo_box.setPrefHeight(35);
        program_turu_combo_box.setId("program_turu_cb");
        gun_sayisi_combo_box.setId("gun_sayisi_cb");

        databaseBilesenleriEkle();
        button = new Button("+");
        button.setId("antrenman_ekleme");
        button.setOnAction(event -> {

            antrenman_ekle();

        });

        program_turu_combo_box.setOnAction(e -> {

            secilenTip = program_turu_combo_box.getValue();
            gun_sayisi_combo_box.getItems().clear();
            uyariLabel.setText("");

            switch (secilenTip) {
                case "PPL":
                    gun_sayisi_combo_box.getItems().addAll(3, 5, 6);
                    break;
                case "UL":
                    gun_sayisi_combo_box.getItems().add(4);
                    break;
                case "FULLBODY":
                    gun_sayisi_combo_box.getItems().addAll(3, 4);
                    break;
                case "BROSPLIT":
                    gun_sayisi_combo_box.getItems().add(5);
                    break;
            }
        });

        gun_sayisi_combo_box.setOnShowing(event -> {
            if (program_turu_combo_box.getValue() == null) {
                uyariLabel.setText("Önce Program Tipini Belirleyiniz.");
                FadeTransition fade = new FadeTransition(Duration.millis(1500), uyariLabel);
                fade.setFromValue(0);
                fade.setToValue(1);
                fade.play();
                FadeTransition fade2 = new FadeTransition(Duration.millis(3000), uyariLabel);
                fade2.setFromValue(1);
                fade2.setToValue(0);
                fade2.play();
            }

        });
    }

    public static void ayarlamalar() {

        HBox enGenelKutu = new HBox(20);
        enGenelKutu.setAlignment(Pos.CENTER);
        enGenelKutu.setPadding(new Insets(30, 230, 0, 0));

        VBox genelKutu = new VBox(25);
        genelKutu.setAlignment(Pos.CENTER);
        genelKutu.setPadding(new Insets(150, 50, 0, 0));

        VBox labeller = new VBox(20);
        labeller.setPadding(new Insets(0, 0, 30, 370));
        labeller.getChildren().addAll(label1, label2);
        labeller.setAlignment(Pos.CENTER_LEFT);

        genelKutu.getChildren().addAll(program_turu_combo_box, gun_sayisi_combo_box, uyariLabel, button, cikis_yap);

        enGenelKutu.getChildren().addAll(labeller, genelKutu);

        root.setCenter(enGenelKutu);
        root.setLeft(labeller);

    }

    public void antrenman_ekle() {

        String secilen_antrenman_tipi = program_turu_combo_box.getValue();
        Integer secilen_gun_sayisi = gun_sayisi_combo_box.getValue();

        if (secilen_antrenman_tipi == null || secilen_gun_sayisi == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen hem antrenman tipini hem de gün sayısını seçiniz!");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();
            return;
        }
        try (Connection con = Database.connect()) {
            String sorgu = "SELECT user_id FROM users WHERE username= ?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("user_id");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        try (Connection con = Database.connect()) {
            String sorgu = "INSERT INTO eklenen_antrenman_sablonlari (user_id,username,antrenman_tipi,gun_sayisi) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sorgu, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user_id);
            ps.setString(2, username);
            ps.setString(3, secilen_antrenman_tipi);
            ps.setInt(4, secilen_gun_sayisi);

            int result = ps.executeUpdate();
            if (result > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    antrenman_id = generatedKeys.getInt(1);
                }

                AntrenmanOlusturma tabloEkrani = new AntrenmanOlusturma(antrenman_id, user_id, username,
                        secilen_antrenman_tipi,
                        secilen_gun_sayisi);

                AnaKontrolEkrani.setRoot(tabloEkrani.getRoot());

            } else {
                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Bilgi");
                alert2.setHeaderText(null);
                alert2.setContentText("Antrenman Eklenemedi. Tekrar Deneyiniz.");

                DialogPane dialogPane = alert2.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert2.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void databaseBilesenleriEkle() {
        try (Connection con = Database.connect()) {
            String sorgu = "SELECT DISTINCT antrenman_tipi FROM antrenman_programlari_tur_gun";
            PreparedStatement ps1 = con.prepareStatement(sorgu);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                program_tipi = rs1.getString("antrenman_tipi");
                program_turu_combo_box.getItems().addAll(program_tipi);
            }

        } catch (SQLException e) {
            System.out.println("Database bağlantısı kurulamadı!");
            e.printStackTrace();
        }
    }

}
