package fr.louisbl.helloechonest.services;

import android.content.Context;

import com.echonest.api.v4.BasicPlaylistParams;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;

import fr.louisbl.helloechonest.R;

/**
 * Created by louis on 10/13/14.
 */
public class EchoNest {
    private static EchoNestAPI sEchoNest;
    private static String EN_API_KEY;

    public static void initEchoNestAPI(Context context) {
        EN_API_KEY = context.getString(R.string.echo_nest_api_key);
    }

    protected static EchoNestAPI getInstance() throws EchoNestException {
        if (sEchoNest == null) {
            sEchoNest = new EchoNestAPI(EN_API_KEY);
        }
        return sEchoNest;
    }

    public static Playlist getSimilarArtists(int results, String artist ) throws EchoNestException {
        BasicPlaylistParams params = new BasicPlaylistParams();
        params.addArtist(artist);
        params.setType(BasicPlaylistParams.PlaylistType.ARTIST_RADIO);
        params.setResults(results);
        return getInstance().createBasicPlaylist(params);
    }
}
