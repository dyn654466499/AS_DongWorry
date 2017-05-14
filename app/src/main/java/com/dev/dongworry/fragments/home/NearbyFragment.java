package com.dev.dongworry.fragments.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.GoodsDetailActivity;
import com.dev.dongworry.adapters.GoodsAdapter;
import com.dev.dongworry.adapters.ScreeningCheckBoxAdapter;
import com.dev.dongworry.adapters.ScreeningPriceAdapter;
import com.dev.dongworry.adapters.SmartSortAdapter;
import com.dev.dongworry.customview.CustomEditText;
import com.dev.dongworry.customview.RefreshLayout;
import com.dev.dongworry.fragments.BaseFragment;
import com.dev.dongworry.utils.ImageUtil;
import com.dev.dongworry.utils.VoiceUtils;

import java.util.ArrayList;

public class NearbyFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        RefreshLayout.OnLoadListener{

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 0;
    public static final int SEARCH = 1;
    public static final int SELECT_CITY = 2;

    private CustomEditText et_nearby_search;

    private RelativeLayout categoryClickedLayout;

    private PopupWindow popupWindow_allCategory, popupWindow_way,
            popupWindow_smartSorting, popupWindow_screening;
    private TextView tv_nearby_localCity;
    private RefreshLayout mswipeLayout_nearby;

    private ListView listView_nearby;
    private Context mContext;
    private View rootView;

    private boolean isRefresh = false;//是否刷新中
    private GoodsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden && categoryClickedLayout != null){
            //延时，改善视觉体验
            categoryClickedLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideCategoryPopup(categoryClickedLayout, true);
                }
            },100);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case VoiceUtils.SPEECH_SUCEESE:
                    if(msg.obj instanceof String){
                        String searchKey = (String)msg.obj;
                        et_nearby_search.setHint(searchKey.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", ""));
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_nearby, container, false);
//        et_nearby_search = (CustomEditText)rootView.findViewById(R.id.et_nearby_search);
//        et_nearby_search.setCommand(new Commands() {
//
//            @Override
//            public void executeCommand() {
//                // TODO Auto-generated method stub
//                try {
//                    VoiceUtils.startSpeech(mContext,mHandler);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(mContext, "请先装谷歌语音助手", Toast.LENGTH_SHORT)
//                            .show();
//                }
//
//            }
//        });
//        et_nearby_search.setOnClickListener(this);

//        tv_nearby_localCity = (TextView)rootView.findViewById(R.id.tv_nearby_localCity);
//        tv_nearby_localCity.setOnClickListener(this);

        listView_nearby = (ListView)rootView.findViewById(R.id.listView_nearby);
        adapter = new GoodsAdapter(mContext);
        listView_nearby.setAdapter(adapter);
        listView_nearby.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startActivity(new Intent(mContext, GoodsDetailActivity.class));
            }
        });

        popupWindow_allCategory = createAllCategoryPopupWindow();
        popupWindow_way = createWayPopupWindow();
        popupWindow_smartSorting = createSmartSortingPopupWindow();
        popupWindow_screening = createScreeningPopupWindow();

        LinearLayout lLayout_allCategories = (LinearLayout)rootView.findViewById(R.id.lLayout_nearby_allCategories);
        for (int i = 0;i < lLayout_allCategories.getChildCount();i++){
            lLayout_allCategories.getChildAt(i).setOnClickListener(this);
        }

        //设置SwipeRefreshLayout
        mswipeLayout_nearby = (RefreshLayout)rootView.findViewById(R.id.swipeLayout_nearby);

        mswipeLayout_nearby.setColorSchemeColors(getColor(R.color.themeColor));
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mswipeLayout_nearby.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        mswipeLayout_nearby.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        mswipeLayout_nearby.setSize(RefreshLayout.DEFAULT);
        //设置下拉刷新的监听
        mswipeLayout_nearby.setOnRefreshListener(this);
        mswipeLayout_nearby.setOnLoadListener(this);
        return rootView;
    }

    /**
     * 创建“全部”popupwindow
     * @return
     */
    private PopupWindow createAllCategoryPopupWindow() {
        final View popupView_allCategory = LayoutInflater.from(mContext).inflate(
                R.layout.category_smartsorting, null, false);

        final PopupWindow popupWindow_allCategory = new PopupWindow(popupView_allCategory,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        popupWindow_allCategory.setAnimationStyle(R.style.popupTheme);

        final ListView listView_allCategory = (ListView) popupView_allCategory
                .findViewById(R.id.listView_smartSorting);
        final String[] allCategoryNames = getResources().getStringArray(R.array.label_home);
        /**
         *
         */
        final SmartSortAdapter allCategoryAdapter = new SmartSortAdapter(
                mContext, R.layout.category_smartsorting_item,
                allCategoryNames);
        allCategoryAdapter.setCurPosition(allCategoryNames.length -1);
        listView_allCategory.setAdapter(allCategoryAdapter);
        listView_allCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                Button button = (Button) categoryClickedLayout.getChildAt(0);
                button.setText(allCategoryNames[position]);
                allCategoryAdapter.setSelectedPosition(position);
                allCategoryAdapter.notifyDataSetChanged();
                /**
                 * 延迟popupwindow消失
                 */
                popupView_allCategory.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        popupWindow_allCategory.dismiss();
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
        popupWindow_allCategory.setFocusable(false);
        /**
         * 必须设置drawable
         */
        popupWindow_allCategory.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_allCategory.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /**
                 * 获取listView的矩形区域，判断点击是否在该区域内，若不在，则dismiss。
                 */
                Rect rect = new Rect(0, 0, listView_allCategory.getWidth(),
                        listView_allCategory.getHeight());
                if (!rect.contains((int) event.getX(), (int) event.getY())) {
                    popupWindow_allCategory.dismiss();
                }
                return false;
            }
        });
        popupWindow_allCategory.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                if (categoryClickedLayout != null) {
                    hideCategoryPopup(categoryClickedLayout, true);
                }
            }
        });
        return popupWindow_allCategory;
    }

    /**
     * 创建“排序”popupwindow
     * @return
     */
    private PopupWindow createWayPopupWindow() {
        final View popupView_smartSorting = LayoutInflater.from(mContext).inflate(
                R.layout.category_smartsorting, null, false);

        final PopupWindow popupWindow_smartSorting = new PopupWindow(popupView_smartSorting,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        popupWindow_smartSorting.setAnimationStyle(R.style.popupTheme);

        final ListView listView_smartSorting = (ListView) popupView_smartSorting
                .findViewById(R.id.listView_smartSorting);
        final String[] smartSortingNames = { getString(R.string.sort),
                getString(R.string.topEvaluate),
                getString(R.string.lastedRelease),
                getString(R.string.topSales), getString(R.string.lowestPrice),
                getString(R.string.highestPrice), };
        /**
         *
         */
        final SmartSortAdapter smartSortAdapter = new SmartSortAdapter(
                mContext, R.layout.category_smartsorting_item,
                smartSortingNames);
        listView_smartSorting.setAdapter(smartSortAdapter);
        listView_smartSorting.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
        popupWindow_smartSorting.setTouchInterceptor(new View.OnTouchListener() {

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
        popupWindow_smartSorting.setOnDismissListener(new PopupWindow.OnDismissListener() {

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
     * 创建“智能排序”popupwindow
     * @return
     */
    private PopupWindow createSmartSortingPopupWindow() {
        final View popupView_smartSorting = LayoutInflater.from(mContext).inflate(
                R.layout.category_smartsorting, null, false);

        final PopupWindow popupWindow_smartSorting = new PopupWindow(popupView_smartSorting,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        popupWindow_smartSorting.setAnimationStyle(R.style.popupTheme);

        final ListView listView_smartSorting = (ListView) popupView_smartSorting
                .findViewById(R.id.listView_smartSorting);
        final String[] smartSortingNames = { getString(R.string.sort),
                getString(R.string.topEvaluate),
                getString(R.string.lastedRelease),
                getString(R.string.topSales), getString(R.string.lowestPrice),
                getString(R.string.highestPrice), };
        /**
         *
         */
        final SmartSortAdapter smartSortAdapter = new SmartSortAdapter(
                mContext, R.layout.category_smartsorting_item,
                smartSortingNames);
        listView_smartSorting.setAdapter(smartSortAdapter);
        listView_smartSorting.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
        popupWindow_smartSorting.setTouchInterceptor(new View.OnTouchListener() {

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
        popupWindow_smartSorting.setOnDismissListener(new PopupWindow.OnDismissListener() {

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
    private PopupWindow createScreeningPopupWindow() {
        final View popupView_screening = LayoutInflater.from(mContext).inflate(
                R.layout.category_screening, null, false);

        final LinearLayout mainLayout = (LinearLayout)popupView_screening.findViewById(R.id.linearLayout_screening_main);

        final PopupWindow popupWindow_screening = new PopupWindow(popupView_screening,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        popupWindow_screening.setAnimationStyle(R.style.popupTheme);

        /**
         * 筛选条件UI初始化
         */
        final GridView gridView_topScreening = (GridView) popupView_screening
                .findViewById(R.id.gridView_screening_top);
        final String[] screening_checkbox_names = { "热销","推荐",
                getString(R.string.lastedRelease),
                getString(R.string.topSales), getString(R.string.lowestPrice),
                getString(R.string.highestPrice),};
        final ScreeningCheckBoxAdapter checkBoxAdapter = new ScreeningCheckBoxAdapter(
                mContext,screening_checkbox_names);
        gridView_topScreening.setAdapter(checkBoxAdapter);
        gridView_topScreening.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
                mContext, R.layout.category_screening_price_item,
                screening_prices_names);
        final GridView gridView_screening_price = (GridView) popupView_screening
                .findViewById(R.id.gridView_screening_price);
        gridView_screening_price.setAdapter(screeningPriceAdapter);
        gridView_screening_price.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
        popupWindow_screening.setTouchInterceptor(new View.OnTouchListener() {

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
        popupWindow_screening.setOnDismissListener(new PopupWindow.OnDismissListener() {

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

        Button button_screening_price = (Button)popupView_screening.findViewById(R.id.btn_screening_price);
        button_screening_price.setOnClickListener(this);

        return popupWindow_screening;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        // TODO Auto-generated method stub
        switch (view.getId()) {
//            case R.id.tv_nearby_localCity:
//                intent = new Intent(getActivity(), CityActivity.class);
//                startActivityForResult(intent,SELECT_CITY);
//                return;
//
//            case R.id.et_nearby_search:
//                /**
//                 * 禁用输入法
//                 */
//                InputMethodManager inputMethodManager = (InputMethodManager) mContext
//                        .getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//
//                intent = new Intent(mContext, SearchActivity.class);
//                intent.putExtra("searchKey", et_nearby_search.getText()
//                        .toString());
//                startActivityForResult(intent, SEARCH);
//                getActivity().overridePendingTransition(0, R.anim.activity_up);
//                return;

            case R.id.btn_screening_price:
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
                return;

            default:
                break;
        }
        clickCategoryView(view);
    }

    /**
     * xml设置onclick，必须在GoodsActivity中构造如下方法才能执行。
     *
     * @param view
     */
    public void clickCategoryView(View view) {
        if (view instanceof RelativeLayout) {
            RelativeLayout layout = (RelativeLayout) view;
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
    private void showCategoryPopup(RelativeLayout categoryLayout, boolean isSetTag) {
        if (isSetTag)
            categoryLayout.setTag("clicked");
        Button button = (Button) categoryLayout.getChildAt(0);
        button.setTextColor(getColor(R.color.themeColor));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_arrows_down);
        Drawable drawable = new BitmapDrawable(getResources(),ImageUtil.rotateBitmap(bitmap,180));
        drawable.setColorFilter(getColor(R.color.themeColor), PorterDuff.Mode.SRC_ATOP);
        button.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
        switch (button.getId()) {
            case R.id.btn_nearby_allCategories:
                popupWindow_allCategory.showAsDropDown(categoryLayout);
                break;
            case R.id.btn_nearby_jewel:
                popupWindow_way.showAsDropDown(categoryLayout);
                break;
            case R.id.btn_nearby_smartSorting:
                popupWindow_smartSorting.showAsDropDown(categoryLayout);
                break;
            case R.id.btn_nearby_screening:
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
    private void hideCategoryPopup(RelativeLayout categoryLayout, boolean isSetTag) {
        if (isSetTag)
            categoryLayout.setTag("unclick");

        Button button = (Button) categoryLayout.getChildAt(0);
        button.setTextColor(Color.BLACK);
        Drawable drawable = getDrawable(R.drawable.icon_arrows_down);
//        drawable.setColorFilter(getColor(R.color.font_deep_gray), PorterDuff.Mode.SRC_ATOP);
        button.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
        switch (button.getId()) {
            case R.id.btn_nearby_allCategories:
                if (popupWindow_allCategory.isShowing()) {
                    popupWindow_allCategory.dismiss();
                }
                break;
            case R.id.btn_nearby_jewel:
                if (popupWindow_way.isShowing()) {
                    popupWindow_way.dismiss();
                }
                break;
            case R.id.btn_nearby_smartSorting:
                if (popupWindow_smartSorting.isShowing()) {
                    popupWindow_smartSorting.dismiss();
                }
                break;
            case R.id.btn_nearby_screening:
                if (popupWindow_screening.isShowing()) {
                    popupWindow_screening.dismiss();
                }
                break;
            default:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case VOICE_RECOGNITION_REQUEST_CODE:
                    ArrayList<String> matchResults = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String voice_str = "";
                    voice_str = matchResults.get(0).toString();// 只要最相似的就行，去第一个，
                    et_nearby_search.setText(voice_str);
                    /**
                     * 执行搜索
                     */
                    startNearbySearch(rootView);
                    break;

                case SEARCH:
                    String searchKey = data.getStringExtra("searchKey");
                    et_nearby_search.setText(searchKey);
                    /**
                     * 执行搜索
                     */
                    startNearbySearch(rootView);
                    break;

                case SELECT_CITY:
                    String cityname = data.getStringExtra("cityname");
                    if(tv_nearby_localCity != null && cityname != null) {
                        tv_nearby_localCity.setText(cityname + " ");
                    }
                    break;
            }
        }
    }

    private void startNearbySearch(View rootView){
        /**
         * 进行搜索后，将popupwindow以及分类标签初始化。
         */
        LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.lLayout_nearby_allCategories);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if(view instanceof LinearLayout){
                Button button = (Button)((LinearLayout) view).getChildAt(0);
                button.setText((String)button.getTag());
            }
        }

        if(popupWindow_smartSorting.isShowing())popupWindow_smartSorting.dismiss();
        if(popupWindow_screening.isShowing())popupWindow_screening.dismiss();
        popupWindow_smartSorting = createSmartSortingPopupWindow();
        popupWindow_screening = createScreeningPopupWindow();
    }

    @Override
    public void onRefresh() {
        //检查是否处于刷新状态
        if (!isRefresh) {
            isRefresh = true;
            //模拟加载网络数据，这里设置4秒，正好能看到4色进度条
            mswipeLayout_nearby.postDelayed(new Runnable() {
                public void run() {
                    //显示或隐藏刷新进度条
                    mswipeLayout_nearby.setRefreshing(false);
                    //修改adapter的数据
//                    data.add("这是新添加的数据");
//                    mAdapter.notifyDataSetChanged();
                    isRefresh = false;
                }
            }, 4000);
        }
    }

    private int nums=5;
    @Override
    public void onLoad() {
        mswipeLayout_nearby.postDelayed(new Runnable() {
            public void run() {
                if(nums < 15) {
                    nums += 5;
                    adapter.update(nums);
                    // 更新完后调用该方法结束刷新
                    mswipeLayout_nearby.setLoading(false);
                }else{
                    mswipeLayout_nearby.setOnLoadComplete();
                }

            }
        }, 4000);
    }

}
