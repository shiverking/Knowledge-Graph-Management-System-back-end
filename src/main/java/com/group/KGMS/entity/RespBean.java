package com.group.KGMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @作者 闫崇傲
 * @用途 后台返回json数据类
 * @修改时间 2022/9/19
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class RespBean {
    private static final String SUCCESS = "成功";
    private static final String ERROR = "失败";
    private String msg = "";
    /**
     * 数据总条数
     */
    private Long count = 0L;
    /**
     * 返回是否成功
     */
    private Boolean result = false;
    /**
     * 返回数据信息
     */
    private Object data;

    private RespBean() {
    }

    public static RespBean result(Boolean result, String msg, Object data, Long count) {
        RespBean RespBean = new RespBean();
        RespBean.setResult(result);
        RespBean.setMsg(msg);
        RespBean.setData(data);
        RespBean.setCount(count);
        return RespBean;
    }

    /**
     * 成功的响应
     *
     * @return
     */
    public static RespBean success() {
        return result(true, SUCCESS, null, null);
    }

    public static RespBean success(String msg) {
        return result(true, msg, null, null);
    }

    public static RespBean success(Object data) {
        return result(true, SUCCESS, data, null);
    }

    public static RespBean success(Object data, Long count) {
        return result(true, SUCCESS, data, count);
    }


    public static RespBean success(String msg, Object data) {
        return result(true, msg, data, null);
    }

    public static RespBean success(String msg, Object data, Long count) {
        return result(true, msg, data, count);
    }

    /**
     * 失败的响应
     *
     * @param msg
     * @return
     */
    public static RespBean error() {
        return result(false, ERROR, null, null);
    }

    public static RespBean error(String msg) {
        return result(false, msg, null, null);
    }

    public static RespBean error(Object data) {
        return result(false, ERROR, data, null);
    }

    public static RespBean error(Object data, Long count) {
        return result(false, ERROR, data, count);
    }

    public static RespBean error(String msg, Object data) {
        return result(false, msg, data, null);
    }

    public static RespBean error(String msg, Object data, Long count) {
        return result(false, msg, data, count);
    }
}
