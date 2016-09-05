package org.jboss.qe.collector.service.PageType;

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
        key = "\""+key+"\":";
        int index_from = rawPage.indexOf(key)+key.length()+1;
        int index_to = rawPage.indexOf('"',index_from);
        String value =rawPage.substring(index_from,index_to);
        return value;
    }

    public int getInt(String key){
        key = "\""+key+"\"";
        int index_from = rawPage.indexOf(key)+key.length()+1;
        int index_to = rawPage.indexOf(',',index_from);
        int value =Integer.valueOf(rawPage.substring(index_from,index_to));
        return value;
    }
}
