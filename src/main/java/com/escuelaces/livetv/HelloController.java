package com.escuelaces.livetv;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.util.HashMap;
import java.util.Map;


public class HelloController {
    @FXML
    public
    ListView VideosView;

    Pagination pagination;

    //Player factory
    Map<String,MediaPlayer> players = new HashMap<String, MediaPlayer>();


    // Canales TDT
    SimpleListProperty<Channel> TDT = new SimpleListProperty<>(
            FXCollections.observableArrayList()
    );



    @FXML
    void initialize(){
       TDT.get().addAll(
               new Channel(1,"LA1","https://ztnr.rtve.es/ztnr/1688877.m3u8"),
               new Channel(2,"LA2","https://ztnr.rtve.es/ztnr/1688885.m3u8"),
               new Channel(24,"24h", "https://ztnr.rtve.es/ztnr/1694255.m3u8"),
               new Channel(8,"Eight", "https://moctobpltc-i.akamaihd.net/hls/live/571329/eight/playlist.m3u8"),
               new Channel(8,"skip_armstrong", "https://d3rlna7iyyu8wu.cloudfront.net/skip_armstrong/skip_armstrong_stereo_subs.m3u8")
       );

       VideosView.setCellFactory(listView -> new ListCell<Channel> () {



            @Override
            protected void updateItem(Channel channel, boolean empty) {
                super.updateItem(channel, empty);
                MediaPlayer player;

                if (channel == null || empty){
                    setGraphic(null);
                }else{

                    // Pone la celda alineada en el centro.
                    setAlignment(Pos.CENTER);

                    //Descarga, decodifica y reproduce el video

                    // Creamos un reproductor para reproducir el video en caso de que no exista uno ya reproduciendo


                    if(players.containsKey(channel.URL.get()))
                        player = players.get(channel.URL.get());
                    else {
                        player = new MediaPlayer(new Media(channel.URL.get()));
                        players.put(channel.URL.get(), player);
                    }


                    player.setAutoPlay(true);

                    //If la celda esta seleccionada activamos el sonido
                    if (isSelected())
                        player.setMute(false);
                    else
                        player.setMute(true);



                    //Creamos una vista que pinte el video en la pantalla
                    MediaView mediaView = new MediaView(player);


                    //Apapta el tamaño del video al tamaño de la vista(MediaView) donde se esta reproduciendo.
                    mediaView.autosize();

                    // Preserva la relacion de aspecto, para que al cambiar la altura del video cambie la anchura automaticamente,
                    // de forma que no aparezca el video distorsionado.
                    mediaView.setPreserveRatio(true);
                    mediaView.setFitHeight(320);

                    boundsInParentProperty().addListener((observableValue, oldBounds, bounds) -> {
                        // La celda ha aparecido /desaparecido, POR LA PARTE DE ARRIBA
                        if (bounds.getHeight() != 0 &&  bounds.getMaxY() <= 1) {
                            // Ha salido
                            if (oldBounds.getMaxY() > bounds.getMaxY()) {
                                player.pause();
                            }
                            // HA ENTRADO
                            else if (oldBounds.getMaxY() < bounds.getMaxY()){
                                player.play();
                            }

                        }
                    });


                    // Establece el contenido grafico de la celda.
                    setGraphic(
                            mediaView
                    );
                }
            }
        }


       );


       VideosView.itemsProperty().bind(TDT);



    }
}