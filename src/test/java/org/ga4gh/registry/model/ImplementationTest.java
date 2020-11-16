package org.ga4gh.registry.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ImplementationTest {

    private class TestCase {

        private String id;
        private String name;
        private String description;
        private Organization organization;
        private String contactUrl;
        private String documentationUrl;
        private Date createdAt;
        private Date updatedAt;
        private String version;
        private List<Standard> standards;
        private String expString;
    

        public TestCase(String id, String name, String description, Organization organization, String contactUrl, String documentationUrl, Date createdAt, Date updatedAt, String version, List<Standard> standards, String expString) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.organization = organization;
            this.contactUrl = contactUrl;
            this.documentationUrl = documentationUrl;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.version = version;
            this.standards = standards;
            this.expString = expString;
        }

        public String getId() {
            return id;
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

        public String getVersion() {
            return version;
        }

        public List<Standard> getStandards() {
            return standards;
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
                "org.genomics.implementation.testa",
                "Genomics Implementation A",
                "An implementation of a GA4GH standard",
                new Organization("Genomics Group", "GG", "https://genomicsgroup.org"),
                "mailto:me@genomicsgroup.org",
                "https://genomicsgroup.org/implementations.testa",
                createdDateA.getTime(),
                updatedDateA.getTime(),
                "12.13.14",
                new ArrayList<Standard>() {{
                    add(new Standard());
                }},
                "Implementation [id=org.genomics.implementation.testa, name=Genomics Implementation A, description=An implementation of a GA4GH standard, contactUrl=mailto:me@genomicsgroup.org, documentationUrl=https://genomicsgroup.org/implementations.testa, version=12.13.14]"
            )}
        };
    }

    public void assertions(Implementation impl, TestCase testCase) {
        Assert.assertEquals(impl.getId(), testCase.getId());
        Assert.assertEquals(impl.getName(), testCase.getName());
        Assert.assertEquals(impl.getDescription(), testCase.getDescription());
        Assert.assertEquals(impl.getOrganization(), testCase.getOrganization());
        Assert.assertEquals(impl.getContactUrl(), testCase.getContactUrl());
        Assert.assertEquals(impl.getDocumentationUrl(), testCase.getDocumentationUrl());
        Assert.assertEquals(impl.getCreatedAt(), testCase.getCreatedAt());
        Assert.assertEquals(impl.getUpdatedAt(), testCase.getUpdatedAt());
        Assert.assertEquals(impl.getVersion(), testCase.getVersion());
        Assert.assertEquals(impl.toString(), testCase.getExpString());
        Assert.assertEquals(impl.getTableName(), "implementation");
    }

    @Test(dataProvider = "cases")
    public void testImplementationNoArgsConstructor(TestCase testCase) throws Exception {
        Implementation impl = new Implementation();
        impl.setId(testCase.getId());
        impl.setName(testCase.getName());
        impl.setDescription(testCase.getDescription());
        impl.setOrganization(testCase.getOrganization());
        impl.setContactUrl(testCase.getContactUrl());
        impl.setDocumentationUrl(testCase.getDocumentationUrl());
        impl.setCreatedAt(testCase.getCreatedAt());
        impl.setUpdatedAt(testCase.getUpdatedAt());
        impl.setVersion(testCase.getVersion());
        impl.setStandards(testCase.getStandards());
        assertions(impl, testCase);
    }

    @Test(dataProvider = "cases")
    public void testImplementationAllArgsConstructor(TestCase testCase) throws Exception {
        Implementation impl = new Implementation(testCase.getName(), testCase.getDescription(), testCase.getContactUrl(), testCase.getDocumentationUrl(), testCase.getCreatedAt(), testCase.getUpdatedAt(), testCase.getVersion());
        impl.setId(testCase.getId());
        impl.setOrganization(testCase.getOrganization());
        impl.setStandards(testCase.getStandards());
        assertions(impl, testCase);
    }
}
