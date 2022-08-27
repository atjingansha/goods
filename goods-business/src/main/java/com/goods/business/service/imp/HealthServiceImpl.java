package com.goods.business.service.imp;

import com.goods.business.mapper.HealthMapper;
import com.goods.business.service.HealthService;
import com.goods.common.error.BusinessCodeEnum;
import com.goods.common.error.BusinessException;
import com.goods.common.model.business.Health;
import com.goods.common.model.system.User;
import com.goods.common.response.ActiveUser;
import com.goods.common.vo.business.HealthVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author WangJin
 * @create 2022-08-26 15:38
 */
@Service
public class HealthServiceImpl implements HealthService {


    @Autowired
    private HealthMapper healthMapper;

    /**
     * 健康上报
     * @param healthVO
     */
    @Override
    public void report(HealthVO healthVO) throws BusinessException {

        Long userId = healthVO.getUserId();


        Health report = isReport(healthVO.getUserId());
        if (report != null) {
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "今日已经打卡,无法重复打卡！");
        }
        Health health = new Health();
        BeanUtils.copyProperties(healthVO, health);
        health.setCreateTime(new Date());
        healthMapper.insert(health);
    }

        /**
         * 今日是否已报备
         * @param id
         * @return
         */
    @Override
    public Health isReport(Long id) {
        List<Health> health=healthMapper.isReport(id);
        if(health.size()>0){
            return  health.get(0);
        }
        return null;
    }
}
