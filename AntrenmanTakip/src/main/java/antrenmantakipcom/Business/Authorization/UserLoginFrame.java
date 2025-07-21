package antrenmantakipcom.Business.Authorization;

import java.util.ArrayList;

import antrenmantakipcom.Business.Utilities.Functions.Concrete.AlertFunction;
import antrenmantakipcom.Business.Utilities.Functions.Concrete.ImageFunction;
import antrenmantakipcom.DataAccess.Concrete.Dal.UserDal;
import antrenmantakipcom.Entities.Concrete.User;
import antrenmantakipcom.Main;
import antrenmantakipcom.MainScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UserLoginFrame {
    static StackPane rootGenel;
    static BorderPane root;
    static Label nameLabel;
    static Label passwordLabel;
    static TextField nameField;
    static PasswordField passwordField;
    static Button LoginButton;
    static Button RegisterButton;
    static String username;
    static String password;
    static ArrayList<String> Kullanicilar = new ArrayList<>();
    static ImageView infoIcon;
    static ImageView imageLogin;
    static ImageView addNewUserImageView;

    public static StackPane getRoot() {

        rootGenel = new StackPane();
        rootGenel.setId("rootGenel");
        rootGenel.getStylesheets().add(UserLoginFrame.class.getResource("/static/style.css").toExternalForm());
        bilesenler();
        try {
            ayarlamalar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootGenel;
    }

    public static void ayarlamalar() throws Exception {

        HBox genellayout = new HBox();
        genellayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(350, 0, 0, 0));

        VBox layout2 = new VBox(15);
        layout2.setAlignment(Pos.CENTER);
        layout2.setPadding(new Insets(350, 0, 0, 0));

        VBox Column1 = new VBox(10);
        Column1.setAlignment(Pos.CENTER);
        Column1.getChildren().addAll(nameLabel, passwordLabel);
        // Column1.setStyle("-fx-border-width:2px;-fx-border-color:Red");

        HBox passwordHBox = new HBox(10);
        passwordHBox.setAlignment(Pos.CENTER);
        passwordHBox.getChildren().addAll(passwordField, infoIcon);
        // passwordHBox.setStyle("-fx-border-width:2px;-fx-border-color:Red");

        VBox Column2 = new VBox(10);
        Column2.setAlignment(Pos.CENTER);
        Column2.getChildren().addAll(nameField);
        // Column2.setStyle("-fx-border-width:2px;-fx-border-color:Red");

        HBox buttonsRow = new HBox(10);
        buttonsRow.setAlignment(Pos.CENTER);
        buttonsRow.setPadding(new Insets(0, 0, 400, 0));
        buttonsRow.getChildren().addAll(LoginButton, RegisterButton);
        // buttonsRow.setStyle("-fx-border-width:2px;-fx-border-color:Red");

        layout.getChildren().addAll(Column1);
        layout2.getChildren().addAll(Column2, passwordHBox);

        genellayout.getChildren().addAll(layout, layout2);

        root.setCenter(genellayout);
        root.setBottom(buttonsRow);

        rootGenel.getChildren().add(root);
    }

    public static void bilesenler() {
        root = new BorderPane();
        nameLabel = new Label("Enter your username:");
        passwordLabel = new Label("Enter your password:");
        nameLabel.setMinWidth(120);
        passwordLabel.setMinWidth(120);

        nameField = new TextField();
        nameField.setPromptText("Username");
        nameField.setMinWidth(120);
        nameField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMinWidth(120);

        infoIcon = ImageFunction.LoadTooltip("/ICONS/info.png", "Parolanız Yanlışsa Kontrol Ediniz:\n" +
                        "- En az 8 karakter\n" +
                        "- En az 1 büyük harf\n" +
                        "- En az 1 rakam\n" +
                        "- En az 1 özel karakter (@, #, !, vs.)");

        imageLogin = ImageFunction.LoadImage("/ICONS/ikon1.png");

        LoginButton = new Button("Giriş Yap", imageLogin);
        LoginButton.setOnAction(e -> {
            try {

                kullaniciKontrol();

            } catch (Exception e1) {

            }

        });
        LoginButton.setMinWidth(120);

        addNewUserImageView = ImageFunction.LoadImage("/ICONS/ekle.png");

        RegisterButton = new Button("Yeni Kullanıcı Oluştur", addNewUserImageView);
        RegisterButton.setOnAction(e -> {

            try {
                AddNewUserScreen ekran = new AddNewUserScreen();
                Main.setRoot(ekran.getRoot());
            } catch (Exception ex) {
                System.out.println("yeni sekme açılırken hata oluştu...");
                ex.printStackTrace();
            }
        });
        RegisterButton.setMinWidth(120);

        nameLabel.setStyle("-fx-font-style:italic;-fx-font-size:20px;-fx-text-fill:white");
        // nameField.setStyle("-fx-border-width: 1px;-fx-prompt-text-fill:black;");
        passwordLabel.setStyle("-fx-font-style:italic;-fx-font-size:20px;-fx-text-fill:white");
        // passwordField.setStyle("-fx-border-width: 1px;-fx-prompt-text-fill:black;");
        // LoginButton.setStyle("-fx-border-width: 1px;");
        // RegisterButton.setStyle("-fx-border-width: 1px; color:white");

    }

    public static void kullaniciKontrol() throws Exception {
        username = nameField.getText();
        password = passwordField.getText();

        if (username.equals("") && password.equals("")) {
            AlertFunction.MissingDataAlert();
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            UserDal userDal = new UserDal(User.class);
            int user_id = userDal.selectUserID(user);
            String sorgu = "SELECT * FROM users WHERE username=? AND password=?";

            if (user_id > 0) {
                MainScreen.setUsername(username);
                Main.setRoot(MainScreen.getRoot());

            } else {
                AlertFunction.UserNotFound();
            }

        }
    }

}
