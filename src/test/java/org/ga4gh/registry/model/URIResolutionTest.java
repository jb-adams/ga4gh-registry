package org.ga4gh.registry.model;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class URIResolutionTest {

    private class TestCase {
        private String serviceId;
        private String serviceName;
        private ServiceType serviceType;
        private String resolvedURL;
        
        public TestCase(String serviceId, String serviceName, ServiceType serviceType, String resolvedURL) {
            this.serviceId = serviceId;
            this.serviceName = serviceName;
            this.serviceType = serviceType;
            this.resolvedURL = resolvedURL;
        }

        public String getServiceId() {
            return serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public ServiceType getServiceType() {
            return serviceType;
        }

        public String getResolvedURL() {
            return resolvedURL;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                "uk.ac.ebi.drs",
                "EBI DRS Service",
                new ServiceType("org.ga4gh", "drs", "1.0.0"),
                "https://drsapi.ebi.ac.uk/api/ga4gh/drs/v1/item12345"
            )}
        };
    }

    public void assertions(URIResolution resolution, TestCase testCase) {
        Assert.assertEquals(resolution.getServiceId(), testCase.getServiceId());
        Assert.assertEquals(resolution.getServiceName(), testCase.getServiceName());
        Assert.assertEquals(resolution.getServiceType(), testCase.getServiceType());
        Assert.assertEquals(resolution.getResolvedURL(), testCase.getResolvedURL());
    }

    @Test(dataProvider = "cases")
    public void testURIResolutionNoArgsConstructor(TestCase testCase) throws Exception {
        URIResolution resolution = new URIResolution();
        resolution.setServiceId(testCase.getServiceId());
        resolution.setServiceName(testCase.getServiceName());
        resolution.setServiceType(testCase.getServiceType());
        resolution.setResolvedURL(testCase.getResolvedURL());
        assertions(resolution, testCase);
    }

    @Test(dataProvider = "cases")
    public void testURIResolutionAllArgsConstructor(TestCase testCase) throws Exception {
        URIResolution resolution = new URIResolution(testCase.getServiceId(), testCase.getServiceName(), testCase.getServiceType(), testCase.getResolvedURL());
        assertions(resolution, testCase);
    }
}
