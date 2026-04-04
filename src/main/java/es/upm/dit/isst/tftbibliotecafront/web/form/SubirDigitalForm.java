package es.upm.dit.isst.tftbibliotecafront.web.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubirDigitalForm {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    private MultipartFile pdf;
}

