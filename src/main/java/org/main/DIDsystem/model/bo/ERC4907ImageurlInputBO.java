package org.main.DIDsystem.model.bo;

import java.lang.Object;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ERC4907ImageurlInputBO extends BO{
  private BigInteger tokenId;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(tokenId);
    return args;
  }
}
