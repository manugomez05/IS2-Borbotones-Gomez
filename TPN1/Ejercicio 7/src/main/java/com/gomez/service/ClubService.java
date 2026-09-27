package com.gomez.service;

import com.gomez.dto.*;
import com.gomez.entity.*;
import com.gomez.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class ClubService {
    private final SocioRepository socioRepository;
    private final GrupoFamiliarRepository grupoRepository;
    private final RegistroAccesoRepository accesoRepository;
    private final PagoCuotaRepository pagoRepository;

    public ClubService(SocioRepository socioRepository, GrupoFamiliarRepository grupoRepository,
                       RegistroAccesoRepository accesoRepository, PagoCuotaRepository pagoRepository) {
        this.socioRepository = socioRepository; this.grupoRepository = grupoRepository;
        this.accesoRepository = accesoRepository; this.pagoRepository = pagoRepository;
    }

    @Transactional(readOnly = true)
    public List<Socio> socios() { return socioRepository.findAllByOrderByApellidoAscNombreAsc(); }
    @Transactional(readOnly = true)
    public List<GrupoFamiliar> grupos() { return grupoRepository.findAll(); }
    @Transactional(readOnly = true)
    public List<RegistroAcceso> accesos() { return accesoRepository.findTop20ByOrderByIngresoDesc(); }
    @Transactional(readOnly = true)
    public List<PagoCuota> pagos() { return pagoRepository.findTop20ByOrderByFechaDesc(); }

    @Transactional
    public void registrarSocio(SocioDto dto) {
        GrupoFamiliar grupo = grupoRepository.findById(dto.getGrupoFamiliarId())
                .orElseThrow(() -> new IllegalArgumentException("El grupo familiar no existe."));
        Socio socio = new Socio(); socio.setNombre(dto.getNombre()); socio.setApellido(dto.getApellido());
        socio.setDocumento(dto.getDocumento()); socio.setFechaNacimiento(dto.getFechaNacimiento());
        socio.setGrupoFamiliar(grupo); socioRepository.save(socio);
    }

    @Transactional
    public void registrarIngreso(AccesoDto dto) {
        Socio socio = socioRepository.findById(dto.getSocioId())
                .orElseThrow(() -> new IllegalArgumentException("El socio no existe."));
        RegistroAcceso acceso = new RegistroAcceso(); acceso.setSocio(socio); acceso.setIngreso(LocalDateTime.now());
        if (dto.getImagenBase64() != null && !dto.getImagenBase64().isBlank()) {
            acceso.setImagenRostro(Base64.getDecoder().decode(dto.getImagenBase64()));
        }
        accesoRepository.save(acceso);
    }

    @Transactional
    public void registrarEgreso(Long accesoId) {
        RegistroAcceso acceso = accesoRepository.findById(accesoId)
                .orElseThrow(() -> new IllegalArgumentException("El registro de acceso no existe."));
        acceso.setEgreso(LocalDateTime.now()); accesoRepository.save(acceso);
    }

    @Transactional
    public void registrarPago(PagoCuotaDto dto) {
        GrupoFamiliar grupo = grupoRepository.findById(dto.getGrupoFamiliarId())
                .orElseThrow(() -> new IllegalArgumentException("El grupo familiar no existe."));
        PagoCuota pago = new PagoCuota(); pago.setGrupoFamiliar(grupo); pago.setImporte(dto.getImporte());
        pago.setMedioPago(dto.getMedioPago()); pago.setFecha(LocalDate.now()); pagoRepository.save(pago);
    }
}
