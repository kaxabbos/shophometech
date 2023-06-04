package com.shophometech.controller.main;

import com.shophometech.model.Ordering;
import com.shophometech.model.Stats;
import com.shophometech.model.User;
import com.shophometech.model.enums.Category;
import com.shophometech.model.enums.OrderingStatus;
import com.shophometech.model.enums.Role;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Attributes extends Main {

    protected void addAttributes(Model model) {
        model.addAttribute("role", getUserRole());
        model.addAttribute("inStock", appliancesRepo.findAll().size());
    }

    protected void addAttributesOrdering(Model model, Long idBuyer) {
        addAttributes(model);
        User selectedBuyer = userRepo.getReferenceById(idBuyer);
        model.addAttribute("buyers", userRepo.findAllByRole(Role.BUYER));
        model.addAttribute("selectedBuyer", selectedBuyer);
        List<Ordering> orderingList = selectedBuyer.getOrderingList();
        List<Ordering> res = new ArrayList<>();

        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.NOT_CONFIRMED) {
                res.add(i);
            }
        }

        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.CONFIRMED) {
                res.add(i);
            }
        }

        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.SHIPPED) {
                res.add(i);
            }
        }

        model.addAttribute("orderings", res);
    }

    protected void addAttributesCart(Model model) {
        addAttributes(model);
        model.addAttribute("carts", orderingRepo.findAllByUserAndOrderingStatus(getUser(), OrderingStatus.ISSUED));
    }

    protected void addAttributesIndex(Model model) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("appliances", appliancesRepo.findAll());
    }

    protected void addAttributesIndex(Model model, Category category) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("appliances", appliancesRepo.findAllByCategory(category));
    }

    protected void addAttributesStat(Model model) {
        addAttributes(model);
        List<Stats> list = statsRepo.findAll();
        for (Stats i : list) {
            i.setIncome(round(i.getIncome(), 2));
        }
        statsRepo.saveAll(list);
        list.sort(Comparator.comparing(Stats::getIncome));
        Collections.reverse(list);
        double sum = 0;
        for (Stats i : list) sum += i.getIncome();
        model.addAttribute("sum", Main.round(sum, 2));
        model.addAttribute("stats", list);

        list.sort(Comparator.comparing(Stats::getQuantity));
        Collections.reverse(list);

        String[] topQuantityName = new String[5];
        int[] topQuantityNumber = new int[5];

        for (int i = 0; i < list.size(); i++) {
            if (i == 5) break;
            topQuantityName[i] = list.get(i).getAppliances().getName();
            topQuantityNumber[i] = list.get(i).getQuantity();
        }

        model.addAttribute("topQuantityName", topQuantityName);
        model.addAttribute("topQuantityNumber", topQuantityNumber);
    }

    protected void addAttributesAppliancesAdd(Model model) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
    }

    protected void addAttributesAppliancesEdit(Model model, Long id) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("appliances", appliancesRepo.getReferenceById(id));
    }

    protected void addAttributesAppliances(Model model, Long id) {
        addAttributes(model);
        model.addAttribute("appliances", appliancesRepo.getReferenceById(id));
    }

    protected void addAttributesUsers(Model model) {
        addAttributes(model);
        model.addAttribute("actual", getUser());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
    }
}
