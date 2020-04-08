package com.mazzama.learn.newstopic.dto.response;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class BaseResponse {
    private Long id;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
}
