package es.upm.dit.isst.tftbibliotecafront.web;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public final class UiMessageHelper {

    private UiMessageHelper() {
    }

    public static void success(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("successMessage", message);
    }

    public static void error(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("errorMessage", message);
    }
}

