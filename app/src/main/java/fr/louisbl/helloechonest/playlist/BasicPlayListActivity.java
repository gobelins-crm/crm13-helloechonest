package fr.louisbl.helloechonest.playlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.echonest.api.v4.Song;

import fr.louisbl.helloechonest.R;
import fr.louisbl.helloechonest.server.EchoNest;

public class BasicPlayListActivity extends Activity implements PlayListFragment.OnSongClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EchoNest.initEchoNestAPI(this);
        setContentView(R.layout.activity_basic_play_list);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, PlayListFragment.newInstance(20, "Helloween"))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.basic_play_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSongClicked(Song song) {
        // TODO implements onSongClicked
    }
}
