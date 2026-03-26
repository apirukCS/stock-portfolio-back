package com.stock_portfolio.stock_portfolio_api.repository;

import com.stock_portfolio.stock_portfolio_api.entity.StockTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long>, JpaSpecificationExecutor<StockTransaction> {

    // 1. ดึง transactions ของ user (เรียงตามวันที่)
    List<StockTransaction> findByUserIdOrderByTransactionDateDesc(Long userId);

    // Pagination สำหรับ user transactions
//    Page<StockTransaction> findByUserIdOrderByTransactionDateDesc(Long userId, Pageable pageable);

        List<StockTransaction> findByUserId(Long userId);
    Page<StockTransaction> findByUserIdOrderByTransactionDateDesc(Long userId, Pageable pageable);

//    @Query(value = """
//    SELECT * FROM stock_transaction st
//    WHERE st.user_id = :userId
//      AND (:stockId IS NULL OR st.stock_id = :stockId)
//      AND (:startDate IS NULL OR DATE(st.transaction_date) >= :startDate)
//      AND (:endDate IS NULL OR DATE(st.transaction_date) <= :endDate)
//    ORDER BY st.transaction_date DESC
//    """,
//            countQuery = """
//    SELECT COUNT(*) FROM stock_transaction st
//    WHERE st.user_id = :userId
//      AND (:stockId IS NULL OR st.stock_id = :stockId)
//      AND (:startDate IS NULL OR DATE(st.transaction_date) >= :startDate)
//      AND (:endDate IS NULL OR DATE(st.transaction_date) <= :endDate)
//    """,
//            nativeQuery = true)
//    Page<StockTransaction> findTransactionsByUserId(
//            @Param("userId") Long userId,
//            @Param("stockId") Long stockId,
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate,
//            Pageable pageable);





    Optional<StockTransaction> findByIdAndUserId(Long id, Long userId);
//    List<StockTransaction> findByUserIdAndStockId(Long userId, Long stockId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Stock s WHERE s.userId = :userId AND s.id = :stockId")
    boolean existsStockByUserIdAndStockId(Long userId, Long stockId);

//    Optional<StockTransaction> findByIdAndUserId(Long id, Long userId);

    // 11. Delete transactions ของ stock (เมื่อลบ stock)
    void deleteByStockId(Long stockId);

}

