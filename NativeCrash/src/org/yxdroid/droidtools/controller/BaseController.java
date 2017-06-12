package org.yxdroid.droidtools.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.yxdroid.droidtools.MainApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
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
        if (!isEmpty(MainApplication.initDir)) {
            fileChooser.setInitialDirectory(new File(MainApplication.initDir));
        }
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(name, suffix);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(this.stage);
        if (file != null) {
            MainApplication.initDir = file.getParentFile().getAbsolutePath();
        }
        return file;
    }

    protected File openDirChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(MainApplication.app);
    }

    protected boolean isEmpty(String text) {
        if (text == null || text.length() == 0) {
            return true;
        }
        return false;
    }


    protected void showTip(String title, String masthead, String message) {
        /*Dialogs.create()
                .owner(stage)
                .title(title)
                .masthead(masthead)
                .message(message)
                .showInformation();*/
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(masthead);
        alert.setContentText(message);

        alert.showAndWait();
    }

    // http://code.makery.ch/blog/javafx-8-dialogs/ 对话框使用方式

    protected String loadFile(String fileName) {
        File file = null;
        try {
            file = new File(getClass().getClassLoader().getResource("cmd/" + fileName).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //File cmdFile = new File(getClass().getClassLoader().getResource(fileName).toURI());
        return file.getAbsolutePath();
    }

    protected String runCmd(String cmd) throws Exception {
        BufferedReader br = null;
        try {

            System.out.println(cmd);
            Process p = Runtime.getRuntime().exec(cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
