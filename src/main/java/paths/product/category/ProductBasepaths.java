package paths.product.category;

public class ProductBasepaths {
    
    private String baseuripath="http://43.204.100.142:8010/products";
    private String endpointpath="/v1/category";
    private String bearerbasepath="http://43.204.100.142:8011/users-services";
    private String bearerendpoint="/v1/token/login";
    public String getBaseuripath() {
        return baseuripath;
    }
    public String getEndpointpath() {
        return endpointpath;
    }
    public String getBearerbasepath() {
        return bearerbasepath;
    }
    public String getBearerendpoint() {
        return bearerendpoint;
    }
   
    
}
