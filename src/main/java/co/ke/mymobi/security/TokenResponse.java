package co.ke.mymobi.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenResponse {
    private String accessToken;
    private Date expiry;
//    private AccountModel user;

}
