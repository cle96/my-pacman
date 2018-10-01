package org.example.pacman;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.Log;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;


public class Game {
    private GameView gameView;
    private int h, w;
    private TextView pointsView;
    private Context context;
    private Bitmap pacBitmap;
    private int pacx, pacy;
    public Enemy enemy;
    private ArrayList<GoldCoin> coins = new ArrayList<>();

    private int points = 0;
    public enum Orientation {LEFT, RIGHT, UP, DOWN}
    public Orientation direction = Orientation.RIGHT;

    public boolean gameOver = false;
    public boolean gamePaused = false;

    public Game(Context context, TextView view) {
        this.context = context;
        this.pointsView = view;
        setPacmanBitmap();
    }


    public void setGameView(GameView view) {
        this.gameView = view;
    }

    public void newGame() {

        pacx = 50;
        pacy = 400;
        this.enemy = new Enemy(context);
        enemy.x = 800;
        enemy.y = 800;

        points = 0;
        pointsView.setText(context.getResources().getString(R.string.points) + " " + points);
        coins = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GoldCoin coin = new GoldCoin(context);
            Random rand = new Random();
            int x = rand.nextInt(1113) + 1;
            int y = rand.nextInt(1080) + 1;
            coin.x = x >= 80 ? x - 80 : x;
            coin.y = y >= 40 ? y - 40 : y;

            coins.add(coin);
        }
        this.gameOver = false;
        this.gamePaused = false;
        gameView.invalidate();

    }

    public void setSize(int h, int w) {
        this.h = h;
        this.w = w;
    }

    public void movePacmanRight(int pixels) {
        if (pacx + pixels + pacBitmap.getWidth() < w && !gamePaused) {
            pacx = pacx + pixels;
            doCollisionCheck();
            gameView.invalidate();
            direction = Orientation.RIGHT;
            setPacmanBitmap();
        }
    }


    public void movePacmanUp(int pixels) {
        if (pacy + pixels > pixels && !gamePaused) {
            pacy = pacy - pixels;
            doCollisionCheck();
            gameView.invalidate();
            direction = Orientation.UP;
            setPacmanBitmap();
        }
    }

    public void movePacmanLeft(int pixels) {
        if (pacx + pixels > pixels && !gamePaused) {
            pacx = pacx - pixels;
            doCollisionCheck();
            gameView.invalidate();
            direction = Orientation.LEFT;
            setPacmanBitmap();
        }
    }

    public void movePacmanDown(int pixels) {
        if (pacy + pixels + pacBitmap.getHeight() < h && !gamePaused) {
            pacy = pacy + pixels;
            doCollisionCheck();
            gameView.invalidate();
            direction = Orientation.DOWN;
            setPacmanBitmap();
        }
    }

    public void moveEnemyRandomly(int pixels) {
        if(!gamePaused && !gameOver) {
            Random random = new Random();
            if (enemy.timesMove != 0) {
                switch (enemy.direction) {
                    case RIGHT:
                        if (enemy.x + pixels + enemy.getBitMap().getWidth() < w && !gamePaused) {
                            enemy.x = enemy.x + pixels;
                        }
                        break;
                    case UP:
                        if (enemy.y + pixels > pixels && !gamePaused) {
                            enemy.y = enemy.y - pixels;
                        }
                        break;
                    case LEFT:
                        if (enemy.x + pixels > pixels && !gamePaused) {
                            enemy.x = enemy.x - pixels;
                        }
                        break;
                    case DOWN:
                        if (enemy.y + pixels + enemy.getBitMap().getHeight() < h && !gamePaused) {
                            enemy.y = enemy.y + pixels;
                        }
                        break;
                }
                enemy.timesMove--;
            } else {
                enemy.direction = Orientation.values()[random.nextInt(4)];
                enemy.timesMove = 10 + random.nextInt(10);
            }
            gameView.invalidate();
        }
    }

    public void doCollisionCheck() {
        GoldCoin coinToRemove = null;
        for (GoldCoin coin : coins) {
            if (pacmanColides(coin.x, coin.y, 40)) {
                points++;
                pointsView.setText(context.getResources().getString(R.string.points) + " " + points);
                coinToRemove = coin;
            }


        }
        if (coinToRemove != null) {
            coins.remove(coinToRemove);
            if (coins.size() == 0){
                gamePaused = true;
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("YOU HAVE WON THE GAME!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                newGame();
                            }
                        });
                alertDialog.show();
            }
        }
        if (pacmanColides(enemy.x, enemy.y, 80)) {
            this.gameOver = true;
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("You have lost");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            newGame();
                        }
                    });
            alertDialog.show();
        }

    }

    public boolean pacmanColides(int x, int y, int elementSize) {
        return getPacx() + 80 >= x && getPacx() < x + elementSize && getPacy() + 80 >= y && getPacy() < y + elementSize;
    }

    public int getPacx() {
        return pacx;
    }

    public int getPacy() {
        return pacy;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<GoldCoin> getCoins() {
        return coins;
    }

    public Bitmap getPacBitmap() {
        return pacBitmap;
    }

    public void setPacmanBitmap() {
        switch (direction) {
            case RIGHT:
                pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanr);
                break;
            case UP:
                pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanu);
                break;
            case LEFT:
                pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanl);
                break;
            case DOWN:
                pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmand);
                break;
        }
    }


}
