package fall2018.csc2017.twentyfortyeight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.slidingtiles.ForgetActivity;
import fall2018.csc2017.slidingtiles.R;

public class MenuActivity2048 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_menu);
        addButtonListener(R.id.newButton2048, GameActivity2048.class);
        addButtonListener(R.id.loadButton2048, ForgetActivity.class);
    }

    void addButtonListener(int id, Class c) {
        Button button = findViewById(id);
        final Class switchTo = c;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(switchTo);
            }
        });
    }

    private void switchToActivity(Class c) {
        Intent tmp = new Intent(this, c);
        startActivity(tmp);
    }
}
