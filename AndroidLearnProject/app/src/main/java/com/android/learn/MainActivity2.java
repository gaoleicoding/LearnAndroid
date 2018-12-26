package com.android.learn;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.learn.base.activity.BasePermisssionActivity;
import com.android.learn.base.utils.PermissionUtil;
import com.android.speechdemo.xf.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity2 extends BasePermisssionActivity implements View.OnClickListener{

    private Button btn_speech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();

        initEvent();//事件要放到初始化对象后边，不然报空指针异常
    }

    private void initEvent() {
        btn_speech.setOnClickListener(this);
    }

    private void initView() {
        btn_speech = (Button) findViewById(R.id.btn_speech);
    }

    int ret = 0; // 函数调用返回值
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_speech:
                requestRecordAudioPermission();

                break;
            default:
        }
    }

    public void requestRecordAudioPermission() {
        requestPermission(this, new PermissionUtil.RequestPermissionCallBack() {
            @Override
            public void granted() {
                String dictationResultStr = "[";
                SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(MainActivity2.this, null);
                mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
                mIat.setParameter(SpeechConstant.NET_TIMEOUT, "10000");
                mIat.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "10000");
                RecognizerDialog iatDialog = new RecognizerDialog(MainActivity2.this, null);
                iatDialog.setListener(new RecognizerDialogListener() {
                    @Override
                    public void onResult(RecognizerResult recognizerResult, boolean b) {
                        Log.d("TAG","recognizerResult="+ recognizerResult.getResultString());
//                        if (!b) {
//                            dictationResultStr += recognizerResult.getResultString() + ",";
//                        } else {
//                            dictationResultStr += recognizerResult.getResultString() + "]";
//                        }
//                        if (b) {
                            // 解析Json列表字符串
//                            List dictationResultList = GsonUtils.getInstance()
//                                    .fromJson(dictationResultStr,new TypeToken<List<String>>(){}.getType());
//                            String finalResult = "";
//                            for (int i = 0; i < dictationResultList.size() - 1; i++) {
//                                finalResult += dictationResultList.get(i)
//                                        .toString();
                            }
//                            et_search.setText(finalResult);
//
//                            //获取焦点
//                            et_search.requestFocus();
//
//                            //将光标定位到文字最后，以便修改
//                            et_search.setSelection(finalResult.length());
//                            Log.d("TAG", finalResult);
//                        }
//                    }
                    @Override
                    public void onError(SpeechError speechError) {

                    }
                });
                iatDialog.show();
            }


            @Override
            public void denied() {
            }
        }, new String[]{Manifest.permission.RECORD_AUDIO});
    }

}

