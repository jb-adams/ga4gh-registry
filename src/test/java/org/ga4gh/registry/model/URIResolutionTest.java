package org.ga4gh.registry.model;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class URIResolutionTest {

    private class TestCase {
        private String serviceName;

        public TestCase(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceName() {
            return serviceName;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase("EBI DRS Service")}
        };
    }

    public void assertions(URIResolution resolution, TestCase testCase) {
        Assert.assertEquals(resolution.getServiceName(), testCase.getServiceName());
    }

    @Test(dataProvider = "cases")
    public void testURIResolutionNoArgsConstructor(TestCase testCase) throws Exception {
        URIResolution resolution = new URIResolution();
        resolution.setServiceName(testCase.getServiceName());
        assertions(resolution, testCase);
    }

    @Test(dataProvider = "cases")
    public void testURIResolutionAllArgsConstructor(TestCase testCase) throws Exception {
        URIResolution resolution = new URIResolution(testCase.getServiceName());
        assertions(resolution, testCase);
    }
}
