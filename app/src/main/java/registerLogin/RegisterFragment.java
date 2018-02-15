package registerLogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Activities.R;
import Client_Server_Communication.Poller;
import Models.Result;
import lobby.LobbyFragment;


/**
 * Created by fjameson on 2/2/18.
 */
 public class RegisterFragment extends Fragment {
    EditText username;
    EditText password;
    EditText confpswd;
    Button register;
    TextView backToLogin;
    LoginRegisterPresentor l;

    public RegisterFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        l = new LoginRegisterPresentor(getContext());
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        username= (EditText)view.findViewById(R.id.editText_username1);
        password= (EditText)view.findViewById(R.id.editText2_password1);
        confpswd= (EditText)view.findViewById(R.id.editText2_passwordconf1);
        register= (Button)view.findViewById(R.id.button2_register1);
        backToLogin= (TextView) view.findViewById(R.id.login1);

       register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Result r=  l.register(getActivity(), username.getText().toString(), password.getText().toString(), confpswd.getText().toString());
//                if(r!= null)
//                {
//                    FragmentManager headfrag = getActivity().getSupportFragmentManager();
//                    Fragment fragment = new LobbyFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("username", username.getText().toString());
//                    bundle.putString("password", password.getText().toString());
//                    bundle.putString("authToken", r.getAuthToken());
//                    fragment.setArguments(bundle);
//                    headfrag.beginTransaction().replace(R.id.activity_main, fragment).commit();
//                }

            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // l.switchToRegister(getActivity());
                LoginFragment fragment = new LoginFragment();
                FragmentManager headfrag = getActivity().getSupportFragmentManager();
                headfrag.beginTransaction().replace(R.id.activity_main, fragment).commit();
            }
        });

        return view;
    }

    public void switchToLobby()
    {
        FragmentManager headfrag = getActivity().getSupportFragmentManager();
        Fragment fragment = new LobbyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", username.getText().toString());
        bundle.putString("password", password.getText().toString());
        //bundle.putString("authToken", r.getAuthToken());
        fragment.setArguments(bundle);
        headfrag.beginTransaction().replace(R.id.activity_main, fragment).commit();
    }


}

