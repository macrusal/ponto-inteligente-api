package br.com.hibejix.pontointeligente.api.dtos;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 27/03/2018 03:02
 */
public class EmpresaDTO {

    private Long id;
    private String razaoSocial;
    private String cnpj;

    public EmpresaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "EmpresaDto [id=" + id + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
    }
}
