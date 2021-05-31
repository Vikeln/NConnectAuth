package co.ke.mymobi.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private Integer id;
    private String code;
    private String name;

    public Item(Integer id) {
        this.id = id;
    }

    public Item(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Item(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    
}