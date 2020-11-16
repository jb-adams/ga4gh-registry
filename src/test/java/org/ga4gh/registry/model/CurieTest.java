package org.ga4gh.registry.model;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CurieTest {

    private class TestCase {

        private String curieString;
        private boolean expSuccess;
        private String expPrefix;
        private String expId;

        public TestCase(String curieString, boolean expSuccess, String expPrefix, String expId) {
            this.curieString = curieString;
            this.expSuccess = expSuccess;
            this.expPrefix = expPrefix;
            this.expId = expId;
        }

        public String getCurieString() {
            return curieString;
        }

        public boolean getExpSuccess() {
            return expSuccess;
        }

        public String getExpPrefix() {
            return expPrefix;
        }

        public String getExpId() {
            return expId;
        }
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {new TestCase(
                "NOTCURIE12345",
                false,
                "",
                ""
            )},
            {new TestCase(
                "GA4GHDRS:00001",
                true,
                "GA4GHDRS",
                "00001"
            )}
        };
    }

    @Test(dataProvider = "cases")
    public void testCurie(TestCase testCase) throws Exception {
        try {
            Curie curie = Curie.fromString(testCase.getCurieString());
            Assert.assertEquals(true, testCase.getExpSuccess());
            Assert.assertEquals(curie.getPrefix(), testCase.getExpPrefix());
            Assert.assertEquals(curie.getId(), testCase.getExpId());
        } catch (ParseException ex) {
            Assert.assertEquals(false, testCase.getExpSuccess());
        }
    }
}
