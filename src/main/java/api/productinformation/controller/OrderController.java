package api.productinformation.controller;

import api.productinformation.dto.RequestDto;
import api.productinformation.dto.order.NewOrder;
import api.productinformation.dto.user.NewUser;
import api.productinformation.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveOrder(@RequestBody NewOrder newOrder) {

        return orderService.order(newOrder.getUserId(), newOrder.getItemId(), newOrder.getCount());
    }

    @PostMapping("/cancel")
    public ResponseEntity<Object> cancelOrder(@RequestBody RequestDto requestDto){
        return orderService.cancelOrder(requestDto.getId());
    }
}
