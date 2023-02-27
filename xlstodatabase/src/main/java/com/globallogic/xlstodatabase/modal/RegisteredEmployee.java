package com.globallogic.xlstodatabase.modal;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class RegisteredEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int registrationId;
    @ManyToOne
            @JoinColumn(name = "mid")
    MeetingDetails mid;
    @ManyToOne
            @JoinColumn(name = "eid")
    Employee eid;
}
