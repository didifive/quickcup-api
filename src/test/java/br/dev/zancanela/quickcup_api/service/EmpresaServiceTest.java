package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Empresa;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.EmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.when;

class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaService empresaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateEmpresa() {
        Empresa novosDados = new Empresa();
        novosDados.setId((short) 1);
        novosDados.setNome("Nova Empresa");

        when(empresaRepository.save(any(Empresa.class))).thenReturn(novosDados);

        Empresa updatedEmpresa = empresaService.update(novosDados);

        assertThat(updatedEmpresa).isNotNull();
        assertThat(updatedEmpresa.getNome()).isEqualTo("Nova Empresa");
    }

    @Test
    void testGetByIdFound() {
        Empresa empresa = new Empresa();
        empresa.setId((short) 1);
        empresa.setNome("Empresa Teste");

        when(empresaRepository.findById(anyShort())).thenReturn(Optional.of(empresa));

        Empresa foundEmpresa = empresaService.getById((short) 1);

        assertThat(foundEmpresa).isNotNull();
        assertThat(foundEmpresa.getId()).isEqualTo((short) 1);
        assertThat(foundEmpresa.getNome()).isEqualTo("Empresa Teste");
    }

    @Test
    void testGetByIdNotFound() {
        when(empresaRepository.findById(anyShort())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> empresaService.getById((short) 1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Empresa não encontrada");
    }

    @Test
    void testGetEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId((short) 1);
        empresa.setNome("Empresa Teste");
        List<Empresa> empresaList = Collections.singletonList(empresa);

        when(empresaRepository.findAll()).thenReturn(empresaList);

        Empresa foundEmpresa = empresaService.getEmpresa();

        assertThat(foundEmpresa).isNotNull();
        assertThat(foundEmpresa.getNome()).isEqualTo("Empresa Teste");
    }
}
