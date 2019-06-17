package io.udaptor.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dictionary {

    /*These are the TBoxes
    public static final String ONT_UDAPTOR = "http://udaptor.io/concept/Music/";
    public static final String ONT_GOOGLE_MUSIC = "http://udaptor.io/services/GoogleMusic/";
    public static final String ONT_APPLE_MUSIC = "http://udaptor.io/services/AppleMusic/";
    public static final String ONT_SPOTIFY = "http://udaptor.io/services/Spotify/";
    */

    //Name of services supported
    public static final String SERVICE_SPOTIFY = "Spotify";
    public static final String SERVICE_GOOGLE_MUSIC = "GoogleMusic";
    public static final String SERVICE_APPLE_MUSIC = "AppleMusic";
    //todo add more keynames of services if needed

    //metadata TBoxes for supported input and output parameters
    public static final String ONT_GOOGLE_MUSIC_METADATA = "http://udaptor.io/metadata/GoogleMusic/";
    public static final String ONT_APPLE_MUSIC_METADATA = "http://udaptor.io/metadata/AppleMusic/";
    public static final String ONT_SPOTIFY_METADATA = "http://udaptor.io/metadata/Spotify/";
    //todo add more ontologies URI if needed

    //hash to associate the corresponding supported input and output services names with their metadata ontologies
    public static final HashMap<String,String> AVAILABLE_ONTOLOGIES_HASH = new HashMap<String,String>() {
        {
            put(SERVICE_SPOTIFY, ONT_SPOTIFY_METADATA);
            put(SERVICE_GOOGLE_MUSIC, ONT_GOOGLE_MUSIC_METADATA);
            put(SERVICE_APPLE_MUSIC, ONT_APPLE_MUSIC_METADATA);
            //todo add more (servicesName,ontologies) if needed
        }
    };

    //List services that are supported for mapping
    public static List<String> getAllAvailableServicesForMapping(){
        List<String> outputList = new ArrayList<>();
        outputList.addAll(AVAILABLE_ONTOLOGIES_HASH.keySet());
        return outputList;
    }



}
