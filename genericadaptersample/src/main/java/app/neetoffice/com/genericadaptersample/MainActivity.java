package app.neetoffice.com.genericadaptersample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onList(View view) {
        final Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void onRecyclerList(View view) {
        final Intent intent = new Intent(this, RecyclerListActivity.class);
        startActivity(intent);
    }

    public void onRecyclerGrid(View view) {
        final Intent intent = new Intent(this, RecyclerGridActivity.class);
        startActivity(intent);
    }

    public void onRecyclerHeaderGrid(View view) {
        final Intent intent = new Intent(this, RecyclerHeaderGridActivity.class);
        startActivity(intent);
    }
}
