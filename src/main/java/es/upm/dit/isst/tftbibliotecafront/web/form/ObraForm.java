package es.upm.dit.isst.tftbibliotecafront.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObraForm {

    @NotBlank
    @Size(max = 200)
    private String autor;

    @NotBlank
    @Size(max = 250)
    private String titulo;

    @NotBlank
    @Size(max = 4000)
    private String resumen;

    @NotBlank
    @Size(max = 1000)
    private String palabrasClaveCsv;
}

