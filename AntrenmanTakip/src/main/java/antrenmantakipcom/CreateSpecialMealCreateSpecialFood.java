package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.controlsfx.control.CheckComboBox;

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

public class CreateSpecialMealCreateSpecialFood {
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
    private Button addPrivateFoodBoxDeleteButton;
    private TextField foodNameField;
    private TextField foodCalorieField;
    private TextField foodFatField;
    private TextField foodCarbField;
    private TextField foodProtField;
    private ObservableList<Food> food_list;
    private Button showFoodList;
    private boolean foodPanelAcikMi = false;
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
    private Button addPrivateMealBoxDeleteButton;
    private Button showMealList;
    private TextField mealNameField;
    private boolean mealPanelAcikMi = false;
    private CheckComboBox<Food> foodComboBox;
    private ObservableList<Meal> meal_list;
    private VBox translatedListPanelMeal;
    private StackPane centerStackPane2;
    private TableView<Food> table2;
    private TextField searchField;
    private TableView<Meal> meal_table;
    private Button close_meal_listButton;
    private String username;
    private HBox selectedFoodBoxesContainerLabel = new HBox(15);
    private HBox selectedFoodBoxesContainer = new HBox(35);
    private ComboBox<Integer> amountComboBox;
    private Map<Food, ComboBox<Integer>> foodAmountMap = new HashMap<>();
    private TextField gramsField;

    public class Meal {
        private String meal_name;
        private float total_cal;
        private float total_fat;
        private float total_carb;
        private float total_prot;
        private ArrayList<Food> foods = new ArrayList<>();

        public Meal(String meal_name, float total_calorie, float total_fat, float total_carb, float total_prot,
                ArrayList<Food> foods) {
            this.meal_name = meal_name;
            this.total_cal = total_calorie;
            this.total_fat = total_fat;
            this.total_carb = total_carb;
            this.total_prot = total_prot;
            this.foods = foods;
        }

        public String getMealName() {
            return meal_name;
        }

        public float getTotalCal() {
            return total_cal;
        }

        public float getTotalFat() {
            return total_fat;
        }

        public float getTotalCarb() {
            return total_carb;
        }

        public float getTotalProt() {
            return total_prot;
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
            return String.format("%s (cal: %.2f, fat: %.2f, carb: %.2f, prot: %.2f)",
                    foodName, calorie, fat, carb, prot);
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

    public CreateSpecialMealCreateSpecialFood(String username) {
        this.username = username;
        pane = new BorderPane();
        food_list = FXCollections.observableArrayList();
        meal_list = FXCollections.observableArrayList();
        exitButton = new Button("Exit");
        exitButton.setId("cikis_butonlari");
        exitButton.setOnAction(e -> {
            Main.setRoot(MainScreen.getRoot());
        });
        close_meal_listButton = new Button("Close");
        close_meal_listButton.setId("cikis_butonlari");
        close_meal_listButton.setOnAction(e -> {
            if (mealPanelAcikMi) {
                TranslateTransition transition = new TranslateTransition(Duration.millis(300), translatedListPanelMeal);
                transition.setToX(1200);
                transition.setOnFinished(event -> translatedListPanelMeal.setVisible(false));
                transition.play();
                mealPanelAcikMi = false;
            }
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

        searchField = new TextField();
        searchField.setPromptText("Ara...");

        searchField.textProperty().addListener((obs, oldText, newText) -> {
            ObservableList<Food> filtered = FXCollections.observableArrayList();

            for (Food food : food_list) {
                if (food.getFoodName().toLowerCase().contains(newText.toLowerCase())) {
                    filtered.add(food);
                }
            }

            if (filtered.isEmpty()) {
                foodComboBox.hide();
            } else {
                foodComboBox.getItems().setAll(filtered);
                foodComboBox.show();
            }

            foodComboBox.getItems().setAll(filtered);
        });

        translatedListPanelMeal = new VBox(10);
        translatedListPanelMeal.getStylesheets().add(getClass().getResource("/static/style.css").toExternalForm());
        translatedListPanelMeal.setId("translatedList");
        translatedListPanelMeal.setPrefWidth(500);
        translatedListPanelMeal.setPrefHeight(900);
        translatedListPanelMeal.setMaxHeight(900);
        translatedListPanelMeal.setMaxWidth(500);
        translatedListPanelMeal.setAlignment(Pos.CENTER);
        translatedListPanelMeal.setTranslateX(1200);
        translatedListPanelMeal.setVisible(false);

        addPrivateFoodBoxDeleteButton = new Button("Delete");
        addPrivateFoodBoxDeleteButton.setOnAction(e -> {
            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            int food_id = 0;
            if (selectedIndex >= 0) {
                try (Connection con = Database.connect()) {
                    String id_sorgu = "SELECT id FROM saved_special_foods WHERE food_name = ?";
                    PreparedStatement ps = con.prepareStatement(id_sorgu);
                    ps.setString(1, table.getSelectionModel().getSelectedItem().foodName);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        food_id = rs.getInt("id");
                    }
                    String sorgu = "DELETE FROM saved_special_foods WHERE id = ? ";
                    PreparedStatement ps2 = con.prepareStatement(sorgu);
                    ps2.setInt(1, food_id);
                    int sonuc = ps2.executeUpdate();
                    if (sonuc > 0) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("DELETED");
                        alert.setHeaderText(null);
                        alert.setContentText("Food has deleted succesfully");
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets()
                                .add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                        alert.showAndWait();
                        Food selectedFood = table.getSelectionModel().getSelectedItem();
                        if (selectedFood != null) {
                            food_list.remove(selectedFood);
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
        addPrivateMealBoxDeleteButton = new Button("Delete");
        addPrivateMealBoxDeleteButton.setOnAction(e -> {
            int selectedIndex = meal_table.getSelectionModel().getSelectedIndex();
            int food_id = 0;
            if (selectedIndex >= 0) {
                try (Connection con = Database.connect()) {
                    String id_sorgu = "SELECT id FROM saved_meals WHERE meal_name = ?";
                    PreparedStatement ps = con.prepareStatement(id_sorgu);
                    ps.setString(1, meal_table.getSelectionModel().getSelectedItem().meal_name);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        food_id = rs.getInt("id");
                    }
                    String sorgu = "DELETE FROM saved_meals WHERE id = ? ";
                    PreparedStatement ps2 = con.prepareStatement(sorgu);
                    ps2.setInt(1, food_id);
                    int sonuc = ps2.executeUpdate();
                    if (sonuc > 0) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("DELETED");
                        alert.setHeaderText(null);
                        alert.setContentText("Meal has deleted succesfully");
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets()
                                .add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                        alert.showAndWait();
                        Meal selectedMeal = meal_table.getSelectionModel().getSelectedItem();
                        if (selectedMeal != null) {
                            meal_list.remove(selectedMeal);
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
                alert.setContentText("Please select a meal correctly");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets()
                        .add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            }

        });

        addPrivateMealBox = new VBox(10);
        addPrivateMealBox.setPadding(new Insets(150, 150, 0, 0));
        addPrivateBoxLabelBox = new VBox(30);
        addPrivateMealBoxFieldBox = new VBox(30);
        addPrivateMealBoxButtonsBox = new HBox(10);
        addPrivateMealBoxElementsBox = new HBox(10);

        addPrivateMealBoxHeaderLabel = new Label("Add your special meals ");
        addPrivateMealBoxHeaderLabel.setStyle("-fx-font-size:30px;-fx-font-style:italic");

        addPrivateMealNameLabel = new Label("Meal Name : ");
        addPrivateMealNameLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");

        chooseFoodLabel = new Label("Food Name : ");
        chooseFoodLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");

        mealNameField = new TextField();
        mealNameField.setPrefSize(200, 50);
        mealNameField.setOnAction(e -> foodComboBox.requestFocus());
        mealNameField.setPromptText("Enter your meal name here");

        foodComboBox = new CheckComboBox<>();
        foodComboBox.setPrefSize(400, 50);
        foodComboBox.setMaxSize(400, 50);

       

        addPrivateMealBoxSaveButton = new Button("Add");
        addPrivateMealBoxSaveButton.setOnAction(e -> {
            saveMeal();
        });
        showMealList = new Button("Show Meal List");

        selectedFoodBoxesContainer.setMaxSize(40, 40);
        selectedFoodBoxesContainer.setPrefSize(40, 40);
        addPrivateMealBoxButtonsBox.getChildren().addAll(addPrivateMealBoxSaveButton, showMealList);
        addPrivateMealBoxFieldBox.getChildren().addAll(mealNameField, foodComboBox, selectedFoodBoxesContainerLabel,
                selectedFoodBoxesContainer);
        addPrivateBoxLabelBox.getChildren().addAll(addPrivateMealNameLabel, chooseFoodLabel);
        addPrivateMealBoxElementsBox.getChildren().addAll(addPrivateBoxLabelBox, addPrivateMealBoxFieldBox);
        addPrivateMealBox.getChildren().addAll(addPrivateMealBoxHeaderLabel, searchField, addPrivateMealBoxElementsBox,
                addPrivateMealBoxButtonsBox);

        addPrivateFoodBox = new VBox(5);
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
        Label gramsLabel = new Label("Grams: ");
        gramsLabel.setStyle("-fx-font-size:15px;-fx-font-style:italic");

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
        foodProtField.setOnAction(e -> gramsField.requestFocus());
        foodProtField.setPromptText("Enter prot(grams) of the food here(kcal)");
        gramsField = new TextField();
        gramsField.setPrefSize(300, 80);
        gramsField.setPromptText("Enter that how much you want to add(grams)");

        addPrivateBoxLabelBox.getChildren().addAll(addPrivateBoxFoodNameLabel, addPrivateBoxCalorieLabel,
                addPrivateBoxFatLabel, addPrivateBoxCarbLabel, addPrivateBoxProtLabel, gramsLabel);
        addPrivateBoxTextFieldBox.getChildren().addAll(foodNameField, foodCalorieField, foodFatField, foodCarbField,
                foodProtField, gramsField);

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

        meal_table = new TableView<>();
        meal_table.setPrefHeight(600);
        meal_table.setPrefWidth(600);
        meal_table.setMaxWidth(600);
        meal_table.setId("Meal List");

        TableColumn<Meal, String> meal_name_clmn = new TableColumn<>("meal_name");
        meal_name_clmn.setCellValueFactory(new PropertyValueFactory<>("mealName"));
        meal_name_clmn.setPrefWidth(100);

        TableColumn<Meal, Float> meal_total_cal_clmn = new TableColumn<>("total_cal");
        meal_total_cal_clmn.setCellValueFactory(new PropertyValueFactory<>("totalCal"));
        meal_total_cal_clmn.setPrefWidth(90);

        TableColumn<Meal, Float> meal_total_fat_clmn = new TableColumn<>("fat");
        meal_total_fat_clmn.setCellValueFactory(new PropertyValueFactory<>("totalFat"));
        meal_total_fat_clmn.setPrefWidth(90);

        TableColumn<Meal, Float> meal_total_carb_clmn = new TableColumn<>("carb");
        meal_total_carb_clmn.setCellValueFactory(new PropertyValueFactory<>("totalCarb"));
        meal_total_carb_clmn.setPrefWidth(90);

        TableColumn<Meal, Float> meal_total_prot_clmn = new TableColumn<>("prot");
        meal_total_prot_clmn.setCellValueFactory(new PropertyValueFactory<>("totalProt"));
        meal_total_prot_clmn.setPrefWidth(80);

        meal_name_clmn.setStyle("-fx-alignment: CENTER;");
        meal_total_cal_clmn.setStyle("-fx-alignment: CENTER;");
        meal_total_fat_clmn.setStyle("-fx-alignment: CENTER;");
        meal_total_carb_clmn.setStyle("-fx-alignment: CENTER;");
        meal_total_prot_clmn.setStyle("-fx-alignment: CENTER;");

        Label labelveriyok2 = new Label("İçeride veri yok :( ");
        labelveriyok2.setStyle("-fx-text-fill:black;-fx-font-style:italic");
        meal_table.setPlaceholder(labelveriyok2);

        meal_table.getColumns().addAll(meal_name_clmn, meal_total_cal_clmn, meal_total_fat_clmn, meal_total_carb_clmn,
                meal_total_prot_clmn);

        meal_table.setItems(meal_list);

        translatedListPanelMeal.getChildren().addAll(meal_table, close_meal_listButton, addPrivateMealBoxDeleteButton);
        showMealList.setOnAction(e -> {
            showMealList();
            TranslateTransition transition = new TranslateTransition(Duration.millis(300), translatedListPanelMeal);

            if (mealPanelAcikMi) {
                transition.setToX(1200);
                transition.setOnFinished(event -> translatedListPanelMeal.setVisible(false));
                mealPanelAcikMi = false;
            } else {
                translatedListPanelMeal.setVisible(true);
                transition.setToX(100);
                mealPanelAcikMi = true;
            }

            transition.play();
        });

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

        translatedListPanel.getChildren().addAll(table, closeButton, addPrivateFoodBoxDeleteButton);
        showFoodList = new Button("Show Food List");
        showFoodList.setOnAction(e -> {
            verileriCek();
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

        addPrivateBoxElementsBox.getChildren().addAll(addPrivateBoxLabelBox, addPrivateBoxTextFieldBox);

        addPrivateBoxButtonsHBox.getChildren().addAll(addPrivateFoodBoxSaveButton, showFoodList);
        addPrivateBoxButtonsHBox.setPadding(new Insets(30, 0, 0, 120));
        addPrivateFoodBox.getChildren().addAll(addPrivateBoxHeaderLabel, addPrivateBoxElementsBox,
                addPrivateBoxButtonsHBox);

        StackPane leftStackPane = new StackPane();
        leftStackPane.getChildren().add(addPrivateFoodBox);
        leftStackPane.getChildren().add(translatedListPanel);
        StackPane rightStackPane = new StackPane();
        rightStackPane.getChildren().add(addPrivateMealBox);
        rightStackPane.getChildren().add(translatedListPanelMeal);

        pane.setLeft(leftStackPane);
        pane.setRight(rightStackPane);
        pane.setBottom(exitButton);
        verileriCek();
        foodComboBox.getItems().setAll(food_list);

    }

    public void CheckComboBoxAyarlama() {
        int meal_id;
        String food_name;
        float calorie;
        float fat;
        float carb;
        float prot;
        ArrayList<Food> yemekler = new ArrayList<>();
        try (Connection con = Database.connect()) {
            String sorgu = "SELECT * FROM saved_special_foods";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                meal_id = rs.getInt("id");
                food_name = rs.getString("food_name");
                calorie = rs.getFloat("calorie");
                fat = rs.getFloat("fat");
                carb = rs.getFloat("carb");
                prot = rs.getFloat("prot");
                Food food = new Food(food_name, calorie, fat, carb, prot);
                yemekler.add(food);
            }
            food_list = FXCollections.observableArrayList(yemekler);
            foodComboBox.getItems().setAll(food_list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String grams = gramsField.getText().trim();
        int user_id = 0;

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
            foodName = foodName + "(" + grams + " gr)";
            float calorie = Float.parseFloat(calorieText);
            float fat = Float.parseFloat(fatText);
            float carb = Float.parseFloat(carbText);
            float prot = Float.parseFloat(protText);

            try (Connection con = Database.connect()) {
                String sorgu = "INSERT INTO saved_special_foods (food_name,user_id,calorie,fat,carb,prot) VALUES (?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sorgu);
                ps.setString(1, foodName);
                ps.setInt(2, user_id);
                ps.setFloat(3, calorie);
                ps.setFloat(4, fat);
                ps.setFloat(5, carb);
                ps.setFloat(6, prot);

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
                    foodComboBox.getItems().setAll(food_list);
                    

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

    public void saveMeal() {
        int user_id = 0;
        String meal_name = mealNameField.getText().trim();
        float totalCal = 0;
        float totalFat = 0;
        float totalCarb = 0;
        float totalProt = 0;
        ArrayList<Food> secilenFoodList = new ArrayList<>(foodComboBox.getCheckModel().getCheckedItems());
        for (Food food : secilenFoodList) {
            int miktar = 1;
            if (foodAmountMap.containsKey(food)) {
                miktar = foodAmountMap.get(food).getValue();
            }

            float cal = food.calorie;
            float fat = food.fat;
            float carb = food.carb;
            float prot = food.prot;

            totalCal += cal * miktar;
            totalFat += fat * miktar;
            totalCarb += carb * miktar;
            totalProt += prot * miktar;
        }
        Meal meal = new Meal(meal_name, totalCal, totalFat, totalCarb, totalProt, secilenFoodList);
        meal_list.add(meal);
        if (mealNameField.getText().isEmpty() || foodComboBox.getCheckModel().getCheckedItems().isEmpty()) {
            Alert hataAlert = new Alert(Alert.AlertType.WARNING);
            hataAlert.setTitle("EMPTY AREA!");
            hataAlert.setHeaderText(null);
            hataAlert.setContentText("Please make sure you writed a name and choosed a food");
            hataAlert.showAndWait();
        } else {

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
            try (Connection con = Database.connect()) {
                String sorgu = "INSERT INTO saved_meals (meal_name,user_id,total_cal,total_fat,total_carb,total_prot) VALUES (?,?,?,?,?,?) ";
                PreparedStatement ps = con.prepareStatement(sorgu);
                ps.setString(1, meal_name);
                ps.setInt(2, user_id);
                ps.setFloat(3, totalCal);
                ps.setFloat(4, totalFat);
                ps.setFloat(5, totalCarb);
                ps.setFloat(6, totalProt);

                int result = ps.executeUpdate();
                if (result > 0) {
                    searchField.setText("");
                    mealNameField.setText("");
                    foodComboBox.getCheckModel().clearChecks();
                    Alert hataAlert = new Alert(Alert.AlertType.INFORMATION);
                    hataAlert.setTitle("Meal is added");
                    hataAlert.setHeaderText(null);
                    hataAlert.setContentText("You can show the list of meals with pressing show button");
                    hataAlert.showAndWait();

                } else {
                    Alert hataAlert = new Alert(Alert.AlertType.ERROR);
                    hataAlert.setTitle("Error!");
                    hataAlert.setHeaderText(null);
                    hataAlert.setContentText("The meal could not be added");
                    hataAlert.showAndWait();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void showMealList() {
        meal_list.clear();
        int user_id = 0;
        String meal_name = null;
        float total_cal = 0;
        float total_fat = 0;
        float total_carb = 0;
        float total_prot = 0;

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

        try (Connection con = Database.connect()) {
            String sorgu = "SELECT * FROM saved_meals WHERE user_id =?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                meal_name = rs.getString("meal_name");
                total_cal = rs.getFloat("total_cal");
                total_fat = rs.getFloat("total_fat");
                total_carb = rs.getFloat("total_carb");
                total_prot = rs.getFloat("total_prot");
                ArrayList<Food> foods = new ArrayList<>();

                Meal meal = new Meal(meal_name, total_cal, total_fat, total_carb, total_prot, foods);
                meal_list.add(meal);
            }

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
        int user_id = 0;

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

        try (Connection con = Database.connect()) {
            String sorgu = "SELECT * FROM saved_special_foods WHERE user_id =?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setInt(1, user_id);
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
