package com.stock_portfolio.stock_portfolio_api.service;

import com.stock_portfolio.stock_portfolio_api.dto.request.TargetRequest;
import com.stock_portfolio.stock_portfolio_api.dto.response.TargetResponse;
import com.stock_portfolio.stock_portfolio_api.entity.Target;
import com.stock_portfolio.stock_portfolio_api.exception.ResourceNotFoundException;
import com.stock_portfolio.stock_portfolio_api.repository.TargetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TargetService {

    private final TargetRepository targetRepository;

    public TargetResponse getByUserId(Long userId) {
        Optional<Target> target = targetRepository.findByUserId(userId);
        return new TargetResponse(target.get().getTarget());
    }

    public Target createTarget(Long userId, TargetRequest request) {
        Target target = Target.builder()
                .userId(userId)
                .target(request.getTarget())
                .build();

        return targetRepository.save(target);
    }

    public Target updateTarget(Long userId, TargetRequest request) {
        Target target = targetRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Target not found"));

        if (!target.getUserId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        target.setTarget(request.getTarget());
        return targetRepository.save(target);
    }
}
