package com.tictactoe;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Text user;

    @FXML
    private GridPane board;

    @FXML
    private ImageView g00;

    @FXML
    private ImageView g01;

    @FXML
    private ImageView g20;

    @FXML
    private ImageView g02;

    @FXML
    private ImageView g10;

    @FXML
    private ImageView g11;

    @FXML
    private ImageView g12;

    @FXML
    private ImageView g21;

    @FXML
    private ImageView g22;

    @FXML
    private Text timer;

    @FXML
    private ToggleButton sound;

    @FXML
    private ImageView soundImage;

    private int time = 60;
    private Timeline timeline;
    private LinkedList<ImageView> cells;
    private Image X;
    private Image O;
    private Image on;
    private Image off;
    private MediaPlayer mediaPlayer;
    private char [][] game;
    private boolean end = false;
    private String winner = "";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000),
                actionEvent -> {
                    time--;
                    if (time > 0) {
                        timer.setText(String.valueOf(time));
                    } else {
                        timer.setText("Draw!");
                        end = true;
                    }
                }
                ));
        timeline.setCycleCount(60);
        timeline.play();

        game = new char [3][3];

        for (int i = 0 ; i < 3 ; i++){
            for (int j = 0 ; j < 3 ; j++){
                game[i][j] = 'e';
            }
        }

        cells = new LinkedList<>();

        cells.add(g00);
        cells.add(g01);
        cells.add(g02);
        cells.add(g10);
        cells.add(g11);
        cells.add(g12);
        cells.add(g20);
        cells.add(g21);
        cells.add(g22);

        try {
            on = new Image(new FileInputStream(new File(GameController.class.getResource("on.png").getFile()).getAbsolutePath()));
            off = new Image(new FileInputStream(new File(GameController.class.getResource("off.png").getFile()).getAbsolutePath()));
            X = new Image(new FileInputStream(new File(GameController.class.getResource("x.png").getFile()).getAbsolutePath()));
            O = new Image(new FileInputStream(new File(GameController.class.getResource("o.png").getFile()).getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Media media = new Media("https://dl.songsara.net/FRE/2021/4/S%20N%20U%20G%20-%20Secret%20Pond%20(2021)%20SONGSARA.NET/04%20Fallen%20Leaves.mp3");
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        sound.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (sound.isSelected()){
                    soundImage.setImage(off);
                    mediaPlayer.stop();
                } else {
                    soundImage.setImage(on);
                    mediaPlayer.play();
                }
            }
        });
    }

    public void setUser (String user){
        this.user.setText(user);
    }

    @FXML
    void Chose(MouseEvent event) {
        if (!end) {
            ImageView imageView = (ImageView) event.getSource();
            if (imageView.getImage() == null) {
                imageView.setImage(X);
                String str = imageView.getId();
                game[Character.getNumericValue(str.charAt(1))][Character.getNumericValue(str.charAt(2))] = 'x';
            }

            checkEnd();
        }

        if (!end){

            LinkedList<ImageView> possibles = new LinkedList<>();

            for (ImageView i : cells) {
                if (i.getImage() == null) {
                    possibles.add(i);
                }
            }
            Random random = new Random();
            Collections.shuffle(possibles);
            ImageView i = possibles.get(random.nextInt(possibles.size()));
            i.setImage(O);
            String str = i.getId();
            game[Character.getNumericValue(str.charAt(1))][Character.getNumericValue(str.charAt(2))] = 'o';

            checkEnd();
        }
    }

    @FXML
    void repeat(ActionEvent event) {
        for (ImageView i : cells){
            i.setImage(null);
        }

        for (int i = 0 ; i < 3 ; i++){
            for (int j = 0 ; j < 3 ; j++){
                game[i][j] = 'e';
            }
        }

        timeline.stop();
        time = 60;
        timer.setText(String.valueOf(60));
        timeline = new Timeline(new KeyFrame(Duration.millis(1000),
                actionEvent -> {
                    time--;
                    if (time > 0) {
                        timer.setText(String.valueOf(time));
                    } else {
                        timer.setText("Draw!");
                        end = true;
                    }
                }
        ));
        timeline.setCycleCount(60);
        timeline.play();
        end = false;
    }

    void checkEnd () {
        for (int i = 0 ; i < 3 ; i++){
            if (!end && game[i][0] == 'x' && game[i][1] == 'x' && game[i][2] == 'x'){
                end = true;
                winner = user.getText();
                break;
            } else if (!end && game[0][i] == 'x' && game[1][i] == 'x' && game[2][i] == 'x'){
                end = true;
                winner = user.getText();
                break;
            } else if (!end && game[i][0] == 'o' && game[i][1] == 'o' && game[i][2] == 'o'){
                end = true;
                winner = "PC";
                break;
            } else if (!end && game[0][i] == 'o' && game[1][i] == 'o' && game[2][i] == 'o'){
                end = true;
                winner = "PC";
                break;
            }
        }

        if (!end && game[0][0] == 'x' && game[1][1] == 'x' && game[2][2] == 'x'){
            end = true;
            winner = user.getText();
        } else if (!end && game[0][2] == 'x' && game[1][1] == 'x' && game[2][0] == 'x'){
            end = true;
            winner = user.getText();
        } else if (!end && game[0][0] == 'o' && game[1][1] == 'o' && game[2][2] == 'o'){
            end = true;
            winner = "PC";
        } else if (!end && game[0][2] == 'o' && game[1][1] == 'o' && game[2][0] == 'o'){
            end = true;
            winner = "PC";
        }

        if (end){
            timeline.stop();
            timer.setText("Winner is " + winner);
        } else {
            end = true;
            for (int i = 0 ; i < 3; i++){
                for (int j = 0 ; j < 3 ; j++){
                    if (game[i][j] == 'e'){
                        end = false;
                        break;
                    }
                }
            }

            if (end){
                timeline.stop();
                timer.setText("Draw");
            }
        }
    }

}
