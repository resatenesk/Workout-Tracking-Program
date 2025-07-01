package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AntrenmanEkleme {

    private static BorderPane root;
    private final Button geriButton;
    private TableView<KullaniciVeri> tablo;
    private String username;
    private ObservableList<KullaniciVeri> liste;
    private Label antrenmanIDlabel;
    private Label kacinciGunLabel;
    private ComboBox<Integer> kacincıGunComboBox;
    private int gun_sayisi;
    private List<Integer> gun_listesi = new ArrayList<>();
    private VBox icerikSag;
    private VBox icerikSol;
    private VBox hareketlerVbox;
    private VBox textFieldVBox;
    List<Label> labeller = new ArrayList<>();
    List<HBox> hareketSatirlari = new ArrayList<>();
    private ImageView infoIcon;
    private Image infoImage;
    private Button ekleButton;
    private int antrenman_id;
    private int secilenGun = 0;
    private DatePicker datePicker;
    private LocalDate secilenTarih;
    private Button antrenmanSil;
    private VBox sagdanGelecekPanel;
    private Button ozelYiyecekListeGoruntule;

    @SuppressWarnings({ "static-access", "SuspiciousToArrayCall", "CollectionsToArray" })
    public AntrenmanEkleme(String username) {

        antrenmanIDlabel = new Label("");
        this.username = username;
        root = new BorderPane();
        root.setPadding(new Insets(20));

        datePicker = new DatePicker();
        datePicker.setPromptText("Tarih Seçiniz");

        Label baslik = new Label("Antrenman Ekleme Ekranı");
        baslik.setStyle("-fx-font-size:20px");

        HBox header = new HBox(baslik);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        // header.setStyle("-fx-border-width:2px;-fx-border-color:blue");
        root.setTop(header);

        Image imageC = new Image(AnaEkran.class.getResourceAsStream("/ICONS/logout.png"));
        ImageView imageViewC = new ImageView(imageC);
        imageViewC.setFitWidth(20);
        imageViewC.setFitHeight(20);

        geriButton = new Button("← Geri Dön");
        geriButton.setId("cikis_butonlari");
        geriButton.setStyle("-fx-font-size: 14px;");
        geriButton.setOnAction(e -> {
            AnaEkran anaEkran = new AnaEkran();
            AnaKontrolEkrani.setRoot(anaEkran.getRoot());
        });

        HBox footer = new HBox(geriButton);
        footer.setAlignment(Pos.CENTER_RIGHT);
        // footer.setStyle("-fx-border-width:2px;-fx-border-color:red");
        root.setBottom(footer);

        icerikSol = new VBox(2);
        icerikSol.setMargin(antrenmanIDlabel, Insets.EMPTY);
        icerikSol.setAlignment(Pos.CENTER);
        // icerikSol.setStyle("-fx-border-width:2px;-fx-border-color:green");
        icerikSol.setPadding(new Insets(0, 0, 300, 0));
        tablo = tabloyuGetir();
        tablo.setOnMouseClicked(e -> {
            gun_listesi.clear();
            kacincıGunComboBox.getItems().clear();
            KullaniciVeri secilenVeri = (KullaniciVeri) tablo.getSelectionModel().getSelectedItem();
            if (secilenVeri != null) {
                gun_sayisi = secilenVeri.getGunSayisi();
                antrenman_id = secilenVeri.getAntrenmanID();

                for (int i = 1; i < gun_sayisi + 1; i++) {
                    gun_listesi.add(i);
                }
                kacincıGunComboBox.getItems().addAll(gun_listesi);

            }

        });

        antrenmanSil = new Button("Delete Selected Workout");
        antrenmanSil.setOnAction(e -> {
            tablodanVeriSil();

        });

        ekleButton = new Button("EKLE");
        ekleButton.setVisible(false);
        ekleButton.setOnAction(e -> {

            secilenTarih = datePicker.getValue();
            if (secilenTarih != null) {
                veritabaniVerileriAktar();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Tarih Seçilmedi!");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen Tarih Seçiniz.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            }
            secilenTarih = null;

        });

        icerikSol.getChildren().addAll(tablo, antrenmanIDlabel, datePicker, antrenmanSil);

        icerikSag = new VBox(30);
        // icerikSag.setStyle("-fx-border-width:2px;-fx-border-color:green");
        icerikSag.setPrefWidth(700);
        icerikSag.setMaxWidth(700);
        icerikSag.setAlignment(Pos.TOP_CENTER);

        infoImage = new Image(getClass().getResource("/ICONS/info.png").toExternalForm());
        infoIcon = new ImageView(infoImage);
        infoIcon.setStyle(
                "-fx-text-fill: #007acc; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 16px; " +
                        "-fx-cursor: hand;");
        infoIcon.setFitHeight(20);
        infoIcon.setFitWidth(20);
        infoIcon.setPreserveRatio(true);
        infoIcon.setStyle("-fx-cursor: hand;");
        Tooltip passwordTooltip = new Tooltip(
                "Lütfen ağrılıkları ve tekrar sayılarını aralarda virgül bırakarak giriniz.");
        Tooltip.install(infoIcon, passwordTooltip);

        HBox kacinciGunHBox = new HBox(10);
        kacinciGunHBox.setAlignment(Pos.CENTER);
        // kacinciGunHBox.setStyle("-fx-border-width:2px;-fx-border-color:green");

        kacinciGunLabel = new Label("Kaçıncı Güne Hareket Ekleyeceksiniz ?");
        kacinciGunLabel.setStyle("-fx-font-size:15px;");

        textFieldVBox = new VBox(5);
        textFieldVBox.setAlignment(Pos.CENTER);

        kacincıGunComboBox = new ComboBox<>();
        kacincıGunComboBox.setOnAction(e -> {
            secilenGun = kacincıGunComboBox.getValue();
            if (secilenGun != 0) {
                hareketleriOlustur();
                if (!icerikSag.getChildren().contains(textFieldVBox)) {
                    icerikSag.getChildren().add(textFieldVBox);
                }
            }
        });
        icerikSag.getChildren().addAll(kacinciGunHBox);
        kacinciGunHBox.getChildren().addAll(kacinciGunLabel, kacincıGunComboBox, infoIcon);
        root.setLeft(icerikSol);
        root.setRight(icerikSag);
        root.setBottom(geriButton);

    }

    public void tablodanVeriSil() {
        KullaniciVeri secilenVeri = (KullaniciVeri) tablo.getSelectionModel().getSelectedItem();
        if (secilenVeri != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Onay");
            alert.setHeaderText(null);
            alert.setContentText("Onaylıyor musunuz?");
            Optional<ButtonType> result1 = alert.showAndWait();

            if (result1.isPresent() && result1.get() == ButtonType.OK) {
                int id = secilenVeri.getAntrenmanID();
                try (Connection con = Database.connect()) {
                    String sorgu = "DELETE FROM eklenen_antrenman_sablonlari WHERE id= ?";
                    PreparedStatement ps = con.prepareStatement(sorgu);
                    ps.setInt(1, id);
                    int result = ps.executeUpdate();
                    if (result > 0) {
                        KullaniciVeri veri = tablo.getSelectionModel().getSelectedItem();
                        liste.remove(veri);
                    }
                } catch (SQLException e) {
                }
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Hareket Yok!");
            alert.setHeaderText(null);
            alert.setContentText("Şablon seçilmedi !");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();

        }
    }

    public void veritabaniVerileriAktar() {
        System.out.println("Toplam hareket satırı sayısı: " + hareketSatirlari.size());
        if (secilenGun == 0) {
            System.out.println("HATA: Lütfen bir gün seçin!");
            return;
        }

        // === TÜM SATIRLARDA BOŞLUK KONTROLÜ ===
        boolean bosVeriVarMi = false;
        for (HBox veri : hareketSatirlari) {
            if (veri.getChildren().size() < 3)
                continue;

            VBox column2 = (VBox) veri.getChildren().get(1);
            VBox column3 = (VBox) veri.getChildren().get(2);

            if (column2.getChildren().isEmpty() || column3.getChildren().isEmpty())
                continue;

            TextField agirlikTextField = (TextField) column2.getChildren().get(0);
            TextField tekrarTextField = (TextField) column3.getChildren().get(0);

            if (agirlikTextField.getText().isBlank() || tekrarTextField.getText().isBlank()) {
                bosVeriVarMi = true;
                break; // Bir tane bile boş varsa çık
            }
        }

        if (bosVeriVarMi) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Veri Eksik!");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen tüm ağırlık ve tekrar bilgilerini doldurun.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();
            return; // Boş veri varsa kayıt yapma
        }

        // === VERİTABANINA KAYIT KISMI ===
        try (Connection con = Database.connect()) {
            String sorgu = "INSERT INTO kayitlar (username, antrenman_id, gun_no, hareket_adi, set_no, agirlik, tekrar, tarih) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sorgu);

            for (HBox veri : hareketSatirlari) {
                if (veri.getChildren().size() < 3)
                    continue;

                VBox column1 = (VBox) veri.getChildren().get(0);
                VBox column2 = (VBox) veri.getChildren().get(1);
                VBox column3 = (VBox) veri.getChildren().get(2);

                if (column1.getChildren().isEmpty() || column2.getChildren().isEmpty()
                        || column3.getChildren().isEmpty())
                    continue;

                Label hareketLabel = (Label) column1.getChildren().get(0);
                TextField agirlikTextField = (TextField) column2.getChildren().get(0);
                TextField tekrarTextField = (TextField) column3.getChildren().get(0);

                String hareket_adi = hareketLabel.getText();
                String agirliklar = agirlikTextField.getText();
                String tekrarlar = tekrarTextField.getText();

                String[] kgArray = agirliklar.split(",");
                String[] tekrarArray = tekrarlar.split(",");

                if (kgArray.length != tekrarArray.length) {
                    System.out.println("HATA: " + hareket_adi + " için set ve tekrar sayısı eşleşmiyor!");
                    continue;
                }

                LocalDate secilenTarih = datePicker.getValue();

                for (int i = 0; i < kgArray.length; i++) {
                    try {
                        double kg = Double.parseDouble(kgArray[i].trim());
                        int tekrar = Integer.parseInt(tekrarArray[i].trim());

                        ps.setString(1, username);
                        ps.setInt(2, antrenman_id);
                        ps.setInt(3, secilenGun);
                        ps.setString(4, hareket_adi);
                        ps.setInt(5, i + 1);
                        ps.setDouble(6, kg);
                        ps.setInt(7, tekrar);
                        ps.setDate(8, java.sql.Date.valueOf(secilenTarih));

                        int result = ps.executeUpdate();

                    } catch (NumberFormatException ex) {
                        System.out.println("HATA: Sayı parse edilemedi! (" + hareket_adi + ", Set " + (i + 1) + ")");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Veriler Başarıyla Kaydedildi !");
        alert.setHeaderText(null);
        alert.setContentText(
                "Antrenman Gelişiminizi \"Antrenman Grafikleri\" Bölümünden Takip Edebilirsiniz.");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets()
                .add(getClass().getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();
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
        hareketSatirlari.clear();
        textFieldVBox.getChildren().clear();

        String sorgu = "SELECT hareket_adi FROM eklenen_antrenman_programlari WHERE antrenman_id = ? AND gun_no = ?";

        try (Connection con = Database.connect();
                PreparedStatement ps = con.prepareStatement(sorgu)) {

            ps.setInt(1, antrenman_id);
            ps.setInt(2, secilenGun);

            ResultSet rs = ps.executeQuery();

            int satirIndex = 0; // satır indeksi takip için

            while (rs.next()) {
                String hareket_adi = rs.getString("hareket_adi");

                Label label = new Label(hareket_adi);
                label.setStyle("-fx-font-size: 14px; -fx-padding: 5 0 5 10;");

                TextField textFieldSol = new TextField();
                textFieldSol.setPromptText("Kg");

                TextField textFieldSag = new TextField();
                textFieldSag.setPromptText("Tekrar");

                HBox satir = new HBox(20);

                VBox column1 = new VBox(label);
                VBox column2 = new VBox(textFieldSol);
                VBox column3 = new VBox(textFieldSag);
                satir.getChildren().addAll(column1, column2, column3);

                hareketSatirlari.add(satir);
                textFieldVBox.getChildren().addAll(satir);
                ekleButton.setVisible(true);
                if (!icerikSag.getChildren().contains(textFieldVBox)) {
                    icerikSag.getChildren().add(textFieldVBox);
                }
                if (!icerikSag.getChildren().contains(ekleButton)) {
                    icerikSag.getChildren().add(ekleButton);
                }

                // Enter eventlerini ekleyelim

                final int currentIndex = satirIndex; // final değişken

                textFieldSol.setOnAction(e -> {
                    textFieldSag.requestFocus();
                });

                textFieldSag.setOnAction(e -> {
                    // Sonraki satır varsa oradaki sol textfield'a geç
                    int nextIndex = currentIndex + 1;
                    if (nextIndex < hareketSatirlari.size()) {
                        HBox nextSatir = hareketSatirlari.get(nextIndex);
                        VBox nextColumn2 = (VBox) nextSatir.getChildren().get(1);
                        if (!nextColumn2.getChildren().isEmpty()) {
                            TextField nextTextFieldSol = (TextField) nextColumn2.getChildren().get(0);
                            nextTextFieldSol.requestFocus();
                        }
                    }
                });

                satirIndex++;
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

    public ObservableList<KullaniciVeri> veritabaniVerileriCek(String username) {
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
