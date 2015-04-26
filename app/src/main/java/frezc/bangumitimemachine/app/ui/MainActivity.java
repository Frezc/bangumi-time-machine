package frezc.bangumitimemachine.app.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.entity.User;
import frezc.bangumitimemachine.app.network.http.BasicAuth;
import frezc.bangumitimemachine.app.network.http.NetWorkTool;
import frezc.bangumitimemachine.app.ui.dialog.LoginDialog;


public class MainActivity extends ActionBarActivity
    implements View.OnClickListener{
    private Toolbar toolbar;
    private ImageView photo;

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

        initView();
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        LoginDialog ld = new LoginDialog();
        ld.show(getSupportFragmentManager(), "dialog_login");
    }
}
