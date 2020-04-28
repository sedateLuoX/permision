package com.mmall.service;


import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.MD5Util;
import com.mmall.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysLogService sysLogService;

    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkTelePhoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已经被使用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已经被使用");
        }

        String password = PasswordUtil.randomPassword();

        password ="123456";

        String  encodePassword = MD5Util.encrypt(password);
        SysUser sysUser = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone())
                .mail(param.getMail()).password(encodePassword)
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();

        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysUser.setOperateTime(new Date());

        //todo  : 发送邮件
        userMapper.insertSelective(sysUser);
        sysLogService.saveUserLog(null, sysUser);
    }


    public void update(UserParam param){
        BeanValidator.check(param);
        if (checkTelePhoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已经被使用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已经被使用");
        }
        SysUser before = userMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待跟新用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).
                telephone(param.getTelephone()).mail(param.getMail()).
                deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        userMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveUserLog(before, after);
    }

    public boolean checkTelePhoneExist(String telePhone ,Integer userId){

        return userMapper.countByTelephone(telePhone,userId)>0;
    }

    public boolean checkEmailExist(String Email ,Integer userId){

        return userMapper.countByMail(Email,userId)>0;
    }

    public SysUser findByKeyword(String keyWord){
        return userMapper.findByKeyword(keyWord);
    }

    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
        BeanValidator.check(page);
        int count = userMapper.countByDeptId(deptId);
        if (count > 0) {
            List<SysUser> list = userMapper.getPageByDeptId(deptId, page);
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    public List<SysUser> getAll() {
        return userMapper.getAll();
    }

}
