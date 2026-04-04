package es.upm.dit.isst.tftbibliotecafront.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.upm.dit.isst.tftbibliotecafront.model.Obra;
import es.upm.dit.isst.tftbibliotecafront.service.BibliotecaFacade;
import es.upm.dit.isst.tftbibliotecafront.service.BibliotecaHttpMessages;
import es.upm.dit.isst.tftbibliotecafront.web.form.ObraForm;
import es.upm.dit.isst.tftbibliotecafront.web.form.SubirDigitalForm;
import jakarta.validation.Valid;

@Controller
public class ObrasController {

    private final BibliotecaFacade bibliotecaFacade;

    public ObrasController(BibliotecaFacade bibliotecaFacade) {
        this.bibliotecaFacade = bibliotecaFacade;
    }

    @ModelAttribute("obraForm")
    ObraForm obraForm() {
        return new ObraForm();
    }

    @ModelAttribute("subirDigitalForm")
    SubirDigitalForm subirDigitalForm() {
        return new SubirDigitalForm();
    }

    @GetMapping("/obras")
    public String obras(
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) Long obraId,
            @ModelAttribute("subirDigitalForm") SubirDigitalForm subirDigitalForm,
            Model model) {
        if (subirDigitalForm.getId() == null && obraId != null) {
            subirDigitalForm.setId(obraId);
        }
        loadPage(autor, obraId, model);
        return "obras";
    }

    @PostMapping("/obras/crear")
    public String crearObra(
            @Valid @ModelAttribute("obraForm") ObraForm obraForm,
            BindingResult bindingResult,
            @RequestParam(required = false) String filterAutor,
            Model model,
            RedirectAttributes redirectAttributes) {
        List<String> palabrasClave = parsePalabrasClave(obraForm.getPalabrasClaveCsv(), bindingResult);
        if (bindingResult.hasErrors()) {
            loadPage(filterAutor, null, model);
            model.addAttribute("pageTitle", "Obras");
            return "obras";
        }

        Obra obra = Obra.builder()
                .autor(obraForm.getAutor())
                .titulo(obraForm.getTitulo())
                .resumen(obraForm.getResumen())
                .palabrasClave(palabrasClave)
                .build();

        try {
            Obra creada = bibliotecaFacade.crear(obra);
            UiMessageHelper.success(redirectAttributes, "Obra creada correctamente.");
            return "redirect:/obras?obraId=" + creada.getId();
        } catch (RestClientResponseException ex) {
            model.addAttribute("pageError", BibliotecaHttpMessages.userMessage(ex));
            loadPage(filterAutor, null, model);
            model.addAttribute("pageTitle", "Obras");
            return "obras";
        }
    }

    @PostMapping("/obras/subir-digital")
    public String subirDigital(
            @Valid @ModelAttribute("subirDigitalForm") SubirDigitalForm form,
            BindingResult bindingResult,
            @RequestParam(required = false) String filterAutor,
            Model model,
            RedirectAttributes redirectAttributes) {
        validatePdf(form.getPdf(), bindingResult);
        if (bindingResult.hasErrors()) {
            loadPage(filterAutor, form.getId(), model);
            model.addAttribute("pageTitle", "Obras");
            return "obras";
        }

        try {
            bibliotecaFacade.subirDigital(form.getId(), form.getPdf().getBytes());
            UiMessageHelper.success(redirectAttributes, "Copia digital subida correctamente.");
            return "redirect:/obras?obraId=" + form.getId();
        } catch (IOException ex) {
            model.addAttribute("pageError", "No se pudo leer el PDF seleccionado.");
            loadPage(filterAutor, form.getId(), model);
            model.addAttribute("pageTitle", "Obras");
            return "obras";
        } catch (RestClientResponseException ex) {
            model.addAttribute("pageError", BibliotecaHttpMessages.userMessage(ex));
            loadPage(filterAutor, form.getId(), model);
            model.addAttribute("pageTitle", "Obras");
            return "obras";
        }
    }

    @GetMapping("/obras/{id}/descargar")
    public String descargar(@PathVariable Long id) {
        return "redirect:" + bibliotecaFacade.downloadUrl(id);
    }

    private void validatePdf(MultipartFile pdf, BindingResult bindingResult) {
        if (pdf == null || pdf.isEmpty()) {
            bindingResult.rejectValue("pdf", "pdf.empty", "Debes seleccionar un PDF.");
            return;
        }
        if (!isPdf(pdf)) {
            bindingResult.rejectValue("pdf", "pdf.type", "El fichero debe ser un PDF.");
        }
    }

    private boolean isPdf(MultipartFile pdf) {
        String contentType = pdf.getContentType();
        if (contentType != null && MediaType.APPLICATION_PDF_VALUE.equalsIgnoreCase(contentType)) {
            return true;
        }
        String filename = pdf.getOriginalFilename();
        return filename != null && filename.toLowerCase(Locale.ROOT).endsWith(".pdf");
    }

    private List<String> parsePalabrasClave(String csv, BindingResult bindingResult) {
        if (csv == null) {
            bindingResult.rejectValue("palabrasClaveCsv", "palabrasClave.empty", "Introduce al menos una palabra clave.");
            return List.of();
        }

        List<String> palabrasClave = Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(token -> !token.isBlank())
                .distinct()
                .toList();

        if (palabrasClave.isEmpty()) {
            bindingResult.rejectValue("palabrasClaveCsv", "palabrasClave.empty", "Introduce al menos una palabra clave.");
            return List.of();
        }
        if (palabrasClave.size() > 10) {
            bindingResult.rejectValue("palabrasClaveCsv", "palabrasClave.max", "No se permiten mas de 10 palabras clave.");
        }
        boolean tooLong = palabrasClave.stream().anyMatch(token -> token.length() > 50);
        if (tooLong) {
            bindingResult.rejectValue(
                    "palabrasClaveCsv",
                    "palabrasClave.item.max",
                    "Cada palabra clave debe tener como maximo 50 caracteres.");
        }
        return palabrasClave;
    }

    private void loadPage(String autor, Long obraId, Model model) {
        model.addAttribute("pageTitle", "Obras");
        model.addAttribute("filterAutor", autor == null ? "" : autor);
        model.addAttribute("selectedObra", null);
        model.addAttribute("selectedObraId", obraId);
        model.addAttribute("obras", List.of());

        try {
            model.addAttribute("obras", bibliotecaFacade.listar(blankToNull(autor)));
        } catch (RestClientResponseException ex) {
            model.addAttribute("pageError", BibliotecaHttpMessages.userMessage(ex));
            return;
        }

        if (obraId == null) {
            return;
        }

        try {
            model.addAttribute("selectedObra", bibliotecaFacade.consultar(obraId));
        } catch (RestClientResponseException ex) {
            model.addAttribute("pageError", BibliotecaHttpMessages.userMessage(ex));
        }
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }
}

