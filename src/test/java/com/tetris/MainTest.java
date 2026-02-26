package com.tetris;

import org.junit.Assert;
import org.junit.Test;

public class MainTest {

    @Test
    public void shouldCreateConfigInstance() {
        Main config = new Main();
        Assert.assertNotNull(config);
    }
}
