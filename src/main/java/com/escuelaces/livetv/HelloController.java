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


    @FXML
    void initialize(){


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


                    if(TVData.players.containsKey(channel.URL.get()))
                        player = TVData.players.get(channel.URL.get());
                    else {
                        player = new MediaPlayer(new Media(channel.URL.get()));
                        TVData.players.put(channel.URL.get(), player);
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
                        if (bounds.getHeight() == 0 || bounds.getMaxY() == oldBounds.getMaxY()) return;

                        // La celda ha desaparecido, por ARRIBA
                        if (bounds.getMaxY() <= 1 && oldBounds.getMaxY() > 1 ){
                            player.pause();
                            System.out.println("La celda " + channel.Nombre.get() + " ha desaparecido, por ARRIBA");
                            System.out.println(bounds.getMaxY() +" " + oldBounds.getMaxY() );
                        }
                        // La celda ha aparecido, por ARRIBA
                        else if (bounds.getMaxY() > 0  && oldBounds.getMaxY() < 0 )  {
                            player.play();
                            System.out.println("La celda " + channel.Nombre.get() + " ha aparecido, por ARRIBA");
                            System.out.println(oldBounds.getMaxY() + " "  + bounds.getMaxY()  );
                        }

                        var ListMaxY = getListView().getHeight();
                        // La celda ha desaparecido, por abajo
                         if(bounds.getMinY() > ListMaxY && oldBounds.getMinY() <= ListMaxY ){
                             player.pause();
                             System.out.println("La celda " + channel.Nombre.get() + " ha desaparecido, por ABAJO");
                             System.out.println(bounds.getMinY() +" " + oldBounds.getMinY() );
                         }
                         // La celda ha aparecido x abajo
                         else if(bounds.getMinY() < ListMaxY && oldBounds.getMinY() > ListMaxY){
                             System.out.println("La celda " + channel.Nombre.get() + " ha aparecido, por ABAJO");
                             System.out.println(bounds.getMinY() +" " + oldBounds.getMinY() );
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


       VideosView.itemsProperty().bind(TVData.TDT);



    }
}