package antrenmantakipcom.Business.Authorization;

import java.util.List;

import antrenmantakipcom.Business.Utilities.Functions.Concrete.AlertFunction;
import antrenmantakipcom.Business.Utilities.Functions.Concrete.CreateButton;
import antrenmantakipcom.Business.Utilities.Functions.Concrete.ImageFunction;
import antrenmantakipcom.DataAccess.Concrete.Dal.UserDal;
import antrenmantakipcom.Entities.Concrete.User;
import antrenmantakipcom.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AddNewUserScreen {
    StackPane pane;
    BorderPane root = new BorderPane();
    Label nameLabel;
    Label passwordLabel;
    static TextField nameField;
    static PasswordField passwordField;
    Button GeriDonButton;
    Button KullaniciEkle;
    VBox layout;
    static String username;
    static String password;
    ImageView infoIcon;
    Image infoImage;

    public StackPane getRoot() {
        pane = new StackPane();
        pane.setId("rootGenel");
        pane.getStylesheets().add(UserLoginFrame.class.getResource("/static/style.css").toExternalForm());
        bilesenler();

        root.setId("kullaniciEklemeRoot");
        bilesenler();
        try {
            ayarlamalar();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return pane;
    }

    public void ayarlamalar() throws Exception {
        HBox genellayout = new HBox();
        genellayout.setAlignment(Pos.CENTER);
        genellayout.setPadding(new Insets(400, 0, 0, 0));

        VBox layout = new VBox(15);
        // layout.setPadding(new Insets(0,0,100,0));
        layout.setAlignment(Pos.CENTER);

        VBox layout2 = new VBox(15);
        layout2.setAlignment(Pos.CENTER);

        VBox Column1 = new VBox(10);
        Column1.setAlignment(Pos.CENTER);
        Column1.getChildren().addAll(nameLabel, passwordLabel);

        HBox passwordHBox = new HBox(10);
        passwordHBox.setAlignment(Pos.CENTER);
        passwordHBox.getChildren().addAll(passwordField, infoIcon);

        VBox Column2 = new VBox(10);
        Column2.setAlignment(Pos.CENTER);
        Column2.getChildren().addAll(nameField);

        HBox buttonsRow = new HBox(10);
        buttonsRow.setAlignment(Pos.CENTER);
        buttonsRow.setPadding(new Insets(0, 0, 400, 0));
        buttonsRow.getChildren().addAll(KullaniciEkle, GeriDonButton);

        layout.getChildren().addAll(Column1);
        layout2.getChildren().addAll(Column2, passwordHBox);

        genellayout.getChildren().addAll(layout, layout2, buttonsRow);
        root.setTop(genellayout);
        root.setCenter(buttonsRow);
        pane.getChildren().add(root);
    }

    public void bilesenler() {
        root = new BorderPane();
        nameLabel = new Label("Enter your username: ");
        passwordLabel = new Label("Enter your password: ");
        nameLabel.setMinWidth(120);
        passwordLabel.setMinWidth(120);

        nameField = new TextField();
        nameField.setPromptText("Username");
        nameField.setMinWidth(120);
        nameField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMinWidth(120);

        infoIcon = ImageFunction.LoadImage("/ICONS/info.png");
        infoIcon.setStyle(
                "-fx-text-fill: #007acc; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 16px; " +
                        "-fx-cursor: hand;");
        infoIcon.setPreserveRatio(true);
        infoIcon.setStyle("-fx-cursor: hand;");
        Tooltip passwordTooltip = new Tooltip(
                "Parola Kuralları:\n" +
                        "- En az 8 karakter\n" +
                        "- En az 1 büyük harf\n" +
                        "- En az 1 rakam\n" +
                        "- En az 1 özel karakter (@, #, !, vs.)");
        Tooltip.install(infoIcon, passwordTooltip);

        GeriDonButton = CreateButton.createExitButton();
        GeriDonButton.setOnAction(e -> {
            try {
                Main.setRoot(UserLoginFrame.getRoot());
            } catch (Exception e1) {

                e1.printStackTrace();
            }

        });
        GeriDonButton.setMinWidth(120);

        KullaniciEkle = CreateButton.createSaveButton();
        KullaniciEkle.setOnAction(e -> {
            try {
                kullaniciEkle();

            } catch (Exception e1) {

                e1.printStackTrace();
            }

        });
        KullaniciEkle.setMinWidth(120);

        nameLabel.setStyle("-fx-font-style:italic;-fx-font-size:20px;-fx-text-fill:white");
        passwordLabel.setStyle("-fx-font-style:italic;-fx-font-size:20px;-fx-text-fill:white");

    }

    public void kullaniciEkle() {
        username = nameField.getText();
        password = passwordField.getText();

        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#!$%^&*])[A-Za-z\\d@#!$%^&*]{8,}$";

        if (username.equals("") || password.equals("")) {
            AlertFunction.MissingDataAlert();

        } else {
            UserDal userRepo = new UserDal(User.class);
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            List<User> list = userRepo.GetAll("SELECT * FROM users");
            boolean exists = list.stream().anyMatch(u -> u.getUsername().equals(username));
            if (exists) {
                AlertFunction.UserAlreadyExist();
                return;
            }
            if (!password.matches(regex)) {
                AlertFunction.WrongPasswordAlert();
                return;
            }
            userRepo.Add(user);
            Main.setRoot(UserLoginFrame.getRoot());
        }

    }

}
