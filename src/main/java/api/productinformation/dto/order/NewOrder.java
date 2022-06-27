package api.productinformation.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewOrder {
    private Long userId;
    private Long itemId;
    private int count;
}
