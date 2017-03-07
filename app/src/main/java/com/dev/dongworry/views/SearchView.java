package com.dev.dongworry.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.text.Selection;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.dev.dongworry.R;
import com.dev.dongworry.adapters.SearchHistoryAdapter;
import com.dev.dongworry.controlers.BaseControler;
import com.dev.dongworry.customview.CustomEditText;
import com.dev.dongworry.interfaces.Commands;
import com.dev.dongworry.utils.DialogUtils;
import com.dev.dongworry.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * "搜索界面"
 * @author 邓耀宁
 *
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
public class SearchView extends BaseView implements BaseControler.ViewChangeListener{
	public static final int VOICE_RECOGNITION_REQUEST_CODE = 0;
	
	private CustomEditText editText_search_goods;
	
	
	public SearchView(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mActivity.setContentView(R.layout.activity_search);
		
		editText_search_goods = (CustomEditText)mActivity.findViewById(R.id.editText_search_goods);
		editText_search_goods.setText(mActivity.getIntent().getStringExtra("searchKey"));
		/**
		 * 将光标移到最后，方便修改文字内容。
		 */
		CharSequence charSequence = editText_search_goods.getText();
		if (charSequence instanceof Spannable) {
			Spannable spanText = (Spannable) charSequence;
			Selection.setSelection(spanText, charSequence.length());
		}
		/**
		 * 需要delay一下才能弹出输入法。
		 */
		editText_search_goods.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InputMethodManager inputMethodManager = (InputMethodManager) mActivity
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(editText_search_goods, InputMethodManager.SHOW_FORCED);
			}
		}, 100);
		
		/**
		 * 如果设置了command，则这个自定义的editText将显示在xml上设置的图片，点击该图片则执行该command。
		 */
		editText_search_goods.setCommand(new Commands() {
			
			@Override
			public void executeCommand() {
				// TODO Auto-generated method stub
			        try {  
			            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);  
			            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,  
			                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);  
			            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请对着麦克风说话！");  
			            mActivity.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);  
			        } catch (Exception e) {  
			            e.printStackTrace(); 
			            Toast.makeText(mActivity, "请先装谷歌语音助手", Toast.LENGTH_SHORT).show();  
			        }  
			        
			    }  
		});
		
		ButtonListener buttonListener = new ButtonListener();
		final Button button_search = (Button)mActivity.findViewById(R.id.button_search);
		button_search.setOnClickListener(buttonListener);
		
		Button button_search_clearHistory = (Button)mActivity.findViewById(R.id.button_search_clearHistory);
		
		ImageButton imageButton_search_back = (ImageButton)mActivity.findViewById(R.id.imageButton_search_back);
		imageButton_search_back.setOnClickListener(buttonListener);
		/**
		 * ----------------------------------------    热门搜索列表           -------------------------------------------------
		 */
		GridView gridView = (GridView)mActivity.findViewById(R.id.gridView_search_topSearch);
        //新建List
		final ArrayList<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
		String keyName = "500M流量";
        //获取数据
        for(int i=0;i<9;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", keyName);
            data_list.add(map);
        }
        //新建适配器
        String [] from ={"text"};
        int [] to = {R.id.textView_search_topList};
        SimpleAdapter simpleAdapter = new SimpleAdapter(mActivity, data_list, R.layout.goods_search_top, from, to);
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				editText_search_goods.setText((String) data_list.get(position).get("text"));
				button_search.callOnClick();
			}
		});
        
        /**
         * -------------------------------------------    搜索历史记录列表              --------------------------------------------
         */
        final ArrayList<String> searchKey = SharedPreferenceUtils.getSearchHistory(mActivity);
        RelativeLayout layout = (RelativeLayout)mActivity.findViewById(R.id.relativeLayout_search_history);
		if(searchKey != null){
			ListView listView = (ListView)mActivity.findViewById(R.id.listView_search_history);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position,
						long arg3) {
					// TODO Auto-generated method stub
					editText_search_goods.setText(searchKey.get(position));
					button_search.callOnClick();
				}
			});
			SearchHistoryAdapter adapter = new SearchHistoryAdapter(mActivity, searchKey);
			listView.setAdapter(adapter);
			
			button_search_clearHistory.setOnClickListener(buttonListener);
		}else{
			layout.setVisibility(View.INVISIBLE);
		}
		
	}
	
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.button_search:
				/**
				 * 将搜索关键字回调给商品界面，并设置搜索历史记录
				 */
				Intent intent = new Intent();
				if(editText_search_goods.getText().length() == 0){
					String hint = (String)editText_search_goods.getHint();
				    intent.putExtra("searchKey", hint);   
				    SharedPreferenceUtils.setSearchHistory(mActivity, hint);
				}else{
					String searchKey = editText_search_goods.getText().toString();
					intent.putExtra("searchKey", searchKey); 
					SharedPreferenceUtils.setSearchHistory(mActivity, searchKey);
				}
				mActivity.setResult(Activity.RESULT_OK, intent);
				finish();
				mActivity.overridePendingTransition(0, R.anim.activity_down);
				break;
				
			case R.id.imageButton_search_back:
				finish();
				mActivity.overridePendingTransition(0, R.anim.activity_down);
				break;
				
			case R.id.button_search_clearHistory:
				DialogUtils.showDialog(mActivity, "", getString(R.string.isSureClearHistory), new Commands() {
					
					@Override
					public void executeCommand() {
						// TODO Auto-generated method stub
						/**
						 * 清除历史记录并隐藏
						 */
						SharedPreferenceUtils.clearSearchHistory(mActivity);
						RelativeLayout layout = (RelativeLayout)mActivity.findViewById(R.id.relativeLayout_search_history);
						layout.setVisibility(View.INVISIBLE);
					}
				});
				break;
			default:
				break;
			}
		}
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE  
                && resultCode == Activity.RESULT_OK) { 
			/**
			 * 将语音结果处理并回调给商品界面，同时设置搜索历史记录
			 */
            ArrayList<String> matchResults = data  
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);  
            String voice_str = "";  
            voice_str = matchResults.get(0).toString();// 只要最相似的就行，去第一个，  
            SharedPreferenceUtils.setSearchHistory(mActivity, voice_str);
			Intent intent = new Intent();
		    intent.putExtra("searchKey", voice_str); 
			mActivity.setResult(Activity.RESULT_OK, intent);
			
			finish();
			mActivity.overridePendingTransition(0, R.anim.activity_down);
        }  
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			mActivity.overridePendingTransition(0, R.anim.activity_down);
			return true;
		}
		return false;
	}
	
	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
