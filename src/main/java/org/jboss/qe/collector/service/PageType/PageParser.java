package org.jboss.qe.collector.service.PageType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

   public JSONArray getSuites() {
      try {
         return obj.getJSONArray("suites");
      } catch (JSONException e) {
         e.printStackTrace();
         return null;
      }
   }

   public static JSONArray getCases(JSONObject suite) {
      try {
         return suite.getJSONArray("cases");
      } catch (JSONException e) {
         e.printStackTrace();
         return null;
      }
   }
}
