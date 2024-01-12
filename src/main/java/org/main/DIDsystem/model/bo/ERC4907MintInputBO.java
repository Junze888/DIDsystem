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
public class ERC4907MintInputBO {
  private String to;

  private BigInteger tokenId;

  private String url;

  private String location;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(to);
    args.add(tokenId);
    args.add(url);
    args.add(location);
    return args;
  }
}
