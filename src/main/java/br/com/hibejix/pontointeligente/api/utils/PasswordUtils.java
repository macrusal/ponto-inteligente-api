package br.com.hibejix.pontointeligente.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 23/03/2018 22:48
 */
public class PasswordUtils {

    private static final Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

    public PasswordUtils() {

    }

    public static String gerarBCrypt(final String senha) {
        if (senha == null) {
            return senha;
        }

        logger.info("Gerando hash com BCrypt");
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        return bcryptEncoder.encode(senha);
    }

}
