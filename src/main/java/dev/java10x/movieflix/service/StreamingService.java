package dev.java10x.movieflix.service;

import dev.java10x.movieflix.entity.Streaming;
import dev.java10x.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository streamingRepository;

    public List<Streaming> findAll(){
        return streamingRepository.findAll();
    }

    public Streaming save(Streaming streaming){
        return streamingRepository.save(streaming);
    }

    public Optional<Streaming> findById(Long id){
        return streamingRepository.findById(id);
    }

    public void deleteById(Long id){
        streamingRepository.deleteById(id);
    }
}
