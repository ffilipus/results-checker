package org.jboss.qe.collector.filter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fjerabek on 7.9.16.
 */
public interface Matcher {
   boolean test(JSONObject json) throws JSONException;
}
