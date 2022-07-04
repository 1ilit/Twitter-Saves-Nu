import twitter4j.*;
import java.util.List;
import java.util.Timer;

public class Main {
    static Status mTweet;
    static Maze map=new Maze(5);
    static Twitter t=new TwitterFactory().getInstance();

    /**
     * @return the last tweet (the updated maze)
     */
    public static Status getLatestTweet() {
        List<Status> list = null;
        try {
            list = t.getUserTimeline("@daywa1kr");
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return list.get(0);
    }

    public static void main(String[] args){
       Timer timer=new Timer();
       timer.scheduleAtFixedRate(new Update(), 0, 900000); //updates every 15 minutes
    }
}
