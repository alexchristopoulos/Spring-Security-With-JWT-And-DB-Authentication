package gr.app.springbackend.bean;

public class AuthenticationBean{

    private String accessToken;

    public AuthenticationBean() {
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthenticationBean(String accessToken) {
        this.accessToken = accessToken;
    }
    
}