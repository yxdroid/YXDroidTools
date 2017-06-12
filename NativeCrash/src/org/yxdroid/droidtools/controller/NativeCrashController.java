package org.yxdroid.droidtools.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sun.awt.OSInfo;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class NativeCrashController extends BaseController {

    @FXML
    private TextField edtSoPath;

    @FXML
    private TextField edtAddr;

    private String soPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    public void onOpenSoPath(ActionEvent event) {
        File file = openFileChooser("SO files (*.so)", "*.so");
        if (file != null) {
            soPath = file.getAbsolutePath();
            edtSoPath.setText(file.getName());
            edtSoPath.selectEnd();
            System.out.println(file);
        }
    }

    @FXML
    public void onCrash(ActionEvent event) throws URISyntaxException {

        if (isEmpty(soPath)) {
            showTip("提示", null, "请选择SO路径");
            return;
        }

        String addr = edtAddr.getText().toString().trim();
        if (isEmpty(addr)) {
            showTip("提示", null, "汇编地址为空");
            return;
        }

        String cmd = "";
        if (OSInfo.getOSType() == OSInfo.OSType.MACOSX) {
            cmd = loadFile("aarch64-linux-android-addr2line");
        } else if (OSInfo.getOSType() == OSInfo.OSType.WINDOWS) {
            cmd = loadFile("aarch64-linux-android-addr2line.exe");
        }

        cmd = cmd + " -e " + soPath + " " + addr;
        String result = null;
        try {
            result = runCmd(cmd);
            if (result.contains("??:")) {
                showTip("Crash 结果", "异常位置", "未在" + edtSoPath.getText().toString().trim() + "中定位到" + addr + "处错误");
            } else {
                showTip("Crash 结果", "发现异常位置", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showTip("Crash 结果", e.getMessage(), result);
        }
    }


    public void onCancel(ActionEvent event) {
        stage.close();
    }
}
