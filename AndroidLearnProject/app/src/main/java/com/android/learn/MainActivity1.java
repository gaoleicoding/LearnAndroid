package com.android.learn;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.learn.base.activity.BasePermisssionActivity;
import com.android.learn.base.utils.PermissionUtil;
import com.android.speechdemo.xf.JsonParser;
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

public class MainActivity1 extends BasePermisssionActivity implements View.OnClickListener{

    private static String TAG = "com.android.learn.MainActivity1";
    private Button btn_speech;
    private EditText tv_show;
    private SpeechRecognizer mIat;
    private RecognizerDialog mIatDialog;
    private Toast mToast;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    // 引擎类型 这里我只考虑了一种引擎类型 :云端的
    private String mCLOUDType = SpeechConstant.TYPE_CLOUD;
    private SharedPreferences mSharedPreferences;
    //是否要进行翻译 默认：false，这里主要是考虑到了你的需求有无可以让用户选择翻译不翻译的情况，
    //如果你知道你的产品在往后都不需要这个需求的时候，你大可不需要 SharedPreferences的方式，直接定死一种情况，翻译或者不翻译

    private boolean mTranslateEnable = false;

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
        tv_show = (EditText) findViewById(R.id.tv_show);
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
    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 封装 Toast 的方法
     * @param str
     */
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎  注意：这里我只设置云端的方式！后面再考虑本地和混合的类型
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mCLOUDType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        this.mTranslateEnable = false;
        if( mTranslateEnable ){
            Log.i( TAG, "translate enable" );
            mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
            mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
            mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
        }

        String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
            }
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        /**
         * 识别成功时回调数据
         */
        public void onResult(RecognizerResult results, boolean isLast) {
            //这里应该是是否需要翻译，翻译的和不翻译的json解析方式不一样，
            // 是否翻译，看用户的设置，如若用户没设置，有默认的方式
            if( mTranslateEnable){
                printTransResult( results );
            }else{
                printResult(results);
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            if(mTranslateEnable && error.getErrorCode() == 14002) {
                showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
                showTip(error.getPlainDescription(true));
            }
        }

    };

    private void printTransResult (RecognizerResult results) {
        String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
        String oris = JsonParser.parseTransResult(results.getResultString(),"src");

        if( TextUtils.isEmpty(trans)||TextUtils.isEmpty(oris) ){
            showTip( "解析结果失败，请确认是否已开通翻译功能。" );
        }else{
            tv_show.setText( "原始语言:\n"+oris+"\n目标语言:\n"+trans );
        }

    }

    /**
     * 成功时显示说话的文字
     * @param results
     */
    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        tv_show.setText(resultBuffer.toString());
        //考虑到TextView只能显示文字 ，后面还要测试文字转语音，所以换EditText控件
        tv_show.setSelection(tv_show.length());

    }
    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            if (mTranslateEnable && error.getErrorCode() == 14002) {
                showTip(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
            } else {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            if (mTranslateEnable) {
                printTransResult(results);
            } else {
                printResult(results);
            }

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    public void requestRecordAudioPermission() {
        requestPermission(this, new PermissionUtil.RequestPermissionCallBack() {
            @Override
            public void granted() {

                // 使用SpeechRecognizer对象，可根据回调消息自定义界面；这种方式主要是考虑到了，没有听写Dialog的时候，进行的听写监听
                mIat = SpeechRecognizer.createRecognizer(MainActivity1.this, mInitListener);
                Log.i(TAG, "onCreate: mIat == null ?"+mIat);
                //SpeechRecognizer对象 null 的原因：一、 so 文件放错了位置 二、so文件与自己的SDK不匹配
                // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
                // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置显示RecognizerDialog需要的布局文件和图片资源
                mIatDialog = new RecognizerDialog(MainActivity1.this, mInitListener);
//                mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
                mToast = Toast.makeText(MainActivity1.this, "", Toast.LENGTH_SHORT);
                // 移动数据分析，收集开始听写事件
                FlowerCollector.onEvent(MainActivity1.this, "iat_recognize");

                tv_show.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
//                setParam();

                boolean isShowDialog = true;
                if (isShowDialog) {
                    // 显示听写对话框
                    mIatDialog.setListener(mRecognizerDialogListener);
                    mIatDialog.show();
//                    showTip(getString(R.string.text_begin));
                } else {
                    // 不显示听写对话框
                    ret = mIat.startListening(mRecognizerListener);
                    if (ret != ErrorCode.SUCCESS) {
                        showTip("听写失败,错误码：" + ret);
                    } else {
//                        showTip(getString(R.string.text_begin));
                    }
                }
            }

            @Override
            public void denied() {
            }
        }, new String[]{Manifest.permission.RECORD_AUDIO});
    }

}

