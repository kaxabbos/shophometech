package com.shophometech.controller;

import com.shophometech.controller.main.Attributes;
import com.shophometech.model.Ordering;
import com.shophometech.model.Stats;
import com.shophometech.model.User;
import com.shophometech.model.enums.OrderingStatus;
import com.shophometech.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ordering")
public class OrderingCont extends Attributes {

    @GetMapping
    public String Ordering(Model model) {
        List<User> users = userRepo.findAllByRole(Role.BUYER);
        if (users.size() > 0) {
            addAttributesOrdering(model, users.get(0).getId());
            return "ordering";
        }
        addAttributesIndex(model);
        model.addAttribute("message", "Нету заказов!");
        return "index";
    }

    @PostMapping
    public String OrderingBuyer(Model model, @RequestParam Long idBuyer) {
        addAttributesOrdering(model, idBuyer);
        return "ordering";
    }

    @GetMapping("/confirmed/{idOrdering}")
    public String OrderingConfirmed(Model model, @PathVariable Long idOrdering) {
        Ordering ordering = orderingRepo.getReferenceById(idOrdering);
        ordering.setOrderingStatus(OrderingStatus.CONFIRMED);
        orderingRepo.save(ordering);
        addAttributesOrdering(model, ordering.getUser().getId());
        return "ordering";
    }

    @GetMapping("/confirmed/all/{idBuyer}")
    public String OrderingConfirmedAll(Model model, @PathVariable Long idBuyer) {
        List<Ordering> orderingList = userRepo.getReferenceById(idBuyer).getOrderingList();
        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.NOT_CONFIRMED) {
                i.setOrderingStatus(OrderingStatus.CONFIRMED);
            }
        }
        orderingRepo.saveAll(orderingList);
        addAttributesOrdering(model, idBuyer);
        return "ordering";
    }

    @GetMapping("/shipped/all/{idBuyer}")
    public String OrderingShippedAll(Model model, @PathVariable Long idBuyer) {
        List<Ordering> orderingList = userRepo.getReferenceById(idBuyer).getOrderingList();
        for (Ordering i : orderingList) {
            if (i.getQuantity() > i.getAppliances().getQuantity()) {
                addAttributesOrdering(model, i.getUser().getId());
                model.addAttribute("message", "Недостаточное количество!");
                return "ordering";
            }
        }
        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.CONFIRMED) {
                i.getAppliances().setQuantity(i.getAppliances().getQuantity() - i.getQuantity());
                Stats stats = i.getAppliances().getStats();
                if (i.getAppliances().getDiscount() == 0) {
                    stats.addIncome(i.getQuantity(), i.getSum());
                } else {
                    stats.addIncome(i.getQuantity(), i.calculating());
                }
                i.setOrderingStatus(OrderingStatus.SHIPPED);
            }
        }
        orderingRepo.saveAll(orderingList);
        addAttributesOrdering(model, idBuyer);
        return "ordering";
    }

    @GetMapping("/shipped/{idOrdering}")
    public String OrderingShipped(Model model, @PathVariable Long idOrdering) {
        Ordering ordering = orderingRepo.getReferenceById(idOrdering);

        if (ordering.getQuantity() > ordering.getAppliances().getQuantity()) {
            addAttributesOrdering(model, ordering.getUser().getId());
            model.addAttribute("message", "Недостаточное количество!");
            return "ordering";
        }

        ordering.setOrderingStatus(OrderingStatus.SHIPPED);
        ordering.getAppliances().setQuantity(ordering.getAppliances().getQuantity() - ordering.getQuantity());
        Stats stats = ordering.getAppliances().getStats();
        if (ordering.getAppliances().getDiscount() == 0) {
            stats.addIncome(ordering.getQuantity(), ordering.getSum());
        } else {
            stats.addIncome(ordering.getQuantity(), ordering.calculating());
        }
        orderingRepo.save(ordering);
        addAttributesOrdering(model, ordering.getUser().getId());
        return "ordering";
    }

}
