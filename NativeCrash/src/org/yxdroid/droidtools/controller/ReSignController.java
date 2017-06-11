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
import javafx.scene.control.TextField;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
import org.yxdroid.droidtools.os.OSinfo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    }

    public void onInput(ActionEvent event) {
        File file = openFileChooser("APK files (*.apk)", "*.apk");
        if (file != null) {

            edtInput.setText(file.getName());

            apkPath = file.getAbsolutePath();
            edtOutput.setText(file.getParentFile().getAbsolutePath()
                    + File.separator + reName());
            edtInput.selectEnd();
            edtOutput.selectEnd();
        }
    }

    private String reName() {
        String apkName = edtInput.getText().toString().trim();
        return apkName.replace(".apk","") + "_sign.apk";
    }

    public void onOutput(ActionEvent event) {
        File dir = openDirChooser();
        if (dir != null) {
            edtOutput.setText(dir.getAbsolutePath()
                    + File.separator + reName());
            edtOutput.selectEnd();
        }
    }

    public void onSign(ActionEvent event) throws IOException {
        if (isEmpty(apkPath)) {
            showTip("提示", null, "请选择签名APK文件");
            return;
        }

        String outPath = edtOutput.getText().toString().trim();
        if (isEmpty(outPath)) {
            showTip("提示", null, "请选择输出路径");
            return;
        }

        if(!outPath.endsWith(".apk")) {
            showTip("提示", null,"输出文件不是APK文件");
            edtOutput.selectEnd();
            return;
        }

        File outDir = new File(outPath).getParentFile();

        Action response = Dialogs.create()
                .owner(stage)
                .title("提示")
                .masthead("签名成功!")
                .message("是否打开已签名APK文件目录?")
                .actions(ACTION_CANCEL, ACTION_YES)
                .showConfirm();

        if (response == ACTION_YES) {
            if (OSinfo.isMacOS() || OSinfo.isMacOSX()) {
                Runtime.getRuntime().exec("open " + outDir.getAbsolutePath());
            } else if (OSinfo.isWindows()) {
                Runtime.getRuntime().exec("explorer.exe " + outDir.getAbsolutePath());
            }
        } else {
        }
    }

    public void onCancel(ActionEvent event) {
        stage.close();
    }
}
