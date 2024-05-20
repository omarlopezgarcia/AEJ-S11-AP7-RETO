package pe.edu.vallegrande.app.exception;

import lombok.Data;

@Data
public class Error {
    private String code;
    private String message;

}