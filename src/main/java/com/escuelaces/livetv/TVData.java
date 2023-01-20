package com.escuelaces.livetv;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class TVData
{
    public static SimpleListProperty<Channel> TDT = new SimpleListProperty<>(
            FXCollections.observableArrayList()
    );

    //Player factory
    public static Map<String, MediaPlayer> players = new HashMap<String, MediaPlayer>();


    public static void addChannels(Channel ... channels) {

        TDT.get().addAll(
            channels
        );
    }
}
