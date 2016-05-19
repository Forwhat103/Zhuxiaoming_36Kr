package com.zhuxiaoming.kr36.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.find.FindFragment;
import com.zhuxiaoming.kr36.invest.InvestFragment;
import com.zhuxiaoming.kr36.mine.MineFragment;
import com.zhuxiaoming.kr36.news.NewsFragment;
import com.zhuxiaoming.kr36.news.all.NewsAllFragment;
import com.zhuxiaoming.kr36.news.earlyitem.EarlyItemFragment;
import com.zhuxiaoming.kr36.news.krtv.KrTvFragment;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是主页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {
    private ViewPager mainVp;// 创建ViewPager对象
    private TabLayout mainTab;// 创建TabLayout对象
    private List<Fragment> fragments;// 创建碎片集合的对象
    private MainAdapter mainAdapter;// 创建主页适配器的对象
    private DrawerLayout mainDl;// 设置抽屉DrawerLayout对象
    private NavigationView mainNv;// 创建侧滑导航对象
    private ImageView backIv;// 抽屉返回按钮
    private View navigationHeadView;// 抽屉的头视图
    private DrawerBroadcast drawerBroadcast;// 打开抽屉的广播


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mainVp = bindView(R.id.main_vp);
        mainTab = bindView(R.id.main_tab);
        mainDl = bindView(R.id.main_dl);
        mainNv = bindView(R.id.main_nv);
        navigationHeadView = LayoutInflater.from(this).inflate(R.layout.navigation_header, null);
        backIv = (ImageView) navigationHeadView.findViewById(R.id.navigation_header_back_iv);
        navigationHeadView = LayoutInflater.from(this).inflate(R.layout.navigation_header, null);
        View titleBarView = LayoutInflater.from(this).inflate(R.layout.tool_bar_news, null);
    }

    @Override
    protected void initData() {
//        setSupportActionBar(mainTb);// 设置标题栏Toolbar
        initFragment();// 添加Fragment数据
        mainAdapter = new MainAdapter(getSupportFragmentManager());//初始化适配器
        mainAdapter.setFragments(fragments);// 将Fragment数据添加到适配器
        mainVp.setAdapter(mainAdapter);// 设置ViewPager的适配器
        mainTab.setupWithViewPager(mainVp);// 设置TabLayout的适配器
        initTabs();// 添加Tab数据
        mainVp.addOnPageChangeListener(this);
        mainVp.setOffscreenPageLimit(3);// 参数:缓存当前界面每一侧的界面数
        // 注册广播
        drawerBroadcast = new DrawerBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zhuxiaoming.kr36.DRAWER_BC");
        registerReceiver(drawerBroadcast, filter);

        backIv.setOnClickListener(this);
//        mainNv.addHeaderView(navigationHeadView);
        // 设置侧滑导航栏的点击事件
        mainNv.setNavigationItemSelectedListener(this);
        mainNv.setItemIconTintList(null);// 设置抽屉菜单图标恢复本来的颜色
    }


    // 添加Fragment数据
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new InvestFragment());
        fragments.add(new FindFragment());
        fragments.add(new MineFragment());
    }

    // 设置侧滑导航栏的点击事件
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Bundle urlArg = new Bundle();
        EarlyItemFragment fragment = new EarlyItemFragment();
        switch (item.getItemId()) {
            case R.id.navigation_header_item_back:
                // 点击返回
                mainDl.closeDrawers();// 关闭抽屉
                break;
            case R.id.navigation_header_item_all:
                // 点击全部item
                mainDl.closeDrawers();// 关闭抽屉
                getSupportFragmentManager().beginTransaction().replace(R.id.news_frame, new NewsAllFragment()).commit();
                EventBus.getDefault().post(new String("新闻"));
                break;
            case R.id.navigation_header_item_early:// 早期项目
                urlArg.putInt("urlId",67);
                fragment.setArguments(urlArg);
                getSupportFragmentManager().beginTransaction().replace(R.id.news_frame, fragment).commit();
                EventBus.getDefault().post(new String("早期项目"));
                break;
            case R.id.navigation_header_item_after_b_wheel:// B轮后
                urlArg.putInt("urlId",68);
                fragment.setArguments(urlArg);
                getSupportFragmentManager().beginTransaction().replace(R.id.news_frame, fragment).commit();
                EventBus.getDefault().post(new String("B轮后"));
                break;
            case R.id.navigation_header_item_big_company:// 大公司
                urlArg.putInt("urlId",23);
                fragment.setArguments(urlArg);
                getSupportFragmentManager().beginTransaction().replace(R.id.news_frame, fragment).commit();
                EventBus.getDefault().post(new String("大公司"));
                break;
            case R.id.navigation_header_item_capital:// 资本
                urlArg.putInt("urlId",69);
                fragment.setArguments(urlArg);
                getSupportFragmentManager().beginTransaction().replace(R.id.news_frame, fragment).commit();
                EventBus.getDefault().post(new String("资本"));
                break;
            case R.id.navigation_header_item_depth:// 深度
                urlArg.putInt("urlId",70);
                fragment.setArguments(urlArg);
                getSupportFragmentManager().beginTransaction().replace(R.id.news_frame, fragment).commit();
                EventBus.getDefault().post(new String("深度"));
                break;
            case R.id.navigation_header_item_research:// 研究
                urlArg.putInt("urlId",71);
                fragment.setArguments(urlArg);
                getSupportFragmentManager().beginTransaction().replace(R.id.news_frame, fragment).commit();
                EventBus.getDefault().post(new String("研究"));
                break;
            case R.id.navigation_header_item_tv:// 氪TV
                getSupportFragmentManager().beginTransaction().replace(R.id.news_frame, new KrTvFragment()).commit();
                EventBus.getDefault().post(new String("氪TV"));
                break;
        }
        item.setChecked(false);//点击了把它设为不选中状态
        mainDl.closeDrawers();// 关闭抽屉
        return true;
    }

    // 添加TabLayout的Tab数据
    private void initTabs() {
        int[] tabs = {R.drawable.selector_news, R.drawable.selector_invest, R.drawable.selector_find, R.drawable.selector_mine};
        for (int i = 0; i < tabs.length; i++) {
            mainTab.getTabAt(i).setIcon(tabs[i]);
        }
    }

    // 监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation_header_back_iv:
                // 返回到主界面
                mainDl.closeDrawers();// 关闭侧滑
                break;
        }
    }

    // ViewPager的状态改变监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //这个方法会在屏幕滚动过程中不断被调用
    }

    @Override
    public void onPageSelected(int position) {
        //这个方法有一个参数position，代表哪个页面被选中。当用手指滑动翻页的时候，
        // 如果翻动成功了（滑动的距离够长），手指抬起来就会立即执行这个方法，position就是当前滑动到的页面。
        // 如果直接setCurrentItem翻页，那position就和setCurrentItem的参数一致，这种情况在onPageScrolled执行方法前就会立即执行
        switch (position) {
            case 0:
                mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case 1:
            case 2:
            case 3:
                mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 这个方法在手指操作屏幕的时候发生变化
    }

    // 定义广播
    class DrawerBroadcast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收到广播时打开抽屉
            mainDl.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(drawerBroadcast);
    }
}
