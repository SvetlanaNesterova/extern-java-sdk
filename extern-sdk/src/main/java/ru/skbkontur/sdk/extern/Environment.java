/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.skbkontur.sdk.extern;

import com.google.gson.Gson;


/**
 * @author AlexS
 */
public class Environment {

    public final Gson gson = new Gson();

    public Configuration configuration;
    public String accessToken;

    public Environment() {
        configuration = new Configuration();
    }
}
