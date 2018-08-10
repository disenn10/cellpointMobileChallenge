package com.example.disen.cellpointassessment.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by disen on 7/23/2018.
 */

public class Utils {

    public static ArrayList<GitUser> fetchData(String request, String language) throws IOException {
        URL url = null;
        ArrayList<GitUser> data = new ArrayList<>();
        try {
            url = createUrl(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String output = makeHttpConnection(url);
        try {
            data = extractData(output, language);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static ArrayList<GitUser> fetchNumberofLanguages(String request) throws IOException {
        URL url = null;
        ArrayList<GitUser> language_list = new ArrayList<>();
        try {
            url = createUrl(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String output = makeHttpConnection(url);
        language_list = getLanguages(output);
        return language_list;
    }

    public static URL createUrl(String request) throws MalformedURLException {
        if(request == null){
            return null;
        }
        URL url = new URL(request);
        return url;
    }


    public static String makeHttpConnection(URL url) throws IOException {
        if(url == null){
            return null;
        }
        InputStream in = null;
        String output = null;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000);
        urlConnection.connect();
        if (urlConnection.getResponseCode() == 200) {
            in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            while (scanner.hasNext()) {
                output = scanner.next();
            }
        }
        urlConnection.disconnect();
        return output;
    }

    public static ArrayList<GitUser> extractData(String output, String language_match) throws JSONException {
        if(output == null){
            return null;
        }
        String name = null;
        String url = null;
        String description = "none";
        String created = null;
        String updated = null;
        String language = null;
        int stars = 0;
        int watchers = 0;
        ArrayList<GitUser> data_list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(output);
        JSONArray items_Arr = jsonObject.getJSONArray("items");
        for (int i = 0; i < items_Arr.length(); i++) {
            JSONObject item_obj = items_Arr.getJSONObject(i);
            if (item_obj.has("name")) {
                name = item_obj.getString("name");
            }
            if (item_obj.has("html_url")) {
                url = item_obj.getString("html_url");
            }
            if (item_obj.has("description")) {
                if (!item_obj.getString("description").equals("null")) {
                    description = item_obj.getString("description");
                }
            }
            if (item_obj.has("created_at")) {
                created = item_obj.getString("created_at");
            }
            if (item_obj.has("updated_at")) {
                updated = item_obj.getString("updated_at");
            }
            if (item_obj.has("stargazers_count")) {
                stars = item_obj.getInt("stargazers_count");
            }
            if (item_obj.has("watchers")) {
                watchers = item_obj.getInt("watchers");
            }
            if (item_obj.has("language")) {
                language = item_obj.getString("language").toUpperCase();
            }
            if (language.equals(language_match)) {
                data_list.add(new GitUser(name, url, description, created, updated, watchers, stars));
            }
        }
        return data_list;
    }

    //List all the programming languages the user used
    public static ArrayList<GitUser> getLanguages(String output) {
        if(output == null){
            return null;
        }
        String language = null;
        Boolean duplicates = false;
        ArrayList<GitUser> language_list = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(output);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray items_Arr = null;
            try {
                items_Arr = jsonObject.getJSONArray("items");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < items_Arr.length(); i++) {
                int noLanguage = 0;
                JSONObject item_obj = null;
                try {
                    item_obj = items_Arr.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                if (item_obj.has("language")) {
                    try {
                        language = item_obj.getString("language").toUpperCase();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //check if this language has already been added on the list
                duplicates = checkDuplicates(language, language_list);
                //if not add it to the list and update its number of repos
                if (duplicates == false) {
                    noLanguage += 1;
                    language_list.add(new GitUser(language, noLanguage));
                }
                //update its number of repos if the language has already been added to the list
                else {
                    updateNorepos(language, language_list);
                }
            }
        return language_list;
    }


    //Check duplicates programming languages
    public static Boolean checkDuplicates(String lang, ArrayList<GitUser> lang_list) {
        if (lang_list.size() == 0) {
            return false;
        } else {
            for (int i = 0; i < lang_list.size(); i++) {
                if (lang.equals(lang_list.get(i).getLanguage())) {
                    return true;
                }
            }
            return false;
        }
    }

    ////////////////////////////
    //Update number of repos in programming languages
    public static void updateNorepos(String lang, ArrayList<GitUser> lang_list) {
        int no_repos = 0;
        for (int i = 0; i < lang_list.size(); i++) {
            if (lang.equals(lang_list.get(i).getLanguage())) {
                no_repos = lang_list.get(i).getNoLanguages() + 1;
                lang_list.set(i, new GitUser(lang, no_repos));
                return;
            }
        }
    }


    public static String convertDate(String jsonDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        Date date = dateFormat.parse(jsonDate);//You will get date object relative to server/client timezone wherever it is parsed
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
        String dateStr = formatter.format(date);
        return dateStr;

    }

    public static String createRequest(String query) {
        String new_query = "https://api.github.com/search/repositories?q=user:" + query + "&per_page=100";
        return new_query;
    }
}
