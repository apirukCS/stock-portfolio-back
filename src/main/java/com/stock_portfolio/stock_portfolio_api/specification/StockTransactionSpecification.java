package com.stock_portfolio.stock_portfolio_api.specification;
import com.stock_portfolio.stock_portfolio_api.entity.StockTransaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class StockTransactionSpecification {

    public static Specification<StockTransaction> hasUserId(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("userId"), userId);
    }

    public static Specification<StockTransaction> hasStockId(Long stockId) {
        return (root, query, cb) -> {
            if (stockId == null) {
                return null;
            }
            return cb.equal(root.get("stock").get("id"), stockId);
        };
    }

    public static Specification<StockTransaction> startDate(LocalDate startDate) {
        return (root, query, cb) -> {
            if (startDate == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(
                    root.get("transactionDate"), startDate.atStartOfDay()
            );
        };
    }

    public static Specification<StockTransaction> endDate(LocalDate endDate) {
        return (root, query, cb) -> {
            if (endDate == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(
                    root.get("transactionDate"), endDate.atTime(23,59,59)
            );
        };
    }

    public static Specification<StockTransaction> hasTransactionTypes(List<String> types) {
        return (root, query, cb) -> {
            if (types == null || types.isEmpty()) {
                return null;
            }
            return root.get("transactionType").in(types);
        };
    }
}