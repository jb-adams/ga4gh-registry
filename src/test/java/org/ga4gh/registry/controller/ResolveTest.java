package org.ga4gh.registry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.ga4gh.registry.AppConfig;
import org.ga4gh.registry.testutils.ResourceLoader;
import org.ga4gh.registry.testutils.annotations.RegistryTestProperties;

@Test
@RegistryTestProperties
@ContextConfiguration(classes = { AppConfig.class, Resolve.class })
@WebAppConfiguration
public class ResolveTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    private static final String resolveDir = "/responses/resolve/";

    @DataProvider(name = "resolveURICases")
    public Object[][] resolveURICases() {
        return new Object[][] {
            {"NOTCURIE12345", status().isBadRequest(), false, null},
            {"NOMATCH:12345", status().isNotFound(), false, null},
            {"MYDRS:12345", status().isOk(), true, resolveDir + "00.json"}
        };
    }

    @Test(dataProvider = "resolveURICases")
    public void testResolveURI(String curie, ResultMatcher expStatus, boolean expSuccess, String expResultResourcePath) throws Exception {
        MvcResult result = mockMvc.perform(get("/resolve-uri/" + curie)).andExpect(expStatus).andReturn();
        System.out.println("RESPONSE BODY\n*****");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println("*****");
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            String expResponseBody = ResourceLoader.load(expResultResourcePath);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }

    
}
