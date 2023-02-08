package com.globallogic.xlstodatabase.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmployeeHoursDto {
    Date startDate;
    Date endDate;
    long eid;
    int totalHours;

}
