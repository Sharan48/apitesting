package pojo;

public class StoreFrontLoginUserResponse {
    private String validity_in_seconds;
    private String created_on;
    private int id;
    private String user_name;
    private Boolean is_expired;
    private String modified_on;
    private int user_id;
    private String last_access_token;
    
    public String getValidity_in_seconds() {
        return validity_in_seconds;
    }
    public void setValidity_in_seconds(String validity_in_seconds) {
        this.validity_in_seconds = validity_in_seconds;
    }
    public String getCreated_on() {
        return created_on;
    }
    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public Boolean getIs_expired() {
        return is_expired;
    }
    public void setIs_expired(Boolean is_expired) {
        this.is_expired = is_expired;
    }
    public String getModified_on() {
        return modified_on;
    }
    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getLast_access_token() {
        return last_access_token;
    }
    public void setLast_access_token(String last_access_token) {
        this.last_access_token = last_access_token;
    }
    public Boolean getIs_active() {
        return is_active;
    }
    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }
    private Boolean is_active;
    
}
