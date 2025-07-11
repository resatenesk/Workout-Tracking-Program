package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.controlsfx.control.CheckComboBox;

import antrenmantakipcom.DataAccess.Concrete.Database;
import antrenmantakipcom.Entities.Concrete.WorkoutTemplate;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CreatingWorkoutsScreen {
    private static BorderPane root;
    private int antrenman_id;
    private int user_id;
    private String username;
    private String antrenman_tipi;
    private int gun_sayisi;
    private static TableView<WorkoutTemplate> tablo;
    private static Label label1;
    private static VBox checkboxRoot;
    private static HBox labelRoot;
    private ArrayList<String> pushday;
    private ArrayList<String> pullday;
    private ArrayList<String> legday;
    private ArrayList<String> upperday;
    private ArrayList<String> lowerday;
    private ArrayList<String> fullbody;
    private ArrayList<String> gogus;
    private ArrayList<String> kol;
    private ArrayList<String> bacak;
    private ArrayList<String> sırt;
    private ArrayList<String> omuz;
    private ObservableList<String> hareketList;
    private VBox gunKutusu;
    private List<CheckComboBox<String>> comboBoxListesi;
    private Map<Integer, List<String>> gun_hareket_map;
    private static Button ekleButton;
    private static Button onaylaButton;
    private ScrollPane scrollableCheckboxArea;
    private static VBox hareketleriGostermeKutusu;
    private int gun_no;
    private static Button tablodanSilButton;
    ObservableList<WorkoutTemplate> liste;

    public CreatingWorkoutsScreen(int antrenman_id, int user_id, String username, String antrenman_tipi, int gun_sayisi) {
        this.antrenman_id = antrenman_id;
        this.user_id = user_id;
        this.username = username;
        this.antrenman_tipi = antrenman_tipi;
        this.gun_sayisi = gun_sayisi;

        bilesenler();

    }

    public void tablodanVeriSil() {
        int id = tablo.getSelectionModel().getSelectedItem().getAntrenmanID();
        try (Connection con = Database.connect()) {
            String sorgu = "DELETE FROM eklenen_antrenman_sablonlari WHERE id= ?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result > 0) {
                WorkoutTemplate veri = tablo.getSelectionModel().getSelectedItem();
                liste.remove(veri);
            }
        } catch (SQLException e) {
        }
    }

    @SuppressWarnings("unchecked")
    public void bilesenler() {
        tablodanSilButton = new Button("Antrenman şablonunu sil");
        tablodanSilButton.setOnAction(e -> {
            tablodanVeriSil();
        });
        Image imageC = new Image(MainScreen.class.getResourceAsStream("/ICONS/logout.png"));
        ImageView imageViewC = new ImageView(imageC);
        imageViewC.setFitWidth(20);
        imageViewC.setFitHeight(20);
       
        label1 = new Label(username.substring(0, 1).toUpperCase() + username.substring(1, username.length())
                + " adlı kişinin eklediği antrenman şablonları:");
        label1.setStyle("-fx-font-size:15px;-fx-alignment:center;-fx-font-style:italic");

        tablo = new TableView<>();
        tablo.setPrefHeight(300);
        tablo.setPrefWidth(450);
        tablo.setMaxWidth(450);
        tablo.setId("antrenmanTablo");
        // tablo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablo.setOnMouseClicked(e -> {
            WorkoutTemplate secilenVeri = tablo.getSelectionModel().getSelectedItem();
            if (secilenVeri == null) {
                return;
            }
            this.antrenman_tipi = secilenVeri.getAntrenmanTipi();
            this.gun_sayisi = secilenVeri.getGunSayisi();
            if (secilenVeri != null) {
                int gunSayisi = secilenVeri.getGunSayisi();
                switch (secilenVeri.getAntrenmanTipi()) {
                    case "PPL":
                        checkboxOlustur(gunSayisi);
                        break;
                    case "UL":
                        checkboxOlustur(gun_sayisi);
                        break;
                    case "FULLBODY":
                        checkboxOlustur(gun_sayisi);
                        break;
                    default:
                        checkboxOlustur(gun_sayisi);
                        break;
                }
                ekleButton.setVisible(true);

                FadeTransition fadeIn2 = new FadeTransition(Duration.millis(500), ekleButton);
                fadeIn2.setFromValue(0.0);
                fadeIn2.setToValue(1.0);
                fadeIn2.play();

            }

        });

        ekleButton = new Button("Ekle");
        ekleButton.setId("AntrenmanEkleEkleButton");
        ekleButton.setPrefWidth(120);
        ekleButton.setPrefHeight(40);
        ekleButton.setVisible(false);
        ekleButton.setOnAction(e -> {
            secilenHareketleriAktar();
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), checkboxRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
            onaylaButton.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), checkboxRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

        });

        onaylaButton = new Button("Onayla");
        onaylaButton.setId("AntrenmanEkleOnaylaButton");
        onaylaButton.setPrefWidth(120);
        onaylaButton.setPrefHeight(40);
        onaylaButton.setVisible(false);
        onaylaButton.setOnAction(e -> {
            tabloOlustur();

        });

        gun_hareket_map = new HashMap<>();
        hareketleriGostermeKutusu = new VBox(2);
        hareketleriGostermeKutusu.setAlignment(Pos.CENTER);
        // hareketleriGostermeKutusu.setStyle("-fx-border-width:2px;-fx-border-color:Yellow");
        hareketleriGostermeKutusu.setMinHeight(200);
        hareketleriGostermeKutusu.setMaxWidth(500);
        hareketleriGostermeKutusu.setPadding(new Insets(30, 20, 0, 0));

        TableColumn<WorkoutTemplate, Integer> antrenmanIdCol = new TableColumn<>("Antrenman ID");
        antrenmanIdCol.setCellValueFactory(new PropertyValueFactory<>("AntrenmanID"));
        antrenmanIdCol.setId("column1");
        antrenmanIdCol.setPrefWidth(100);

        TableColumn<WorkoutTemplate, Integer> userIdCol = new TableColumn<>("Kullanıcı ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        userIdCol.setId("column2");
        antrenmanIdCol.setPrefWidth(100);

        TableColumn<WorkoutTemplate, String> usernameCol = new TableColumn<>("İsim");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        usernameCol.setId("column3");
        usernameCol.setPrefWidth(50);

        TableColumn<WorkoutTemplate, String> tipCol = new TableColumn<>("Antrenman Tipi");
        tipCol.setCellValueFactory(new PropertyValueFactory<>("AntrenmanTipi"));
        tipCol.setId("column4");
        tipCol.setPrefWidth(100);

        TableColumn<WorkoutTemplate, Integer> gunCol = new TableColumn<>("Gün Sayısı");
        gunCol.setCellValueFactory(new PropertyValueFactory<>("GunSayisi"));
        gunCol.setId("column5");
        gunCol.setPrefWidth(100);

        tablo.setItems(veritabaniVerileriCek(this.username));
        Label labelveriyok = new Label("İçeride veri yok :( ");
        labelveriyok.setStyle("-fx-text-fill:black;-fx-font-style:italic");
        tablo.setPlaceholder(labelveriyok);

        tablo.getColumns().addAll(antrenmanIdCol, userIdCol, usernameCol, tipCol, gunCol);

    }

    public void secilenHareketleriAktar() {
        gun_hareket_map.clear();
        hareketleriGostermeKutusu.getChildren().clear();
        String metin = null;

        for (int i = 0; i < comboBoxListesi.size(); i++) {
            CheckComboBox<String> combo = comboBoxListesi.get(i);
            ObservableList<String> secilenHareketler = combo.getCheckModel().getCheckedItems();
            gun_hareket_map.put(i, new ArrayList<>(secilenHareketler));
        }

        for (int gun : gun_hareket_map.keySet()) {
            List<String> hareketler = gun_hareket_map.get(gun);
            if (hareketler.isEmpty()) {
                metin = (gun + 1) + ". Güne Hareket Eklenmedi";
            } else {
                metin = (gun + 1) + ". Gün seçilen hareketler: " + hareketler;
            }

            if (metin.length() >= 100) {
                String ilkParca = metin.substring(0, 100);
                String ikinciParca = metin.substring(100);

                Label label1 = new Label();
                label1.setStyle("-fx-font-size: 10px; -fx-font-style: italic; -fx-text-fill: white;");
                Label label2 = new Label();
                label2.setStyle("-fx-font-size: 10px; -fx-font-style: italic; -fx-text-fill: white;");
                label1.setText(ilkParca);
                label2.setText("..." + ikinciParca);
                hareketleriGostermeKutusu.getChildren().addAll(label1, label2);
            } else {
                Label label = new Label(metin);
                label.setStyle("-fx-font-size: 10px; -fx-font-style: italic; -fx-text-fill: white;");
                hareketleriGostermeKutusu.getChildren().add(label);
            }
        }

    }

    public BorderPane getRoot() {
        bilesenler();
        root = new BorderPane();
        VBox solIcerik = new VBox(15);
        solIcerik.setPadding(new Insets(10, 0, 0, 50));
        solIcerik.setAlignment(Pos.TOP_LEFT);
        solIcerik.setMaxHeight(620);
        solIcerik.maxWidth(450);
        solIcerik.getChildren().addAll(label1, tablo, hareketleriGostermeKutusu);
        // solIcerik.setStyle("-fx-border-width:2px;-fx-border-color:Blue");
        solIcerik.getStylesheets()
                .add(CreatingWorkoutsScreen.class.getResource("/static/style.css").toExternalForm());

        HBox buttonYerlesimi = new HBox(15);
        buttonYerlesimi.setAlignment(Pos.BOTTOM_RIGHT);
        buttonYerlesimi.getChildren().addAll(tablodanSilButton, ekleButton, onaylaButton);

        // buttonYerlesimi.setStyle("-fx-border-width:2px;-fx-border-color:Blue");
        buttonYerlesimi.setPadding(new Insets(0, 220, 70, 0));
        buttonYerlesimi.setPrefHeight(40);
        buttonYerlesimi.setPrefWidth(120);
        buttonYerlesimi.setMaxHeight(120);

        HBox sagIcerik = new HBox(20);
        checkboxRoot = new VBox(10);
        labelRoot = new HBox(10);
        sagIcerik.getChildren().add(checkboxRoot);
        sagIcerik.getChildren().add(labelRoot);
        sagIcerik.setAlignment(Pos.BASELINE_RIGHT);
        sagIcerik.setMaxHeight(680);
        sagIcerik.setPadding(new Insets(10, 200, 0, 100));
        // sagIcerik.setStyle("-fx-border-width:2px;-fx-border-color:Red");
        sagIcerik.getStylesheets()
                .add(WorkoutStyleAndDaySelectionScreen.class.getResource("/static/style.css").toExternalForm());

        root.setLeft(solIcerik);
        root.setRight(sagIcerik);
        root.setBottom(buttonYerlesimi);
        // genelYerlesim.setCenter(hareketleriGostermeKutusu);

        return root;
    }

    public void checkboxOlustur(int gun_sayisi) {
        checkboxRoot.getChildren().clear();
        comboBoxListesi = new ArrayList<>();
        for (int i = 0; i < gun_sayisi; i++) {
            VBox gunKutusu = new VBox(5);
            gunKutusu.setPadding(new Insets(5));

            Label gunLabel = new Label((i + 1) + ". Gün'e Hareket Ekle");
            gunLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;-fx-font-style:italic;-fx-font-size:20px");
            gunKutusu.getChildren().add(gunLabel);

            ArrayList<String> hareketler = new ArrayList<>();

            switch (antrenman_tipi) {
                case "PPL":
                    switch (i % 3) {
                        case 0 -> {
                            databaseVerileri("push");
                            hareketler = pushday;
                        }
                        case 1 -> {
                            databaseVerileri("pull");
                            hareketler = pullday;
                        }
                        case 2 -> {
                            databaseVerileri("legs");
                            hareketler = legday;
                        }
                    }
                    break;
                case "UL":
                    switch (i % 2) {
                        case 0 -> {
                            databaseVerileri("upper");
                            hareketler = upperday;
                        }
                        case 1 -> {
                            databaseVerileri("lower");
                            hareketler = lowerday;
                        }
                    }
                    break;
                case "FULLBODY":
                    databaseVerileri("fullbody");
                    hareketler = fullbody;
                    break;
                default:
                    switch (i % 5) {
                        case 0 -> {
                            databaseVerileri("gogus");
                            hareketler = gogus;
                        }
                        case 1 -> {
                            databaseVerileri("sırt");
                            hareketler = sırt;
                        }
                        case 2 -> {
                            databaseVerileri("kol");
                            hareketler = kol;
                        }
                        case 3 -> {
                            databaseVerileri("bacak");
                            hareketler = bacak;
                        }
                        case 4 -> {
                            databaseVerileri("omuz");
                            hareketler = omuz;
                        }
                    }
                    break;
            }

            ObservableList<String> hareketList = FXCollections.observableArrayList(hareketler);
            CheckComboBox<String> checkComboBox = new CheckComboBox<>(hareketList);
            checkComboBox.setId("checkComboBox");
            checkComboBox.setPrefWidth(250);
            gunKutusu.getChildren().add(checkComboBox);
            scrollableCheckboxArea = new ScrollPane();
            scrollableCheckboxArea.setContent(gunKutusu);
            scrollableCheckboxArea.setFitToWidth(true);
            scrollableCheckboxArea.setPrefHeight(150);
            scrollableCheckboxArea.setMaxHeight(80);
            scrollableCheckboxArea.setMinSize(200, 100);
            scrollableCheckboxArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Yatay çubuk gizli
            scrollableCheckboxArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Dikey çubuk gizli
            checkboxRoot.getChildren().add(scrollableCheckboxArea);

            comboBoxListesi.add(checkComboBox);

            ScaleTransition st = new ScaleTransition(Duration.millis(200), gunKutusu);
            st.setFromX(0);
            st.setFromY(0);
            st.setToX(1);
            st.setToY(1);
            st.setDelay(Duration.millis(i * 100));
            st.play();

        }
    }

    public void tabloOlustur() {
        try (Connection con = Database.connect()) {

            int yeni_antrenman_id = 1;
            String maxIDQuery = "SELECT MAX(antrenman_id) FROM eklenen_antrenman_programlari WHERE username = ?";
            try (PreparedStatement maxIDStmt = con.prepareStatement(maxIDQuery)) {
                maxIDStmt.setString(1, username);
                try (ResultSet maxRS = maxIDStmt.executeQuery()) {
                    if (maxRS.next()) {
                        yeni_antrenman_id = maxRS.getInt(1) + 1;
                    }
                }
            }

            int user_id = 0;
            String user_idSorgu = "SELECT user_id FROM users WHERE username = ?";
            try (PreparedStatement psUser = con.prepareStatement(user_idSorgu)) {
                psUser.setString(1, username);
                try (ResultSet rsUser = psUser.executeQuery()) {
                    if (rsUser.next()) {
                        user_id = rsUser.getInt("user_id");
                    }
                }
            }

            String insertSorgu = "INSERT INTO eklenen_antrenman_programlari (antrenman_id, user_id, username, antrenman_tipi, gun_sayisi, gun_no, hareket_adi) VALUES (?, ?, ?, ?, ?, ?, ?)";
            boolean bosHareketVar = false;
            for (List<String> hareketler : gun_hareket_map.values()) {
                if (hareketler.isEmpty()) {
                    bosHareketVar = true;
                    break;
                }
            }

            if (bosHareketVar) {
                Alert hataAlert = new Alert(Alert.AlertType.ERROR);
                hataAlert.setTitle("Hata");
                hataAlert.setHeaderText(null);
                hataAlert.setContentText("Hiçbir antrenman Eklenmedi. Lütfen en az bir hareket seçiniz.");
                hataAlert.showAndWait();
                return;
            }

            try (PreparedStatement psInsert = con.prepareStatement(insertSorgu)) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Onay");
                alert.setHeaderText(null);
                alert.setContentText("Onaylıyor musunuz?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    for (List<String> hareketler : gun_hareket_map.values()) {
                        if (hareketler.isEmpty()) {
                            bosHareketVar = true;
                            break;
                        }
                    }
                    if (bosHareketVar) {
                        Alert hataAlert = new Alert(Alert.AlertType.ERROR);
                        hataAlert.setTitle("Hata");
                        hataAlert.setHeaderText(null);
                        hataAlert.setContentText("Hiçbir antrenman Eklenmedi. Tekrar Deneyiniz.");
                        hataAlert.showAndWait();
                        return;
                    }

                    for (Map.Entry<Integer, List<String>> entry : gun_hareket_map.entrySet()) {
                        int gun = entry.getKey();
                        List<String> hareketler = entry.getValue();

                        for (String hareket : hareketler) {
                            psInsert.setInt(1, antrenman_id);
                            psInsert.setInt(2, user_id);
                            psInsert.setString(3, username);
                            psInsert.setString(4, antrenman_tipi);
                            psInsert.setInt(5, gun_sayisi);
                            psInsert.setInt(6, gun + 1);
                            psInsert.setString(7, hareket);
                            psInsert.addBatch();
                        }
                    }
                    Main.setRoot(MainScreen.getRoot());
                    MainScreen.gosterBasariMesaji("Antrenman Oluşturuldu...");

                    psInsert.executeBatch();

                    try {

                    } catch (Exception ex) {

                    }

                } else {
                    System.out.println("Kullanıcı onaylamadı...");
                }
            }

        } catch (SQLException e) {

        }
    }

    public void databaseVerileri(String antrenman_tur) {
        pushday = new ArrayList<>();
        pullday = new ArrayList<>();
        legday = new ArrayList<>();
        upperday = new ArrayList<>();
        lowerday = new ArrayList<>();
        fullbody = new ArrayList<>();
        gogus = new ArrayList<>();
        kol = new ArrayList<>();
        bacak = new ArrayList<>();
        sırt = new ArrayList<>();
        omuz = new ArrayList<>();

        try (Connection con = Database.connect()) {
            String sorgu = "SELECT * FROM hareketler WHERE antrenman_tur_kategori = ? ";
            PreparedStatement ps = con.prepareStatement(sorgu);
            switch (antrenman_tur) {
                case "push" -> {
                    ps.setString(1, "Push");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        pushday.add(rs.getString("hareket_adi"));
                    }
                }
                case "pull" -> {
                    ps.setString(1, "Pull");
                    ResultSet rs2 = ps.executeQuery();
                    while (rs2.next()) {
                        pullday.add(rs2.getString("hareket_adi"));
                    }
                }
                case "legs" -> {
                    ps.setString(1, "Legs");
                    ResultSet rs3 = ps.executeQuery();
                    while (rs3.next()) {
                        legday.add(rs3.getString("hareket_adi"));
                    }
                }

                case "upper" -> {
                    ps.setString(1, "Upper");
                    ResultSet rs4 = ps.executeQuery();
                    while (rs4.next()) {
                        upperday.add(rs4.getString("hareket_adi"));
                    }
                }
                case "lower" -> {
                    ps.setString(1, "Lower");
                    ResultSet rs5 = ps.executeQuery();
                    while (rs5.next()) {
                        lowerday.add(rs5.getString("hareket_adi"));
                    }
                }
                case "fullbody" -> {
                    ps.setString(1, "FULLBODY");
                    ResultSet rs5 = ps.executeQuery();
                    while (rs5.next()) {
                        fullbody.add(rs5.getString("hareket_adi"));
                    }
                }
                case "gogus" -> {
                    ps.setString(1, "Gogus");
                    ResultSet rs5 = ps.executeQuery();
                    while (rs5.next()) {
                        gogus.add(rs5.getString("hareket_adi"));
                    }
                }
                case "kol" -> {
                    ps.setString(1, "Kol");
                    ResultSet rs5 = ps.executeQuery();
                    while (rs5.next()) {
                        kol.add(rs5.getString("hareket_adi"));
                    }
                }
                case "sırt" -> {
                    ps.setString(1, "Sırt");
                    ResultSet rs5 = ps.executeQuery();
                    while (rs5.next()) {
                        sırt.add(rs5.getString("hareket_adi"));
                    }
                }
                case "omuz" -> {
                    ps.setString(1, "Omuz");
                    ResultSet rs5 = ps.executeQuery();
                    while (rs5.next()) {
                        omuz.add(rs5.getString("hareket_adi"));
                    }
                }
                case "bacak" -> {
                    ps.setString(1, "Bacak");
                    ResultSet rs5 = ps.executeQuery();
                    while (rs5.next()) {
                        bacak.add(rs5.getString("hareket_adi"));
                    }
                }

                default -> {

                }
            }
        } catch (SQLException ex) {

        }

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
                WorkoutTemplate veri = new WorkoutTemplate(antrenman_id2, user_id2, username2, antrenman_tipi2,
                        gun_sayisi2);
                liste.add(veri);

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return liste;
    }
}
