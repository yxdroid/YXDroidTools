package org.yxdroid.droidtools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import org.yxdroid.droidtools.controller.MainStateController;

import java.io.File;
import java.util.List;

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

    private MainStateController mainStateController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.app = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/MainState.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.setTitle("YXDroidTools");
        primaryStage.setScene(new Scene(root, 560, 296));
        primaryStage.show();

        mainStateController = fxmlLoader.getController();
        setDragEvent(root);
    }

    private void setDragEvent(Parent root) {
        root.setOnDragOver(event -> {
            if (event.getGestureSource() != root) {
                Dragboard dragboard = event.getDragboard();
                if (dragboard.hasFiles()) {
                    List<File> files = dragboard.getFiles();
                    File file = files.get(0);
                    String fileName = file.getName();
                    if (fileName.endsWith(".apk") || fileName.endsWith(".so")) {
                        event.acceptTransferModes(TransferMode.ANY);
                    }
                }
            }
        });

        root.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                List<File> files = dragboard.getFiles();
                File file = files.get(0);

                String fileName = file.getName();
                if (fileName.endsWith(".so")) {
                    mainStateController.jumpStage("native_crash.fxml", "Native Crash", file.getAbsolutePath());
                } else if (fileName.endsWith(".apk")) {
                    mainStateController.jumpStage("resign.fxml", "重签名", file.getAbsolutePath());
                }
                System.out.println(file.getAbsolutePath());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
