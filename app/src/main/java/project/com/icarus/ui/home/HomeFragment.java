package project.com.icarus.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import project.com.icarus.R;
import project.com.icarus.db.DatabaseHandler;

public class HomeFragment extends Fragment {
//    private OnLoginInteractionListener mListener;


    Button btnLogin,btnClear;
    EditText txtLogin;
    EditText txtPassword;
    private HomeFragment.OnLoginListener mListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        txtLogin = root.findViewById(R.id.txt_login);
        txtPassword = root.findViewById(R.id.txt_password);


        btnLogin = (Button) root.findViewById(R.id.btnLogin);
        btnClear = (Button) root.findViewById(R.id.btnClear);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);
                if (dbHandler.checkPassword(txtLogin.getText().toString(), txtPassword.getText().toString())) {
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_LONG).show();
                    if (mListener != null) {
                        boolean isAdmin = dbHandler.checkAdmin(txtLogin.getText().toString());
                        mListener.onLogin(txtLogin.getText().toString(),isAdmin);
                    }


                } else {
                    Toast.makeText(getContext(), "Login Failed. Please try again or contact your administrator", Toast.LENGTH_LONG).show();
                }


            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtLogin.setText("");
                txtPassword.setText("");
            }
        });
        return root;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (HomeFragment.OnLoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnLoginListener {
        void onLogin (String userName, boolean isAdmin);
    }



}
