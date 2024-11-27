package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ArquivoUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Salva um objeto em um arquivo JSON
    public static void salvarEstado(Object estado, String caminhoArquivo) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(caminhoArquivo), estado);
    }

    // Carrega um objeto de um arquivo JSON
    public static <T> T carregarEstado(String caminhoArquivo, Class<T> tipo) throws IOException {
        return objectMapper.readValue(new File(caminhoArquivo), tipo);
    }
}
