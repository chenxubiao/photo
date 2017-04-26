package cn.chenxubiao.common.bean;

import cn.chenxubiao.common.utils.JsonUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by chenxb on 17-3-31.
 */
public final class ResponseEntity implements Serializable {
    private static final long serialVersionUID = 5883496224129191159L;
    private boolean success;
    private String message;
    private int errorCode;
    private transient String json = null;
    private Map<String, Object> vars;
    private Map<String, Object> attributes;

    public ResponseEntity() {
    }

    private ResponseEntity(boolean success) {
        this.success = success;
    }

    private ResponseEntity(boolean success, int errorCode) {
        this.success = success;
        this.errorCode = errorCode;
    }

    public static ResponseEntity success() {
        return new ResponseEntity(true);
    }

    public static ResponseEntity success(String message) {
        ResponseEntity dataholder = new ResponseEntity(true);
        dataholder.setMessage(message);
        return dataholder;
    }

    public static ResponseEntity failure(String message) {
        ResponseEntity dataholder = new ResponseEntity(false);
        dataholder.setMessage(message);
        return dataholder;
    }

    public static ResponseEntity failure(String message, int errorCode) {
        ResponseEntity dataholder = new ResponseEntity(false, errorCode);
        dataholder.setMessage(message);
        return dataholder;
    }

    public static ResponseEntity parse(String json) throws Exception {
        if(json != null && json.length() > 0) {
            Map vars = JsonUtil.toMap(json);
            if(vars != null && vars.size() > 0) {
                Boolean success = (Boolean)vars.get("success");
                if(success != null) {
                    ResponseEntity rr = new ResponseEntity(success.booleanValue());
                    rr.setMessage((String)vars.get("message"));
                    rr.vars = vars;
                    vars.remove("success");
                    vars.remove("message");
                    if(!success.booleanValue()) {
                        rr.errorCode = Integer.parseInt("" + vars.get("errorCode"));
                    }

                    return rr;
                }
            }
        }

        return failure("接口调用失败");
    }

    public ResponseEntity set(String key, Object value) {
        if(key != null) {
            if(this.vars == null) {
                this.vars = new LinkedHashMap();
            }

            this.vars.put(key, value);
        }

        return this;
    }

    public ResponseEntity setResult(Object object) {
        return this.set("results", object);
    }

    public ResponseEntity setItems(Collection<?> items) {
        return this.set("items", items);
    }

    public ResponseEntity setAttribute(String key, Object value) {
        if(key != null) {
            if(this.attributes == null) {
                this.attributes = new LinkedHashMap();
            }

            this.attributes.put(key, value);
        }

        return this;
    }

    public <T> T getAttribute(String key) {
        return key != null && this.attributes != null? (T) this.attributes.get(key) :null;
    }

    public <T> T get(String key) {
        return key != null && this.vars != null? (T) this.vars.get(key) :null;
    }

    public String toJson() {
        if(this.json == null) {
            if(this.vars == null) {
                this.vars = new HashMap();
            }

            if(!this.success) {
                this.vars.put("errorCode", Integer.valueOf(this.errorCode));
            }

            this.vars.put("success", Boolean.valueOf(this.success));
            this.vars.put("message", this.message == null?"":this.message);
            this.json = JsonUtil.toString(this.vars);
        }

        return this.json;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public Map<String, Object> getVars() {
        return this.vars;
    }
}
