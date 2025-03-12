package com.itau.cadastro_chave_pix.domains;

import com.itau.cadastro_chave_pix.domains.enums.StatusChave;
import com.itau.cadastro_chave_pix.domains.enums.TipoPessoa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chaves_pix")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChavePix {

    @Id
    @GeneratedValue
    private UUID id;  // Chave primária no formato UUID

    @Column(nullable = false, length = 9)
    private String tipoChave; // celular, email, cpf, etc.

    @Column(nullable = false, unique = true, length = 77)
    private String valorChave;

    @Column(nullable = false, length = 10)
    private String tipoConta; // corrente ou poupança

    @Column(nullable = false)
    private Integer numeroAgencia;

    @Column(nullable = false)
    private Integer numeroConta;

    @Column(nullable = false, length = 30)
    private String nomeCorrentista;

    @Column(length = 45)
    private String sobrenomeCorrentista;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @Enumerated(EnumType.STRING)
    private StatusChave status;

    @Column(nullable = false)
    private LocalDateTime dataHoraInclusao;

    private LocalDateTime dataHoraInativacao;

    @PrePersist
    public void prePersist() {
        this.dataHoraInclusao = LocalDateTime.now();
        this.status = StatusChave.ATIVA;
    }
}
