package io.lhysin.order.service

import io.lhysin.order.entity.Log
import io.lhysin.order.repository.LogRepository
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class LogService (
    private val logRepository: LogRepository
) {
    fun findById(id: Long): Log {
        return logRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun createLog(msg: String): Long {
        val log = Log(msg = msg)
        logRepository.save(log)
        return log.logId ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun findAll(): List<Log> {
       return logRepository.findAll()
    }

}