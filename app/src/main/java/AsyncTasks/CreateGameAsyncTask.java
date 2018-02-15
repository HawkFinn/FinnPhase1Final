package AsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import Client_Server_Communication.ClientFacade;
import Models.Client;
import Models.Game;
import Models.Request;
import Models.Result;

/**
 * Created by brianotte on 2/13/18.
 */

public class CreateGameAsyncTask extends AsyncTask<Request, Void, Result> {
    ClientFacade clientFacade = new ClientFacade();
    @Override
    protected Result doInBackground(Request... requests) {
        Result result = clientFacade.createGame(requests[0]);
        return result;
    }
    //onPostExecute updates the Client model:
    @Override
    protected void onPostExecute(Result result){
        if(result.getErrorMsg() == null) {
            Game game = new Game();
            game.setId(result.getGameId());
            game.addPlayer(Client.getInstance().getUserName());
            HashMap<String, Game> temp = Client.getInstance().getGameMap();
            temp.put(result.getGameId(), game);
            Client.getInstance().setGameMap(temp);
            Client.getInstance().createGame();
        }else {
            Client.getInstance().sendMessage(result.getErrorMsg());
        }
        System.out.println("Created a game - This is the asyncTask");
    }
}
