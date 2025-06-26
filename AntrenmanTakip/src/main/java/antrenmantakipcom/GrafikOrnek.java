package antrenmantakipcom;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GrafikOrnek extends Application {

    @Override
    public void start(Stage primaryStage) {
        // X Ekseni → Tarih (Kategori bazlı)
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Tarih");

        // Y Ekseni → Değer (kg veya tekrar)
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Kg / Tekrar");

        // Grafik oluştur
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Bench Press İlerlemesi");

        // Ağırlık Serisi (Mavi çizgi)
        XYChart.Series<String, Number> agirlikSerisi = new XYChart.Series<>();
        agirlikSerisi.setName("Ağırlık (kg)");

        agirlikSerisi.getData().add(new XYChart.Data<>("2025-06-01", 60));
        agirlikSerisi.getData().add(new XYChart.Data<>("2025-06-10", 65));
        agirlikSerisi.getData().add(new XYChart.Data<>("2025-06-20", 70));
        agirlikSerisi.getData().add(new XYChart.Data<>("2025-06-25", 72));

        // Tekrar Serisi (Kırmızı çizgi)
        XYChart.Series<String, Number> tekrarSerisi = new XYChart.Series<>();
        tekrarSerisi.setName("Tekrar Sayısı");

        tekrarSerisi.getData().add(new XYChart.Data<>("2025-06-01", 12));
        tekrarSerisi.getData().add(new XYChart.Data<>("2025-06-10", 10));
        tekrarSerisi.getData().add(new XYChart.Data<>("2025-06-20", 8));
        tekrarSerisi.getData().add(new XYChart.Data<>("2025-06-25", 6));

        // Serileri grafiğe ekle
        lineChart.getData().addAll(agirlikSerisi, tekrarSerisi);

        // Y Eksenine yanına yatay "Bench Press" etiketi
        Label yAxisLabel = new Label("Bench Press");
        yAxisLabel.setRotate(-90); // Yatay yazı
        StackPane yAxisLabelContainer = new StackPane(yAxisLabel);
        yAxisLabelContainer.setPrefWidth(50);

        BorderPane root = new BorderPane();
        root.setCenter(lineChart);
        root.setLeft(yAxisLabelContainer);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Antrenman Grafiği");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
