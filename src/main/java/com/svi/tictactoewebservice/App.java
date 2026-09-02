package com.svi.tictactoewebservice;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class App extends Application {
    // Needed to enable Jakarta REST and specify path.
}
