package org.yxdroid.droidtools.controller;

import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import org.yxdroid.droidtools.MainApplication;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * User: yxfang
 * Date: 2017-06-10
 * Time: 17:31
 * ------------- Description -------------
 * <p>
 * ---------------------------------------
 */
public abstract class BaseController implements Initializable, ControllerInit {

    protected Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void bindStage(Stage stage) {
        this.stage = stage;
    }

    protected File openFileChooser(String name, String suffix) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(name,suffix);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(this.stage);
        return file;
    }

    protected File openDirChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(MainApplication.app);
    }

    protected boolean isEmpty(String text) {
        if(text == null || text.length() == 0) {
            return true;
        }
        return false;
    }


    protected void showTip(String title, String masthead, String message) {
        Dialogs.create()
                .owner(stage)
                .title(title)
                .masthead(masthead)
                .message(message)
                .showInformation();
    }

    // http://code.makery.ch/blog/javafx-8-dialogs/ 对话框使用方式
}
