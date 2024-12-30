package com.secor.fdamenuservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@Entity
//@Table(name = "menu_view")
@Getter @Setter
public class MenuView {
    //@Id
    //@Column(name = "menuid", nullable = false, length = 50)
    private String menuid;

    List<RestroMenuView> restroMenuViewList;
}