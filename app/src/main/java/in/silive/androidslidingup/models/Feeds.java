package in.silive.androidslidingup.models;

import java.util.ArrayList;

/**
 * Created by kone on 14/9/15.
 */
public class Feeds {
public String name;
public Feeds(String name){
    this.name=name;
}

    public String getName() {
        return name;
    }
public static ArrayList<Feeds> createFeedList(int numofFeeds){
    ArrayList<Feeds> feedlist= new ArrayList<>();

    for (int i = 1; i <= numofFeeds; i++) {
        feedlist.add(new Feeds("Person " + i));
    }

    return feedlist;
    }
}
