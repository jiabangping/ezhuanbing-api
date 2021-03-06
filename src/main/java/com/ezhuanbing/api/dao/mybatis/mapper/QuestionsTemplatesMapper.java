package com.ezhuanbing.api.dao.mybatis.mapper;

import com.ezhuanbing.api.dao.mybatis.model.QuestionsTemplates;
import com.ezhuanbing.api.dao.mybatis.model.QuestionsTemplatesCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionsTemplatesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    int countByExample(QuestionsTemplatesCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    int deleteByExample(QuestionsTemplatesCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    int insert(QuestionsTemplates record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    int insertSelective(QuestionsTemplates record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    List<QuestionsTemplates> selectByExample(QuestionsTemplatesCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    QuestionsTemplates selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    int updateByExampleSelective(@Param("record") QuestionsTemplates record, @Param("example") QuestionsTemplatesCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    int updateByExample(@Param("record") QuestionsTemplates record, @Param("example") QuestionsTemplatesCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    int updateByPrimaryKeySelective(QuestionsTemplates record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mb_questionstemplates
     *
     * @mbggenerated Tue Sep 06 17:31:57 CST 2016
     */
    int updateByPrimaryKey(QuestionsTemplates record);
}