package org.yxdroid.droidtools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * User: yxfang
 * Date: 2017-06-10
 * Time: 16:51
 * ------------- Description -------------
 * <p>
 * ---------------------------------------
 */
public class MainApplication extends Application {

    public static Stage app;

    public static String initDir = "";

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.app = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/MainState.fxml"));
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.setTitle("YXDroidTools");
        primaryStage.setScene(new Scene(root, 560, 296));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
