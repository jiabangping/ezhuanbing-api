package com.ezhuanbing.api.dao.mybatis.mapper;

import com.ezhuanbing.api.dao.mybatis.model.DoctorEducationFile;
import com.ezhuanbing.api.dao.mybatis.model.DoctorEducationFileCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DoctorEducationFileMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    int countByExample(DoctorEducationFileCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    int deleteByExample(DoctorEducationFileCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    int insert(DoctorEducationFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    int insertSelective(DoctorEducationFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    List<DoctorEducationFile> selectByExample(DoctorEducationFileCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    DoctorEducationFile selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    int updateByExampleSelective(@Param("record") DoctorEducationFile record, @Param("example") DoctorEducationFileCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    int updateByExample(@Param("record") DoctorEducationFile record, @Param("example") DoctorEducationFileCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    int updateByPrimaryKeySelective(DoctorEducationFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_doctoreducationfile
     *
     * @mbggenerated Sat Sep 10 11:27:18 CST 2016
     */
    int updateByPrimaryKey(DoctorEducationFile record);
}