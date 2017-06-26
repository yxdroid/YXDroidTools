package org.yxdroid.droidtools.controller;/**
 * User: yxfang
 * Date: 2017-06-10
 * Time: 16:54
 * ------------- Description -------------
 * <p>
 * ---------------------------------------
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
import org.yxdroid.droidtools.os.OSinfo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.controlsfx.dialog.Dialog.ACTION_CANCEL;
import static org.controlsfx.dialog.Dialog.ACTION_YES;

public class ReSignController extends BaseController {

    @FXML
    private TextField edtInput;

    @FXML
    private TextField edtOutput;

    private String apkPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        edtInput.setOnDragOver(event -> {
            if (event.getGestureSource() != edtInput) {
                Dragboard dragboard = event.getDragboard();
                if (dragboard.hasFiles()) {
                    List<File> files = dragboard.getFiles();
                    File file = files.get(0);
                    if (file.getName().endsWith(".apk")) {
                        event.acceptTransferModes(TransferMode.ANY);
                    }
                }
            }
        });
        edtInput.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                List<File> files = dragboard.getFiles();
                File file = files.get(0);
                edtInput.setText(file.getName());
                setOutputValue(file);
            }
        });
    }

    public void onInput(ActionEvent event) {
        File file = openFileChooser("APK files (*.apk)", "*.apk");
        if (file != null) {

            edtInput.setText(file.getName());

            setOutputValue(file);
        }
    }

    void setOutputValue(File file) {
        apkPath = file.getAbsolutePath();
        edtOutput.setText(file.getParentFile().getAbsolutePath()
                + File.separator + reName());
        edtInput.selectEnd();
        edtOutput.selectEnd();
    }


    private String reName() {
        String apkName = edtInput.getText().toString().trim();
        return apkName.replace(".apk", "") + "_sign.apk";
    }

    public void onOutput(ActionEvent event) {
        File dir = openDirChooser();
        if (dir != null) {
            edtOutput.setText(dir.getAbsolutePath()
                    + File.separator + reName());
            edtOutput.selectEnd();
        }
    }

    public void onSign(ActionEvent event) throws IOException, URISyntaxException {
        if (isEmpty(apkPath)) {
            showTip("提示", null, "请选择签名APK文件");
            return;
        }

        String outPath = edtOutput.getText().toString().trim();
        if (isEmpty(outPath)) {
            showTip("提示", null, "请选择输出路径");
            return;
        }

        if (!outPath.endsWith(".apk")) {
            showTip("提示", null, "输出文件不是APK文件");
            edtOutput.selectEnd();
            return;
        }

        File outPathFile = new File(outPath);
        if (outPathFile.exists()) {
            outPathFile.delete();
        }

        String signJar = loadFile("signapk.jar");
        String pem = loadFile("platform.x509.pem");
        String pk8 = loadFile("platform.pk8");
        String runResult = null;
        try {
            runResult = runCmd(String.format("java -jar %s %s %s %s %s",
                    signJar, pem, pk8, apkPath, outPath));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("提示");
            alert.setHeaderText("签名成功!");
            alert.setContentText("是否打开已签名APK文件目录?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (OSinfo.isMacOS() || OSinfo.isMacOSX()) {
                    Runtime.getRuntime().exec("open " + outPathFile.getParentFile().getAbsolutePath());
                } else if (OSinfo.isWindows()) {
                    Runtime.getRuntime().exec("explorer.exe " + outPathFile.getParentFile().getAbsolutePath());
                }
            } else {
            }
        } catch (Exception e) {
            // 兼容jdk 8u40 和 8u20 对话框使用
            if (e instanceof NullPointerException) {

                Action response = Dialogs.create()
                        .owner(stage)
                        .title("提示")
                        .masthead("签名成功!")
                        .message("是否打开已签名APK文件目录?")
                        .actions(ACTION_CANCEL, ACTION_YES)
                        .showConfirm();
                if (response == ACTION_YES) {
                    if (OSinfo.isMacOS() || OSinfo.isMacOSX()) {
                        Runtime.getRuntime().exec("open " + outPathFile.getParentFile().getAbsolutePath());
                    } else if (OSinfo.isWindows()) {
                        Runtime.getRuntime().exec("explorer.exe " + outPathFile.getParentFile().getAbsolutePath());
                    }
                } else {

                }
            } else {
                if (isEmpty(runResult)) {
                    showTip("提示", "签名失败!", e.getMessage());
                    return;
                }
            }

        }

    }

    public void onCancel(ActionEvent event) {
        stage.close();
    }
}
