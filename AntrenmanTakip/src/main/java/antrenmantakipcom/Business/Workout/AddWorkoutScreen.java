package antrenmantakipcom.Business.Workout;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import antrenmantakipcom.Business.Utilities.Functions.Concrete.AlertFunction;
import antrenmantakipcom.Business.Utilities.Functions.Concrete.CreateButton;
import antrenmantakipcom.Business.Utilities.Functions.Concrete.ImageFunction;
import antrenmantakipcom.DataAccess.Abstract.IAddedWorkoutProgramDal;
import antrenmantakipcom.DataAccess.Abstract.IMovementDal;
import antrenmantakipcom.DataAccess.Abstract.IRecordsDal;
import antrenmantakipcom.DataAccess.Abstract.IWorkoutDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.AddedWorkoutProgramDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.RecordsDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.WorkoutTemplateDal;
import antrenmantakipcom.Entities.Concrete.AddedWorkoutProgram;
import antrenmantakipcom.Entities.Concrete.Records;
import antrenmantakipcom.Entities.Concrete.WorkoutTemplate;
import antrenmantakipcom.Main;
import antrenmantakipcom.MainScreen;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddWorkoutScreen {

    private static BorderPane root;
    private final Button geriButton;
    private TableView<WorkoutTemplate> tablo;
    private String username;
    private ObservableList<WorkoutTemplate> liste;
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
    private int user_id;

    private Label hareketLabel;
    private TextField agirlikTextField;
    private TextField tekrarTextField;

    IRecordsDal _RecordsDal;
    IWorkoutDal _WorkoutDal;
    IMovementDal _MovementDal;
    IAddedWorkoutProgramDal _AddedWorkoutProgramDal;

    @SuppressWarnings({ "static-access", "SuspiciousToArrayCall", "CollectionsToArray" })
    public AddWorkoutScreen(String username) {
        _RecordsDal = new RecordsDal(Records.class);
        _WorkoutDal = new WorkoutTemplateDal(WorkoutTemplate.class);
        _AddedWorkoutProgramDal = new AddedWorkoutProgramDal(AddedWorkoutProgram.class);
        antrenmanIDlabel = new Label("");
        this.username = username;
        root = new BorderPane();
        root.setPadding(new Insets(20));

        datePicker = new DatePicker();
        datePicker.setPromptText("Please select a date");

        Label baslik = new Label("Adding Workout Screen");
        baslik.setStyle("-fx-font-size:20px");

        HBox header = new HBox(baslik);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        // header.setStyle("-fx-border-width:2px;-fx-border-color:blue");
        root.setTop(header);

        geriButton = CreateButton.createExitButton();
        geriButton.setOnAction(e -> {
            MainScreen anaEkran = new MainScreen();
            Main.setRoot(anaEkran.getRoot());
        });

        HBox footer = new HBox(geriButton);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(10, 10, 0, 10)); // Dış boşluk
        // footer.setStyle("-fx-border-width:2px;-fx-border-color:red");
        root.setBottom(footer);

        icerikSol = new VBox(2);
        icerikSol.setMargin(antrenmanIDlabel, Insets.EMPTY);
        icerikSol.setAlignment(Pos.CENTER);
        icerikSol.setPrefHeight(300);
        icerikSol.setMaxHeight(300);
        // icerikSol.setStyle("-fx-border-width:2px;-fx-border-color:green");
        icerikSol.setPadding(new Insets(0, 0, 0, 0));
        tablo = tabloyuGetir();
        tablo.setOnMouseClicked(e -> {
            gun_listesi.clear();
            kacincıGunComboBox.getItems().clear();
            kacincıGunComboBox.getSelectionModel().clearSelection();
            ekleButton.setVisible(false);
            for (HBox value : hareketSatirlari) {
                value.getChildren().clear();
            }
            WorkoutTemplate secilenVeri = tablo.getSelectionModel().getSelectedItem();

            if (secilenVeri != null) {
                gun_sayisi = secilenVeri.getGunSayisi();
                antrenman_id = secilenVeri.getAntrenmanID();

                for (int i = 1; i <= gun_sayisi; i++) {
                    gun_listesi.add(i);
                }
                kacincıGunComboBox.getItems().addAll(gun_listesi);
            }
        });

        antrenmanSil = CreateButton.createDeleteButton();
        antrenmanSil.setOnAction(e -> {
            tablodanVeriSil();

        });

        ekleButton = CreateButton.createSaveButton();
        ekleButton.setVisible(false);
        ekleButton.setOnAction(e -> {

            secilenTarih = datePicker.getValue();
            if (secilenTarih == null) {
                AlertFunction.DateIsNotSelectedAlert();
                return;

            } else {
                veritabaniVerileriAktar();
            }
            secilenTarih = null;

        });

        icerikSol.getChildren().addAll(tablo, antrenmanIDlabel, datePicker, antrenmanSil);

        icerikSag = new VBox(30);
        // icerikSag.setStyle("-fx-border-width:2px;-fx-border-color:green");
        icerikSag.setPadding(new Insets(0, 10, 10, 10)); // Dış boşluk
        icerikSag.setPrefWidth(700);
        icerikSag.setMaxWidth(700);
        icerikSag.setPrefHeight(500);
        icerikSag.setMaxHeight(500);
        icerikSag.setAlignment(Pos.TOP_CENTER);

        infoIcon = ImageFunction.LoadTooltip(
                "/ICONS/info.png",
                "Select the workout you want to add.\n" +
                        "Then select the day you want to add it. Then enter the exercises one by one.\n" +
                        "But be careful, put a , between the reps and weights. The data is separated by ,.");
        HBox kacinciGunHBox = new HBox(10);
        kacinciGunHBox.setAlignment(Pos.CENTER);
        // kacinciGunHBox.setStyle("-fx-border-width:2px;-fx-border-color:green");

        kacinciGunLabel = new Label("On which day will you add movement?");
        kacinciGunLabel.setStyle("-fx-font-size:15px;");

        textFieldVBox = new VBox(5);
        textFieldVBox.setAlignment(Pos.CENTER);

        kacincıGunComboBox = new ComboBox<>();
        kacincıGunComboBox.setOnAction(e -> {
            if (kacincıGunComboBox.getSelectionModel().getSelectedItem() != null) {
                secilenGun = kacincıGunComboBox.getValue();
            }

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
    }

    public void tablodanVeriSil() {
        WorkoutTemplate secilenVeri = (WorkoutTemplate) tablo.getSelectionModel().getSelectedItem();
        if (secilenVeri != null) {
            Optional<ButtonType> result1 = AlertFunction.ConfirmAlert();

            if (result1.isPresent() && result1.get().getText().equals("Yes")) {
                int id = secilenVeri.getAntrenmanID();
                _WorkoutDal.Delete(secilenVeri, id);
                liste.remove(secilenVeri);
            }
        } else {
            AlertFunction.MissingDataAlert();

        }
    }

    public void veritabaniVerileriAktar() {
        if (datePicker.getValue() == null) {
            AlertFunction.DateIsNotSelectedAlert();
            return;
        }
        Date tarih = Date.valueOf(datePicker.getValue());

        Integer gun_no = kacincıGunComboBox.getSelectionModel().getSelectedItem();
        if (gun_no == null || gun_no == 0) {
            AlertFunction.MissingDataAlert();
            return;
        }

        WorkoutTemplate secilenVeri = tablo.getSelectionModel().getSelectedItem();
        if (secilenVeri == null) {
            AlertFunction.NoElementsSelectedAlert();
            return;
        }

        for (HBox satir : hareketSatirlari) {
            if (satir.getChildren().size() < 3)
                continue;

            TextField agirlikField = (TextField) ((VBox) satir.getChildren().get(1)).getChildren().get(0);
            TextField tekrarField = (TextField) ((VBox) satir.getChildren().get(2)).getChildren().get(0);

            if (agirlikField.getText().isBlank() || tekrarField.getText().isBlank()) {
                AlertFunction.MissingDataAlert();
                return;
            }
        }

        int user_id = secilenVeri.getUserID();
        String username = secilenVeri.getUsername();
        int antrenman_id = secilenVeri.getAntrenmanID();

        for (HBox satir : hareketSatirlari) {
            if (satir.getChildren().size() < 3)
                continue;

            Label hareketLabel = (Label) ((VBox) satir.getChildren().get(0)).getChildren().get(0);
            TextField agirlikField = (TextField) ((VBox) satir.getChildren().get(1)).getChildren().get(0);
            TextField tekrarField = (TextField) ((VBox) satir.getChildren().get(2)).getChildren().get(0);

            String hareket_adi = hareketLabel.getText();
            String agirlikText = agirlikField.getText().replace(" ", "");
            String tekrarText = tekrarField.getText().replace(" ", "");

            String[] agirliklar = agirlikText.split(",");
            String[] tekrarlars = tekrarText.split(",");

            if (agirliklar.length != tekrarlars.length) {
                System.out.println("Number of sets does not match:" + hareket_adi);
                continue;
            }

            for (int i = 0; i < agirliklar.length; i++) {
                try {
                    double agirlik = Double.parseDouble(agirliklar[i].trim());
                    int tekrar = Integer.parseInt(tekrarlars[i].trim());
                    int set_no = i + 1;

                    Records r = new Records(user_id, username, antrenman_id, gun_no, hareket_adi, set_no, agirlik,
                            tekrar, tarih);
                    _RecordsDal.Add(r);

                } catch (NumberFormatException e) {
                    AlertFunction.FailAlert();
                    return;
                }
            }
        }

        AlertFunction.SuccessAlert();
    }

    public void hareketleriOlustur() {
        WorkoutTemplate secilenVeri = (WorkoutTemplate) tablo.getSelectionModel().getSelectedItem();
        if (secilenVeri == null) {
            return;
        }

        int antrenman_id = secilenVeri.getAntrenmanID();

        Integer gunIndex = kacincıGunComboBox.getValue();
        if (gunIndex == null) {
            return;
        }
        String category = secilenVeri.getAntrenmanTipi();
        if (secilenGun == 0) {
            return;
        }

        ObservableList<AddedWorkoutProgram> list = _AddedWorkoutProgramDal.GetAll(
                "SELECT * FROM eklenen_antrenman_programlari WHERE antrenman_id = ? AND gun_no = ?", antrenman_id,
                secilenGun);

        labeller.clear();
        hareketSatirlari.clear();
        textFieldVBox.getChildren().clear();

        int satirIndex = 0;
        for (AddedWorkoutProgram program : list) {

            String hareket_adi = program.getHareket_adi();

            Label label = new Label(hareket_adi);
            label.setStyle("-fx-font-size: 14px; -fx-padding: 5 0 5 10;");

            TextField textFieldSol = new TextField();
            textFieldSol.setPromptText("Weight");

            TextField textFieldSag = new TextField();
            textFieldSag.setPromptText("Reps");

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

            final int currentIndex = satirIndex;

            textFieldSol.setOnAction(e -> {
                textFieldSag.requestFocus();
            });

            textFieldSag.setOnAction(e -> {
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
    }

    public TableView tabloyuGetir() {
        tablo = new TableView<>();
        tablo.setMinHeight(300);
        tablo.setMaxHeight(300);
        tablo.setPrefHeight(300);
        tablo.setPrefWidth(450);
        tablo.setMaxWidth(450);

        tablo.setId("antrenmanTablo");
        TableColumn<WorkoutTemplate, Integer> antrenmanIdCol = new TableColumn<>("Workout ID");
        antrenmanIdCol.setCellValueFactory(new PropertyValueFactory<>("AntrenmanID"));
        antrenmanIdCol.setId("column1");
        antrenmanIdCol.setPrefWidth(100);

        TableColumn<WorkoutTemplate, Integer> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        userIdCol.setId("column2");
        userIdCol.setPrefWidth(100);

        TableColumn<WorkoutTemplate, String> usernameCol = new TableColumn<>("Name");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        usernameCol.setId("column3");
        usernameCol.setPrefWidth(50);

        TableColumn<WorkoutTemplate, String> tipCol = new TableColumn<>("Workout Type");
        tipCol.setCellValueFactory(new PropertyValueFactory<>("AntrenmanTipi"));
        tipCol.setId("column4");
        tipCol.setPrefWidth(100);

        TableColumn<WorkoutTemplate, Integer> gunCol = new TableColumn<>("Number of Days");
        gunCol.setCellValueFactory(new PropertyValueFactory<>("GunSayisi"));
        gunCol.setId("column5");
        gunCol.setPrefWidth(100);

        tablo.setItems(veritabaniVerileriCek(this.username));
        Label labelveriyok = new Label("No Data 🔧:(");
        labelveriyok.setStyle("-fx-text-fill:black;-fx-font-style:italic;-fx-font-size:10px;");
        tablo.setPlaceholder(labelveriyok);

        tablo.getColumns().addAll(antrenmanIdCol, userIdCol, usernameCol, tipCol, gunCol);
        return tablo;

    }

    public ObservableList<WorkoutTemplate> veritabaniVerileriCek(String username) {

        liste = _WorkoutDal.GetAll("SELECT * FROM eklenen_antrenman_sablonlari WHERE username=?", username);
        return liste;
    }

    public BorderPane getRoot() {

        root.setId("rootBackgroundGeneral");
        return root;
    }
}
