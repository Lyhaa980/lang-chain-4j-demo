package com.lyh.tools;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyh.entity.po.Reservation;
import com.lyh.service.IReservationService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Component

public class ReservationTools {

    @Autowired
    private IReservationService reservationService;


//    查询工具方法，按电话查询

    /**
     * 根据电话查询
     */
    @Tool("根据预约学生的电话查询预约信息")
    @GetMapping("/phone/{phone}")
    public Reservation getByPhone(
            @P("预约学生的电话") @PathVariable String phone) {
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        return reservationService.getOne(queryWrapper);
    }


//    添加预约信息工具方法

    /**
     * 新增记录（使用独立参数）
     */
    @Tool("新增预约信息")
    @PostMapping("/add")
    public boolean addReservationTool(
            @P("预约学生的姓名") @RequestParam String name,
            @P("预约学生的性别") @RequestParam String gender,
            @P("预约学生的电话") @RequestParam String phone,
            @P("预约时间，格式：yyyy-MM-dd HH:mm:ss") @RequestParam String communicationTime,
            @P("预约的省份") @RequestParam String province,
            @P("考生的估计分数") @RequestParam Integer estimatedScore) {
        Reservation reservation = new Reservation();
        reservation.setName(name)
                .setGender(gender)
                .setPhone(phone)
                .setCommunicationTime(LocalDateTime.parse(communicationTime))
                .setProvince(province)
                .setEstimatedScore(estimatedScore);
        return reservationService.save(reservation);
    }
}
