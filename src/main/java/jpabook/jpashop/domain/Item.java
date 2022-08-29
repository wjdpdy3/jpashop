package jpabook.jpashop.domain;

import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;


    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // name, price, stockQuantity 변경
    public Item changeItem(String name, int price, int stockQuantity){
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
        return this;
    }

    //==비즈니스 로직==//

    /**
     * stcok 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * @param quantity
     */
    public void removeStock(int quantity){
        int resStock = this.stockQuantity - quantity;
        if (resStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = resStock;
    }

}
