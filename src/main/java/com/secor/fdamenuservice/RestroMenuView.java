package com.secor.fdamenuservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@Entity
//@Table(name = "restro_menu_view")
@Getter
@Setter
public class RestroMenuView {
    //@Id
    //@Column(name = "restromenuid", nullable = false, length = 50)
    private String restro_name;
    private String restro_city;
    private List<DishView> dishViewList;
}