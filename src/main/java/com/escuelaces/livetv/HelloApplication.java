package com.escuelaces.livetv;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    public TVData tvData;


    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Live TV");

        TVData.addChannels(
                new Channel(1,"LA1","https://ztnr.rtve.es/ztnr/1688877.m3u8"),
                new Channel(2,"LA2","https://ztnr.rtve.es/ztnr/1688885.m3u8"),
                new Channel(24,"24h", "https://ztnr.rtve.es/ztnr/1694255.m3u8"),
                new Channel(8,"Eight", "https://moctobpltc-i.akamaihd.net/hls/live/571329/eight/playlist.m3u8"),
                new Channel(8,"skip_armstrong", "https://d3rlna7iyyu8wu.cloudfront.net/skip_armstrong/skip_armstrong_stereo_subs.m3u8")
        );

        Pagination TVView = new Pagination();
        TVView.setPageCount(TVData.TDT.getSize());
        TVView.setCurrentPageIndex(0);
        TVView.setMaxPageIndicatorCount(TVData.TDT.getSize());

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
            player.setMute(true);



            //Creamos una vista que pinte el video en la pantalla
            MediaView mediaView = new MediaView(player);


            //Apapta el tamaño del video al tamaño de la vista(MediaView) donde se esta reproduciendo.
            mediaView.autosize();

            // Preserva la relacion de aspecto, para que al cambiar la altura del video cambie la anchura automaticamente,
            // de forma que no aparezca el video distorsionado.
            mediaView.setPreserveRatio(true);

            mediaView.setFitWidth(1800);

            return mediaView;

        });



        VBox vBox = new VBox(TVView);
        Scene tvScene = new Scene(vBox, 960, 600);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 600, 900);



        stage.maximizedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(stage.isMaximized())
                stage.setScene(tvScene);
        });



       stage.setTitle("Live TV");
       stage.setScene(scene);
       stage.show();






    }


    public static void main(String[] args) {
        launch();
    }
}