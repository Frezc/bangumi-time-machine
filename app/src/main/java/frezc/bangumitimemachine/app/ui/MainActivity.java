package frezc.bangumitimemachine.app.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.toolbox.ImageLoader;
import frezc.bangumitimemachine.app.MyApplication;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.db.DB;
import frezc.bangumitimemachine.app.entity.BaseAuth;
import frezc.bangumitimemachine.app.entity.LoginUser;
import frezc.bangumitimemachine.app.network.http.BasicAuth;
import frezc.bangumitimemachine.app.network.http.NetWorkTool;
import frezc.bangumitimemachine.app.ui.customview.DivisorView;
import frezc.bangumitimemachine.app.ui.customview.SubheaderView;
import frezc.bangumitimemachine.app.ui.dialog.LoginDialog;
import frezc.bangumitimemachine.app.ui.drawer.Section;
import frezc.bangumitimemachine.app.ui.fragment.CalendarFragment;
import frezc.bangumitimemachine.app.ui.fragment.NetFragment;
import frezc.bangumitimemachine.app.ui.fragment.WatchingListFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
    implements View.OnClickListener, Section.OnSelectListener, Toolbar.OnMenuItemClickListener,
        LoginDialog.OnLoginSuccessListener{
    private Toolbar toolbar;
    //头像
    private ImageView photo;
    private TextView username;
    private TextView sign;
    //头像图片请求
    private ImageLoader.ImageContainer photoRequest;
    //侧边栏列表
    private LinearLayout sectionContainter;
//    private MyApplication app;
    private DrawerLayout drawerLayout;
    private FrameLayout mainContainer;

    private List<Section> sectionList = new ArrayList<Section>();
    private Section selectSection = null;
    private int sectionOrder;

    public static final String NOWFRAGMENT = "nowFragment";
    private NetFragment contentFragment;
    private FragmentManager fragmentManager;
    private CalendarFragment calendarFragment;
    private WatchingListFragment watchingListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        app = (MyApplication) getApplication();

        initView();
        initSections();
        initFragment(savedInstanceState);
        autoLogin();
    }

    private void autoLogin() {
        BaseAuth auth = new DB(this).getAuth();
        if(auth != null){
            LoginDialog ld = LoginDialog.newInstance(auth);
            ld.setOnLoginSuccessListener(this);
            ld.show(getSupportFragmentManager(), "dialog_login");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NOWFRAGMENT, contentFragment.getTag());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "横屏", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "竖屏", Toast.LENGTH_SHORT).show();
        }
    }

    private void initFragment(Bundle save) {
        fragmentManager = getSupportFragmentManager();
        if(save == null) {
            calendarFragment = CalendarFragment.newInstance(this);
            watchingListFragment = WatchingListFragment.newInstance(this);
            contentFragment = calendarFragment;
            fragmentManager.beginTransaction()
                    .add(R.id.main_container, contentFragment, UIParams.FRAGMENT_CALENDAR)
                    .commit();
        }else {

            calendarFragment = (CalendarFragment) fragmentManager.findFragmentByTag(UIParams.FRAGMENT_CALENDAR);
            watchingListFragment = (WatchingListFragment) fragmentManager.findFragmentByTag(UIParams.FRAGMENT_WATCHING);
            contentFragment = (NetFragment) fragmentManager.findFragmentByTag(save.getString(NOWFRAGMENT));

            fragmentManager.beginTransaction()
                    .show(calendarFragment)
                    .commit();
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("subtitle");
        setSupportActionBar(toolbar);
        //setNavigationIcon需要放在 setSupportActionBar之后才会生效
        toolbar.setNavigationIcon(R.drawable.abc_ic_menu_share_mtrl_alpha);
        toolbar.setOnMenuItemClickListener(this);

        photo = (ImageView) findViewById(R.id.user_photo);
        photo.setOnClickListener(this);

        username = (TextView) findViewById(R.id.user_name);
        sign = (TextView) findViewById(R.id.user_sign);

        sectionContainter = (LinearLayout) findViewById(R.id.section_container);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        mainContainer = (FrameLayout) findViewById(R.id.main_container);

    }

    /**
     * init drawer list
     */
    private void initSections() {
        sectionList.clear();
        sectionOrder = 0;
        Section homeSection = new Section(this, Section.TYPE_SECTION_ICON,
                "主页", R.mipmap.ic_home_grey600_24dp, UIParams.PAGE_HOME);
        homeSection.setColorSelectedSection(0xffF09199);
        homeSection.setOnSelectListener(this);

        Section searcherSection = new Section(this, Section.TYPE_SECTION_ICON,
                "搜索", R.mipmap.ic_search_grey600_24dp, UIParams.PAGE_SEARCHER);
        searcherSection.setColorSelectedSection(0xffF09199);
        searcherSection.setOnSelectListener(this);

        Section notificationSection = new Section(this, Section.TYPE_SECTION_ICON,
                "消息", R.mipmap.ic_announcement_grey600_24dp, 0);
        notificationSection.setNotification(10);
        searcherSection.setColorSelectedSection(0xffF09199);
        notificationSection.setOnSelectListener(this);

        Section animeSection = new Section(this, Section.TYPE_SECTION, "补番列表", UIParams.PAGE_WATCHLIST);
        animeSection.setColorSelectedSection(0xffF09199);
        animeSection.setOnSelectListener(this);


        //...
        sectionList.add(homeSection);
        sectionList.add(searcherSection);
        sectionList.add(notificationSection);
        sectionList.add(animeSection);

        //add sections
        addToDrawer(Section.TYPE_SECTION, null);
        addToDrawer(Section.TYPE_SECTION, null);
        addToDrawer(Section.TYPE_SECTION, null);
        addToDrawer(Section.TYPE_DIVISOR, null);
        addToDrawer(Section.TYPE_SECTION, null);

        //初始选中section
        homeSection.select();
        selectSection = homeSection;
    }

    /**
     * add section or divisor or subheader to drawer
     * @param type TYPE_SECTION TYPE_DIVISOR TYPE_SUBHEADER
     * @param title only be used when type is TYPE_SUBHEADER
     */
    private void addToDrawer(int type, @Nullable String title){
        switch (type){
            case Section.TYPE_SECTION:
                if(sectionOrder < sectionList.size()){
                    LinearLayout.LayoutParams params =
                            new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * UIParams.density));
                    sectionContainter.addView(sectionList.get(sectionOrder++).getView(),params);
                }else {
                    throw new IndexOutOfBoundsException("There is no more sections");
                }
                break;

            case Section.TYPE_DIVISOR:
                View view = new DivisorView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,1);
                params.setMargins(0,(int)(8 * UIParams.density), 0, (int)(8 * UIParams.density));
                sectionContainter.addView(view, params);
                break;

            case Section.TYPE_SUBHEADER:
                if(title == null){
                    title = "Default";
                }
                SubheaderView subheader = new SubheaderView(this, title);
                sectionContainter.addView(subheader);
                break;

            default:
                throw new IllegalArgumentException("No such Type");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_photo:
                if(MyApplication.isUserLogin()) {

                }else {
                    LoginDialog ld = LoginDialog.newInstance(null);
                    ld.setOnLoginSuccessListener(this);
                    ld.show(getSupportFragmentManager(), "dialog_login");
                }
                break;
        }
    }

    @Override
    public void onSelect(Section section) {
        if(selectSection == section){
            return;
        }
        selectSection.unselect();
        selectSection = section;
        section.select();

        switch (section.getTag()){
            case UIParams.PAGE_WATCHLIST:
                Toast.makeText(this,"补番列表", Toast.LENGTH_SHORT).show();
                switchContent(contentFragment, watchingListFragment, UIParams.FRAGMENT_WATCHING);
                contentFragment = watchingListFragment;
                break;

            case UIParams.PAGE_BOOK:

                break;

            case UIParams.PAGE_MUSIC:

                break;

            case UIParams.PAGE_GAME:

                break;

            case UIParams.PAGE_REALITY:

                break;

            case UIParams.PAGE_SEARCHER:
                Toast.makeText(this,"搜索", Toast.LENGTH_SHORT).show();
                break;

            case UIParams.PAGE_HOME:
                switchContent(contentFragment, calendarFragment, UIParams.FRAGMENT_CALENDAR);
                contentFragment = calendarFragment;
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * 切换fragment 不过不太好设置tag
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to, String tag){
        if(contentFragment != to){
            contentFragment = (NetFragment) to;
            //添加动画
            FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right);
            if(to.isAdded()){
                transaction.hide(from).show(to);
            }else {
                transaction.hide(from).add(R.id.main_container, to, tag);
            }
            transaction.commit();
        }
    }

    /**
     * toolbar的菜单点击事件
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String msg = null;
        switch (item.getItemId()) {
            case R.id.action_edit:
                msg = "Click edit";
                break;
            case R.id.action_settings:
                msg = "Click setting";
                break;
            case R.id.action_refresh:
                contentFragment.onRefresh();
                msg = "refresh!";
                break;
        }

        if(msg != null && !msg.equals("")) {
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onLogin(LoginUser user) {
        username.setText(user.getNickname());
        if(user.getSign().isEmpty()){
            sign.setText(R.string.default_sign);
        }else {
            sign.setText(user.getSign());
        }
        photoRequest =  NetWorkTool.getInstance(this).loadImage(user.getAvatar().small,
                ImageLoader.getImageListener(photo,R.mipmap.ico_ios,R.mipmap.icon));
        //重置fragment
        if(watchingListFragment != null){
            watchingListFragment.setResetFlag(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(photoRequest != null){
            photoRequest.cancelRequest();
        }
    }
}
