package antrenmantakipcom.Business.Workout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import antrenmantakipcom.Business.Utilities.Functions.Concrete.AlertFunction;
import antrenmantakipcom.Business.Utilities.Functions.Concrete.CreateButton;
import antrenmantakipcom.DataAccess.Abstract.IMovementDal;
import antrenmantakipcom.DataAccess.Abstract.IRecordsDal;
import antrenmantakipcom.DataAccess.Abstract.IWorkoutDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.MovementDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.RecordsDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.WorkoutTemplateDal;
import antrenmantakipcom.DataAccess.Concrete.Database;
import antrenmantakipcom.Entities.Concrete.Movement;
import antrenmantakipcom.Entities.Concrete.Records;
import antrenmantakipcom.Entities.Concrete.WorkoutTemplate;
import antrenmantakipcom.Main;
import antrenmantakipcom.MainScreen;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ShowWorkoutGraphsScreen {
    private BorderPane root;
    private String username;
    private Button geriDon;
    private int antrenman_id;
    private ObservableList<Integer> antrenmanIDleri = FXCollections.observableArrayList();
    private String antrenman_tipi;
    private ObservableList<String> antrenman_tipleri = FXCollections.observableArrayList();
    private String antrenman_adi;
    private ObservableList<String> hareketler = FXCollections.observableArrayList();
    private VBox genelKutu;
    private VBox veriIstemeKutusu;
    private HBox labellerHbox;
    private HBox combolarHBox;
    private HBox uyariHBox;
    private HBox grafikKutusu;
    private ComboBox antrenman_id_combobox;
    private ComboBox antrenman_tipi_combobox;
    private ComboBox<String> hareket_combobox;
    private int secilen_id;
    private List<String> secilen_antrenman_tipi;
    private ObservableList<String> PPLliste = FXCollections.observableArrayList();
    private ObservableList<String> ULliste = FXCollections.observableArrayList();
    private ObservableList<String> FULLBODYliste = FXCollections.observableArrayList();
    private ObservableList<String> BROSPLITliste = FXCollections.observableArrayList();
    private Label uyariLabel = new Label();
    private Label uyariLabel2 = new Label();
    private String secilen_antrenman_tipi_ozel;
    private ObservableList<String> hareketlerListesi = FXCollections.observableArrayList();
    private String secilen_hareket;

    private IRecordsDal _IRecordsDal;
    private IMovementDal _IMovementDal;
    private IWorkoutDal _WorkoutDal;

    public ShowWorkoutGraphsScreen(String username) {

        _IRecordsDal = new RecordsDal(Records.class);
        _IMovementDal = new MovementDal(Movement.class);
        _WorkoutDal = new WorkoutTemplateDal(WorkoutTemplate.class);

        this.username = username;
        antrenmanIDsiniAl();

        uyariLabel.setText("");
        uyariLabel.setStyle("-fx-font-size:20px");
        uyariLabel2.setText("");
        uyariLabel2.setStyle("-fx-font-size:20px");
        PPLliste.add("Push");
        PPLliste.add("Pull");
        PPLliste.add("Legs");

        ULliste.add("Upper");
        ULliste.add("Lower");

        FULLBODYliste.add("Fullbody");

        BROSPLITliste.add("Gogus");
        BROSPLITliste.add("Kol");
        BROSPLITliste.add("Bacak");
        BROSPLITliste.add("Sırt");
        BROSPLITliste.add("Omuz");

        root = new BorderPane();

        geriDon = CreateButton.createExitButton();
        geriDon.setOnAction(e -> {
            Main.setRoot(MainScreen.getRoot());

        });

        Label baslik = new Label("Training Graphs Screen");
        HBox header = new HBox(baslik);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));

        antrenman_id_combobox = new ComboBox<>();
        antrenman_id_combobox.setPrefWidth(150);
        antrenman_id_combobox.setOnAction(e -> {
            secilen_id = (int) antrenman_id_combobox.getValue();
            secilen_antrenman_tipi = antrenmanTipiniAl(); // artık List<String>
            for (String string : secilen_antrenman_tipi) {
                if (secilen_id > 0) {
                    if (secilen_antrenman_tipi.contains("PPL")) {
                        antrenman_tipi_combobox.setItems(PPLliste);
                    } else if (secilen_antrenman_tipi.contains("UL")) {
                        antrenman_tipi_combobox.setItems(ULliste);
                    } else if (secilen_antrenman_tipi.contains("FULLBODY")) {
                        antrenman_tipi_combobox.setItems(FULLBODYliste);
                    } else if (secilen_antrenman_tipi.contains("BROSPLIT")) {
                        antrenman_tipi_combobox.setItems(BROSPLITliste);
                    } else {
                        antrenman_tipi_combobox.setItems(FXCollections.observableArrayList());
                    }
                }
            }

        });

        uyariHBox = new HBox(10);
        uyariHBox.getChildren().addAll(uyariLabel, uyariLabel2);
        uyariHBox.setAlignment(Pos.BOTTOM_CENTER);
        uyariHBox.setPadding(new Insets(30, 0, 0, 60));
        antrenman_tipi_combobox = new ComboBox<>();
        antrenman_tipi_combobox.setPrefWidth(150);
        antrenman_tipi_combobox.setOnShowing(e -> {
            if ((antrenman_tipi_combobox.getValue() == null) && (antrenman_id_combobox.getValue() == null)) {
                uyariLabel.setText("Please Before Select a Workout ID");
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

        hareket_combobox = new ComboBox<>();
        hareket_combobox.setPrefWidth(200);
        hareket_combobox.setOnShowing(e -> {
            if ((hareket_combobox.getValue() == null) && (antrenman_tipi_combobox.getValue() == null)) {

                uyariLabel2.setText("Choose Your Training Type First!");
                FadeTransition fade = new FadeTransition(Duration.millis(1500), uyariLabel2);
                fade.setFromValue(0);
                fade.setToValue(1);
                fade.play();
                FadeTransition fade2 = new FadeTransition(Duration.millis(3000), uyariLabel2);
                fade2.setFromValue(1);
                fade2.setToValue(0);
                fade2.play();
            }

        });
        antrenman_tipi_combobox.setOnAction(e -> {
            hareketlerListesi.clear();
            hareketleriAl();
            hareket_combobox.setItems(hareketlerListesi);
            hareket_combobox.getSelectionModel().clearSelection();
            secilen_hareket = null;
            grafikKutusu.getChildren().clear();
        });

        hareket_combobox.setOnAction(e -> {
            secilen_hareket = hareket_combobox.getValue();
            if (secilen_hareket != null && !secilen_hareket.isEmpty()) {
                grafikOlustur();
            }
        });

        antrenman_id_combobox.setItems(antrenmanIDleri);
        if (antrenman_id_combobox.getValue() != null) {
            hareketleriAl();
            hareket_combobox.setItems(hareketlerListesi);
        }

        labellerHbox = new HBox(50);
        labellerHbox.setAlignment(Pos.CENTER);
        combolarHBox = new HBox(100);
        combolarHBox.setAlignment(Pos.CENTER);

        Label label1 = new Label("Antrenman ID");
        label1.setStyle("-fx-font-size:25px");
        Label label2 = new Label("Antrenman Type");
        label2.setStyle("-fx-font-size:25px");
        Label label3 = new Label("Movement");
        label3.setStyle("-fx-font-size:25px");
        labellerHbox.getChildren().addAll(label1, label2, label3);

        combolarHBox.getChildren().addAll(antrenman_id_combobox, antrenman_tipi_combobox, hareket_combobox);

        veriIstemeKutusu = new VBox(10);
        veriIstemeKutusu.setPrefHeight(200);
        veriIstemeKutusu.setMaxHeight(200);
        // veriIstemeKutusu.setStyle("-fx-border-width:2px;-fx-border-color:green");
        veriIstemeKutusu.getChildren().addAll(labellerHbox, combolarHBox, uyariHBox);

        grafikKutusu = new HBox();
        grafikKutusu.setAlignment(Pos.CENTER);
        // grafikKutusu.setStyle("-fx-border-width:2px;-fx-border-color:red");
        grafikKutusu.setPrefHeight(600);
        grafikKutusu.setMaxHeight(600);

        genelKutu = new VBox(20);
        genelKutu.setAlignment(Pos.CENTER);
        genelKutu.getChildren().addAll(veriIstemeKutusu, grafikKutusu);
        // genelKutu.setStyle("-fx-border-width:2px;-fx-border-color:blue");

        root.setCenter(genelKutu);
        root.setTop(header);
        root.setBottom(geriDon);

    }

    public void grafikOlustur() {
        if (secilen_hareket == null || secilen_hareket.isEmpty()) {
            grafikKutusu.getChildren().clear();
            return;
        }
        if (!_IRecordsDal.IFRecordsExist(secilen_hareket, secilen_id)) {
            if (secilen_hareket == null || secilen_hareket.isEmpty()) {
                grafikKutusu.getChildren().clear();
                return;
            }
            AlertFunction.ThereIsNoWorkoutThereAlert();
            secilen_hareket = null;
            return;
        }

        double agirlik_ortalamasi = 0;
        double tekrar_ortalamasi = 0;
        String tarih = null;
        try (Connection con = Database.connect()) {
            String sorgu = "SELECT tarih, AVG(agirlik) AS ort_agirlik, AVG(tekrar) AS ort_tekrar FROM kayitlar WHERE hareket_adi = ? GROUP BY tarih ORDER BY tarih";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, secilen_hareket);
            ResultSet rs = ps.executeQuery();

            CategoryAxis xEkseni = new CategoryAxis();
            xEkseni.setLabel("Time(Left to Right Increasing)");
            xEkseni.setTickLabelsVisible(false);

            NumberAxis yEkseni = new NumberAxis();
            yEkseni.setLabel("QA/Repeat");
            yEkseni.setAutoRanging(false);

            LineChart<String, Number> lineChart = new LineChart<>(xEkseni, yEkseni);
            lineChart.setPrefSize(800, 600);
            lineChart.setMaxSize(800, 600);
            lineChart.setTitle(secilen_hareket + " Progress Chart");
            lineChart.setAnimated(true);

            _IRecordsDal.CheckIF(secilen_hareket, yEkseni);

            XYChart.Series<String, Number> agirliklar = new XYChart.Series<>();
            agirliklar.setName("Weights");
            XYChart.Series<String, Number> tekrarlar = new XYChart.Series<>();
            tekrarlar.setName("Reps");

            while (rs.next()) {

                tarih = rs.getString("tarih");
                agirlik_ortalamasi = rs.getDouble("ort_agirlik");
                tekrar_ortalamasi = rs.getDouble("ort_tekrar");
                agirliklar.getData().add(new XYChart.Data<>(tarih, agirlik_ortalamasi));
                tekrarlar.getData().add(new XYChart.Data<>(tarih, tekrar_ortalamasi));

            }
            grafikKutusu.getChildren().clear();
            lineChart.getData().addAll(agirliklar, tekrarlar);
            lineChart.getStylesheets().add(getClass().getResource("/static/style.css").toExternalForm());
            grafikKutusu.getChildren().addAll(lineChart);

        } catch (SQLException ex) {
        }
    }

    public void hareketleriAl() {

        hareketlerListesi.clear();
        String secilenAntrenmanTipi = (String) antrenman_tipi_combobox.getValue();
        if (secilenAntrenmanTipi == null || secilenAntrenmanTipi.isEmpty()) {
            return;
        }
        ObservableList<Movement> hareketler = _IMovementDal
                .GetAll("SELECT * FROM hareketler WHERE antrenman_tur_kategori = ? ", secilenAntrenmanTipi);
        for (Movement movement : hareketler) {
            hareketlerListesi.add(movement.getHareket_adi());
        }

    }

    public List<String> antrenmanTipiniAl() {
        ObservableList<WorkoutTemplate> list = _WorkoutDal
                .GetAll("SELECT * FROM eklenen_antrenman_sablonlari WHERE id = ?", secilen_id);
        List<String> tipler = new ArrayList<>();
        for (WorkoutTemplate addedWorkoutProgram : list) {
            String tip = addedWorkoutProgram.getAntrenmanTipi();
            tipler.add(tip);
        }
        List<String> tip = tipler.stream().distinct().collect(Collectors.toList());
        return tip;

    }

    public void antrenmanIDsiniAl() {
        List<WorkoutTemplate> list = _WorkoutDal.GetAll("SELECT * FROM eklenen_antrenman_sablonlari WHERE username = ?",
                username);
        for (WorkoutTemplate workoutTemplate : list) {
            int id = workoutTemplate.getAntrenmanID();
            antrenmanIDleri.add(id);
        }
    }

    public BorderPane getRoot() {
        root.setId("rootBackgroundGeneral");
        return root;
    }
}