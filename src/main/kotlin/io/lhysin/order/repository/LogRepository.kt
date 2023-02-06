package io.lhysin.order.repository

import io.lhysin.order.entity.Log
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LogRepository : JpaRepository<Log, Long> {
}