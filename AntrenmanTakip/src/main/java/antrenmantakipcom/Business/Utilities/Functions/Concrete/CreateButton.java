package antrenmantakipcom.Business.Utilities.Functions.Concrete;

import antrenmantakipcom.Business.Utilities.Functions.Abstract.IFunction;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CreateButton implements IFunction {

    private CreateButton() {
    }

    public static Button createExitButton() {
        ImageView iconLogout = ImageFunction.LoadImage("ICONS/logout.png");
        Button button = new Button("Exit", iconLogout);
        button.setId("cikis_butonlari");
        return button;
    }

    public static Button createCloseButton() {
        ImageView iconClose = ImageFunction.LoadImage("ICONS/close.png");
        Button button = new Button("Close", iconClose);
        button.setId("close_butonlari");
        return button;
    }

    public static Button createDeleteButton() {
        ImageView iconDelete = ImageFunction.LoadImage("ICONS/sil.png");
        Button button = new Button("Delete", iconDelete);
        button.setId("delete_butonlari");
        return button;
    }

    public static Button createSaveButton() {
        ImageView iconSave = ImageFunction.LoadImage("ICONS/ekle.png");
        Button button = new Button("Save", iconSave);
        button.setId("save_butonlari");
        return button;
    }

}
