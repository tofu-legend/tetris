package com.tetris;

import com.jfinal.core.Controller;

public class GameController extends Controller {

    public void index() {
        render("/index.html");
    }
}
