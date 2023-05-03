import twitter4j.TwitterException;
import java.util.TimerTask;

public class Update extends TimerTask{
    @Override
    public void run() {
        char facing=Main.map.getFacingDir();
        String sign="";
        switch(facing){
            case 'N':
                sign="⬆️";
                break;
            case 'S':
                sign="⬇️";
                break;
            case 'W':
                sign="⬅️";
                break;
            case 'E':
                sign="➡️";
                break;
        }

        try{
            Main.mTweet=Main.t.updateStatus("Current direction:"+sign+"\n"+Main.map.toString());
        }catch(TwitterException e){
            System.out.println("u dum, boink");
            e.printStackTrace(); //exception will be caught when the map is the same,
                                 // e.g. Nu is stuck and needs to change the direction
        }

        char dir;
        if(Main.getLatestTweet().getFavoriteCount()>Main.getLatestTweet().getRetweetCount()){
            dir='l'; //to move counter-clockwise
        }
        else if(Main.getLatestTweet().getFavoriteCount()<Main.getLatestTweet().getRetweetCount()){
            dir='r'; //to move clockwise
        }
        else{
            dir='s'; //to move in the direction Nu is already facing
        }

        Main.map.update(dir); //update Nu's position
    }
}
