package com.example.demo.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private static final int MAX_PAYLOAD_LENGTH = 10000; // 10KB limit for logging
    private static final int CONTENT_CACHE_LIMIT = 1024 * 1024; // 1MB cache limit

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Wrap request and response to cache content (with cache limit for request)
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request, CONTENT_CACHE_LIMIT);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            // Continue with filter chain first
            filterChain.doFilter(requestWrapper, responseWrapper);

        } finally {
            // Calculate execution time
            long executionTime = System.currentTimeMillis() - startTime;

            // Log request and response details after chain completes
            logRequest(requestWrapper);
            logResponse(responseWrapper, executionTime);

            // IMPORTANT: Copy cached body to actual response
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder requestLog = new StringBuilder();
        requestLog.append("\n=== INCOMING REQUEST ===\n");
        requestLog.append("Method: ").append(request.getMethod()).append("\n");
        requestLog.append("URI: ").append(request.getRequestURI()).append("\n");

        // Log query parameters
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestLog.append("Query String: ").append(queryString).append("\n");
        }

        // Log headers
        requestLog.append("Headers:\n");
        Collections.list(request.getHeaderNames()).forEach(headerName -> {
            String headerValue = request.getHeader(headerName);
            // Mask sensitive headers
            if (headerName.equalsIgnoreCase("Authorization") ||
                    headerName.equalsIgnoreCase("Cookie")) {
                headerValue = "***MASKED***";
            }
            requestLog.append("  ").append(headerName).append(": ").append(headerValue).append("\n");
        });

        // Log request body (must be called AFTER filterChain.doFilter)
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            String requestBody = getContentAsString(content, request.getCharacterEncoding());
            if (requestBody.length() > MAX_PAYLOAD_LENGTH) {
                requestBody = requestBody.substring(0, MAX_PAYLOAD_LENGTH) + "... (truncated)";
            }
            requestLog.append("Request Body:\n").append(requestBody).append("\n");
        }

        requestLog.append("========================");
        logger.info(requestLog.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response, long executionTime) {
        StringBuilder responseLog = new StringBuilder();
        responseLog.append("\n=== OUTGOING RESPONSE ===\n");
        responseLog.append("Status: ").append(response.getStatus()).append("\n");
        responseLog.append("Execution Time: ").append(executionTime).append(" ms\n");

        // Log response headers
        responseLog.append("Headers:\n");
        response.getHeaderNames().forEach(headerName -> {
            String headerValue = response.getHeader(headerName);
            responseLog.append("  ").append(headerName).append(": ").append(headerValue).append("\n");
        });

        // Log response body
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            String responseBody = getContentAsString(content, response.getCharacterEncoding());
            if (responseBody.length() > MAX_PAYLOAD_LENGTH) {
                responseBody = responseBody.substring(0, MAX_PAYLOAD_LENGTH) + "... (truncated)";
            }
            responseLog.append("Response Body:\n").append(responseBody).append("\n");
        }

        responseLog.append("=========================");
        logger.info(responseLog.toString());
    }

    private String getContentAsString(byte[] content, String encoding) {
        try {
            return new String(content, encoding != null ? encoding : "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error decoding content", e);
            return "[Error decoding content]";
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Optional: Skip logging for certain paths like health checks
        String path = request.getRequestURI();
        return path.startsWith("/actuator") ||
                path.startsWith("/health") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".ico");
    }
}
