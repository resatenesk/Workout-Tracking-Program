package antrenmantakipcom.Business.FoodMeal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.controlsfx.control.CheckComboBox;

import antrenmantakipcom.Business.Utilities.Functions.Concrete.AlertFunction;
import antrenmantakipcom.Business.Utilities.Functions.Concrete.CreateButton;
import antrenmantakipcom.DataAccess.Abstract.IEntityRepositoryBase;
import antrenmantakipcom.DataAccess.Concrete.Dal.UserDal;
import antrenmantakipcom.Entities.Concrete.Food;
import antrenmantakipcom.Entities.Concrete.Meal;
import antrenmantakipcom.Entities.Concrete.User;
import antrenmantakipcom.Main;
import antrenmantakipcom.MainScreen;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    int user_id = 0;
    IEntityRepositoryBase<Food> foodBase;
    UserDal userBase;
    IEntityRepositoryBase<Meal> mealBase;

    public CreateSpecialMealCreateSpecialFood(String username) {
        this.username = username;
        pane = new BorderPane();
        foodBase = new IEntityRepositoryBase<>(Food.class);
        userBase = new UserDal(User.class);
        mealBase = new IEntityRepositoryBase<>(Meal.class);
        User user = new User();
        user.setUsername(username);
        user_id = userBase.selectUserID(user);

        food_list = FXCollections.observableArrayList();
        meal_list = FXCollections.observableArrayList();

        exitButton = CreateButton.createExitButton();
        exitButton.setOnAction(e -> {
            Main.setRoot(MainScreen.getRoot());
        });

        close_meal_listButton = CreateButton.createCloseButton();
        close_meal_listButton.setOnAction(e -> {
            if (mealPanelAcikMi) {
                TranslateTransition transition = new TranslateTransition(Duration.millis(300), translatedListPanelMeal);
                transition.setToX(1200);
                transition.setOnFinished(event -> translatedListPanelMeal.setVisible(false));
                transition.play();
                mealPanelAcikMi = false;
            }
        });

        addPrivateFoodBoxSaveButton = CreateButton.createSaveButton();
        addPrivateFoodBoxSaveButton.setOnAction(e -> {
            saveSpecialFood();
            foodNameField.setText("");
            foodCalorieField.setText("");
            foodFatField.setText("");
            foodCarbField.setText("");
            foodProtField.setText("");
            gramsField.setText("");
        });

        addPrivateFoodBoxDeleteButton = CreateButton.createDeleteButton();
        addPrivateFoodBoxDeleteButton.setOnAction(e -> {
            Food food = table.getSelectionModel().getSelectedItem();

            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                int result = foodBase.Delete(food, food.getFoodID());
                if (result > 0) {
                    AlertFunction.ShowDeletedAlert();
                    food_list.remove(food);
                    table.getSelectionModel().clearSelection();
                } else {
                    AlertFunction.NoConnectionWithDatabaseAlert();
                }
            } else {
                AlertFunction.NoElementsSelectedAlert();
            }

        });

        addPrivateMealBoxDeleteButton = CreateButton.createDeleteButton();
        addPrivateMealBoxDeleteButton.setOnAction(e -> {

            int selected_index = meal_table.getSelectionModel().getSelectedIndex();

            if (selected_index >= 0) {
                Meal meal = meal_table.getSelectionModel().getSelectedItem();
                int meal_id = meal.getMealID();
                int sonuc = mealBase.Delete(meal, meal_id);
                if (sonuc > 0) {
                    AlertFunction.ShowDeletedAlert();
                    Meal selectedMeal = meal_table.getSelectionModel().getSelectedItem();
                    if (selectedMeal != null) {
                        meal_list.remove(selectedMeal);
                        table.getSelectionModel().clearSelection();
                    }
                } else {
                    AlertFunction.NoConnectionWithDatabaseAlert();
                }
            } else {
                AlertFunction.NoElementsSelectedAlert();
            }

        });

        addPrivateMealBoxSaveButton = CreateButton.createSaveButton();
        addPrivateMealBoxSaveButton.setOnAction(e -> {
            saveMeal();
        });

        showMealList = CreateButton.createListButton();
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

        Button closeButton = CreateButton.createCloseButton();
        closeButton.setOnAction(e -> {
            if (foodPanelAcikMi) {
                TranslateTransition transition = new TranslateTransition(Duration.millis(300), translatedListPanel);
                transition.setToX(-500);
                transition.setOnFinished(event -> translatedListPanel.setVisible(false));
                transition.play();
                foodPanelAcikMi = false;
            }
        });

        showFoodList = CreateButton.createListButton();
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

        translatedListPanel = new VBox(10);
        translatedListPanel.setId("translatedListPanel");
        translatedListPanel.getStylesheets().add(getClass().getResource("/static/style.css").toExternalForm());
        translatedListPanel.setPrefWidth(500);
        translatedListPanel.setPrefHeight(900);
        translatedListPanel.setMaxHeight(900);
        translatedListPanel.setMaxWidth(500);
        translatedListPanel.setAlignment(Pos.CENTER);
        translatedListPanel.setTranslateX(-500);
        translatedListPanel.setVisible(false);

        searchField = new TextField();
        searchField.setPromptText("Search...");

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
        translatedListPanelMeal.setId("translatedListPanel");
        translatedListPanelMeal.getStylesheets().add(getClass().getResource("/static/style.css").toExternalForm());
        translatedListPanelMeal.setPrefWidth(500);
        translatedListPanelMeal.setPrefHeight(900);
        translatedListPanelMeal.setMaxHeight(900);
        translatedListPanelMeal.setMaxWidth(500);
        translatedListPanelMeal.setAlignment(Pos.CENTER);
        translatedListPanelMeal.setTranslateX(1200);
        translatedListPanelMeal.setVisible(false);

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

        table = new TableView<>();
        table.setPrefHeight(600);
        table.setPrefWidth(600);
        table.setMaxWidth(600);
        table.setId("Food List");

        TableColumn<Food, Integer> foodIdCol = new TableColumn<>("ID");
        foodIdCol.setCellValueFactory(new PropertyValueFactory<>("foodID"));
        foodIdCol.setVisible(false);

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

        Label labelveriyok = new Label("No Data 🔧:( ");
        labelveriyok.setStyle("-fx-text-fill:black;-fx-font-style:italic");
        table.setPlaceholder(labelveriyok);

        table.getColumns().addAll(foodIdCol, nameCol, calorieCol, fatCol, carbCol, protCol);

        meal_table = new TableView<>();
        meal_table.setPrefHeight(600);
        meal_table.setPrefWidth(600);
        meal_table.setMaxWidth(600);
        meal_table.setId("Meal List");

        TableColumn<Meal, Integer> MealIDColumn = new TableColumn<>("ID");
        MealIDColumn.setCellValueFactory(new PropertyValueFactory<>("mealID"));
        MealIDColumn.setVisible(false);

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

        Label labelveriyok2 = new Label("No Data 🔧:( ");
        labelveriyok2.setStyle("-fx-text-fill:black;-fx-font-style:italic");
        meal_table.setPlaceholder(labelveriyok2);

        meal_table.getColumns().addAll(MealIDColumn, meal_name_clmn, meal_total_cal_clmn, meal_total_fat_clmn,
                meal_total_carb_clmn,
                meal_total_prot_clmn);

        meal_table.setItems(meal_list);
        translatedListPanelMeal.getChildren().addAll(meal_table, close_meal_listButton, addPrivateMealBoxDeleteButton);

        translatedListPanel.getChildren().addAll(table, closeButton, addPrivateFoodBoxDeleteButton);

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

        CheckComboBoxAyarlama();

    }

    public void CheckComboBoxAyarlama() {
        ObservableList<Food> list = foodBase.GetAll("SELECT * FROM saved_special_foods WHERE user_id=?", user_id);
        food_list.clear();
        food_list.setAll(list);
        foodComboBox.getItems().setAll(food_list);
    }

    public BorderPane getPane() {
        pane.setId("rootBackgroundGeneral");
        return pane;

    }

    public void saveSpecialFood() {
        String foodName = foodNameField.getText().trim();
        String calorieText = foodCalorieField.getText().trim();
        String fatText = foodFatField.getText().trim();
        String carbText = foodCarbField.getText().trim();
        String protText = foodProtField.getText().trim();
        String grams = gramsField.getText().trim();

        if (foodName.isEmpty() || calorieText.isEmpty() || fatText.isEmpty() || carbText.isEmpty()
                || protText.isEmpty() || grams.isEmpty()) {
            AlertFunction.MissingDataAlert();
            return;
        }

        try {
            foodName = foodName + "(" + grams + " gr)";
            float calorie = Float.parseFloat(calorieText);
            float fat = Float.parseFloat(fatText);
            float carb = Float.parseFloat(carbText);
            float prot = Float.parseFloat(protText);
            Food food = new Food(foodName, calorie, fat, carb, prot);
            int sonuc = foodBase.Add(food);
            if (sonuc > 0) {
                AlertFunction.SuccessAlert();
            } else {
                AlertFunction.FailAlert();
            }

        } catch (NumberFormatException e) {
            AlertFunction.NumberFormatExceptionAlert();
        }

    }

    public void saveMeal() {

        String meal_name = mealNameField.getText().trim();
        float totalCal = 0;
        float totalFat = 0;
        float totalCarb = 0;
        float totalProt = 0;

        ArrayList<Food> secilenFoodList = new ArrayList<>(foodComboBox.getCheckModel().getCheckedItems());

        for (Food food : secilenFoodList) {
            totalCal += food.getCalorie();
            totalFat += food.getFat();
            totalCarb += food.getCarb();
            totalProt += food.getProt();
        }
        Meal meal = new Meal(user_id, meal_name, totalCal, totalFat, totalCarb, totalProt);

        if (mealNameField.getText().isEmpty() || foodComboBox.getCheckModel().getCheckedItems().isEmpty()) {
            AlertFunction.MissingDataAlert();
        } else {
            int result = mealBase.Add(meal);
            if (result > 0) {
                meal_list.add(meal);
                searchField.setText("");
                mealNameField.setText("");
                foodComboBox.getCheckModel().clearChecks();
                AlertFunction.SuccessAlert();

            } else {
                AlertFunction.FailAlert();

            }

        }

    }

    public void showMealList() {

        ObservableList<Meal> list = mealBase.GetAll("SELECT * FROM saved_meals WHERE user_id = ?", user_id);
        meal_list.clear();
        meal_list.addAll(list);
        meal_table.setItems(meal_list);

    }

    public void verileriCek() {
        ObservableList<Food> yeniListe = foodBase.GetAll(
                "SELECT id,food_name,calorie,fat,carb,prot FROM saved_special_foods WHERE user_id = ?", user_id);

        food_list.clear();
        food_list.addAll(yeniListe);
        table.setItems(food_list);
    }

}
