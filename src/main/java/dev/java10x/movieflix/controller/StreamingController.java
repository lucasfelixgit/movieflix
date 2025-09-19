package dev.java10x.movieflix.controller;

import dev.java10x.movieflix.controller.request.StreamingRequest;
import dev.java10x.movieflix.controller.response.StreamingResponse;
import dev.java10x.movieflix.entity.Streaming;
import dev.java10x.movieflix.mapper.StreamingMapper;
import dev.java10x.movieflix.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService streamingService;

    @GetMapping
    public ResponseEntity<List<StreamingResponse>> findAllStreamings(){
        List<StreamingResponse> streamings = streamingService.findAll().stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();
        
        return ResponseEntity.ok(streamings);
    }

    @PostMapping
    public ResponseEntity<StreamingResponse> saveStreaming(@RequestBody StreamingRequest streamingRequest){
        Streaming savedStreaming = streamingService.save(StreamingMapper.toStreaming(streamingRequest));
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StreamingMapper.toStreamingResponse(savedStreaming));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getStreamingById(@PathVariable Long id){
        Optional<Streaming> optionalStreaming = streamingService.findById(id);
        return optionalStreaming
                .map(streaming -> ResponseEntity.ok().body(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreamingById(@PathVariable Long id){
        Optional<Streaming> optionalStreaming = streamingService.findById(id);
        if (optionalStreaming.isPresent()){
            streamingService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
