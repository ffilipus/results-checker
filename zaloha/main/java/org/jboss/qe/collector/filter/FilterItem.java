package org.jboss.qe.collector.filter;

import org.jboss.qe.collector.Colour;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for filter item. Failed tests can correspond with this rule.
 */
public class FilterItem {
    /**
     * Create a new FilterItem
     * @param colour The colour of the failure message.
     */
    public FilterItem(Colour colour) {

        this.colour = colour;
    }
    public FilterItem() {
    }
    /**
     * All regular expressions from this list must match with url of configuration with failed test.
     */
    List<String> urlRegEx = new ArrayList<String>();

    /**
     *  One regular expressions from this list must match with failed test name.
     */
    List<String> testsRegEx = new ArrayList<String>();

    List<Closure<Boolean>> testMatchers = new ArrayList<Closure<Boolean>>();

    /**
     * Description of error.
     */
    String errorText;

    /**
     * Color of printed results.
     */
    Colour colour = Colour.BLACK;

    /**
     * Category of error. Optional.
     */
    String category;

    /**
     * Add regular expression. It must match with url of configuration with failed test.
     */
    public FilterItem addUrl(String url) {
        this.urlRegEx.add(url);
        return this;
    }

    /**
     * Add regular expression. One of all added regular expression must match with failed test name.
     */
    public FilterItem addTest(String test) {
        this.testsRegEx.add(test);
        return this;
    }

    public FilterItem addTestMatcher(Closure<Boolean> matcher) {
        this.testMatchers.add(matcher);
        return this;
    }

    /**
     * Set description of error.
     */
    public FilterItem setErrorText(String inErrorText) {
        errorText = inErrorText;
        return this;
    }

    /**
     * Set color of printed results.
     */
    public FilterItem setColour(Colour colour) {
        this.colour = colour;
        return this;
    }

    /**
     * Set category of error. Optional.
     */
    public FilterItem setCategory(String category) {
        this.category = category;
        return this;
    }

    public List<String> getUrlRegEx(){
        return this.urlRegEx;
    }

    public List<String> getTestsRegEx(){
        return this.testsRegEx;
    }

    public String getCategory(){
        return this.category;
    }

    public List<Closure<Boolean>> getTestMatchers(){
        return this.testMatchers;
    }
}
