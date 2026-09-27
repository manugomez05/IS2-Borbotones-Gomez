package com.gomez.dto;

import com.gomez.entity.MedioPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PagoCuotaDto {
    @NotNull private Long grupoFamiliarId;
    @NotNull @DecimalMin("0.01") private BigDecimal importe;
    @NotNull private MedioPago medioPago;
    public Long getGrupoFamiliarId() { return grupoFamiliarId; }
    public void setGrupoFamiliarId(Long grupoFamiliarId) { this.grupoFamiliarId = grupoFamiliarId; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public MedioPago getMedioPago() { return medioPago; }
    public void setMedioPago(MedioPago medioPago) { this.medioPago = medioPago; }
}
