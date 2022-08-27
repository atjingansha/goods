package com.goods.controller.business;

import com.goods.business.service.ConsumerService;
import com.goods.business.service.HealthService;
import com.goods.common.annotation.ControllerEndpoint;
import com.goods.common.error.BusinessException;
import com.goods.common.model.business.Health;
import com.goods.common.model.system.User;
import com.goods.common.response.ActiveUser;
import com.goods.common.response.ResponseBean;
import com.goods.common.vo.business.HealthVO;
import com.goods.common.vo.business.OutStockVO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author WangJin
 * @create 2022-08-26 15:37
 */
@RequestMapping("business/health")
@RestController
public class HealthController {

    @Autowired
    private HealthService healthService;


    /**
     * 今日是否已报备
     * @return
     */
    @ApiOperation(value = "是否报备",notes = "今日是否已报备")
    @GetMapping("/isReport")
    public ResponseBean isReport(){
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Health report = healthService.isReport(activeUser.getUser().getId());
        return ResponseBean.success(report);
    }


    /**
     * 健康上报
     * @param healthVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "健康上报失败", operation = "健康上报")
    @ApiOperation(value = "健康上报",notes = "用户健康上报")
    @RequiresPermissions({"health:report"})
    @PostMapping("/report")
    public ResponseBean report(@Validated @RequestBody HealthVO healthVO) throws BusinessException {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        healthVO.setUserId(activeUser.getUser().getId());
        healthService.report(healthVO);
        return ResponseBean.success();
    }
}
