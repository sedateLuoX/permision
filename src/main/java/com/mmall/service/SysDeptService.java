package com.mmall.service;


import com.google.common.base.Preconditions;
import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParma;
import com.mmall.util.BeanValidator;
import com.mmall.util.LeveUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    public void  save(DeptParma parma) {
        BeanValidator.check(parma);
        if (checkExist(parma.getParentId(), parma.getName(), parma.getId())) {
            throw new ParamException("同一层级下存在相同的部门名称");
        }
        SysDept dept = SysDept.builder().name(parma.getName()).parentId(parma.getParentId())
                .seq(parma.getSeq()).remark(parma.getRemark()).build();
        dept.setLevel(LeveUtil.calculateLeve(getLeve(parma.getParentId()), parma.getParentId()));
        dept.setOperator("system"); //todo;
        dept.setOperateIp("127.0.0.1"); //todo;
        dept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(dept);

    }

    public void update(DeptParma parma) {

        BeanValidator.check(parma);
        if (checkExist(parma.getParentId(), parma.getName(), parma.getId())) {
            throw new ParamException("同一层级下存在相同的部门名称");
        }

        SysDept before = sysDeptMapper.selectByPrimaryKey(parma.getId());
        Preconditions.checkNotNull(before,"待跟新部门不存在");

        if (checkExist(parma.getParentId(), parma.getName(), parma.getId())) {
            throw new ParamException("同一层级下存在相同的部门名称");
        }
        SysDept after = SysDept.builder().id(parma.getId()).name(parma.getName()).parentId(parma.getParentId())
                .seq(parma.getSeq()).remark(parma.getRemark()).build();

        after.setLevel(LeveUtil.calculateLeve(getLeve(parma.getParentId()),parma.getParentId()));
        after.setOperator("system-1111"); //todo;
        after.setOperateIp("127.0.0.1"); //todo;
        after.setOperateTime(new Date());

        updateWithChild(before,after);
    }

    @Transactional
    public void updateWithChild(SysDept before , SysDept after){

        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();

        if(!after.getLevel().equals(before.getLevel())){
            List<SysDept> deptList = sysDeptMapper.getChildDeptByLevel(before.getLevel());
            if(CollectionUtils.isNotEmpty(deptList)){
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if(level.indexOf(oldLevelPrefix)==0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);

    }


    private boolean checkExist( Integer parentId , String deptName, Integer deptId){

        return sysDeptMapper.countByNameAndParentId(parentId,deptName,deptId)>0;
    }

    private String getLeve(Integer deptId) {

        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);

        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }


}
