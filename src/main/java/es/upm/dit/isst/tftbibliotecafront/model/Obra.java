package es.upm.dit.isst.tftbibliotecafront.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Obra {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String autor;

    @NotBlank
    @Size(max = 250)
    private String titulo;

    @NotBlank
    @Size(max = 4000)
    private String resumen;

    @NotEmpty
    @Size(max = 10)
    @Default
    private List<@NotBlank @Size(max = 50) String> palabrasClave = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate fechaDeposito;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String urlCopiaDigital;
}
