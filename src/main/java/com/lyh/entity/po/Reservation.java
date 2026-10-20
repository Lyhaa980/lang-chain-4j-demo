package com.lyh.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lyh
 * @since 2026-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 考生姓名
     */
    @TableField("name")
    private String name;

    /**
     * 考生性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 考生手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 沟通时间
     */
    @TableField("communication_time")
    private LocalDateTime communicationTime;

    /**
     * 考生所处的省份
     */
    @TableField("province")
    private String province;

    /**
     * 考生预估分数
     */
    @TableField("estimated_score")
    private Integer estimatedScore;


}
