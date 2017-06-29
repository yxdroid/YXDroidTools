package org.yxdroid.droidtools.os;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 * User: yxfang
 * Date: 2017-06-29
 * Time: 14:25
 * ------------- Description -------------
 * <p>
 * ---------------------------------------
 */
public class DialogUtil {

    public static void showTip(Stage stage, String title, String masthead, String message) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(masthead);
            alert.setContentText(message);

            alert.showAndWait();
        } catch (Exception e) {
            // 兼容jdk 8u40 和 8u20 对话框使用
            Dialogs.create()
                    .owner(stage)
                    .title(title)
                    .masthead(masthead)
                    .message(message)
                    .showInformation();
        }
    }
}
