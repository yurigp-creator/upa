package br.ceub.poo.upa.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PacienteGrupoTest {

    private Paciente criarPacienteTriado(Prioridade prioridade, int senha, LocalDateTime hora) throws Exception {
        Paciente p = new Paciente("Teste", "05298224725", LocalDate.of(1990, 1, 1), senha, hora);
        SinaisVitais sv = new SinaisVitais(36.5, 80, 120, 80, 98, 0, true);
        Enfermeiro enf = new Enfermeiro("Enf", "11122233344", LocalDate.of(1980, 1, 1), "COREN-123");
        Triagem t = new Triagem("Dor", sv, enf, prioridade, hora);
        p.registrarTriagem(t);
        return p;
    }

    @Test
    @DisplayName("1. Vermelho deve vir antes de Azul no compareTo")
    void vermelhoVemAntesDeAzul() throws Exception {
        LocalDateTime agora = LocalDateTime.now();
        Paciente pVermelho = criarPacienteTriado(Prioridade.VERMELHO, 1, agora);
        Paciente pAzul = criarPacienteTriado(Prioridade.AZUL, 2, agora);

        assertTrue(pVermelho.compareTo(pAzul) < 0);
    }

    @Test
    @DisplayName("2. Empate de cor desempata por ordem de chegada (FIFO)")
    void empateCorDesempataPorChegada() throws Exception {
        LocalDateTime agora = LocalDateTime.now();
        Paciente p1 = criarPacienteTriado(Prioridade.AMARELO, 1, agora.minusMinutes(10));
        Paciente p2 = criarPacienteTriado(Prioridade.AMARELO, 2, agora);

        assertTrue(p1.compareTo(p2) < 0);
    }

    @Test
    @DisplayName("3. Tentar triar paciente já triado lança IllegalStateException")
    void triarNovamenteLancaExcecao() throws Exception {
        LocalDateTime agora = LocalDateTime.now();
        Paciente p = criarPacienteTriado(Prioridade.VERDE, 1, agora);

        assertThrows(IllegalStateException.class, () -> p.registrarTriagem(p.getTriagem()));
    }

    @Test
    @DisplayName("4. Marcação de atendido altera o status corretamente")
    void marcarComoAtendidoAlteraStatus() throws Exception {
        LocalDateTime agora = LocalDateTime.now();
        Paciente p = criarPacienteTriado(Prioridade.VERDE, 1, agora);
        p.marcarComoAtendido();

        assertEquals(StatusPaciente.ATENDIDO, p.getStatus());
    }

    @Test
    @DisplayName("5. Tentar atender paciente não triado lança IllegalStateException")
    void atenderSemTriagemLancaExcecao() {
        Paciente p = new Paciente("Teste", "05298224725", LocalDate.of(1990, 1, 1), 1, LocalDateTime.now());

        assertThrows(IllegalStateException.class, p::marcarComoAtendido);
    }

    @Test
    @DisplayName("6. Formatação de senha com 3 dígitos (P001)")
    void formatacaoSenhaTresDigitos() {
        Paciente p = new Paciente("Teste", "05298224725", LocalDate.of(1990, 1, 1), 5, LocalDateTime.now());
        assertEquals("P005", p.getSenhaFormatada());
    }

    @Test
    @DisplayName("7. Identificação do médico deve conter CRM e consultório")
    void identificacaoMedicoFormatada() {
        Medico m = new Medico("Dr. Carlos", "11122233344", LocalDate.of(1980, 1, 1), "CRM-DF 123", 3);
        assertTrue(m.getIdentificacao().contains("CRM-DF 123"));
        assertTrue(m.getIdentificacao().contains("Consultório 3"));
    }

    @Test
    @DisplayName("8. Atendimento calcula corretamente o tempo de espera dentro do prazo")
    void atendimentoCalculaTempoEDentroDoPrazo() throws Exception {
        LocalDateTime chegada = LocalDateTime.now().minusMinutes(5);
        Paciente p = criarPacienteTriado(Prioridade.LARANJA, 1, chegada); // Laranja = max 10 min
        Medico m = new Medico("Dr. Carlos", "11122233344", LocalDate.of(1980, 1, 1), "CRM-DF 123", 1);

        Atendimento a = new Atendimento(p, m, LocalDateTime.now());

        assertEquals(5, a.getTempoEsperaMin());
        assertTrue(a.isDentroDoPrazo());
    }
}