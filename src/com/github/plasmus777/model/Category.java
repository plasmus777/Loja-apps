package com.github.plasmus777.model;

public enum Category{
    INTERNET, UTILITIES, SYSTEM, AUDIO, VIDEO, EDUCATION, GRAPHICS, OFICCE, DEVELOPMENT, PRODUCTIVITY, GAMES;

    @Override
    public String toString() {
        return switch (this) {
            case INTERNET -> "Internet";
            case UTILITIES -> "Utilidades";
            case SYSTEM -> "Sistema";
            case AUDIO -> "Áudio";
            case VIDEO -> "Vídeo";
            case EDUCATION -> "Educação";
            case GRAPHICS -> "Gráficos";
            case OFICCE -> "Office";
            case DEVELOPMENT -> "Desenvolvimento";
            case PRODUCTIVITY -> "Produtividade";
            case GAMES -> "Jogos";
            default -> "Categoria Desconhecida";
        };
    }
}
