package pl.discovery.client2;

class Config {
    private String serviceName;
    private String url;

    public Config(String serviceName, String url) {
        this.serviceName = serviceName;
        this.url = url;
    }

    public Config() {

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
