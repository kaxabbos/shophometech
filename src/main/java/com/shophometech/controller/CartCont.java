package com.shophometech.controller;

import com.shophometech.controller.main.Attributes;
import com.shophometech.model.Appliances;
import com.shophometech.model.Ordering;
import com.shophometech.model.User;
import com.shophometech.model.enums.OrderingStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartCont extends Attributes {

    @GetMapping
    public String Cart(Model model) {
        addAttributesCart(model);
        return "cart";
    }

    @PostMapping("/add/{idAppliances}")
    public String CartAdd(@PathVariable Long idAppliances, @RequestParam int quantity) {
        User user = getUser();
        List<Ordering> orderingList = user.getOrderingList();

        for (Ordering i : orderingList) {
            if (i.getAppliances().getId().equals(idAppliances) && i.getOrderingStatus() == OrderingStatus.ISSUED) {
                System.out.println(1);
                i.addQuantity(quantity);
                orderingRepo.save(i);
                return "redirect:/appliances/{idAppliances}";
            }
        }

        System.out.println(2);
        Appliances appliances = appliancesRepo.getReferenceById(idAppliances);
        Ordering ordering = new Ordering();
        ordering.addAppliancesAndUser(appliances, user);
        ordering.addQuantity(quantity);
        orderingRepo.save(ordering);
        return "redirect:/appliances/{idAppliances}";
    }

    @PostMapping("/buy/{id}")
    public String CartBuy(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        ordering.setOrderingStatus(OrderingStatus.NOT_CONFIRMED);
        orderingRepo.save(ordering);
        return "redirect:/cart";
    }

    @GetMapping("/buy/all")
    public String CartBuyAll(Model model) {
        User user = getUser();
        List<Ordering> orderingList = user.getOrderingList();

        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.ISSUED) {
                i.setOrderingStatus(OrderingStatus.NOT_CONFIRMED);
            }
        }
        orderingRepo.saveAll(orderingList);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String CartDelete(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        User user = ordering.getUser();
        user.removeOrdering(ordering);
        userRepo.save(user);
        return "redirect:/cart";
    }

    @GetMapping("/delete/all")
    public String CartDeleteAll() {
        User user = getUser();
        List<Ordering> orderingList = user.getOrderingList();
        List<Ordering> list = new ArrayList<>();
        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.ISSUED) {
                list.add(i);
            }
        }
        for (Ordering i : list) {
            orderingRepo.save(i);
            user.removeOrdering(i);
        }
        userRepo.save(user);
        return "redirect:/cart";
    }
}
