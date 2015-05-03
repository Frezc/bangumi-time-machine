package frezc.bangumitimemachine.app.ui;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import frezc.bangumitimemachine.app.MyApplication;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.ui.customview.DivisorView;
import frezc.bangumitimemachine.app.ui.customview.SubheaderView;
import frezc.bangumitimemachine.app.ui.dialog.LoginDialog;
import frezc.bangumitimemachine.app.ui.drawer.Section;
import frezc.bangumitimemachine.app.ui.fragment.CalendarFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
    implements View.OnClickListener, Section.OnSelectListener{
    private Toolbar toolbar;
    //头像
    private ImageView photo;
    //侧边栏列表
    private LinearLayout sectionContainter;
    private MyApplication app;
    private DrawerLayout drawerLayout;
    private FrameLayout mainContainer;

    private List<Section> sectionList = new ArrayList<Section>();
    private Section selectSection = null;
    private int sectionOrder;

    private Fragment contentFragment;
    private FragmentManager fragmentManager;
    private CalendarFragment calendarFragment;

    /**
     * toolbar的菜单点击事件
     */
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = null;
            switch (menuItem.getItemId()) {
                case R.id.action_edit:
                    msg = "Click edit";
                    break;
                case R.id.action_settings:
                    msg = "Click setting";
                    break;
            }

            if(msg != null && !msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (MyApplication) getApplication();
        initView();
        initSections();
        initFragment(savedInstanceState==null);

    }

    private void initFragment(Boolean isInit) {
        fragmentManager = getSupportFragmentManager();
        if(isInit) {
            calendarFragment = CalendarFragment.newInstance(this);
            contentFragment = calendarFragment;
            fragmentManager.beginTransaction()
                    .add(R.id.main_container, contentFragment, UIParams.FRAGMENT_CALENDAR)
                    .commit();
        }else {
//            contentFragment = fragmentManager.findFragmentById(R.id.main_container);
            calendarFragment = (CalendarFragment) fragmentManager.findFragmentByTag(UIParams.FRAGMENT_CALENDAR);

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
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        photo = (ImageView) findViewById(R.id.user_photo);
        photo.setOnClickListener(this);

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

        Section animeSection = new Section(this, Section.TYPE_SECTION, "我的动画", UIParams.PAGE_ANIME);
        animeSection.setColorSelectedSection(0xffF09199);
        animeSection.setOnSelectListener(this);


        //...
        sectionList.add(homeSection);
        sectionList.add(searcherSection);
        sectionList.add(animeSection);

        //add sections
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
                if(app.isUserLogin()) {

                }else {
                    LoginDialog ld = new LoginDialog();
                    ld.show(getSupportFragmentManager(), "dialog_login");
                }
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
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
            case UIParams.PAGE_ANIME:
                Toast.makeText(this,"我的动画", Toast.LENGTH_SHORT).show();
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

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void switchContent(Fragment from, Fragment to){
        if(contentFragment != to){
            contentFragment = to;
            //添加动画
            FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right);
            if(to.isAdded()){
                transaction.hide(from).show(to);
            }else {
                transaction.hide(from).add(R.id.main_container, to);
            }
            transaction.commit();
        }
    }
}
