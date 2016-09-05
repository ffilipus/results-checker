package org.jboss.qe.collector.filter.resteasy;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.filter.AbstractFilter;
import org.jboss.qe.collector.filter.FilterItem;

public class Eap7xResteasyTestsuite extends AbstractFilter {

    static FilterItem [] items = {

                new FilterItem()
                        .addTest("org.jboss.resteasy.test.client.proxy.ResponseObjectTest#testSimpleProxyBuilder")
                        .addTest("org.jboss.resteasy.test.client.proxy.ResponseObjectTest.testLinkFollowProxyBuilder")
                        .setErrorText("https://issues.jboss.org/browse/JBEAP-2446").setColour(Colour.YELLOW),

                new FilterItem()
                        .addTest("org.jboss.resteasy.test.spring.inmodule.TypeMappingTest.test")
                        .addTest("org.jboss.resteasy.test.spring.deployment.TypeMappingDependenciesInDeploymentTest.test")
                        .setErrorText("https://issues.jboss.org/browse/JBEAP-2963").setColour(Colour.YELLOW),

                new FilterItem()
                        .addTest("org.jboss.resteasy.test.resource.basic.DefaultMediaTypeTest.postDate")
                        .addTest("org.jboss.resteasy.test.resource.basic.DefaultMediaTypeTest.postFoo")
                        .setErrorText("https://issues.jboss.org/browse/JBEAP-2847").setColour(Colour.YELLOW),

                new FilterItem()
                        .addTest("org.jboss.resteasy.test.providers.jaxb.JaxbXmlRootElementProviderTest.testGetParentJson")
                        .setErrorText("https://issues.jboss.org/browse/JBEAP-3530").setColour(Colour.YELLOW),

                new FilterItem()
                        .addTest("org.jboss.resteasy.test.validation.ValidationExceptionsTest.testConstraintDeclarationException")
                        .setErrorText("https://issues.jboss.org/browse/JBEAP-3459 - reported, fails sometimes (50%)").setColour(Colour.PURPLE),

                new FilterItem()
                        .addTest("org.jboss.resteasy.test.client.TraceTest#TraceTest")
                        .setErrorText("https://gitlab.mw.lab.eng.bos.redhat.com/jbossqe-eap/eap7-resteasy-ts/merge_requests/114 - TraceTest needs fix").setColour(Colour.PURPLE)
    };

    public String filter(FailedTest failedTest) {
        return coreFilter(failedTest, items);
    }
}
