package com.katri.web.platformSvc.myData.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "[플랫폼 서비스] > [내 손안의 시험인증]의 저장 Response")
public class MyDataSaveRes extends Common {


}
