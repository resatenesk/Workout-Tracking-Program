package antrenmantakipcom.Business.FoodMeal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import antrenmantakipcom.DataAccess.Concrete.Database;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ObservableList<Data> dataList = FXCollections.observableArrayList();
    private int user_id = 0;
    private Button geriDon;

    public class Data {
        private int user_id;
        private int totalCal;
        private int totalFat;
        private int totalCarb;
        private int totalProt;
        private Date date;

        public Data(int user_id, int totalCal, int totalFat, int totalCarb, int totalProt, Date date) {
            this.date = date;
            this.totalCal = totalCal;
            this.totalFat = totalFat;
            this.totalCarb = totalCarb;
            this.totalProt = totalProt;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getTotalCal() {
            return totalCal;
        }

        public int getTotalFat() {
            return totalFat;
        }

        public int getTotalCarb() {
            return totalCarb;
        }

        public int getTotalProt() {
            return totalProt;
        }

        public Date getDate() {
            return date;
        }

        @Override
        public String toString() {
            return "TotalCal: " + totalCal + " TotalFat: " + totalFat + " TotalCarb: " + totalCarb + " TotalProt: "
                    + totalProt + " Tarih: " + date;
        }

    }

    public ShowFoodGraphs(String username) {

        this.username = username;
        pane = new BorderPane();
        //pane.setStyle("-fx-border-size:5px;-fx-border-color:green");

        Image imageC = new Image(MainScreen.class.getResourceAsStream("/ICONS/logout.png"));
        ImageView imageViewC = new ImageView(imageC);
        imageViewC.setFitWidth(20);
        imageViewC.setFitHeight(20);
        geriDon = new Button("Exit", imageViewC);
        geriDon.setId("cikis_butonlari");
        geriDon.setOnAction(e -> {
            try {
                Main.setRoot(MainScreen.getRoot());
            } catch (Exception ex) {
            }
        });

        generalBox = new VBox(10);
        //generalBox.setStyle("-fx-border-size:5px;-fx-border-color:green");

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
        // previousButton2 = new Button("Previous");
        // previousButton2.setPrefSize(120, 40);
        // nextButton2 = new Button("Next");
        // nextButton2.setPrefSize(120, 40);

        graphsBox = new StackPane();
        //graphsBox.setStyle("-fx-border-size:5px;-fx-border-color:green");
        graphsBox.setPrefSize(500, 500);
        graphsBox.setMaxSize(500, 500);

        PieChartBox = new StackPane();
        //PieChartBox.setStyle("-fx-border-size:5px;-fx-border-color:green");
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
        try (Connection con = Database.connect()) {
            String sorguID = "SELECT user_id FROM users WHERE username = ?";
            PreparedStatement psID = con.prepareStatement(sorguID);
            psID.setString(1, username);
            ResultSet rsID = psID.executeQuery();
            while (rsID.next()) {
                user_id = rsID.getInt("user_id");
            }

            String sorgu = "SELECT calorie, fat, carb, prot, date FROM daily_food_values WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cal = (int) (rs.getFloat("calorie"));
                int fat = (int) (rs.getFloat("fat"));
                int carb = (int) (rs.getFloat("carb"));
                int prot = (int) (rs.getFloat("prot"));
                Date date = rs.getDate("date");

                Data data = new Data(user_id, cal, fat, carb, prot, date);
                dataList.add(data);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
