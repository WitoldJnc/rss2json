package com.rss2json.rss2json.impl;

import com.rss2json.rss2json.repo.UrlValidatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class UrlValodatorServiceImplTest {


    private static final String INVALID_URL = "https://google.pugle";
    private static final String VALID_URL = "https://google.com";

    @MockBean
    private UrlValidatorService service;

    @Test
    public void shouldReturnFalseWhenUrlInvalid() {
        when(service.isValid(INVALID_URL)).thenReturn(false);
        Assertions.assertFalse(service.isValid(INVALID_URL));
    }

    @Test
    public void shouldReturnTrueWhenUrlValid() {
        when(service.isValid(VALID_URL)).thenReturn(true);
        Assertions.assertTrue(service.isValid(VALID_URL));
    }
}