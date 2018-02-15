package Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import Models.Game;
import Models.Request;
import registerLogin.LoginFragment;
import lobby.LobbyFragment;
import game.GameFragment;
import registerLogin.LoginRegisterPresentor;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/**
 * Created by fjameson on 2/9/18.
 */

public class MainActivity extends AppCompatActivity {
    private Button button;
    public final static String openMap= "true";
    public LobbyFragment  Lobbyfragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getStringExtra(openMap) != null) {
            // open game
        } else {
            LoginFragment fragment = new LoginFragment();
            FragmentManager headfrag = getSupportFragmentManager();
            headfrag.beginTransaction().replace(R.id.activity_main, fragment).commit();
        }
    }

    public void switchToLobby(Request r)
    {
        if (r.getAuthToken().equals("ERROR: Invalid Registration"))
        {
            Toast.makeText(getBaseContext(), "ERROR: Invalid Registration", Toast.LENGTH_SHORT).show();
        }
        else{
        FragmentManager headfrag = getSupportFragmentManager();
        Lobbyfragment = new LobbyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", r.getUsername());
        bundle.putString("password", r.getPassword());
        bundle.putString("authToken", r.getAuthToken());
        Lobbyfragment.setArguments(bundle);
        headfrag.beginTransaction().replace(R.id.activity_main, Lobbyfragment).commit();}
    }

    public void openGame()
    {
        FragmentManager headfrag = getSupportFragmentManager();
        Fragment fragment = new GameFragment();
        headfrag.beginTransaction().replace(R.id.activity_main, fragment).commit();
    }

    public void updateCreate(Game currentGame)
    {
        //currentGame = lobbyP.createGame(getActivity(), play, gameName.getText().toString());
        Lobbyfragment.createGame = currentGame;
        Lobbyfragment.createUpdate= true;
    }

    public void updateJoin (Game currentGame)
    {
        //join code there wasn't really anything being used
    }


}
