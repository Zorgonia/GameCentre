package fall2018.csc2017.twentyfortyeight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fall2018.csc2017.slidingtiles.R;

/**
 * The main game activity for 2048
 */
public class GameActivity2048 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_main);
    }
}
