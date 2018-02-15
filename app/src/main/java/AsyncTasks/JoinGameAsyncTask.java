package AsyncTasks;

import android.os.AsyncTask;

import Client_Server_Communication.ClientFacade;
import Models.Client;
import Models.Game;
import Models.Request;
import Models.Result;

/**
 * Created by brianotte on 2/13/18.
 */

public class JoinGameAsyncTask extends AsyncTask<Request, Void, Result> {
    ClientFacade clientFacade = new ClientFacade();
    @Override
    protected Result doInBackground(Request... requests) {
        Result result = clientFacade.createGame(requests[0]);
        return result;
    }
    //onPostExecute updates the Client model:
    @Override
    protected void onPostExecute(Result result){
        System.out.println("Joined a game - This is the asyncTask");
        Game game = Client.getInstance().getGameMap().get(result.getGameId());
    }
}
