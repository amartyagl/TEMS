package com.globallogic.xlstodatabase.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class SMEDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int smeId;
    @ManyToOne
    @JoinColumn(name = "eid")
    Employee eid;
    String topic;
}
