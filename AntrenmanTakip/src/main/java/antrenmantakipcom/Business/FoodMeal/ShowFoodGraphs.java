package antrenmantakipcom.Business.FoodMeal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import antrenmantakipcom.Business.Utilities.Functions.Concrete.CreateButton;
import antrenmantakipcom.DataAccess.Abstract.IDailyFoodValueDal;
import antrenmantakipcom.DataAccess.Abstract.IUserDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.DailyFoodValueDal;
import antrenmantakipcom.DataAccess.Concrete.Dal.UserDal;
import antrenmantakipcom.DataAccess.Concrete.Database;
import antrenmantakipcom.Entities.Concrete.DailyFoodValue;
import antrenmantakipcom.Entities.Concrete.User;
import antrenmantakipcom.Main;
import antrenmantakipcom.MainScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ShowFoodGraphs {
    private BorderPane pane;
    private String username;
    private VBox generalBox;
    private HBox LineChartHBox;
    private HBox PieChartHBox;
    private Button previousButton1;
    private Button nextButton1;
    private Button previousButton2;
    private Button nextButton2;
    private StackPane graphsBox;
    private StackPane PieChartBox;
    private ObservableList<DailyFoodValue> dataList;
    private int user_id = 0;
    private Button geriDon;
    private IUserDal _IUserDal;
    private IDailyFoodValueDal _DailyFoodValueDal;

    public ShowFoodGraphs(String username) {
        _IUserDal = new UserDal(User.class);
        _DailyFoodValueDal = new DailyFoodValueDal(DailyFoodValue.class);

        this.username = username;
        User user = new User();
        user.setUsername(username);
        user_id = _IUserDal.selectUserID(user);
        pane = new BorderPane();

        geriDon = CreateButton.createExitButton();
        geriDon.setOnAction(e -> {
            try {
                Main.setRoot(MainScreen.getRoot());
            } catch (Exception ex) {
            }
        });

        generalBox = new VBox(10);
        LineChartHBox = new HBox(10);
        LineChartHBox.setAlignment(Pos.CENTER);
        PieChartHBox = new HBox(10);
        PieChartHBox.setAlignment(Pos.CENTER);

        previousButton1 = new Button("Weekly");
        previousButton1.setOnAction(e -> {
            drawGraphs(7);
        });
        previousButton1.setPrefSize(150, 40);
        nextButton1 = new Button("Monthly");
        nextButton1.setOnAction(e -> {
            drawGraphs(30);
        });
        nextButton1.setPrefSize(150, 40);

        graphsBox = new StackPane();
     
        graphsBox.setPrefSize(500, 500);
        graphsBox.setMaxSize(500, 500);

        PieChartBox = new StackPane();
     
        PieChartBox.setPrefSize(500, 500);
        PieChartBox.setMaxSize(500, 500);

        LineChartHBox.getChildren().addAll(previousButton1, graphsBox, nextButton1);
        PieChartHBox.getChildren().addAll(PieChartBox);

        generalBox.getChildren().addAll(LineChartHBox, PieChartHBox);
        generalBox.setAlignment(Pos.CENTER);

        pane.setCenter(generalBox);
        pane.setBottom(geriDon);
        getData();
        int gun_sayisi = 0;
        if (dataList.size() < 7) {
            gun_sayisi = dataList.size();
        }
        drawGraphs(gun_sayisi);
        drawPieChart();

    }

    public BorderPane getPane() {
        return pane;
    }

    public void getData() {
        dataList = _DailyFoodValueDal.GetAll(
                "SELECT id ,user_id , calorie , fat , carb , prot , date FROM daily_food_values WHERE user_id = ?",
                user_id);
    }

    public void drawGraphs(int gunSayisi) {
        try (Connection con = Database.connect()) {

            String sorgu = "SELECT date, calorie FROM daily_food_values " +
                    "WHERE user_id = ? AND date >= (SELECT MAX(date) FROM daily_food_values WHERE user_id = ?) - INTERVAL "
                    + gunSayisi + " DAY " +
                    "ORDER BY date";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setInt(1, user_id);
            ps.setInt(2, user_id);

            ResultSet rs = ps.executeQuery();

            CategoryAxis xEkseni = new CategoryAxis();
            xEkseni.setLabel("Tarih");
            xEkseni.setTickLabelsVisible(true);

            NumberAxis yEkseni = new NumberAxis();
            yEkseni.setLowerBound(1800);
            yEkseni.setUpperBound(2800);
            yEkseni.setTickUnit(50);
            yEkseni.setLabel("Kalori");
            yEkseni.setAutoRanging(false);

            LineChart<String, Number> lineChart = new LineChart<>(xEkseni, yEkseni);
            lineChart.setTitle("Kalori Alımı ( " + gunSayisi + " Gün)");
            lineChart.setAnimated(false);

            XYChart.Series<String, Number> calSeries = new XYChart.Series<>();
            calSeries.setName("Kalori");

            while (rs.next()) {
                Date tarih = rs.getDate("date");
                double cal = rs.getDouble("calorie");
                calSeries.getData().add(new XYChart.Data<>(tarih.toString(), cal));
            }

            graphsBox.getChildren().clear();
            lineChart.getData().add(calSeries);
            graphsBox.getChildren().add(lineChart);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void drawPieChart() {
        try (Connection con = Database.connect()) {

            String sorgu = "SELECT AVG(carb) AS ort_carb, AVG(fat) AS ort_fat, AVG(prot) AS ort_prot " +
                    "FROM daily_food_values " +
                    "WHERE user_id = ? AND date >= (SELECT MAX(date) FROM daily_food_values WHERE user_id = ?) - INTERVAL 30 DAY";

            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setInt(1, user_id);
            ps.setInt(2, user_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double ortCarb = rs.getDouble("ort_carb");
                double ortFat = rs.getDouble("ort_fat");
                double ortProt = rs.getDouble("ort_prot");

                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Carb", ortCarb),
                        new PieChart.Data("Fat", ortFat),
                        new PieChart.Data("Prot", ortProt));

                PieChart pieChart = new PieChart(pieChartData);
                pieChart.setTitle("Makro Dağılımı (Aylık Ortalama)");
                pieChart.setLegendVisible(true);
                pieChart.setLabelsVisible(true);
                pieChart.setClockwise(true);
                pieChart.setStartAngle(90);

                PieChartBox.getChildren().clear();
                PieChartBox.getChildren().add(pieChart);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
