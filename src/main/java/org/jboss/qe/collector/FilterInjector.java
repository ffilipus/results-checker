package org.jboss.qe.collector;


import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import org.jboss.qe.collector.filterslists.FiltersFromFile;
import org.jboss.qe.collector.filterslists.FiltersList;

/**
 *  Created by fjerabek on 13.9.16.
 */
public class FilterInjector extends AbstractModule {
   @Inject
   public FilterInjector() {
   }

   @Override
   protected void configure() {
      FiltersList filters;
      filters = new FiltersFromFile();
      bind(FiltersList.class).to(filters.getClass());
   }
}