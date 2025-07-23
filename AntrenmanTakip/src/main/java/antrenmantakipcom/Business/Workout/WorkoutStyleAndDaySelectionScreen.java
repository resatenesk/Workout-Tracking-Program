package antrenmantakipcom.Business.Workout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.Business.Utilities.Functions.Concrete.AlertFunction;
import antrenmantakipcom.Business.Utilities.Functions.Concrete.CreateButton;
import antrenmantakipcom.DataAccess.Abstract.IUserDal;
import antrenmantakipcom.DataAccess.Abstract.IWorkoutDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.UserDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.WorkoutTemplateDal;
import antrenmantakipcom.DataAccess.Concrete.Database;
import antrenmantakipcom.Entities.Concrete.User;
import antrenmantakipcom.Entities.Concrete.WorkoutTemplate;
import antrenmantakipcom.Main;
import antrenmantakipcom.MainScreen;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class WorkoutStyleAndDaySelectionScreen {

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
    static IUserDal _IUserDal = new UserDal(User.class);
    static IWorkoutDal _WorkoutDal = new WorkoutTemplateDal(WorkoutTemplate.class);

    public static void setUsername(String username) {
        WorkoutStyleAndDaySelectionScreen.username = username;
    }

    public Pane getRoot() {
        root = new BorderPane();
        bilesenler();
        ayarlamalar();
        return root;
    }

    public void bilesenler() {

        cikis_yap = CreateButton.createExitButton();
        cikis_yap.setOnAction(e -> {
            try {
                Main.setRoot(MainScreen.getRoot());

            } catch (Exception ex) {
            }
        });
        program_turu_combo_box = new ComboBox<>();
        gun_sayisi_combo_box = new ComboBox<>();
        uyariLabel = new Label();
        label1 = new Label("Determine Training Type:");
        label1.setStyle("-fx-font-size:20px");
        label2 = new Label("Specify the number of days:");
        label2.setStyle("-fx-font-size: 20px; -fx-padding: 0 0 0 0;");
        uyariLabel.setStyle("-fx-font-size:10px");
        program_turu_combo_box.setPrefWidth(150);
        gun_sayisi_combo_box.setPrefWidth(150);
        program_turu_combo_box.setPrefHeight(35);
        gun_sayisi_combo_box.setPrefHeight(35);
        program_turu_combo_box.setId("program_turu_cb");
        gun_sayisi_combo_box.setId("gun_sayisi_cb");

        databaseBilesenleriEkle();
        button = CreateButton.createSaveButton();
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
                uyariLabel.setText("First, Determine the Program Type.");
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
            AlertFunction.MissingDataAlert();
            return;
        }
        User user = new User();
        user.setUsername(username);
        int user_id = _IUserDal.selectUserID(user);
        WorkoutTemplate program = new WorkoutTemplate(user_id, username, secilen_antrenman_tipi, secilen_gun_sayisi);
        System.out.println(user_id + " " + username + " " + secilen_antrenman_tipi + " " + secilen_gun_sayisi);
        int result = _WorkoutDal.Add(program);
        if (result > 0) {
            CreatingWorkoutsScreen tabloEkrani = new CreatingWorkoutsScreen(antrenman_id, user_id, username,
                    secilen_antrenman_tipi,
                    secilen_gun_sayisi);

            Main.setRoot(tabloEkrani.getRoot());

        } else {
            AlertFunction.FailAlert();
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
            e.printStackTrace();
        }
    }

}
