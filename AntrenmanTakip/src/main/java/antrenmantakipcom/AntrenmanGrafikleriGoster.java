package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AntrenmanGrafikleriGoster {
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
    private ComboBox hareket_combobox;
    private int secilen_id;
    private String secilen_antrenman_tipi;
    private ObservableList<String> PPLliste = FXCollections.observableArrayList();
    private ObservableList<String> ULliste = FXCollections.observableArrayList();
    private ObservableList<String> FULLBODYliste = FXCollections.observableArrayList();
    private ObservableList<String> BROSPLITliste = FXCollections.observableArrayList();
    private Label uyariLabel = new Label();
    private Label uyariLabel2 = new Label();
    private String secilen_antrenman_tipi_ozel;
    private ObservableList<String> hareketlerListesi = FXCollections.observableArrayList();
    private String secilen_hareket;

    public AntrenmanGrafikleriGoster(String username) {

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

        Image image = new Image(Main.class.getResourceAsStream("/ICONS/go-back-icon.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        geriDon = new Button("Geri Dön");
        geriDon.setId("cikis_butonlari");
        geriDon.setMinWidth(120);
        geriDon.setOnAction(e -> {
            AnaKontrolEkrani.setRoot(AnaEkran.getRoot());

        });

        Label baslik = new Label("Antrenman Grafikleri Ekranı");
        HBox header = new HBox(baslik);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));

        antrenman_id_combobox = new ComboBox<>();
        antrenman_id_combobox.setPrefWidth(120);
        antrenman_id_combobox.setOnAction(e -> {
            secilen_id = (int) antrenman_id_combobox.getValue();
            secilen_antrenman_tipi = antrenmanTipiniAl();
            if (secilen_id > 0) {
                if (secilen_antrenman_tipi.equals("PPL")) {
                    antrenman_tipi_combobox.setItems(PPLliste);
                }
                if (secilen_antrenman_tipi.equals("UL")) {
                    antrenman_tipi_combobox.setItems(ULliste);
                }
                if (secilen_antrenman_tipi.equals("FULLBODY")) {
                    antrenman_tipi_combobox.setItems(FULLBODYliste);
                }
                if (secilen_antrenman_tipi.equals("BROSPLIT")) {
                    antrenman_tipi_combobox.setItems(BROSPLITliste);
                }
            }

        });
        uyariHBox = new HBox(10);
        uyariHBox.getChildren().addAll(uyariLabel, uyariLabel2);
        uyariHBox.setAlignment(Pos.BOTTOM_CENTER);
        uyariHBox.setPadding(new Insets(30, 0, 0, 60));
        antrenman_tipi_combobox = new ComboBox<>();
        antrenman_tipi_combobox.setPrefWidth(120);
        antrenman_tipi_combobox.setOnShowing(e -> {
            if ((antrenman_tipi_combobox.getValue() == null) && (antrenman_id_combobox.getValue() == null)) {
                uyariLabel.setText("Önce Antrenman ID Seçiniz!");
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
        antrenman_tipi_combobox.setOnAction(e -> {
            hareketlerListesi.clear();
            hareketleriAl();
            hareket_combobox.setItems(hareketlerListesi);
        });
        hareket_combobox = new ComboBox<>();
        hareket_combobox.setPrefWidth(120);
        hareket_combobox.setOnShowing(e -> {
            if ((hareket_combobox.getValue() == null) && (antrenman_tipi_combobox.getValue() == null)) {

                uyariLabel2.setText("Önce Antrenman Tipini Seçiniz!");
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
        hareket_combobox.setOnAction(e -> {
            secilen_hareket = (String) hareket_combobox.getValue();
            System.out.println(secilen_hareket);
            grafikOlustur();
        });

        antrenman_id_combobox.setItems(antrenmanIDleri);
        if (antrenman_id_combobox.getValue() != null) {
            hareketleriAl();
            hareket_combobox.setItems(hareketlerListesi);
        }

        labellerHbox = new HBox(25);
        labellerHbox.setAlignment(Pos.CENTER);
        combolarHBox = new HBox(50);
        combolarHBox.setAlignment(Pos.CENTER);

        Label label1 = new Label("Antrenman ID");
        label1.setStyle("-fx-font-size:20px");
        Label label2 = new Label("Antrenman Tipi");
        label2.setStyle("-fx-font-size:20px");
        Label label3 = new Label("Hareket");
        label3.setStyle("-fx-font-size:20px");
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
        double agirlik_ortalamasi = 0;
        double tekrar_ortalamasi = 0;
        String tarih = null;
        try (Connection con = Database.connect()) {
            String sorgu = "SELECT tarih, AVG(agirlik) AS ort_agirlik, AVG(tekrar) AS ort_tekrar FROM kayitlar WHERE hareket_adi = ? GROUP BY tarih ORDER BY tarih";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, secilen_hareket);
            ResultSet rs = ps.executeQuery();

            CategoryAxis xEkseni = new CategoryAxis();
            xEkseni.setLabel("Zaman(Soldan Sağa Artan)");
            xEkseni.setTickLabelsVisible(false);

            NumberAxis yEkseni = new NumberAxis();
            yEkseni.setLabel("KG/Tekrar");
            yEkseni.setAutoRanging(false);

            if (secilen_hareket.equals("Bench Press")) {
                yEkseni.setLowerBound(60);
                yEkseni.setUpperBound(100);
                yEkseni.setTickUnit(5);
            }
            if (secilen_hareket.equals("Incline Dumbell Press")) {
                yEkseni.setLowerBound(20);
                yEkseni.setUpperBound(50);
                yEkseni.setTickUnit(2.5);
            }
            if (secilen_hareket.equals("High Cable Crossover")) {
                yEkseni.setLowerBound(15);
                yEkseni.setUpperBound(40);
                yEkseni.setTickUnit(5);
            }
            if (secilen_hareket.equals("Dumbell Shoulder Press")) {
                yEkseni.setLowerBound(15);
                yEkseni.setUpperBound(40);
                yEkseni.setTickUnit(2.5);
            }
            if (secilen_hareket.equals("Triceps Dips")) {
                yEkseni.setLowerBound(0);
                yEkseni.setUpperBound(15);
                yEkseni.setTickUnit(1);
            }
            if (secilen_hareket.equals("Lateral Raise")) {
                yEkseni.setLowerBound(0);
                yEkseni.setUpperBound(20);
                yEkseni.setTickUnit(2.5);
            }

            LineChart<String, Number> lineChart = new LineChart<>(xEkseni, yEkseni);
            lineChart.setTitle(secilen_hareket + " İlerleme Grafiği");

            XYChart.Series<String, Number> agirliklar = new XYChart.Series<>();
            agirliklar.setName("Ağırlıklar");
            XYChart.Series<String, Number> tekrarlar = new XYChart.Series<>();
            tekrarlar.setName("Tekrarlar");

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
        try (Connection con = Database.connect()) {
            String sorgu = "SELECT hareket_adi FROM hareketler WHERE antrenman_tur_kategori = ? ";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, (String) antrenman_tipi_combobox.getValue());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hareketlerListesi.add(rs.getString("hareket_adi"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String antrenmanTipiniAl() {
        try (Connection con = Database.connect()) {
            String sorgu2 = "SELECT DISTINCT antrenman_tipi FROM eklenen_antrenman_sablonlari WHERE id = ?";
            PreparedStatement ps2 = con.prepareStatement(sorgu2);
            ps2.setInt(1, secilen_id);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                antrenman_tipi = rs2.getString("antrenman_tipi");
            }
        } catch (SQLException ex) {
        }
        return antrenman_tipi;
    }

    public void antrenmanIDsiniAl() {
        try (Connection con = Database.connect()) {
            String sorgu = "SELECT * FROM eklenen_antrenman_sablonlari WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                antrenmanIDleri.add(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BorderPane getRoot() {
        return root;
    }
}
