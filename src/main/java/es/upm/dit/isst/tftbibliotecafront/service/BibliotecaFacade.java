package es.upm.dit.isst.tftbibliotecafront.service;

import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import es.upm.dit.isst.tftbibliotecafront.config.TftBibliotecaProperties;
import es.upm.dit.isst.tftbibliotecafront.model.Obra;

@Service
public class BibliotecaFacade {

    private static final String OBRAS_PATH = "/biblioteca/obras";
    private static final ParameterizedTypeReference<List<Obra>> OBRAS_TYPE = new ParameterizedTypeReference<>() {
    };

    private final RestClient restClient;
    private final String baseUrl;

    public BibliotecaFacade(RestClient restClient, TftBibliotecaProperties properties) {
        this.restClient = restClient;
        this.baseUrl = normalizeBaseUrl(properties.getBaseUrl());
    }

    public List<Obra> listar(String autor) {
        String uri = UriComponentsBuilder.fromUriString(baseUrl + OBRAS_PATH)
                .queryParamIfPresent("autor", Optional.ofNullable(blankToNull(autor)))
                .toUriString();
        List<Obra> obras = restClient.get()
                .uri(uri)
                .retrieve()
                .body(OBRAS_TYPE);
        return obras == null ? List.of() : obras;
    }

    public Obra consultar(Long id) {
        return restClient.get()
                .uri(baseUrl + OBRAS_PATH + "/" + id)
                .retrieve()
                .body(Obra.class);
    }

    public Obra crear(Obra obra) {
        return restClient.post()
                .uri(baseUrl + OBRAS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(obra)
                .retrieve()
                .body(Obra.class);
    }

    public Obra subirDigital(Long id, byte[] pdf) {
        return restClient.put()
                .uri(baseUrl + OBRAS_PATH + "/" + id + "/digital")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf)
                .retrieve()
                .body(Obra.class);
    }

    public String downloadUrl(Long id) {
        return baseUrl + OBRAS_PATH + "/" + id + "/digital";
    }

    private String normalizeBaseUrl(String rawBaseUrl) {
        if (rawBaseUrl == null || rawBaseUrl.isBlank()) {
            return "http://localhost:8070";
        }
        return rawBaseUrl.endsWith("/") ? rawBaseUrl.substring(0, rawBaseUrl.length() - 1) : rawBaseUrl;
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }
}
