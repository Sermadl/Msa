package com.apigateway.aggregation.controller.dto.response;

import com.apigateway.aggregation.client.dto.item.response.ItemResponse;
import com.apigateway.aggregation.client.dto.order.response.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MyPageResponse {
    private List<ItemResponse> recentlyOrderedItems;
    private int orderCount;
    private List<OrderResponse> approvedOrders;
    private List<OrderResponse> completedOrders;
}
