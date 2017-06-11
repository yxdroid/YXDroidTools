package org.yxdroid.droidtools.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class NativeCrashController extends BaseController{

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

        if(isEmpty(soPath)) {
            showTip("提示", null, "请选择SO路径");
            return;
        }

        String addr = edtAddr.getText().toString().trim();
        if(isEmpty(addr)) {
            showTip("提示",null, "汇编地址为空");
            return;
        }
        File cmdFile = new File(getClass()
                .getClassLoader().getResource("cmd/aarch64-linux-android-addr2line")
                .toURI());

        String cmd = cmdFile.getAbsolutePath() + " -e " + soPath + " " + addr;
        String result = runCmd(cmd);

        if(result.contains("??:")) {
            showTip("Crash 结果","异常位置","未在" + edtSoPath.getText().toString().trim() + "中定位到" + addr + "处错误");
        }
        else{
            showTip("Crash 结果","发现异常位置",result);
        }
    }

    private String runCmd(String cmd) {
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
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    public void onCancel(ActionEvent event) {
        stage.close();
    }
}
