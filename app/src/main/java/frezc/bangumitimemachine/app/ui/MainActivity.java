package frezc.bangumitimemachine.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import frezc.bangumitimemachine.app.MyApplication;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.ui.customview.DivisorView;
import frezc.bangumitimemachine.app.ui.customview.SubheaderView;
import frezc.bangumitimemachine.app.ui.dialog.LoginDialog;
import frezc.bangumitimemachine.app.ui.drawer.Section;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
    implements View.OnClickListener, Section.OnClickListener{
    private Toolbar toolbar;
    private ImageView photo;
    private LinearLayout sectionContainter;
    private MyApplication app;

    private List<Section> sectionList = new ArrayList<Section>();
    private int sectionOrder;

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

        LinearLayout linearLayout = new LinearLayout(this);
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

    }

    /**
     * init drawer list
     */
    private void initSections() {
        sectionList.clear();
        sectionOrder = 0;
        Section searcherSection = new Section(this, Section.TYPE_SECTION_ICON,
                "搜索", R.mipmap.ic_search_grey600_24dp, UIParams.PAGE_SEARCHER);
        searcherSection.setOnClickListener(this);

        Section animeSection = new Section(this, Section.TYPE_SECTION, "我的动画", UIParams.PAGE_ANIME);
        animeSection.setOnClickListener(this);


        //...
        sectionList.add(searcherSection);
        sectionList.add(animeSection);

        //add sections
        addToDrawer(Section.TYPE_SECTION, null);
        addToDrawer(Section.TYPE_DIVISOR, null);
        addToDrawer(Section.TYPE_SECTION, null);
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
    }

    @Override
    public void onClick(Section section) {
        switch (section.getTag()){
            case UIParams.PAGE_ANIME:

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

                break;
        }
    }
}
