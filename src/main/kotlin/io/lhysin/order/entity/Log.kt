package io.lhysin.order.entity


import javax.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime


@Entity
@Table(name = "TBL_LOGS")
class Log(
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val logId: Long? = null,

    @Column(nullable = false)
    val msg: String,

    @Column(nullable = true)
    @CreatedDate
    val createdDate: LocalDateTime? = null,

    @Column(nullable = true)
    @LastModifiedDate
    val lastModifiedDate: LocalDateTime? = null,
)