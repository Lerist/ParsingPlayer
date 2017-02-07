

package com.hustunique.parsingplayer.parser;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hustunique.parsingplayer.LogUtil;
import com.hustunique.parsingplayer.parser.extractor.Extractor;
import com.hustunique.parsingplayer.parser.extractor.YoukuExtractor;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JianGuo on 1/16/17.
 * Parser extracting video info from a given string.
 */

public class VideoParser {
    private static final String TAG = "VideoParser";
    private Extractor mExtractor;
    private static Map<String, Class<? extends Extractor>> sMatchMap = new HashMap<>();

    private static VideoParser mParser;

    private VideoParser() {
    }

    public static VideoParser getInstance(){
        if (mParser == null){
            mParser = new VideoParser();
        }
        return mParser;
    }

    static {
        // TODO: 1/17/17 Maybe there is a better solution to register map between regex and IExtractor here
        sMatchMap.put(YoukuExtractor.VALID_URL, YoukuExtractor.class);
    }

    @NonNull
    Extractor createExtractor(@NonNull String url) throws ExtractException{
        if (url == null) throw new ExtractException("Url shouldn't be null");
        Class<? extends Extractor> clz = findClass(url);
        if (clz != null) {
            try {
                return clz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LogUtil.wtf(TAG, e);
            }
        }
        throw new ExtractException("This url is not valid or unsupported yet");
    }

    @Nullable
    private Class<? extends Extractor> findClass(@NonNull String url) {
        for (String reg : sMatchMap.keySet()) {
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return sMatchMap.get(reg);
            }
        }
        return null;
    }

    public void parse(String url, Extractor.ExtractCallback callback) {
        try {
            mExtractor = createExtractor(url);
            mExtractor.extract(url, callback);
        } catch (ExtractException e) {
            callback.onError(e);
        }
    }


}
