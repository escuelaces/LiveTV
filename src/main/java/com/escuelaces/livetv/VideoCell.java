package com.escuelaces.livetv;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;

import javafx.scene.media.*;




public class VideoCell extends ListCell<Channel> {

    MediaView mediaView;




    @Override
    protected void updateItem(Channel channel, boolean empty) {
        super.updateItem(channel, empty);


        if (channel == null || empty){
            setGraphic(null);
        }else{



            // Pone la celda alineada en el centro.
            setAlignment(Pos.CENTER);

            //Descarga, decodifica y reproduce el video
            var player = new MediaPlayer(new Media(channel.URL.get()));
            player.setAutoPlay(true);
            player.setMute(true);


            // Presenta el video en pantalla
            mediaView = new MediaView(player);

            //Apapta el tamaño del video al tamaño de la vista(MediaView) donde se esta reeproduciendo.
            mediaView.autosize();

            // Preserva la relacion de aspecto, para que al cambiar la altura del video cambie la anchura automaticamente,
            // de forma que no aparezca el video distorsionado.
            mediaView.setPreserveRatio(true);
            mediaView.setFitHeight(320);

            // Establece el contenido grafico de la celda.
            setGraphic(
                    mediaView
            );
        }
    }
}
