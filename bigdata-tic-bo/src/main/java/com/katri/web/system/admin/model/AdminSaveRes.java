package com.katri.web.system.admin.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Admin 등록 response")
@ToString
public class AdminSaveRes extends Common {

}
