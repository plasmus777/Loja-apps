package com.github.plasmus777.model.application;

public enum Category{
    INTERNET, UTILITIES, SYSTEM, AUDIO, VIDEO, EDUCATION, GRAPHICS, OFFICE, DEVELOPMENT, PRODUCTIVITY, GAMES, OUTROS;

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
            case OFFICE -> "Office";
            case DEVELOPMENT -> "Desenvolvimento";
            case PRODUCTIVITY -> "Produtividade";
            case GAMES -> "Jogos";
            default -> "Outros";
        };
    }
}
