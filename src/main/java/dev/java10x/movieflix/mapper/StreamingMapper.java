package dev.java10x.movieflix.mapper;

import dev.java10x.movieflix.controller.request.StreamingRequest;
import dev.java10x.movieflix.controller.response.StreamingResponse;
import dev.java10x.movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {

    public static Streaming toStreaming(StreamingRequest streamingRequest){
        return Streaming.builder()
                .name(streamingRequest.name())
                .build();
    }

    public static StreamingResponse toStreamingResponse(Streaming streaming){
        return StreamingResponse.builder()
                .id(streaming.getId())
                .name(streaming.getName())
                .build();
    }
}
