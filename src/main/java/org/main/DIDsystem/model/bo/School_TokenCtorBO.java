package org.main.DIDsystem.model.bo;

import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class School_TokenCtorBO {
  private String name_;

  private String symbol_;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(name_);
    args.add(symbol_);
    return args;
  }
}
