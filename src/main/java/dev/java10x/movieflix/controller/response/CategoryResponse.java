package dev.java10x.movieflix.controller.response;

import lombok.Builder;

@Builder
public record CategoryResponse(Long id, String name) {
}
