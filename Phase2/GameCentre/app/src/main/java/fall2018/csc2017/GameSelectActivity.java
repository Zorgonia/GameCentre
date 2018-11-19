package fall2018.csc2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import fall2018.csc2017.checkers.CheckerMenuActivity;
import fall2018.csc2017.slidingtiles.ForgetActivity;
import fall2018.csc2017.slidingtiles.LoadActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SaveActivity;
import fall2018.csc2017.slidingtiles.StartingActivity;
import fall2018.csc2017.twentyfortyeight.MenuActivity2048;

/**
 * An Activity to select games
 * Using much code from https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */
public class GameSelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);

        ArrayList<String> gameList = new ArrayList<>();
        gameList.add("Sliding Tiles");
        gameList.add("Checkers???");
        gameList.add("Goes to forget activity");
        gameList.add("twenty forty eight");
        gameList.add("goes to save activity");
        RecyclerView rv = findViewById(R.id.RecyclerGame);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL );
        rv.addItemDecoration(itemDecoration);
        final RecycleAdapter adapter = new RecycleAdapter(gameList);
        adapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //Toast.makeText(GameSelectActivity.this,String.format(Locale.CANADA,"%s",adapter.dataSet.get(position)) ,Toast.LENGTH_SHORT).show();
                switchToGame(position);
            }
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * A method that switches the current screen to a game home screen
     * @param position the position of the click on the recycler view
     */
    public void switchToGame(int position) {
        Intent tmp = new Intent(this, SaveActivity.class);
        if (position == 0) {
            tmp = new Intent(this, StartingActivity.class);
        } else if (position == 1) {
            tmp = new Intent(this, CheckerMenuActivity.class);
        } else if (position == 2) {
            tmp = new Intent(this, ForgetActivity.class);
        } else if (position == 3) {
            tmp = new Intent(this, MenuActivity2048.class);
        }
        startActivity(tmp);
    }
}
