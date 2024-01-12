package org.main.DIDsystem.model.bo;

import java.lang.Object;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ERC4907SetUserInputBO {
  private BigInteger tokenId;

  private String user;

  private BigInteger useTime;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(tokenId);
    args.add(user);
    args.add(useTime);
    return args;
  }
}
