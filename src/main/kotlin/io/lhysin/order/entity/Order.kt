package io.lhysin.order.entity

import io.lhysin.order.type.OrderType
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "ORDERS")
class Order(
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null,

    @Column(nullable = false)
    val itemId: Long,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val orderType: OrderType,

//    @Column(nullable = false)
//    @CreatedBy
//    val createdBy: String? = null,

    @Column(nullable = true)
    @CreatedDate
    val createdDate: LocalDateTime? = null,

//    @Column(nullable = true)
//    @LastModifiedBy
//    val lastModifiedBy: String? = null,

    @Column(nullable = true)
    @LastModifiedDate
    val lastModifiedDate: LocalDateTime? = null,
)