package org.lemon.entrance.model;
import lombok.Data;

import java.util.Date;

@Data
public class EntranceRBACModel  {
    String id;
    String devsn;
    String cid;
    Date tmstart;
    Date tmend;
    Integer door1;
    Integer door2;
    Integer door3;
    Integer door4;
}
