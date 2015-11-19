package in.silive.androidslidingup.network;

import android.os.AsyncTask;
import android.util.Log;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kartikey on 7/15/2015.
 */
public class FetchDataforLists extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
    NetworkResponseListener nrl;
    URL url;
    ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> resultHashMap;


    boolean searchForIds = false;
    JSONObject json = new JSONObject();
    int looper = 0;
    ArrayList<String> searchList = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    public String type_of_request;

    public void setNrl(NetworkResponseListener nrl) {
        this.nrl = nrl;
    }

    public void setType_of_request(String type_of_request) {
        this.type_of_request = type_of_request;
    }

    public void setURL(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public void setSearchForIds(boolean searchForIds) {
        this.searchForIds = searchForIds;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public void setSearchList(ArrayList<String> searchList) {
        this.searchList = searchList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            nrl.beforeRequest();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(type_of_request);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            if (type_of_request.equals("POST")) {
                urlConnection.setDoOutput(true);
                Log.d("FetchData", "inside json");
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(json.toString());
                wr.flush();
            }
            urlConnection.connect();

            InputStream is = urlConnection.getInputStream();
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String fetchedData = "";
            String line;
            try {
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                fetchedData = sb.toString();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //     fetchedData = fetchedData.substring(1, fetchedData.length() - 1);
//                fetchedData = fetchedData.replaceAll("\"", "");
            /*fetchedData = fetchedData.replaceAll("\\\\", "");
            fetchedData = fetchedData.replaceAll("/", "");
            */
            if(fetchedData.charAt(0)!='['){
                fetchedData="["+fetchedData+"]";
            }
            Log.d("FetchData", fetchedData);
            if (!fetchedData.equals("[]")) {
                InputStream stream = new ByteArrayInputStream(fetchedData.getBytes());
                JsonFactory factory = new JsonFactory();
                JsonParser jsonParser = factory.createJsonParser(stream);
                JsonToken token = jsonParser.nextToken();
                // Expected JSON is an array so if current token is "[" then while
                // we don't get
                // "]" we will keep parsing
                Log.d("FetchData", fetchedData);
                if (searchList.size() == 0) {
                    return null;
                }
                if (token == JsonToken.START_ARRAY) {
                    while (token != JsonToken.END_ARRAY) {
                        // Inside array there are many objects, so it has to start
                        // with "{" and end with "}"
                        token = jsonParser.nextToken();
                        if (token == JsonToken.START_OBJECT) {
                            boolean isadded = false;
                            resultHashMap = new HashMap<>();

                            while (token != JsonToken.END_OBJECT) {
                                // Each object has a name which we will use to
                                // identify the type.
                                token = jsonParser.nextToken();
                                if (token == JsonToken.FIELD_NAME) {
                                    String objectName = jsonParser.getCurrentName();
                                    for (looper = 0; looper < searchList.size(); looper++) {
                                        if (0 == objectName.compareToIgnoreCase(searchList.get(looper))) {
                                            token = jsonParser.nextToken();
                                            resultHashMap.put(searchList.get(looper), jsonParser.getText());
                                            Log.d("feedid", searchList.get(looper) + jsonParser.getText());
                                            if (looper == searchList.size() - 1)
                                                isadded = true;
                                        }
                                    }                               //  jsonParser.nextToken();
                                }
                                if (isadded) {
                                    results.add(resultHashMap);
                                    isadded = false;
                                    Log.d("feed", "feed item added" + results.size());

                                }
                            }
                        }
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> strings) {
        super.onPostExecute(strings);
        Log.d("Suck u", "Suck u" + strings.size());
        if (strings == null)
            Log.d("Suck u", "Suck u");
        try {
            nrl.postRequest(strings);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getMaterialId(int position) {
        if (ids.size() != 0 && ids.get(position) != null)
            return ids.get(position);
        return null;
    }

    public ArrayList<String> getIdsArray() {
        return ids;
    }
}
