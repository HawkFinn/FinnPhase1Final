package lobby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import Activities.R;
import game.GameFragment;
import Models.Game;
import Models.Client;


import java.util.ArrayList;
import java.util.Timer;


/**
 * Created by fjameson on 2/2/18.
 */

public class LobbyFragment extends Fragment {

//    Timer timer = new Timer();
//        timer.schedule(new Poller(), 0, 1000);

    /*public LobbyFragment()
    {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lobby_fragment, container, false);
        return view;
    }*/

    public GamesAdapter gamesAdapter;
    private RecyclerView mGamesRecView;
    public  Game createGame = new Game();
    TextView curName;
    TextView p1;
    TextView p2;
    TextView p3;
    TextView p4;
    TextView p5;
    Button join;
    Button start;
    Button done;
    Button create;
    public LinearLayout newGame;
    public EditText gameName;
    Game currentGame = null;
    ArrayList<Game> games = new ArrayList<>();
    TextView players[] = {p1, p2, p3, p4, p5};
    public View view;
    public boolean createUpdate= false;
    ArrayList<String> play;
    public LobbyFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lobby_fragment, container, false);
        final ArrayList<String>playernames = new ArrayList<String>();

        final Bundle acceptedUser = getArguments();
        playernames.add(acceptedUser.getString("username"));
        curName = (TextView) view.findViewById(R.id.yourName);
        curName.setText(acceptedUser.getString("username"));


        create= (Button)view.findViewById(R.id.create);
        p1 = (TextView) view.findViewById(R.id.p1);
        p2 = (TextView) view.findViewById(R.id.p2);
        p3 = (TextView) view.findViewById(R.id.p3);
        p4 = (TextView) view.findViewById(R.id.p4);
        p5 = (TextView) view.findViewById(R.id.p5);
        final LobbyPresentor lobbyP = new LobbyPresentor(getActivity());
        final  TextView numPlayers = (TextView) view.findViewById(R.id.numPlayers);
        numPlayers.setVisibility(View.GONE);
        join= (Button) view.findViewById(R.id.join);
        start= (Button) view.findViewById(R.id.start);
        done= (Button) view.findViewById(R.id.done);
        newGame = (LinearLayout) view.findViewById(R.id.newGame);
        gameName = (EditText) view.findViewById(R.id.gName);
        mGamesRecView = (RecyclerView) view.findViewById(R.id.game_list);
        mGamesRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final ArrayList<Game> games = Client.getInstance().getGameList();

        gamesAdapter = new GamesAdapter(games);
        mGamesRecView.setAdapter(gamesAdapter);
        if(createUpdate == true)
        {
           newGame.setVisibility(View.GONE);
           gameName.setText(null);
           gamesAdapter.addGametoView(currentGame);
           gamesAdapter.notifyDataSetChanged();
        }

        //starting to add people
        p1.setText(acceptedUser.getString("username"));
        p2.setText("loser");

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentGame != null) {
                    currentGame = lobbyP.joinGame(getActivity(), currentGame, acceptedUser.getString("username"));
                    if (currentGame.isJoinable()) {
                        start.setEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "you can't join a game that doesn't exist", Toast.LENGTH_LONG).show();
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentGame != null) {
                    lobbyP.startGame(getActivity(), currentGame);

                }
                else
                {
                    Toast.makeText(getActivity(), "you can't join a game that doesn't exist", Toast.LENGTH_LONG).show();
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"You're creating a game", Toast.LENGTH_SHORT).show();
                newGame.setVisibility(View.VISIBLE);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame.setVisibility(View.VISIBLE);
                if (gameName.getText().equals(null))
                {
                    Toast.makeText(getActivity(), "The game needs a name", Toast.LENGTH_SHORT).show();
                }
                else {
                    //question for Finn -
                    currentGame = lobbyP.createGame(getActivity(), play, gameName.getText().toString());
                    newGame.setVisibility(View.GONE);
                    gameName.setText(null);
                    gamesAdapter.addGametoView(currentGame);
                    gamesAdapter.notifyDataSetChanged();
                }
            }
        });
        if(createUpdate == true)
        {
            newGame.setVisibility(View.GONE);
            gameName.setText(null);
            gamesAdapter.addGametoView(currentGame);
            gamesAdapter.notifyDataSetChanged();
        }
        return view;
    }

    private class GamesHolder extends RecyclerView.ViewHolder {

        private TextView gameName;
        private Game mGame;

        public GamesHolder(View itemView) {
            super(itemView);

            gameName = (TextView) itemView.findViewById(R.id.game_name);

            gameName.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    //This needs to be adjusted in the client model;
                    ArrayList<Game> tempGlist = Client.getInstance().getGameList();
                    int gListSize = tempGlist.size();

                    for (int i = 0; i <gListSize; i++) {

                        if (tempGlist.get(i).equals(gameName.getText().toString())) {
                            currentGame = tempGlist.get(i);
                        }

                    }

                    for (int i = 0; i < players.length; i++) {
                        players[i].setText((CharSequence) currentGame.getPlayers().get(i));

                        if (!players[i].getText().equals("")) {
                            players[i].setVisibility(View.VISIBLE);
                        }
                        else players[i].setVisibility(View.GONE);
                    }




                }
            });
        }

        public void bindGame(Game game) {
            mGame = game;
            gameName.setText(mGame.getId());

        }
    }


   public class GamesAdapter extends RecyclerView.Adapter<GamesHolder> {

        private ArrayList<Game> games;

        public GamesAdapter(ArrayList<Game> g) {
            if (games == null)
            {
                Game game = new Game();
                games = new ArrayList<Game>();
                games.add(game);
            }
            else
            {games = g; }}

        @Override
        public GamesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_item_games, parent, false);
            return new GamesHolder(view);
        }

        public void addGametoView(Game game)
        {
            games.add(game);
        }

        @Override
        public void onBindViewHolder(GamesHolder holder, int position) {

            Game game = games.get(position);
            holder.bindGame(game);
        }

        @Override
        public int getItemCount() {
            return games.size();
        }
    }



}
