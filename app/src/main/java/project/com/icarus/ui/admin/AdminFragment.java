package project.com.icarus.ui.admin;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import project.com.icarus.R;
import project.com.icarus.db.DatabaseHandler;
import project.com.icarus.db.Location;
import project.com.icarus.db.User;

public class AdminFragment extends Fragment {


    Button btnAddUser,btnAddLocation;
    Button btnUserConfirm, btnUserCancel;
    Button btnLocConfirm, btnLocCancel;
    Spinner spnUserRole;
    EditText txtUser, txtPassword, txtPasswordConfirm;
    EditText txtLoc, txtLocDesc;


    String[] Roles = {
        "Standard","Administrator"
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin, container, false);

        btnAddUser = (Button) root.findViewById(R.id.btnAddUser);
        btnAddLocation = (Button) root.findViewById(R.id.btnAddLocation);
        btnAddUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window

                View popupView = inflater.inflate(R.layout.fragment_add_user, null);

                // create a popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                btnUserConfirm = (Button) popupView.findViewById(R.id.btnUserConfirm);
                btnUserCancel = (Button) popupView.findViewById(R.id.btnUserCancel);
                spnUserRole = (Spinner) popupView.findViewById(R.id.dropdown_role);
                txtUser = (EditText)  popupView.findViewById(R.id.txt_login);
                txtPassword = (EditText)  popupView.findViewById(R.id.txt_password);
                txtPasswordConfirm  =(EditText)  popupView.findViewById(R.id.txt_confirm_password);
                        //txt_login txt_password txt_confirm_password

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Roles);
                spnUserRole.setAdapter(adapter);

                btnUserConfirm.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {

                        if (!txtUser.getText().equals("")){
                            if(txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString())){
                                DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);

                                String role = Roles[spnUserRole.getSelectedItemPosition()];
                                User user = new User(txtUser.getText().toString(),role,"", 0);
                                user.setPassword(txtPassword.getText().toString());
                                boolean result = dbHandler.addUser(user);
                                if(result){
                                    Toast.makeText(getContext(), "User added successfully", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getContext(), "User creation failed. Please contact your administrator.", Toast.LENGTH_LONG).show();
                                }
                                popupWindow.dismiss();
                            }
                            else{
                                Toast.makeText(getContext(), "Passwords do not match, Please try again.", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
                btnUserCancel.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {
                        popupWindow.dismiss();
                    }
                });



            }
        });
        btnAddLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window

                View popupView = inflater.inflate(R.layout.fragment_add_location, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                btnLocConfirm = (Button) popupView.findViewById(R.id.btnLocConfirm);
                btnLocCancel = (Button) popupView.findViewById(R.id.btnLocCancel);

                txtLoc = (EditText)  popupView.findViewById(R.id.txt_loc_name);
                txtLocDesc = (EditText)  popupView.findViewById(R.id.txt_loc_desc);


                btnLocConfirm.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {

                        if (!txtLoc.getText().toString().equals("")) {
                            DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);


                            Location location = new Location(txtLoc.getText().toString(), txtLocDesc.getText().toString(), "", 0);

                            boolean result = dbHandler.addLocation(location);
                            if (result) {
                                Toast.makeText(getContext(), "Location added successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Location creation failed. Please contact your administrator.", Toast.LENGTH_LONG).show();
                            }
                            popupWindow.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Please enter a valid Location name.", Toast.LENGTH_LONG).show();
                        }
                    }

                });
                btnLocCancel.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {
                        popupWindow.dismiss();
                    }
                });



            }
        });
        return root;
    }

}