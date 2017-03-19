package com.dev.dongworry.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dev.dongworry.R;
import com.dev.dongworry.customview.CustomToast;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.UnderstanderResult;

import java.io.IOException;

import taobe.tec.jcc.JChineseConvertor;

import static com.dev.dongworry.activities.ListAppsActivity.TAG;
import static com.dev.dongworry.consts.Constants.RECEIVE;
import static com.dev.dongworry.consts.Constants.TOAST_SHOW_TIME;
import static com.dev.dongworry.consts.Constants.VOICE_ENABLE;
import static com.dev.dongworry.consts.Constants.VOICE_INTERRUPTED;
import static com.dev.dongworry.consts.Constants.VOICE_LANGUAGE;
import static com.dev.dongworry.consts.Constants.VOICE_SETTING;
import static com.dev.dongworry.consts.Constants.VOICE_SPEAKER;

/**
 * Created by dengyaoning on 17/3/18.
 */

public class VoiceUtils {
    /**
     * 语音合成对象
     */
    private static SpeechSynthesizer mSpeechSynthesizer;
    /**
     * 语义理解对象（语音到语义）。
     */
    private static SpeechUnderstander mSpeechUnderstander;

    private static CustomToast toast_speech;
    private static ImageView imageView_toast_voice;
    private static Context mContext;
    private static Handler mHandler;

    public static final int SPEECH_SUCEESE = 0;
    public static final int SPEECH_FAIL = 1;

    public static void startSpeech(Context context,Handler handler) {
        mContext = context;
        mHandler = handler;
        // 初始化语音合成对象
        mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
        mSpeechUnderstander = SpeechUnderstander.createUnderstander(context, speechUnderstanderInitListener);
        /**
         * 初始化语音动画toast
         */
        toast_speech = CustomToast.makeText(mContext, "请开始说话！", TOAST_SHOW_TIME);
        toast_speech.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast_speech.getView();
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.view_speech, null, false);
        imageView_toast_voice = (ImageView) layout.findViewById(R.id.imageView_view_voice);
        toastView.addView(layout);


        // 设置参数 为何每次进行语义理解都要设置参数？
        try {
            setSpeechParams();
            if (mSpeechUnderstander.isUnderstanding()) {// 开始前检查状态
                mSpeechUnderstander.stopUnderstanding();
            } else {
                int ret = mSpeechUnderstander.startUnderstanding(speechUnderstanderListener);
                if (ret != 0) {
                    Log.e(TAG, "语义理解失败,错误码:" + ret);
                } else {
                    Log.e(TAG, "请开始说话!! ");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 初始化监听器（语音到语义）。
     */
    private static InitListener speechUnderstanderInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
//                showTip("初始化失败,错误码："+code);
            }
        }
    };

    /**
     * 初期化监听。
     */
    private static InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("", "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS){

            }
        }
    };

    private static SpeechUnderstanderListener speechUnderstanderListener = new SpeechUnderstanderListener() {

        @Override
        public void onBeginOfSpeech() {
            toast_speech.setText(mContext.getString(R.string.startSpeech));
            toast_speech.show();
        }

        @Override
        public void onEndOfSpeech() {
            toast_speech.setText(mContext.getString(R.string.Recognizing));
            imageView_toast_voice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.zero));
        }

        @Override
        public void onError(SpeechError error) {
            synthetizeInSilence(error.getErrorDescription());
            if (toast_speech != null) {
                toast_speech.cancel();
            }
            if (mHandler != null) {
                Message.obtain(mHandler, SPEECH_FAIL, error.getErrorDescription()).sendToTarget();
            }
            mHandler = null;
            mContext = null;
        }


        @Override
        public void onResult(UnderstanderResult results) {
            if (toast_speech != null) {
                toast_speech.cancel();
            }
            if (results != null) {
                String[] JsonResult = JsonUtil.ResultArray(results
                        .getResultString());
                DataHoldUtil.setSpeechJson(results.getResultString());
                if (JsonResult[1] != null) {
                    if (mHandler != null) {
                        Message.obtain(mHandler, SPEECH_SUCEESE, JsonResult[1]).sendToTarget();
                        mHandler = null;
                        mContext = null;
                    }
                } else {
                    if (mHandler != null) {
                        Message.obtain(mHandler, SPEECH_FAIL, mContext.getString(R.string.TextIsNull)).sendToTarget();
                        mHandler = null;
                        mContext = null;
                    }
                }
            }
        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onVolumeChanged(int v, byte[] arg1) {
            // TODO Auto-generated method stub

            if (v >= 0 && v <= 5)
                imageView_toast_voice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.zero));
            if (v > 5 && v <= 10)
                imageView_toast_voice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.one));
            if (v > 10 && v <= 15)
                imageView_toast_voice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.two));
            if (v > 15 && v <= 20)
                imageView_toast_voice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.three));
            if (v > 20 && v <= 25)
                imageView_toast_voice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.four));
            if (v > 25 && v <= 30)
                imageView_toast_voice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.five));
            if (v > 30 && v <= 35)
                imageView_toast_voice.setImageDrawable(mContext.getResources().getDrawable(R.drawable.six));

        }
    };

    private static void setSpeechParams() {
        mSpeechUnderstander.setParameter(SpeechConstant.PARAMS, null);
        SharedPreferences sp = mContext.getSharedPreferences(VOICE_SETTING, Context.MODE_PRIVATE);
        String lang = sp.getString(VOICE_LANGUAGE, "mandarin");
        if (lang.equals("en_us")) {
            // 设置语言
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "en_us");
        }else {
            // 设置语言
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mSpeechUnderstander.setParameter(SpeechConstant.ACCENT, lang);
        }

        // 设置语音前端点
        mSpeechUnderstander.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点
        mSpeechUnderstander.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号
        mSpeechUnderstander.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        //mSpeechUnderstander.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        //mSpeechUnderstander.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/sud.wav");
    }

    /**
     * 使用SpeechSynthesizer合成语音，不弹出合成Dialog.
     *
     * @param text
     */
    private static void synthetizeInSilence(String text) {
        if(!TextUtils.isEmpty(text)){
            String locale = mContext.getResources().getConfiguration().locale.toString();
            JChineseConvertor jConvertor = null;
            try {
                jConvertor = JChineseConvertor.getInstance();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jConvertor != null) {
                if (locale.contentEquals("zh_TW")) {
                    // 转成繁体
                    text = jConvertor.s2t(text);
                } else {
                    // 转成简体
                    text = jConvertor.t2s(text);
                }
            }
            if(text.contains("AV女演员") || text.contains("AV女优"))text = "对不起，该内容违反相关规定，请见谅！";

            /**
             * 在语音设置中是否开启了语音合成功能。
             */
            SharedPreferences sp = mContext.getSharedPreferences(VOICE_SETTING, Context.MODE_PRIVATE);
            if(sp.getBoolean(VOICE_ENABLE, true)){
                if (null == mSpeechSynthesizer) {
                    // 创建合成对象.
                    mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
                }
                // 清空参数
                mSpeechSynthesizer.setParameter(SpeechConstant.PARAMS, null);

                // 设置在线合成发音人
                mSpeechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, sp.getString(VOICE_SPEAKER, "aisxa"));
                // 获取语速
                int speed = 50;
                // 设置语速
                mSpeechSynthesizer.setParameter(SpeechConstant.SPEED, "" + speed);
                // 获取音量.
                int volume = 50;
                // 设置音量
                mSpeechSynthesizer.setParameter(SpeechConstant.VOLUME, "" + volume);
                // 获取语调
                int pitch = 50;
                // 设置语调
                mSpeechSynthesizer.setParameter(SpeechConstant.PITCH, "" + pitch);

                // 设置播放器音频流类型
                mSpeechSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, "3");
                // 设置播放合成音频打断音乐播放，默认为true
                mSpeechSynthesizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS,String.valueOf(sp.getBoolean(VOICE_INTERRUPTED, true)));

                // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
                // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//		mSpeechSynthesizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//		mSpeechSynthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH,
//				Environment.getExternalStorageDirectory() + "/msc/tts.wav");
                // 进行语音合成.
                mSpeechSynthesizer.startSpeaking(text, mSynthesizerListener);
            }
        }
    }

    // 编写一个类实现语音合成接口
    private static SynthesizerListener mSynthesizerListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {

        }

        @Override
        public void onCompleted(SpeechError error) {

        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            // TODO Auto-generated method stub

        }

    };
}
