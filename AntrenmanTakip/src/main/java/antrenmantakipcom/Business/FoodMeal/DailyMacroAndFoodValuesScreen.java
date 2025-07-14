package antrenmantakipcom.Business.FoodMeal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import org.controlsfx.control.CheckComboBox;

import antrenmantakipcom.DataAccess.Concrete.Database;
import antrenmantakipcom.Entities.Concrete.Food;
import antrenmantakipcom.Entities.Concrete.Meal;
import antrenmantakipcom.Main;
import antrenmantakipcom.MainScreen;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class DailyMacroAndFoodValuesScreen {
    private BorderPane root;
    private VBox DailyFoodBox;
    private HBox calorieHBox;
    private Label calorieLabel;
    private TextField calorieTextField;
    private HBox fatHBox;
    private Label fatLabel;
    private TextField fatTextField;
    private HBox carbHBox;
    private Label carbLabel;
    private TextField carbTextField;
    private HBox protHBox;
    private Label protLabel;
    private TextField protTextField;
    private HBox buttonsHBox;
    private Button saveButton;
    private DatePicker datePicker;
    private Button geriDonButton;
    private String username;

    private VBox ManualDailyFoodBox;
    private HBox addMealHBox;
    private TextField SearchBar;
    private Label AddMeal;
    private Label header;
    private CheckComboBox<Meal> selectMeal;
    private Label information;
    private Button saveManualButton;
    private ObservableList<Food> food_list;
    private ObservableList<Meal> meal_list;
    private ObservableList<Data> daily_food_values_list;
    private DatePicker datePicker2;

    private TableView<Data> table;
    private boolean foodPanelAcikMi = false;
    private VBox translatedListPanel;
    private StackPane centerStackPane;
    private Button showValueList;

    public class Data {
        private int id;
        private float cal;
        private float fat;
        private float carb;
        private float prot;
        private Date date;

        public Data(int id, float cal, float fat, float carb, float prot, Date date) {
            this.id = id;
            this.cal = cal;
            this.fat = fat;
            this.carb = carb;
            this.prot = prot;
            this.date = date;
        }

        public int getId() {
            return id;
        }

        public Float getCal() {
            return cal;
        }

        public Float getFat() {
            return fat;
        }

        public Float getCarb() {
            return carb;
        }

        public Float getProt() {
            return prot;
        }

        public Date getDate() {
            return date;
        }

        @Override
        public String toString() {
            return "Calorie: " + cal + " Fat: " + fat + " Carb " + carb + " Prot: " + prot + " Date: " + date;
        }

    }

    public DailyMacroAndFoodValuesScreen(String username) {
        this.username = username;
        root = new BorderPane();

        food_list = FXCollections.observableArrayList();
        meal_list = FXCollections.observableArrayList();
        daily_food_values_list = FXCollections.observableArrayList();

        translatedListPanel = new VBox(10);
        translatedListPanel.getStylesheets().add(getClass().getResource("/static/style.css").toExternalForm());
        translatedListPanel.setId("translatedList");
        translatedListPanel.setPrefWidth(500);
        translatedListPanel.setPrefHeight(1000);
        translatedListPanel.setMaxHeight(1000);
        translatedListPanel.setMaxWidth(500);
        translatedListPanel.setAlignment(Pos.CENTER);
        translatedListPanel.setTranslateX(-500);
        translatedListPanel.setTranslateY(150);
        translatedListPanel.setVisible(false);

        table = new TableView<>();
        createTableOfListOfAddedValues();

        Button closeButton = new Button("Close");
        closeButton.setId("cikis_butonlari");
        closeButton.setOnAction(e -> {
            if (foodPanelAcikMi) {
                TranslateTransition transition = new TranslateTransition(Duration.millis(300), translatedListPanel);
                transition.setToX(-500);
                transition.setOnFinished(event -> translatedListPanel.setVisible(false));
                transition.play();
                foodPanelAcikMi = false;
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            Data selectedData = table.getSelectionModel().getSelectedItem();
            int user_id = 0;
            if (selectedIndex >= 0) {
                try (Connection con = Database.connect()) {
                    String sorgu = "SELECT user_id FROM users WHERE username =?";
                    PreparedStatement ps = con.prepareStatement(sorgu);
                    ps.setString(1, username);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        user_id = rs.getInt("user_id");
                    }
                    int value_id = selectedData.getId();
                    String sorgu2 = "DELETE FROM daily_food_values WHERE id = ? ";
                    PreparedStatement ps3 = con.prepareStatement(sorgu2);
                    ps3.setInt(1, value_id);
                    int sonuc = ps3.executeUpdate();
                    if (sonuc > 0) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("DELETED");
                        alert.setHeaderText(null);
                        alert.setContentText("Food has deleted succesfully");
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets()
                                .add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                        alert.showAndWait();
                        if (selectedData != null) {
                            daily_food_values_list.remove(selectedData);
                            table.getSelectionModel().clearSelection();
                        }

                    } else {

                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("NOT SELECTED");
                alert.setHeaderText(null);
                alert.setContentText("Please select a food correctly");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets()
                        .add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            }
        });

        translatedListPanel.getChildren().addAll(table, closeButton, deleteButton);
        showValueList = new Button("List");
        showValueList.setOnAction(e -> {
            daily_food_valuesGetData();
            TranslateTransition transition = new TranslateTransition(Duration.millis(300), translatedListPanel);

            if (foodPanelAcikMi) {
                transition.setToX(-500);
                transition.setOnFinished(event -> translatedListPanel.setVisible(false));
                foodPanelAcikMi = false;
            } else {
                translatedListPanel.setVisible(true);
                transition.setToX(0);
                foodPanelAcikMi = true;
            }

            transition.play();
        });

        selectMeal = new CheckComboBox<>();
        selectMeal.setPrefSize(200, 40);
        selectMeal.setMaxSize(200, 40);
        getData();

        ManualDailyFoodBox = new VBox(10);
        addMealHBox = new HBox(10);
        SearchBar = new TextField();
        SearchBar.setPromptText("Search ... ");
        SearchBar.textProperty().addListener((obs, oldText, newText) -> {
            ObservableList<Meal> filtered = FXCollections.observableArrayList();

            for (Meal meal : meal_list) {
                if (meal.getMealName().toLowerCase().contains(newText.toLowerCase())) {
                    filtered.add(meal);
                }
            }

            if (filtered.isEmpty()) {
                selectMeal.hide();
            } else {
                selectMeal.getItems().setAll(filtered);
                selectMeal.show();
            }

            selectMeal.getItems().setAll(filtered);
        });

        AddMeal = new Label("Add Meal");
        AddMeal.setStyle("-fx-font-size:15px;-fx-font-style:italic");

        information = new Label("");
        information.setStyle("-fx-font-size:15px;-fx-font-style:italic");

        selectMeal.getCheckModel().getCheckedItems()
                .addListener((javafx.collections.ListChangeListener<Meal>) change -> {
                    float totalCal = 0;
                    float totalFat = 0;
                    float totalCarb = 0;
                    float totalProt = 0;

                    for (Meal selectedMeal : selectMeal.getCheckModel().getCheckedItems()) {
                        totalCal += selectedMeal.getTotalCal();
                        totalFat += selectedMeal.getTotalFat();
                        totalCarb += selectedMeal.getTotalCarb();
                        totalProt += selectedMeal.getTotalProt();
                    }

                    information.setText(
                            String.format("Calorie: %.1f Fat: %.1f Carb: %.1f Prot: %.1f",
                                    totalCal, totalFat, totalCarb, totalProt));
                });

        saveManualButton = new Button("Save");
        saveManualButton.setOnAction(e -> {
            SaveDailyCalorieManual();
            information.setText("");
        });

        header = new Label("Add your values manual");
        header.setStyle("-fx-font-size:30px;-fx-font-style:italic");
        datePicker2 = new DatePicker();
        datePicker2.setPromptText("Enter a date");

        addMealHBox.getChildren().addAll(AddMeal, selectMeal, datePicker2);

        ManualDailyFoodBox.getChildren().addAll(header, SearchBar, addMealHBox, information, saveManualButton);
        ManualDailyFoodBox.setAlignment(Pos.CENTER);
        ManualDailyFoodBox.setPadding(new Insets(50, 50, 0, 0));
        ManualDailyFoodBox.setPrefSize(500, 500);
        ManualDailyFoodBox.setMaxSize(500, 500);

        DailyFoodBox = new VBox(10);
        Label label = new Label("Enter your daily food values");
        label.setStyle("-fx-font-size:30px;-fx-font-style:italic");
        calorieHBox = new HBox(10);
        fatHBox = new HBox(45);
        carbHBox = new HBox(33);
        protHBox = new HBox(36);
        buttonsHBox = new HBox(10);
        buttonsHBox.setPadding(new Insets(20, 0, 0, 66));

        calorieLabel = new Label("Calories: ");
        calorieLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");
        calorieTextField = new TextField();
        calorieTextField.setOnAction(e -> fatTextField.requestFocus());
        calorieTextField.setPrefSize(300, 80);
        calorieTextField.setPromptText("Enter your daily calories here(kcal)");

        calorieHBox.getChildren().addAll(calorieLabel, calorieTextField);

        fatLabel = new Label("Fat :");
        fatLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");
        fatTextField = new TextField();
        fatTextField.setOnAction(e -> carbTextField.requestFocus());
        fatTextField.setPrefSize(300, 80);
        fatTextField.setPromptText("Enter your daily fat here(grams)");

        fatHBox.getChildren().addAll(fatLabel, fatTextField);

        carbLabel = new Label("Carb: ");
        carbLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");
        carbTextField = new TextField();
        protTextField = new TextField();
        carbTextField.setOnAction(e -> protTextField.requestFocus());
        carbTextField.setPrefSize(300, 80);
        carbTextField.setPromptText("Enter your daily carb here(grams)");

        carbHBox.getChildren().addAll(carbLabel, carbTextField);

        protLabel = new Label("Prot :");
        protLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");
        protTextField.setOnAction(e -> datePicker.requestFocus());
        protTextField.setPrefSize(300, 80);
        protTextField.setPromptText("Enter Your daily prot here(grams)");

        protHBox.getChildren().addAll(protLabel, protTextField);

        saveButton = new Button("Save");
        saveButton.setPrefSize(120, 40);
        saveButton.setMaxSize(120, 40);

        saveButton.setOnAction(e -> {
            SaveDailyCalorie();
            fatTextField.setText("");
            carbTextField.setText("");
            calorieTextField.setText("");
            protTextField.setText("");
        });

        datePicker = new DatePicker();
        datePicker.setPromptText("Enter a date");

        Image imageC = new Image(MainScreen.class.getResourceAsStream("/ICONS/logout.png"));
        ImageView imageViewC = new ImageView(imageC);
        imageViewC.setFitWidth(20);
        imageViewC.setFitHeight(20);
        geriDonButton = new Button("Exit", imageViewC);
        geriDonButton.setId("cikis_butonlari");
        geriDonButton.setOnAction(e -> {
            Main.setRoot(MainScreen.getRoot());
        });

        buttonsHBox.getChildren().addAll(datePicker, saveButton, showValueList);
        buttonsHBox.setPadding(new Insets(0, 20, 0, 0));

        DailyFoodBox.getChildren().addAll(label, calorieHBox, fatHBox, carbHBox, protHBox, buttonsHBox);
        DailyFoodBox.setAlignment(Pos.CENTER);
        DailyFoodBox.setPadding(new Insets(50, 0, 0, 50));
        DailyFoodBox.setPrefSize(500, 500);
        DailyFoodBox.setMaxSize(500, 500);
        BorderPane mainContent = new BorderPane();
        mainContent.setRight(ManualDailyFoodBox);
        StackPane leftStackPane = new StackPane();
        leftStackPane.getChildren().addAll(DailyFoodBox, translatedListPanel);
        leftStackPane.setPadding(new Insets(0, 0, 270, 0));
        StackPane centerStackPane = new StackPane();
        centerStackPane.getChildren().addAll(mainContent);
        root.setCenter(centerStackPane);
        root.setLeft(leftStackPane);
        root.setBottom(geriDonButton);
        daily_food_valuesGetData();
    }

    public BorderPane getPane() {
        return root;
    }

    public void SaveDailyCalorie() {
        int user_id = 0;
        String calorieText = calorieTextField.getText().trim();
        String fatText = fatTextField.getText().trim();
        String carbText = carbTextField.getText().trim();
        String protText = protTextField.getText().trim();
        LocalDate date = datePicker.getValue();

        if (calorieText.isEmpty() || fatText.isEmpty() || carbText.isEmpty() || protText.isEmpty() || date == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Veri Eksik!");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen tüm alanları doldurunuz.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();
            return;
        }

        try (Connection con = Database.connect()) {
            String sorgu = "SELECT user_id FROM users WHERE username =?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("user_id");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        try {
            float calorie = Float.parseFloat(calorieText);
            float fat = Float.parseFloat(fatText);
            float carb = Float.parseFloat(carbText);
            float prot = Float.parseFloat(protText);

            try (Connection con = Database.connect()) {
                String sorgu = "INSERT INTO daily_food_values (user_id,calorie,fat,carb,prot,date) VALUES (?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sorgu);
                ps.setInt(1, user_id);
                ps.setFloat(2, calorie);
                ps.setFloat(3, fat);
                ps.setFloat(4, carb);
                ps.setFloat(5, prot);
                ps.setDate(6, java.sql.Date.valueOf(date));

                int sonuc = ps.executeUpdate();
                if (sonuc > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Başarılı!");
                    alert.setHeaderText(null);
                    alert.setContentText("Veriler başarıyla kaydedildi.");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Başarısız!");
                    alert.setHeaderText(null);
                    alert.setContentText("Veriler kaydedilemedi");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                    alert.showAndWait();
                }
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Hatalı Giriş!");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen sayısal alanlara sadece sayı giriniz.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getData() {
        try (Connection con = Database.connect()) {
            int user_id = 0;

            String sorguID = "SELECT user_id FROM users WHERE username = ?";
            PreparedStatement psID = con.prepareStatement(sorguID);
            psID.setString(1, username);
            ResultSet rsID = psID.executeQuery();
            while (rsID.next()) {
                user_id = rsID.getInt("user_id");
            }

            String sorgu2 = "SELECT meal_name,total_cal,total_fat,total_carb,total_prot FROM saved_meals WHERE user_id = ?";
            PreparedStatement ps2 = con.prepareStatement(sorgu2);
            ps2.setInt(1, user_id);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                String meal_name = rs2.getString("meal_name");
                float totalCal = rs2.getFloat("total_cal");
                float totalFat = rs2.getFloat("total_fat");
                float totalCarb = rs2.getFloat("total_carb");
                float totalProt = rs2.getFloat("total_prot");
                Meal meal = new Meal(meal_name, user_id, totalCal, totalFat, totalCarb, totalProt);
                meal_list.add(meal);

            }
            selectMeal.getItems().setAll(meal_list);
        } catch (SQLException ex) {
        }

    }

    public void SaveDailyCalorieManual() {
        int user_id = 0;
        float totalCal = 0;
        float totalFat = 0;
        float totalCarb = 0;
        float totalProt = 0;
        for (Meal selectedMeal : selectMeal.getCheckModel().getCheckedItems()) {
            totalCal += selectedMeal.getTotalCal();
            totalFat += selectedMeal.getTotalFat();
            totalCarb += selectedMeal.getTotalCarb();
            totalProt += selectedMeal.getTotalProt();
        }

        LocalDate date = datePicker2.getValue();

        if (date == null || selectMeal.getCheckModel().getCheckedItems().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Veri Eksik!");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen tüm alanları doldurunuz.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();
            return;
        }

        try (Connection con = Database.connect()) {
            String sorgu = "SELECT user_id FROM users WHERE username =?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("user_id");
            }
            String sorgu2 = "INSERT INTO daily_food_values (user_id,calorie,fat,carb,prot,date) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps2 = con.prepareStatement(sorgu2);
            ps2.setInt(1, user_id);
            ps2.setFloat(2, totalCal);
            ps2.setFloat(3, totalFat);
            ps2.setFloat(4, totalCarb);
            ps2.setFloat(5, totalProt);
            ps2.setDate(6, java.sql.Date.valueOf(date));

            int sonuc = ps2.executeUpdate();
            if (sonuc > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Başarılı!");
                alert.setHeaderText(null);
                alert.setContentText("Veriler başarıyla kaydedildi.");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
                selectMeal.getCheckModel().clearChecks();

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Başarısız!");
                alert.setHeaderText(null);
                alert.setContentText("Veriler kaydedilemedi");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            }
        }

        catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Hatalı Giriş!");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen sayısal alanlara sadece sayı giriniz.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void daily_food_valuesGetData() {
        int id;
        Float cal;
        Float fat;
        Float carb;
        Float prot;
        Date date;
        int user_id = 0;
        try (Connection con = Database.connect()) {
            String sorgu = "SELECT user_id FROM users WHERE username =?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("user_id");
            }
            daily_food_values_list.clear();
            String sorgu2 = "SELECT id,calorie,fat,carb,prot,date FROM daily_food_values WHERE user_id = ?";
            PreparedStatement ps2 = con.prepareStatement(sorgu2);
            ps2.setInt(1, user_id);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                id = rs2.getInt("id");
                cal = rs2.getFloat("calorie");
                fat = rs2.getFloat("fat");
                carb = rs2.getFloat("carb");
                prot = rs2.getFloat("prot");
                date = rs2.getDate("date");
                Data data = new Data(id, cal, fat, carb, prot, date);
                daily_food_values_list.add(data);

            }
            table.setItems(daily_food_values_list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createTableOfListOfAddedValues() {

        table.setPrefHeight(600);
        table.setPrefWidth(600);
        table.setMaxWidth(600);
        table.setId("Food List");

        TableColumn<Data, Float> calCol = new TableColumn<>("calorie");
        calCol.setCellValueFactory(new PropertyValueFactory<>("cal"));
        calCol.setPrefWidth(100);

        TableColumn<Data, Float> fatCol = new TableColumn<>("fat");
        fatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));
        fatCol.setPrefWidth(90);

        TableColumn<Data, Float> carbCol = new TableColumn<>("carb");
        carbCol.setCellValueFactory(new PropertyValueFactory<>("carb"));
        carbCol.setPrefWidth(90);

        TableColumn<Data, Float> protCol = new TableColumn<>("prot");
        protCol.setCellValueFactory(new PropertyValueFactory<>("prot"));
        protCol.setPrefWidth(90);

        TableColumn<Data, Date> dateCol = new TableColumn<>("date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(80);

        calCol.setStyle("-fx-alignment: CENTER;");
        fatCol.setStyle("-fx-alignment: CENTER;");
        carbCol.setStyle("-fx-alignment: CENTER;");
        protCol.setStyle("-fx-alignment: CENTER;");
        dateCol.setStyle("-fx-alignment: CENTER;");

        Label labelveriyok = new Label("İçeride veri yok :( ");
        labelveriyok.setStyle("-fx-text-fill:black;-fx-font-style:italic");
        table.setPlaceholder(labelveriyok);

        table.getColumns().addAll(calCol, fatCol, carbCol, protCol, dateCol);

    }
}
