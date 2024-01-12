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
public class School_TokenTransferInputBO {
  private String from;

  private String to;

  private BigInteger tokenId;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(from);
    args.add(to);
    args.add(tokenId);
    return args;
  }
}
