package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AntrenmanEkleme {

    private static BorderPane root;
    private final Button geriButton;
    private TableView tablo;
    private String username;
    private ObservableList liste;
    private Label antrenmanIDlabel;
    private Label kacinciGunLabel;
    private ComboBox<Integer> kacincıGunComboBox;
    private int gun_sayisi;
    private List<Integer> gun_listesi = new ArrayList<>();
    private VBox icerikSag;
    private VBox hareketlerVbox;
    List<Label> labeller = new ArrayList<>();

    @SuppressWarnings("static-access")
    public AntrenmanEkleme(String username) {

        antrenmanIDlabel = new Label("");
        this.username = username;
        root = new BorderPane();
        root.setPadding(new Insets(20));

        Label baslik = new Label("Antrenman Ekleme Ekranı");
        baslik.setStyle("-fx-font-size:20px");

        HBox header = new HBox(baslik);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-border-width:2px;-fx-border-color:blue");
        root.setTop(header);

        geriButton = new Button("← Geri Dön");
        geriButton.setStyle("-fx-font-size: 14px;");
        geriButton.setOnAction(e -> {
            AnaKontrolEkrani.setRoot(AnaEkran.getRoot());
            try {

            } catch (Exception ex) {

            }
        });

        HBox footer = new HBox(geriButton);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setStyle("-fx-border-width:2px;-fx-border-color:red");
        root.setBottom(footer);

        VBox icerikSol = new VBox(2);

        icerikSol.setMargin(antrenmanIDlabel, Insets.EMPTY);
        icerikSol.setAlignment(Pos.CENTER);
        icerikSol.setStyle("-fx-border-width:2px;-fx-border-color:green");
        icerikSol.setPadding(new Insets(0, 0, 300, 0));
        tablo = tabloyuGetir();
        tablo.setOnMouseClicked(e -> {
            gun_listesi.clear();
            kacincıGunComboBox.getItems().clear();
            KullaniciVeri secilenVeri = (KullaniciVeri) tablo.getSelectionModel().getSelectedItem();
            if (secilenVeri != null) {
                antrenmanIDlabel.setText("Seçilen Antrenman'ın ID'si: " + secilenVeri.getAntrenmanID());
                antrenmanIDlabel.setStyle("-fx-font-size:10px;");
                antrenmanIDlabel.setMinHeight(50);
                antrenmanIDlabel.setMaxHeight(50);
                antrenmanIDlabel.setPrefHeight(50);
                int gun_sayisi = secilenVeri.getGunSayisi();
                for (int i = 1; i < gun_sayisi + 1; i++) {
                    gun_listesi.add(i);
                }
                kacincıGunComboBox.getItems().addAll(gun_listesi);

            } else {
                antrenmanIDlabel.setText("");
            }
        });
        icerikSol.getChildren().addAll(tablo, antrenmanIDlabel);

        icerikSag = new VBox(30);
        icerikSag.setStyle("-fx-border-width:2px;-fx-border-color:green");
        icerikSag.setPrefWidth(500);
        icerikSag.setMaxWidth(500);
        icerikSag.setAlignment(Pos.TOP_CENTER);

        HBox kacinciGunHBox = new HBox(10);
        kacinciGunHBox.setAlignment(Pos.CENTER);

        kacinciGunLabel = new Label("Kaçıncı Güne Hareket Ekleyeceksiniz ?");
        kacinciGunLabel.setStyle("-fx-font-size:15px;");

        kacincıGunComboBox = new ComboBox<>();
        kacincıGunComboBox.setOnAction(e -> {
            Integer secilenGun = kacincıGunComboBox.getValue();
            if (secilenGun != null) {
                hareketleriOlustur();
            }
        });
        hareketlerVbox = new VBox(10);
        hareketlerVbox.setAlignment(Pos.CENTER_LEFT);

        kacinciGunHBox.getChildren().addAll(kacinciGunLabel, kacincıGunComboBox);
        icerikSag.getChildren().addAll(kacinciGunHBox, hareketlerVbox);
        root.setLeft(icerikSol);
        root.setRight(icerikSag);
    }

    public void hareketleriOlustur() {
        KullaniciVeri secilenVeri = (KullaniciVeri) tablo.getSelectionModel().getSelectedItem();
        if (secilenVeri == null)
            return;

        int antrenman_id = secilenVeri.getAntrenmanID();
        Integer secilenGun = kacincıGunComboBox.getValue();
        if (secilenGun == null)
            return;

        labeller.clear(); 
        hareketlerVbox.getChildren().clear(); 

        String sorgu = "SELECT hareket_adi FROM eklenen_antrenman_programlari WHERE antrenman_id = ? AND gun_no = ?";

        try (Connection con = Database.connect();
                PreparedStatement ps = con.prepareStatement(sorgu)) {

            ps.setInt(1, antrenman_id);
            ps.setInt(2, secilenGun);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String hareket_adi = rs.getString("hareket_adi");
                Label label = new Label(hareket_adi);
                label.setStyle("-fx-font-size: 14px; -fx-padding: 5 0 5 10;");
                labeller.add(label);
                hareketlerVbox.getChildren().add(label);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TableView tabloyuGetir() {
        tablo = new TableView<>();
        tablo.setMinHeight(300);
        tablo.setMaxHeight(300);
        tablo.setPrefHeight(300);
        tablo.setPrefWidth(450);
        tablo.setMaxWidth(450);

        tablo.setId("antrenmanTablo");
        TableColumn<KullaniciVeri, Integer> antrenmanIdCol = new TableColumn<>("Antrenman ID");
        antrenmanIdCol.setCellValueFactory(new PropertyValueFactory<>("AntrenmanID"));
        antrenmanIdCol.setId("column1");
        antrenmanIdCol.setPrefWidth(100);

        TableColumn<KullaniciVeri, Integer> userIdCol = new TableColumn<>("Kullanıcı ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        userIdCol.setId("column2");
        antrenmanIdCol.setPrefWidth(100);

        TableColumn<KullaniciVeri, String> usernameCol = new TableColumn<>("İsim");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        usernameCol.setId("column3");
        usernameCol.setPrefWidth(50);

        TableColumn<KullaniciVeri, String> tipCol = new TableColumn<>("Antrenman Tipi");
        tipCol.setCellValueFactory(new PropertyValueFactory<>("AntrenmanTipi"));
        tipCol.setId("column4");
        tipCol.setPrefWidth(100);

        TableColumn<KullaniciVeri, Integer> gunCol = new TableColumn<>("Gün Sayısı");
        gunCol.setCellValueFactory(new PropertyValueFactory<>("GunSayisi"));
        gunCol.setId("column5");
        gunCol.setPrefWidth(100);

        tablo.setItems(veritabaniVerileriCek(this.username));
        Label labelveriyok = new Label("İçeride veri yok :( ");
        labelveriyok.setStyle("-fx-text-fill:black;-fx-font-style:italic;-fx-font-size:10px;");
        tablo.setPlaceholder(labelveriyok);

        tablo.getColumns().addAll(antrenmanIdCol, userIdCol, usernameCol, tipCol, gunCol);
        return tablo;

    }

    public ObservableList veritabaniVerileriCek(String username) {
        liste = FXCollections.observableArrayList();

        int antrenman_id2;
        int user_id2;
        String username2;
        String antrenman_tipi2;
        int gun_sayisi2;

        try (Connection con = Database.connect()) {
            String sorgu = "SELECT * FROM eklenen_antrenman_sablonlari WHERE username=?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                antrenman_id2 = rs.getInt("id");
                user_id2 = rs.getInt("user_id");
                username2 = rs.getString("username");
                antrenman_tipi2 = rs.getString("antrenman_tipi");
                gun_sayisi2 = rs.getInt("gun_sayisi");
                KullaniciVeri veriler = new KullaniciVeri(antrenman_id2, user_id2, username2, antrenman_tipi2,
                        gun_sayisi2);
                liste.add(veriler);

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return liste;
    }

    public BorderPane getRoot() {
        return root;
    }
}
