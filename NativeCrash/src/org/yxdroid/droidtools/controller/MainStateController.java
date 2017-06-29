package org.yxdroid.droidtools.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.yxdroid.droidtools.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * User: yxfang
 * Date: 2017-06-10
 * Time: 17:12
 * ------------- Description -------------
 * <p>
 * ---------------------------------------
 */
public class MainStateController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onNativeCrash(ActionEvent event) throws IOException {
        jumpStage("native_crash.fxml", "Native Crash", null);
    }

    @FXML
    public void onReSign(ActionEvent event) throws IOException {
        jumpStage("resign.fxml", "重签名", null);
    }

    public void jumpStage(String stageFxml, String title, String initValue) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/" + stageFxml));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.setTitle(title);
            stage.setScene(new Scene(root, 560, 296));

            BaseController controller = fxmlLoader.getController();
            controller.bindStage(stage);
            controller.initValue(initValue);

            stage.setOnCloseRequest(event -> MainApplication.app.show());
            stage.setOnHidden(event -> MainApplication.app.show());

            MainApplication.app.hide();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
