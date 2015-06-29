package frezc.bangumitimemachine.app.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import frezc.bangumitimemachine.app.R;
import frezc.bangumitimemachine.app.entity.User;
import frezc.bangumitimemachine.app.network.http.BasicAuth;
import frezc.bangumitimemachine.app.network.http.NetWorkTool;

/**
 * Created by freeze on 2015/4/26.
 */
public class LoginDialog extends DialogFragment
    implements View.OnClickListener, Response.Listener<User>
                ,Response.ErrorListener{
    private EditText etEmail,etPassword;
    private TextView tvLoginFail;
    private Button loginButton;
    private ProgressBar loginProgress;
    private CheckBox autoLogin;

    private NetWorkTool netWorkTool;
    private BasicAuth basicAuth;

    private String email;
    private String password;


    private OnLoginSuccessListener onLoginSuccessListener;

    public void setOnLoginSuccessListener(OnLoginSuccessListener onLoginSuccessListener) {
        this.onLoginSuccessListener = onLoginSuccessListener;
    }

    public static LoginDialog newInstance(@Nullable String email, boolean isAuto,
                                          OnLoginSuccessListener onLoginSuccessListener){
        LoginDialog loginDialog = new LoginDialog();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putBoolean("isAuto",isAuto);
        loginDialog.setArguments(bundle);
        loginDialog.setOnLoginSuccessListener(onLoginSuccessListener);
        return loginDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE,0);
        netWorkTool = NetWorkTool.getInstance(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_login,container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        etEmail = (EditText) v.findViewById(R.id.login_email);
        etPassword = (EditText) v.findViewById(R.id.login_password);
        etEmail.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        tvLoginFail = (TextView) v.findViewById(R.id.login_fail);
        loginButton = (Button) v.findViewById(R.id.login_comfirm);
        loginButton.setOnClickListener(this);
        loginProgress = (ProgressBar) v.findViewById(R.id.login_wait);
        autoLogin = (CheckBox) v.findViewById(R.id.login_auto);
        
        Bundle bundle = getArguments();
        if(bundle != null){
            etEmail.setText(bundle.getString("email"));
            autoLogin.setChecked(bundle.getBoolean("isAuto"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_comfirm:
                if(!checkAvailable()){
                    setError("用户名或密码格式不正确");
                    return;
                }

                if(basicAuth == null){
                    basicAuth = new BasicAuth(email,password, this, this);
                }else {
                    basicAuth.setUsernameAndPassword(email,password);
                }

                basicAuth.sendRequest(netWorkTool);

                setWait();
                break;
        }
    }

    //进入等待状态
    private void setWait() {
        loginButton.setVisibility(View.INVISIBLE);
        loginProgress.setVisibility(View.VISIBLE);
    }

    //本地简单检查合法性
    private boolean checkAvailable() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if(email == null || password == null || email.isEmpty() || password.length()<8){
            return false;
        }else {
            return true;
        }
    }

    //登录成功
    @Override
    public void onResponse(User loginUser) {
        if(loginUser.getAuth() == null) {
            setError("用户名或密码错误");
        }else {
            Toast.makeText(getActivity(), "登录成功 " + loginUser.getNickname(), Toast.LENGTH_SHORT).show();

            if(onLoginSuccessListener != null){
                onLoginSuccessListener.onLogin(loginUser, autoLogin.isChecked());
            }
            dismiss();
        }
    }

    //登录失败
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        setError("网络错误");
    }

    private void setError(String errorMsg) {
        loginProgress.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        loginButton.setText("重试");
        tvLoginFail.setVisibility(View.VISIBLE);
        tvLoginFail.setText(errorMsg);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(basicAuth != null) {
            basicAuth.cancel();
        }
        super.onDismiss(dialog);
    }

    public interface OnLoginSuccessListener{
        void onLogin(User user, boolean isSave);
    }
}
