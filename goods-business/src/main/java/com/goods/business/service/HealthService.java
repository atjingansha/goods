package com.goods.business.service;

import com.goods.common.error.BusinessException;
import com.goods.common.model.business.Health;
import com.goods.common.vo.business.HealthVO;

import java.util.Date;

/**
 * @author WangJin
 * @create 2022-08-26 15:38
 */
public interface HealthService {
    void report(HealthVO healthVO) throws BusinessException;


    Health isReport(Long id);
}
