package com.escuelaces.livetv;

import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.util.HashMap;
import java.util.Map;


public class TV {
    @FXML
    public
    Pagination TVView;


    @FXML
    void initialize() {

        TVView.setPageFactory(pageIndex -> {
            MediaPlayer player;

            var channels = TVData.TDT.get();
            var channel = channels.get(pageIndex);

            // Creamos un reproductor para reproducir el video en caso de que no exista uno ya reproduciendo
            if (TVData.players.containsKey(channel.URL.get()))
                player = TVData.players.get(channel.URL.get());
            else {
                player = new MediaPlayer(new Media(channel.URL.get()));
                TVData.players.put(channel.URL.get(), player);
            }

            player.setAutoPlay(true);



            //Creamos una vista que pinte el video en la pantalla
            MediaView mediaView = new MediaView(player);


            //Apapta el tamaño del video al tamaño de la vista(MediaView) donde se esta reproduciendo.
            mediaView.autosize();

            // Preserva la relacion de aspecto, para que al cambiar la altura del video cambie la anchura automaticamente,
            // de forma que no aparezca el video distorsionado.
            //mediaView.setPreserveRatio(true);


            return mediaView;

        });

    }

}
