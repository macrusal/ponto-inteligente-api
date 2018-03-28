CREATE TABLE `empresa` (
  `id`               BIGINT(20)   NOT NULL,
  `cnpj`             VARCHAR(255) NOT NULL,
  `data_atualizacao` DATETIME     NOT NULL,
  `data_criacao`     DATETIME     NOT NULL,
  `razao_social`     VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `funcionario` (
  `id`                     BIGINT(20)   NOT NULL,
  `cpf`                    VARCHAR(255) NOT NULL,
  `data_atualizacao`       DATETIME     NOT NULL,
  `data_criacao`           DATETIME     NOT NULL,
  `email`                  VARCHAR(255) NOT NULL,
  `nome`                   VARCHAR(255) NOT NULL,
  `perfil`                 VARCHAR(255) NOT NULL,
  `qtd_horas_almoco`       FLOAT          DEFAULT NULL,
  `qtd_horas_trabalho_dia` FLOAT          DEFAULT NULL,
  `senha`                  VARCHAR(255) NOT NULL,
  `valor_hora`             DECIMAL(19, 2) DEFAULT NULL,
  `empresa_id`             BIGINT(20)     DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `lancamento` (
  `id`               BIGINT(20)   NOT NULL,
  `data`             DATETIME     NOT NULL,
  `data_atualizacao` DATETIME     NOT NULL,
  `data_criacao`     DATETIME     NOT NULL,
  `descricao`        VARCHAR(255) DEFAULT NULL,
  `localizacao`      VARCHAR(255) DEFAULT NULL,
  `tipo`             VARCHAR(255) NOT NULL,
  `funcionario_id`   BIGINT(20)   DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Indexes for table `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `funcionario`
--
ALTER TABLE `funcionario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4cm1kg523jlopyexjbmi6y54j` (`empresa_id`);

--
-- Indexes for table `lancamento`
--
ALTER TABLE `lancamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK46i4k5vl8wah7feutye9kbpi4` (`funcionario_id`);

--
-- AUTO_INCREMENT for table `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `funcionario`
--
ALTER TABLE `funcionario`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `lancamento`
--
ALTER TABLE `lancamento`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `funcionario`
--
ALTER TABLE `funcionario`
  ADD CONSTRAINT `FK4cm1kg523jlopyexjbmi6y54j` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`);

--
-- Constraints for table `lancamento`
--
ALTER TABLE `lancamento`
  ADD CONSTRAINT `FK46i4k5vl8wah7feutye9kbpi4` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionario` (`id`);
