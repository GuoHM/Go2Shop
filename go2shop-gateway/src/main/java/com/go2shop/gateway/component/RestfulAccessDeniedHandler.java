package com.go2shop.gateway.component;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import cn.hutool.json.JSONUtil;
import reactor.core.publisher.Mono;

@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.FORBIDDEN);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		String body = JSONUtil.toJsonStr(denied.getMessage());
		DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Mono.just(buffer));
	}
}
