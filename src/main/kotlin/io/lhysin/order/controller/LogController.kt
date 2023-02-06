package io.lhysin.order.controller

import io.lhysin.order.entity.Log
import io.lhysin.order.service.LogService
import org.springframework.web.bind.annotation.*

@RestController
class LogController(

    private val logService: LogService

) {
    @GetMapping("/api/v1/logs/{id}")
    fun findById(@PathVariable("id") id: Long): Log {
        return logService.findById(id);
    }
    @GetMapping("/api/v1/logs")
    fun findAll(): List<Log> {
        return logService.findAll();
    }

}