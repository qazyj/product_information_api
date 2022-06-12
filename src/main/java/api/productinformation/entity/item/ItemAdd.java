package api.productinformation.entity.item;

import api.productinformation.entity.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ItemAdd {
    private String itemName;
    private String itemType;
    private Long itemPrice;
    private String startDate;
    private String endDate;


    @DateTimeFormat(pattern = "yyyy.M.d")
    private LocalDate startDateLocalType;
    @DateTimeFormat(pattern = "yyyy.M.d")
    private LocalDate endDateLocalType;

    public ItemAdd(String itemName, String itemType, Long itemPrice, String startDate, String endDate) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.startDate = startDate;
        this.endDate = startDate;
    }

    public void StringToLocalDate() {
        this.startDateLocalType = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
        this.endDateLocalType = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
    }
}
