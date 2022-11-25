package com.group.KGMS.utils;

import lombok.Data;

@Data
public class JsonResult {

    private static final String SUCCESS = "成功";
    private static final String ERROR = "失败";

    /**
     * 响应状态code,可以自定义成想要的值(简短信息)
     */
    private Integer code = 0;

    /**
     * 数据总条数
     */
    private Long count = 0L;

    /**
     * 返回是否成功
     */
    private Boolean result = false;

    /**
     * 返回提示信息
     */
    private String msg = "";

    /**
     * 返回数据信息
     */
    private Object data;

    private JsonResult() {
    }

    /**
     * 成功的响应
     *
     * @return
     */
    public static com.group.KGMS.utils.JsonResult success() {
        return result(true, SUCCESS, null, null);
    }

    public static com.group.KGMS.utils.JsonResult success(String msg) {
        return result(true, msg, null, null);
    }

    public static com.group.KGMS.utils.JsonResult success(Object data) {
        return result(true, SUCCESS, data, null);
    }

    public static com.group.KGMS.utils.JsonResult success(Object data, Long count) {
        return result(true, SUCCESS, data, count);
    }


    public static com.group.KGMS.utils.JsonResult success(String msg, Object data) {
        return result(true, msg, data, null);
    }

    public static com.group.KGMS.utils.JsonResult success(String msg, Object data, Long count) {
        return result(true, msg, data, count);
    }

    /**
     * 失败的响应
     *
     * @return
     */
    public static com.group.KGMS.utils.JsonResult error() {
        return result(false, ERROR, null, null);
    }

    public static com.group.KGMS.utils.JsonResult error(String msg) {
        return result(false, msg, null, null);
    }

    public static com.group.KGMS.utils.JsonResult error(Object data) {
        return result(false, ERROR, data, null);
    }

    public static com.group.KGMS.utils.JsonResult error(Object data, Long count) {
        return result(false, ERROR, data, count);
    }

    public static com.group.KGMS.utils.JsonResult error(String msg, Object data) {
        return result(false, msg, data, null);
    }

    public static com.group.KGMS.utils.JsonResult error(String msg, Object data, Long count) {
        return result(false, msg, data, count);
    }

    public static com.group.KGMS.utils.JsonResult result(Boolean result, String msg, Object data, Long count) {
        com.group.KGMS.utils.JsonResult jsonResult = new com.group.KGMS.utils.JsonResult();
        jsonResult.setResult(result);
        jsonResult.setMsg(msg);
        jsonResult.setData(data);
        jsonResult.setCount(count);
        return jsonResult;
    }

    private void setCount(Long count) {
        this.count = count;
    }

    private void setData(Object data) {
        this.data = data;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    private void setResult(Boolean result) {
        this.result = result;
    }

}
