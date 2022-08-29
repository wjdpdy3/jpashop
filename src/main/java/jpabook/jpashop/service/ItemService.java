package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //Merge
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    //Flush -> Dirty Checking
    @Transactional
    public Item updateItem(Long itemId, Book param){
        Item findItem = itemRepository.findOne(itemId);
//        findItem.setPrice(param.getPrice());
//        findItem.setName(param.getName());
//        findItem.setStockQuantity(param.getStockQuantity());

        Item changeItem =
                findItem.changeItem(param.getName(), param.getPrice(), param.getStockQuantity());
        return changeItem;
    }

    @Transactional
    public Item updateItemFinal(Long itemId, UpdateItemDto updateItemDto){
        Item findItem = itemRepository.findOne(itemId);
        Item changeItem =
                findItem.changeItem(updateItemDto.getName(), updateItemDto.getPrice(), updateItemDto.getStockQuantity());
        return changeItem;
    }

    public List<Item> findItems(){
        return  itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
