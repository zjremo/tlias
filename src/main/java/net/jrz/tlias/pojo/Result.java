package net.jrz.tlias.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success() {
        Result res = new Result();
        res.code = 1;
        res.msg = "success";

        return res;
    }

    public static Result success(Object data) {
        Result res = success();
        res.data = data;
        return res;
    }

    public static Result error(String msg) {
        Result res = new Result();
        res.code = 0;
        res.msg = msg;

        return res;
    }
}

