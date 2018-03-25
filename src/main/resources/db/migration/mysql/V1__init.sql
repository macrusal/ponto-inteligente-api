CREATE TABLE empresa (
  empresa_id       BIGINT(20)   NOT NULL,
  cnpj             VARCHAR(255) NOT NULL,
  data_atualizacao DATETIME     NOT NULL,
  data_criacao     DATETIME     NOT NULL,
  razao_social     VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE funcionario (
  funcionario_id         BIGINT(20)   NOT NULL,
  cpf                    VARCHAR(255) NOT NULL,
  data_atualizacao       DATETIME     NOT NULL,
  data_criacao           DATETIME     NOT NULL,
  email                  VARCHAR(255) NOT NULL,
  nome                   VARCHAR(255) NOT NULL,
  perfil                 VARCHAR(255) NOT NULL,
  qtd_horas_almoco       FLOAT          DEFAULT NULL,
  qtd_horas_trabalho_dia FLOAT          DEFAULT NULL,
  senha                  VARCHAR(255) NOT NULL,
  valor_hora             DECIMAL(19, 2) DEFAULT NULL,
  empresa_id             BIGINT(20)     DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE lancamento (
  lancamento_id    BIGINT(20)   NOT NULL,
  data             DATETIME     NOT NULL,
  data_atualizacao DATETIME     NOT NULL,
  data_criacao     DATETIME     NOT NULL,
  descricao        VARCHAR(255) DEFAULT NULL,
  localizacao      VARCHAR(255) DEFAULT NULL,
  tipo             VARCHAR(255) NOT NULL,
  funcionario_id   BIGINT(20)   DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Indexes for table empresa
--
ALTER TABLE empresa
  ADD PRIMARY KEY (empresa_id);

--
-- Indexes for table funcionario
--
ALTER TABLE funcionario
  ADD PRIMARY KEY (funcionario_id);
-- ADD KEY 'FK_FUNC_EMPRESA' (empresa_id);

--
-- Indexes for table lancamento
--
ALTER TABLE lancamento
  ADD PRIMARY KEY (lancamento_id);
-- ADD KEY 'FK_LCTO_FUNC' (funcionario_id);

--
-- AUTO_INCREMENT for table empresa
--
ALTER TABLE empresa
  MODIFY empresa_id BIGINT(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table funcionario
--
ALTER TABLE funcionario
  MODIFY funcionario_id BIGINT(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table lancamento
--
ALTER TABLE lancamento
  MODIFY lancamento_id BIGINT(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table funcionario
--
ALTER TABLE funcionario
  ADD CONSTRAINT FK_FUNC_EMPRESA FOREIGN KEY (empresa_id) REFERENCES empresa (empresa_id);

--
-- Constraints for table lancamento
--
ALTER TABLE lancamento
  ADD CONSTRAINT FK_LCTO_FUNC FOREIGN KEY (funcionario_id) REFERENCES funcionario (funcionario_id);