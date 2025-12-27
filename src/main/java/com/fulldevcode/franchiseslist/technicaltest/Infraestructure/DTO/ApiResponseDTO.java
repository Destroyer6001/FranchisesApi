package com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponseDTO(){this.timestamp = LocalDateTime.now();}

    public ApiResponseDTO(boolean Success, String Message, T Data)
    {
        this.success = Success;
        this.message = Message;
        this.data = Data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponseDTO<T> Success(String Message, T Data)
    {
        return new ApiResponseDTO<>(true, Message, Data);
    }

    public static <T> ApiResponseDTO<T> Error (String message)
    {
        return  new ApiResponseDTO<>(false, message, null);
    }

}

