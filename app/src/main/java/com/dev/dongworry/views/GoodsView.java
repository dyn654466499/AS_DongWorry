package com.dev.dongworry.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.GoodsDetailActivity;
import com.dev.dongworry.activities.SearchActivity;
import com.dev.dongworry.adapters.GoodsAdapter;
import com.dev.dongworry.adapters.ScreeningCheckBoxAdapter;
import com.dev.dongworry.adapters.ScreeningPriceAdapter;
import com.dev.dongworry.adapters.SmartSortAdapter;
import com.dev.dongworry.controlers.BaseControler;
import com.dev.dongworry.controlers.GoodsControler;
import com.dev.dongworry.customview.CustomEditText;
import com.dev.dongworry.interfaces.Commands;

import java.util.ArrayList;

/**
 * 商品列表
 * 
 * @author 邓耀宁
 * 
 */
public class GoodsView extends BaseView implements BaseControler.ViewChangeListener {
	public static final int VOICE_RECOGNITION_REQUEST_CODE = 0;
	public static final int SEARCH = 1;

	private CustomEditText editText_goods_search;

	private LinearLayout categoryClickedLayout;

	private GoodsControler controler;

	private PopupWindow popupWindow_allCategory, popupWindow_topSearch,
			popupWindow_smartSorting, popupWindow_screening;

	private ListView listView_goods;

	public GoodsView(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
		controler = new GoodsControler(mActivity);
		controler.setViewChangeListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mActivity.setContentView(R.layout.activity_goods);

		editText_goods_search = (CustomEditText) mActivity
				.findViewById(R.id.editText_goods_search);
		editText_goods_search.setCommand(new Commands() {

			@Override
			public void executeCommand() {
				// TODO Auto-generated method stub
				try {
					Intent intent = new Intent(
							RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
							RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请对着麦克风说话！");
					mActivity.startActivityForResult(intent,
							VOICE_RECOGNITION_REQUEST_CODE);
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(mActivity, "请先装谷歌语音助手", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
		ButtonListener buttonListener = new ButtonListener();
		editText_goods_search.setOnClickListener(buttonListener);

		ImageButton imageButton_search_back = (ImageButton) mActivity
				.findViewById(R.id.imageButton_goods_back);
		imageButton_search_back.setOnClickListener(buttonListener);
		
		listView_goods = (ListView) mActivity.findViewById(R.id.listView_goods);
		GoodsAdapter adapter = new GoodsAdapter(mActivity);
		listView_goods.setAdapter(adapter);
		listView_goods.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mActivity.startActivity(new Intent(mActivity, GoodsDetailActivity.class));
			}
		});
		// final View searchView =
		// LayoutInflater.from(mActivity).inflate(R.layout.activity_search,
		// null,false);
		// AutoLoadingUtils loadingUtils = new
		// AutoLoadingUtils(mActivity,R.id.imageView_autoLoading);
		// loadingUtils.showAutoLoadingLayout();

		popupWindow_smartSorting = creatSmartSortingPopupWindow();
		popupWindow_screening = creatScreeningPopupWindow();
	}

	// /**
	// * 设置添加屏幕的背景透明度
	// * @param bgAlpha
	// */
	// public void backgroundAlpha(float bgAlpha)
	// {
	// WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
	// lp.alpha = bgAlpha; //0.0-1.0
	// mActivity.getWindow().setAttributes(lp);
	// }
	
	/**
	 * 创建“全部分类”popupwindow
	 * @return
	 */
	private PopupWindow creatAllCategoryPopupWindow() {
		return null;
	}
	/**
	 * 创建“热门搜索”popupwindow
	 * @return
	 */
	private PopupWindow creatTopSearchPopupWindow() {
		return null;
	}

	/**
	 * 创建“智能排序”popupwindow
	 * @return
	 */
	private PopupWindow creatSmartSortingPopupWindow() {
		final View popupView_smartSorting = LayoutInflater.from(mActivity).inflate(
				R.layout.category_smartsorting, null, false);

		final PopupWindow popupWindow_smartSorting = new PopupWindow(popupView_smartSorting,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, false);
		popupWindow_smartSorting.setAnimationStyle(R.style.popupTheme);
		
		final ListView listView_smartSorting = (ListView) popupView_smartSorting
				.findViewById(R.id.listView_smartSorting);
		final String[] smartSortingNames = { getString(R.string.smartSorting),
				getString(R.string.topEvaluate),
				getString(R.string.lastedRelease),
				getString(R.string.topSales), getString(R.string.lowestPrice),
				getString(R.string.highestPrice), };
		/**
		 * 
		 */
		final SmartSortAdapter smartSortAdapter = new SmartSortAdapter(
				mActivity, R.layout.category_smartsorting_item,
				smartSortingNames);
		listView_smartSorting.setAdapter(smartSortAdapter);
		listView_smartSorting.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Button button = (Button) categoryClickedLayout.getChildAt(0);
				button.setText(smartSortingNames[position]);
				smartSortAdapter.setSelectedPosition(position);
				smartSortAdapter.notifyDataSetChanged();
				/**
				 * 延迟popupwindow消失
				 */
				popupView_smartSorting.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						popupWindow_smartSorting.dismiss();
						/**
						 * 在此按所选的排序执行代码
						 */

					}
				}, 300);
			}
		});

		/**
		 * 关键的地方，切勿设置如下注释的函数，改为在touchListener中监听是否dismiss。
		 */
		// categoryWindow.setTouchable(true);
		// categoryWindow.setOutsideTouchable(true);
		popupWindow_smartSorting.setFocusable(false);
		/**
		 * 必须设置drawable
		 */
		popupWindow_smartSorting.setBackgroundDrawable(new BitmapDrawable());
		popupWindow_smartSorting.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				/**
				 * 获取listView的矩形区域，判断点击是否在该区域内，若不在，则dismiss。
				 */
				Rect rect = new Rect(0, 0, listView_smartSorting.getWidth(),
						listView_smartSorting.getHeight());
				if (!rect.contains((int) event.getX(), (int) event.getY())) {
					popupWindow_smartSorting.dismiss();
				}
				return false;
			}
		});
		popupWindow_smartSorting.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				if (categoryClickedLayout != null) {
					hideCategoryPopup(categoryClickedLayout, true);
				}
			}
		});
		return popupWindow_smartSorting;
	}

	/**
	 * 创建“筛选”popupwindow
	 * @return
	 */
	private PopupWindow creatScreeningPopupWindow() {
		final View popupView_screening = LayoutInflater.from(mActivity).inflate(
				R.layout.category_screening, null, false);

		final LinearLayout mainLayout = (LinearLayout)popupView_screening.findViewById(R.id.linearLayout_screening_main);
		
		final PopupWindow popupWindow_screening = new PopupWindow(popupView_screening,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, false);
		popupWindow_screening.setAnimationStyle(R.style.popupTheme);
		
		/**
		 * 筛选条件UI初始化
		 */
		final GridView gridView_topScreening = (GridView) popupView_screening
				.findViewById(R.id.gridView_screening_top);
//		final String[] screening_checkbox_names = { getString(R.string.smartSorting),
//				getString(R.string.topEvaluate),
//				getString(R.string.lastedRelease),
//				getString(R.string.topSales), getString(R.string.lowestPrice),
//				getString(R.string.highestPrice), };
		final String[] screening_checkbox_names = { "热销","推荐",
				getString(R.string.lastedRelease),
				getString(R.string.topSales), getString(R.string.lowestPrice),
				getString(R.string.highestPrice),};
		final ScreeningCheckBoxAdapter checkBoxAdapter = new ScreeningCheckBoxAdapter(
				mActivity,screening_checkbox_names);
		gridView_topScreening.setAdapter(checkBoxAdapter);
		gridView_topScreening.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				checkBoxAdapter.setSelectedPosition(position);
				checkBoxAdapter.notifyDataSetChanged();
			}
		});

		final String[] screening_prices_names = { "不限","10-20","20-50","50-200","200-400","400以上"};
		/**
		 * 筛选价格UI初始化
		 */
		final ScreeningPriceAdapter screeningPriceAdapter = new ScreeningPriceAdapter(
				mActivity, R.layout.category_screening_price_item,
				screening_prices_names);
		final GridView gridView_screening_price = (GridView) popupView_screening
				.findViewById(R.id.gridView_screening_price);
		gridView_screening_price.setAdapter(screeningPriceAdapter);
		gridView_screening_price.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				screeningPriceAdapter.setSelectedPosition(position);
				screeningPriceAdapter.notifyDataSetChanged();
			}
		});
		
		
		/**
		 * 关键的地方，切勿设置如下注释的函数，改为在touchListener中监听是否dismiss。
		 */
		//popupWindow_screening.setTouchable(true);
		//popupWindow_screening.setOutsideTouchable(true);
		//popupWindow_screening.setFocusable(false);
		/**
		 * 必须设置drawable
		 */
		popupWindow_screening.setBackgroundDrawable(new BitmapDrawable());
		popupWindow_screening.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				/**
				 * 获取mainLayout的矩形区域，判断点击是否在该区域内，若不在，则dismiss。
				 */
				Rect rect = new Rect(0, 0, mainLayout.getWidth(),
						mainLayout.getHeight());
				if (!rect.contains((int) event.getX(), (int) event.getY())) {
					
					popupWindow_screening.dismiss();
				}
				return false;
			}
		});
		popupWindow_screening.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				if (categoryClickedLayout != null) {
					hideCategoryPopup(categoryClickedLayout, true);
				}
				/**
				 * 在popupwindow消失时将position重新设置为-1
				 */
				checkBoxAdapter.setSelectedPosition(-1);
			}
		});
		
		Button button_screening_price = (Button)popupView_screening.findViewById(R.id.button_screening_price);
		button_screening_price.setOnClickListener(new ButtonListener());
		
		return popupWindow_screening;
	}
	
	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.imageButton_goods_back:
				finish();
				break;

			case R.id.editText_goods_search:
				/**
				 * 禁用输入法
				 */
				InputMethodManager inputMethodManager = (InputMethodManager) mActivity
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(mActivity
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);

				Intent intent = new Intent(mActivity, SearchActivity.class);
				intent.putExtra("searchKey", editText_goods_search.getText()
						.toString());
				mActivity.startActivityForResult(intent, SEARCH);
				mActivity.overridePendingTransition(0, R.anim.activity_up);
				break;
				
			case R.id.button_screening_price:
				/**
				 * 延迟popupwindow消失
				 */
				view.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						popupWindow_screening.dismiss();
						/**
						 * 在此以筛选结果来执行代码
						 */

					}
				}, 300);
				break;
				
			default:
				break;
			}
		}

	}

	/**
	 * xml设置onclick，必须在GoodsActivity中构造如下方法才能执行。
	 * 
	 * @param view
	 */
	public void clickCategoryView(View view) {
		if (view instanceof LinearLayout) {
			Log.e(getTAG(), "clickCategoryView");
			LinearLayout layout = (LinearLayout) view;
			/**
			 * 如果前一个点击的layout和现在点击的layout不是同一个，则前一个layout hide popup。
			 */
			if (categoryClickedLayout != null
					&& categoryClickedLayout != layout
					&& "clicked"
							.equals((String) categoryClickedLayout.getTag())) {
				hideCategoryPopup(categoryClickedLayout, true);
			}

			if ("clicked".equals((String) layout.getTag())) {
				hideCategoryPopup(layout, true);
			} else {
				showCategoryPopup(layout, true);
			}
			/**
			 * 保存点击的layout
			 */
			categoryClickedLayout = layout;
		}
	}

	/**
	 * 显示分类信息
	 * 
	 * @param categoryLayout
	 */
	private void showCategoryPopup(LinearLayout categoryLayout, boolean isSetTag) {
		if (isSetTag)
			categoryLayout.setTag("clicked");
		Button button = (Button) categoryLayout.getChildAt(0);
		final ImageView imageView = (ImageView) categoryLayout.getChildAt(1);

		button.setTextColor(mActivity.getResources().getColor(R.color.blue));
		imageView.setImageResource(R.drawable.icon_arrows_red_up);
		switch (button.getId()) {
		case R.id.button_allCategories:

			break;
		case R.id.button_topSearch:
			
			break;
		case R.id.button_smartSorting:
			popupWindow_smartSorting.showAsDropDown(categoryLayout);
			break;
		case R.id.button_screening:
			popupWindow_screening.showAsDropDown(categoryLayout);
			break;
		default:
			break;
		}
	}

	/**
	 * 隐藏分类信息
	 * 
	 * @param categoryLayout
	 */
	private void hideCategoryPopup(LinearLayout categoryLayout, boolean isSetTag) {
		if (isSetTag)
			categoryLayout.setTag("unclick");

		Button button = (Button) categoryLayout.getChildAt(0);
		final ImageView imageView = (ImageView) categoryLayout.getChildAt(1);

		button.setTextColor(Color.BLACK);
		imageView.setImageResource(R.drawable.icon_arrows_gray_down);
		switch (button.getId()) {
		case R.id.button_allCategories:

			break;
		case R.id.button_topSearch:

			break;
		case R.id.button_smartSorting:
			if (popupWindow_smartSorting.isShowing())
				popupWindow_smartSorting.dismiss();
			break;
		case R.id.button_screening:
			if (popupWindow_screening.isShowing())
				popupWindow_screening.dismiss();
			break;
		default:
			break;
		}
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if (hasFocus) {

		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == Activity.RESULT_OK) {
			ArrayList<String> matchResults = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String voice_str = "";
			voice_str = matchResults.get(0).toString();// 只要最相似的就行，去第一个，
			editText_goods_search.setText(voice_str);
			/**
			 * 执行搜索
			 */
			startSearchGoods();
		}

		if (requestCode == SEARCH && resultCode == Activity.RESULT_OK) {
			if (data != null) {
				String searchKey = data.getStringExtra("searchKey");
				Log.e(getTAG(), searchKey);
				editText_goods_search.setText(searchKey);
				/**
				 * 执行搜索
				 */
				startSearchGoods();
			}
		}
	}
	
	private void startSearchGoods(){
		/**
		 * 进行搜索后，将popupwindow以及分类标签初始化。
		 */
		LinearLayout layout = (LinearLayout)mActivity.findViewById(R.id.linearLayout_allCategories);
		for (int i = 0; i < layout.getChildCount(); i++) {
			View view = layout.getChildAt(i);
			if(view instanceof LinearLayout){
				Button button = (Button)((LinearLayout) view).getChildAt(0);
				button.setText((String)button.getTag());
				Log.e(getTAG(), "Category button text is init!");
			}
		}
		
		if(popupWindow_smartSorting.isShowing())popupWindow_smartSorting.dismiss();
		if(popupWindow_screening.isShowing())popupWindow_screening.dismiss();
		popupWindow_smartSorting = creatSmartSortingPopupWindow();
		popupWindow_screening = creatScreeningPopupWindow();
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub

	}

}
