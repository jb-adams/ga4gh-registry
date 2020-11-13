package org.ga4gh.registry.model;

import java.util.Calendar;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ServiceTest {

    private class TestCase {

        private String id;
        private StandardVersion standardVersion;
        private String name;
        private String description;
        private Organization organization;
        private String contactUrl;
        private String documentationUrl;
        private Date createdAt;
        private Date updatedAt;
        private String environment;
        private String version;
        private String url;
        private String standardArtifact;
        private String expString;

        public TestCase(String id, StandardVersion standardVersion, String name, String description, Organization organization, String contactUrl, String documentationUrl, Date createdAt, Date updatedAt, String environment, String version, String url, String standardArtifact, String expString) {

            this.id = id;
            this.standardVersion = standardVersion;
            this.name = name;
            this.description = description;
            this.organization = organization;
            this.contactUrl = contactUrl;
            this.documentationUrl = documentationUrl;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.environment = environment;
            this.version = version;
            this.url = url;
            this.standardArtifact = standardArtifact;
            this.expString = expString;
        }

        public String getId() {
            return id;
        }

        public StandardVersion getStandardVersion() {
            return standardVersion;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Organization getOrganization() {
            return organization;
        }

        public String getContactUrl() {
            return contactUrl;
        }

        public String getDocumentationUrl() {
            return documentationUrl;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public String getEnvironment() {
            return environment;
        }

        public String getVersion() {
            return version;
        }

        public String getUrl() {
            return url;
        }

        public String getStandardArtifact() {
            return standardArtifact;
        }

        public String getExpString() {
            return expString;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        Calendar createdDateA = Calendar.getInstance();
        Calendar updatedDateA = Calendar.getInstance();
        createdDateA.set(2020, 5, 1, 17, 48, 9);
        updatedDateA.set(2020, 5, 1, 17, 48, 9);


        return new Object[][] {
            {new TestCase(
                "a95fcb4c-2754-4a7e-992f-751808bec425",
                new StandardVersion("1.2.0", "https://htsget.org/v/1.2.0"),
                "htsget reference implementation",
                "description of this service",
                new Organization("Global Alliance", "GA4GH", "https://ga4gh.org"),
                "mailto:me@ga4gh.org",
                "https://ga4gh.org/implementations/thisone",
                createdDateA.getTime(),
                updatedDateA.getTime(),
                "production",
                "1.0.0",
                "https://htsget.ga4gh.org",
                "htsget",
                "Service [id=a95fcb4c-2754-4a7e-992f-751808bec425, name=htsget reference implementation, description=description of this service, contactUrl=mailto:me@ga4gh.org, documentationUrl=https://ga4gh.org/implementations/thisone, environment=production, version=1.0.0, url=https://htsget.ga4gh.org]"
            )}
        };
    }

    public void assertions(Service service, TestCase testCase) {
        Assert.assertEquals(service.getId().toString(), testCase.getId().toString());
        Assert.assertEquals(service.getStandardVersion().getVersionNumber(), testCase.getStandardVersion().getVersionNumber());
        Assert.assertEquals(service.getName(), testCase.getName());
        Assert.assertEquals(service.getDescription(), testCase.getDescription());
        Assert.assertEquals(service.getOrganization().getName(), testCase.getOrganization().getName());
        Assert.assertEquals(service.getContactUrl(), testCase.getContactUrl());
        Assert.assertEquals(service.getDocumentationUrl(), testCase.getDocumentationUrl());
        Assert.assertEquals(service.getCreatedAt(), testCase.getCreatedAt());
        Assert.assertEquals(service.getUpdatedAt(), testCase.getUpdatedAt());
        Assert.assertEquals(service.getEnvironment(), testCase.getEnvironment());
        Assert.assertEquals(service.getVersion(), testCase.getVersion());
        Assert.assertEquals(service.getUrl(), testCase.getUrl());
        Assert.assertEquals(service.toString(), testCase.getExpString());
        Assert.assertEquals(service.getServiceType().getGroup(), "org.ga4gh");
        Assert.assertEquals(service.getServiceType().getArtifact(), testCase.getStandardArtifact());
        Assert.assertEquals(service.getServiceType().getVersion(), testCase.getStandardVersion().getVersionNumber());
    }

    @Test(dataProvider = "cases")
    public void testServiceNoArgsConstructor(TestCase testCase) throws Exception {

        Service service = new Service();
        service.setId(testCase.getId());
        service.setStandardVersion(testCase.getStandardVersion());
        service.getStandardVersion().setStandard(new Standard());
        service.getStandardVersion().getStandard().setArtifact(testCase.getStandardArtifact());
        service.setName(testCase.getName());
        service.setDescription(testCase.getDescription());
        service.setOrganization(testCase.getOrganization());
        service.setContactUrl(testCase.getContactUrl());
        service.setDocumentationUrl(testCase.getDocumentationUrl());
        service.setCreatedAt(testCase.getCreatedAt());
        service.setUpdatedAt(testCase.getUpdatedAt());
        service.setEnvironment(testCase.getEnvironment());
        service.setVersion(testCase.getVersion());
        service.setUrl(testCase.getUrl());
        assertions(service, testCase);
    }

    @Test(dataProvider = "cases")
    public void testServiceAllArgsConstructor(TestCase testCase) throws Exception {

        Service service = new Service(testCase.getName(), testCase.getDescription(), testCase.getContactUrl(), testCase.getDocumentationUrl(), testCase.getCreatedAt(), testCase.getUpdatedAt(), testCase.getEnvironment(), testCase.getVersion(), testCase.getUrl());
        service.setId(testCase.getId());
        service.setStandardVersion(testCase.getStandardVersion());
        service.getStandardVersion().setStandard(new Standard());
        service.getStandardVersion().getStandard().setArtifact(testCase.getStandardArtifact());
        service.setOrganization(testCase.getOrganization());
        assertions(service, testCase);
    }
}