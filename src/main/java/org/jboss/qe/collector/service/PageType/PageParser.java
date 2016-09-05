package org.jboss.qe.collector.service.PageType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jiri Bilek
 */
public class PageParser {
    private String rawPage;

    public PageParser(String rawJSON){
        this.rawPage=rawJSON;
    }

    public boolean isMatrix(){
        return rawPage.contains("runs");
    }

    public String get(String key){

        JSONObject obj = null;
        try {
            obj = new JSONObject(rawPage);
            return obj.getString(key);
            //System.out.println("Cislo bildu:"+obj.getJSONArray("runs").getJSONObject(0).getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
