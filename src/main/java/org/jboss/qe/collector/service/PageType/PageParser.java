package org.jboss.qe.collector.service.PageType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jiri Bilek
 */
public class PageParser {
   private String rawPage;
   private JSONObject obj;

   public PageParser(String rawJSON) {
      this.rawPage = rawJSON;
      try {
         obj = new JSONObject(rawPage);
      } catch (JSONException e) {
         e.printStackTrace();
      }
   }

   public boolean isMatrix() {
      return rawPage.contains("runs");
   }

   public String get(String key) {

      try {
         return obj.getString(key);
      } catch (JSONException e) {
         e.printStackTrace();
         return null;
      }
   }

   public JSONArray getRuns() {
      try {
         return obj.getJSONArray("runs");
         //System.out.println("Cislo bildu:"+obj.getJSONArray("runs").getJSONObject(0).getString("url"));
      } catch (JSONException e) {
         e.printStackTrace();
         return null;
      }
   }
}
