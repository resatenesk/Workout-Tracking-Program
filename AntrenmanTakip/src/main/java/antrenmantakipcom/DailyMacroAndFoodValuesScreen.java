package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private VBox addPrivateFoodBox;
    private HBox addPrivateBoxElementsBox;
    private VBox addPrivateBoxLabelBox;
    private VBox addPrivateBoxTextFieldBox;
    private HBox addPrivateBoxButtonsHBox;
    private Label addPrivateBoxHeaderLabel;
    private Label addPrivateBoxFoodNameLabel;
    private Label addPrivateBoxCalorieLabel;
    private Label addPrivateBoxFatLabel;
    private Label addPrivateBoxCarbLabel;
    private Label addPrivateBoxProtLabel;
    private TextField foodNameField;
    private TextField foodCalorieField;
    private TextField foodFatField;
    private TextField foodCarbField;
    private TextField foodProtField;
    private Button addPrivateFoodBoxSaveButton;

    public class Food {
        private String foodName;
        private float calorie;
        private float fat;
        private float carb;
        private float prot;

        public Food(String foodName, float calorie, float fat, float carb, float prot) {
            this.foodName = foodName;
            this.calorie = calorie;
            this.fat = fat;
            this.carb = carb;
            this.prot = prot;
        }

        @Override
        public String toString() {
            return "Food name: " + foodName + " Calorie: " + calorie + " Fat: " + fat + " Carb: " + carb + " Prot: "
                    + prot;
        }
    }

    public DailyMacroAndFoodValuesScreen() {
        root = new BorderPane();

        addPrivateFoodBox = new VBox(10);
        addPrivateFoodBox.setPadding(new Insets(50, 50, 0, 0));
        addPrivateBoxElementsBox = new HBox(10);
        addPrivateBoxLabelBox = new VBox(55);
        addPrivateBoxTextFieldBox = new VBox(10);
        addPrivateBoxButtonsHBox = new HBox(10);

        addPrivateBoxHeaderLabel = new Label("Add your special food values");
        addPrivateBoxHeaderLabel.setStyle("-fx-font-size:30px;-fx-font-style:italic");

        addPrivateBoxFoodNameLabel = new Label("Food Name : ");
        addPrivateBoxFoodNameLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");
        addPrivateBoxCalorieLabel = new Label("Food Calorie : ");
        addPrivateBoxCalorieLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");
        addPrivateBoxFatLabel = new Label("Food Fat : ");
        addPrivateBoxFatLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");
        addPrivateBoxCarbLabel = new Label("Food Carb : ");
        addPrivateBoxCarbLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");
        addPrivateBoxProtLabel = new Label("Food Prot : ");
        addPrivateBoxProtLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");

        foodNameField = new TextField();
        foodNameField.setOnAction(e -> foodCalorieField.requestFocus());
        foodNameField.setPrefSize(300, 80);
        foodNameField.setPromptText("Enter your food name here(kcal)");
        foodCalorieField = new TextField();
        foodCalorieField.setOnAction(e -> foodFatField.requestFocus());
        foodCalorieField.setPrefSize(300, 80);
        foodCalorieField.setPromptText("Enter calories of the food here(kcal)");
        foodFatField = new TextField();
        foodFatField.setOnAction(e -> foodCarbField.requestFocus());
        foodFatField.setPrefSize(300, 80);
        foodFatField.setPromptText("Enter fat(grams) of the food here(kcal)");
        foodCarbField = new TextField();
        foodCarbField.setOnAction(e -> foodProtField.requestFocus());
        foodCarbField.setPrefSize(300, 80);
        foodCarbField.setPromptText("Enter carbs(grams) of the food here(kcal)");
        foodProtField = new TextField();
        foodProtField.setPrefSize(300, 80);
        foodProtField.setPromptText("Enter prot(grams) of the food here(kcal)");

        addPrivateBoxLabelBox.getChildren().addAll(addPrivateBoxFoodNameLabel, addPrivateBoxCalorieLabel,
                addPrivateBoxFatLabel, addPrivateBoxCarbLabel, addPrivateBoxProtLabel);
        addPrivateBoxTextFieldBox.getChildren().addAll(foodNameField, foodCalorieField, foodFatField, foodCarbField,
                foodProtField);

        addPrivateFoodBoxSaveButton = new Button("Save");
        addPrivateFoodBoxSaveButton.setOnAction(e -> {
            saveSpecialFood();
            foodNameField.setText("");
            foodCalorieField.setText("");
            foodFatField.setText("");
            foodCarbField.setText("");
            foodProtField.setText("");
        });

        addPrivateBoxElementsBox.getChildren().addAll(addPrivateBoxLabelBox, addPrivateBoxTextFieldBox);

        addPrivateBoxButtonsHBox.getChildren().addAll(addPrivateFoodBoxSaveButton);
        addPrivateBoxButtonsHBox.setPadding(new Insets(30, 0, 0, 240));
        addPrivateFoodBox.getChildren().addAll(addPrivateBoxHeaderLabel, addPrivateBoxElementsBox,
                addPrivateBoxButtonsHBox);

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
        carbTextField.setOnAction(e -> protTextField.requestFocus());
        carbTextField.setPrefSize(300, 80);
        carbTextField.setPromptText("Enter your daily carb here(grams)");

        carbHBox.getChildren().addAll(carbLabel, carbTextField);

        protLabel = new Label("Prot :");
        protLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");
        protTextField = new TextField();
        carbTextField.setOnAction(e -> datePicker.requestFocus());
        protTextField.setPrefSize(300, 80);
        protTextField.setPromptText("Enter Your daily prot here(grams)");

        protHBox.getChildren().addAll(protLabel, protTextField);

        saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            SaveDailyCalorie();
            fatTextField.setText("");
            carbTextField.setText("");
            calorieTextField.setText("");
            protTextField.setText("");


        });

        datePicker = new DatePicker();
        datePicker.setPromptText("Enter a date");
        geriDonButton = new Button("Exit");
        geriDonButton.setId("cikis_butonlari");
        geriDonButton.setOnAction(e -> {
            AnaKontrolEkrani.setRoot(AnaEkran.getRoot());
        });

        buttonsHBox.getChildren().addAll(datePicker, saveButton);

        DailyFoodBox.getChildren().addAll(label, calorieHBox, fatHBox, carbHBox, protHBox, buttonsHBox);
        DailyFoodBox.setAlignment(Pos.CENTER);
        DailyFoodBox.setPadding(new Insets(50, 0, 0, 50));
        // generalBox.setStyle("-fx-border-size:2px;-fx-border-style:solid;-fx-border-color:red");
        DailyFoodBox.setPrefSize(500, 500);
        DailyFoodBox.setMaxSize(500, 500);

        root.setLeft(DailyFoodBox);
        root.setRight(addPrivateFoodBox);
        root.setBottom(geriDonButton);
    }

    public BorderPane getPane() {
        return root;
    }

    public void saveSpecialFood() {
        String foodName = foodNameField.getText().trim();
        String calorieText = foodCalorieField.getText().trim();
        String fatText = foodFatField.getText().trim();
        String carbText = foodCarbField.getText().trim();
        String protText = foodProtField.getText().trim();

        if (foodName.isEmpty() || calorieText.isEmpty() || fatText.isEmpty() || carbText.isEmpty()
                || protText.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Veri Eksik!");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen tüm alanları doldurunuz.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();
            return;
        }
        try {
            float calorie = Float.parseFloat(calorieText);
            float fat = Float.parseFloat(fatText);
            float carb = Float.parseFloat(carbText);
            float prot = Float.parseFloat(protText);

            try (Connection con = Database.connect()) {
                String sorgu = "INSERT INTO saved_special_foods (food_name,calorie,fat,carb,prot) VALUES (?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sorgu);
                ps.setString(1, foodName);
                ps.setFloat(2, calorie);
                ps.setFloat(3, fat);
                ps.setFloat(4, carb);
                ps.setFloat(5, prot);
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

    public void SaveDailyCalorie() {
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

        try {
            float calorie = Float.parseFloat(calorieText);
            float fat = Float.parseFloat(fatText);
            float carb = Float.parseFloat(carbText);
            float prot = Float.parseFloat(protText);

            try (Connection con = Database.connect()) {
                String sorgu = "INSERT INTO daily_food_values (calorie,fat,carb,prot,date) VALUES (?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sorgu);
                ps.setFloat(1, calorie);
                ps.setFloat(2, fat);
                ps.setFloat(3, carb);
                ps.setFloat(4, prot);
                ps.setDate(5, java.sql.Date.valueOf(date));
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
}
