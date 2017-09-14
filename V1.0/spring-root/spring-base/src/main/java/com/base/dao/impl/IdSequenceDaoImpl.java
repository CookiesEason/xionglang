package com.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.base.dao.IdSequenceDao;
import com.base.generic.dao.impl.GenericOneDaoImpl;
import com.base.one.entity.IdSequence;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月21日
 * @此类作用
 */
@Repository
public class IdSequenceDaoImpl extends GenericOneDaoImpl<IdSequence> implements IdSequenceDao {

}
