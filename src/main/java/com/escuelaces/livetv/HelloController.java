package com.escuelaces.livetv;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;



public class HelloController {
    @FXML
    public
    ListView VideosView;


    SimpleListProperty<Channel> TDT = new SimpleListProperty<>(
            FXCollections.observableArrayList()
    );



    @FXML
    void initialize(){
       TDT.get().addAll(
               new Channel(1,"LA1","https://ztnr.rtve.es/ztnr/1688877.m3u8"),
               new Channel(2,"LA2","https://ztnr.rtve.es/ztnr/1688885.m3u8"),
               new Channel(24,"24h", "https://ztnr.rtve.es/ztnr/1694255.m3u8")
       );

       VideosView.setCellFactory(listView -> new VideoCell());

       VideosView.itemsProperty().bind(TDT);

    }
}