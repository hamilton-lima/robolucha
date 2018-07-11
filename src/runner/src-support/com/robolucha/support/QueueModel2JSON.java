package com.robolucha.support;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robolucha.game.vo.MatchRunStateVO;
import com.robolucha.game.vo.MessageVO;
import com.robolucha.models.MatchEvent;
import com.robolucha.publisher.RemoteQueue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class QueueModel2JSON {

    // MatchStatePublisher > MatchRunStateVO
    // MatchMessagePublisher > MessageVO
    // MatchEventPublisher > MatchEvent

    public static void main(String[] args) throws IOException {
        generate( new MatchRunStateVO() );
        generate( new MessageVO() );
        generate( new MatchEvent() );
    }

    private static void generate(Object object) throws IOException {
        String fileName = RemoteQueue.getChannelName( object ).toLowerCase() + ".json";
        writeFile(fileName, generateJson(object));
    }

    private static void writeFile(String fileName, String json) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(json);
        writer.flush();
        writer.close();
    }

    private static String generateJson(Object object) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        String result = gson.toJson(object);
        return result;
    }

}
