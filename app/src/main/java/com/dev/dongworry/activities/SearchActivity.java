package com.dev.dongworry.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.dev.dongworry.adapters.SearchHistoryAdapter;
import com.dev.dongworry.customview.CustomEditText;
import com.dev.dongworry.interfaces.Commands;
import com.dev.dongworry.utils.DialogUtils;
import com.dev.dongworry.utils.SPUtils;
import com.dev.dongworry.R;
import com.dev.dongworry.utils.VoiceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dev.dongworry.consts.Constants.SEARCH_KEY;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
public class SearchActivity extends BaseActivity{

	private CustomEditText et_search;
	private Context mContext;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case VoiceUtils.SPEECH_SUCEESE:
					if(msg.obj instanceof String){
						String searchKey = (String)msg.obj;
						et_search.setText(searchKey.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", ""));
						//TODO 执行搜索
					}
					break;

				case VoiceUtils.SPEECH_FAIL:
					if(msg.obj instanceof String){
						showTip((String)msg.obj);
					}
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_search);
		et_search = (CustomEditText)findViewById(R.id.editText_search_goods);
		et_search.setCommand(new Commands() {
            @Override
            public void executeCommand() {
                // TODO Auto-generated method stub
                try {
                    VoiceUtils.startSpeech(mContext,mHandler);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "请先装谷歌语音助手", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
		/**
		 * 需要delay一下才能弹出输入法。
		 */
		et_search.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InputMethodManager inputMethodManager = (InputMethodManager) mContext
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(et_search, InputMethodManager.SHOW_FORCED);
			}
		}, 100);
		
		final Button button_search = (Button)findViewById(R.id.button_search);
		button_search.setOnClickListener(this);
		
		Button button_search_clearHistory = (Button)findViewById(R.id.button_search_clearHistory);
		
		/**
		 * ----------------------------------------    热门搜索列表           -------------------------------------------------
		 */
		GridView gridView = (GridView)findViewById(R.id.gridView_search_topSearch);
        //新建List
		final ArrayList<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
		String[] keyNames = getResources().getStringArray(R.array.searchTop);
        //获取数据
        for(int i=0;i<8;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", keyNames[i]);
            data_list.add(map);
        }
        //新建适配器
        String [] from ={"text"};
        int [] to = {R.id.textView_search_topList};
        SimpleAdapter simpleAdapter = new SimpleAdapter(mContext, data_list, R.layout.item_goods_search_top, from, to);
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				et_search.setText((String) data_list.get(position).get("text"));
				button_search.callOnClick();
			}
		});
        
        /**
         * -------------------------------------------    搜索历史记录列表              --------------------------------------------
         */
        final ArrayList<String> searchKey = SPUtils.getSearchHistory(mContext);
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout_search_history);
		if(searchKey != null){
			ListView listView = (ListView)findViewById(R.id.listView_search_history);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position,
						long arg3) {
					// TODO Auto-generated method stub
					et_search.setText(searchKey.get(position));
					button_search.callOnClick();
				}
			});
			SearchHistoryAdapter adapter = new SearchHistoryAdapter(mContext, searchKey);
			listView.setAdapter(adapter);
			
			button_search_clearHistory.setOnClickListener(this);
		}else{
			layout.setVisibility(View.INVISIBLE);
		}
		
	}
	
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.button_search:
				/**
				 * 将搜索关键字回调给主界面，并设置搜索历史记录
				 */
				Intent intent = new Intent();
				if(et_search.getText().length() == 0){
					Toast.makeText(mContext, "请输入关键字", Toast.LENGTH_SHORT).show();
				}else{
					String searchKey = et_search.getText().toString();
					intent.putExtra(SEARCH_KEY, searchKey); 
					SPUtils.setSearchHistory(mContext, searchKey);
					setResult(Activity.RESULT_OK, intent);
					finish();
					overridePendingTransition(0, R.anim.activity_down);
				}
				break;
				
			case R.id.button_search_clearHistory:
				DialogUtils.showDialog(mContext, "", getString(R.string.isSureClearHistory), new Commands() {
					@Override
					public void executeCommand() {
						// TODO Auto-generated method stub
						/**
						 * 清除历史记录并隐藏
						 */
						SPUtils.clearSearchHistory(mContext);
						LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout_search_history);
						layout.setVisibility(View.INVISIBLE);
					}
				});
				break;
			default:
				break;
			}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == MainActivity.SEARCH_TEXT  
                && resultCode == Activity.RESULT_OK) { 
//			/**
//			 * 将输入结果回调给主界面，同时设置搜索历史记录
//			 */
//            String text= et_search.getText().toString();
//            SPUtils.setSearchHistory(mContext, text);
//			Intent intent = new Intent();
//		    intent.putExtra("text", text); 
//			setResult(Activity.RESULT_OK, intent);
//			finish();
//			overridePendingTransition(0, R.anim.activity_down);
        }  
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(0, R.anim.activity_down);
			return true;
		}
		return false;
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
}
