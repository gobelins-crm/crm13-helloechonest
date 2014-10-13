package fr.louisbl.helloechonest.playlist;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;

import fr.louisbl.helloechonest.server.EchoNest;

/**
 * Created by louis on 10/13/14.
 */
public class PlayListLoader extends AsyncTaskLoader<Playlist> {

    private Playlist mPlaylist;
    private int mResults;
    private String mArtist;

    public PlayListLoader(Context context) {
        super(context);
    }

    @Override
    public Playlist loadInBackground() {
        try {
            mPlaylist = EchoNest.getArtistRadio(mResults, mArtist);
        } catch (EchoNestException e) {
            mPlaylist = null;
        }
        return mPlaylist;
    }

    @Override
    protected void onStartLoading() {
        if (mPlaylist != null) {
            deliverResult(mPlaylist);
        } else {
            forceLoad();
        }
        super.onStartLoading();
    }

    public void setResults(int mResults) {
        this.mResults = mResults;
    }

    public void setArtist(String mArtist) {
        this.mArtist = mArtist;
    }
}
