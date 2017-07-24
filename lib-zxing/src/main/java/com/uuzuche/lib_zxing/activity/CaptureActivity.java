package com.uuzuche.lib_zxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.decoding.CaptureActivityHandler;

/**
 * Initial the camera
 * <p>
 * 默认的二维码扫描Activity
 */
public class CaptureActivity extends AppCompatActivity implements View.OnClickListener{

    CaptureFragment captureFragment;
    private ImageView iv_finish;
    private ImageView iv_help;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
        initViews();
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        //  CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    private void initViews() {
        iv_finish= (ImageView) findViewById(R.id.finish);
        iv_help= (ImageView) findViewById(R.id.help);

        iv_finish.setOnClickListener(this);
        iv_help.setOnClickListener(this);
        flag=getIntent().getIntExtra("from",0);
        if(flag==1){
            iv_help.setVisibility(View.VISIBLE);
        }else if(flag==2){
            iv_help.setVisibility(View.GONE);
        }
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

            if(flag==1){
                if (!handleResult(result)) {
                    return;
                }
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                bundle.putString(CodeUtils.RESULT_STRING, codeToatal);
                resultIntent.putExtras(bundle);
                CaptureActivity.this.setResult(RESULT_OK, resultIntent);
                CaptureActivity.this.finish();
            }else if(flag==2){
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                bundle.putString(CodeUtils.RESULT_STRING, result);
                resultIntent.putExtras(bundle);
                CaptureActivity.this.setResult(RESULT_OK, resultIntent);
                CaptureActivity.this.finish();
            }


        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }
    };

    public String ticketCode = "";
    public String encashCode = "";
    private String codeToatal = "";
    private String temp = "";
    private CaptureActivityHandler mCaptureActivityHandler;

    private boolean handleResult(String barcode) {
        // 票号
        if (temp.equals("")) {
            temp = barcode;
        } else if (temp.equals(barcode)) {
            captureFragment.setAnalyzeCallback(analyzeCallback);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
            return false;
        }

        if (barcode.length() == 18 || barcode.length() == 20) {
            ticketCode = barcode;
        }
        // 兑奖码
        else if (barcode.length() == 11) {
            encashCode = barcode;
        }
        // 二合一
        else if (barcode.length() == 29 || barcode.length() == 31) {
            codeToatal = barcode;
        } else {
            Toast.makeText(this, R.string.notfound, Toast.LENGTH_LONG).show();
        }


        if (!codeToatal.equals("")) {
            return true;
        } else if (ticketCode.equals("")) {
            Toast.makeText(this, "继续扫描票号二维码", Toast.LENGTH_LONG).show();
            captureFragment.setAnalyzeCallback(analyzeCallback);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
            return false;
        } else if (encashCode.equals("")) {
            Toast.makeText(this, "继续扫描兑奖二维码", Toast.LENGTH_LONG).show();
            captureFragment.setAnalyzeCallback(analyzeCallback);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
            return false;
        } else if (!encashCode.equals("") && !ticketCode.equals("")) {
            codeToatal = ticketCode + encashCode;
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.finish){
            finish();
        }else if(v.getId()==R.id.help){
            startActivity(new Intent(this,HelpActivity.class));
        }
    }
}