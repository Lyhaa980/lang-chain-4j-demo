package com.lyh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyh.entity.po.Reservation;
import com.lyh.service.IReservationService;
import com.lyh.service.impl.ReservationServiceImpl;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyh
 * @since 2026-10-20
 */
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    /**
     * 批量新增记录
     */
    @PostMapping("/batch")
    public boolean saveBatch(@RequestBody List<Reservation> reservations) {
        return reservationService.saveBatch(reservations);
    }

    /**
     * 查询所有记录
     */
    @GetMapping("/list")
    public List<Reservation> list() {
        return reservationService.list();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    /**
     * 新增记录
     */
    @PostMapping
    @Tool
    public boolean save(@RequestBody Reservation reservation) {
        return reservationService.save(reservation);
    }

    /**
     * 更新记录
     */
    @PutMapping
    public boolean update(@RequestBody Reservation reservation) {
        return reservationService.updateById(reservation);
    }

    /**
     * 删除记录
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return reservationService.removeById(id);
    }

    /**
     * 按姓名模糊查询
     */
    @GetMapping("/search")
    public List<Reservation> searchByName(@RequestParam String name) {
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return reservationService.list(queryWrapper);
    }


}
