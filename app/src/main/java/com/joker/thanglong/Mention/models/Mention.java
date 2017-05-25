package com.joker.thanglong.Mention.models;

import android.widget.EditText;

import com.percolate.mentions.Mentionable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A mention inserted into the {@link EditText}. All mentions inserted into the
 * {@link EditText} must implement the {@link Mentionable} interface.
 */
public class Mention implements Mentionable {

    private String mentionName;
    private int mentionId;
    private int offset;
    private int length;


    @Override
    public String toString() {
        HashMap<String,Integer> map = new HashMap<>();
        map.put("id",mentionId);
        map.put("offset",offset);
        map.put("length",length);
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    public int getMentionId() {
        return mentionId;
    }

    public void setMentionId(int mentionId) {
        this.mentionId = mentionId;
    }

    @Override
    public int getMentionOffset() {
        return offset;
    }

    @Override
    public int getMentionLength() {
        return length;
    }

    @Override
    public String getMentionName() {
        return mentionName;
    }

    @Override
    public void setMentionOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void setMentionLength(int length) {
        this.length = length;
    }

    @Override
    public void setMentionName(String mentionName) {
        this.mentionName = mentionName;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("id",mentionId);
        result.put("offset",offset);
        result.put("length",length);
        return result;
    }
}
