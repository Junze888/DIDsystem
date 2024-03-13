package org.main.DIDsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    public static final String U_FAIL = "1";

    private String did;
    private String Nickname;
    private String Document;




}
