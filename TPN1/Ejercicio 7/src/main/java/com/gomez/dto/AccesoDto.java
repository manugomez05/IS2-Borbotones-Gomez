package com.gomez.dto;

import jakarta.validation.constraints.NotNull;

public class AccesoDto {
    @NotNull private Long socioId;
    private String imagenBase64;
    public Long getSocioId() { return socioId; }
    public void setSocioId(Long socioId) { this.socioId = socioId; }
    public String getImagenBase64() { return imagenBase64; }
    public void setImagenBase64(String imagenBase64) { this.imagenBase64 = imagenBase64; }
}
