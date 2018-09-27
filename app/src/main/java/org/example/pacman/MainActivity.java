package org.example.pacman;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    GameView gameView;
    Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        gameView = findViewById(R.id.gameView);
        TextView textView = findViewById(R.id.points);


        game = new Game(this, textView);
        game.setGameView(gameView);
        gameView.setGame(game);
        game.newGame();

        Button buttonRight = findViewById(R.id.moveRight);
        Button buttonLeft = findViewById(R.id.moveLeft);
        Button buttonUp = findViewById(R.id.moveUp);
        Button buttonDown = findViewById(R.id.moveDown);
        final Button buttonPause = findViewById(R.id.pause);

        buttonRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.movePacmanRight(0);
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.movePacmanLeft(0);
            }
        });

        buttonUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.movePacmanUp(0);
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.movePacmanDown(0);
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttonText = buttonPause.getText()+"";
                if(buttonText.equals("Pause")){
                    game.gamePaused = true;
                    buttonPause.setText("Resume");
                }else{
                    game.gamePaused = false;
                    gameView.invalidate();
                    buttonPause.setText("Pause");
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "settings clicked", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_newGame) {
            Toast.makeText(this, "New Game clicked", Toast.LENGTH_LONG).show();
            game.newGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}