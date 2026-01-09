package com.zenith.companyms.company.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.zenith.companyms.company.CompanyService;
import com.zenith.companyms.company.dto.ReviewMessage;

@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "company-rating-queue")
    public void receiveReviewMessage(ReviewMessage reviewMessage) {
        companyService.updateCompanyRating(reviewMessage);
    }
}
