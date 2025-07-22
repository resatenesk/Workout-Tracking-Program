package antrenmantakipcom.Business.FoodMeal;

import java.time.LocalDate;
import java.util.Date;

import org.controlsfx.control.CheckComboBox;

import antrenmantakipcom.Business.Utilities.Functions.Concrete.AlertFunction;
import antrenmantakipcom.Business.Utilities.Functions.Concrete.CreateButton;
import antrenmantakipcom.DataAccess.Abstract.IDailyFoodValueDal;
import antrenmantakipcom.DataAccess.Abstract.IMealDal;
import antrenmantakipcom.DataAccess.Abstract.IUserDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.DailyFoodValueDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.MealDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.UserDal;
import antrenmantakipcom.Entities.Concrete.DailyFoodValue;
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
import javafx.scene.control.DatePicker;
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
    private ObservableList<DailyFoodValue> daily_food_values_list;
    private DatePicker datePicker2;

    private TableView<DailyFoodValue> table;
    private boolean foodPanelAcikMi = false;
    private VBox translatedListPanel;
    private StackPane centerStackPane;
    private Button showValueList;
    private int user_id;

    IDailyFoodValueDal _DailyFoodValueDal;
    IUserDal _IUserDal;
    IMealDal _IMealDal;

    public DailyMacroAndFoodValuesScreen(String username) {
        this.username = username;
        root = new BorderPane();


        _DailyFoodValueDal = new DailyFoodValueDal(DailyFoodValue.class);
        _IUserDal = new UserDal(User.class);
        User user = new User();
        user.setUsername(username);
        user_id = _IUserDal.selectUserID(user);
        _IMealDal = new MealDal(Meal.class);

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

        Button deleteButton = CreateButton.createDeleteButton();
        deleteButton.setOnAction(e -> {
            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            DailyFoodValue selectedData = table.getSelectionModel().getSelectedItem();
            if (selectedIndex >= 0) {
                int sonuc = _DailyFoodValueDal.Delete(selectedData, selectedData.getId());
                if (sonuc > 0) {
                    AlertFunction.ShowDeletedAlert();
                    if (selectedData != null) {
                        daily_food_values_list.remove(selectedData);
                        table.getSelectionModel().clearSelection();
                    }

                } else {
                    AlertFunction.FailAlert();
                }
            } else {
                AlertFunction.NoElementsSelectedAlert();
            }
        });

        translatedListPanel.getChildren().addAll(table, closeButton, deleteButton);
        showValueList = CreateButton.createListButton();
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

        saveManualButton = CreateButton.createSaveButton();
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

        saveButton = CreateButton.createSaveButton();
        saveButton.setPrefSize(120, 40);
        saveButton.setMaxSize(120, 40);

        saveButton.setOnAction(e -> {
            SaveDailyCalorie();
            fatTextField.setText("");
            carbTextField.setText("");
            calorieTextField.setText("");
            protTextField.setText("");
            datePicker.setValue(null);
        });

        datePicker = new DatePicker();
        datePicker.setPromptText("Enter a date");

        geriDonButton = CreateButton.createExitButton();
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
        String calorieText = calorieTextField.getText().trim();
        String fatText = fatTextField.getText().trim();
        String carbText = carbTextField.getText().trim();
        String protText = protTextField.getText().trim();
        LocalDate date = datePicker.getValue();

        if (calorieText.isEmpty() || fatText.isEmpty() || carbText.isEmpty() || protText.isEmpty()) {
            AlertFunction.MissingDataAlert();
            return;
        }
        if (date == null) {
            AlertFunction.DateIsNotSelectedAlert();
        }

        try {
            float calorie = Float.parseFloat(calorieText);
            float fat = Float.parseFloat(fatText);
            float carb = Float.parseFloat(carbText);
            float prot = Float.parseFloat(protText);

            DailyFoodValue entity = new DailyFoodValue(user_id,calorie, fat, carb, prot, date);
            int sonuc = _DailyFoodValueDal.Add(entity);
            if (sonuc > 0) {
                daily_food_values_list.add(entity);
                AlertFunction.SuccessAlert();
            } else {
                AlertFunction.FailAlert();
            }
        }

        catch (NumberFormatException e) {
            AlertFunction.NumberFormatExceptionAlert();
        }
    }

    public void getData() {

            meal_list = _IMealDal.GetAll("SELECT * FROM saved_meals WHERE user_id = ?", user_id);
            selectMeal.getItems().setAll(meal_list);
            
        

    }

    public void SaveDailyCalorieManual() {
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
            AlertFunction.DateIsNotSelectedAlert();
            return;
        }

        try {

            DailyFoodValue data = new DailyFoodValue(user_id,totalCal, totalFat, totalCarb, totalProt, date);
            int sonuc = _DailyFoodValueDal.Add(data);
            if (sonuc > 0) {

                daily_food_values_list.add(data);
                AlertFunction.SuccessAlert();

            } else {
                AlertFunction.FailAlert();
            }
        }

        catch (NumberFormatException e) {
            AlertFunction.NumberFormatExceptionAlert();
        }
    }

    void daily_food_valuesGetData() {
        daily_food_values_list = _DailyFoodValueDal.GetAll("SELECT * FROM daily_food_values WHERE user_id = ?",
                user_id);
        table.setItems(daily_food_values_list);
    }

    void createTableOfListOfAddedValues() {

        table.setPrefHeight(600);
        table.setPrefWidth(600);
        table.setMaxWidth(600);
        table.setId("Food List");

        TableColumn<DailyFoodValue, Float> calCol = new TableColumn<>("calorie");
        calCol.setCellValueFactory(new PropertyValueFactory<>("Cal"));
        calCol.setPrefWidth(100);

        TableColumn<DailyFoodValue, Float> fatCol = new TableColumn<>("fat");
        fatCol.setCellValueFactory(new PropertyValueFactory<>("Fat"));
        fatCol.setPrefWidth(90);

        TableColumn<DailyFoodValue, Float> carbCol = new TableColumn<>("carb");
        carbCol.setCellValueFactory(new PropertyValueFactory<>("Carb"));
        carbCol.setPrefWidth(90);

        TableColumn<DailyFoodValue, Float> protCol = new TableColumn<>("prot");
        protCol.setCellValueFactory(new PropertyValueFactory<>("Prot"));
        protCol.setPrefWidth(90);

        TableColumn<DailyFoodValue, Date> dateCol = new TableColumn<>("date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
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
