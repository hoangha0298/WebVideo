package com.example.demo.model.response;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

    /*
    1.
    code type = success begin 200 -> 299
    code type = error client begin 400 -> 499
    code type = error server begin 500 -> 600

    2.
    cannot initialize from outside class. If you need to add a new ResponseType, create it below
    */

@Getter
@ToString
public class ResponseType implements Cloneable {

    private final int id;

    private final TypeEnum type;

    @Setter
    private String message;

    private final static Set<ResponseType> allResponseType = new HashSet<>();

    private ResponseType(int id, TypeEnum type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;

        if (allResponseType.contains(this)) {
            throw new RuntimeException(String.format("ID %s already exist!", id));
        }

        allResponseType.add(this);
    }

    // type success
    public static ResponseType SUCCESS = new ResponseType(200, TypeEnum.SUCCESS, "Thành công");

    public static ResponseType SUCCESS_AND_CONTINUE = new ResponseType(201, TypeEnum.SUCCESS, "Thành công và đang thực hiện tiếp tác vụ");

    // type error client
    public static ResponseType ERROR_CLIENT = new ResponseType(400, TypeEnum.ERROR_CLIENT, "Client gửi thông tin lỗi");

    public static ResponseType UNKNOWN_SOURCE = new ResponseType(404, TypeEnum.ERROR_CLIENT, "Không tìm thấy tài nguyên yêu cầu");

    public static ResponseType SOURCE_IS_NOT_FOLDER = new ResponseType(410, TypeEnum.ERROR_CLIENT, "Tài nguyên yêu cầu không phải thư mục");

    // type error server
    public static ResponseType ERROR_SERVER = new ResponseType(500, TypeEnum.ERROR_SERVER, "Lỗi server");

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseType that = (ResponseType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ResponseType clone() {
        try {
            return (ResponseType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public enum TypeEnum {
        SUCCESS, ERROR_CLIENT, ERROR_SERVER, WARNING
    }
}
