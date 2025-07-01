package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CreateSpecialMeal {
    private BorderPane pane;
    private Button exitButton;
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
    private Button addPrivateFoodBoxSaveButton;
    private TextField foodNameField;
    private TextField foodCalorieField;
    private TextField foodFatField;
    private TextField foodCarbField;
    private TextField foodProtField;
    private ObservableList<Food> food_list;
    private Button showFoodList;
    private boolean panelAcikMi = false;
    private VBox translatedListPanel;
    private StackPane centerStackPane;
    private TableView<Food> table;

    private VBox addPrivateMealBox;
    private VBox addPrivateMealBoxFieldBox;
    private VBox addPrivateMealBoxLabelBox;
    private HBox addPrivateMealBoxButtonsBox;
    private HBox addPrivateMealBoxElementsBox;
    private Label addPrivateMealBoxHeaderLabel;
    private Label addPrivateMealNameLabel;
    private Label chooseFoodLabel;
    private Button addPrivateMealBoxSaveButton;
    private Button showMealList;
    private TextField mealNameField;
    private ComboBox<Food> foodComboBox;
    private ObservableList<Meal> meal_list;
    private VBox translatedListPanelMeal;
    private StackPane centerStackPane2;
    private TableView<Food> table2;

    public class Meal {
        private String meal_name;
        private Food food;
        private float total_calorie;
        private float total_fat;
        private float total_carb;
        private float total_prot;

        public Meal(String meal_name) {
            this.meal_name = meal_name;
        }
    }

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

        public String getFoodName() {
            return foodName;
        }

        public float getCalorie() {
            return calorie;
        }

        public float getFat() {
            return fat;
        }

        public float getCarb() {
            return carb;
        }

        public float getProt() {
            return prot;
        }
    }

    public CreateSpecialMeal() {
        pane = new BorderPane();
        food_list = FXCollections.observableArrayList();
        exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            AnaKontrolEkrani.setRoot(AnaEkran.getRoot());
        });

        translatedListPanel = new VBox(10);
        translatedListPanel.getStylesheets().add(getClass().getResource("/static/style.css").toExternalForm());
        translatedListPanel.setId("translatedList");
        translatedListPanel.setPrefWidth(500);
        translatedListPanel.setPrefHeight(900);
        translatedListPanel.setMaxHeight(900);
        translatedListPanel.setMaxWidth(500);
        translatedListPanel.setAlignment(Pos.CENTER);
        translatedListPanel.setTranslateX(-500);
        translatedListPanel.setVisible(false);

        translatedListPanelMeal = new VBox(10);
        translatedListPanelMeal.getStylesheets().add(getClass().getResource("/static/style.css").toExternalForm());
        translatedListPanelMeal.setId("translatedList");
        translatedListPanelMeal.setPrefWidth(500);
        translatedListPanelMeal.setPrefHeight(900);
        translatedListPanelMeal.setMaxHeight(900);
        translatedListPanelMeal.setMaxWidth(500);
        translatedListPanelMeal.setAlignment(Pos.CENTER);
        translatedListPanelMeal.setTranslateX(-500);
        translatedListPanelMeal.setVisible(false);

        addPrivateMealBox = new VBox(10);
        addPrivateMealBox.setPadding(new Insets(50, 50, 0, 0));
        addPrivateBoxLabelBox = new VBox(30);
        addPrivateMealBoxFieldBox = new VBox(30);
        addPrivateMealBoxButtonsBox = new HBox(10);
        addPrivateMealBoxElementsBox = new HBox(10);

        addPrivateMealBoxHeaderLabel = new Label("Add your special meals ");
        addPrivateMealBoxHeaderLabel.setStyle("-fx-font-size:30px;-fx-font-style:italic");

        addPrivateMealNameLabel = new Label("Food Name : ");
        addPrivateMealNameLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");

        chooseFoodLabel = new Label("Food Name : ");
        chooseFoodLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");

        mealNameField = new TextField();
        mealNameField.setPrefSize(200,50);
        mealNameField.setOnAction(e -> foodComboBox.requestFocus());
        mealNameField.setPromptText("Enter your meal name here");

        foodComboBox = new ComboBox<>();
        foodComboBox.setPrefSize(200, 50);
        foodComboBox.setPromptText("Enter your foods");

        addPrivateMealBoxSaveButton = new Button("Add");
        showMealList = new Button("Show Meal List");
        
        addPrivateMealBoxButtonsBox.getChildren().addAll(addPrivateMealBoxSaveButton,showMealList);
        addPrivateMealBoxFieldBox.getChildren().addAll(mealNameField,foodComboBox);
        addPrivateBoxLabelBox.getChildren().addAll(addPrivateMealNameLabel, chooseFoodLabel);
        addPrivateMealBoxElementsBox.getChildren().addAll(addPrivateBoxLabelBox, addPrivateMealBoxFieldBox);
        addPrivateMealBox.getChildren().addAll(addPrivateMealBoxHeaderLabel, addPrivateMealBoxElementsBox,addPrivateMealBoxButtonsBox);

        addPrivateFoodBox = new VBox(10);
        addPrivateFoodBox.setPadding(new Insets(50, 0, 0, 50));
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

        table = new TableView<>();
        table.setPrefHeight(600);
        table.setPrefWidth(600);
        table.setMaxWidth(600);
        table.setId("Food List");

        TableColumn<Food, String> nameCol = new TableColumn<>("food_name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        nameCol.setPrefWidth(100);

        TableColumn<Food, Float> calorieCol = new TableColumn<>("calorie");
        calorieCol.setCellValueFactory(new PropertyValueFactory<>("calorie"));
        calorieCol.setPrefWidth(90);

        TableColumn<Food, Float> fatCol = new TableColumn<>("fat");
        fatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));
        fatCol.setPrefWidth(90);

        TableColumn<Food, Float> carbCol = new TableColumn<>("carb");
        carbCol.setCellValueFactory(new PropertyValueFactory<>("carb"));
        carbCol.setPrefWidth(90);

        TableColumn<Food, Float> protCol = new TableColumn<>("prot");
        protCol.setCellValueFactory(new PropertyValueFactory<>("prot"));
        protCol.setPrefWidth(80);

        nameCol.setStyle("-fx-alignment: CENTER;");
        calorieCol.setStyle("-fx-alignment: CENTER;");
        fatCol.setStyle("-fx-alignment: CENTER;");
        carbCol.setStyle("-fx-alignment: CENTER;");
        protCol.setStyle("-fx-alignment: CENTER;");

        Label labelveriyok = new Label("İçeride veri yok :( ");
        labelveriyok.setStyle("-fx-text-fill:black;-fx-font-style:italic");
        table.setPlaceholder(labelveriyok);

        table.getColumns().addAll(nameCol, calorieCol, fatCol, carbCol, protCol);
        table.setItems(food_list);
        Button closeButton = new Button("Close");
        closeButton.setId("cikis_butonlari");
        closeButton.setOnAction(e -> {
            if (panelAcikMi) {
                TranslateTransition transition = new TranslateTransition(Duration.millis(300), translatedListPanel);
                transition.setToX(-500);
                transition.setOnFinished(event -> translatedListPanel.setVisible(false));
                transition.play();
                panelAcikMi = false;
            }
        });

        translatedListPanel.getChildren().addAll(table, closeButton);
        showFoodList = new Button("Show Food List");
        showFoodList.setOnAction(e -> {
            verileriCek();
            TranslateTransition transition = new TranslateTransition(Duration.millis(300), translatedListPanel);

            if (panelAcikMi) {
                transition.setToX(-500);
                transition.setOnFinished(event -> translatedListPanel.setVisible(false));
                panelAcikMi = false;
            } else {
                translatedListPanel.setVisible(true);
                transition.setToX(0);
                panelAcikMi = true;
            }

            transition.play();
        });

        addPrivateBoxElementsBox.getChildren().addAll(addPrivateBoxLabelBox, addPrivateBoxTextFieldBox);

        addPrivateBoxButtonsHBox.getChildren().addAll(addPrivateFoodBoxSaveButton, showFoodList);
        addPrivateBoxButtonsHBox.setPadding(new Insets(30, 0, 0, 120));
        addPrivateFoodBox.getChildren().addAll(addPrivateBoxHeaderLabel, addPrivateBoxElementsBox,
                addPrivateBoxButtonsHBox);

        StackPane leftStackPane = new StackPane();
        leftStackPane.getChildren().add(addPrivateFoodBox);
        leftStackPane.getChildren().add(translatedListPanel);
        pane.setLeft(leftStackPane);
        pane.setRight(addPrivateMealBox);
        pane.setBottom(exitButton);
        
    }

    public BorderPane getPane() {
        return pane;
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
                    Food food = new Food(foodName, calorie, fat, carb, prot);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Başarılı!");
                    alert.setHeaderText(null);
                    alert.setContentText("Veriler başarıyla kaydedildi.");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                    alert.showAndWait();
                    food_list.add(food);
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

    public void verileriCek() {
        String ad = null;
        float cal = 0;
        float fat = 0;
        float carb = 0;
        float prot = 0;

        try (Connection con = Database.connect()) {
            String sorgu = "SELECT * FROM saved_special_foods";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ResultSet rs = ps.executeQuery();
            food_list.clear();
            while (rs.next()) {
                ad = rs.getString("food_name");
                cal = rs.getFloat("calorie");
                fat = rs.getFloat("fat");
                carb = rs.getFloat("carb");
                prot = rs.getFloat("prot");
                Food food = new Food(ad, cal, fat, carb, prot);
                food_list.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
