package br.com.hibejix.pontointeligente.api.response;

import java.util.List;

/**
 * @author msalvador
 * @version $Revision: $<br/>
 *          $Id: $
 * @since 25/03/2018 16:03
 */
public class Response<T> {

    private T data;
    private List<String> errors;

    public Response() {
        
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
