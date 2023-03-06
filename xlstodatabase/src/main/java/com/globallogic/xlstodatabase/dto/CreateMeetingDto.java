package com.globallogic.xlstodatabase.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateMeetingDto {

    String topic;
    String description;
    String startDate;
    String startTime;
    String endTime;
    List<String> attendees;

}
