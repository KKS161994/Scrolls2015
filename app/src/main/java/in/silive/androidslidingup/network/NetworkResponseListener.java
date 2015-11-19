package in.silive.androidslidingup.network;

import java.net.MalformedURLException;

/**
 * Created by Kartikey on 7/15/2015.
 */
public interface NetworkResponseListener {
    public void beforeRequest() throws MalformedURLException;


    public void postRequest(Object result) throws MalformedURLException;
}
