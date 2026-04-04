package es.upm.dit.isst.tftbibliotecafront.service;

import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class BibliotecaHttpMessages {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private BibliotecaHttpMessages() {
    }

    public static String userMessage(RestClientResponseException ex) {
        String body = ex.getResponseBodyAsString();
        if (body != null && !body.isBlank()) {
            String trimmed = body.trim();
            if (looksLikeJson(trimmed)) {
                try {
                    JsonNode root = OBJECT_MAPPER.readTree(trimmed);
                    if (root.hasNonNull("detail")) {
                        return root.get("detail").asText();
                    }
                    if (root.hasNonNull("title")) {
                        return root.get("title").asText();
                    }
                } catch (Exception ignored) {
                    // Si no es JSON valido, se devuelve como texto plano.
                }
            }
            return trimmed;
        }

        String statusText = ex.getStatusText();
        return statusText == null || statusText.isBlank()
                ? "No se pudo completar la operacion."
                : statusText;
    }

    private static boolean looksLikeJson(String content) {
        return content.startsWith("{") || content.startsWith("[");
    }
}

