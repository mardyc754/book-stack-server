package com.bookstack.bookstack.utils;

import graphql.GraphQLContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class ResponseHeaderInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        return chain.next(request).doOnNext(response -> {
            String value = response.getExecutionInput().getGraphQLContext().get("token");

            // logout case
            if ("".equals(value)) {
                ResponseCookie cookie = ResponseCookie.from("token", "")
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .sameSite("None")
                        .maxAge(0)
                        .build();
                response.getResponseHeaders().add(HttpHeaders.SET_COOKIE, cookie.toString());
                // login case
            } else if (value != null) {
                ResponseCookie cookie = ResponseCookie.from("token", value)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .sameSite("None")
                        .maxAge(36000)
                        .build();

                response.getResponseHeaders().add(HttpHeaders.SET_COOKIE, cookie.toString());
            }
        });
    }

}