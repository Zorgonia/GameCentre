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

import fall2018.csc2017.slidingtiles.ForgetActivity;
import fall2018.csc2017.slidingtiles.LoadActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SaveActivity;
import fall2018.csc2017.slidingtiles.StartingActivity;

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
        gameList.add("Monkey Business");
        gameList.add("Eating cheese on a monday afternoon");
        gameList.add("twenty forty eight");
        gameList.add("check(mate)ers");
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

    public void switchToGame(int position) {
        Intent tmp = new Intent(this, SaveActivity.class);
        if (position == 0) {
            tmp = new Intent(this, StartingActivity.class);
        } else if (position == 1) {
            tmp = new Intent(this, LoadActivity.class);
        } else if (position == 2) {
            tmp = new Intent(this, ForgetActivity.class);
        }
        startActivity(tmp);
    }
}
